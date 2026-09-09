package com.vinalvoiceai.app.ui.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.VinalApp;
import com.vinalvoiceai.app.data.AppDatabase;
import com.vinalvoiceai.app.util.AiApiClient;
import com.vinalvoiceai.app.util.LangManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private static final int REQ_STT = 200;
    private ChatAdapter adapter;
    private EditText input;
    private SpeechRecognizer speechRecognizer;
    private boolean isStreaming = false;
    private String systemPrompt = "Lu asisten AI yang helpful, sopan, dan selalu pakai bahasa yang user minta.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.chat_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rv = findViewById(R.id.rv_chat);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter();
        rv.setAdapter(adapter);

        input = findViewById(R.id.et_input);
        ImageButton btnMic = findViewById(R.id.btn_mic);
        ImageButton btnSend = findViewById(R.id.btn_send);
        ImageButton btnClear = findViewById(R.id.btn_clear);

        loadHistory();

        btnSend.setOnClickListener(v -> sendMessage());
        btnClear.setOnClickListener(v -> {
            VinalApp.get().db().clearChatHistory();
            adapter.clear();
            VinalApp.get().tts().speak(getString(R.string.history_cleared));
        });

        btnMic.setOnClickListener(v -> startVoiceInput());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQ_STT);
        }
    }

    private void loadHistory() {
        List<AppDatabase.ChatMessage> hist = VinalApp.get().db().getChatHistory();
        adapter.setItems(hist);
    }

    private void sendMessage() {
        if (isStreaming) return;
        String text = input.getText().toString().trim();
        if (TextUtils.isEmpty(text)) return;

        String apiKey = VinalApp.get().db().getString("api_key", "");
        if (TextUtils.isEmpty(apiKey)) {
            Toast.makeText(this, R.string.err_no_api_key, Toast.LENGTH_LONG).show();
            return;
        }

        VinalApp.get().db().saveChatHistory(appendToHistory("user", text));
        adapter.append("user", text);
        input.setText("");
        VinalApp.get().vibe().vibrate("mulai");

        adapter.append("assistant", getString(R.string.ai_thinking));
        streamToServer(apiKey, text);
    }

    private void streamToServer(String apiKey, String newMessage) {
        isStreaming = true;
        List<AppDatabase.ChatMessage> hist = VinalApp.get().db().getChatHistory();
        JSONArray msgs = new JSONArray();
        try {
            for (AppDatabase.ChatMessage m : hist) {
                JSONObject o = new JSONObject();
                o.put("role", m.role);
                o.put("content", m.content);
                msgs.put(o);
            }
        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show());
            isStreaming = false;
            return;
        }

        new AiApiClient().streamChat(apiKey, null, msgs, systemPrompt, new AiApiClient.StreamCallback() {
            @Override public void onChunk(String chunk, String acc) {
                runOnUiThread(() -> adapter.updateLast("assistant", acc));
            }
            @Override public void onComplete(String full) {
                runOnUiThread(() -> {
                    isStreaming = false;
                    VinalApp.get().vibe().vibrate("selesai");
                    VinalApp.get().db().saveChatHistory(updateLastInHistory("assistant", full));
                    VinalApp.get().tts().speak(full);
                });
            }
            @Override public void onError(String error) {
                runOnUiThread(() -> {
                    isStreaming = false;
                    String err = getString(R.string.err_generic) + ": " + error;
                    adapter.updateLast("assistant", err);
                    VinalApp.get().db().saveChatHistory(updateLastInHistory("assistant", err));
                    VinalApp.get().vibe().vibrate("error");
                });
            }
        });
    }

    private List<AppDatabase.ChatMessage> appendToHistory(String role, String content) {
        List<AppDatabase.ChatMessage> list = VinalApp.get().db().getChatHistory();
        list.add(new AppDatabase.ChatMessage(role, content));
        return list;
    }

    private List<AppDatabase.ChatMessage> updateLastInHistory(String role, String content) {
        List<AppDatabase.ChatMessage> list = VinalApp.get().db().getChatHistory();
        if (!list.isEmpty()) {
            list.set(list.size() - 1, new AppDatabase.ChatMessage(role, content));
        } else {
            list.add(new AppDatabase.ChatMessage(role, content));
        }
        return list;
    }

    private void startVoiceInput() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, R.string.err_no_stt, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toLanguageTag());

        if (speechRecognizer != null) speechRecognizer.destroy();
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override public void onReadyForSpeech(Bundle params) {}
            @Override public void onBeginningOfSpeech() {}
            @Override public void onRmsChanged(float rmsdB) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onEndOfSpeech() {}
            @Override public void onError(int error) {
                Toast.makeText(ChatActivity.this, getString(R.string.err_stt) + " (" + error + ")", Toast.LENGTH_SHORT).show();
            }
            @Override public void onResults(Bundle results) {
                java.util.ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (list != null && !list.isEmpty()) {
                    String cur = input.getText().toString();
                    input.setText(cur + (cur.isEmpty() ? "" : " ") + list.get(0));
                }
            }
            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onEvent(int eventType, Bundle params) {}
        });
        speechRecognizer.startListening(intent);
    }

    @Override
    protected void onDestroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
            speechRecognizer = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
