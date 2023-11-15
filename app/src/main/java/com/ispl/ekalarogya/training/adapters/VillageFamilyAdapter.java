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
import com.ispl.ekalarogya.training.main.dashotheractivity.family.AddFamily;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;

import java.util.List;

public class VillageFamilyAdapter extends RecyclerView.Adapter<VillageFamilyAdapter.ViewHolder> {

private final Context mContaxt;
private final List<VillageFamilyModel> villageFamilyModel;
private static final String TAG = "OwnerListAdapter";
private VillageFamilyAdapter.OnItemClickListener listener;



public interface OnItemClickListener {
    void onItemClick(VillageFamilyModel item);
}

    public VillageFamilyAdapter(Context context, List<VillageFamilyModel> villageFamilyModel) {
        this.mContaxt = context;
        this.villageFamilyModel=villageFamilyModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VillageFamilyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_village_family, null);

        return new VillageFamilyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VillageFamilyAdapter.ViewHolder holder, int position) {
        final VillageFamilyModel baseResponse = villageFamilyModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_family_id.setText(baseResponse.getFamily_head_id());
        holder.tv_family_head.setText(baseResponse.getFamily_head());
        holder.tv_total_member.setText(baseResponse.getFamily_member_count());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(villageFamilyModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+villageFamilyModel.size());
        return villageFamilyModel.size();
    }

class ViewHolder extends RecyclerView.ViewHolder{
    TextView tv_family_id,tv_family_head,tv_total_member;
    ImageView editpen,vieweye;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_family_id=itemView.findViewById(R.id.tv_family_id);
        tv_family_head=itemView.findViewById(R.id.tv_family_head);
        tv_total_member=itemView.findViewById(R.id.tv_total_member);
        editpen=itemView.findViewById(R.id.editbtn);
        vieweye=itemView.findViewById(R.id.viewbtn);



    }


    public void bind(VillageFamilyModel villageFamilyModel, VillageFamilyAdapter.OnItemClickListener listener, int position) {
        editpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: edit"+villageFamilyModel.getFamily_head_id()+"\n");;
                Intent intent = new Intent(mContaxt, AddFamily.class);
                intent.putExtra("id", villageFamilyModel.getFamily_head_id());
                intent.putExtra("type", "edit");
                mContaxt.startActivity(intent);
            }
        });

        vieweye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: view"+villageFamilyModel.getFamily_head_id()+"\n");
                Intent intent = new Intent(mContaxt, AddFamily.class);
                intent.putExtra("id", villageFamilyModel.getFamily_head_id());
                intent.putExtra("type", "view");
                mContaxt.startActivity(intent);

            }
        });

    }
}



}
