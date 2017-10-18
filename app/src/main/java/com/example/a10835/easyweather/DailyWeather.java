package com.example.a10835.easyweather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a10835.easyweather.json.CityDailyWeather;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 10835 on 2017/10/9.
 */

public class DailyWeather {
    public static final String FLODER="weather";
    private static final String TAG = "DailyWeather";
    public static class DailyWeaterViewHolder extends RecyclerView.ViewHolder{
        private TextView mDateText;
        private TextView mMaxText;
        private TextView mMinText;
        private ImageView mWeatherImage;

        public DailyWeaterViewHolder(View itemView) {
            super(itemView);
            mDateText=itemView.findViewById(R.id.date_text_recycleView);
            mMaxText=itemView.findViewById(R.id.max_tem_recycleView);
            mMinText=itemView.findViewById(R.id.min_tem_RecycleView);
            mWeatherImage=itemView.findViewById(R.id.image_daily_weatherRecylceView);
        }
        public void Bind(Context context, CityDailyWeather weather){
            mDateText.setText(EasyWeatherFragment.getWeekOfDays(weather.getDateStr()));
            mMinText.setText(weather.getMinTem());
            mMaxText.setText(weather.getMaxTem());
            String weatherCode= weather.getWeatherCode();
            try {
                InputStream in=context.getAssets().open(FLODER+"/"+weatherCode+".png");
                Bitmap bitmap=BitmapFactory.decodeStream(in);
                mWeatherImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(TAG, "getAssets error ",e );
            }

        }
    }
    public static class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeaterViewHolder>{
        private List<CityDailyWeather> weathers;

        public DailyWeatherAdapter(List<CityDailyWeather> weathers) {
            this.weathers = weathers;
        }

        @Override
        public DailyWeaterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_daily_item,parent,false);
            return new DailyWeaterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DailyWeaterViewHolder holder, int position) {
            CityDailyWeather weather=weathers.get(position);
            holder.Bind(holder.itemView.getContext(),weather);
        }

        @Override
        public int getItemCount() {
            return weathers.size();
        }
    }
}
