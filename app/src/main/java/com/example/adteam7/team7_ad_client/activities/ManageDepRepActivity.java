package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

/**
 * Created by Kay Thi Swe Tun
 **/
public class ManageDepRepActivity extends AppCompatActivity {
    String[] items = {"All Categories",
            "Entertainment Products", "Kitchen Accessories", "Personal Care", "Household Accessories"};
Spinner spinEmp;
Button assign;

    APIDataAgent agent=new APIDataAgentImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dep_rep);
        spinEmp=findViewById(R.id.sprEmp);
        assign=findViewById(R.id.assign);
        ArrayAdapter<String> spinneradapter;
        spinneradapter = new ArrayAdapter<String>(
                ManageDepRepActivity.this,
                android.R.layout.simple_list_item_1,
                items);
        spinEmp.setAdapter(spinneradapter);


        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(ManageDepRepActivity.this, "Assigned", Toast.LENGTH_SHORT).show();
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        agent.delegateDepHeadGet();                      //  tes =agent.login(username.getText().toString(),passwrod.getText().toString());
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean res) {
                        //show(emp);

                    }
                }.execute();

            }
        });
    }
}
