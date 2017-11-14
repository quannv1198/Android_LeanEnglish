package com.example.quan.english.fragment.menu.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quan.english.Key;
import com.example.quan.english.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<Chat> chats;
    private Context context;

    public ChatAdapter(Context context, ArrayList<Chat> chats) {
        this.chats = chats;
        this.context = context;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_chat_me, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_chat_simsimi, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        if (!chats.get(position).getMe()) {
            holder.tvChat.setText(chats.get(position).getContent());
        } else {
            holder.tvChat.setText(chats.get(position).getContent());
            Glide.with(context).load("https://graph.facebook.com/" + Key.getUserId() + "/picture?type=large").into(holder.circleImageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!chats.get(position).getMe()) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return chats == null ? 0 : chats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChat;
        private CircleImageView circleImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView =(CircleImageView)itemView.findViewById(R.id.clv_avatar_my_chat);
            tvChat = (TextView) itemView.findViewById(R.id.tv_content_chat);
        }
    }
}