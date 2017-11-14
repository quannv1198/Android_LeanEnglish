package com.example.quan.english;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quan.english.fragment.FragmentAdapter;
import com.example.quan.english.fragment.home.notify.NotifyActivity;
import com.example.quan.english.fragment.menu.setting.SettingActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private ImageView imvToolBarMain;
    private TextView tvToolBarMain;
    private CloseActivityReceiver closeActivityReceiver = new CloseActivityReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (!isUser()) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        registerCloseActivityReceiver();
        tabLayout = (TabLayout) findViewById(R.id.tlo_home);
        viewPager = (ViewPager) findViewById(R.id.vpg_home);
        tvToolBarMain = (TextView) findViewById(R.id.tv_toolbar_main);
        imvToolBarMain = (ImageView) findViewById(R.id.imv_toolbar_main);
        FragmentManager manager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(manager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_video);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_audio);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_action_menu);
        imvToolBarMain.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                tvToolBarMain.setText("Home");
                imvToolBarMain.setImageResource(R.drawable.ic_action_notify);
                break;
            case 1:
                tvToolBarMain.setText("Video");
                imvToolBarMain.setImageResource(R.drawable.custom_bg);
                break;
            case 2:
                tvToolBarMain.setText("Audio");
                imvToolBarMain.setImageResource(R.drawable.custom_bg);
                break;
            case 3:
                tvToolBarMain.setText("Menu");
                imvToolBarMain.setImageResource(R.drawable.ic_action_setting);
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_toolbar_main:
                switch (viewPager.getCurrentItem()) {
                    case 0:
                        Intent intent0 = new Intent(getBaseContext(), NotifyActivity.class);
                        startActivity(intent0);
                        break;
                    case 3:
                        Intent intent = new Intent(getBaseContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
        }
    }

    private boolean isUser() {
        try {
            File file = new File(getCacheDir(), "Remember");
            if (!file.exists()) {
                return false;
            }
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String data = reader.readLine();
            String[] strings = data.split("_");
            Key.setUserId(strings[0]);
            Key.setUserName(strings[1]);
            Key.setUserGender(strings[2]);
            Key.setUserAge(strings[3]);
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    class CloseActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "CloseHomeActivity":
                    HomeActivity.this.finish();
                    break;
                case Key.SELECT_VIEWPAGER + 1:
                    viewPager.setCurrentItem(1);
                    break;
                case Key.SELECT_VIEWPAGER + 2:
                    viewPager.setCurrentItem(2);
                    break;
            }

        }
    }

    private void registerCloseActivityReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CloseHomeActivity");
        intentFilter.addAction(Key.SELECT_VIEWPAGER + 1);
        intentFilter.addAction(Key.SELECT_VIEWPAGER + 2);
        registerReceiver(closeActivityReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(closeActivityReceiver);
        super.onDestroy();
    }
}