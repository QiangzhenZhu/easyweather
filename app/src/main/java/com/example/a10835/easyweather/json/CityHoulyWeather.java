package com.example.a10835.easyweather.json;

/**
 * Created by 10835 on 2017/10/10.
 */

public class CityHoulyWeather  {
    private String nowHour;
    private String weatherCode;
    private String sunRise;
    private String sunFall;
    private String hourTem;

    public String getSunRise() {
        return sunRise;
    }



    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunFall() {
        return sunFall;
    }

    public void setSunFall(String sunFall) {
        this.sunFall = sunFall;
    }



    public CityHoulyWeather() {

    }

    public String getNowHour() {
        return nowHour;
    }

    public void setNowHour(String hourString) {
        String temp=hourString.substring(11,13);
        if (Integer.parseInt(temp)==0){
            nowHour="上午"+12+"时";
        }
        if (Integer.parseInt(temp)<=12&&Integer.parseInt(temp)>=1){
          nowHour= "上午"+temp+"时";
        }else if (Integer.parseInt(temp)>=13&&Integer.parseInt(temp)<=24)
        {
            nowHour="下午"+(Integer.parseInt(temp)-12)+"时";
        }
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getHourTem() {
        return hourTem;
    }

    public void setHourTem(String hourTem) {
        this.hourTem = hourTem;
    }
}
