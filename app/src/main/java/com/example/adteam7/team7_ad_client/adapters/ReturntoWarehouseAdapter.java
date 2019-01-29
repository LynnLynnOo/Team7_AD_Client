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

import com.example.adteam7.team7_ad_client.data.ReturntoWarehouseApi;

import java.util.List;

public class ReturntoWarehouseAdapter extends ArrayAdapter<ReturntoWarehouseApi> {

        int resource;
        private List<ReturntoWarehouseApi> items;

        public ReturntoWarehouseAdapter(Context context, List<ReturntoWarehouseApi> items) {
            super(context, R.layout.return_row, items);
            this.resource = R.layout.return_row;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(resource, null);

            ReturntoWarehouseApi returntoware = items.get(position);

            if (returntoware != null) {
                TextView e = (TextView) v.findViewById(R.id.textView1);
                e.setText("Request ID :" + returntoware.getRequestId());
                e.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                e = (TextView) v.findViewById(R.id.textView2);
                e.setText("Department Name:" + returntoware.getDepartment());
                e.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

            }
            return v;
        }
    }


