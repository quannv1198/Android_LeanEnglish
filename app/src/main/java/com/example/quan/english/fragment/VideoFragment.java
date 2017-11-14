package com.example.quan.english.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ServerApi;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.video.Video;
import com.example.quan.english.fragment.video.TypesVideoAdapter;
import com.example.quan.english.fragment.video.VideoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private ArrayList<Video> videos;
    private TypesVideoAdapter videoAdapter;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_types_video);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_style_video);
        refreshLayout.setColorSchemeColors(Color.BLUE);
        getDataForList();
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    private void getDataForList() {
        videos = new ArrayList<>();
        ServerApi api = new ServerApi("http://it2k.comli.com/video/data.json");
        api.getDataAsync(new ServerApi.OnDataLoadListener() {
            @Override
            public void onLoaded(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("types");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectNameVideo = jsonArray.getJSONObject(i);
                        JSONArray jsonArrayVideo = jsonObject.getJSONArray(jsonObjectNameVideo.getString("name"));
                        JSONObject jsonObjectVideo = jsonArrayVideo.getJSONObject(0);
                        String url = jsonObjectVideo.getString("url");
                        videos.add(new Video(jsonObjectNameVideo.getString("name"), url));
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoAdapter = new TypesVideoAdapter(getContext(), videos);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(videoAdapter);
                            refreshLayout.setRefreshing(false);
                            videoAdapter.setClickListener(new ClickListener() {
                                @Override
                                public void itemClicked(View view, int position) {
                                    Intent intent = new Intent(getContext(), VideoActivity.class);
                                    intent.putExtra(Key.NAME_TYPE_VIDEO, videos.get(position).getName());
                                    startActivity(intent);
                                }
                            });

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void onRefresh() {
        getDataForList();
    }

}
