package com.test.home.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.home.databinding.FragmentDisplayBinding;
import com.test.home.model.DataViewModel;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class DisplayFragment extends Fragment {


    DataViewModel dataModel;
    FragmentDisplayBinding binding;
    private static final String KEY = "param";
    private LiveData<String> output;

    public DisplayFragment() {
    }

    public static DisplayFragment newInstance(String param1, String param2) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDisplayBinding.inflate(inflater,container,false);
        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataModel = ViewModelProviders.of(this).get(DataViewModel.class);
        dataModel.init();
        binding.tvSecondData.setMovementMethod(new ScrollingMovementMethod());
        binding.tvThirdData.setMovementMethod(new ScrollingMovementMethod());
        getLifecycle().addObserver(dataModel);
        onClick();
    }

    private void onClick(){
        binding.btnSubmit.setOnClickListener(v->{
           onSubmit();
        });
    }

    private void onSubmit(){
        binding.progressCircular.setVisibility(View.VISIBLE);
        dataModel.getTenthCharacter().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String data) {
                binding.tvFirstData.append(data);
            }
        });

        dataModel.getListOfTenthChar().observe(getViewLifecycleOwner(),data ->{

            StringBuilder stringBuilder = new StringBuilder();
            for(String string:data){
                stringBuilder.append(string+"\n");
            }
            binding.tvSecondData.append(stringBuilder.toString());


        });

        dataModel.getUniqueWord().observe(getViewLifecycleOwner(),data ->{
            StringBuilder stringBuilder = new StringBuilder();

            for(Map.Entry<String,Integer> mapEntry: data.entrySet() ){
                stringBuilder.append(mapEntry.getKey() + "   :->  "+mapEntry.getValue() +"\n");
            }
            binding.tvThirdData.append(stringBuilder.toString());
            binding.progressCircular.setVisibility(View.GONE);


        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(output!=null && output.getValue()!=null)
        {
            outState.putString(KEY,output.getValue());
        }
        super.onSaveInstanceState(outState);
    }
}
