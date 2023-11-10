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
import com.inventics.ekalarogya.training.main.dashotheractivity.herbalremedies.medicinegarden.AddMedicineGarden;
import com.inventics.ekalarogya.training.models.MedicineGardenModel;

import java.util.List;

public class SinglePlantMedFamAdapter  extends RecyclerView.Adapter<SinglePlantMedFamAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<MedicineGardenModel> medicineGardenModelList;
    private static final String TAG = "OwnerListAdapter";
    private SinglePlantMedFamAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(MedicineGardenModel item);
    }

    public SinglePlantMedFamAdapter(Context context, List<MedicineGardenModel> medicineGardenModelList) {
        this.mContaxt = context;
        this.medicineGardenModelList=medicineGardenModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SinglePlantMedFamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_single_fam_med_harden, null);

        return new SinglePlantMedFamAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final SinglePlantMedFamAdapter.ViewHolder holder, int position) {
        final MedicineGardenModel baseResponse = medicineGardenModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
        String plant_names="";
        if (baseResponse.getPlantModelList()!=null&&baseResponse.getPlantModelList().size()>0){
            for (int i=0;i<baseResponse.getPlantModelList().size();i++){
                plant_names=plant_names+baseResponse.getPlantModelList().get(i).getPlant_name()+",";
            }
        }


        holder.tv_plants.setText(plant_names);
//        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_id.setText(baseResponse.getMonth());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(medicineGardenModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+medicineGardenModelList.size());
        return medicineGardenModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_plants;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_plants=itemView.findViewById(R.id.tv_plants);

            editpen=itemView.findViewById(R.id.editbtn);
//            vieweye=itemView.findViewById(R.id.viewbtn);
//            vieweye.setVisibility(View.GONE);
        }


        public void bind(MedicineGardenModel medicineGardenModel, SinglePlantMedFamAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContaxt, AddMedicineGarden.class);
                    intent.putExtra("type","edit");
                    intent.putExtra("m_data", medicineGardenModel);
                    Log.d(TAG, "onClick: edit :: "+medicineGardenModel+"\n");;

                    mContaxt.startActivity(intent);

                }
            });



        }
    }



}

