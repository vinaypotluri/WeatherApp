package com.example.android.navigationdrawerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.navigationdrawerexample.model.gitmodel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

// mapview fragment
public class f2 extends Fragment {


    String API = "http://forecast.weather.gov";
    MapView mMapView;
    GoogleMap map;
    gitmodel gitmodel;
    gitapi git;
    RestAdapter restAdapter;
    ArrayList al;
    ArrayAdapter adapter;
    ListView listView;

    final String[][] arrays = {{"Seattle-Tacoma International Airport","47.44472","-122.31361"},
            {"San Francisco International Airport","37.61961","-122.36558"},
            {"Los Angeles International Airport","33.93806","-118.38889"},
            {"Kennedy International Airport","40.64","-73.76"},
            {"Detroit Metropolitan Wayne County Airport","42.23","-83.33"},
            {"Bismarck Municipal Airport ","46.78","-100.76"},
            {"Hollywood International Airport","26.07","-80.15"},
            {"Bergstrom International Airport ","30.19","-97.67"},
            {"Millard Airport","41.2","-96.11"},
            {"O'Hare International Airport","41.98","-87.9"}};

    final Float[] markers =
            {BitmapDescriptorFactory.HUE_ORANGE,
            BitmapDescriptorFactory.HUE_AZURE,
            BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_CYAN,
            BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_RED,
            BitmapDescriptorFactory.HUE_ROSE,
            BitmapDescriptorFactory.HUE_VIOLET,
            BitmapDescriptorFactory.HUE_YELLOW,
            BitmapDescriptorFactory.HUE_BLUE};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.f2, container, false);

        al = new ArrayList();

        restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        git = restAdapter.create(gitapi.class);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        map = mMapView.getMap();
        map.getUiSettings().setAllGesturesEnabled(false);
        map.getUiSettings().setRotateGesturesEnabled(false);

        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            mMapView.getMap().getUiSettings().setScrollGesturesEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(39.833333, -98.583333), 3);    // center of map
        map.animateCamera(cameraUpdate);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        for(int i=0;i<10;i++) {
            git.getFeed((arrays[i][1]), arrays[i][2], "json", new myCallback<gitmodel>(i,al ) {
                @Override
                public void success(gitmodel gitmodel, Response response) {
                    setMarkers(map, i, gitmodel);

                        Intent intent=new Intent("ListViewIntent");
                        intent.setAction("com.example.android.navigationdrawerexample.listen");
                        intent.putExtra("message", gitmodel.getcurrentLocationName() + "\n\n" + gitmodel.getcurrentWeather()+"\t\t\t\t\t\t\t\t"+ gitmodel.getcurrentTemp() + " \u2109");
                        getActivity().sendBroadcast(intent);

                }
                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void setMarkers(GoogleMap map,int i,gitmodel gitmodel)
    {
        map
                .addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(arrays[i][1]), Double.parseDouble(arrays[i][2])))
                        .title(arrays[i][0])
                        .snippet(gitmodel.getcurrentTemp() + " \u2109")
                        .icon((BitmapDescriptorFactory
                                .defaultMarker(markers[i]))));
    }

    private class myCallback <T> implements Callback<T> {
        // setting a constructor to read "i" & arraylist
        int i ;
        ArrayList a;
        public myCallback (int i,ArrayList a) {this.i = i; this.a = a;}
        @Override
        public void success(T t, Response response) {}
        @Override
        public void failure(RetrofitError error) {}
    }


}
