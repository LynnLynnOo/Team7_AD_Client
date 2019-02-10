package com.example.adteam7.team7_ad_client.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.model.DisbursementSationeryItem;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;

/**
 * Created by dodo
 **/
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.RvHolder> {

    List<DisbursementSationeryItem> list;
    Boolean check;

    public ItemListAdapter(Context c,List<DisbursementSationeryItem> lv,Boolean check) {
        this.list = lv;
        this.check=check;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, null);

        return new RvHolder(v);
    }

    @Override
    public void onBindViewHolder(RvHolder holder, final int position) {


        holder.desc.setText(list.get(position).getDescription()+"");

        holder.reqqty.setText(list.get(position).getQuantity()+"");
        int pos=position+1;
        holder.no.setText(pos+"");
        if (!check){
            //come from Acknowledge

            holder.receive2.setVisibility(View.VISIBLE);
            holder.receiveqty.setVisibility(View.GONE);
            if (list.get(position).getReceivedQty() == 0) {
                holder.receive2.setText(list.get(position).getQuantity() + "");
            } else
                holder.receive2.setText(list.get(position).getReceivedQty() + "");
        }
        else {
            //come from Detail
            holder.receiveqty.setValue(list.get(position).getQuantity());
            holder.receive2.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class RvHolder extends RecyclerView.ViewHolder {
        TextView reqqty,desc,no,receive2;
        NumberPicker receiveqty;

        public RvHolder(View itemView) {
            super(itemView);
            //  receive = itemView.findViewById(R.id.receive);
            reqqty = itemView.findViewById(R.id.reqqty);
            desc = itemView.findViewById(R.id.desc);
            no = itemView.findViewById(R.id.no);
            receiveqty=itemView.findViewById(R.id.receive);
            receiveqty.setDisplayFocusable(true);
            receive2=itemView.findViewById(R.id.receive2);


            receiveqty.setValueChangedListener(new ValueChangedListener() {
                @Override
                public void valueChanged(int value, ActionEnum action) {
                    list.get(getAdapterPosition()).setReceivedQty(value);
                }
            });

        }

    }
    public List<DisbursementSationeryItem> getList(){
        return list;
    }

   /* public interface DisbDetailDataController{
        void onTapPromotion(List<DisbursementSationeryItem> detaillist);
    }*/

}