package com.example.farhaan.everydaywallpaper;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Farhaan on 28-08-2016.
 */
public class SettingsAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] headers;
    private final String[] contents;
    private final int[] type;
    Switch[] aSwitch = new Switch[5];
    TextView[] header = new TextView[5];
    TextView[] content = new TextView[5];
    private boolean status;

    public SettingsAdapter(Activity context, int resource, String[] headers, String[] contents, int[] type){
        super(context, resource, headers);
        this.context = context;
        this.headers = headers;
        this.contents = contents;
        this.type = type;
    }

    public void toggleSwitch(int position){
        aSwitch[position].toggle();
    }

    public void disableItem(int position){
        header[position].setTextColor(Color.parseColor("#999999"));
        content[position].setTextColor(Color.parseColor("#999999"));
    }

    public void enableItem(int position) {
        header[position].setTextColor(Color.parseColor("#FFFFFF"));
        content[position].setTextColor(Color.parseColor("#AAAAAA"));
    }

    public boolean switchStatus(int position) {
        aSwitch[position].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                status = isChecked;
            }
        });
        return status;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView;
        if (type[position] == 0){
            rowView = inflater.inflate(R.layout.settings_list_no_button, null, true);
        } else {
            rowView = inflater.inflate(R.layout.settings_list_button, null, true);
            aSwitch[position] = (Switch) rowView.findViewById(R.id.mySwitch);
            aSwitch[position].setClickable(false);
            /*rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Inside switch");
                    aSwitch[position].toggle();
                }
            });*/
        }
        header[position] = (TextView) rowView.findViewById(R.id.text_header);
        header[position].setText(headers[position]);
        content[position] = (TextView) rowView.findViewById(R.id.text_content);
        content[position].setText(contents[position]);
        return rowView;
    }

}
