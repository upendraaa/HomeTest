package com.test.home.network;

import io.reactivex.Observable;

public class NetworkRepository {

    private NetworkApi networkApi;

    private NetworkRepository(){
        networkApi = NetworkService.createNetworkService(NetworkApi.class);
    }

    private static NetworkRepository networkRepository = null;


    public static NetworkRepository getInstance(){
        if(networkRepository == null)
        {
            networkRepository = new NetworkRepository();
        }
        return networkRepository;
    }

    public Observable<String> getData(){
        return networkApi.getData();
    }
}
