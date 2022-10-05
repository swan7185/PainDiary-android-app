package com.example.paindiaryapp.fragment;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paindiaryapp.HomeActivity;
import com.example.paindiaryapp.R;
import com.example.paindiaryapp.databinding.FragmentMapBinding;
import com.example.paindiaryapp.databinding.FragmentReportsBinding;
import com.example.paindiaryapp.model.UserDailyData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReportFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private FragmentReportsBinding binding;
    private HomeActivity activity;
    private ArrayList<PieEntry> painEntry = new ArrayList<PieEntry>();
    private List<PieEntry> stepEntry = new ArrayList<PieEntry>();
    private List<UserDailyData> userDailyData = new ArrayList<UserDailyData>();

    private ArrayList<Integer> painLocationChartColors = new ArrayList<Integer>();
    private ArrayList<Integer> stepChartColors = new ArrayList<Integer>();

    private String currentDateString;
    private LineChart line;
    List<Entry> list = new ArrayList<>();

    public ReportFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.activity = (HomeActivity) getActivity();

        Observer<List<UserDailyData>> userListUpdateObserver = new Observer<List<UserDailyData>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<UserDailyData> userDailyData) {
                stepEntry.clear();
                painEntry.clear();
                HashMap<String, Integer> painLocationCounter = new HashMap<String, Integer>();

                for (UserDailyData userData : userDailyData) {
                    if (painLocationCounter.containsKey(userData.painPosition)) {
                        painLocationCounter.replace(userData.painPosition, painLocationCounter.get(userData.painPosition) + 1);
                    } else
                        painLocationCounter.put(userData.painPosition, 1);
                }


                for (String key : painLocationCounter.keySet()) {
                    painEntry.add(new PieEntry(painLocationCounter.get(key), key));
                }
                stepEntry.add(new PieEntry(userDailyData.get(userDailyData.size() - 1).cur, "current day steps"));
                stepEntry.add(new PieEntry(userDailyData.get(userDailyData.size() - 1).goal, "goal steps"));

                binding.pieChartPainLocation.setUsePercentValues(true);
                Description painChartDescription = new Description();
                painChartDescription.setText("Pain location pie chart");
                binding.pieChartPainLocation.setDescription(painChartDescription);


                binding.pieChartPainLocation.setExtraOffsets(5, 5, 5, 5);

                binding.pieChartPainLocation.setDrawHoleEnabled(true);
                binding.pieChartPainLocation.setHoleColor(Color.WHITE);

                binding.pieChartPainLocation.setTransparentCircleColor(Color.WHITE);
                binding.pieChartPainLocation.setTransparentCircleAlpha(0);
                binding.pieChartPainLocation.setHoleRadius(0f);
                binding.pieChartPainLocation.setTransparentCircleRadius(0f);

                binding.pieChartPainLocation.setDrawCenterText(true);

                binding.pieChartPainLocation.setRotationAngle(0);


                //Get pielocationChart figure column
                Legend legend = binding.pieChartPainLocation.getLegend();
                legend.setForm(Legend.LegendForm.LINE);
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                legend.setDrawInside(false);

                //l.setXEntrySpace(7f); //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
                //l.setYOffset(0f);//设置比例块Y轴偏移量

                legend.setYEntrySpace(4f); //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）

                binding.pieChartPainLocation.setEntryLabelColor(Color.BLACK);
                binding.pieChartPainLocation.setEntryLabelTextSize(8f);

                PieDataSet dataSet = new PieDataSet(painEntry, "pain location items");

                dataSet.setDrawIcons(false);

                dataSet.setSliceSpace(0f);
                dataSet.setIconsOffset(new MPPointF(0, 40));

                dataSet.setSelectionShift(0f);
                painLocationChartColors.add(getResources().getColor(R.color.purple_200));
                painLocationChartColors.add(getResources().getColor(R.color.light_blue_200));
                painLocationChartColors.add(getResources().getColor(R.color.teal_200));
                painLocationChartColors.add(getResources().getColor(R.color.browser_actions_divider_color));
                painLocationChartColors.add(getResources().getColor(R.color.browser_actions_bg_grey));
                painLocationChartColors.add(getResources().getColor(R.color.purple_500));
                painLocationChartColors.add(getResources().getColor(R.color.material_on_background_emphasis_medium));
                painLocationChartColors.add(getResources().getColor(R.color.light_blue_600));
                painLocationChartColors.add(getResources().getColor(R.color.black));
                painLocationChartColors.add(getResources().getColor(R.color.material_on_surface_stroke));
                painLocationChartColors.add(getResources().getColor(R.color.purple_700));
                dataSet.setColors(painLocationChartColors);
                PieData pieData = new PieData(dataSet);
                pieData.setDrawValues(true);

                binding.pieChartPainLocation.setData(pieData);
                binding.pieChartPainLocation.invalidate();
                pieData.setValueTextColor(Color.BLACK);
                pieData.setValueTextSize(8f);
                pieData.setValueFormatter(new PercentFormatter());


                binding.pieChartPainLocation.setData(pieData);
                binding.pieChartPainLocation.invalidate();

                binding.pieChartPainLocation.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        PieEntry pieEntry = (PieEntry) e;
                        Toast.makeText(getActivity(), "->value:" + pieEntry.getValue() + "->lable:" + pieEntry.getLabel(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });


                binding.pieChartStepsTaken.setUsePercentValues(true);
                Description stepDescription = new Description();
                stepDescription.setText("Steps taken pie chart");
                binding.pieChartStepsTaken.setDescription(stepDescription);

                binding.pieChartStepsTaken.setExtraOffsets(5, 5, 5, 5);

                binding.pieChartStepsTaken.setDrawHoleEnabled(true);
                binding.pieChartStepsTaken.setHoleColor(Color.WHITE);

                binding.pieChartStepsTaken.setTransparentCircleColor(Color.WHITE);
                binding.pieChartStepsTaken.setTransparentCircleAlpha(0);
                binding.pieChartStepsTaken.setHoleRadius(50f);
                binding.pieChartStepsTaken.setTransparentCircleRadius(31f);

                binding.pieChartStepsTaken.setDrawCenterText(true);
                binding.pieChartStepsTaken.setRotationAngle(0);

                //Get pieLocationChart figure column
                Legend y = binding.pieChartStepsTaken.getLegend();
                y.setForm(Legend.LegendForm.LINE);
                y.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                y.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                y.setOrientation(Legend.LegendOrientation.VERTICAL);
                y.setDrawInside(false);
                y.setYEntrySpace(7f);

                binding.pieChartStepsTaken.setEntryLabelColor(Color.BLACK);
                binding.pieChartStepsTaken.setEntryLabelTextSize(8f);

                PieDataSet dataSet2 = new PieDataSet(stepEntry, "steps items");

                dataSet2.setDrawIcons(false);

                dataSet2.setSliceSpace(0f);
                dataSet2.setIconsOffset(new MPPointF(0, 40));

                dataSet2.setSelectionShift(0f);

                stepChartColors.add(getResources().getColor(R.color.purple_200));
                stepChartColors.add(getResources().getColor(R.color.teal_200));
                dataSet2.setColors(stepChartColors);
                PieData pieData2 = new PieData(dataSet2);
                pieData2.setDrawValues(true);

                String lebals = "";
                LegendEntry[] legendEntries = new LegendEntry[dataSet2.getEntryCount()];
                for (int i = 0; i < dataSet2.getEntryCount(); i++) {
                    PieEntry entryForIndex = dataSet2.getEntryForIndex(i);
                    lebals = entryForIndex.getLabel() + "  " + entryForIndex.getValue();
                    LegendEntry legendEntry = new LegendEntry(lebals, Legend.LegendForm.CIRCLE, 10, 1f, null, stepChartColors.get(i));
                    legendEntries[i] = legendEntry;
                }
                legend.setCustom(legendEntries);

                binding.pieChartStepsTaken.setData(pieData2);
                binding.pieChartStepsTaken.invalidate();

                pieData.setValueTextColor(Color.BLACK);
                pieData.setValueTextSize(8f);

            }
        };
        activity.userViewMode.getAllUserData().observe(activity, userListUpdateObserver);



        binding.pieChartStepsTaken.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                Toast.makeText(getActivity(), "->value:" + pieEntry.getValue() + "->lable:" + pieEntry.getLabel(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePicker dateView, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        binding.startDateTextView.setText(currentDateString);
    }
}
