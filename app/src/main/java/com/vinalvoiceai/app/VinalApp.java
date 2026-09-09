package com.vinalvoiceai.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.vinalvoiceai.app.data.AppDatabase;
import com.vinalvoiceai.app.util.LangManager;
import com.vinalvoiceai.app.util.TtsManager;
import com.vinalvoiceai.app.util.VibrationManager;

public class VinalApp extends Application {
    private static final String PREFS_NAME = "vinal_prefs";
    private static VinalApp instance;
    private SharedPreferences prefs;
    private AppDatabase database;
    private TtsManager ttsManager;
    private VibrationManager vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        database = new AppDatabase(this);
        ttsManager = new TtsManager(this);
        vibrator = new VibrationManager(this);
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
    }

    public static VinalApp get() { return instance; }
    public SharedPreferences prefs() { return prefs; }
    public AppDatabase db() { return database; }
    public TtsManager tts() { return ttsManager; }
    public VibrationManager vibe() { return vibrator; }
}
