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
import com.inventics.ekalarogya.training.models.VillageFamilyModel;

import java.util.List;

public class MedicineGardenFamilyAdapter extends RecyclerView.Adapter<MedicineGardenFamilyAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<VillageFamilyModel> villageFamilyModelList;
    private static final String TAG = "OwnerListAdapter";
    private MedicineGardenFamilyAdapter.OnItemClickListener listener;
    String headid,v_id;



    public interface OnItemClickListener {
        void onItemClick(VillageFamilyModel item);
    }

    public MedicineGardenFamilyAdapter(Context context,String v_id, List<VillageFamilyModel> villageFamilyModelList) {
        this.mContaxt = context;
        this.villageFamilyModelList=villageFamilyModelList;
        this.listener = listener;
        this.v_id=v_id;
    }

    @NonNull
    @Override
    public MedicineGardenFamilyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new MedicineGardenFamilyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MedicineGardenFamilyAdapter.ViewHolder holder, int position) {
        final VillageFamilyModel baseResponse = villageFamilyModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(" "+" "+baseResponse.getFamily_head());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(villageFamilyModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+villageFamilyModelList.size());
        return villageFamilyModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView viewdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            viewdata=itemView.findViewById(R.id.viewdata);


        }


        public void bind(VillageFamilyModel villageFamilyModelList, MedicineGardenFamilyAdapter.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: v_id"+v_id+"\n");
                    Intent intent = new Intent(mContaxt, AddMedicineGarden.class);
                    intent.putExtra("h_data",villageFamilyModelList);
                    intent.putExtra("type", "add");
                    intent.putExtra("v_id", v_id);
                    Log.d(TAG, "onClick headid: "+villageFamilyModelList.toString());
                    mContaxt.startActivity(intent);

                }
            });




        }
    }



}
