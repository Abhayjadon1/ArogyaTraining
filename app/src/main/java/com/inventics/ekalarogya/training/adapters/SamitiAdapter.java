package com.inventics.ekalarogya.training.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.main.dashotheractivity.samitimeeting.SamitiMeetingAdd;
import com.inventics.ekalarogya.training.models.SamitiModel;

import java.util.List;

public class SamitiAdapter  extends RecyclerView.Adapter<SamitiAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<SamitiModel> samitiModelList;
    private static final String TAG = "OwnerListAdapter";
    private SamitiAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(SamitiModel item);
    }

    public SamitiAdapter(Context context, List<SamitiModel> samitiModelList) {
        this.mContaxt = context;
        this.samitiModelList=samitiModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SamitiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_village_family, null);

        return new SamitiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SamitiAdapter.ViewHolder holder, int position) {
        final SamitiModel baseResponse = samitiModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_family_id.setText(baseResponse.getMeeting_date());
        holder.tv_family_head.setText(baseResponse.getVillage());
        holder.tv_total_member.setText(baseResponse.getTotal());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(samitiModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+samitiModelList.size());
        return samitiModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_family_id,tv_family_head,tv_total_member;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_family_id=itemView.findViewById(R.id.tv_family_id);
            tv_family_head=itemView.findViewById(R.id.tv_family_head);
            tv_total_member=itemView.findViewById(R.id.tv_total_member);
            editpen=itemView.findViewById(R.id.editbtn);
            editpen.setVisibility(View.VISIBLE);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(SamitiModel samitiModelList, SamitiAdapter.OnItemClickListener listener, int position) {


            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+samitiModelList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, SamitiMeetingAdd.class);
                    intent.putExtra("m_data", samitiModelList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+samitiModelList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, SamitiMeetingAdd.class);
                    intent.putExtra("m_data", samitiModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}
