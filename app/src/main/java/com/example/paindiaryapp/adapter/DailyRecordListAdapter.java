package com.example.paindiaryapp.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paindiaryapp.HomeActivity;
import com.example.paindiaryapp.R;
import com.example.paindiaryapp.Util;
import com.example.paindiaryapp.model.UserDailyData;
import com.example.paindiaryapp.model.UserRepository;
import com.example.paindiaryapp.viewmodel.UserViewModel;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyRecordListAdapter extends
    RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Activity context;
        List<UserDailyData> userArrayList;

        static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public DailyRecordListAdapter(Activity context, List<UserDailyData> userArrayList) {
            this.context = context;
            this.userArrayList = userArrayList;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(context).inflate(R.layout.rv_layout,parent,false);
            return new RecyclerViewViewHolder(rootView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            UserDailyData user = userArrayList.get(position);
            RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

            viewHolder.txtView_title.setText(dateFormat.format(user.date));
            viewHolder.txtView_description.setText(user.getDescription());
        }

        @Override
        public int getItemCount() {
            return userArrayList.size();
        }

        class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
            TextView txtView_title;
            TextView txtView_description;

            public RecyclerViewViewHolder(@NonNull View itemView) {
                super(itemView);
                txtView_title = itemView.findViewById(R.id.txtView_title);
                txtView_description = itemView.findViewById(R.id.txtView_description);


            }
        }
    }

