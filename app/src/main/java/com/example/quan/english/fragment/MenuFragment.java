package com.example.quan.english.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quan.english.R;
import com.example.quan.english.fragment.menu.chat.ChatActivity;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.menu.Menu;
import com.example.quan.english.fragment.menu.MenuAdapter;
import com.example.quan.english.fragment.menu.qr.CodeQRActivity;
import com.example.quan.english.fragment.menu.shop.ShopActivity;
import com.example.quan.english.fragment.menu.user.UserActivity;
import com.example.quan.english.linking.LinkingActivity;

import java.util.ArrayList;

public class MenuFragment extends Fragment implements ClickListener {
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private ArrayList<Menu> menus = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_menu);
        addMenu();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        menuAdapter = new MenuAdapter(menus, getContext());
        recyclerView.setAdapter(menuAdapter);
        menuAdapter.setClickListener(this);
        return view;
    }

    @Override
    public void itemClicked(View view, int position) {
        switch (position) {
            case 0:
                Intent intent0 = new Intent(getContext(), UserActivity.class);
                startActivity(intent0);
                break;
            case 1:
                Toast.makeText(getContext(), "Tính năng đang được xây dựng", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Intent intent2 = new Intent(getContext(), ChatActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(getContext(), CodeQRActivity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(getContext(), ShopActivity.class);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(getContext(), LinkingActivity.class);
                startActivity(intent5);
                break;
            case 6:
                Uri uri = Uri.parse("https://www.youtube.com/channel/UC9lwM9eNa5yncs9W7ZTZHXQ");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setCancelable(true)
                        .setTitle("Information")
                        .setMessage(" English We Can\n\r\n\rBeta version 1.0\n\r\n\rlaptrinhvient3h@gmail.com\n\r\n\rEdit by QuanNV_IT")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

    }

    private void addMenu() {
        menus.add(new Menu("Tìm quanh đây", R.drawable.ic_action_around));
        menus.add(new Menu("Phòng trò chuyện", R.drawable.ic_action_chat));
        menus.add(new Menu("Quét mã QR", R.drawable.ic_action_code_qr));
        menus.add(new Menu("Shop", R.drawable.ic_action_shop));
        menus.add(new Menu("Game", R.drawable.ic_action_game));
        menus.add(new Menu("Channel", R.drawable.ic_action_channel));
        menus.add(new Menu("Information", R.drawable.ic_action_about));
    }
}