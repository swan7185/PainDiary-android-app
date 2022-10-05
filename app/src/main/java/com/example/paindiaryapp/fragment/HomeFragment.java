package com.example.paindiaryapp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paindiaryapp.HomeActivity;
import com.example.paindiaryapp.R;
import com.example.paindiaryapp.databinding.FragmentDailyrecordBinding;
import com.example.paindiaryapp.databinding.FragmentHomeBinding;

import com.example.paindiaryapp.viewmodel.WeatherViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment} factory method to
 * create an instance of this fragment.
 *
 */



public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((HomeActivity)getActivity()).weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherResponse -> {
            binding.temperature.setText(String.format("%.2f °C", weatherResponse.weatherData.temp-273.15));
            binding.humidity.setText(String.valueOf(weatherResponse.weatherData.humidity) + " %");
            binding.pressure.setText(String.valueOf(weatherResponse.weatherData.pressure) + " hPa");
        });
        return root;
    }
}