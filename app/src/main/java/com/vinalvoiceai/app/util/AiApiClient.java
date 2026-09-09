package com.vinalvoiceai.app.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

public class AiApiClient {
    public static final String DEFAULT_BASE = "https://api.x.ai/v1";
    public static final String DEFAULT_MODEL = "grok-2-latest";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;

    public AiApiClient() {
        client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    }

    public interface StreamCallback {
        void onChunk(String chunk, String accumulated);
        void onComplete(String fullResponse);
        void onError(String error);
    }

    public void streamChat(String apiKey, String model, JSONArray messages, String systemPrompt, StreamCallback cb) {
        try {
            JSONObject payload = new JSONObject();
            payload.put("model", model != null ? model : DEFAULT_MODEL);
            payload.put("stream", true);
            payload.put("temperature", 0.7);

            JSONArray msgs = new JSONArray();
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                JSONObject sys = new JSONObject();
                sys.put("role", "system");
                sys.put("content", systemPrompt);
                msgs.put(sys);
            }
            for (int i = 0; i < messages.length(); i++) msgs.put(messages.get(i));
            payload.put("messages", msgs);

            Request req = new Request.Builder()
                .url(DEFAULT_BASE + "/chat/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(payload.toString(), JSON))
                .build();

            StringBuilder acc = new StringBuilder();
            EventSource es = EventSources.createFactory(client).newEventSource(req, new EventSourceListener() {
                @Override
                public void onEvent(EventSource es, String id, String type, String data) {
                    if (data == null || data.equals("[DONE]")) return;
                    try {
                        JSONObject obj = new JSONObject(data);
                        JSONArray choices = obj.optJSONArray("choices");
                        if (choices != null && choices.length() > 0) {
                            JSONObject delta = choices.getJSONObject(0).optJSONObject("delta");
                            if (delta != null) {
                                String content = delta.optString("content", "");
                                if (!content.isEmpty()) {
                                    acc.append(content);
                                    cb.onChunk(content, acc.toString());
                                }
                            }
                        }
                    } catch (Exception ignored) {}
                }

                @Override
                public void onClosed(EventSource es) {
                    cb.onComplete(acc.toString());
                }

                @Override
                public void onFailure(EventSource es, Throwable t, Response r) {
                    cb.onError(t != null ? t.getMessage() : "Unknown error");
                }
            });
        } catch (Exception e) {
            cb.onError(e.getMessage());
        }
    }
}
