package com.ispl.ekalarogya.training.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.main.dashotheractivity.preventives.socialSanitisation.SocialSanitiasationCheck;
import com.ispl.ekalarogya.training.models.SocialSanitisationModel;

import java.util.List;

public class SocialSanitisationAdapter extends RecyclerView.Adapter<SocialSanitisationAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<SocialSanitisationModel> socialSanitisationModelList;
    private static final String TAG = "OwnerListAdapter";
    private SocialSanitisationAdapter.OnItemClickListener listener;
    String soakpit,waterTreat,toilet,compost;



    public interface OnItemClickListener {
        void onItemClick(SocialSanitisationModel item);
    }

    public SocialSanitisationAdapter(Context context, List<SocialSanitisationModel> socialSanitisationModelList) {
        this.mContaxt = context;
        this.socialSanitisationModelList=socialSanitisationModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SocialSanitisationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_social_sanitation, null);

        return new SocialSanitisationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SocialSanitisationAdapter.ViewHolder holder, int position) {
        final SocialSanitisationModel baseResponse = socialSanitisationModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_family_head.setText(baseResponse.getHead_name());
        holder.tv_date.setText(baseResponse.getInspection_date());
        waterTreat=baseResponse.getWater_treatment();
        soakpit=baseResponse.getSoakpit();
        compost=baseResponse.getCompost_pit();
        toilet=baseResponse.getToilets();
        Log.d(TAG, "onBindViewHolder: "+waterTreat+" "+soakpit+" "+compost+" "+toilet);

        if (waterTreat!=null&&waterTreat.equalsIgnoreCase("water treatment")){
            holder.cbWaterTreatment.setChecked(true);
        }
        if (soakpit!=null&&soakpit.contains("Soakpit")){
            holder.cbSoakpit.setChecked(true);
        }
        if (compost!=null&&compost.equalsIgnoreCase("Compost Pit")){
            holder.cbCompost.setChecked(true);
        }
        if (toilet!=null&&toilet.equalsIgnoreCase("toilets")){
            holder.cbtoilets.setChecked(true);
        }

        if (holder.cbCompost.isChecked()&&holder.cbSoakpit.isChecked()&&holder.cbtoilets.isChecked()&&holder.cbWaterTreatment.isChecked()){
            holder.editpen.setVisibility(View.GONE);
        }else {
            holder.editpen.setVisibility(View.VISIBLE);
        }
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(socialSanitisationModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+socialSanitisationModelList.size());
        return socialSanitisationModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_date,tv_family_head,tv_hygiene_checkup;
        CheckBox cbtoilets,cbSoakpit,cbCompost,cbWaterTreatment;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_family_head=itemView.findViewById(R.id.tv_family_head);
            tv_date=itemView.findViewById(R.id.tv_date);
            cbtoilets=itemView.findViewById(R.id.cbtoilets);
            cbSoakpit=itemView.findViewById(R.id.cbSoakpit);
            cbCompost=itemView.findViewById(R.id.cbCompost);
            cbWaterTreatment=itemView.findViewById(R.id.cbwaterTreatment);
            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(SocialSanitisationModel socialSanitisationModelList, SocialSanitisationAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+socialSanitisationModelList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, SocialSanitiasationCheck.class);
                    intent.putExtra("m_data", socialSanitisationModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+socialSanitisationModelList.getHead_id()+"\n");;
                    Intent intent = new Intent(mContaxt, SocialSanitiasationCheck.class);
                    intent.putExtra("m_data", socialSanitisationModelList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}
