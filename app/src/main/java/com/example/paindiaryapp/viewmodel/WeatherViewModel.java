package com.example.paindiaryapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.paindiaryapp.model.WeatherRepository;
import com.example.paindiaryapp.model.WeatherResponse;

public class WeatherViewModel extends ViewModel {
    private MutableLiveData<WeatherResponse> mutableWeatherData;
    private WeatherRepository weatherRepository;
    private static String lat = "31.23";
    private static String lon = "120.71";

    public void init(){
        if (mutableWeatherData != null){
            return;
        }
        weatherRepository = weatherRepository.getInstance();
        mutableWeatherData = weatherRepository.getWeather(lat, lon);

    }

    public LiveData<WeatherResponse> getWeatherData() {
        return mutableWeatherData;
    }
}
