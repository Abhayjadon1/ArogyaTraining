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
import com.inventics.ekalarogya.training.main.dashotheractivity.villagevisit.VillageVisitAdd;
import com.inventics.ekalarogya.training.models.VillageVisitModel;

import java.util.List;

public class VillageVisitAdapter  extends RecyclerView.Adapter<VillageVisitAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<VillageVisitModel> villageVisitModel;
    private static final String TAG = "OwnerListAdapter";
    private VillageVisitAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(VillageVisitModel item);
    }

    public VillageVisitAdapter(Context context, List<VillageVisitModel> villageVisitModel) {
        this.mContaxt = context;
        this.villageVisitModel=villageVisitModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VillageVisitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_village_visit, null);

        return new VillageVisitAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final VillageVisitAdapter.ViewHolder holder, int position) {
        final VillageVisitModel baseResponse = villageVisitModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
        String traingList="";


        holder.tv_date.setText(baseResponse.getDate());
        if (baseResponse.getVillage_name()==null){
            holder.tv_village.setText(baseResponse.getVillage_id());
        }else {
            holder.tv_village.setText(baseResponse.getVillage_name());
        }
        holder.tv_purspose.setText(baseResponse.getPurpose());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(villageVisitModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+villageVisitModel.size());
        return villageVisitModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date,tv_village,tv_purspose;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_village=itemView.findViewById(R.id.tv_village);
            tv_purspose=itemView.findViewById(R.id.tv_purspose);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);
        }


        public void bind(VillageVisitModel villageVisitModel, VillageVisitAdapter.OnItemClickListener listener, int position) {
            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+villageVisitModel.getVisit()+"\n");;
                    Intent intent = new Intent(mContaxt, VillageVisitAdd.class);
                    intent.putExtra("m_data", villageVisitModel);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);
                }
            });
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+villageVisitModel.getVisit()+"\n");;
                    Intent intent = new Intent(mContaxt, VillageVisitAdd.class);
                    intent.putExtra("m_data", villageVisitModel);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });




        }
    }



}


