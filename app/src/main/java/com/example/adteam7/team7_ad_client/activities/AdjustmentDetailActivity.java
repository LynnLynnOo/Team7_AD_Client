package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

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
                finish();
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

    @Override
    public void finish() {
        Intent data = new Intent();
        EditText quantityText = findViewById(R.id.quantity);
        Log.d("quantityString",quantityText.getText().toString());
        if(!quantityText.getText().toString().isEmpty()){
            data.putExtra("quantity",Integer.parseInt(quantityText.getText().toString()));
            data.putExtra("itemId",ItemId);
        }
        setResult(RESULT_OK,data);
        Log.d("finish","!!!!!!!!!!!!!!!!");
        super.finish();
    }
}
