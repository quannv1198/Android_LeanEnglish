package com.example.quan.english.fragment.menu.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.quan.english.Key;
import com.example.quan.english.R;
import com.example.quan.english.fragment.menu.ClickListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private ArrayList<Book> books;
    private ShopAdapter shopAdapter;
    private Toolbar toolbar;
    private ProgressDialog mProgressDialog;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        toolbar = (Toolbar) findViewById(R.id.toolbar_shop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shop book");
        recyclerView = (RecyclerView) findViewById(R.id.rcv_shop);
        GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                books = new ArrayList<>();
                try {
                    Document document;
                    document = Jsoup.connect("https://tiki.vn/sach-hoc-tieng-anh/c1856?src=tree&page=2").get();
                    Elements elements = document.select("div.product-item a");
                    for (int i = 0; i < elements.size(); i++) {
                        String link = elements.get(i).attr("href");
                        Elements elements3 = elements.get(i).select("p.price-sale");
                        String price = elements3.text();
                        Elements elements2 = elements.get(i).select("span.title");
                        String title = elements2.text();
                        Elements elements1 = elements.get(i).select("span img");
                        String linkImage;
                        if (elements1.size() == 1) {
                            linkImage = elements1.attr("src");
                        } else {
                            linkImage = elements1.get(1).attr("src");
                        }
                        books.add(new Book(link, title, price, linkImage));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shopAdapter = new ShopAdapter(getBaseContext(), books);
                        shopAdapter.setClickListener(new ClickListener() {
                            @Override
                            public void itemClicked(View view, int position) {
                                Intent intent = new Intent(getBaseContext(), BookActivity.class);
                                intent.putExtra(Key.LINK_BOOK, books.get(position).getLink());
                                intent.putExtra(Key.NAME_BOOK, books.get(position).getName());
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(shopAdapter);
                        mProgressDialog.cancel();
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_video, menu);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            shopAdapter = new ShopAdapter(getBaseContext(), books);
            shopAdapter.setClickListener(new ClickListener() {
                @Override
                public void itemClicked(View view, int position) {
                    Intent intent = new Intent(getBaseContext(), BookActivity.class);
                    intent.putExtra(Key.LINK_BOOK, books.get(position).getLink());
                    intent.putExtra(Key.NAME_BOOK, books.get(position).getName());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(shopAdapter);
            return false;
        } else {
            ArrayList<Book> searchList = new ArrayList<>();
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getName().toLowerCase().contains(newText.toLowerCase())) {
                    searchList.add(books.get(i));
                }
            }
            shopAdapter = new ShopAdapter(getBaseContext(), searchList);
            shopAdapter.setClickListener(new ClickListener() {
                @Override
                public void itemClicked(View view, int position) {
                    Intent intent = new Intent(getBaseContext(), BookActivity.class);
                    intent.putExtra(Key.LINK_BOOK, books.get(position).getLink());
                    intent.putExtra(Key.NAME_BOOK, books.get(position).getName());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(shopAdapter);
        }
        return false;
    }
}
