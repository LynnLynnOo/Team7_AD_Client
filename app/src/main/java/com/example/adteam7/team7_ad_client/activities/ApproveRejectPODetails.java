package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.PendingPO;
import com.example.adteam7.team7_ad_client.data.PendingPODetails;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.network.JSONParser;

import java.util.List;

public class ApproveRejectPODetails extends AppCompatActivity {

    boolean isNew = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_podetails);
        final TextView pono = findViewById(R.id.poTextViewPono);
        final TextView date = findViewById(R.id.poTextViewDate);
        final TextView orderedby = findViewById(R.id.poTextViewOrderedBy);
        final TextView amount = findViewById(R.id.poTextViewAmount);


        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            isNew = false;
            String id = intent.getStringExtra("id");

            new AsyncTask<String, Void, List<PendingPODetails>>() {

                @Override
                protected List<PendingPODetails> doInBackground(String... params) {
                    List<PendingPODetails> polist = PendingPODetails.GetPendingPODetails(params[0]);
                    //Log.i("list",polist.get(0).get("Description"));
                    return polist;
                }

                @Override
                protected void onPostExecute(List<PendingPODetails> polist) {
                    pono.setText(polist.get(0).get("PONo"));
                    date.setText(polist.get(0).get("Date"));
                    orderedby.setText(polist.get(0).get("OrderedBy"));
                    amount.setText(polist.get(0).get("Amount"));


                    ListView list = (ListView) findViewById(R.id.polistDetails);
                    SimpleAdapter adapter = new SimpleAdapter(ApproveRejectPODetails.this, polist,
                            R.layout.porowdetails,
                            new String[]{"Description", "UnitPrice", "Quantity", "UnitAmount"},
                            //,"PONo","Date","OrderedBy","Amount"},
                            new int[]{R.id.poTextViewDescription, R.id.poTextViewUnitPrice, R.id.poTextViewQty, R.id.poTextViewTotalAmount});
                    //,R.id.poTextViewPono,R.id.poTextViewDate,R.id.poTextViewOrderedBy,R.id.poTextViewAmount});
                    list.setAdapter(adapter);
                    Log.i("list", SessionManager.getInstance().getUserid());
                }

            }.execute(id);
        }
    }


    public void ApproveReject(final View v) {

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            isNew = false;
            String id = intent.getStringExtra("id");


            PendingPO po = new PendingPO(id, "", "", "", "", "", SessionManager.getInstance().getUserid());
            new AsyncTask<PendingPO, Void, Void>() {

                @Override
                protected Void doInBackground(PendingPO... params) {
                    PendingPO.ApproveRejectPO(params[0], v.getId());
                    //Log.i("Btn", Integer.toString(v.getId()));

                    Intent homepage = new Intent(ApproveRejectPODetails.this, ApproveRejectPO.class);
                    startActivity(homepage);
                    return null;
                }
            }.execute(po);
            Toast.makeText(ApproveRejectPODetails.this, "Successful!", Toast.LENGTH_SHORT).show();
        }
    }

}


