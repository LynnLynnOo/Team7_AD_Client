package com.example.adteam7.team7_ad_client.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.adteam7.team7_ad_client.R;

import java.util.ArrayList;
import java.util.List;

public class AdjustmentDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment_detail);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.adjustment_category_row,R.id.categoryText,list);

    }
}
