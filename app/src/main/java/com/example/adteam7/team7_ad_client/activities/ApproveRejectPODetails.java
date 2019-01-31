package com.example.adteam7.team7_ad_client.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ApproveRejectPODetails extends AppCompatActivity {

    //region Author Zan Tun Khine

    boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_podetails);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Purchase Orders");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final TextView pono = findViewById(R.id.poTextViewPono);
        final TextView date = findViewById(R.id.poTextViewDate);
        final TextView orderedby = findViewById(R.id.poTextViewOrderedBy);
        final TextView amount = findViewById(R.id.poTextViewAmount);


        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            isNew = false;
            String id = intent.getStringExtra("id");

            new AsyncTask<String, Void, List<PendingPODetails>>() {

                APIDataAgent dataAgent = new APIDataAgentImpl();

                @Override
                protected List<PendingPODetails> doInBackground(String... params) {
                    List<PendingPODetails> polist = dataAgent.GetPendingPODetails(params[0]);
                    //Log.i("list",polist.get(0).get("Description"));
                    return polist;
                }

                @TargetApi(Build.VERSION_CODES.O)
                @Override
                protected void onPostExecute(List<PendingPODetails> polist) {
                    pono.setText(polist.get(0).get("PONo"));

                    //To format the date
                    String poDate = polist.get(0).get("Date");
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
                    Date actualdate = null;
                    try {
                        actualdate = (Date) formatter.parse(poDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String poDate2 = newFormat.format(actualdate);

                    date.setText(poDate2);
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
                    Log.i("userID", SessionManager.getInstance().getUserid());
                }

            }.execute(id);
        }
    }


    public void ApproveReject(final View v) {

        final APIDataAgent dataAgent = new APIDataAgentImpl();

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            isNew = false;
            String id = intent.getStringExtra("id");

            PendingPO po = new PendingPO(id, "", "", "", "", "", SessionManager.getInstance().getUserid());
            new AsyncTask<PendingPO, Void, Void>() {

                @Override
                protected Void doInBackground(PendingPO... params) {
                    dataAgent.ApproveRejectPO(params[0], v.getId());
                    Log.i("Btn", Integer.toString(v.getId()));
                    Log.i("userID", SessionManager.getInstance().getUserid());

                    Intent homepage = new Intent(ApproveRejectPODetails.this, ApproveRejectPO.class);
                    startActivity(homepage);
                    return null;
                }
            }.execute(po);
            Toast.makeText(ApproveRejectPODetails.this, "Successful!", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion
}


