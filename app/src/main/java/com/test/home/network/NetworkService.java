package com.test.home.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkService {

    public static  String BASE_URL = "https://truecaller.blog/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    public static <S> S createNetworkService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
