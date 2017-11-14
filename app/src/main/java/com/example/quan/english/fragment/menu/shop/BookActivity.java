package com.example.quan.english.fragment.menu.shop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quan.english.Key;
import com.example.quan.english.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.io.IOException;

public class BookActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout, lnlBuy;
    private String linkRead;
    private String name;
    private Spinner spnCountry, spn2;
    private String[] country = {"Ha Noi", "TP.Ho Chi Minh"};
    private String[] tpHCM = {"Quận Thủ Đức", "Quận Gò Vấp", "Quận Bình Thạnh", "Quận Tân Bình", "Quận Tân Phú", "Quận Phú Nhuận", "Quận Bình Tân", "Huyện Củ Chi", "Huyện Hóc Môn", "Huyện Nhà Bè"};
    private String[] haNoi = {"Quận Ba Đình", "Quận Hoàn Kiếm", "Quận Hai Bà Trưng", "Quận Đống Đa", "Quận Tây Hồ", "Quận Cầu Giấy", "Quận Thanh Xuân", "Quận Hoàng Mai", "Quận Long Biên", "Huyện Từ Liêm", "Quận Hà Đông"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        toolbar = (Toolbar) findViewById(R.id.toolbar_book);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_book);
        linearLayout = (LinearLayout) findViewById(R.id.lnl_read);
        lnlBuy = (LinearLayout) findViewById(R.id.lnl_buy_book);
        lnlBuy.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        name = getIntent().getStringExtra(Key.NAME_BOOK);
        final String link = getIntent().getStringExtra(Key.LINK_BOOK);
        linkRead = link.substring(0, link.indexOf("?"));
        linkRead = linkRead.substring(linkRead.lastIndexOf("p") + 1, linkRead.lastIndexOf("."));
        linkRead = "https://vcdn.tikicdn.com/media/bookreader/" + linkRead + "/files/OEBPS/Text/index.html";
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
        linearLayout.setOnClickListener(this);

        Thread thread = new Thread(new Runnable() {
            String linkImage;
            String nameBook, aboutBook;

            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(link).get();
                    Elements element = document.select("div.magiczoom");
                    Elements element2 = document.select("div.item-box");
                    aboutBook = document.select("div.product-content-detail").text();
                    aboutBook = aboutBook.replaceAll("\\. ", "\r\n\r\n");
                    nameBook = element2.select("h1").text();
                    linkImage = element.select("a img").get(1).attr("src");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookAdapter bookAdapter = new BookAdapter(getBaseContext(), linkImage, nameBook, aboutBook);
                        recyclerView.setAdapter(bookAdapter);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnl_read:
                Intent intent = new Intent(getBaseContext(), ReadBookActivity.class);
                intent.putExtra("linkRead", linkRead);
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.lnl_buy_book:
                final Dialog dialog = new Dialog(BookActivity.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_buy_book);
                spnCountry = (Spinner) dialog.findViewById(R.id.spn_country);
                spn2 = (Spinner) dialog.findViewById(R.id.spn_2);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, country);
                spnCountry.setAdapter(arrayAdapter);
                spnCountry.setOnItemSelectedListener(this);
                TextView tvBuy;
                tvBuy = (TextView) dialog.findViewById(R.id.tv_buy);
                tvBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getBaseContext(),"success",Toast.LENGTH_SHORT).show();
                       dialog.cancel();
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter arrayAdapter;
        if (parent.getItemAtPosition(position).toString().equals("Ha Noi")) {
            arrayAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, haNoi);
            spn2.setAdapter(arrayAdapter);
        } else {
            arrayAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, tpHCM);
            spn2.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}