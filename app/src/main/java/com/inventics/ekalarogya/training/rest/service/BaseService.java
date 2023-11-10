package com.inventics.ekalarogya.training.rest.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.utils.ExtraUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class BaseService {
    private static Retrofit retrofit;

    public static Retrofit getAPIClient() {
        if (retrofit == null) {

            Builder okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
            okHttpClient.addInterceptor(loggingInterceptor);

            retrofit = new Retrofit.Builder().baseUrl(ExtraUtils.imgURL + "api/")
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(createGSON()))
                    .build();
        }
        return retrofit;
    }

    public static Gson createGSON() {
        return new GsonBuilder().setLenient().create();
    }

}
