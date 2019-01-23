package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.StationeryRequest;

public class ApproveRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);

        Intent i = getIntent();
        String rid = i.getStringExtra("rid");
        new AsyncTask<String, Void, StationeryRequest>() {
            @Override
            protected StationeryRequest doInBackground(String... params) {
                return StationeryRequest.ReadStationeryRequest(params[0]);
            }

            @Override
            protected void onPostExecute(StationeryRequest rid) {
                show(rid);
            }
        }.execute(rid);
    }

    void show(StationeryRequest request) {
        int[] dest = new int[]{R.id.editText1, R.id.editText2, R.id.editText3, R.id.editText4};
        String[] src = new String[]{"RequestId", "RequestedBy", "Status", "RequestDate"};
        for (int n = 0; n < dest.length; n++) {
            EditText txt = findViewById(dest[n]);
            txt.setText(request.get(src[n]));
        }

    }
}
