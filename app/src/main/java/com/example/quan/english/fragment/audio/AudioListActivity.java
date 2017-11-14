package com.example.quan.english.fragment.audio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.quan.english.Key;
import com.example.quan.english.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class AudioListActivity extends AppCompatActivity {
    private ListView livAudio;
    private AudioAdapter audioAdapter;
    private Toolbar toolbar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        Intent intent = getIntent();
        livAudio = (ListView) findViewById(R.id.liv_audio);
        toolbar = (Toolbar) findViewById(R.id.toolbar_list_audio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final String a = intent.getStringExtra(Key.NAME_TYPE_AUDIO);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> strings = null;
                try {
                    Document document = Jsoup.connect("http://www.listenaminute.com/").get();
                    Elements elements = document.select("tbody tr td ul li font a");
                    strings = new ArrayList<>();
                    for (int j = 0; j < elements.size(); j++) {
                        Element element = elements.get(j);
                        String link = element.attr("href");
                        link = link.substring(link.lastIndexOf("/") + 1);
                        link = link.replaceAll(".html", "");
                        link = link.replaceAll("_", " ");
                        if (String.valueOf(link.charAt(0)).equals(a)) {
                            strings.add(link);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final ArrayList<String> finalStrings = strings;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        audioAdapter = new AudioAdapter(getBaseContext(), finalStrings);
                        livAudio.setAdapter(audioAdapter);
                        mProgressDialog.cancel();
                    }
                });
            }
        });
        thread.start();
        livAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = audioAdapter.getItem(position);
                Intent intent = new Intent(getBaseContext(), AudioPlayActivity.class);
                intent.putExtra(Key.LINK_AUDIO, link);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}