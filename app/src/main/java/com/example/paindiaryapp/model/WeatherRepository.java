package com.example.paindiaryapp.model;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.paindiaryapp.service.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {
    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "68064816b4cbbd6a56b88afc676582a8";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static WeatherRepository newsRepository;

    public static WeatherRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new WeatherRepository();
        }
        return newsRepository;
    }

    private WeatherService weatherService;

    public WeatherRepository(){
        weatherService = retrofit.create(WeatherService.class);
    }

    public MutableLiveData<WeatherResponse> getWeather(String lat, String lon){
        MutableLiveData<WeatherResponse> weatherData = new MutableLiveData<>();
        Call<WeatherResponse> call = weatherService.getCurrentWeatherData(lat, lon, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    Log.d("weather service", "call success");
                    weatherData.setValue(weatherResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                Log.d("weather service", "fail to call api " + t.getMessage());
                weatherData.setValue(null);
            }
        });
        return weatherData;
    }
}