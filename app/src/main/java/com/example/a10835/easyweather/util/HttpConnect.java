package com.example.a10835.easyweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 10835 on 2017/10/9.
 */

public class HttpConnect {
    public static void SendOkHttp(String url,okhttp3.Callback callback){
        OkHttpClient cilent =new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        cilent.newCall(request).enqueue(callback);
    }
}
