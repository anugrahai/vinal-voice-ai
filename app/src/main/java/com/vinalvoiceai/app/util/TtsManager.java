package com.vinalvoiceai.app.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

public class TtsManager {
    private TextToSpeech tts;
    private boolean ready = false;

    public TtsManager(Context ctx) {
        tts = new TextToSpeech(ctx, status -> {
            ready = status == TextToSpeech.SUCCESS;
            if (ready && tts != null) {
                tts.setLanguage(Locale.getDefault());
                tts.setSpeechRate(1.0f);
                tts.setPitch(1.0f);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tts.setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build());
                }
            }
        });
    }

    public void speak(String text) {
        if (ready && tts != null && text != null && !text.isEmpty()) {
            tts.stop();
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "vinal_" + System.currentTimeMillis());
        }
    }

    public void setLanguage(Locale locale) {
        if (ready && tts != null) tts.setLanguage(locale);
    }

    public void setRate(float rate) {
        if (ready && tts != null) tts.setSpeechRate(rate);
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
