package com.example.a10835.easyweather.util;

import android.net.Uri;
import android.util.Log;

import com.example.a10835.easyweather.json.City;
import com.example.a10835.easyweather.json.CityDailyWeather;
import com.example.a10835.easyweather.json.CityHoulyWeather;
import com.example.a10835.easyweather.moudle.CityPath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10835 on 2017/10/9.
 */

public class ApiUrl {

    private static final String TAG = "EasyWeatherFetch";
    private static final String KEY = "g0poiykdjmiphn8r";
    public static final int LEVEL_NOWWEATHER = 0;
    public static final int LEVEL_DAILYWEATHER = 1;
    public static final int LEVEL_LIVESUGGESTION = 2;
    public static final int LEVEL_SUNRISE = 3;
    public static final int LEVEL_HOULYWEATHER=4;
    public static final int LEVEL_AQI=5;
    public static final int LEVEL_CITY_SEARCH=6;


    private static final Uri nowWeatherUri = Uri.parse("https://api.seniverse.com/v3/weather/now.json?key=" + KEY)
            .buildUpon()
            .build();
    public static final Uri dailyWeatherUri = Uri.
            parse("https://api.seniverse.com/v3/weather/daily.json?key=" + KEY + "&language=zh-Hans&unit=c&start=0&days=10")
            .buildUpon()
            .build();
    public static final Uri hourlyWeatherUri= Uri
            .parse("https://api.seniverse.com/v3/weather/hourly.json?key="+KEY+"&start=0&hours=24")
            .buildUpon()
            .build();
    public static final Uri liveSuggestions = Uri.parse("https://api.seniverse.com/v3/life/suggestion.json?key=" + KEY)
            .buildUpon()
            .build();
    public static final Uri sunRise = Uri.parse("https://api.seniverse.com/v3/geo/sun.json?key=" + KEY+"&start=0&days=1")
            .buildUpon()
            .build();
    public static final Uri Aqi= Uri.parse("https://api.seniverse.com/v3/air/now.json?key="+KEY+"&scope=city")
            .buildUpon()
            .build();
    public static final Uri citySearch=Uri.parse("https://api.seniverse.com/v3/location/search.json?key="+KEY)
            .buildUpon()
            .build();


    public static String getApiUrL(int requestCode, String query) {
        String method = "location";
        switch (requestCode) {
            case LEVEL_NOWWEATHER:
                Uri uri = nowWeatherUri.buildUpon()
                        .appendQueryParameter(method, query)
                        .build();
                return uri.toString();

            case LEVEL_DAILYWEATHER:
                Uri dailyWeather = dailyWeatherUri.buildUpon()
                        .appendQueryParameter(method, query)
                        .build();
                return dailyWeather.toString();

            case LEVEL_LIVESUGGESTION:
                Uri Suggestion = liveSuggestions.buildUpon()
                        .appendQueryParameter(method, query)
                        .build();
                return Suggestion.toString();

            case LEVEL_SUNRISE:
                Uri SunRise = sunRise.buildUpon()
                        .appendQueryParameter(method, query)
                        .build();
                return SunRise.toString();
            case LEVEL_HOULYWEATHER:
                Uri houlyWeather=hourlyWeatherUri.buildUpon()
                        .appendQueryParameter(method,query)
                        .build();
                return houlyWeather.toString();
            case LEVEL_AQI:
                Uri aqi=Aqi.buildUpon()
                        .appendQueryParameter(method,query)
                        .build();
                return aqi.toString();
            case LEVEL_CITY_SEARCH:
                Uri searchCity=citySearch.buildUpon()
                        .appendQueryParameter("q",query)
                        .build();
                 return searchCity.toString();
            default:
                return null;
        }


    }

    public static City parstNowWeatherJsonItem(String response) {
        City city = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("results");
            //获取json中的location 数组
            JSONObject js = results.getJSONObject(0);
            JSONObject location = js.getJSONObject("location");
            JSONObject now = js.getJSONObject("now");
            city = new City();
            city.setCityName(location.getString("name"));
            city.setId(location.getString("id"));
            city.setNowWeather(now.getString("text"));
            city.setNowTem(now.getString("temperature"));
            city.setCode(now.getString("code"));
            city.setFellsLike(now.getString("feels_like"));
            city.setHumidity(now.getString("humidity"));
            city.setPressure(now.getString("pressure"));
            city.setVisibility(now.getString("visibility"));
            String wind=now.getString("wind_direction")+"    "+now.getString("wind_speed")+"km/h";
            city.setWind(wind);
        } catch (JSONException e) {
            Log.e(TAG, "parstJsonItem: ", e);
        }
        return city;
    }

    public static List<CityDailyWeather> parstDailyWeatherJsonItem(String response) {
        List<CityDailyWeather> weather = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("results");
            JSONObject object = array.getJSONObject(0);
            JSONArray dailyJsonArrary = object.getJSONArray("daily");
            for (int i = 0; i < dailyJsonArrary.length(); i++) {
                JSONObject daiyly = dailyJsonArrary.getJSONObject(i);
                CityDailyWeather mcityDailWeather = new CityDailyWeather();
                mcityDailWeather.setWeather(daiyly.getString("text_day"));
                mcityDailWeather.setWeatherCode(daiyly.getString("code_day"));
                mcityDailWeather.setMaxTem(daiyly.getString("high"));
                mcityDailWeather.setMinTem(daiyly.getString("low"));
                mcityDailWeather.setDateStr(daiyly.getString("date"));
                mcityDailWeather.setRainFallPro(daiyly.getString("precip"));
                weather.add(mcityDailWeather);
            }
        } catch (JSONException e) {
            Log.e(TAG, "parstDailyWeatherJsonItem: error", e);
        }
        return weather;
    }

    public static List<CityHoulyWeather> parseHoulyWeatherJsonItem(String response){
        List<CityHoulyWeather> hourlyWeathers=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray  result=jsonObject.getJSONArray("results");
            JSONObject js=result.getJSONObject(0);
            JSONArray houly=js.getJSONArray("hourly");
            for (int i = 0; i <houly.length() ; i++) {
                JSONObject ob=houly.getJSONObject(i);
                CityHoulyWeather hw=new CityHoulyWeather();
                hw.setNowHour(ob.getString("time"));
                hw.setHourTem(ob.getString("temperature"));
                hw.setWeatherCode(ob.getString("code"));
                hourlyWeathers.add(hw);

            }
        } catch (JSONException e) {
            Log.e(TAG, "parseHoulyWeatherJsonItem: ",e );
        }
        return hourlyWeathers;
    }
    public static String[] parseSunRiseJsonItem(String response) {
        String []sunRise=new String[2];
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            JSONObject results=jsonArray.getJSONObject(0);
            JSONArray sun=results.getJSONArray("sun");
            JSONObject object=sun.getJSONObject(0);
            sunRise[0]=object.getString("sunrise");
            sunRise[1]=object.getString("sunset");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sunRise;
    }
    public static String[] parseAqiJsonItem(String response){
        String [] aqis=new String[5];

            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONArray  results=jsonObject.getJSONArray("results");
                JSONObject object=results.getJSONObject(0);
                JSONObject aqi=object.getJSONObject("air");
                JSONObject city=aqi.getJSONObject("city");
                aqis[0]=city.getString("aqi");
                aqis[1]=city.getString("pm25");
                aqis[2]=city.getString("quality");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return aqis;
    }
    public static  List<CityPath> parseCityItem(String response) {
        List<CityPath> citys=new ArrayList<>();
        try {
            JSONObject object=new JSONObject(response);
            JSONArray results=object.getJSONArray("results");
            for (int i = 0; i <results.length() ; i++) {
                JSONObject city=results.getJSONObject(i);
                CityPath path=new CityPath();
                path.setCodeId(city.getString("id"));
                path.setName(city.getString("name"));
                path.setPath(city.getString("path"));
                citys.add(path);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return citys;
    }
}