package com.example.quan.english.linking;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quan.english.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

public class LinkingActivity extends Activity implements View.OnClickListener {
    private ArrayList<Linking> linkings;
    private ArrayList<String> intricacy;
    private TextView tvVi, tvQuestion, tvScore, tvTime, tvResult, tvPlay, tvExit, tvExitMain, tvScoreGameOVer, tvBestScore;
    private GridView grvEg;
    private LinkingAdapter linkingAdapter;
    private String result = "";
    private int b;
    private Button btnAgain;
    private LinearLayout lnlGameOver, lnlPlayGame;
    private Handler handler;
    private boolean isRunning;
    private int time;
    private int score = 0;
    private int question = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linking);
        tvVi = (TextView) findViewById(R.id.tv_vi);
        tvExit = (TextView) findViewById(R.id.tv_exit);
        tvExitMain = (TextView) findViewById(R.id.tv_exit_main);
        tvPlay = (TextView) findViewById(R.id.tv_play);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvScore = (TextView) findViewById(R.id.tv_score_linking);
        tvTime = (TextView) findViewById(R.id.tv_time_linking);
        tvScoreGameOVer = (TextView) findViewById(R.id.tv_score_game_over);
        tvBestScore = (TextView) findViewById(R.id.tv_best_score);
        grvEg = (GridView) findViewById(R.id.grv_eg);
        tvResult = (TextView) findViewById(R.id.tv_result);
        btnAgain = (Button) findViewById(R.id.btn_again);
        lnlGameOver = (LinearLayout) findViewById(R.id.lnl_game_over);
        lnlPlayGame = (LinearLayout) findViewById(R.id.lnl_play_game);
        getDataFromAssets();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 12:
                        tvTime.setText("Time: " + msg.arg1);
                        if (msg.arg1 == 0) {
                            gameOver();
                        }
                        break;
                }
            }
        };

        tvExit.setOnClickListener(this);
        tvExitMain.setOnClickListener(this);
        tvPlay.setOnClickListener(this);
        btnAgain.setOnClickListener(this);

        grvEg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!intricacy.get(i).equals("")) {
                    result = result + " " + intricacy.get(i);
                }
                result = result.trim();
                tvResult.setText(result);
                intricacy.remove(i);
                intricacy.add(i, "");
                String[] t = result.split(" ");
                grvEg.setAdapter(linkingAdapter = new LinkingAdapter(getBaseContext(), intricacy));
                if (t.length == intricacy.size()) {
                    if ((tvResult.getText().toString().trim()).equals(linkings.get(b).getEnglish())) {
                        playGame();
                        score++;
                        question++;
                        tvQuestion.setText("Question: " + question);
                        tvScore.setText("Score: " + score);
                        time = 11;
                        tvTime.setText("Time: 11");
                        tvResult.setText("");
                        result = "";
                    } else {
                        gameOver();
                        question = 1;
                        tvQuestion.setText("Question: 1");
                        score = 0;
                        time = 11;
                        tvScore.setText("Score: " + score);
                        tvTime.setText("time: 11");
                    }
                }
            }
        });
    }

    public void getDataFromAssets() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("datalinking.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String data = reader.readLine();
            linkings = new ArrayList<>();
            while (data != null) {
                String[] strings = data.split("_");
                linkings.add(new Linking(strings[0], strings[1]));
                data = reader.readLine();
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playGame() {
        isRunning = true;
        time = 11;
        lnlGameOver.setX(10000);
        intricacy = new ArrayList<>();
        int a = linkings.size();
        b = (int) (Math.random() * a);
        tvVi.setText(linkings.get(b).getVietnamese());
        String[] strings = linkings.get(b).getEnglish().split(" ");
        for (int i = strings.length - 1; i >= 0; i--) {
            intricacy.add(strings[i]);
        }
        Collections.shuffle(intricacy);
        linkingAdapter = new LinkingAdapter(getBaseContext(), intricacy);
        grvEg.setAdapter(linkingAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_again:
                tvResult.setText("");
                result = "";
                time = 11;
                score = 0;
                question = 1;
                tvScore.setText("Score: 0");
                tvQuestion.setText("Question: 1");
                tvTime.setText("Time: 11");
                playGame();
                break;
            case R.id.tv_play:
                lnlPlayGame.setX(10000);
                timeRunning();
                playGame();
                break;
            case R.id.tv_exit:
            case R.id.tv_exit_main:
                finish();
                break;

        }
    }

    private void timeRunning() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    Message msg = new Message();
                    msg.what = 12;
                    msg.arg1 = time--;
                    handler.sendMessage(msg);
                    if (time <= 0) {
                        time = 0;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void gameOver() {
        tvScoreGameOVer.setText("Your Score: " + score);
        lnlGameOver.setX(0);
    }
}