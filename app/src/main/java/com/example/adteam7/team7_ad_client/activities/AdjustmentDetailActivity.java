package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdjustmentDetailActivity extends AppCompatActivity {
    APIDataAgent api = new APIDataAgentImpl();
    private  static  List<AdjustmentItem> items;
    private static  String ItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustment_detail);
        new AsyncGetCategory().execute();

        Button confirm = findViewById(R.id.confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((NumberPicker) findViewById(R.id.quantity)).getValue() != 0) {
                    Intent data = new Intent();
                    int quantity = ((NumberPicker) findViewById(R.id.quantity)).getValue();
                    data.putExtra("quantity", quantity);
                    data.putExtra("itemId", ItemId);
                    setResult(RESULT_OK, data);
                    Log.d("finish", "!!!!!!!!!!!!!!!!");
                    finish();
                } else {

                }
            }
        });

        Button cancel = findViewById(R.id.rejectButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final NumberPicker numberPicker = findViewById(R.id.quantity);
        numberPicker.setValue(0);
        final Button increment = findViewById(R.id.increment);
        increment.setOnTouchListener(new View.OnTouchListener() {
            Timer timer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            numberPicker.increment(5);
                        }
                    }, 500, 500);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    timer.cancel();
                    Log.d("Action", "button release");
                }
                return false;
            }
        });
        final Button decrement = findViewById(R.id.decrement);
        decrement.setOnTouchListener(new View.OnTouchListener() {
            Timer timer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            numberPicker.increment(-5);
                        }
                    }, 500, 500);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    timer.cancel();
                    Log.d("Action", "button release");
                }
                return false;
            }
        });
    }

    private class AsyncGetCategory extends AsyncTask<Void,Void,List<String>>{
        @Override
        protected List<String> doInBackground(Void... voids) {
            return ((APIDataAgentImpl) api).adjustmentGetCategories();
        }

        @Override
        protected void onPostExecute(List<String> result) {
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.adjustment_category_row,R.id.categoryText,result);
            Spinner categorySpinner = findViewById(R.id.categorySpinner);
            categorySpinner.setAdapter(adapter);
            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String category = parent.getItemAtPosition(position).toString();
                    String[] categories = new String[]{category};
                    new AsyncGetItem().execute(categories);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


    private class AsyncGetItem extends AsyncTask<String,Void,List<AdjustmentItem>>{
        @Override
        protected List<AdjustmentItem> doInBackground(String... category) {
            return ((APIDataAgentImpl)api).adjustmentGetItem(category[0]);
        }

        @Override
        protected void onPostExecute(final List<AdjustmentItem> adjustmentItems) {
            items = adjustmentItems;
            List<String> description = new ArrayList<String>();
            for(int i=0;i<adjustmentItems.size();i++){
                description.add(adjustmentItems.get(i).description);
            }
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.adjustment_category_row,R.id.categoryText,description);
            Spinner itemSpinner = findViewById(R.id.itemSpinner);
            itemSpinner.setAdapter(adapter);
            itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String description = parent.getItemAtPosition(position).toString();
                    EditText uom = findViewById(R.id.unitOfMeasure);
                    for(AdjustmentItem item:items){
                        if(item.description == description){
                            uom.setText(item.unitOfMeasure);
                            NumberPicker numberPicker = findViewById(R.id.quantity);
                            numberPicker.setMin(-item.quantityWareHouse);
                            ItemId = item.itemId;
                            break;
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
}
