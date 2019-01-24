package com.example.adteam7.team7_ad_client.activities;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieval_list);


        setRetrievalDataAdapter();
        setupRecyclerView();

    }

    private void setRetrievalDataAdapter() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        ArrayList<StationeryRetrievalApiModel> retrievals = agent.RetrievalListGet();
        mAdapter = new RetrievalAdapter(retrievals);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mAdapter.retrievals.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
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
}
