package com.example.farhaan.everydaywallpaper;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Farhaan on 27-08-2016.
 */
public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] drawer_items;
    private final Integer[] drawer_icons;

    public CustomList(Activity context, String[] drawer_items, Integer[] drawer_icons){
        super(context, R.layout.navigation_list_items, drawer_items);
        this.context = context;
        this.drawer_items = drawer_items;
        this.drawer_icons = drawer_icons;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.navigation_list_items, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_content);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.navigation_list_icon);
        txtTitle.setText(drawer_items[position]);
        imageView.setImageResource(drawer_icons[position]);
        return rowView;
    }
}
