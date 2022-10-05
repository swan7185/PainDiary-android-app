package com.example.paindiaryapp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paindiaryapp.HomeActivity;
import com.example.paindiaryapp.R;
import com.example.paindiaryapp.adapter.DailyRecordListAdapter;
import com.example.paindiaryapp.databinding.FragmentDailyrecordBinding;
import com.example.paindiaryapp.model.UserDailyData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DailyRecordFragment extends Fragment {

    private FragmentDailyrecordBinding binding;
    private HomeActivity activity;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        binding = FragmentDailyrecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.activity = (HomeActivity)getActivity();
        recyclerView = binding.recyclerView;
        activity.userViewMode.getAllUserData().observe(activity, userListUpdateObserver);
        return root;
    }


    Observer<List<UserDailyData>> userListUpdateObserver = new Observer<List<UserDailyData>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onChanged(List<UserDailyData> userDatas) {
            DailyRecordListAdapter recyclerViewAdapter = new DailyRecordListAdapter(activity, userDatas);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}