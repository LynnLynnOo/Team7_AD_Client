package com.example.adteam7.team7_ad_client.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ItemListAdapter;
import com.example.adteam7.team7_ad_client.adapters.ReturnItemAdapter;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.data.ReturnItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

public class ReturnItemListActivity extends AppCompatActivity {

    RecyclerView itemsrv;
    ReturnItemAdapter adapter;
    Button cancel,returnall;
    String disbno, disbotp, depname;

    List<ReturnItem> itemlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_item_list);

        itemsrv=findViewById(R.id.detailrv);
        itemsrv.setLayoutManager(new LinearLayoutManager(this));
        cancel=findViewById(R.id.cancel);
        returnall=findViewById(R.id.returnall);



        adapter=new ReturnItemAdapter(ReturnItemListActivity.this,itemlist,true);

        itemsrv.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        returnall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
