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
import com.ispl.ekalarogya.training.main.dashotheractivity.preventives.socialSanitisation.SocialSanitiasationCheck;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;

import java.util.List;

public class SocialSanitisationFamilyAdapter  extends RecyclerView.Adapter<SocialSanitisationFamilyAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<VillageFamilyModel> villageFamilyModelList;
    private static final String TAG = "OwnerListAdapter";
    private SocialSanitisationFamilyAdapter.OnItemClickListener listener;
    String headid,vill_id;



    public interface OnItemClickListener {
        void onItemClick(VillageFamilyModel item);
    }

    public SocialSanitisationFamilyAdapter(Context context,String vill_id, List<VillageFamilyModel> villageFamilyModelList) {
        this.mContaxt = context;
        this.villageFamilyModelList=villageFamilyModelList;
        this.listener = listener;
        this.vill_id=vill_id;
    }

    @NonNull
    @Override
    public SocialSanitisationFamilyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new SocialSanitisationFamilyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SocialSanitisationFamilyAdapter.ViewHolder holder, int position) {
        final VillageFamilyModel baseResponse = villageFamilyModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(baseResponse.getFamily_head());

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


        public void bind(VillageFamilyModel villageFamilyModelList, SocialSanitisationFamilyAdapter.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+headid+"\n");
                    Intent intent = new Intent(mContaxt, SocialSanitiasationCheck.class);
                    intent.putExtra("m_data",villageFamilyModelList);
                    intent.putExtra("type", "add");
                    intent.putExtra("v_id", vill_id);
                    Log.d(TAG, "onClick: "+vill_id +" "+villageFamilyModelList.getFamily_head_id());
                    mContaxt.startActivity(intent);

                }
            });




        }
    }



}
