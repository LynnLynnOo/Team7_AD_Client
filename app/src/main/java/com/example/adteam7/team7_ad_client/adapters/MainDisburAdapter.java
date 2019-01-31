package com.example.adteam7.team7_ad_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adteam7.team7_ad_client.R;
import com.example.adteam7.team7_ad_client.activities.DisbursementDetailActivity;
import com.example.adteam7.team7_ad_client.data.Disbursement;
import com.example.adteam7.team7_ad_client.data.DisbursementSationeryItem;

import java.util.List;

/**
 * Created by Kay Thi Swe Tun
 **/
public class MainDisburAdapter extends RecyclerView.Adapter<MainDisburAdapter.RvHolder> {

    List<Disbursement> listitem;
    Context context;

    public MainDisburAdapter(Context context,List<Disbursement> lv) {
        this.context=context;
        this.listitem = lv;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.disbursement, null);

        return new RvHolder(v);
    }

    @Override
    public void onBindViewHolder(RvHolder holder, final int position) {


        holder.disbno.setText(listitem.get(position).getDisbursementNo());
        holder.depname.setText(listitem.get(position).getDepartmentName());

        holder.repname.setText(listitem.get(position).getEmployeeName());
        holder.cpoint.setText(listitem.get(position).getCollectionDescription());


        holder.tapfordetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String disbno=listitem.get(position).getDisbursementNo();
                Intent i=new Intent(context, DisbursementDetailActivity.class);

                String disotp = listitem.get(position).getOTP();

                i.putExtra("disbno",disbno);

                i.putExtra("disbotp", disotp);
                i.putExtra("depname", listitem.get(position).getDepartmentName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }


    class RvHolder extends RecyclerView.ViewHolder {
        TextView disbno ,depname,cpoint,repname;
        LinearLayout tapfordetail;

        public RvHolder(View itemView) {
            super(itemView);
            tapfordetail=itemView.findViewById(R.id.godetail);
            disbno = itemView.findViewById(R.id.disbno);

            depname = itemView.findViewById(R.id.depname);

            cpoint = itemView.findViewById(R.id.cpoint);
            repname = itemView.findViewById(R.id.repname);


        }

    }




    public interface DeatailItemClickController {
        void onTapDetail(List<DisbursementSationeryItem> data);
    }
}