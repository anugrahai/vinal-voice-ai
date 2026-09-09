package com.vinalvoiceai.app.ui.audio;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.VinalApp;
import com.vinalvoiceai.app.util.LangManager;

import java.util.Locale;

public class AudioSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_audio);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SeekBar sbRate = findViewById(R.id.sb_rate);
        SeekBar sbPitch = findViewById(R.id.sb_pitch);
        Switch swVibe = findViewById(R.id.sw_vibration);
        Switch swAudio = findViewById(R.id.sw_audio_enabled);
        TextView tvRate = findViewById(R.id.tv_rate);
        TextView tvPitch = findViewById(R.id.tv_pitch);
        android.widget.Button btnTest = findViewById(R.id.btn_test);

        int ratePct = VinalApp.get().db().getInt("tts_rate", 100);
        int pitchPct = VinalApp.get().db().getInt("tts_pitch", 100);
        sbRate.setProgress(ratePct);
        sbPitch.setProgress(pitchPct);
        tvRate.setText(String.format(Locale.getDefault(), "%d%%", ratePct));
        tvPitch.setText(String.format(Locale.getDefault(), "%d%%", pitchPct));
        swVibe.setChecked(VinalApp.get().db().getBool("vibration_on", true));
        swAudio.setChecked(VinalApp.get().db().getBool("audio_enabled", true));

        sbRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar sb, int p, boolean u) {
                tvRate.setText(String.format(Locale.getDefault(), "%d%%", p));
            }
            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {
                VinalApp.get().db().putInt("tts_rate", sb.getProgress());
                VinalApp.get().tts().setRate(sb.getProgress() / 100f);
            }
        });
        sbPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar sb, int p, boolean u) {
                tvPitch.setText(String.format(Locale.getDefault(), "%d%%", p));
            }
            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {
                VinalApp.get().db().putInt("tts_pitch", sb.getProgress());
            }
        });
        swVibe.setOnCheckedChangeListener((b, c) -> VinalApp.get().db().putBool("vibration_on", c));
        swAudio.setOnCheckedChangeListener((b, c) -> VinalApp.get().db().putBool("audio_enabled", c));
        btnTest.setOnClickListener(v -> {
            VinalApp.get().tts().setRate(sbRate.getProgress() / 100f);
            VinalApp.get().tts().speak(getString(R.string.audio_test));
            if (swVibe.isChecked()) VinalApp.get().vibe().vibrate("mulai");
        });
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
