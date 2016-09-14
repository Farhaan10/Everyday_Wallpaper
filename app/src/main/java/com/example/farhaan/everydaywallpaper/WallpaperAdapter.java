package com.example.farhaan.everydaywallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Farhaan on 04-09-2016.
 */
public class WallpaperAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final List<String> dates;
    private final List<String> pictures;
    private final List<String> descriptions;
    //PopupWindow popupWindow;

    public WallpaperAdapter(Activity context, int resource, List<String> dates, List<String> pictures, List<String> descriptions){
        super(context, resource, dates);
        this.context = context;
        this.dates = dates;
        this.pictures = pictures;
        this.descriptions = descriptions;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.wallpaper_item, null, true);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        ImageView picture = (ImageView) rowView.findViewById(R.id.myImage);
        TextView description = (TextView) rowView.findViewById(R.id.imageDescription);
        date.setText(dates.get(position));
        new DownloadImageTask(picture).execute("http://bing.com" + pictures.get(position) +"_480x360.jpg");
        //picture.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), pictures[position], 50, 50));
        //picture.setBackgroundResource(pictures[position]);
        picture.setScaleType(ImageView.ScaleType.FIT_XY);
        picture.setOnClickListener(new ImageListener(position));
        description.setText(descriptions.get(position));
        return rowView;
    }

    private class ImageListener implements ImageView.OnClickListener{

        int position;
        public ImageListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            System.out.println("Image Clicked");
            //initiatePopupWindow(position);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            try{
                new DownloadWallpaperTask(wallpaperManager).execute("http://bing.com" + Data.pictureLinks.get(position) +"_1920x1080.jpg");
                Toast toast = Toast.makeText(context, "Wallpaper set to this", Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*private void initiatePopupWindow(final int number) {
        System.out.println("Popup created");
        LayoutInflater popupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = popupInflater.inflate(R.layout.popup_wallpaper, null);
        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
        TextView setWallpaper = (TextView) layout.findViewById(R.id.setWallpaper);
        TextView downloadWallpaper = (TextView) layout.findViewById(R.id.downloadWallpaper);
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                try{
                    new DownloadWallpaperTask(wallpaperManager).execute("http://bing.com" + Data.pictureLinks.get(number) +"_1920x1080.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    /*public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }*/

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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
            bmImage.setImageBitmap(result);
        }
    }

    private class DownloadWallpaperTask extends AsyncTask<String, Void, Bitmap> {
        WallpaperManager wallpaperManager;

        public DownloadWallpaperTask(WallpaperManager wallpaperManager) {
            this.wallpaperManager = wallpaperManager;
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
                wallpaperManager.setBitmap(result);
                System.out.println("Wallpaper changed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
