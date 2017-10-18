package com.example.a10835.easyweather.json;

/**
 * Created by 10835 on 2017/10/9.
 */

public class City {


    private String id;
    private String cityName;
    private String nowWeather;
    private String nowTem;
    private String code;
    private String fellsLike;
    private String pressure;
    private String humidity;
    private String visibility;
    private String wind;

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getFellsLike() {
        return fellsLike;
    }

    public void setFellsLike(String fellsLike) {
        this.fellsLike = fellsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public City() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getNowWeather() {
        return nowWeather;
    }

    public void setNowWeather(String nowWeather) {
        this.nowWeather = nowWeather;
    }

    public String getNowTem() {
        return nowTem;
    }

    public void setNowTem(String nowTem) {
        this.nowTem = nowTem;
    }
}
