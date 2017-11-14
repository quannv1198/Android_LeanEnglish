package com.example.quan.english.fragment.video;


import android.app.Dialog;
import android.content.Context;
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
import com.example.quan.english.R;
import com.example.quan.english.ShareFB;
import com.example.quan.english.fragment.menu.ClickListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TypesVideoAdapter extends RecyclerView.Adapter<TypesVideoAdapter.ViewHolder> {
    private ArrayList<Video> videos;
    private Context context;
    private ClickListener clickListener = null;

    public TypesVideoAdapter(Context context, ArrayList<Video> videos) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_types_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String url = "https://img.youtube.com/vi/" + videos.get(position).getPath() + "/0.jpg";
        Glide.with(context).load(url).into(holder.imvTypeVideo);
        Glide.with(context).load(url).into(holder.imvVideo);
        holder.tvTypeVideo.setText(videos.get(position).getName());
        holder.imvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_list_view);
                dialog.setCancelable(true);
                final String title = videos.get(position).getName();
                dialog.setTitle(title);
                String[] strings = {"Share to Facebook"};
                ListView livMenu = (ListView) dialog.findViewById(R.id.liv_dialog_menu);
                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, strings);
                livMenu.setAdapter(arrayAdapter);
                livMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                ShareFB shareFB = new ShareFB("https://www.youtube.com/watch?v=" + videos.get(position).getPath(), url, title, context);
                                shareFB.share();
                                dialog.cancel();
                                break;
                        }
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
        TextView tvTypeVideo;
        ImageView imvTypeVideo, imvMenu;
        CircleImageView imvVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            imvMenu = (ImageView) itemView.findViewById(R.id.imv_item_menu_video);
            imvVideo = (CircleImageView) itemView.findViewById(R.id.imv_circle_video);
            tvTypeVideo = (TextView) itemView.findViewById(R.id.tv_name_item_type_video);
            imvTypeVideo = (ImageView) itemView.findViewById(R.id.imv_item_type_video);
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