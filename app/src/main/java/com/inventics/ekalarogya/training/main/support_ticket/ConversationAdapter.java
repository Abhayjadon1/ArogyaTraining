package com.inventics.ekalarogya.training.main.support_ticket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;


public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {

    private Context context;
    private List<ConversationModel> arrayList;



    public interface OnItemClickListner{
        void  onItemClick(SupportTicket gramotthanModel);

    }
    OnItemClickListner listner;
    public ConversationAdapter(Context context, List<ConversationModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        Collections.reverse(arrayList);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
         return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ConversationModel list=arrayList.get(position);

        holder.tvMSG.setText(Html.fromHtml(list.comment, HtmlCompat.FROM_HTML_MODE_COMPACT));
        holder.tvtime.setText(list.created_at);
        try{
            if (list.getUser_img()!=null){
                Picasso.with(context).load(list.getUser_img()).into(holder.userProf);

            }
            if (list.getCustomerName()!=null){
                holder.tvID.setText(R.string.you);
            }else if(list.getCustomerName()==""||list.getCustomerName()==null) {
                holder.tvID.setText(list.getUserName());
                holder.tvID.setText(Html.fromHtml(list.getUserName()));
            }

        }catch (Exception e){
            Log.d("TAG", "onBindViewHolder: "+e.getMessage());
        }

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listner!=null)
                {
                    listner.onItemClick(list);
                }
            }
        });*/
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       TextView tvID,tvMSG,tvtime;
       ImageView userProf;

        public ViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tvID);
            tvMSG = itemView.findViewById(R.id.tvMSG);
            tvtime = itemView.findViewById(R.id.tvtime);
            userProf = itemView.findViewById(R.id.userProf);
        }
    }
}
