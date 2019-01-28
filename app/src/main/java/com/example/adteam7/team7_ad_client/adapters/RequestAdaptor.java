package com.example.adteam7.team7_ad_client.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;

import java.util.List;

public class RequestAdaptor extends ArrayAdapter<StationeryRequestApiModel> {

    int resource;
    private List<StationeryRequestApiModel> items;

    public RequestAdaptor(Context context, List<StationeryRequestApiModel> items) {
        super(context, R.layout.request_detail_row, items);
        this.resource = R.layout.request_detail_row;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        StationeryRequestApiModel req = items.get(position);
        if (req != null) {
            TextView e = (TextView) v.findViewById(R.id.Req_textView1);
            e.setText("Request ID :" + req.getRequestId());
            e = (TextView) v.findViewById(R.id.Req_textView2);
            e.setText("Requested By :" + req.getRequestedBy());
            e = (TextView) v.findViewById(R.id.Req_textView3);
            e.setText("Status :" + req.getRequestDate());
            e = (TextView) v.findViewById(R.id.Req_textView4);
            e.setText("Date :" + req.getStatus());
        }
        return v;
    }
}
