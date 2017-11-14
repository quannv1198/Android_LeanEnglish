package com.example.quan.english.fragment.audio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.ShareFB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AudioPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String link;
    private TextView textView, tvTime;
    private SeekBar seekBar;
    private Handler handler;
    private Toolbar toolbar;
    private int stare = 0;
    private ImageView imvStare;
    private ProgressDialog mpProgressDialog;
    private boolean isSong = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);
        textView = (TextView) findViewById(R.id.tv_data_audio);
        tvTime = (TextView) findViewById(R.id.tv_time_audio);
        seekBar = (SeekBar) findViewById(R.id.seekBar_audio);
        toolbar = (Toolbar) findViewById(R.id.toolbar_play_audio);
        imvStare = (ImageView) findViewById(R.id.imv_stare);
        imvStare.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        link = intent.getStringExtra(Key.LINK_AUDIO);
        getSupportActionBar().setTitle(link);
        link = link.replaceAll(" ", "_");
        link = "http://www.listenaminute.com/" + link.charAt(0) + "/" + link + ".html";
        String linkMp3 = link.replaceAll(".html", ".mp3");
        mpProgressDialog = new ProgressDialog(this);
        mpProgressDialog.setMessage("Loading...");
        mpProgressDialog.setCancelable(false);
        mpProgressDialog.show();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 01:
                        String a = mToS(msg.arg1);
                        int b = (msg.arg1 * 100) / msg.arg2;
                        tvTime.setText(a);
                        seekBar.setProgress(b);
                }
            }
        };
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(link).get();
                    final Elements elements = document.select("body p");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(elements.get(3).text());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            mediaPlayer.setDataSource(linkMp3);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {
                    isSong = true;
                    mpProgressDialog.cancel();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }

    @Override
    protected void onStop() {
        mediaPlayer.stop();
        super.onStop();
    }

    private String mToS(int time) {
        int a = time / 1000;
        int b = a / 60;
        int c = a % 60;
        return b + ":" + c;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_stare:
                switch (stare) {
                    case 0:
                        if (isSong) {
                            imvStare.setImageResource(R.drawable.ic_action_pause);
                            mediaPlayer.start();
                            Thread threadPlay = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (mediaPlayer.isPlaying()) {
                                        Message msg = new Message();
                                        msg.what = 01;
                                        msg.arg1 = mediaPlayer.getCurrentPosition();
                                        msg.arg2 = mediaPlayer.getDuration();
                                        handler.sendMessage(msg);
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            threadPlay.start();
                            stare = 1;
                        }
                        break;
                    case 1:
                        imvStare.setImageResource(R.drawable.ic_action_play);
                        mediaPlayer.pause();
                        stare = 0;
                        break;
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_share, menu);
        MenuItem imageView = menu.findItem(R.id.action_share);
        imageView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String linkShare = link.replaceAll(".mp3", ".html");
                imvStare.setImageResource(R.drawable.ic_action_play);
                mediaPlayer.pause();
                stare = 0;
                ShareFB shareFB = new ShareFB(linkShare, "http://i.imgur.com/SMkVfgc.png", "", AudioPlayActivity.this);
                shareFB.share();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}