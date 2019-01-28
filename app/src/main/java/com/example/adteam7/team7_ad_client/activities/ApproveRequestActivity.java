package com.example.adteam7.team7_ad_client.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.RequestTransactionDetailApiModel;
import com.example.adteam7.team7_ad_client.data.SessionManager;
import com.example.adteam7.team7_ad_client.data.StationeryRequestApiModel;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

//region Author:Gao Jiaxue
public class ApproveRequestActivity extends AppCompatActivity {

    SessionManager session = SessionManager.getInstance();
    APIDataAgent agent = new APIDataAgentImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);

        Intent i = getIntent();
        String rid = i.getStringExtra("rid");
        Log.e("Rid", rid);
        new AsyncCallerGet().execute(rid);
        /*after click approve button*/
        final Button appbutton = findViewById(R.id.reqButtonApprove);
        appbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AsyncCallerApprove().execute(rid);
            }
        });
        /*after click reject button*/
        final Button rejbutton = findViewById(R.id.reqButtonReject);
        rejbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AsyncCallerReject().execute(rid);
            }
        });
    }

    void show(StationeryRequestApiModel request) {
        TextView txt1 = findViewById(R.id.text1);
        txt1.setText(request.getRequestId());
        TextView txt2 = findViewById(R.id.text2);
        txt2.setText(request.getRequestedBy());
        TextView txt3 = findViewById(R.id.text3);
        txt3.setText(request.getStatus());
        TextView txt4 = findViewById(R.id.text4);
        txt4.setText(request.getRequestDate());
        List<RequestTransactionDetailApiModel> transactionDetails = request.getRequestTransactionDetailApiModels();

        // ConstraintLayout approveReqView = findViewById(R.id.approveRequest_layout);
        LinearLayout approveReqView = findViewById(R.id.items);


        double sum = 0;
        for (int i = 0; i < transactionDetails.size(); i++) {
            int quantity = Integer.parseInt(transactionDetails.get(i).getQuantity());
            double unitPrice = Double.parseDouble(transactionDetails.get(i).getUnitPrice());
            String s = Double.toString(unitPrice);
            Log.e("i", s);
            sum = sum + quantity * unitPrice;
            String m = Double.toString(sum);
            Log.e("sum", m);
            //make a table layout
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
            tv11.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv11).setGravity(Gravity.LEFT);
            ((TextView) tv11).setText("Item ID:");//set the gravity of this TextView to left

            View tv12 = new TextView(getApplicationContext());
            tv12.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv12).setGravity(Gravity.LEFT);
            ((TextView) tv12).setText(transactionDetails.get(i).getItemId());

            View tv21 = new TextView(getApplicationContext());
            tv21.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv21).setGravity(Gravity.LEFT);
            ((TextView) tv21).setText("Quantity :");

            View tv22 = new TextView(getApplicationContext());
            tv22.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv22).setGravity(Gravity.LEFT);
            ((TextView) tv22).setText(transactionDetails.get(i).getQuantity());

            View tv31 = new TextView(getApplicationContext());
            tv31.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv31).setGravity(Gravity.LEFT);
            ((TextView) tv31).setText("Unit Price :");

            View tv32 = new TextView(getApplicationContext());
            tv32.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            ((TextView) tv32).setGravity(Gravity.LEFT);
            ((TextView) tv32).setText(transactionDetails.get(i).getUnitPrice());
            Log.e("ready to add", "add");

            //Add the Textview to table row
            ((TableRow) tr1).addView(tv11);
            ((TableRow) tr1).addView(tv12);
            Log.e("add to row", "new text");
            ((TableRow) tr2).addView(tv21);
            ((TableRow) tr2).addView(tv22);

            ((TableRow) tr3).addView(tv31);
            ((TableRow) tr3).addView(tv32);
            //Add the TableRow to the Table Layout

            ((TableLayout) newitem).addView(tr1);
            ((TableLayout) newitem).addView(tr2);
            ((TableLayout) newitem).addView(tr3);
            Log.e("add to table", "new text");
            //Add the TableLayout to the LinearLayout
            approveReqView.addView(newitem);
            Log.e("add to layout", "new text");
        }
        View tx_total = new TextView(getApplicationContext());
        ((TextView) tx_total).setText("Total :");
        approveReqView.addView(tx_total);
        View tx_amount = new TextView(getApplicationContext());
        String total = new Double(sum).toString();
        ((TextView) tx_amount).setText("$" + total);
        Log.e("total", "new text");
        approveReqView.addView(tx_amount);
    }

    private class AsyncCallerApprove extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = session.getUsername();
            StationeryRequestApiModel exactReq = agent.GetStationeryRequest(params[0]);
            exactReq.setApprovedBy(name);
            String result = agent.ApproveStationeryRequest(exactReq);
            Log.e("result", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ApproveRequestActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.e("Approve", "no wrong till here");
            Intent intent = new Intent(getApplicationContext(), ViewRequestActivity.class);
            startActivity(intent);
        }
    }

    private class AsyncCallerReject extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String name = session.getUsername();
            StationeryRequestApiModel exactReq = agent.GetStationeryRequest(params[0]);
            exactReq.setApprovedBy(name);
            String result = agent.RejectStationeryRequest(exactReq);
            Log.e("result", result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(ApproveRequestActivity.this, result, Toast.LENGTH_SHORT).show();
            Log.e("Reject", "no wrong till here");
            Intent intent = new Intent(getApplicationContext(), ViewRequestActivity.class);
            startActivity(intent);
        }
    }

    private class AsyncCallerGet extends AsyncTask<String, Void, StationeryRequestApiModel> {
            @Override
            protected StationeryRequestApiModel doInBackground(String... params) {
                return agent.GetStationeryRequest(params[0]);
            }

            @Override
            protected void onPostExecute(StationeryRequestApiModel req) {
                Log.e("PostExecute", "no wrong till here");
                show(req);
            }
    }

}
//endregion
