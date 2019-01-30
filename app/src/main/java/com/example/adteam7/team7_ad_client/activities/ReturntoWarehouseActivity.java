package com.example.adteam7.team7_ad_client.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ReturntoWarehouseAdapter;
import com.example.adteam7.team7_ad_client.data.ReturntoWarehouseApi;

import java.util.ArrayList;
import java.util.List;


public class ReturntoWarehouseActivity extends AppCompatActivity {

/*for list*/
    ListView lw;
    ReturntoWarehouseAdapter lwAdapter;
    List<ReturntoWarehouseApi> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returnto_warehouse);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Return to Warehouse");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lw = findViewById(R.id.ReturntoWarehouseView);


        ///get data from api
        new AsyncGetItemList().execute();


    }


    private class AsyncGetItemList extends AsyncTask<Void, Void, List<ReturntoWarehouseApi>> {


        @Override
        protected List<ReturntoWarehouseApi> doInBackground(Void... voids) {


            return ReturntoWarehouseApi.GetItemList();
        }

        @Override
        protected void onPostExecute(List<ReturntoWarehouseApi> returntowarehouse) {

            if (returntowarehouse != null) {
                lwAdapter = new ReturntoWarehouseAdapter(ReturntoWarehouseActivity.this, returntowarehouse);

                lw.setAdapter(lwAdapter);

            }

        }
    }
}
