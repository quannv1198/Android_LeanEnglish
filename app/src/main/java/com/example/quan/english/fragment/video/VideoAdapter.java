package com.example.quan.english.fragment.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quan.english.R;

import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter {
    private ArrayList<Video> videos;
    private LayoutInflater inflater;
    private Context context;

    public VideoAdapter(ArrayList typesVideos, Context context) {
        this.videos = typesVideos;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Video getItem(int position) {
        return videos.get(position);
    }

    @Override

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.style_item_video, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvNameVideo = (TextView) convertView.findViewById(R.id.tv_name_item_video);
            viewHolder.imvVideo = (ImageView) convertView.findViewById(R.id.imv_item_video);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNameVideo.setText(videos.get(position).getName());
        String url = "https://img.youtube.com/vi/" + videos.get(position).getPath() + "/0.jpg";
        Glide.with(context).load(url).into(viewHolder.imvVideo);
        return convertView;
    }

    private class ViewHolder {
        private ImageView imvVideo;
        private TextView tvNameVideo;
    }
}