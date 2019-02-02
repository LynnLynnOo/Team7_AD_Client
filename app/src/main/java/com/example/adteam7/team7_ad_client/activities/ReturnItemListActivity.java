package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ReturnItemAdapter;
import com.example.adteam7.team7_ad_client.data.ReturnItem;
import com.example.adteam7.team7_ad_client.data.ReturnItemPostBack;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.ArrayList;
import java.util.List;

public class ReturnItemListActivity extends AppCompatActivity {

    RecyclerView itemsrv;
    ReturnItemAdapter adapter;
    Button cancel, returnall;
    String disbno, disbotp, depname;
    APIDataAgent agent = new APIDataAgentImpl();
    List<ReturnItem> itemlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_item_list);

        itemsrv = findViewById(R.id.detailrv);
        itemsrv.setLayoutManager(new LinearLayoutManager(this));
        cancel = findViewById(R.id.cancel);
        returnall = findViewById(R.id.returnall);

        Intent i = getIntent();
        itemlist = (List<ReturnItem>) i.getSerializableExtra("LIST");

        Toast.makeText(this, "list size " + itemlist.size(), Toast.LENGTH_SHORT).show();
        adapter = new ReturnItemAdapter(ReturnItemListActivity.this, itemlist, true);

        itemsrv.setAdapter(adapter);
        adapter.setReqId(i.getStringExtra("reqId"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        returnall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return all
                List<ReturnItemPostBack> returnalllist = new ArrayList<>();
                for (ReturnItem c : itemlist
                ) {
                    ReturnItemPostBack item = new ReturnItemPostBack(i.getStringExtra("reqId"), c.getItemId(), c.getQuantity());
                    returnalllist.add(item);
                }

                new AsyncSetReturnLLItem().execute(returnalllist);
            }
        });
    }

    private class AsyncSetReturnLLItem extends AsyncTask<List<ReturnItemPostBack>, Void, String> {
        @Override
        protected String doInBackground(List<ReturnItemPostBack>... param) {
            return agent.returnAllItem(param[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            finish();
            Toast.makeText(ReturnItemListActivity.this, "Successfully returned all the item to the warehouse.", Toast.LENGTH_SHORT).show();
            //refresh the adapter

        }

    }

}
