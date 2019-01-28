package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.RequestAdaptor;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

public class ViewRequestActivity extends AppCompatActivity {


    APIDataAgent agent = new APIDataAgentImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        new AsyncCallerGet().execute();
    }

    /*Show request history when entered the page*/

    private class AsyncCallerGet extends AsyncTask<Void, Void, List<StationeryRequestApiModel>> {
            @Override
            protected List<StationeryRequestApiModel> doInBackground(Void... params) {
                return agent.ReadStationeryRequest();
            }

            @Override
            protected void onPostExecute(List<StationeryRequestApiModel> result) {
                RequestAdaptor adapter = new RequestAdaptor(getApplicationContext(), result);
                ListView list = (ListView) findViewById(R.id.RequestlistView);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        StationeryRequestApiModel selected = (StationeryRequestApiModel) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(getApplicationContext(), ApproveRequestActivity.class);
                        intent.putExtra("rid", selected.getRequestId());
                        Log.e("Rid", selected.getRequestId());
                        startActivity(intent);

                    }
                });
            }
    }
}

