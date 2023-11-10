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
import com.inventics.ekalarogya.training.main.dashotheractivity.eye.eyevan.EyeVanAdd;
import com.inventics.ekalarogya.training.models.EyeVanModel;

import java.util.List;

public class EyeVanAdapter extends RecyclerView.Adapter<EyeVanAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<EyeVanModel> eyeVanModelList;
    private static final String TAG = "OwnerListAdapter";
    private EyeVanAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(EyeVanModel item);
    }

    public EyeVanAdapter(Context context, List<EyeVanModel> eyeVanModelList) {
        this.mContaxt = context;
        this.eyeVanModelList=eyeVanModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EyeVanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.eye_van_row, null);

        return new EyeVanAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final EyeVanAdapter.ViewHolder holder, int position) {
        final EyeVanModel baseResponse = eyeVanModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_id.setText(String.valueOf(position+1));
        holder.tvAge.setText(baseResponse.getAge());
        if (baseResponse.getMember_name()==null){
            holder.tvName.setText(baseResponse.getGuest_patient_name());
        }else{
            holder.tvName.setText(baseResponse.getMember_name());
        }

//        holder.tvName.setText(baseResponse.getMember_name());
        holder.tv_date.setText(baseResponse.getTest_date());
        holder.tv_recovery.setText(baseResponse.getPower());
//        try{
//            if (baseResponse.getCured().equalsIgnoreCase("Yes")){
//                holder.tv_recovery.setText(baseResponse.getCured());
//            }else if(baseResponse.getCured().equalsIgnoreCase("no")){
//                holder.tv_recovery.setVisibility(View.GONE);
//                holder.editpen.setVisibility(View.VISIBLE);
//            }

//        }catch (Exception e){
//            Log.d(TAG, "onBindViewHolder: "+e.getMessage());
//        }

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).error(R.drawable.add_image).into(holder.dash_imagee);

        holder.bind(eyeVanModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+eyeVanModelList.size());
        return eyeVanModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAge , tv_id  , tvName,  tv_date,  tv_recovery;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tvAge=itemView.findViewById(R.id.tvAge);
            tvName=itemView.findViewById(R.id.tvName);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_recovery=itemView.findViewById(R.id.tv_recovery);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);
        }


        public void bind(EyeVanModel eyeVanModelList, EyeVanAdapter.OnItemClickListener listener, int position) {
//
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+eyeVanModelList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, EyeVanAdd.class);
                    intent.putExtra("m_data", eyeVanModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });
            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+eyeVanModelList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, EyeVanAdd.class);
                    intent.putExtra("m_data", eyeVanModelList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);
                }
            });



        }
    }



}


