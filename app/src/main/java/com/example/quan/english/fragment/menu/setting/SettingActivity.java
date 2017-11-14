package com.example.quan.english.fragment.menu.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.quan.english.LoginActivity;
import com.example.quan.english.R;
import com.example.quan.english.fragment.menu.ClickListener;
import com.example.quan.english.fragment.menu.Menu;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import java.io.File;
import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements ClickListener {
    private Toolbar toolbar;
    private RecyclerView rcvSetting;
    private ArrayList<Menu> setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        rcvSetting = (RecyclerView) findViewById(R.id.rcv_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setting = new ArrayList<>();
        setting.add(new Menu("LogOut", R.drawable.ic_action_logout));
        SettingAdapter settingAdapter = new SettingAdapter(setting);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        settingAdapter.setClickListener(this);
        rcvSetting.setLayoutManager(layoutManager);
        rcvSetting.setHasFixedSize(true);
        rcvSetting.setAdapter(settingAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClicked(View view, int position) {
        switch (position) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("Do you want to logout!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                disconnectFromFacebook();
                                File file = new File(getCacheDir(), "Remember");
                                if (file.exists()) {
                                    file.delete();
                                }
                                Intent intentClose = new Intent();
                                intentClose.setAction("CloseHomeActivity");
                                sendBroadcast(intentClose);

                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return;
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
            }
        }).executeAsync();
    }
}