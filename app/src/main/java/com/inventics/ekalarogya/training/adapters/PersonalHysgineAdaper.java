package com.inventics.ekalarogya.training.adapters;

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

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.hygiene.PersonalHygineCheckup;
import com.inventics.ekalarogya.training.models.PersonalHygineListModel;

import java.util.List;

public class PersonalHysgineAdaper  extends RecyclerView.Adapter<PersonalHysgineAdaper.ViewHolder> {

    private final Context mContaxt;
    private final List<PersonalHygineListModel> personalHygineListModels;
    private static final String TAG = "OwnerListAdapter";
    private PersonalHysgineAdaper.OnItemClickListener listener;
    String nailCheck,healthCheck;



    public interface OnItemClickListener {
        void onItemClick(PersonalHygineListModel item);
    }

    public PersonalHysgineAdaper(Context context, List<PersonalHygineListModel> personalHygineListModels) {
        this.mContaxt = context;
        this.personalHygineListModels=personalHygineListModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonalHysgineAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_personal_hygiene, null);

        return new PersonalHysgineAdaper.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonalHysgineAdaper.ViewHolder holder, int position) {
        final PersonalHygineListModel baseResponse = personalHygineListModels.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_family_head.setText(baseResponse.getName());
        holder.tv_date.setText(baseResponse.getInspection_date());
        nailCheck=baseResponse.getNail();
        healthCheck=baseResponse.getHealth();

        if (nailCheck!=null&&nailCheck.equalsIgnoreCase("Yes")){
          holder.nailCb.setChecked(true);
        }else{
            holder.nailCb.setChecked(false);

        }
        if (healthCheck!=null&&healthCheck.equalsIgnoreCase("Yes")){
            holder.healthCb.setChecked(true);
        }else{
            holder.healthCb.setChecked(false);

        }
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(personalHygineListModels.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+personalHygineListModels.size());
        return personalHygineListModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_date,tv_family_head,tv_hygiene_checkup;
        CheckBox nailCb,healthCb;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_family_head=itemView.findViewById(R.id.tv_family_head);
            tv_date=itemView.findViewById(R.id.tv_date);
            nailCb=itemView.findViewById(R.id.nailCb);
            healthCb=itemView.findViewById(R.id.healthCb);
//            tv_hygiene_checkup=itemView.findViewById(R.id.tv_hygiene_checkup);
            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(PersonalHygineListModel personalHygineListModels, PersonalHysgineAdaper.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+personalHygineListModels.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, PersonalHygineCheckup.class);
                    intent.putExtra("m_data", personalHygineListModels);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+personalHygineListModels.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, PersonalHygineCheckup.class);
                    intent.putExtra("m_data", personalHygineListModels);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}
