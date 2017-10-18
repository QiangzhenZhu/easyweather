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

import com.example.a10835.easyweather.json.CityHoulyWeather;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 10835 on 2017/10/10.
 */

public class HoulyWeather {
    public static final String FLODER="weather";
    private static final String TAG = "HoulyWeather";
    public static class HoulyWeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView mHoulyText;
        private TextView mTemText;
        private ImageView mImageView;

        public HoulyWeatherViewHolder(View itemView) {
            super(itemView);
            mHoulyText=itemView.findViewById(R.id.now_houly_item_reclceView);
            mTemText=itemView.findViewById(R.id.houly_tem_recycleView);
            mImageView=itemView.findViewById(R.id.image_houly_recycleView);
        }


        public void Bind(Context context,CityHoulyWeather weather){
            mHoulyText.setText(weather.getNowHour());
            mTemText.setText(weather.getHourTem());
            String weatherCode= weather.getWeatherCode();
            try {
                InputStream in=context.getAssets().open(FLODER+"/"+weatherCode+".png");
                Bitmap bitmap= BitmapFactory.decodeStream(in);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e(TAG, "getAssets error ",e );
            }

        }

    }
    public static class HoulyAdapter extends RecyclerView.Adapter<HoulyWeatherViewHolder>{
        List<CityHoulyWeather> hws;

        public HoulyAdapter(List<CityHoulyWeather> hws) {
            this.hws = hws;
        }


        @Override
        public HoulyWeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.houly_weather_item,parent,false);

            return new HoulyWeatherViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HoulyWeatherViewHolder holder, int position) {
            CityHoulyWeather hw=hws.get(position);
            holder.Bind(holder.itemView.getContext(),hw);
        }

        @Override
        public int getItemCount() {
            return hws.size();
        }
    }
}
