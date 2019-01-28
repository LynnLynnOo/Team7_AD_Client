package com.example.adteam7.team7_ad_client.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.MainDisburAdapter;
import com.example.adteam7.team7_ad_client.data.Disbursement;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.data.Employee;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kay Thi Swe Tun
 **/

public class MainDisbursementListActivity extends AppCompatActivity  {
    APIDataAgent agent = new APIDataAgentImpl();

    RecyclerView rv;
    MainDisburAdapter rvAdapter;
    List<Disbursement> items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_disbursement_list);

        rv=findViewById(R.id.disbursementrv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ///get data from api
     new AsyncGetDisbursement().execute();


    }


    private class AsyncGetDisbursement extends AsyncTask<Void, Void,List<Disbursement>> {


        @Override
        protected List<Disbursement> doInBackground(Void... voids) {

           return agent.getDisbbyDept();
        }

       @Override
       protected void onPostExecute(List<Disbursement> disbursement) {

            if(disbursement != null){
                rvAdapter=new MainDisburAdapter(MainDisbursementListActivity.this,disbursement);

                rv.setAdapter(rvAdapter);

           }

        }
   }
}
