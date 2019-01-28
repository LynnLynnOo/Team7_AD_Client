package com.example.adteam7.team7_ad_client.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.AdjustmentItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdjustmentListAdapter extends RecyclerView.Adapter<adjustmentHolder>{
    public static List<AdjustmentItem> list = new ArrayList<AdjustmentItem>();

    public AdjustmentListAdapter(Context context,List<AdjustmentItem> l){
        this.list = l;
    }

    @NonNull
    @Override
    public adjustmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adjustment_row, null);
        return new adjustmentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull adjustmentHolder adjustmentHolder, int i) {
        adjustmentHolder.category.setText(list.get(i).category);
        adjustmentHolder.decription.setText(list.get(i).description);
        adjustmentHolder.quantity.setText(String.format("%d",list.get(i).quantity));
        NumberFormat nf = new DecimalFormat("$#.##");
        double amount =list.get(i).quantity*list.get(i).price;
        adjustmentHolder.amount.setText(nf.format(amount));
        adjustmentHolder.delete.setTag(i);
        adjustmentHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(Integer.parseInt(v.getTag().toString()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

 class adjustmentHolder extends RecyclerView.ViewHolder{
    TextView category,decription,quantity,amount;
    Button delete;
    public adjustmentHolder(View itemView){
        super(itemView);
        category = itemView.findViewById(R.id.category);
        decription = itemView.findViewById(R.id.description);
        quantity = itemView.findViewById(R.id.quantity);
        amount = itemView.findViewById(R.id.amount);
        delete = itemView.findViewById(R.id.delete);
    }
}
