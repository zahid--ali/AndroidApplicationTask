package com.test.androidapplicationtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.test.androidapplicationtask.adapters.CurrencyAdapter;
import com.test.androidapplicationtask.communication.Api;
import com.test.androidapplicationtask.models.APIResponseModel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<APIResponseModel> {
    ListView lvCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = (ListView) findViewById(R.id.lv_Currency);
        getLatestCurrencyExchangeRates();
    }

    private void getLatestCurrencyExchangeRates() {
        Call<APIResponseModel> call = Api.SERVICE.getLatestCurrencyExchangeRates();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<APIResponseModel> call, Response<APIResponseModel> response) {
        APIResponseModel responseModel = response.body();
        lvCurrency.setAdapter(new CurrencyAdapter(MainActivity.this, responseModel.getCurrencyList()));
    }

    @Override
    public void onFailure(Call<APIResponseModel> call, Throwable t) {
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getResources().getString(R.string.error_title_default))
                .setContentText(getResources().getString(R.string.error_title_message))
                .setConfirmText(getResources().getString(R.string.retry))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        getLatestCurrencyExchangeRates();
                    }
                })
                .show();
    }
}
