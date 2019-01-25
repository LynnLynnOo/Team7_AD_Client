package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.RequestTransactionDetailApiModel;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

public class ApproveRequestActivity extends AppCompatActivity {

    APIDataAgent agent = new APIDataAgentImpl();
    View approveReqView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);

        Intent i = getIntent();
        String rid = i.getStringExtra("rid");
        new AsyncCallerGet().execute(rid);
    }

    void show(StationeryRequestApiModel request) {
        TextView txt1 = findViewById(R.id.textViewr1);
        txt1.setText(request.getRequestId());
        TextView txt2 = findViewById(R.id.textViewr2);
        txt2.setText(request.getRequestedBy());
        TextView txt3 = findViewById(R.id.textViewr3);
        txt3.setText(request.getRequestDate());
        TextView txt4 = findViewById(R.id.textViewr4);
        txt4.setText(request.getStatus());
        List<RequestTransactionDetailApiModel> transactionDetails = request.getRequestTransactionDetailApiModelList();
        View approveReqView = findViewById(R.id.approveRequest_layout);
        double amount = 0;
        for (int i = 0; i < transactionDetails.size(); i++) {
            int quantity = Integer.parseInt(transactionDetails.get(i).getQuantity());
            double unitPrice = Integer.parseInt(transactionDetails.get(i).getUnitPrice());
            amount = amount + quantity * unitPrice;
            View newitem = new TableLayout(getApplicationContext());
            newitem.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            ((TableLayout) newitem).setColumnStretchable(0, true);
            ((TableLayout) newitem).setColumnStretchable(1, true);
            //Set the Table Row
            View tr1 = new TableRow(getApplicationContext());
            tr1.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            View tr2 = new TableRow(getApplicationContext());
            tr2.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            View tr3 = new TableRow(getApplicationContext());
            tr3.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            //Set the 2 TextViews each row
            View tv11 = new TextView(getApplicationContext());
            ;
            tv11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv11).setGravity(Gravity.LEFT);
            ((TextView) tv11).setText("Item ID:");//set the gravity of this TextView to left

            View tv12 = new TextView(getApplicationContext());
            tv12.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv12).setGravity(Gravity.RIGHT);
            ((TextView) tv12).setText(transactionDetails.get(i).getQuantity());

            View tv21 = new TextView(getApplicationContext());
            tv21.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv21).setGravity(Gravity.LEFT);
            ((TextView) tv21).setText("Quantity :");

            View tv22 = new TextView(getApplicationContext());
            tv22.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv22).setGravity(Gravity.RIGHT);
            ((TextView) tv22).setText(transactionDetails.get(i).getQuantity());

            View tv31 = new TextView(getApplicationContext());
            tv31.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv31).setGravity(Gravity.LEFT);
            ((TextView) tv31).setText("Unit Price :");

            View tv32 = new TextView(getApplicationContext());
            tv32.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv32).setGravity(Gravity.RIGHT);
            ((TextView) tv32).setText(transactionDetails.get(i).getUnitPrice());


            //Add the Textview to table row
            ((TableRow) tr1).addView(tv11);
            ((TableRow) tr1).addView(tv12);

            ((TableRow) tr2).addView(tv21);
            ((TableRow) tr2).addView(tv22);

            ((TableRow) tr3).addView(tv31);
            ((TableRow) tr3).addView(tv32);
            //Add the TableRow to the Table Layout
            ((TableLayout) newitem).addView(tr1);
            ((TableLayout) newitem).addView(tr2);
            ((TableLayout) newitem).addView(tr3);

            //Add the TableLayout to the LinearLayout
            ((LinearLayout) approveReqView).addView(newitem);
        }
        View tx_amount = new TextView(getApplicationContext());
        String total = new Double(amount).toString();
        ((TextView) tx_amount).setText(total);
        ((LinearLayout) approveReqView).addView(tx_amount);
    }

    private class AsyncCallerGet extends AsyncTask<String, Void, StationeryRequestApiModel> {
            @Override
            protected StationeryRequestApiModel doInBackground(String... params) {
                return agent.GetStationeryRequest(params[0]);
            }

            @Override
            protected void onPostExecute(StationeryRequestApiModel req) {
                show(req);
            }
    }
}
