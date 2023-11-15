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
import com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition.animea.AnemiaFamily;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;

import java.util.List;

public class AnemiaCheckupFamilyAdapter  extends RecyclerView.Adapter<AnemiaCheckupFamilyAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<VillageFamilyModel> villageFamilyModelList;
    private static final String TAG = "OwnerListAdapter";
    private AnemiaCheckupFamilyAdapter.OnItemClickListener listener;
    String headid,v_id;



    public interface OnItemClickListener {
        void onItemClick(VillageFamilyModel item);
    }

    public AnemiaCheckupFamilyAdapter(Context context,String v_id, List<VillageFamilyModel> villageFamilyModelList) {
        this.mContaxt = context;
        this.villageFamilyModelList=villageFamilyModelList;
        this.listener = listener;
        this.v_id=v_id;
    }

    @NonNull
    @Override
    public AnemiaCheckupFamilyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new AnemiaCheckupFamilyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnemiaCheckupFamilyAdapter.ViewHolder holder, int position) {
        final VillageFamilyModel baseResponse = villageFamilyModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(" "+" "+baseResponse.getFamily_head());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).error(R.drawable.add_image).into(holder.dash_imagee);

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


        public void bind(VillageFamilyModel villageFamilyModelList, AnemiaCheckupFamilyAdapter.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: v_id"+v_id+"\n");
                    Intent intent = new Intent(mContaxt, AnemiaFamily.class);
                    intent.putExtra("h_id",villageFamilyModelList.getFamily_head_id());
                    intent.putExtra("type", "head");
                    intent.putExtra("v_id", v_id);
                    Log.d(TAG, "onClick headid: "+villageFamilyModelList.getFamily_head_id());
                    mContaxt.startActivity(intent);

                }
            });




        }
    }



}
