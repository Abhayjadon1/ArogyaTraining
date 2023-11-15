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
import com.ispl.ekalarogya.training.main.dashotheractivity.family.AddFamilyMember;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;

import java.util.List;

public class VillageFamilymemberAdapter extends RecyclerView.Adapter<VillageFamilymemberAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<FamilyMembersModel> familyMembersModelList;
    private static final String TAG = "OwnerListAdapter";
    private VillageFamilymemberAdapter.OnItemClickListener listener;
    String headid,type;



    public interface OnItemClickListener {
        void onItemClick(FamilyMembersModel item);
    }

    public VillageFamilymemberAdapter(Context context,String type,String head_id, List<FamilyMembersModel> familyMembersModelList) {
        this.mContaxt = context;
        this.familyMembersModelList=familyMembersModelList;
        this.listener = listener;
        this.headid=head_id;
        this.type=type;
        Log.d(TAG, "VillageFamilymemberAdapter: type : "+type);
    }

    @NonNull
    @Override
    public VillageFamilymemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new VillageFamilymemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VillageFamilymemberAdapter.ViewHolder holder, int position) {
        final FamilyMembersModel baseResponse = familyMembersModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(baseResponse.getName());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(familyMembersModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+familyMembersModelList.size());
        return familyMembersModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView viewdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            viewdata=itemView.findViewById(R.id.viewdata);


        }


        public void bind(FamilyMembersModel familyMembersModelList, VillageFamilymemberAdapter.OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+headid+"\n");
                    Intent intent = new Intent(mContaxt, AddFamilyMember.class);
                    intent.putExtra("h_id",headid);
                    intent.putExtra("m_data",familyMembersModelList);
                    intent.putExtra("type", type);
                    Log.d(TAG, "onClick: "+headid);
                    mContaxt.startActivity(intent);

                }
            });




//            editbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "onClick: view"+headid+"\n");
//                    Intent intent = new Intent(mContaxt, AddFamily.class);
//                    intent.putExtra("h_id",headid);
//                    intent.putExtra("m_data",familyMembersModelList);
//                    intent.putExtra("type", "view");
//                    mContaxt.startActivity(intent);
//
//                }
//            });

        }
    }



}
