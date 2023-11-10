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
import com.inventics.ekalarogya.training.main.dashotheractivity.firstaid.FirstAidAdd;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;

import java.util.List;

public class FirstAidFamilyMemberAdapter extends RecyclerView.Adapter<FirstAidFamilyMemberAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<FamilyMembersModel> familyMemberListModelList;
    private static final String TAG = "OwnerListAdapter";
    private FirstAidFamilyMemberAdapter.OnItemClickListener listener;
    String headid;



    public interface OnItemClickListener {
        void onItemClick(FamilyMembersModel item);
    }

    public FirstAidFamilyMemberAdapter(Context context, String head_id, List<FamilyMembersModel>familyMemberListModelList) {
        this.mContaxt = context;
        this.familyMemberListModelList=familyMemberListModelList;
        this.listener = listener;
        this.headid=head_id;
    }

    @NonNull
    @Override
    public FirstAidFamilyMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new FirstAidFamilyMemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FirstAidFamilyMemberAdapter.ViewHolder holder, int position) {
        final FamilyMembersModel baseResponse = familyMemberListModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(" "+baseResponse.getName() +"   ");

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

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


        public void bind(FamilyMembersModel familyMemberListModelList, FirstAidFamilyMemberAdapter.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContaxt, FirstAidAdd.class);
                    intent.putExtra("m_data",familyMemberListModelList);
//                    intent.putExtra("v_id",headid);
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
