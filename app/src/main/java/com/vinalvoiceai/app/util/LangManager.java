package com.vinalvoiceai.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LangManager {
    public static final String[] SUPPORTED = {"id", "en", "ar", "es", "ms"};
    public static final String[] NAMES = {
        "Bahasa Indonesia", "English", "العربية", "Español", "Bahasa Melayu"
    };

    public static String getCurrentLang(Context ctx) {
        SharedPreferences p = ctx.getSharedPreferences("vinal_prefs", Context.MODE_PRIVATE);
        return p.getString("lang", "id");
    }

    public static void setLang(Context ctx, String code) {
        SharedPreferences p = ctx.getSharedPreferences("vinal_prefs", Context.MODE_PRIVATE);
        p.edit().putString("lang", code).apply();
        applyLocale(ctx, code);
    }

    public static void applyLocale(Context ctx, String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources res = ctx.getResources();
        Configuration cfg = new Configuration(res.getConfiguration());
        cfg.setLocale(locale);
        res.updateConfiguration(cfg, res.getDisplayMetrics());
    }
}
