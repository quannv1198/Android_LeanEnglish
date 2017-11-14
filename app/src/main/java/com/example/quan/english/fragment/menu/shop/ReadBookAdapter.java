package com.example.quan.english.fragment.menu.shop;

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

public class ReadBookAdapter extends BaseAdapter {
    private ArrayList<String> strings;
    private LayoutInflater inflater;
    private Context context;

    public ReadBookAdapter(Context context, ArrayList<String> strings) {
        this.strings = strings;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.style_item_imv, parent, false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imv_item_liv);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_page_book);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText("Page " + (position + 1));
        Glide.with(context).load(strings.get(position)).into(viewHolder.imageView);
        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
