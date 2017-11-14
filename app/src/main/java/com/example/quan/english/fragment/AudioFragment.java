package com.example.quan.english.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.fragment.audio.AudioListActivity;
import com.example.quan.english.fragment.audio.AudioType;
import com.example.quan.english.fragment.audio.AudioTypeAdapter;
import com.example.quan.english.fragment.menu.ClickListener;

import java.util.ArrayList;

public class AudioFragment extends Fragment implements ClickListener {
    private RecyclerView recyclerView;
    private AudioTypeAdapter audioTypeAdapter;
    private ArrayList<AudioType> audioTypes = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_type_audio);
        setData();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        audioTypeAdapter = new AudioTypeAdapter(audioTypes);
        audioTypeAdapter.setClickListener(this);
        recyclerView.setAdapter(audioTypeAdapter);
        return view;
    }

    private void setData() {
        audioTypes.add(new AudioType("a", R.drawable.alphabet_a));
        audioTypes.add(new AudioType("b", R.drawable.alphabet_b));
        audioTypes.add(new AudioType("c", R.drawable.alphabet_c));
        audioTypes.add(new AudioType("d", R.drawable.alphabet_d));
        audioTypes.add(new AudioType("e", R.drawable.alphabet_e));
        audioTypes.add(new AudioType("f", R.drawable.alphabet_f));
        audioTypes.add(new AudioType("g", R.drawable.alphabet_g));
        audioTypes.add(new AudioType("h", R.drawable.alphabet_h));
        audioTypes.add(new AudioType("i", R.drawable.alphabet_i));
        audioTypes.add(new AudioType("j", R.drawable.alphabet_j));
        audioTypes.add(new AudioType("k", R.drawable.alphabet_k));
        audioTypes.add(new AudioType("l", R.drawable.alphabet_l));
        audioTypes.add(new AudioType("m", R.drawable.alphabet_m));
        audioTypes.add(new AudioType("n", R.drawable.alphabet_n));
        audioTypes.add(new AudioType("o", R.drawable.alphabet_o));
        audioTypes.add(new AudioType("p", R.drawable.alphabet_p));
        audioTypes.add(new AudioType("q", R.drawable.alphabet_q));
        audioTypes.add(new AudioType("r", R.drawable.alphabet_r));
        audioTypes.add(new AudioType("s", R.drawable.alphabet_s));
        audioTypes.add(new AudioType("t", R.drawable.alphabet_t));
        audioTypes.add(new AudioType("u", R.drawable.alphabet_u));
        audioTypes.add(new AudioType("v", R.drawable.alphabet_v));
        audioTypes.add(new AudioType("w", R.drawable.alphabet_w));
        audioTypes.add(new AudioType("x", R.drawable.alphabet_x));
        audioTypes.add(new AudioType("y", R.drawable.alphabet_y));
        audioTypes.add(new AudioType("z", R.drawable.alphabet_z));
    }

    @Override
    public void itemClicked(View view, int position) {
        if (isNetworkConnected()){
        String a = audioTypes.get(position).getName();
        Intent intent = new Intent(getContext(), AudioListActivity.class);
        intent.putExtra(Key.NAME_TYPE_AUDIO, a);
        startActivity(intent);}
        else {
            Toast.makeText(getContext(),"Not Internet",Toast.LENGTH_SHORT).show();
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