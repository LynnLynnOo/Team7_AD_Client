package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

public class AcknowledgeDelivery extends AppCompatActivity {

    //region Author Zan Tun Khine

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledge_delivery);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Acknowledge Delivery");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        new AsyncTask<Void, Void, List<String>>() {

            APIDataAgent dataAgent = new APIDataAgentImpl();

            @Override
            protected List<String> doInBackground(Void... params) {
                return dataAgent.GetPendingPOList();
            }

            @Override
            protected void onPostExecute(List<String> listPos) {
                ListView list = (ListView) findViewById(R.id.ackpolist);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AcknowledgeDelivery.this,
                        R.layout.ackrow, R.id.ackpotextView1, listPos);

                list.setAdapter(adapter);

                //TextView emptyText = (TextView)findViewById(android.R.id.empty);
                //list.setEmptyView(emptyText);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedpo = (String) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(getApplicationContext(), AcknowledgeDeliveryDetails.class);
                        intent.putExtra("selectedpo", selectedpo);
                        Log.i("SelectedPO", selectedpo);
                        startActivity(intent);
                    }
                });

            }
        }.execute();


    }
    //endregion
}
