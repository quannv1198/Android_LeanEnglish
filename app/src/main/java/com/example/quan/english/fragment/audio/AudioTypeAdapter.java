package com.example.quan.english.fragment.audio;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quan.english.R;
import com.example.quan.english.fragment.menu.ClickListener;

import java.util.ArrayList;

public class AudioTypeAdapter extends RecyclerView.Adapter<AudioTypeAdapter.ViewHolder> {
    private ArrayList<AudioType> audioTypes;
    private ClickListener clickListener;

    public AudioTypeAdapter(ArrayList<AudioType> audioTypes) {
        this.audioTypes = audioTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_type_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(audioTypes.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return audioTypes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imv_item_type_audio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }
        }
    }
    public void setClickListener(ClickListener clicklistener) {
        this.clickListener = clicklistener;
    }
}
