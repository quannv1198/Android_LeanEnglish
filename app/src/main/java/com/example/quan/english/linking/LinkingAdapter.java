package com.example.quan.english.linking;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quan.english.R;

import java.util.ArrayList;


public class LinkingAdapter extends BaseAdapter {
    ArrayList<String> strings;
    LayoutInflater inflater;

    public LinkingAdapter(Context context, ArrayList strings) {
        this.strings = strings;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (strings.get(i).equals("")) {
            view = inflater.inflate(R.layout.item_empty, viewGroup, false);
            TextView textView = (TextView) view.findViewById(R.id.tv_intricacy_empty);
            textView.setText("");
        } else {
            view = inflater.inflate(R.layout.item_linking, viewGroup, false);
            TextView textView = (TextView) view.findViewById(R.id.tv_intricacy);
            textView.setText(strings.get(i));
        }
        return view;
    }
}