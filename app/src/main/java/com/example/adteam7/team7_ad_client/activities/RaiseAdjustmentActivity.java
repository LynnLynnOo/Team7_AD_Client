package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;

import java.util.ArrayList;
import java.util.List;

//Author: Cheng Zongpei
public class RaiseAdjustmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_adjustment);
        List<AdjustmentItem> items = new ArrayList<AdjustmentItem>();
        items.add(new AdjustmentItem("E0001", "Eraser", "Eraser1", 2, 20, 3.00));
        items.add(new AdjustmentItem("E0001", "Eraser", "Eraser1", 2, 20, 3.00));
        ListView lv = findViewById(R.id.AdjustmentList);
        lv.setAdapter(new SimpleAdapter(this, items, R.layout.adjustment_row, new String[]{"category", "itemDescription", "quantity", "currentStock", "amount"},
                new int[]{R.id.category, R.id.description, R.id.quantity, R.id.stock, R.id.amount}));

        Button button = findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RaiseAdjustmentActivity.this, AdjustmentDetailActivity.class);
                startActivity(i);

            }
        });
    }
}
