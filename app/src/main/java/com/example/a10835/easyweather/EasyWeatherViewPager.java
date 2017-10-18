package com.example.a10835.easyweather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a10835.easyweather.moudle.CityPath;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10835 on 2017/10/12.
 */

public class EasyWeatherViewPager extends AppCompatActivity {
    public static final String bingUrl="https://api.dujin.org/bing/1920.php";
    public static final String GET_CITY_NAME="EasyFragment";
    private String location;
    private ViewPager mViewPager;
    private List<CityPath> citys;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            int option=View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            ActionBar actionBar=getSupportActionBar();
            if (actionBar!=null){
                actionBar.hide();
            }

        }
        setContentView(R.layout.activity_easy_weather_view_pager);

        imageView= (ImageView) findViewById(R.id.image_back_ground);
        Glide.with(EasyWeatherViewPager.this).load(bingUrl).into(imageView);
        citys= DataSupport.findAll(CityPath.class);
        if (citys.size()==0){
            CityPath cityPath=new CityPath();
            cityPath.save();
        }
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                citys=DataSupport.findAll(CityPath.class);
                CityPath city=citys.get(position);
                return EasyWeatherFragment.newInstance(city.getName());
            }

            @Override
            public int getCount() {
                return citys.size();
            }
        });
        setCurrentViewPager();
    }
    public static Intent newIntent(Context context, String query){
        Intent intent=new Intent(context,EasyWeatherViewPager.class);
        intent.putExtra(GET_CITY_NAME,query);
        return intent;
    }
    public void setCurrentViewPager(){
        location=getIntent().getStringExtra(GET_CITY_NAME);
        if (!TextUtils.isEmpty(location)){
            for (int i = 0; i <citys.size() ; i++) {
                if (location.equals(citys.get(i).getName())){
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }

        }
    }
}
