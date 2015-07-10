package com.example.android.navigationdrawerexample;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.navigationdrawerexample.model.gitmodel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

// current weather fragment
public class f1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
                                            LocationListener,
                                            GoogleApiClient.ConnectionCallbacks,
                                            GoogleApiClient.OnConnectionFailedListener {


    String API = "http://forecast.weather.gov";
    TextView currentloc,currenttemp,CurrentWeatherDesc;
    TextView humidity,dewpoint,winds,visibility,barometer,timezone;
    TwoWayView lvTest;
    SwipeRefreshLayout swipeLayout;
    ProgressBar progressBar;
    LinearLayout l1,l2;
    ImageView CurrentWeatherImage;

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.f1, container,false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        l1 = (LinearLayout) getActivity().findViewById(R.id.linearlayout1); l1.setVisibility(View.INVISIBLE);
        l2 = (LinearLayout) getActivity().findViewById(R.id.linearlayout2); l2.setVisibility(View.INVISIBLE);
        // other info
        currentloc = (TextView) getView().findViewById(R.id.CurrentLocationName);
        currenttemp = (TextView) getView().findViewById(R.id.CurrentTemp);
        CurrentWeatherDesc = (TextView)getView().findViewById(R.id.CurrentWeatherDesc);
        humidity = (TextView)getView().findViewById(R.id.humidity);
        dewpoint = (TextView)getView().findViewById(R.id.dewpoint);
        winds = (TextView)getView().findViewById(R.id.winds);
        visibility = (TextView)getView().findViewById(R.id.visibility);
        barometer = (TextView)getView().findViewById(R.id.barometer);
        timezone = (TextView)getView().findViewById(R.id.timezone);
        lvTest = (TwoWayView)getView().findViewById(R.id.lvItems);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.loading_spinner);
        CurrentWeatherImage = (ImageView)getActivity().findViewById(R.id.CurrentWeatherImage);

        swipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_red_light);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        createLocationRequest();

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    void updateview(String[] latlng)
    {
        if(isNetworkConnected()) {      // checks for connectivity and sets the git adapter

            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
            gitapi git = restAdapter.create(gitapi.class);
            git.getFeed(latlng[0], latlng[1], "json", new Callback<gitmodel>()
            {
                @Override
                public void success(gitmodel gitmodel, Response response) {
                    SetCurrentWeather(gitmodel);
                }
                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else
            Toast.makeText(getActivity(), "network unavailable", Toast.LENGTH_LONG).show();
    }

    void SetCurrentWeather(gitmodel gitmodel)
    {   // sets the current weather
        String weather = gitmodel.getcurrentWeather();

        l1.setVisibility(View.VISIBLE);
        l2.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        // set current weather
        currentloc.setText(gitmodel.getcurrentLocationName()+" "+gitmodel.getcurrentState());
        currenttemp.setText(gitmodel.getcurrentTemp() + " \u2109");
        CurrentWeatherDesc.setText(gitmodel.getcurrentWeather());
        humidity.setText(gitmodel.getcurrentRelh()+" %");
        dewpoint.setText(gitmodel.getcurrentDewp() + " \u2109");
        winds.setText(gitmodel.getcurrentWinds() + " mph");
        visibility.setText(gitmodel.getcurrentVisibility()+" mi");
        barometer.setText(gitmodel.getcurrentSLP()+" in");
        timezone.setText(gitmodel.getcurrentTimeZone());

        switch(weather)
        {
            case "Rain":
            case "Light Rain": CurrentWeatherImage.setBackgroundResource(R.drawable.rain);
                break;
            case "Cloudy": case "Clouds": case "Overcast": case "Mostly Cloudy":
            case "Partly Cloudy": CurrentWeatherImage.setBackgroundResource(R.drawable.cloudy);
                break;
            case "Snow Flakes": case "Heavy Snow":
            case "Snow": CurrentWeatherImage.setBackgroundResource(R.drawable.sunny);
                break;
            case "Thunder Storm":
            case "Thunder": CurrentWeatherImage.setBackgroundResource(R.drawable.snow);
                break;
            default: CurrentWeatherImage.setBackgroundResource(R.drawable.fair);
        }

        ArrayList item1 = new ArrayList();
        ArrayList item2 = new ArrayList();
        ArrayList item3 = new ArrayList();

        for(int i=0;i<gitmodel.getstartPeriodName().size();i++){
            item1.add(gitmodel.getstartPeriodName().get(i));       // (day/night)
            item2.add(gitmodel.gettempLabel().get(i));             // (high /low)
            item3.add(gitmodel.getTemperature().get(i)+" \u2109"); // temperature in Fahrenheit
        }
        CustomArrayAdapter aItems = new CustomArrayAdapter(getActivity(), item1,item2,item3);
        TwoWayView lvTest = (TwoWayView) getActivity().findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);

    }

    private boolean isNetworkConnected() {
        // checks for internet connectivity
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

    @Override
    public void onRefresh() {
        // pull to refresh
        Toast.makeText(getActivity(),"Refreshing data", Toast.LENGTH_LONG).show();
        updateview(getLocationData());      // pulls location values & updates the view
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 5000);
    }


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();             // gets location values
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(),"disconnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getActivity(),connectionResult.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateview(getLocationData());
        stopLocationUpdates();          // stops the locations updates once received
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public String[] getLocationData() {
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
//            Toast.makeText(getActivity(), lat + " " + lng, Toast.LENGTH_LONG).show();
            return new String[]{ lat, lng };
        } else {
            Toast.makeText(getActivity(),"unable to retrieve", Toast.LENGTH_SHORT).show();
            return  null;
        }

    }
}
