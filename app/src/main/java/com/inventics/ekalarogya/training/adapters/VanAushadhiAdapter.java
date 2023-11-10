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
import com.inventics.ekalarogya.training.main.dashotheractivity.herbalremedies.medprep.VanAushadhiAdd;
import com.inventics.ekalarogya.training.models.VanaushadhiModel;

import java.util.List;

public class VanAushadhiAdapter  extends RecyclerView.Adapter<VanAushadhiAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<VanaushadhiModel> vanaushadhiModelList;
    private static final String TAG = "OwnerListAdapter";
    private VanAushadhiAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(VanaushadhiModel item);
    }

    public VanAushadhiAdapter(Context context, List<VanaushadhiModel> vanaushadhiModelList) {
        this.mContaxt = context;
        this.vanaushadhiModelList=vanaushadhiModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VanAushadhiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_ayurvedic_treatment, null);

        return new VanAushadhiAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final VanAushadhiAdapter.ViewHolder holder, int position) {
        final VanaushadhiModel baseResponse = vanaushadhiModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_month.setText(baseResponse.getAppointment_date());
        if (baseResponse.getPatient_name()!=null)
            holder.tv_treatment.setText(baseResponse.getPatient_name());
        else if(baseResponse.getMember_name()!=null)
            holder.tv_treatment.setText(baseResponse.getMember_name());
        else if(baseResponse.getHead_name()!=null)
            holder.tv_treatment.setText(baseResponse.getHead_name());


////        try{
            if (baseResponse.getCured().equalsIgnoreCase("Yes")){
                holder.tv_recovery.setText(mContaxt.getResources().getString(R.string.yes));
            }else if(baseResponse.getCured().equalsIgnoreCase("no")){
                holder.tv_recovery.setText(mContaxt.getResources().getString(R.string.no));
//                holder.editpen.setVisibility(View.VISIBLE);
            }

//        }catch (Exception e){
//            Log.d(TAG, "onBindViewHolder: "+e.getMessage());
//        }

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(vanaushadhiModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+vanaushadhiModelList.size());
        return vanaushadhiModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_month,tv_treatment,tv_recovery;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_month=itemView.findViewById(R.id.tv_month);
            tv_treatment=itemView.findViewById(R.id.tv_treatment);
//            tv_treatment=itemView.findViewById(R.id.tv_treatment);
            tv_recovery=itemView.findViewById(R.id.tv_recovery);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);

            editpen.setVisibility(View.VISIBLE);
            vieweye.setVisibility(View.GONE);
        }


        public void bind(VanaushadhiModel vanaushadhiModelList, VanAushadhiAdapter.OnItemClickListener listener, int position) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "onClick: edit"+vanaushadhiModelList.getEAP_id()+"\n");;
//                    Intent intent = new Intent(mContaxt, SingleFamilyGarden.class);
//                    intent.putExtra("m_data", vanaushadhiModelList);
//                    intent.putExtra("type", "view");
//                    mContaxt.startActivity(intent);
//                }
//            });
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+vanaushadhiModelList.getEAP_id()+"\n");;
                    Intent intent = new Intent(mContaxt, VanAushadhiAdd.class);
                    intent.putExtra("m_data", vanaushadhiModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });
            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+vanaushadhiModelList.getEAP_id()+"\n");;
                    Intent intent = new Intent(mContaxt, VanAushadhiAdd.class);
                    intent.putExtra("m_data", vanaushadhiModelList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);
                }
            });



        }
    }



}

