package com.example.a10835.easyweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.a10835.easyweather.json.City;
import com.example.a10835.easyweather.json.CityDailyWeather;
import com.example.a10835.easyweather.json.CityHoulyWeather;
import com.example.a10835.easyweather.moudle.CityPath;
import com.example.a10835.easyweather.util.ApiUrl;
import com.example.a10835.easyweather.util.HttpConnect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 10835 on 2017/10/9.
 */

public class EasyWeatherFragment extends Fragment {
    private List<CityPath> citys=new ArrayList<>();
    private static final String TAG = "EasyWeatherFragment";
    public static final String ARGUMENTS_EASY_WEATHER="get_city_name";
    private Boolean isGps;
    private LocationClient mLocationCilent;
    private ImageView mLocationImageView;
    private TextView mCityNameText;
    private TextView mWeatherNowText;
    private TextView mNowTemText;


    private TextView mWeekText;
    private TextView mNowText;
    private TextView mMaxTemText;
    private TextView mMinTemText;


    private TextView mSuggestionText;


    private TextView mSunRiseText;
    private TextView mSunDownText;
    private TextView mRainProbaText;
    private TextView mHumditiyText;
    private TextView mWindSpeedText;
    private TextView mFeelTemText;
    private TextView mRainFallText;
    private TextView mPressureText;
    private TextView mVisibilityText;
    private TextView mUVText;
    private TextView mAqiText;
    private TextView mAirLevelText;










    private RecyclerView mHourlyRecycleView;
    private RecyclerView mWeeklyRecycleView;
    public static Fragment newInstance(String location){
        EasyWeatherFragment fragment=new EasyWeatherFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ARGUMENTS_EASY_WEATHER,location);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isGps=false;
        try{
            String location=getArguments().getString(ARGUMENTS_EASY_WEATHER);
            setView(location);
        }catch (NullPointerException e){
            Log.e(TAG, "onCreate: ",e );
            isGps=true;
            CityPath city=new CityPath();
            mLocationCilent=new LocationClient(getActivity().getApplicationContext());
            mLocationCilent.registerLocationListener(new EasyWeatherLocationLinstner());
            reQuestPression();
            setLocation();
        }









    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmenet_easy_weather,container,false);
        initView(view);
        mWeeklyRecycleView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        LinearLayoutManager manager=new LinearLayoutManager(container.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHourlyRecycleView.setLayoutManager(manager);
        mLocationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SelectCityActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }






    public void initView(View view){
        mLocationImageView=view.findViewById(R.id.imageView);
        mCityNameText=view.findViewById(R.id.textView4);
        mWeatherNowText=view.findViewById(R.id.textView6);
        mNowTemText=view.findViewById(R.id.textView8);
        mWeekText=view.findViewById(R.id.textView17);
        mNowText=view.findViewById(R.id.textView18);
        mMaxTemText=view.findViewById(R.id.textView19);
        mMinTemText=view.findViewById(R.id.textView20);

        mSuggestionText=view.findViewById(R.id.textView);
        mSunRiseText=view.findViewById(R.id.textView3);
        mSunDownText=view.findViewById(R.id.textView7);
        mRainProbaText=view.findViewById(R.id.textView24);
        mHumditiyText=view.findViewById(R.id.textView25);
        mWindSpeedText= view.findViewById(R.id.textView26);
        mFeelTemText=view.findViewById(R.id.textView27);
        mRainFallText=view.findViewById(R.id.textView28);
        mPressureText=view.findViewById(R.id.textView29);
        mVisibilityText=view.findViewById(R.id.textView30);
        mUVText=view.findViewById(R.id.textView31);
        mAqiText=view.findViewById(R.id.textView32);
        mAirLevelText=view.findViewById(R.id.textView33);

        mHourlyRecycleView=view.findViewById(R.id.recyclerView);
        mWeeklyRecycleView=view.findViewById(R.id.daily_weather_recycleView);


    }
    public void setView(String location){
        setWeelyWeather(location.toString());
        setHoulyWeather(location.toString());
        setSunRise(location.toString());
        setNowWeather(location.toString());
        setAQI(location.toString());
    }


    public static String  getWeekOfDays(String str){
        String days[]=str.split("-");
        Date d= new GregorianCalendar(Integer.parseInt(days[0]),Integer.parseInt(days[1]),Integer.parseInt(days[2])).getTime();
        String dateFormat="EEEE";
        String date= (String) DateFormat.format(dateFormat,d);
        return date;
    }
    public  void setNowWeather(String location){
        HttpConnect.SendOkHttp(ApiUrl.getApiUrL(0, location), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                final City city=ApiUrl.parstNowWeatherJsonItem(res);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCityNameText.setText(city.getCityName());
                        mWeatherNowText.setText(city.getNowWeather());
                        mNowTemText.setText(city.getNowTem());
                        mHumditiyText.setText(city.getHumidity());
                        mPressureText.setText(city.getPressure());
                        mVisibilityText.setText(city.getVisibility());
                        mFeelTemText.setText(city.getFellsLike());
                        mWindSpeedText.setText(city.getWind());


                    }
                });
            }
        });
    }
    public void setWeelyWeather(String location){
        HttpConnect.SendOkHttp(ApiUrl.getApiUrL(1, location), new Callback() {
            List<CityDailyWeather> cityDailyWeathers=new ArrayList<CityDailyWeather>();
            List<CityDailyWeather> weathers=new ArrayList<CityDailyWeather>();
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                if (!TextUtils.isEmpty(res)){
                    cityDailyWeathers= ApiUrl.parstDailyWeatherJsonItem(res);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CityDailyWeather cw=cityDailyWeathers.get(0);
                            for (int i = 1; i <cityDailyWeathers.size() ; i++) {
                                weathers.add(cityDailyWeathers.get(i));
                            }
                            mRainProbaText.setText(cw.getRainFallPro());
                            mMaxTemText.setText(cw.getMaxTem());
                            mMinTemText.setText(cw.getMinTem());
                            mWeekText.setText(getWeekOfDays(cw.getDateStr()));
                            mNowText.setText("今天");
                            DailyWeather.DailyWeatherAdapter adapter=new DailyWeather.DailyWeatherAdapter(weathers);
                            mWeeklyRecycleView.setAdapter(adapter);
                        }
                    });
                }else {
                    Log.e(TAG, "onResponse: res is null" );
                }
            }
        });
    }
    public  void setHoulyWeather(String location){
        HttpConnect.SendOkHttp(ApiUrl.getApiUrL(4, location), new Callback() {
            List<CityHoulyWeather> hws=new ArrayList<CityHoulyWeather>();
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hws=ApiUrl.parseHoulyWeatherJsonItem(response.body().string());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHourlyRecycleView.setAdapter(new HoulyWeather.HoulyAdapter(hws));
                    }
                });

            }
        });
    }
    public void setSunRise(String location){
        HttpConnect.SendOkHttp(ApiUrl.getApiUrL(3, location), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String []sun=ApiUrl.parseSunRiseJsonItem(response.body().string());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSunRiseText.setText("早上"+sun[0]);
                        mSunDownText.setText("下午"+sun[1]);
                    }
                });
            }
        });
    }
    public void setAQI(String location){
        HttpConnect.SendOkHttp(ApiUrl.getApiUrL(5, location), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String [] aqis=ApiUrl.parseAqiJsonItem(response.body().string());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAirLevelText.setText(aqis[2]);
                        mAqiText.setText(aqis[0]);
                    }
                });

            }
        });
    }
    class EasyWeatherLocationLinstner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder location=new StringBuilder();
            StringBuilder cityandStreet=new StringBuilder();
            location.append(bdLocation.getLatitude()+":");
            location.append(bdLocation.getLongitude());
            cityandStreet.append(bdLocation.getCountry()+"/");
            cityandStreet.append(bdLocation.getCity()+"/");
            cityandStreet.append(bdLocation.getDistrict());
            cityandStreet.append(bdLocation.getStreet());
            Log.d("listner", "onReceiveLocation: "+cityandStreet);
            mSuggestionText.setText(location);
            setView(location.toString());

        }
    }
    public void setLocation(){

        initLocation();
        mLocationCilent.start();

    }

    public void reQuestPression(){
        List<String> manifestPression=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            manifestPression.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            manifestPression.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            manifestPression.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!manifestPression.isEmpty()){
            String [] pression=manifestPression.toArray(new String[manifestPression.size()]);
            ActivityCompat.requestPermissions(getActivity(),pression,1);

        }else {
            setLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:
                         grantResults) {
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(getActivity(),"必须授权所有权限才能运行本程序",Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                            setLocation();

                    }
                }else {
                    Toast.makeText(getActivity(),"发生未知错误",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        mLocationCilent.setLocOption(option);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isGps){
            mLocationCilent.stop();
        }

    }
}
