package com.provider.beautician.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.provider.beautician.constant.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Archive_PC_1 on 3/10/2018.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static ApiService getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        return retrofit.create(ApiService.class);
    }

}
