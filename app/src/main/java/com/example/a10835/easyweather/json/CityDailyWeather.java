package com.example.a10835.easyweather.json;

/**
 * Created by 10835 on 2017/10/9.
 */

public class CityDailyWeather {
    private String weather;
    private String weatherCode;
    private String maxTem;
    private String minTem;
    private String dateStr;
    private String rainFallPro;

    public String getRainFallPro() {
        return rainFallPro;
    }

    public void setRainFallPro(String rainFallPro) {
        this.rainFallPro = rainFallPro;
    }

    public String getWeather() {
        return weather;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getMaxTem() {
        return maxTem;
    }

    public void setMaxTem(String maxTem) {
        this.maxTem = maxTem;
    }

    public String getMinTem() {
        return minTem;
    }

    public void setMinTem(String minTem) {
        this.minTem = minTem;
    }
}

