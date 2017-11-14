package com.example.quan.english.fragment.audio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.quan.english.R;

import java.util.ArrayList;

public class AudioAdapter extends BaseAdapter {
    private ArrayList<String> strings;
    private LayoutInflater inflater;

    public AudioAdapter(Context context, ArrayList strings) {
        this.strings = strings;
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
            convertView = inflater.inflate(R.layout.style_item_audio, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tv_name_item_audio);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNameAudio.setText(getItem(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvNameAudio;
    }
}
