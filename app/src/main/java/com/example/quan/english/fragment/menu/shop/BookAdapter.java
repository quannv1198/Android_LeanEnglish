package com.example.quan.english.fragment.menu.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quan.english.R;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context context;
    private String link, name, about;

    public BookAdapter(Context context, String link, String name, String about) {
        this.context = context;
        this.link = link;
        this.name = name;
        this.about = about;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_title_rcv_book, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.tvTitle.setText(name);
                Glide.with(context).load(link).into(holder.imvBook);
                holder.tvAboutBook.setText(about);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvBook;
        private TextView tvTitle, tvAboutBook;

        public ViewHolder(View itemView) {
            super(itemView);
            imvBook = (ImageView) itemView.findViewById(R.id.imv_book);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvAboutBook = (TextView) itemView.findViewById(R.id.tv_about_book);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 1;
        }
        return 0;
    }
}
