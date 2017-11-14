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
import com.example.quan.english.fragment.menu.ClickListener;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private ArrayList<Book> books;
    private Context context;
    private ClickListener clickListener;

    public ShopAdapter(Context context, ArrayList<Book> books) {
        this.books = books;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(books.get(position).getName());
        holder.tvPrice.setText(books.get(position).getPrice());
        Glide.with(context).load(books.get(position).getLinkImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView tvName, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imv_item_book);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_item_book_price);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_item_book);
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
