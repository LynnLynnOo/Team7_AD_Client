package com.example.adteam7.team7_ad_client.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.data.Employee;

import java.util.ArrayList;

/**
 * Created by dodo
 **/
public class MainDisburAdapter extends RecyclerView.Adapter<MainDisburAdapter.RvHolder> {

    ArrayList<Employee> lv;

    public MainDisburAdapter(ArrayList<Employee> lv) {
        this.lv = lv;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.disbursement, null);

        return new RvHolder(v);
    }

    @Override
    public void onBindViewHolder(RvHolder holder, final int position) {


        holder.name.setText(lv.get(position).getName());
        //  holder.iv.setImageResource(lv.get(position).getImgid());

        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lv.size();
    }


    class RvHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView iv;
        ImageButton ib;

        public RvHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            //  iv = itemView.findViewById(R.id.iv);
            //ib = itemView.findViewById(R.id.ib);

        }

    }
}