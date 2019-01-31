package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ReturntoWarehouseAdapter;
import com.example.adteam7.team7_ad_client.data.ReturnItem;
import com.example.adteam7.team7_ad_client.data.ReturntoWarehouseApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ReturntoWarehouseActivity extends AppCompatActivity {


    ListView lw;
    ReturntoWarehouseAdapter lwAdapter;
    List<ReturntoWarehouseApi> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returnto_warehouse);

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


                lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        ReturntoWarehouseApi detailitemlist;
                        detailitemlist=(ReturntoWarehouseApi) parent.getAdapter().getItem(position);

                        Intent intent = new Intent(ReturntoWarehouseActivity.this, ReturnItemListActivity.class);
                        Toast.makeText(ReturntoWarehouseActivity.this, " list "+detailitemlist.getItemViewModels().size(), Toast.LENGTH_SHORT).show();

                        intent.putExtra("LIST", (Serializable) detailitemlist.getItemViewModels());
                        //intent.putExtra("id", detailitemlist.);
                        startActivity(intent);
                    }
                });
            }

        }
    }
}
