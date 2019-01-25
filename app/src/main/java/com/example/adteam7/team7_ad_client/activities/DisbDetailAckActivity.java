package com.example.adteam7.team7_ad_client.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.ItemListAdapter;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

public class DisbDetailAckActivity extends AppCompatActivity {

    RecyclerView itemsrv;
    APIDataAgent agent=new APIDataAgentImpl();
    ItemListAdapter adapter;
    Button voiddisb,ackwge;
    List<DisbursementSationeryItem> itemlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disbursement_detail);
        itemsrv=findViewById(R.id.detailrv);
        itemsrv.setLayoutManager(new LinearLayoutManager(this));
        voiddisb=findViewById(R.id.voiddisb);
        ackwge=findViewById(R.id.ackwge);


        voiddisb.setText("Cancel");
        ackwge.setText("Confirm");

        if(getIntent().hasExtra("disblist")){
            List<DisbursementSationeryItem> myList = (List<DisbursementSationeryItem>) getIntent().getSerializableExtra("disblist");
            Toast.makeText(this, "receive list "+myList.size(), Toast.LENGTH_SHORT).show();

            adapter=new ItemListAdapter(DisbDetailAckActivity.this,myList,false);

            itemsrv.setAdapter(adapter);


        }


    }
}
