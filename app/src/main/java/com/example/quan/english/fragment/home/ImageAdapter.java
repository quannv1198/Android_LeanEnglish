package com.example.quan.english.fragment.home;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.quan.english.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private ArrayList<String> strings;

    public ImageAdapter(ArrayList<String> strings) {
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.style_item_vpg_imv,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.imv_item_vpg_home);
        Glide.with(container.getContext()).load(strings.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
