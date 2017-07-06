package com.test.androidapplicationtask.communication;

import com.test.androidapplicationtask.models.APIResponseModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Jarvis on 7/6/2017.
 */

public interface Api {
    Api SERVICE= new Retrofit.Builder()
            .baseUrl("http://api.fixer.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api.class);

    @GET("latest")
    Call<APIResponseModel> getLatestCurrencyExchangeRates();
}
