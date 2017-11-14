package com.example.quan.english.fragment.menu.setting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quan.english.R;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.menu.Menu;

import java.util.ArrayList;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private ClickListener clickListener = null;
    private ArrayList<Menu> settings;

    public SettingAdapter(ArrayList<Menu> settings) {
        this.settings = settings;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_menu);
            imageView = (ImageView) itemView.findViewById(R.id.imv_item_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getAdapterPosition());
            }

        }
    }

    void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(settings.get(position).getName());
        holder.imageView.setImageResource(settings.get(position).getId());
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }
}
