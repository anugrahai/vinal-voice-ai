package com.vinalvoiceai.app.ui.bahasa;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.VinalApp;
import com.vinalvoiceai.app.util.LangManager;

public class BahasaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bahasa);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_bahasa);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ListView lv = findViewById(R.id.lv_bahasa);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, LangManager.NAMES);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, v, pos, id) -> {
            String code = LangManager.SUPPORTED[pos];
            LangManager.setLang(this, code);
            VinalApp.get().tts().speak(getString(R.string.lang_saved));
            Toast.makeText(this, R.string.lang_saved, Toast.LENGTH_SHORT).show();
            recreate();
        });
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
