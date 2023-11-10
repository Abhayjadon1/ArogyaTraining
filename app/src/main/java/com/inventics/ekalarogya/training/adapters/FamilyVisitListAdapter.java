package com.inventics.ekalarogya.training.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.familyvisit.FamilyVisitAdd;

import com.inventics.ekalarogya.training.rest.response.FamilyVisitModel;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)

public class FamilyVisitListAdapter extends RecyclerView.Adapter<FamilyVisitListAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<FamilyVisitModel> FamilyVisitList;
    private static final String TAG = "OwnerListAdapter";
    private FamilyVisitListAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(FamilyVisitModel item);
    }

    public FamilyVisitListAdapter(Context context, List<FamilyVisitModel> FamilyVisitList) {
        this.mContaxt = context;
        this.FamilyVisitList=FamilyVisitList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FamilyVisitListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_family_visit, null);

        return new FamilyVisitListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final FamilyVisitListAdapter.ViewHolder holder, int position) {
        final FamilyVisitModel baseResponse = FamilyVisitList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_village_Name.setText(baseResponse.getVillage_name());
        holder.tv_month.setText(baseResponse.getInspection_date());
//        holder.tv_treatment.setText(baseResponse.getTreatment_type());
        int count=Integer.parseInt(baseResponse.getChildren())+Integer.parseInt(baseResponse.getChildren())+Integer.parseInt(baseResponse.getChildren());
        holder.tv_total_patient.setText(""+count);
//        holder.tv_total_cured.setText(baseResponse.getTotal_cured());


        holder.bind(FamilyVisitList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+FamilyVisitList.size());
        return FamilyVisitList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_village_Name,tv_month,tv_treatment,tv_total_patient,tv_total_cured;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_village_Name=itemView.findViewById(R.id.tv_village_Name);
            tv_month=itemView.findViewById(R.id.tv_month);
//            tv_treatment=itemView.findViewById(R.id.tv_treatment);
            tv_total_patient=itemView.findViewById(R.id.tv_total_patient);
//            tv_total_cured=itemView.findViewById(R.id.tv_total_cured);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);



        }


        public void bind(FamilyVisitModel FamilyVisitList, FamilyVisitListAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+FamilyVisitList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, FamilyVisitAdd.class);
                    intent.putExtra("m_data", FamilyVisitList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+FamilyVisitList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, FamilyVisitAdd.class);
                    intent.putExtra("m_data", FamilyVisitList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}

