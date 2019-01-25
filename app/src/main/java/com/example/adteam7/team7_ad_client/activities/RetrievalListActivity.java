package com.example.adteam7.team7_ad_client.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.adapters.RetrievalAdapter;
import com.example.adteam7.team7_ad_client.controller.SwipeController;
import com.example.adteam7.team7_ad_client.controller.SwipeControllerActions;
import com.example.adteam7.team7_ad_client.data.StationeryRetrievalApiModel;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.ArrayList;

public class RetrievalListActivity extends AppCompatActivity {

    APIDataAgent agent = new APIDataAgentImpl();
    SwipeController swipeController = null;
    private RetrievalAdapter mAdapter;
    ArrayList<StationeryRetrievalApiModel> retrievalsToSend;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_list);

        submit = findViewById(R.id.submit);
        new AsyncCallerGet().execute();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX); //this needs to be changed
                new AsyncCallerSet().execute();

            }
        });
    }

    //Region initializing recyclerview
    private void setRetrievalDataAdapter() {
//        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX); //this needs to be changed
        ArrayList<StationeryRetrievalApiModel> retrievals = agent.RetrievalListGet();

        mAdapter = new RetrievalAdapter(retrievals);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int selectedPosition) {
                //find exactly the same object in the list to send
                StationeryRetrievalApiModel selectedRequest = mAdapter.retrievals.get(selectedPosition);
                showRequestItemDialog(RetrievalListActivity.this, selectedRequest, selectedPosition);
            }

            @Override
            public void onLeftClicked(int selectedPosition) {
                StationeryRetrievalApiModel selectedRequest = mAdapter.retrievals.get(selectedPosition);
                int positionToModify = retrievalsToSend.indexOf(selectedRequest);
                retrievalsToSend.get(positionToModify).setNewQuantity(selectedRequest.getNeededQuantity());
                mAdapter.retrievals.remove(selectedPosition);
                mAdapter.notifyItemRemoved(selectedPosition);
                mAdapter.notifyItemRangeChanged(selectedPosition, mAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void showRequestItemDialog(Context context, StationeryRetrievalApiModel selectedRequest, int selectedPosition) {

        //First top layout for quantity picker
        LinearLayout layoutForNumber = new LinearLayout(context);
        layoutForNumber.setOrientation(LinearLayout.VERTICAL);
        NumberPicker picker = new NumberPicker(context);
        picker.setMinValue(0);
        int maxValue;
//        if (selectedRequest.getQuantityInWarehouse() <= 0) {
//            maxValue = 0;
//        } else {
            maxValue = selectedRequest.getNeededQuantity();
//        }
        picker.setMaxValue(maxValue);

        layoutForNumber.addView(picker);

        //Second layout for Remarks(Text)+EditText field
        TextView remarksLabel = new TextView(context);
        remarksLabel.setText("Remarks: ");

        EditText remarksField = new EditText(context);
        remarksField.setSingleLine(false);
        remarksField.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

        LinearLayout layoutForText = new LinearLayout(context);
        layoutForText.setOrientation(LinearLayout.VERTICAL);
        layoutForText.addView(remarksLabel);
        layoutForText.addView(remarksField);

        //Setting main parent frame for dialog
        LinearLayout mainLayout = new LinearLayout(context);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(layoutForNumber);
        mainLayout.addView(layoutForText);

//        layoutForText.addView(remarks, new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                Gravity.BOTTOM));
//
//        layout.addView(layoutForText, new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT,
//                Gravity.CENTER));

        new AlertDialog.Builder(context)
                .setView(mainLayout)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {


                    //find the position of the same object in the listToSend
                    int positionToModify = retrievalsToSend.indexOf(selectedRequest);
                    retrievalsToSend.get(positionToModify).setNewQuantity(picker.getValue());
                    retrievalsToSend.get(positionToModify).setRemarks(remarksField.getText().toString());

                    //remove item from list
                    mAdapter.retrievals.remove(selectedPosition);
                    mAdapter.notifyItemRemoved(selectedPosition);
                    mAdapter.notifyItemRangeChanged(selectedPosition, mAdapter.getItemCount());
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
    //endregion

    private class AsyncCallerGet extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(RetrievalListActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            setRetrievalDataAdapter();
            retrievalsToSend = new ArrayList<>(mAdapter.retrievals);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            setupRecyclerView();
            pdLoading.dismiss();
        }
    }

    private class AsyncCallerSet extends AsyncTask<Void, Void, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(RetrievalListActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }
        @Override
        protected String doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            String status = agent.RetrievalListSet(retrievalsToSend);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

            pdLoading.dismiss();
            finish();
            Toast.makeText(RetrievalListActivity.this, result, Toast.LENGTH_SHORT).show();

        }
    }
}