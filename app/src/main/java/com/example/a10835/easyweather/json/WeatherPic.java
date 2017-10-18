package com.example.a10835.easyweather.json;

/**
 * Created by 10835 on 2017/10/9.
 */

public class WeatherPic {
    private String name;
    private String mAssets;

    public WeatherPic( String mAssets) {
        this.mAssets = mAssets;
        String[] names=mAssets.split("/");
        String temp=names[names.length-1];
        name=temp.replace(".png","");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmAssets() {
        return mAssets;
    }

    public void setmAssets(String mAssets) {
        this.mAssets = mAssets;
    }
}
