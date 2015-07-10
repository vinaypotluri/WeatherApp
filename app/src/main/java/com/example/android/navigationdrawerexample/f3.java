package com.example.android.navigationdrawerexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.android.navigationdrawerexample.model.gitmodel;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

// listview fragment
public class f3 extends Fragment {

    private static f3 ins;
    String API = "http://forecast.weather.gov";
    public DynamicListView listView;
    public StableArrayAdapter adapter;
    public ArrayList<String> al,al1;
    myreceiver receiver;
    gitmodel gitmodel;
    gitapi git;
    RestAdapter restAdapter;

    ArrayList airports;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.f3, container,false);
        restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        git = restAdapter.create(gitapi.class);

        al = new ArrayList<String>();
        al1 = new ArrayList();
        airports = new ArrayList();
        airports.add("Seattle-Tacoma International Airport");
        airports.add("San Francisco International Airport");
        airports.add("Los Angeles International Airport");
        airports.add("Kennedy International Airport");
        airports.add("Detroit Metropolitan Wayne County Airport");

        return view;
    }

    public static f3 getInstace(){
        return ins;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ins = this;
    }

    public void updateTheTextView(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                al.add(s);
            }
        });

        // sets the listview after getting all the data on arraylist
        listView = (DynamicListView) getActivity().findViewById(R.id.listview);
        adapter = new StableArrayAdapter(getActivity(), R.layout.text_view, al);
        listView.setCheeseList(al);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    private class myCallback <T> implements Callback<T> {
        // setting a constructor to read "i"
        int i ;
        ArrayList a;
        public myCallback (int i,ArrayList a) {this.i = i; this.a = a;}
        @Override
        public void success(T t, Response response) {}
        @Override
        public void failure(RetrofitError error) {}
    }
}
