package com.example.quan.english.fragment.menu.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quan.english.Key;
import com.example.quan.english.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    private ImageView imageView;
    private CircleImageView circleImageView;
    private TextView tvName, tvAge, tvGender;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        imageView = (ImageView) findViewById(R.id.imv_bg_user);
        circleImageView = (CircleImageView) findViewById(R.id.clv_avatar_user);
        tvName = (TextView) findViewById(R.id.tv_name_user);
        tvAge = (TextView) findViewById(R.id.tv_age_user);
        tvGender = (TextView) findViewById(R.id.tv_gender_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Glide.with(UserActivity.this).load("https://graph.facebook.com/" + Key.getUserId() + "/picture?type=large").into(imageView);
        Glide.with(UserActivity.this).load("https://graph.facebook.com/" + Key.getUserId() + "/picture?type=large").into(circleImageView);
        tvGender.setText("Gender " + Key.getUserGender());
        tvName.setText(Key.getUserName());
        tvAge.setText("Age      " + Key.getUserAge());
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