package com.vinalvoiceai.app.ui.gaya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.util.GayaManager;

import java.util.List;
import java.util.Map;

public class GayaAdapter extends BaseExpandableListAdapter {
    private final Context ctx;
    private final List<GayaManager.Category> groups;
    private final Map<GayaManager.Category, List<GayaManager.Gaya>> children;

    public GayaAdapter(Context ctx, List<GayaManager.Category> groups,
                       Map<GayaManager.Category, List<GayaManager.Gaya>> children) {
        this.ctx = ctx;
        this.groups = groups;
        this.children = children;
    }

    @Override
    public int getGroupCount() { return groups.size(); }
    @Override
    public int getChildrenCount(int groupPos) { return children.get(groups.get(groupPos)).size(); }
    @Override
    public Object getGroup(int g) { return groups.get(g); }
    @Override
    public Object getChild(int g, int c) { return children.get(groups.get(g)).get(c); }
    @Override
    public long getGroupId(int g) { return groups.get(g).id; }
    @Override
    public long getChildId(int g, int c) { return children.get(groups.get(g)).get(c).id; }
    @Override
    public boolean hasStableIds() { return true; }
    @Override
    public boolean isChildSelectable(int g, int c) { return true; }

    @Override
    public View getGroupView(int g, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_gaya_group, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.tv_group);
        tv.setText(groups.get(g).name);
        return convertView;
    }

    @Override
    public View getChildView(int g, int c, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_gaya_child, parent, false);
        }
        GayaManager.Gaya gaya = children.get(groups.get(g)).get(c);
        TextView name = convertView.findViewById(R.id.tv_name);
        TextView desc = convertView.findViewById(R.id.tv_desc);
        name.setText(gaya.name);
        desc.setText(gaya.description);
        return convertView;
    }
}
