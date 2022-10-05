package com.example.paindiaryapp.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.paindiaryapp.databinding.FragmentDatepickerBinding;
import com.example.paindiaryapp.databinding.FragmentMapBinding;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private FragmentDatepickerBinding binding;
    private int year;
    private int month;
    private int day;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getTargetFragment(), year, month, day);
    }
}
