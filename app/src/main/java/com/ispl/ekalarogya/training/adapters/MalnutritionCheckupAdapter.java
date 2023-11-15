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
import com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition.malnutrion.MalnutrionCheckupAdd;
import com.ispl.ekalarogya.training.models.MalnutrinCheckupModelResponse;
import com.ispl.ekalarogya.training.models.VillageListModel;

import java.util.List;

public class MalnutritionCheckupAdapter  extends RecyclerView.Adapter<MalnutritionCheckupAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<MalnutrinCheckupModelResponse> malnutrinCheckupModelResponseList;
    private static final String TAG = "OwnerListAdapter";
    private MalnutritionCheckupAdapter.OnItemClickListener listener;
    VillageListModel v_data;


    public interface OnItemClickListener {
        void onItemClick(MalnutrinCheckupModelResponse item);
    }

    public MalnutritionCheckupAdapter(Context context, VillageListModel v_data, List<MalnutrinCheckupModelResponse> malnutrinCheckupModelResponseList) {
        this.mContaxt = context;
        this.malnutrinCheckupModelResponseList=malnutrinCheckupModelResponseList;
        this.listener = listener;
        this.v_data=v_data;
    }

    @NonNull
    @Override
    public MalnutritionCheckupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_malnurished, null);

        return new MalnutritionCheckupAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MalnutritionCheckupAdapter.ViewHolder holder, int position) {
        final MalnutrinCheckupModelResponse baseResponse = malnutrinCheckupModelResponseList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_name.setText(baseResponse.getName());
        holder.tv_date.setText(baseResponse.getDate());
        if(baseResponse.getMalnourished()!=null &&
                baseResponse.getMalnourished().equalsIgnoreCase("yes")){
            holder.tv_malnourished.setText(mContaxt.getResources().getString(R.string.yes));
        }else{
            holder.tv_malnourished.setText(mContaxt.getResources().getString(R.string.no));
        }
        holder.tv_id.setText(String.valueOf(position+1));

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(malnutrinCheckupModelResponseList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+malnutrinCheckupModelResponseList.size());
        return malnutrinCheckupModelResponseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_date,tv_name,tv_malnourished;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_name=itemView.findViewById(R.id.tv_children_name);
            tv_malnourished=itemView.findViewById(R.id.tv_malnourished);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(MalnutrinCheckupModelResponse malnutrinCheckupModelResponseList, MalnutritionCheckupAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+malnutrinCheckupModelResponseList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, MalnutrionCheckupAdd.class);
                    intent.putExtra("m_data", malnutrinCheckupModelResponseList);
                    intent.putExtra("v_data", v_data);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+malnutrinCheckupModelResponseList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, MalnutrionCheckupAdd.class);
                    intent.putExtra("m_data", malnutrinCheckupModelResponseList);
                    intent.putExtra("v_data", v_data);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);
                }
            });


        }
    }



}
