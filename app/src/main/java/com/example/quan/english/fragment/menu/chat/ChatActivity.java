package com.example.quan.english.fragment.menu.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quan.english.R;
import com.example.quan.english.ServerApi;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imvSend;
    private EditText edtMessageContent;
    private ArrayList<Chat> chatContent;
    private ChatAdapter chatAdapter;
    private RecyclerView rcvChat;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        imvSend = (ImageView) findViewById(R.id.imv_send);
        edtMessageContent = (EditText) findViewById(R.id.edt_content_messenger);
        rcvChat = (RecyclerView) findViewById(R.id.rcv_chat);
        toolbar = (Toolbar)findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        rcvChat.setLayoutManager(layoutManager);
        chatContent = new ArrayList<>();
        imvSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_send:
                String text = edtMessageContent.getText().toString();
                text = text.trim();
                if (text.equals("")) {
                    break;
                }
                chatContent.add(new Chat(true, text));
                chatAdapter = new ChatAdapter(getBaseContext(),chatContent);
                rcvChat.setAdapter(chatAdapter);
                String link = "http://trunghi-tienich.rhcloud.com/api.php?text=" + text;
                link = link.trim();
                link = link.replaceAll(" ", "%20");
                ServerApi api = new ServerApi(link);
                api.getDataAsync(new ServerApi.OnDataLoadListener() {
                    @Override
                    public void onLoaded(String data) {
                        if (data.equals("Error")) {
                            chatContent.add(new Chat(false, "xin lỗi tôi không hiểu!"));
                        } else {
                            if (!data.isEmpty()) {
                                chatContent.add(new Chat(false, data));
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ChatAdapter chatAdapter = new ChatAdapter(getBaseContext(),chatContent);
                                rcvChat.setAdapter(chatAdapter);
                            }
                        });
                    }

                    @Override
                    public void onError() {

                    }
                });
                edtMessageContent.setText("");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}