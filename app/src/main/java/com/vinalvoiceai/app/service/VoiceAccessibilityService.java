package com.vinalvoiceai.app.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.vinalvoiceai.app.VinalApp;

public class VoiceAccessibilityService extends AccessibilityService {
    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Hook for global event reading - read aloud mode bisa ditambahin di sini
    }

    @Override
    public void onInterrupt() {}

    public void readAloud(String text) {
        if (VinalApp.get() != null) {
            VinalApp.get().tts().speak(text);
        }
    }
}
