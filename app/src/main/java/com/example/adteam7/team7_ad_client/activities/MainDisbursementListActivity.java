package com.example.adteam7.team7_ad_client.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.MainDisburAdapter;
import com.example.adteam7.team7_ad_client.data.DelegateDepHeadApiModel;
import com.example.adteam7.team7_ad_client.data.Employee;

import java.util.ArrayList;

public class MainDisbursementListActivity extends AppCompatActivity {
    RecyclerView rv;
    MainDisburAdapter rvAdapter;
    ArrayList<Employee> items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_disbursement_list);

        rv=findViewById(R.id.disbursementrv);
        rv.setLayoutManager(new LinearLayoutManager(this));


///get data from api

        rvAdapter=new MainDisburAdapter(items);

        rv.setAdapter(rvAdapter);
    }


   /* private class AsyncCallerGet extends AsyncTask<Void, Void, DelegateDepHeadApiModel> {



    }*/
}
