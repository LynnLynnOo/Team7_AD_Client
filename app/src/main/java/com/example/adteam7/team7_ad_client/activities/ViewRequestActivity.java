package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.RequestAdaptor;
import com.example.adteam7.team7_ad_client.data.StationeryRequest;

import java.util.List;

public class ViewRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        new AsyncTask<Void, Void, List<StationeryRequest>>() {
            @Override
            protected List<StationeryRequest> doInBackground(Void... params) {
                return StationeryRequest.ReadStationeryRequest2();
            }

            @Override
            protected void onPostExecute(List<StationeryRequest> result) {
                RequestAdaptor adapter = new RequestAdaptor(getApplicationContext(), result);
                ListView list = (ListView) findViewById(R.id.RequestlistView);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        StationeryRequest selected = (StationeryRequest) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(getApplicationContext(), ApproveRequestActivity.class);
                        intent.putExtra("rid", selected.get("RequestId"));
                        startActivity(intent);
                    }
                });
            }
        }.execute();
    }
}
