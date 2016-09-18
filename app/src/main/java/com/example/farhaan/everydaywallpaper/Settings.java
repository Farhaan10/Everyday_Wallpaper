package com.example.farhaan.everydaywallpaper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * Created by Farhaan on 27-08-2016.
 */
public class Settings extends Fragment{

    private String[] daily_headers, daily_contents, bing_headers, bing_contents, pictures_headers, pictures_contents, storage_headers, storage_contents, theme_headers, theme_contents, translation_headers, translation_contents;
    private int[] daily_types, bing_types, pictures_types, storage_types, theme_types, translation_types;
    private ListView daily, bing, pictures, storage, theme, translation;
    SettingsAdapter adapter_daily, adapter_bing, adapter_pictures, adapter_storage, adapter_theme, adapter_translation;
    LinearLayout settings_layout;
    WallpaperManager wallpaperManager;
    PopupWindow popupWindow;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Alarm alarm;
    Bundle tempBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        settings_layout = (LinearLayout) view.findViewById(R.id.settings_layout);
        tempBundle = savedInstanceState;
        alarm = new Alarm();
        //settings_layout.bringToFront();
        //********temporary
        //Data.pictureLinks.add(0, "/az/hprichbg/rb/MoscowSkyline_EN-IN10373876477");

        daily_headers = getResources().getStringArray(R.array.settings_daily_headers);
        daily_contents = getResources().getStringArray(R.array.settings_daily_contents);
        daily_types = getResources().getIntArray(R.array.settings_daily_types);
        daily = (ListView) view.findViewById(R.id.settings_daily);
        adapter_daily = new SettingsAdapter(getActivity(), R.id.text_header, daily_headers, daily_contents, daily_types);
        daily.setAdapter(adapter_daily);
        //daily.bringToFront();
        //daily.setOnTouchListener(new LVscroll());
        setListViewHeightBasedOnChildren(daily, "daily");
        daily.setOnItemClickListener(new DailyItemClickListener());

        bing_headers = getResources().getStringArray(R.array.settings_bing_headers);
        bing_contents = getResources().getStringArray(R.array.settings_bing_contents);
        bing_types = getResources().getIntArray(R.array.settings_bing_types);
        bing = (ListView) view.findViewById(R.id.settings_bing);
        adapter_bing = new SettingsAdapter(getActivity(), R.id.text_header, bing_headers, bing_contents, bing_types);
        bing.setAdapter(adapter_bing);
        //bing.setOnTouchListener(new LVscroll());
        setListViewHeightBasedOnChildren(bing, "bing");

        pictures_headers = getResources().getStringArray(R.array.settings_pictures_headers);
        pictures_contents = getResources().getStringArray(R.array.settings_pictures_contents);
        pictures_types = getResources().getIntArray(R.array.settings_pictures_types);
        pictures = (ListView) view.findViewById(R.id.settings_pictures);
        adapter_pictures = new SettingsAdapter(getActivity(), R.id.text_header, pictures_headers, pictures_contents, pictures_types);
        pictures.setAdapter(adapter_pictures);
        //pictures.setOnTouchListener(new LVscroll());
        setListViewHeightBasedOnChildren(pictures, "pictures");

        storage_headers = getResources().getStringArray(R.array.settings_storage_headers);
        storage_contents = getResources().getStringArray(R.array.settings_storage_contents);
        storage_types = getResources().getIntArray(R.array.settings_storage_types);
        storage = (ListView) view.findViewById(R.id.settings_storage);
        adapter_storage = new SettingsAdapter(getActivity(), R.id.text_header, storage_headers, storage_contents, storage_types);
        storage.setAdapter(adapter_storage);
        //storage.setOnTouchListener(new LVscroll());
        setListViewHeightBasedOnChildren(storage, "storage");

        theme_headers = getResources().getStringArray(R.array.settings_theme_headers);
        theme_contents = getResources().getStringArray(R.array.settings_theme_contents);
        theme_types = getResources().getIntArray(R.array.settings_theme_types);
        theme = (ListView) view.findViewById(R.id.settings_theme);
        adapter_theme = new SettingsAdapter(getActivity(), R.id.text_header, theme_headers, theme_contents, theme_types);
        theme.setAdapter(adapter_theme);
        //theme.setOnTouchListener(new LVscroll());
        setListViewHeightBasedOnChildren(theme, "theme");

        translation_headers = getResources().getStringArray(R.array.settings_translation_headers);
        translation_contents = getResources().getStringArray(R.array.settings_translation_contents);
        translation_types = getResources().getIntArray(R.array.settings_translation_types);
        translation = (ListView) view.findViewById(R.id.settings_translation);
        adapter_translation = new SettingsAdapter(getActivity(), R.id.text_header, translation_headers, translation_contents, translation_types);
        translation.setAdapter(adapter_translation);
        //translation.setOnTouchListener(new LVscroll());
        setListViewHeightBasedOnChildren(translation, "translation");

        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, String listName) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if(listName.equals("daily") || listName.equals("pictures")) {
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 50;
        } else {
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        }
        System.out.println("height set = " + params.height);
        listView.setLayoutParams(params);
    }

    private class DailyItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("Inside daily list at " + position);
            if (position == 0) {
                boolean switchStatus = adapter_daily.switchStatus(position);
                adapter_daily.toggleSwitch(position);
                if (!switchStatus) {
                    System.out.println("Switch on");
                    adapter_daily.enableItem(1);
                    adapter_daily.enableItem(2);
                    daily.getChildAt(1).setEnabled(false);
                    daily.getChildAt(2).setEnabled(false);
                    daily.getChildAt(1).setClickable(false);
                    daily.getChildAt(2).setClickable(false);
                    wallpaperManager = WallpaperManager.getInstance(getActivity().getApplicationContext());
                    try{
                        new DownloadImageTask(wallpaperManager).execute("http://bing.com" + Data.pictureLinks.get(0) +"_1920x1080.jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    alarm.setAlarm(getContext());
                } else {
                    System.out.println("Switch off");
                    adapter_daily.disableItem(1);
                    adapter_daily.disableItem(2);
                    daily.getChildAt(1).setEnabled(true);
                    daily.getChildAt(2).setEnabled(true);
                    daily.getChildAt(1).setClickable(true);
                    daily.getChildAt(2).setClickable(true);
                    alarm.cancelAlarm(getContext());
                }
            } else if(position == 1) {
                showTimePicker(view);
            } else if (position == 2){
                boolean saveSwitchStatus = adapter_daily.switchStatus(position);
                adapter_daily.toggleSwitch(position);
                if(!saveSwitchStatus){
                    Data.savePicture = 1;
                } else {
                    Data.savePicture = 0;
                }
            }
        }
    }

    public void showTimePicker(View pickerView) {
        View popupView = getLayoutInflater(tempBundle).inflate(R.layout.popup_wallpaper, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(pickerView, Gravity.CENTER, 0, 0);
        final TimePicker timePicker= (TimePicker) popupView.findViewById(R.id.timePicker);
        Button save = (Button) popupView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.changeHour = timePicker.getCurrentHour();
                Data.changeMinute = timePicker.getCurrentMinute();
                popupWindow.dismiss();
                alarm.cancelAlarm(getContext());
                alarm.setAlarm(getContext());
            }
        });
    }

    /*private class LVscroll implements ListView.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            v.onTouchEvent(event);
            return true;
        }
    }*/

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        WallpaperManager tempManager;

        public DownloadImageTask(WallpaperManager wallpaperManager) {
            this.tempManager = wallpaperManager;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            try {
                tempManager.setBitmap(result);
                System.out.println("Wallpaper changed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
