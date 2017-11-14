package com.example.quan.english.fragment.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quan.english.Key;
import com.example.quan.english.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<Menu> menus;
    private ClickListener clicklistener = null;
    private Context context;

    public MenuAdapter(ArrayList menus, Context context) {
        this.menus = menus;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView, tvUser;
        private ImageView imageView;
        private CircleImageView circleImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_menu);
            tvUser = (TextView) itemView.findViewById(R.id.tv_item_menu_user);
            imageView = (ImageView) itemView.findViewById(R.id.imv_item_menu);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.circleView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clicklistener != null) {
                clicklistener.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != 0) {
            holder.textView.setText(menus.get(position - 1).getName());
            holder.imageView.setImageResource(menus.get(position - 1).getId());
        } else {
            holder.tvUser.setText(Key.getUserName());
            Glide.with(context).load("https://graph.facebook.com/" + Key.getUserId() + "/picture?type=large").into(holder.circleImageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_menu_user, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_menu, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return menus.size() + 1;
    }
}
