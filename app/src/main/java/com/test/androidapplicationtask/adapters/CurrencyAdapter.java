package com.test.androidapplicationtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.androidapplicationtask.R;
import com.test.androidapplicationtask.models.CurrencyModel;

import java.util.List;

/**
 * Created by Jarvis on 7/6/2017.
 */

public class CurrencyAdapter extends BaseAdapter {
    private Context context;
    private List<CurrencyModel> currencyList;

    public CurrencyAdapter(Context context, List<CurrencyModel> currencyList) {
        this.context = context;
        this.currencyList = currencyList;
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int i) {
        return currencyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View currencyItemView = layoutInflater.inflate(R.layout.currency_item, null);
        TextView tvName = (TextView) currencyItemView.findViewById(R.id.tv_Name);
        TextView tvRate = (TextView) currencyItemView.findViewById(R.id.tv_Rate);
        final CurrencyModel c = currencyList.get(i);
        tvName.setText(c.getName());
        tvRate.setText(Double.toString(c.getRate()));
        return currencyItemView;
    }
}
