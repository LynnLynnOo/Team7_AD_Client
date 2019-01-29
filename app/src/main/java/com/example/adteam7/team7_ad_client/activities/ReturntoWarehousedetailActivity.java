/*
package com.example.adteam7.team7_ad_client.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ItemListAdapter;

import java.io.Serializable;
import java.util.List;

        public class ReturntoWarehousedetailActivity extends AppCompatActivity  {

            ListView lw;
          ItemListAdapter adapter;
            Button voiddisb,ackwge;
            String disbno, disbotp,depname;
            List<ReturntoWarehousedetailActivity> itemlist;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_returnto_warehousedetail);


                if (getIntent().hasExtra("disbno")) {
                    new com.example.adteam7.team7_ad_client.activities.DisbursementDetailActivity.AsyncGetDisbursementDetail().execute(disbno);
                }
                if (getIntent().hasExtra("disbotp")) {
                    disbotp = getIntent().getStringExtra("disbotp");
                }
                if (getIntent().hasExtra("depname")) {
                    depname= getIntent().getStringExtra("depname");
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
                Intent intent=new Intent(com.example.adteam7.team7_ad_client.activities.ReturntoWarehousedetailActivity.this,ReturntoWarehouseActivity.class);

                intent.putExtra("disbotp", disbotp);

                intent.putExtra("disblist", (Serializable) dlist);

                startActivity(intent);
            }

            private void onTapVoid() {
                final AlertDialog.Builder builder = new AlertDialog.Builder((com.example.adteam7.team7_ad_client.activities.DisbursementDetailActivity.this));
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

                            new com.example.adteam7.team7_ad_client.activities.DisbursementDetailActivity.AsyncSetDisbVoid().execute(disbno);

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

                    for(int i=0;i<itemlist.size();i++){
                        itemlist.get(i).setReceivedQty(itemlist.get(i).getQuantity());
                    }

                    adapter=new ItemListAdapter(com.example.adteam7.team7_ad_client.activities.DisbursementDetailActivity.this,disbursementSationeryItems,true);

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
                    Toast.makeText(com.example.adteam7.team7_ad_client.activities.DisbursementDetailActivity.this, "process return " + s, Toast.LENGTH_SHORT).show();

                    super.onPostExecute(s);
                }
            }
        }

    }
}
*/
