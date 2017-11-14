package com.example.quan.english.fragment.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ShareFB;
import com.example.quan.english.fragment.audio.AudioPlayActivity;
import com.example.quan.english.fragment.menu.ClickListener;

import java.util.ArrayList;

public class AudioHomeAdapter extends RecyclerView.Adapter<AudioHomeAdapter.ViewHolder> {
    private ArrayList<String> strings;
    private ClickListener clickListener;
    private Context context;

    public AudioHomeAdapter(Context context, ArrayList<String> strings) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_rcv_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvAudio.setText(String.valueOf(strings.get(position).charAt(0)).toUpperCase());
        holder.tvNameAudio.setText(strings.get(position));
        holder.imvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_list_view);
                dialog.setCancelable(true);
                final String title = strings.get(position);
                dialog.setTitle(title);
                String[] strings = {"Listen", "Share to Facebook"};
                ListView livMenu = (ListView) dialog.findViewById(R.id.liv_dialog_menu);
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, strings);
                livMenu.setAdapter(arrayAdapter);
                livMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 1:
                                ShareFB shareFB = new ShareFB("http://www.listenaminute.com/" + title.charAt(0) + "/" + title + ".html", "http://i.imgur.com/SMkVfgc.png", title, context);
                                shareFB.share();
                                break;
                            case 0:
                                Intent intent = new Intent(context, AudioPlayActivity.class);
                                intent.putExtra(Key.LINK_AUDIO, title);
                                context.startActivity(intent);
                                break;
                        }
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvAudio, tvNameAudio;
        private ImageView imvMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAudio = (TextView) itemView.findViewById(R.id.tv_item_rcv_audio);
            tvNameAudio = (TextView) itemView.findViewById(R.id.tv_name_item_rcv_audio);
            imvMenu = (ImageView) itemView.findViewById(R.id.imv_item_rcv_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.itemClicked(v, getAdapterPosition());
        }
    }

    public void setClickListener(ClickListener clickListener) {
        if (clickListener != null) {
            this.clickListener = clickListener;
        }
    }
}