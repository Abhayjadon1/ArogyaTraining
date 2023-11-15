package com.ispl.ekalarogya.training.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.rest.response.DiseasePreventionListResponse;

import java.util.List;

public class DiseasePreventionAdapter  extends RecyclerView.Adapter<DiseasePreventionAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<DiseasePreventionListResponse> villageFamilyModel;
    private static final String TAG = "OwnerListAdapter";
    private DiseasePreventionAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(DiseasePreventionListResponse item);
    }

    public DiseasePreventionAdapter(Context context, List<DiseasePreventionListResponse> villageFamilyModel) {
        this.mContaxt = context;
        this.villageFamilyModel=villageFamilyModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiseasePreventionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_disease_prevention, null);

        return new DiseasePreventionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiseasePreventionAdapter.ViewHolder holder, int position) {
        final DiseasePreventionListResponse baseResponse = villageFamilyModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(baseResponse.getInspection_date());
        holder.tv_village.setText(baseResponse.getVillage_name());
        String awareness="";
        if (baseResponse.getCleanliness()!=null&& !baseResponse.getCleanliness().equalsIgnoreCase("")) {
//            awareness = awareness + baseResponse.getCleanliness();
            awareness = awareness + mContaxt.getResources().getString(R.string.cleanliness);
        }
        if (baseResponse.getMalaria()!=null && !baseResponse.getMalaria().equalsIgnoreCase("") ) {
            awareness = awareness +", "+  mContaxt.getResources().getString(R.string.maleria);
        }
        if (baseResponse.getNutrition()!=null && !baseResponse.getNutrition().equalsIgnoreCase("")) {
            awareness = awareness + ", "+ mContaxt.getResources().getString(R.string.nutrition);
        }
        if (baseResponse.getMaternal_safety()!=null && !baseResponse.getMaternal_safety().equalsIgnoreCase("") ) {
            awareness = awareness +", "+  mContaxt.getResources().getString(R.string.maternal_saftey);
        }
        if (baseResponse.getChild_safety()!=null && !baseResponse.getChild_safety().equalsIgnoreCase("")) {
            awareness = awareness +", "+ mContaxt.getResources().getString(R.string.child_saftey);
        }

        holder.tv_awareness.setText(awareness);
        holder.tv_total_presense.setText(baseResponse.getTotal());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(villageFamilyModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+villageFamilyModel.size());
        return villageFamilyModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_village,tv_awareness,tv_total_presense;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_village=itemView.findViewById(R.id.tv_village);
            tv_awareness=itemView.findViewById(R.id.tv_awareness);
            tv_total_presense=itemView.findViewById(R.id.tv_total_presense);
            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(DiseasePreventionListResponse villageFamilyModel, DiseasePreventionAdapter.OnItemClickListener listener, int position) {
//            editpen.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "onClick: edit"+villageFamilyModel.getFamily_head_id()+"\n");;
//                    Intent intent = new Intent(mContaxt, AddFamily.class);
//                    intent.putExtra("id", villageFamilyModel.getFamily_head_id());
//                    intent.putExtra("type", "view");
//                    mContaxt.startActivity(intent);
//                }
//            });
//
//            vieweye.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "onClick: view"+villageFamilyModel.getFamily_head_id()+"\n");
//                    Intent intent = new Intent(mContaxt, AddFamily.class);
//                    intent.putExtra("id", villageFamilyModel.getFamily_head_id());
//                    intent.putExtra("type", "view");
//                    mContaxt.startActivity(intent);
//
//                }
//            });

        }
    }



}
