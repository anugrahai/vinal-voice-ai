package com.vinalvoiceai.app.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppDatabase {
    private final SharedPreferences prefs;

    public AppDatabase(Context ctx) {
        prefs = ctx.getSharedPreferences("vinal_db", Context.MODE_PRIVATE);
    }

    // ===== Chat history =====
    public List<ChatMessage> getChatHistory() {
        String json = prefs.getString("chat_hist", "[]");
        List<ChatMessage> list = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                list.add(new ChatMessage(o.optString("role"), o.optString("content")));
            }
        } catch (JSONException ignored) {}
        return list;
    }

    public void saveChatHistory(List<ChatMessage> history) {
        JSONArray arr = new JSONArray();
        try {
            for (ChatMessage m : history) {
                JSONObject o = new JSONObject();
                o.put("role", m.role);
                o.put("content", m.content);
                arr.put(o);
            }
        } catch (JSONException ignored) {}
        prefs.edit().putString("chat_hist", arr.toString()).apply();
    }

    public void clearChatHistory() {
        prefs.edit().remove("chat_hist").apply();
    }

    // ===== Settings =====
    public void putString(String key, String val) { prefs.edit().putString(key, val).apply(); }
    public void putBool(String key, boolean val) { prefs.edit().putBoolean(key, val).apply(); }
    public String getString(String key, String def) { return prefs.getString(key, def); }
    public boolean getBool(String key, boolean def) { return prefs.getBoolean(key, def); }
    public int getInt(String key, int def) { return prefs.getInt(key, def); }
    public void putInt(String key, int val) { prefs.edit().putInt(key, val).apply(); }

    public static class ChatMessage {
        public String role;
        public String content;
        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
