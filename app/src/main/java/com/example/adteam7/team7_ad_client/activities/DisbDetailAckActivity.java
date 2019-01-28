package com.example.adteam7.team7_ad_client.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.Fragments.OTPFragment;
import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ItemListAdapter;
import com.example.adteam7.team7_ad_client.adapters.MainDisburAdapter;
import com.example.adteam7.team7_ad_client.data.Disbursement;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;
import com.jkb.vcedittext.VerificationCodeEditText;

import java.util.ArrayList;
import java.util.List;

public class DisbDetailAckActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 12;
    RecyclerView itemsrv;
    APIDataAgent agent=new APIDataAgentImpl();
    ItemListAdapter adapter;
    Button cancel,confirm;
    String disbOTP;
    List<DisbursementSationeryItem> itemlist;
    PopupWindow popWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);
        itemsrv=findViewById(R.id.detailrv);
        itemsrv.setLayoutManager(new LinearLayoutManager(this));
        cancel=findViewById(R.id.voiddisb);
        confirm=findViewById(R.id.ackwge);


        cancel.setText("Cancel");
        confirm.setText("Confirm");

        if(getIntent().hasExtra("disblist")){
            itemlist = (List<DisbursementSationeryItem>) getIntent().getSerializableExtra("disblist");
            Toast.makeText(this, "receive list "+itemlist.size(), Toast.LENGTH_SHORT).show();

            adapter=new ItemListAdapter(DisbDetailAckActivity.this,itemlist,false);

            itemsrv.setAdapter(adapter);


        }
        if(getIntent().hasExtra("disbotp")){

            disbOTP=getIntent().getStringExtra("disbotp");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
             }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigateToOTPConfirm(v.getRootView());
            }
        });


    }


    private void NavigateToOTPConfirm(View view) {

        OTPFragment dialogFragment = new OTPFragment();
        dialogFragment.setTargetFragment(null, REQUEST_CODE);
        dialogFragment.setCancelable(false);
        dialogFragment.getActivity();
        dialogFragment.show(getSupportFragmentManager(), "Sample Fragment");


        Bundle b=new Bundle();
        b.putString("OTP",disbOTP);
        dialogFragment.setArguments(b);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String resultMessage = bundle.getString(OTPFragment.RESULT);


            Toast.makeText(this, "from frag "+resultMessage, Toast.LENGTH_SHORT).show();
            //call API to ack
            new AsyncAcknowledgeDisbursement().execute(itemlist);
            }


        }



    private class AsyncAcknowledgeDisbursement extends AsyncTask<List<DisbursementSationeryItem>, Void,String> {


        @Override
        protected String doInBackground(List<DisbursementSationeryItem>... param) {

            //Toast.makeText(DisbDetailAckActivity.this, "actual receive "+param[0].get(0).getReceivedQty(), Toast.LENGTH_SHORT).show();

            return agent.ackDisbursement(param[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(DisbDetailAckActivity.this, "Acknowledged "+s, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
