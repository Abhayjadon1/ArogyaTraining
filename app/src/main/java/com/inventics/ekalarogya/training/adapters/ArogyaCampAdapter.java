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
import com.inventics.ekalarogya.training.main.dashotheractivity.arogyacamp.ArogyaCampAdd;
import com.inventics.ekalarogya.training.models.ArogyaCampModel;

import java.util.List;

public class ArogyaCampAdapter extends RecyclerView.Adapter<ArogyaCampAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<ArogyaCampModel> arogyaCampModels;
    private static final String TAG = "OwnerListAdapter";
    private ArogyaCampAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(ArogyaCampModel item);
    }

    public ArogyaCampAdapter(Context context, List<ArogyaCampModel> arogyaCampModels) {
        this.mContaxt = context;
        this.arogyaCampModels=arogyaCampModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArogyaCampAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_arogya_camp, null);

        return new ArogyaCampAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ArogyaCampAdapter.ViewHolder holder, int position) {
        final ArogyaCampModel baseResponse = arogyaCampModels.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
        String traingList="";


        holder.tv_total_doctor.setText(baseResponse.getNo_of_doctor());
        holder.tv_total_patient.setText(baseResponse.getTotal_patient());
        holder.tv_date.setText(baseResponse.getMeeting_date());
        holder.tv_village.setText(baseResponse.getVillage_name());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).error(R.drawable.add_image).into(holder.dash_imagee);

        holder.bind(arogyaCampModels.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+arogyaCampModels.size());
        return arogyaCampModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date,tv_village,tv_total_patient,tv_total_doctor;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_village=itemView.findViewById(R.id.tv_village);
            tv_total_patient=itemView.findViewById(R.id.tv_total);
            tv_total_doctor=itemView.findViewById(R.id.tv_total_doctor);

            editpen=itemView.findViewById(R.id.editbtn);
            editpen.setVisibility(View.GONE);
            vieweye=itemView.findViewById(R.id.vieweye);
        }


        public void bind(ArogyaCampModel arogyaCampModels, ArogyaCampAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+arogyaCampModels.getCamp_id()+"\n");;
                    Intent intent = new Intent(mContaxt, ArogyaCampAdd.class);
                    intent.putExtra("m_data", arogyaCampModels);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });
            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+arogyaCampModels.getCamp_id()+"\n");;
                    Intent intent = new Intent(mContaxt, ArogyaCampAdd.class);
                    intent.putExtra("m_data", arogyaCampModels);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);
                }
            });




        }
    }



}


