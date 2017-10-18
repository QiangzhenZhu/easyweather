package com.example.a10835.easyweather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a10835.easyweather.util.HttpConnect;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 10835 on 2017/10/9.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);





        setContentView(R.layout.frame_layout);


        FragmentManager manager=getSupportFragmentManager();
        Fragment fragment=manager.findFragmentById(R.id.fraglment_container);
        if (fragment==null){
            fragment=createFragment();
        }
        manager.beginTransaction()
                .add(R.id.fraglment_container,fragment)
                .commit();
    }

    public abstract Fragment createFragment();
}
