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
import com.inventics.ekalarogya.training.main.dashotheractivity.herbalremedies.ayurvedic.AyurvedicAdd;
import com.inventics.ekalarogya.training.models.HerbalRemediesModel;

import java.util.List;

public class HerbalRemediesListAdapter extends RecyclerView.Adapter<HerbalRemediesListAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<HerbalRemediesModel> herbalRemediesModelList;
    private static final String TAG = "OwnerListAdapter";
    private HerbalRemediesListAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(HerbalRemediesModel item);
    }

    public HerbalRemediesListAdapter(Context context, List<HerbalRemediesModel> herbalRemediesModelList) {
        this.mContaxt = context;
        this.herbalRemediesModelList=herbalRemediesModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HerbalRemediesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_herbal_remedies, null);

        return new HerbalRemediesListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final HerbalRemediesListAdapter.ViewHolder holder, int position) {
        final HerbalRemediesModel baseResponse = herbalRemediesModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_village_Name.setText(baseResponse.getVillage_name());
        holder.tv_month.setText(baseResponse.getCamp_month());
        holder.tv_treatment.setText(baseResponse.getTreatment_type());
        holder.tv_total_patient.setText(baseResponse.getTotal_present());
        holder.tv_total_cured.setText(baseResponse.getTotal_cured());


        holder.bind(herbalRemediesModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+herbalRemediesModelList.size());
        return herbalRemediesModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_village_Name,tv_month,tv_treatment,tv_total_patient,tv_total_cured;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_village_Name=itemView.findViewById(R.id.tv_village_Name);
            tv_month=itemView.findViewById(R.id.tv_month);
            tv_treatment=itemView.findViewById(R.id.tv_treatment);
            tv_total_patient=itemView.findViewById(R.id.tv_total_patient);
            tv_total_cured=itemView.findViewById(R.id.tv_total_cured);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);



        }


        public void bind(HerbalRemediesModel herbalRemediesModelList, HerbalRemediesListAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+herbalRemediesModelList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, AyurvedicAdd.class);
                    intent.putExtra("m_data", herbalRemediesModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+herbalRemediesModelList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, AyurvedicAdd.class);
                    intent.putExtra("m_data", herbalRemediesModelList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}

