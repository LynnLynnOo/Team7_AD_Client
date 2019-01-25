package com.example.adteam7.team7_ad_client.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ItemListAdapter;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

public class DisbursementDetail extends AppCompatActivity {

    RecyclerView itemsrv;
    APIDataAgent agent=new APIDataAgentImpl();
    ItemListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);
        itemsrv=findViewById(R.id.detailrv);
        itemsrv.setLayoutManager(new LinearLayoutManager(this));

        String dno= getIntent().getStringExtra("disbno");
        if(dno!=null){
//            new AsyncGetDisbursementDetail().execute(dno);
        }


    }

//    private class AsyncGetDisbursementDetail extends AsyncTask<String, Void,List<DisbursementSationeryItem>> {
//        @Override
//        protected List<DisbursementSationeryItem> doInBackground(String... param) {
//            return agent.getDisbDetail(param[0]);
//        }
//
//        @Override
//        protected void onPostExecute(List<DisbursementSationeryItem> disbursementSationeryItems) {
//            super.onPostExecute(disbursementSationeryItems);
//            adapter=new ItemListAdapter(DisbursementDetail.this,disbursementSationeryItems);
//
//            itemsrv.setAdapter(adapter);
//
//        }
//    }
}
