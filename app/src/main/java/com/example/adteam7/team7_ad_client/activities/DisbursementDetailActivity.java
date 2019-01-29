package com.example.adteam7.team7_ad_client.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ItemListAdapter;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.io.Serializable;
import java.util.List;

public class DisbursementDetailActivity extends AppCompatActivity  {

    RecyclerView itemsrv;
    APIDataAgent agent=new APIDataAgentImpl();
    ItemListAdapter adapter;
    Button voiddisb,ackwge;
    String disbno, disbotp, depname;
    List<DisbursementSationeryItem> itemlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);
        itemsrv=findViewById(R.id.detailrv);
        itemsrv.setLayoutManager(new LinearLayoutManager(this));
        voiddisb=findViewById(R.id.voiddisb);
        ackwge=findViewById(R.id.ackwge);
        disbno = getIntent().getStringExtra("disbno");

        if (getIntent().hasExtra("disbno")) {
            new AsyncGetDisbursementDetail().execute(disbno);
        }
        if (getIntent().hasExtra("disbotp")) {
            disbotp = getIntent().getStringExtra("disbotp");
        }
        if (getIntent().hasExtra("depname")) {
            depname = getIntent().getStringExtra("depname");
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(depname);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }



        voiddisb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTapVoid();
            }
        });

        ackwge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTapAck();
            }
        });

    }

    private void onTapAck() {

        List<DisbursementSationeryItem> dlist=adapter.getList();
        // Toast.makeText(this, "get from adapter "+dlist.get(0).getReceivedQty(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(DisbursementDetailActivity.this,DisbDetailAckActivity.class);

        intent.putExtra("disbotp", disbotp);

        intent.putExtra("disblist", (Serializable) dlist);

        startActivity(intent);
    }

    private void onTapVoid() {
        final AlertDialog.Builder builder = new AlertDialog.Builder((DisbursementDetailActivity.this));
        builder.setMessage("Are you sure want to process void disbursement?");

        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (itemlist != null) {

                    new AsyncSetDisbVoid().execute(disbno);

                }
                finish();

            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();


    }

    private class AsyncGetDisbursementDetail extends AsyncTask<String, Void,List<DisbursementSationeryItem>> {
        @Override
        protected List<DisbursementSationeryItem> doInBackground(String... param) {
            return agent.getDisbDetail(param[0]);
        }

        @Override
        protected void onPostExecute(List<DisbursementSationeryItem> disbursementSationeryItems) {
            super.onPostExecute(disbursementSationeryItems);
            itemlist=disbursementSationeryItems;

            for (int i = 0; i < itemlist.size(); i++) {
                itemlist.get(i).setReceivedQty(itemlist.get(i).getQuantity());
            }

            adapter=new ItemListAdapter(DisbursementDetailActivity.this,disbursementSationeryItems,true);

            itemsrv.setAdapter(adapter);

        }
    }

    private class AsyncSetDisbVoid extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            String s = agent.voidDisbursement(param[0]);

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(DisbursementDetailActivity.this, "process return " + s, Toast.LENGTH_SHORT).show();

            super.onPostExecute(s);
        }
    }
}
