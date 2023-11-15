package com.ispl.ekalarogya.training.main.support_ticket.another_client;

import com.ispl.ekalarogya.training.BuildConfig;;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ispl.ekalarogya.training.rest.HostSelectionInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseServiceSupportModule {
    private static Retrofit retrofitSupport;
    private static HostSelectionInterceptor hostSelectionInterceptorSupport;

    public static Retrofit getAPISupportClient() {
        if (retrofitSupport == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .writeTimeout(300, TimeUnit.SECONDS);

            //this is mandatory to read api call logs
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
            okHttpClient.addInterceptor(getHostSelectionInterceptor());
            okHttpClient.addInterceptor(loggingInterceptor);

            //and we can add multiple interceptors

            retrofitSupport = new Retrofit.Builder().baseUrl(BuildConfig.HELP_URL_SUPPORT)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(createGSON()))
                    .build();
        }
        return retrofitSupport;
    }

    public static  HostSelectionInterceptor getHostSelectionInterceptor(){
        if(hostSelectionInterceptorSupport==null){
            hostSelectionInterceptorSupport = new HostSelectionInterceptor();
        }
        return hostSelectionInterceptorSupport;
    }

    public static Gson createGSON() {
        return new GsonBuilder().setLenient().create();
    }
}
