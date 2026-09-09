package com.vinalvoiceai.app.ui.gaya;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.VinalApp;
import com.vinalvoiceai.app.util.GayaManager;
import com.vinalvoiceai.app.util.LangManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GayaActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private GayaAdapter adapter;
    private List<GayaManager.Category> categories;
    private Map<GayaManager.Category, List<GayaManager.Gaya>> dataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LangManager.applyLocale(this, LangManager.getCurrentLang(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaya);

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_gaya);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listView = findViewById(R.id.lv_gaya);
        categories = GayaManager.getAllCategories(this);
        for (GayaManager.Category c : categories) {
            dataMap.put(c, c.items);
        }
        adapter = new GayaAdapter(this, categories, dataMap);
        listView.setAdapter(adapter);

        listView.setOnChildClickListener((parent, v, groupPos, childPos, id) -> {
            GayaManager.Gaya g = categories.get(groupPos).items.get(childPos);
            int selectedId = VinalApp.get().db().getInt("selected_gaya_id", 0);
            VinalApp.get().db().putInt("selected_gaya_id", g.id);
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText("Gaya", g.name + "\n\n" + g.description));
            VinalApp.get().tts().speak(getString(R.string.gaya_selected) + ": " + g.name);
            Toast.makeText(this, getString(R.string.gaya_selected) + ": " + g.name, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
