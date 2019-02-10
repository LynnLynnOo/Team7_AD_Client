package com.example.adteam7.team7_ad_client.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.model.RequestTransactionDetailApiModel;

import java.util.List;

public class RequestDetailAdaptor extends ArrayAdapter<RequestTransactionDetailApiModel> {

    int resource;
    private List<RequestTransactionDetailApiModel> items;

    public RequestDetailAdaptor(Context context, List<RequestTransactionDetailApiModel> items) {
        super(context, R.layout.request_item_row, items);
        this.resource = R.layout.request_item_row;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        RequestTransactionDetailApiModel req = items.get(position);

        if (req != null) {
            TextView e = (TextView) v.findViewById(R.id.ReqT_textView1);
            e.setText("Item ID :" + req.getItemId());
            e.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            e = (TextView) v.findViewById(R.id.ReqT_textView2);
            e.setText("Quantity :" + req.getQuantity());
            e.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            e = (TextView) v.findViewById(R.id.ReqT_textView3);
            e.setText("Unit Price :" + req.getUnitPrice());
            e.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }
        return v;
    }
}
