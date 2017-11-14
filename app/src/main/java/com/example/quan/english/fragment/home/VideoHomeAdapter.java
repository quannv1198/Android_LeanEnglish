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

import com.bumptech.glide.Glide;
import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ShareFB;
import com.example.quan.english.fragment.audio.AudioPlayActivity;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.video.Video;
import com.example.quan.english.fragment.video.VideoPlay;

import java.util.ArrayList;

public class VideoHomeAdapter extends RecyclerView.Adapter<VideoHomeAdapter.ViewHolder> {
    private ArrayList<Video> videos;
    private Context context;
    private ClickListener clickListener;

    public VideoHomeAdapter(ArrayList<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_rcv_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(videos.get(position).getName());
        final String url = "https://img.youtube.com/vi/" + videos.get(position).getPath() + "/0.jpg";
        Glide.with(context).load(url).into(holder.imageView);
        holder.imvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_list_view);
                dialog.setCancelable(true);
                final String title = videos.get(position).getName();
                dialog.setTitle(title);
                String[] strings = {"watch", "Share to Facebook"};
                ListView livMenu = (ListView) dialog.findViewById(R.id.liv_dialog_menu);
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, strings);
                livMenu.setAdapter(arrayAdapter);
                livMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 1:
                                ShareFB shareFB = new ShareFB("https://www.youtube.com/watch?v=" + videos.get(position).getPath(), url, title, context);
                                shareFB.share();
                                break;
                            case 0:
                                Intent i = new Intent(context, VideoPlay.class);
                                i.putExtra(Key.URL_YOUTUBE, videos.get(position).getPath());
                                i.putExtra(Key.NAME_TYPE_VIDEO, videos.get(position).getNameType());
                                i.putExtra(Key.NAME_TYPE_AUDIO, videos.get(position).getName());
                                context.startActivity(i);
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
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView imageView, imvMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_rcv_video);
            imageView = (ImageView) itemView.findViewById(R.id.imv_item_rcv_video);
            imvMenu = (ImageView) itemView.findViewById(R.id.imv_item_rcv_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}