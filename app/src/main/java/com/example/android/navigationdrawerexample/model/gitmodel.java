package com.example.android.navigationdrawerexample.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class gitmodel{

    @Expose
    private String operationalMode;
    @Expose
    private String srsName;
    @Expose
    private String creationDate;
    @Expose
    private String creationDateLocal;
    @Expose
    private String productionCenter;
    @Expose
    private String credit;
    @Expose
    private String moreInformation;

//    @SerializedName("location")
    @Expose
    private location location;
    class location {
        private String region;
        private String latitude;
        private String longitude;
        private String elevation;
        private String wfo;
        private String timezone;
        private String areaDescription;
        private String radar;
        private String zone;
        private String county;
        private String firezone;
        private String metar;
    }
    @Expose
    private time time;
    class time {
        private String layoutKey;
        private ArrayList startPeriodName;
        private ArrayList startValidTime;
        private ArrayList tempLabel;
    }
    @Expose
    private data data;
    class data {
        private ArrayList temperature;
        private ArrayList pop;
        private ArrayList weather;
        private ArrayList iconlink;
        private ArrayList hazard;
        private ArrayList hazardUrl;
        private ArrayList text;
    }
    @Expose
    private currentobservation currentobservation;
    class currentobservation {
        private String id;
        private String name;
        private String ele;
        private String latitude;
        private String longitude;
        private String Date;
        private String Temp;
        private String Dewp;
        private String Relh;
        private String Winds;
        private String Windd;
        private String Gust;
        private String Weather;
        private String Weatherimage;
        private String Visibility;
        private String Altimeter;
        private String SLP;
        private String timezone;
        private String state;
        private String WindChill;
    }

    public String getoperationalMode() {return operationalMode;}
    public String getsrsName() {return srsName;}
    public String getcreationDate() {return creationDate;}
    public String getcreationDateLocal() {return creationDateLocal;}

//          location object
            public String getRegion() {return location.region;}
            public String getlatitude() {return location.latitude;}
            public String getLongitude() {return location.longitude;}
            public String getElevation() {return location.elevation;}
            public String getwfo() {return location.wfo;}
            public String getTimezone() {return location.timezone;}
            public String getareaDescription() {return location.areaDescription;}
            public String getRadar() {return location.radar;}
            public String getZone() {return location.zone;}
            public String getCounty() {return location.county;}
            public String getMetar() {return location.metar;}

//          time object
            public  String getlayoutkey() {return time.startPeriodName.toString();}
            public  ArrayList getstartPeriodName() {return time.startPeriodName;}
            public  ArrayList getstartValidTime() {return time.startValidTime;}
            public  ArrayList gettempLabel() {return time.tempLabel;}

//          data object
            public ArrayList getTemperature() {return data.temperature;}
            public  String getpop() {return data.pop.toString();}
            public  String getweather() {return data.weather.toString();}
            public  String geticonLink() {return data.iconlink.toString();}
            public  String gethazard() {return data.hazard.toString();}
            public  String gethazardUrl() {return data.hazardUrl.toString();}
            public  String gettext() {return data.text.toString();}

//          currentobservation object
            public  String getcurrentId() {return currentobservation.id;}
            public  String getcurrentLocationName() {return currentobservation.name;}
            public  String getcurrentEle() {return currentobservation.ele;}
            public  String getcurrentLat() {return currentobservation.latitude;}
            public  String getcurrentLon() {return currentobservation.longitude;}
            public  String getcurrentDate() {return currentobservation.Date;}
            public  String getcurrentTemp() {return currentobservation.Temp;}
            public  String getcurrentDewp() {return currentobservation.Dewp;}
            public  String getcurrentRelh() {return currentobservation.Relh;}
            public  String getcurrentWinds() {return currentobservation.Winds;}
            public  String getcurrentWindd() {return currentobservation.Windd;}
            public  String getcurrentGust() {return currentobservation.Gust;}
            public  String getcurrentWeather() {return currentobservation.Weather;}
            public  String getcurrentWeatherImage() {return currentobservation.Weatherimage;}
            public  String getcurrentVisibility() {return currentobservation.Visibility;}
            public  String getcurrentAltimeter() {return currentobservation.Altimeter;}
            public  String getcurrentSLP() {return currentobservation.SLP;}
            public  String getcurrentTimeZone() {return currentobservation.timezone;}
            public  String getcurrentState() {return currentobservation.state;}

}
