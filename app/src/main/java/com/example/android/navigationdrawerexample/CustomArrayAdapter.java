package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/* this is a custom array adapter for the listview */

public class CustomArrayAdapter extends StableArrayAdapter {

    private final Activity context;
    ArrayList day,label,value;

    public CustomArrayAdapter(Activity context, ArrayList item1, ArrayList item2, ArrayList item3) {
        super(context, R.layout.custom_list_format,item1);

        this.context=context;
        this.day=item1;
        this.label=item2;
        this.value=item3;
    }

@Override
    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.custom_list_format, null, true);

        TextView tvDay = (TextView) rowView.findViewById(R.id.day);
        TextView tvLabel = (TextView) rowView.findViewById(R.id.label);
        TextView tvValue = (TextView) rowView.findViewById(R.id.value);

        tvDay.setText(day.get(position).toString());
        tvLabel.setText(label.get(position).toString());
        tvValue.setText(value.get(position).toString());
        
        return rowView;
    }

}
