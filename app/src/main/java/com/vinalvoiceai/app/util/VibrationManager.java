package com.vinalvoiceai.app.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

public class VibrationManager {
    private final Vibrator vibrator;

    public VibrationManager(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager vm = (VibratorManager) ctx.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vibrator = vm != null ? vm.getDefaultVibrator() : null;
        } else {
            vibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        }
    }

    public void vibrate(String type) {
        if (vibrator == null || !vibrator.hasVibrator()) return;
        long ms;
        switch (type) {
            case "short": ms = 30; break;
            case "mulai": ms = 80; break;
            case "selesai": ms = 200; break;
            case "error": ms = 400; break;
            default: ms = 50;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(ms);
        }
    }
}
