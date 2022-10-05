package com.example.paindiaryapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paindiaryapp.CustomItem;
import com.example.paindiaryapp.R;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<CustomItem> {


    Context context;

    public CustomListAdapter(Context context, int resourceId, //resourceId=your layout
                                 List<CustomItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        CustomItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_dropdown_layout, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.dropdown_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.dropdown_icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        String name = rowItem.getSpinnerItemName();
        int imageId = rowItem.getSpinnerItemImage();
        holder.txtTitle.setText(name);
        holder.imageView.setImageResource(imageId);

        return convertView;
    }
}