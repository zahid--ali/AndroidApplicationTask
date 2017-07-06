package com.test.androidapplicationtask.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.androidapplicationtask.R;
import com.test.androidapplicationtask.adapters.CurrencyAdapter;
import com.test.androidapplicationtask.communication.Api;
import com.test.androidapplicationtask.models.APIResponseModel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<APIResponseModel> {
    ListView lvCurrency;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCurrency = (ListView) findViewById(R.id.lv_Currency);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        getLatestCurrencyExchangeRates();
    }

    private void getLatestCurrencyExchangeRates() {
        Call<APIResponseModel> call = Api.SERVICE.getLatestCurrencyExchangeRates();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<APIResponseModel> call, Response<APIResponseModel> response) {
        progressDialog.dismiss();
        APIResponseModel responseModel = response.body();
        lvCurrency.setAdapter(new CurrencyAdapter(MainActivity.this, responseModel.getCurrencyList()));
        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Base Currency: " + responseModel.getBase())
                .setContentText("Last update date: " + responseModel.getDate())
                .setConfirmText(getResources().getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
        submitResponseToFirebase(responseModel);
    }

    private void submitResponseToFirebase(APIResponseModel responseModel) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("base").setValue(responseModel.getBase());
        mDatabase.child("date").setValue(responseModel.getDate());
        mDatabase.child("rates").setValue(responseModel.getCurrencyList());
    }

    @Override
    public void onFailure(Call<APIResponseModel> call, Throwable t) {
        progressDialog.dismiss();
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
