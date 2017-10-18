package com.example.a10835.easyweather.json;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10835 on 2017/10/9.
 */

public class WeatherAssest {
    private static final String TAG = "WeatherAssest";
    private String FLODER="weather";
    private List<WeatherPic> weatherPic;
    private AssetManager assetManaget;

    public WeatherAssest(Context context) {
        weatherPic = new ArrayList<>();
        assetManaget=context.getAssets();
        try {
            String[] pics=assetManaget.list(FLODER);
            for (int i = 0; i <pics.length ; i++) {
                String mAssets=FLODER+"/"+pics[i];
                WeatherPic weathpic=new WeatherPic(mAssets);
                weatherPic.add(weathpic);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
