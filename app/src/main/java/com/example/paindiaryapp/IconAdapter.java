package com.example.paindiaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class IconAdapter extends ArrayAdapter {

    public IconAdapter(@NonNull Context context, int resourceId, ArrayList<CustomItem> list) {
        super(context, resourceId,list);
    }

    @NonNull
    @Override
    public View getView(int position,@NonNull View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout,parent,false);
        }
            CustomItem item = (CustomItem) getItem(position);
            ImageView icon1 = convertView.findViewById(R.id.icon);
            TextView text1 = convertView.findViewById(R.id.text);
        if(item!=null){
            icon1.setImageResource(item.getSpinnerItemImage());
            text1.setText(item.getSpinnerItemName());
        }
        return convertView;

    }
    public View getDropDownView(int position,@NonNull View convertView, ViewGroup parent){
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout,parent,false);}
            CustomItem dropDownItem = (CustomItem) getItem(position);
            ImageView dropDownIcon1 = convertView.findViewById(R.id.dropdown_icon);
            TextView dropDownText1 = convertView.findViewById(R.id.dropdown_text);
        if(dropDownItem !=null){
            dropDownIcon1.setImageResource(dropDownItem.getSpinnerItemImage());
            dropDownText1.setText(dropDownItem.getSpinnerItemName());
        }
        return convertView;
        }
}
