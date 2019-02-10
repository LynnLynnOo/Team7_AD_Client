package com.example.adteam7.team7_ad_client.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.model.ReturnItem;
import com.example.adteam7.team7_ad_client.model.ReturnItemPostBack;
import com.example.adteam7.team7_ad_client.network.APIDataAgent;
import com.example.adteam7.team7_ad_client.network.APIDataAgentImpl;

import java.util.List;

/**
 * Created by dodo
 **/
public class ReturnItemAdapter extends RecyclerView.Adapter<ReturnItemAdapter.RvHolder> {

    List<ReturnItem> list;
    Boolean check;
    String reqId;
    APIDataAgent agent = new APIDataAgentImpl();

    public ReturnItemAdapter(Context c, List<ReturnItem> lv, Boolean check) {
        this.list = lv;
        this.check = check;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    @Override
    public ReturnItemAdapter.RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.return_item_row, null);

        return new ReturnItemAdapter.RvHolder(v);
    }

    @Override
    public void onBindViewHolder(ReturnItemAdapter.RvHolder holder, final int position) {


        holder.desc.setText(list.get(position).getDescription() + "");

        holder.qty.setText(list.get(position).getQuantity() + "");

        holder.cat.setText(list.get(position).getCategory() + "");

        holder.itemid.setText(list.get(position).getItemId() + "");

        holder.location.setText(list.get(position).getLocation() + "");

        holder.returnto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call single return item
                ReturnItemPostBack item = new ReturnItemPostBack(getReqId(), list.get(position).getItemId(), list.get(position).getQuantity());
                new AsyncSetReturnByItem().execute(item);
                list.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class RvHolder extends RecyclerView.ViewHolder {
        TextView desc, cat, qty, itemid, location;
        Button returnto;

        public RvHolder(View itemView) {
            super(itemView);
            //  receive = itemView.findViewById(R.id.receive);
            qty = itemView.findViewById(R.id.qty);
            desc = itemView.findViewById(R.id.desc);
            cat = itemView.findViewById(R.id.category);
            itemid = itemView.findViewById(R.id.itemid);
            location = itemView.findViewById(R.id.location);
            returnto = itemView.findViewById(R.id.returnto);

        }

    }

    private class AsyncSetReturnByItem extends AsyncTask<ReturnItemPostBack, Void, String> {
        @Override
        protected String doInBackground(ReturnItemPostBack... param) {
            return agent.returnSingleItem(param[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            notifyDataSetChanged();

            //refresh the adapter

        }
    }

}