package com.example.quan.english.fragment.menu.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.quan.english.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ReadBookActivity extends AppCompatActivity {
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        listView = (ListView) findViewById(R.id.liv_read_book);
        toolbar = (Toolbar) findViewById(R.id.toolbar_read_book);
        String title = getIntent().getStringExtra("name");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String link = getIntent().getStringExtra("linkRead");
        final String linkImage = link.replace("Text/index.html", "");
        Thread thread = new Thread(new Runnable() {
            ArrayList<String> strings = new ArrayList<>();

            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(link).get();
                    Elements element = document.select("h1 img");
                    for (int i = 0; i < element.size(); i++) {
                        String linkImg = element.get(i).attr("src");
                        linkImg = linkImg.replace("../", "");
                        linkImg = linkImage + linkImg;
                        strings.add(linkImg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final ArrayList<String> link = strings;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ReadBookAdapter readBookAdapter = new ReadBookAdapter(getBaseContext(), link);
                        listView.setAdapter(readBookAdapter);
                    }
                });
            }
        });
        thread.start();
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