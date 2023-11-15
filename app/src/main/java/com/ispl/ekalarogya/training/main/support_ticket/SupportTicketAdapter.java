package com.ispl.ekalarogya.training.main.support_ticket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.utils.DateUtils;

import java.util.List;


public class SupportTicketAdapter extends RecyclerView.Adapter<SupportTicketAdapter.ViewHolder> {

    private Context context;
    private List<SupportTicket> arrayList;



    public interface OnItemClickListner{
        void  onItemClick(SupportTicket gramotthanModel);

    }
    OnItemClickListner listner;
    public SupportTicketAdapter(Context context, List<SupportTicket> arrayList, OnItemClickListner listner) {
        this.context = context;
        this.arrayList = arrayList;
        this.listner = listner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_ticket_item, parent, false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SupportTicket list=arrayList.get(position);

        holder.tvFamilyNo.setText(DateUtils.parseDate(list.getCreated_at(),DateUtils.DATE_FORMATE,DateUtils.SERVER_FORMAT2));
       // holder.tvFamilyNo.setText(format);
        if (list.status.equalsIgnoreCase("Closed"))
            holder.tvCreatedAt.setText(context.getResources().getString(R.string.closed));
        else if(list.status.equalsIgnoreCase("New"))
            holder.tvCreatedAt.setText(context.getResources().getString(R.string.new_ticket));
//        else if()
//            holder.tvCreatedAt.setText();

        holder.tvSN.setText(list.ticket_id);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null)
                {
                    listner.onItemClick(list);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       TextView tvUpdatedAt,tvCreatedAt,tvFamilyNo,tvSN;

        public ViewHolder(View itemView) {
            super(itemView);

//            tvUpdatedAt = itemView.findViewById(R.id.tvUpdatedAt);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvFamilyNo = itemView.findViewById(R.id.tvFamilyNo);
            tvSN = itemView.findViewById(R.id.tvSN);

        }
    }
}
