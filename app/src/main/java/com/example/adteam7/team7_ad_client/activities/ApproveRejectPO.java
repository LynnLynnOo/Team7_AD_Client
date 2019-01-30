package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.PendingPO;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;


public class ApproveRejectPO extends AppCompatActivity {

    //region Author Zan Tun Khine

    APIDataAgent dataAgent = new APIDataAgentImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_po);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Purchase Orders");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the list of Pending POs

        new AsyncTask<Void, Void, List<PendingPO>>() {

            @Override
            protected List<PendingPO> doInBackground(Void... params) {
                List<PendingPO> polist = dataAgent.GetPendingPO();
                //Log.i("list", polist.get(0).get("PONo"));
                return polist;
            }

            @Override
            protected void onPostExecute(List<PendingPO> polist) {
                ListView list = (ListView) findViewById(R.id.polist);
                SimpleAdapter adapter = new SimpleAdapter(ApproveRejectPO.this, polist,
                        R.layout.porow,
                        new String[]{"PONo", "Date", "OrderedBy"},
                        new int[]{R.id.potextView1, R.id.potextView2, R.id.potextView3});
                list.setAdapter(adapter);
                //Log.i("list",polist.get(1).getPONo());

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PendingPO selected = (PendingPO) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(getApplicationContext(), ApproveRejectPODetails.class);
                        intent.putExtra("id", selected.get("PONo"));
                        startActivity(intent);
                    }
                });
            }
        }.execute();
    }
    //endregion
}




