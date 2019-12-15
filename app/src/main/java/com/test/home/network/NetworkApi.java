package com.test.home.network;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NetworkApi {

    @GET("2018/01/22/life-as-an-android-engineer/")
    Observable<String> getData();
}
