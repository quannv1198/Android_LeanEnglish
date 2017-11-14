package com.example.quan.english.fragment.video;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ArrayList<Video> videos;
    private VideoAdapter videoAdapter;
    private ListView livVideo;
    private Toolbar toolbar;
    private ProgressDialog mProgressDialog;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        final Intent intent = getIntent();
        livVideo = (ListView) findViewById(R.id.liv_video);
        toolbar = (Toolbar) findViewById(R.id.toolbar_video);
        final String nameTypeVideo = intent.getStringExtra(Key.NAME_TYPE_VIDEO);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nameTypeVideo);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        videos = new ArrayList<>();
        ServerApi api = new ServerApi("http://it2k.comli.com/video/data.json");
        api.getDataAsync(new ServerApi.OnDataLoadListener() {
            @Override
            public void onLoaded(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray(nameTypeVideo);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObjectVideo = jsonArray.getJSONObject(i);
                        String name = jsonObjectVideo.getString("name");
                        String url = jsonObjectVideo.getString("url");
                        videos.add(new Video(name, url));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            videoAdapter = new VideoAdapter(videos, getBaseContext());
                            livVideo.setAdapter(videoAdapter);
                            mProgressDialog.cancel();
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
        livVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), VideoPlay.class);
                i.putExtra(Key.URL_YOUTUBE, videos.get(position).getPath());
                i.putExtra(Key.NAME_TYPE_VIDEO, nameTypeVideo);
                i.putExtra(Key.NAME_TYPE_AUDIO, videos.get(position).getName());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_video, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            videoAdapter = new VideoAdapter(videos, getBaseContext());
            livVideo.setAdapter(videoAdapter);
            return false;
        } else {
            ArrayList<Video> searchList = new ArrayList<>();
            for (int i = 0; i < videos.size(); i++) {
                if (videos.get(i).getName().toLowerCase().contains(newText.toLowerCase())) {
                    searchList.add(videos.get(i));
                }
            }
            videoAdapter = new VideoAdapter(searchList, getBaseContext());
            livVideo.setAdapter(videoAdapter);
        }
        return false;
    }
}