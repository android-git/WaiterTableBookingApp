package com.techflitter.testexam.network;

import com.techflitter.testexam.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TechFlitter on 6/30/2017.
 */

public class RetrofitManager {

    private static RetrofitManager manager;
    private Retrofit mRetrofit;

    private RetrofitManager() {
        initRetrofit();
    }

    public static synchronized RetrofitManager getInstance() {
        if (manager == null) {
            manager = new RetrofitManager();
        }
        return manager;
    }

    private void initRetrofit() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(HttpClient.getInstance())
                .addConverterFactory(GsonConverterFactory.create()) // 用到了 com.squareup.retrofit2:adapter-rxjava:2.1.0'
                .build();
    }

    public <T> T create(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
