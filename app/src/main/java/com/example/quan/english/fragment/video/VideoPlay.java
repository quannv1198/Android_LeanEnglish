package com.example.quan.english.fragment.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ServerApi;
import com.example.quan.english.ShareFB;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoPlay extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    private final String API_KEY_YT = "AIzaSyBCDgZtEvwn_fZFWzDVg7-9QVxPBACk0BE";
    private String urlYoutube, nameTypeVideo, nameVideo;
    private ListView livPlayVideo;
    private ArrayList<Video> videos;
    private VideoAdapter videoAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        final YouTubePlayerSupportFragment frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.ytpv);
        frag.initialize(API_KEY_YT, this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_play_video);
        final Intent i = getIntent();
        urlYoutube = i.getStringExtra(Key.URL_YOUTUBE);
        nameTypeVideo = i.getStringExtra(Key.NAME_TYPE_VIDEO);
        nameVideo = i.getStringExtra(Key.NAME_TYPE_AUDIO);
        livPlayVideo = (ListView) findViewById(R.id.liv_related_video);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nameVideo);
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
                            livPlayVideo.setAdapter(videoAdapter);
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
        livPlayVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), VideoPlay.class);
                intent.putExtra(Key.NAME_TYPE_VIDEO, nameTypeVideo);
                intent.putExtra(Key.URL_YOUTUBE, videos.get(position).getPath());
                intent.putExtra(Key.NAME_TYPE_AUDIO, videos.get(position).getName());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer ytp, boolean b) {
        if (!b) {
            ytp.cueVideo(urlYoutube);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, 1).show();
        } else {
            String error = String.format("Error initializing YouTube player", result.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_share, menu);
        MenuItem imageView = menu.findItem(R.id.action_share);
        imageView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ShareFB shareFB = new ShareFB("https://www.youtube.com/watch?v=" + urlYoutube, "https://img.youtube.com/vi/" + urlYoutube + "/0.jpg", nameVideo, VideoPlay.this);
                shareFB.share();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}