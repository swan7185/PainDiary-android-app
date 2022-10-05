package com.example.paindiaryapp.fragment;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.paindiaryapp.CustomItem;
import com.example.paindiaryapp.HomeActivity;
import com.example.paindiaryapp.IconAdapter;
import com.example.paindiaryapp.R;
import com.example.paindiaryapp.TimeReceiver;
import com.example.paindiaryapp.Util;
import com.example.paindiaryapp.adapter.CustomListAdapter;
import com.example.paindiaryapp.databinding.FragmentPaindataentryBinding;
import com.example.paindiaryapp.model.UserDailyData;
import com.example.paindiaryapp.model.WeatherData;
import com.example.paindiaryapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class PainDataEntryFragment extends Fragment {
    private FragmentPaindataentryBinding binding;
    private ArrayList<CustomItem> itemList = new ArrayList<>();
    private Spinner spin;
    private int mHour;
    private int mMinute;
    public static final long DAY= 1000L * 60 * 60 * 24;

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private UserViewModel userViewModel;


    String[] moodLevel = {
        "very low",
        "low",
        "average",
        "good",
        "very good"
    };

    String[] position = {
            "back",
            "neck",
            "head",
            "knees",
            "hips",
            "abdomen",
            "elbows",
            "shoulders",
            "shins",
            "jaw",
            "facial"
    };

    public PainDataEntryFragment(){
        mHour = -1;
        mMinute = -1;
        itemList = new ArrayList<>();
        itemList.add(new CustomItem("very low",R.drawable.ic_baseline_sentiment_very_dissatisfied_24));
        itemList.add(new CustomItem("low",R.drawable.ic_baseline_sentiment_dissatisfied_24));
        itemList.add(new CustomItem("average",R.drawable.ic_baseline_sentiment_neutral_24));
        itemList.add(new CustomItem("good",R.drawable.ic_baseline_sentiment_satisfied_24));
        itemList.add(new CustomItem("very good",R.drawable.ic_baseline_sentiment_satisfied_alt_24));
    }

    private void createNotificationChannel() {
// Create the NotificationChannel, but only on API 26+ because
// the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "PainDiaryApp";
            String description = "PainDairyApp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("PainDiaryApp", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }}

    private void scheduleNotification(Notification notification, long futureInMillis) {

        Intent notificationIntent = new Intent(this.getActivity(), TimeReceiver.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationIntent.putExtra(TimeReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(TimeReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getContext(),
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), "PainDiaryApp");
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.cloudy);
        builder.setAutoCancel(true);
        return builder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = FragmentPaindataentryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        spin = binding.customIconSpinner;
        userViewModel = ((HomeActivity)getActivity()).userViewMode;

        final ArrayAdapter<CharSequence> spinnerAdapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.pain_Level_List, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.painLevelSpinner.setAdapter(spinnerAdapter1);

        final ArrayAdapter<CharSequence> spinnerAdapter2 = ArrayAdapter.createFromResource(view.getContext(), R.array.pain_Location_List, android.R.layout.simple_spinner_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.painLocationSpinner.setAdapter(spinnerAdapter2);


        SharedPreferences sharedPref = requireActivity(). getSharedPreferences("Message", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPref.edit();


        IconAdapter spinnerAdapter3 = new IconAdapter(view.getContext(), R.layout.custom_dropdown_layout, itemList);
        if(binding.customIconSpinner!=null){
            binding.customIconSpinner.setAdapter(spinnerAdapter3);
            binding.customIconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItem item = (CustomItem) parent.getItemAtPosition(position);
                    binding.moodEditText.setText("mood level:" + item.getSpinnerItemName()); }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }});}
        spin.setAdapter(spinnerAdapter3);


        binding.goalEdit.setText("10000");


        binding.painLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String showLevel = parent.getItemAtPosition(position).toString();
                binding.painLevelEditText.setText("Pain intensity level:" + showLevel);
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }});

        binding.painLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String showLocation = parent.getItemAtPosition(position).toString();
                binding.painLocationEditText.setText("Pain Location:" + showLocation);
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {    }});




        binding.setTimeButton.setOnClickListener(new View.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View v) {
            //Jump only when the alarm is activated
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,binding.timePicker.getHour());
            calendar.set(Calendar.MINUTE,binding.timePicker.getMinute()-2);
            calendar.set(Calendar.SECOND,0);
            Date date = calendar.getTime();

            scheduleNotification(getNotification("It's time to update your daily data!"), calendar.getTimeInMillis());

        }
    });





        this.createNotificationChannel();
        binding.SaveButton.setOnClickListener((View v) -> {
            Integer goal = Util.tryParseInt(binding.goalEdit.getText().toString());
            Integer cur = Util.tryParseInt(binding.updateStepsEdit.getText().toString());

            if (goal == null) {
                binding.goalLayout.setError("goal step must be integer");
                return;
            }
            if (cur == null) {
                binding.goalLayout.setError("current step must be integer");
                return;
            }
            binding.customIconSpinner.setEnabled(false);
            binding.painLevelSpinner.setEnabled(false);
            binding.painLocationSpinner.setEnabled(false);
            binding.goalEdit.setEnabled(false);
            binding.updateStepsEdit.setEnabled(false);

            Date today = Util.getToday().getTime();
     

            WeatherData weatherData = ((HomeActivity) getActivity()).weatherViewModel.getWeatherData().getValue().weatherData;
            UserDailyData userData = new UserDailyData(
                    ((HomeActivity) getActivity()).getUserEmail(),
                    (Date) today.clone(),
                    Integer.parseInt(binding.painLevelEditText.getText().toString().split(":")[1]),
                    binding.painLocationEditText.getText().toString(),
                    binding.moodEditText.getText().toString(),
                    Integer.parseInt(binding.goalEdit.getText().toString()),
                    Integer.parseInt(binding.updateStepsEdit.getText().toString()),
                    weatherData.temp - (float) 273.15,
                    weatherData.humidity,
                    weatherData.pressure
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Util.setupDailyJob(userData);
            }

            String tag = "PainDataEntry";
            userViewModel.findByIDFuture(today).whenComplete((UserDailyData oldData, Throwable e) -> {
                if (e != null) {
                    Log.i(tag, e.getMessage());
                }
                if (oldData == null) {
                    userViewModel.insert(userData);
                    Log.i(tag, "insert data " + userData.date.toString());
                } else {
                    userViewModel.Update(userData);
                    Log.i(tag, "update data " + userData.date.toString());
                }
            });

        });

        binding.EditButton.setOnClickListener((View v) -> {
            binding.customIconSpinner.setEnabled(true);
            binding.painLevelSpinner.setEnabled(true);
            binding.painLocationSpinner.setEnabled(true);
            binding.goalEdit.setEnabled(true);
            binding.updateStepsEdit.setEnabled(true);

        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                binding.painLevelEditText.setText("");
                binding.painLocationEditText.setText("");
                binding.moodEditText.setText("");
            }
    });

        return view;
    }
}





