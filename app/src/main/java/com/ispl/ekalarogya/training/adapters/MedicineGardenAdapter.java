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
import com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.medicinegarden.SingleFamilyGarden;
import com.ispl.ekalarogya.training.models.MedicineGardenModel;

import java.util.List;

public class MedicineGardenAdapter extends RecyclerView.Adapter<MedicineGardenAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<MedicineGardenModel> medicineGardenModelList;
    private static final String TAG = "OwnerListAdapter";
    private MedicineGardenAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(MedicineGardenModel item);
    }

    public MedicineGardenAdapter(Context context, List<MedicineGardenModel> medicineGardenModelList) {
        this.mContaxt = context;
        this.medicineGardenModelList=medicineGardenModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineGardenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_medicine_garden, null);

        return new MedicineGardenAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MedicineGardenAdapter.ViewHolder holder, int position) {
        final MedicineGardenModel baseResponse = medicineGardenModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_name.setText(baseResponse.getName());
       holder.tv_id.setText(String.valueOf(position+1));

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(medicineGardenModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+medicineGardenModelList.size());
        return medicineGardenModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_name;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_name=itemView.findViewById(R.id.tv_householder);

            editpen=itemView.findViewById(R.id.editbtn);
//            vieweye=itemView.findViewById(R.id.viewbtn);
        }


        public void bind(MedicineGardenModel medicineGardenModelList, MedicineGardenAdapter.OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+medicineGardenModelList.getHead_id()+"\n");;
                    Intent intent = new Intent(mContaxt, SingleFamilyGarden.class);
                    intent.putExtra("m_data", medicineGardenModelList);
                    intent.putExtra("type", "10");
                    mContaxt.startActivity(intent);
                }
            });



        }
    }



}

