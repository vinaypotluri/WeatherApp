package com.example.android.navigationdrawerexample;


//        import example.com.retrofitexample.gitmode;

import com.example.android.navigationdrawerexample.model.gitmodel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface gitapi {
    /*@GET("/users/{user}")
    public void getFeed(@Path("user") String user,Callback<gitmode> response);*/

    @GET("/MapClick.php")
    public void getFeed(@Query("lat") String user, @Query("lon") String lon, @Query("FcstType") String FcstType, Callback<gitmodel> response);

}