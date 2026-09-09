package com.vinalvoiceai.app.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.VinalApp;
import com.vinalvoiceai.app.util.LangManager;

public class ApiSettingsActivity extends AppCompatActivity {
    private EditText etKey, etBase, etModel;
    private android.widget.Switch swCot, swStreaming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_settings);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_api);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etKey = findViewById(R.id.et_api_key);
        etBase = findViewById(R.id.et_base_url);
        etModel = findViewById(R.id.et_model);
        swCot = findViewById(R.id.sw_cot);
        swStreaming = findViewById(R.id.sw_streaming);
        Button btnSave = findViewById(R.id.btn_save);

        etKey.setText(VinalApp.get().db().getString("api_key", ""));
        etBase.setText(VinalApp.get().db().getString("api_base", com.vinalvoiceai.app.util.AiApiClient.DEFAULT_BASE));
        etModel.setText(VinalApp.get().db().getString("api_model", com.vinalvoiceai.app.util.AiApiClient.DEFAULT_MODEL));
        swCot.setChecked(VinalApp.get().db().getBool("ai_cot_enabled", false));
        swStreaming.setChecked(VinalApp.get().db().getBool("ai_streaming", true));

        btnSave.setOnClickListener(v -> {
            VinalApp.get().db().putString("api_key", etKey.getText().toString().trim());
            VinalApp.get().db().putString("api_base", etBase.getText().toString().trim());
            VinalApp.get().db().putString("api_model", etModel.getText().toString().trim());
            VinalApp.get().db().putBool("ai_cot_enabled", swCot.isChecked());
            VinalApp.get().db().putBool("ai_streaming", swStreaming.isChecked());
            VinalApp.get().tts().speak(getString(R.string.saved));
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
