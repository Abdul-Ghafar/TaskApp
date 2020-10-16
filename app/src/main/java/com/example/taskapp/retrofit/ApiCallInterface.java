package com.example.taskapp.retrofit;



import com.example.taskapp.model.FoodData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCallInterface {

    @GET("fooddata.json")
    Call<List<FoodData>> getAllData();


    // we can make get /post/  calls nnotation for any json data with base url

}
