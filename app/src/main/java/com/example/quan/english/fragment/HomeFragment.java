package com.example.quan.english.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.fragment.home.HomeAdapter;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.menu.shop.ShopActivity;


public class HomeFragment extends Fragment implements ClickListener {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_home);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        if (isNetworkConnected()) {
            HomeAdapter homeAdapter = new HomeAdapter(getContext(), getActivity());
            homeAdapter.setClickListener(this);
            recyclerView.setAdapter(homeAdapter);
        }
        return view;
    }

    @Override
    public void itemClicked(View view, int position) {
        switch (position) {
            case 1:
                Intent intent1 = new Intent();
                intent1.setAction(Key.SELECT_VIEWPAGER + 1);
                getContext().sendBroadcast(intent1);
                break;
            case 2:
                Intent intent2 = new Intent();
                intent2.setAction(Key.SELECT_VIEWPAGER + 2);
                getContext().sendBroadcast(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(getContext(), ShopActivity.class);
                startActivity(intent3);
                break;
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }


}
