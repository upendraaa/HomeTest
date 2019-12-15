package com.test.home.model;

import android.util.Log;

import com.test.home.network.NetworkRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DataViewModel extends ViewModel implements LifecycleObserver {

    MutableLiveData<String> responseData;

    MutableLiveData<List<String>> listOfTenthChar;
    MutableLiveData<String> tenthCharacter;
    MutableLiveData<Map<String, Integer>> uniqueWord;

    private NetworkRepository networkRepository;

    CompositeDisposable compositeDisposable;

    public void init() {

        responseData = new MutableLiveData<>();
        tenthCharacter = new MutableLiveData<>();
        uniqueWord = new MutableLiveData<>();
        listOfTenthChar = new MutableLiveData<>();

        networkRepository = NetworkRepository.getInstance();
        getDataFromRepo();


    }

    public LiveData<String> getData() {
        return responseData;
    }


    private void calculateTenthChar() {

        if (responseData != null && responseData.getValue().length() > 10) {
            tenthCharacter.setValue(responseData.getValue().substring(9, 10));
        }
    }


    private void findEveryTenthChar() {

        char[] characters = responseData.getValue().toString().toCharArray();
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < characters.length; ) {
            list.add(String.valueOf(characters[i]));
            i = i + 10;
        }

        listOfTenthChar.setValue(list);
    }


    private void getUniqueWords(){

        String[] strArray = responseData.getValue().toString().split("[\\s@&.?$+-]+");
        HashMap<String,Integer> map = new HashMap<>();

        for(int i=0;i<strArray.length;i++){

            if(map.keySet().contains(strArray[i])){
                int value = map.get(strArray[i]);
                map.put(strArray[i],value+1);
            }else{
                map.put(strArray[i],1);
            }
        }

       uniqueWord.setValue(map);
    }

    private void getDataFromRepo() {

        networkRepository.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }


    private void handleResults(String data) {
        responseData.setValue(data);
        listOfTenthChar = new MutableLiveData<>();
        tenthCharacter = new MutableLiveData<>();
        uniqueWord = new MutableLiveData<>();
    }

    private void handleError(Throwable t) {
        Log.v("Network Error: ",t.getMessage());

    }


    public LiveData<List<String>> getListOfTenthChar() {
        findEveryTenthChar();
        return listOfTenthChar;
    }

    public LiveData<String> getTenthCharacter() {
        calculateTenthChar();
        return tenthCharacter;
    }

    public LiveData<Map<String, Integer>> getUniqueWord() {
        getUniqueWords();
        return uniqueWord;
    }



}


