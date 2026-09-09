package com.vinalvoiceai.app.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.VinalApp;
import com.vinalvoiceai.app.ui.audio.AudioSettingsActivity;
import com.vinalvoiceai.app.ui.bahasa.BahasaActivity;
import com.vinalvoiceai.app.ui.chat.ChatActivity;
import com.vinalvoiceai.app.ui.gaya.GayaActivity;
import com.vinalvoiceai.app.ui.settings.ApiSettingsActivity;
import com.vinalvoiceai.app.util.LangManager;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_PERM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        bindCard(R.id.card_chat, R.string.menu_chat, v -> openChat());
        bindCard(R.id.card_api, R.string.menu_api, v -> openApiSettings());
        bindCard(R.id.card_gaya, R.string.menu_gaya, v -> openGaya());
        bindCard(R.id.card_audio, R.string.menu_audio, v -> openAudioSettings());
        bindCard(R.id.card_bahasa, R.string.menu_bahasa, v -> openBahasa());
        bindCard(R.id.card_about, R.string.menu_about, v -> openAbout());

        requestNeededPerms();
        VinalApp.get().tts().speak(getString(R.string.welcome_tts));
    }

    private void bindCard(int id, int titleRes, android.view.View.OnClickListener l) {
        CardView cv = findViewById(id);
        if (cv != null) {
            ((android.widget.TextView) cv.findViewById(R.id.card_title)).setText(titleRes);
            cv.setOnClickListener(l);
            cv.setFocusable(true);
            cv.setContentDescription(getString(titleRes));
        }
    }

    private void openChat() {
        VinalApp.get().vibe().vibrate("short");
        startActivity(new Intent(this, ChatActivity.class));
    }

    private void openApiSettings() {
        VinalApp.get().vibe().vibrate("short");
        startActivity(new Intent(this, ApiSettingsActivity.class));
    }

    private void openGaya() {
        VinalApp.get().vibe().vibrate("short");
        startActivity(new Intent(this, GayaActivity.class));
    }

    private void openAudioSettings() {
        VinalApp.get().vibe().vibrate("short");
        startActivity(new Intent(this, AudioSettingsActivity.class));
    }

    private void openBahasa() {
        VinalApp.get().vibe().vibrate("short");
        startActivity(new Intent(this, BahasaActivity.class));
    }

    private void openAbout() {
        VinalApp.get().vibe().vibrate("short");
        new AlertDialog.Builder(this)
            .setTitle(R.string.menu_about)
            .setMessage(R.string.about_text)
            .setPositiveButton(R.string.close, null)
            .show();
    }

    private void requestNeededPerms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQ_PERM);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_accessibility) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
