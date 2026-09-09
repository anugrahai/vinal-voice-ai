package com.vinalvoiceai.app.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vinalvoiceai.app.R;
import com.vinalvoiceai.app.data.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VH> {

    private final List<AppDatabase.ChatMessage> items = new ArrayList<>();

    public void setItems(List<AppDatabase.ChatMessage> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    public void append(String role, String content) {
        items.add(new AppDatabase.ChatMessage(role, content));
        notifyItemInserted(items.size() - 1);
    }

    public void updateLast(String role, String content) {
        if (items.isEmpty()) return;
        items.set(items.size() - 1, new AppDatabase.ChatMessage(role, content));
        notifyItemChanged(items.size() - 1);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        AppDatabase.ChatMessage m = items.get(position);
        if ("user".equals(m.role)) {
            h.label.setText(R.string.role_user);
            h.label.setTextColor(0xFF1976D2);
            h.card.setCardBackgroundColor(0xFFE3F2FD);
        } else {
            h.label.setText(R.string.role_ai);
            h.label.setTextColor(0xFF388E3C);
            h.card.setCardBackgroundColor(0xFFE8F5E9);
        }
        h.content.setText(m.content);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        CardView card;
        TextView label, content;
        VH(View v) {
            super(v);
            card = v.findViewById(R.id.card_msg);
            label = v.findViewById(R.id.tv_label);
            content = v.findViewById(R.id.tv_content);
        }
    }
}
