package com.ispl.ekalarogya.training.adapters;

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

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.main.dashotheractivity.eye.eyescreening.EyeScreeningAdd;
import com.ispl.ekalarogya.training.main.dashotheractivity.eye.eyescreening.EyeScreeningUpdate;
import com.ispl.ekalarogya.training.models.EyeVanModel;

import java.util.List;

public class EyeScreenAdapter extends RecyclerView.Adapter<EyeScreenAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<EyeVanModel> eyeVanModelList;
    private static final String TAG = "OwnerListAdapter";
    private EyeScreenAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(EyeVanModel item);
    }

    public EyeScreenAdapter(Context context, List<EyeVanModel> eyeVanModelList) {
        this.mContaxt = context;
        this.eyeVanModelList=eyeVanModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EyeScreenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_ayurvedic_treatment, null);

        return new EyeScreenAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final EyeScreenAdapter.ViewHolder holder, int position) {
        final EyeVanModel baseResponse = eyeVanModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        if (baseResponse.getMember_name()==null){
            holder.tv_treatment.setText(baseResponse.getGuest_patient_name());
        }else{
            holder.tv_treatment.setText(baseResponse.getMember_name());
        }

        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_month.setText(baseResponse.getTest_date());

        if (baseResponse.getVision_defect()!=null){
            if (baseResponse.getVision_defect().equalsIgnoreCase("Yes"))
                holder.tv_recovery.setText(mContaxt.getResources().getString(R.string.yes));
            else if(baseResponse.getVision_defect().equalsIgnoreCase("no"))
                holder.tv_recovery.setText(mContaxt.getResources().getString(R.string.no));
        }

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
        TextView tv_id,tv_month,tv_treatment,tv_recovery;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_month=itemView.findViewById(R.id.tv_month);
            tv_treatment=itemView.findViewById(R.id.tv_treatment);
            tv_recovery=itemView.findViewById(R.id.tv_recovery);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);
            editpen.setVisibility(View.GONE);
            vieweye.setVisibility(View.VISIBLE);
        }


        public void bind(EyeVanModel eyeVanModelList, EyeScreenAdapter.OnItemClickListener listener, int position) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "onClick: edit"+eyeVanModelList.getEAP_id()+"\n");;
//                    Intent intent = new Intent(mContaxt, SingleFamilyGarden.class);
//                    intent.putExtra("m_data", eyeVanModelList);
//                    intent.putExtra("type", "view");
//                    mContaxt.startActivity(intent);
//                }
//            });
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+eyeVanModelList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, EyeScreeningAdd.class);
                    intent.putExtra("m_data", eyeVanModelList);
                    Log.d(TAG, "onClick: "+eyeVanModelList.toString());
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);

//                    EyeScreeningAdd.openActivity(eyeVanModelList,"edit");
                }
            });
            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "onClick: view adpter"+eyeVanModelList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, EyeScreeningUpdate.class);
                    intent.putExtra("m_data", eyeVanModelList);
                    intent.putExtra("type", "view");
                    Log.d(TAG, "onClick: "+eyeVanModelList.toString());
                    mContaxt.startActivity(intent);

                }
            });



        }
    }



}


