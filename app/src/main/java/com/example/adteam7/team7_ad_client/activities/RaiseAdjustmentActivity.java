package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.AdjustmentListAdapter;
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
        Button add = findViewById(R.id.AddButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keepAdjustment();
                Intent i = new Intent(RaiseAdjustmentActivity.this, AdjustmentDetailActivity.class);
                startActivityForResult(i,111);

            }
        });

        Button confirm = findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keepAdjustment();
                new AsyncSetAdjustment().execute();
           }
        });
        String[] ids = new String[adjustment.size()];
        for (int i=0;i<adjustment.size();i++){
            ids[i] = adjustment.get(i).itemId;
        }
        new AsyncGetInfo().execute(ids);
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
                AdjustmentItem adjustmentItem = ((APIDataAgentImpl)api).adjustmentGetInfo(itemId);
                for (AdjustmentInfo adjustmentInfo:adjustment){
                    if(adjustmentInfo.itemId == itemId){
                        adjustmentItem.quantity = adjustmentInfo.quantity;
                        break;
                    }
                }
                itemInfos.add(adjustmentItem);
            }
            return  itemInfos;
        }

        @Override
        protected void onPostExecute(List<AdjustmentItem> adjustmentItems) {
            AdjustmentListAdapter adapter = new AdjustmentListAdapter(getApplicationContext(),adjustmentItems);
            RecyclerView list = findViewById(R.id.AdjustmentList);
            list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    public void keepAdjustment(){
        List<AdjustmentInfo> newAdjustment = new ArrayList<AdjustmentInfo>();
        for(AdjustmentItem item:AdjustmentListAdapter.list){
            AdjustmentInfo info = new AdjustmentInfo(item.itemId,item.quantity);
            newAdjustment.add(info);
        }
        adjustment = newAdjustment;
    }

    @Override
    public void finish() {
        keepAdjustment();
        super.finish();
    }
}
