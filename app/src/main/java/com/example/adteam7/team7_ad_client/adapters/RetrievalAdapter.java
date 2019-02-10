package com.example.adteam7.team7_ad_client.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.model.StationeryRetrievalApiModel;

import java.util.List;

public class RetrievalAdapter extends RecyclerView.Adapter<RetrievalAdapter.PlayerViewHolder> {
    public List<StationeryRetrievalApiModel> retrievals;

    public RetrievalAdapter(List<StationeryRetrievalApiModel> retrievals) {
        this.retrievals = retrievals;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.retrieval_row, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        StationeryRetrievalApiModel retrieval = retrievals.get(position);
        holder.description.setText(retrieval.getDescription());
        holder.category.setText(retrieval.getCategory());
        holder.location.setText("Location: " + retrieval.getLocation());
        holder.department.setText("Department: " + retrieval.getDepartmentName());
        holder.neededQuantity.setText(String.valueOf(retrieval.getNeededQuantity()));
        holder.availableQuantity.setText("Available: " + String.valueOf(retrieval.getQuantityInWarehouse()));
    }

    @Override
    public int getItemCount() {
        return retrievals.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView description, category, location, neededQuantity, availableQuantity, department;

        public PlayerViewHolder(View view) {
            super(view);
            description = view.findViewById(R.id.description);
            category = view.findViewById(R.id.category);
            department = view.findViewById(R.id.department);
            location = view.findViewById(R.id.location);
            neededQuantity = view.findViewById(R.id.neededQuantity);
            availableQuantity = view.findViewById(R.id.availableQuantity);
        }
    }
}
