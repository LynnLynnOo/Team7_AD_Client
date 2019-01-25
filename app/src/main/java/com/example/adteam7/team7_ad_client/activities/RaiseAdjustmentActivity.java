package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.AdjustmentInfo;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.ArrayList;
import java.util.List;

//Author: Cheng Zongpei
public class RaiseAdjustmentActivity extends AppCompatActivity {

    private static List<AdjustmentInfo> adjustment = new ArrayList<AdjustmentInfo>();
    APIDataAgent api = new APIDataAgentImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_adjustment);
        Button button = findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RaiseAdjustmentActivity.this, AdjustmentDetailActivity.class);
                startActivityForResult(i,111);

            }
        });

        Button confirm = findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncSetAdjustment().execute();
           }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 111){
            if(data.hasExtra("quantity") && data.hasExtra("itemId")){
                Log.d("id",data.getExtras().getString("itemId"));
                AdjustmentInfo info = new AdjustmentInfo(data.getExtras().getString("itemId"),data.getExtras().getInt("quantity"));
                adjustment.add(info);
                String[] ids = new String[adjustment.size()];
                Log.d("id",adjustment.get(0).itemId);
                Log.d("size",String.format("%d",adjustment.size()));
                for (int i=0;i<adjustment.size();i++){
                    ids[i] = adjustment.get(i).itemId;
                }
                new AsyncGetInfo().execute(ids);
            }
        }

    }

    public class AsyncGetInfo extends AsyncTask<String,Void,List<AdjustmentItem>>{
        @Override
        protected List<AdjustmentItem> doInBackground(String... strings) {
            List<AdjustmentItem> itemInfos = new ArrayList<AdjustmentItem>();
            for(String itemId: strings){
                itemInfos.add(((APIDataAgentImpl)api).adjustmentGetInfo(itemId));
            }
            return  itemInfos;
        }

        @Override
        protected void onPostExecute(List<AdjustmentItem> adjustmentItems) {
            List<String> descriptions = new ArrayList<String>();
            for (AdjustmentItem item: adjustmentItems){
                descriptions.add(item.description);
            }
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.adjustment_row,R.id.category,descriptions);
            ListView list = findViewById(R.id.AdjustmentList);
            list.setAdapter(adapter);
        }
    }

    public class AsyncSetAdjustment extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... voids) {
            return ((APIDataAgentImpl)api).adjustmentSet(adjustment);
        }

        @Override
        protected void onPostExecute(String s) {
            finish();
        }
    }
}
