package com.example.adteam7.team7_ad_client.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dodo
 **/
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.RvHolder> {

    List<DisbursementSationeryItem> list;

    public ItemListAdapter(Context c,List<DisbursementSationeryItem> lv) {
        this.list = lv;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, null);

        return new RvHolder(v);
    }

    @Override
    public void onBindViewHolder(RvHolder holder, final int position) {


       holder.desc.setText(list.get(position).getDescription()+"");
    // holder.receive.setText(list.get(position).getQuantity()+"");
     holder.reqqty.setText(list.get(position).getQuantity()+"");
     holder.no.setText(position+"");

         /*holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.remove(position);
                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class RvHolder extends RecyclerView.ViewHolder {
        TextView receive,reqqty,desc,no;


        public RvHolder(View itemView) {
            super(itemView);
          //  receive = itemView.findViewById(R.id.receive);
            reqqty = itemView.findViewById(R.id.reqqty);
            desc = itemView.findViewById(R.id.desc);
            no = itemView.findViewById(R.id.no);
        }

    }

}