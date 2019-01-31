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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.AdjustmentListAdapter;
import com.example.adteam7.team7_ad_client.data.AdjustmentInfo;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;
import com.example.adteam7.team7_ad_client.network.SendMailTask;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Raise Adjustment");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
                if (!adjustment.isEmpty() && !((EditText) findViewById(R.id.remark)).getText().toString().equals("")) {
                    new AsyncSetAdjustment().execute();
                    Log.d("adjustmnet", "database affected");
                    AdjustmentListAdapter.list = new ArrayList<>();
                    sendEmail();
                } else if (adjustment.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "You need at least one item for adjustment", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "You need to fill in the remark", Toast.LENGTH_LONG).show();
                }
           }
        });
        String[] ids = new String[adjustment.size()];
        for (int i = 0; i < adjustment.size(); i++) {
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
                for (int i = 0; i < adjustment.size(); i++) {
                    ids[i] = adjustment.get(i).itemId;
                }
                new AsyncGetInfo().execute(ids);
            }
        }
    }

    public void keepAdjustment() {
        List<AdjustmentInfo> newAdjustment = new ArrayList<AdjustmentInfo>();
        for (AdjustmentItem item : AdjustmentListAdapter.list) {
            AdjustmentInfo info = new AdjustmentInfo(item.itemId, item.quantity);
            newAdjustment.add(info);
        }
        adjustment = newAdjustment;
    }

    @Override
    public void finish() {
        keepAdjustment();
        super.finish();
    }

    public class AsyncSetAdjustment extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            for (AdjustmentInfo info : adjustment) {
                info.remark = ((EditText) findViewById(R.id.remark)).getText().toString();
            }
            return ((APIDataAgentImpl) api).adjustmentSet(adjustment);
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }

    public class AsyncGetInfo extends AsyncTask<String,Void,List<AdjustmentItem>>{
        @Override
        protected List<AdjustmentItem> doInBackground(String... strings) {
            List<AdjustmentItem> itemInfos = new ArrayList<AdjustmentItem>();
            for(String itemId: strings){
                AdjustmentItem adjustmentItem = ((APIDataAgentImpl) api).adjustmentGetInfo(itemId);
                for (AdjustmentInfo adjustmentInfo : adjustment) {
                    if (adjustmentInfo.itemId == itemId) {
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
            AdjustmentListAdapter adapter = new AdjustmentListAdapter(getApplicationContext(), adjustmentItems);
            RecyclerView list = findViewById(R.id.AdjustmentList);
            list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            list.setAdapter(adapter);
        }
    }

    public void sendEmail(){
        double amount = 0;
        for(AdjustmentItem item: AdjustmentListAdapter.list){
            amount+=(item.price*item.quantity);
        }
        Log.d("amount", String.format("%d", (int) amount));
        new AsyncGetEmail().execute(new Integer[]{(int) amount});
    }

    public class AsyncGetEmail extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            return ((APIDataAgentImpl) api).adjustmentGetEmail(integers[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            String emailAddress = s;
            SessionManager session = SessionManager.getInstance();
            Log.d("email", s);
            String subject = "New Adjustment Raised";
            String content ="Kindly remind: \n You got a new adjustment raised.\n Raised by " + session.getUsername() + ".";
            new SendMailTask(RaiseAdjustmentActivity.this).execute(new String[]{"1015440098@qq.com", subject, content});
        }
    }
}
