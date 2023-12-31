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
import com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition.animea.AnemiaCheckupAdd;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;

import java.util.List;

public class AnemiaFamilyMemberAdapter   extends RecyclerView.Adapter<AnemiaFamilyMemberAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<FamilyMembersModel> familyMemberListModelList;
    private static final String TAG = "OwnerListAdapter";
    private AnemiaFamilyMemberAdapter.OnItemClickListener listener;
    String headid,v_id;



    public interface OnItemClickListener {
        void onItemClick(FamilyMembersModel item);
    }

    public AnemiaFamilyMemberAdapter(Context context,  List<FamilyMembersModel>familyMemberListModelList) {
        this.mContaxt = context;
        this.familyMemberListModelList=familyMemberListModelList;
        this.listener = listener;
//        this.headid=head_id;
//        this.v_id=vid;
    }

    @NonNull
    @Override
    public AnemiaFamilyMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new AnemiaFamilyMemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnemiaFamilyMemberAdapter.ViewHolder holder, int position) {
        final FamilyMembersModel baseResponse = familyMemberListModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(" "+baseResponse.getName() +"   ");

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).error(R.drawable.add_image).into(holder.dash_imagee);

        holder.bind(familyMemberListModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+familyMemberListModelList.size());
        return familyMemberListModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView viewdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            viewdata=itemView.findViewById(R.id.viewdata);


        }


        public void bind(FamilyMembersModel familyMemberListModelList, AnemiaFamilyMemberAdapter.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContaxt, AnemiaCheckupAdd.class);
                    intent.putExtra("m_data",familyMemberListModelList);
//                    intent.putExtra("v_id",v_id);
//                    intent.putExtra("h_id",headid);
                    intent.putExtra("type","add");
                    Log.d(TAG, "onClick: member"+headid+" "+familyMemberListModelList.getId());
                    mContaxt.startActivity(intent);
                }
            });
//            viewdata.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContaxt, PersonalHygineCheckup.class);
//                    intent.putExtra("m_data",familyMemberListModelList);
//                    intent.putExtra("h_id",headid);
//                    intent.putExtra("type","view");
//                    Log.d(TAG, "onClick: "+headid+" "+familyMemberListModelList.getId());
//                    mContaxt.startActivity(intent);
//
//                }
//            });




        }
    }



}
