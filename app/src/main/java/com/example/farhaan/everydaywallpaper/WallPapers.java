package com.example.farhaan.everydaywallpaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import network.Response.Images;
import network.Response.Result;
import network.RetrofitProvider;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Farhaan on 27-08-2016.
 */
public class WallPapers extends Fragment{

    /*private Integer[] pictures = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8
    };
    private String[] descriptions = {
            "images", "images", "images", "images", "images", "images", "images", "images"
    };*/
    private ListView wallpaperList;
    WallpaperAdapter adapter;
    /*List<String> pictureLinks = new ArrayList<String>();
    List<String> dates = new ArrayList<String>();
    List<String> pictureDescriptions = new ArrayList<String>();*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        wallpaperList = (ListView) view.findViewById(R.id.wallpaper_list);

        RetrofitProvider.getInstance().provideApi().getResult().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Result result) {
                        List<Images> images = result.getImages();
                        for (int i=0;i<images.size();i++){
                            Data.pictureLinks.add(images.get(i).getUrlbase());
                            Data.dates.add(images.get(i).getStartdate());
                            Data.pictureDescriptions.add(images.get(i).getCopyright());
                            System.out.println(Data.pictureDescriptions.get(i));
                        }
                        adapter = new WallpaperAdapter(getActivity(), 8, Data.dates, Data.pictureLinks, Data.pictureDescriptions);
                        wallpaperList.setAdapter(adapter);
                    }
                });

        return view;
    }

}
