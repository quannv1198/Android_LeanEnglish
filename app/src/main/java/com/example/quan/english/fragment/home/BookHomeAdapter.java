package com.example.quan.english.fragment.home;

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
import com.example.quan.english.fragment.menu.shop.Book;

import java.util.ArrayList;

public class BookHomeAdapter extends RecyclerView.Adapter<BookHomeAdapter.ViewHolder> {
    private ArrayList<Book> books;
    private Context context;
    private ClickListener clickListener;

    public BookHomeAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_item_rcv_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(books.get(position).getLinkImage()).into(holder.imvBook);
        holder.tvNameBook.setText(books.get(position).getName());
        holder.tvPrice.setText(books.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvPrice, tvNameBook;
        private ImageView imvBook;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_item_rcv_book_price);
            tvNameBook = (TextView) itemView.findViewById(R.id.tv_name_item_rcv_book);
            imvBook = (ImageView) itemView.findViewById(R.id.imv_item_rcv_book);
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