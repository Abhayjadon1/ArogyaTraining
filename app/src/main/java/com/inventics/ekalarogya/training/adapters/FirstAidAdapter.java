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
import com.inventics.ekalarogya.training.models.FirstAidModel;

import java.util.List;

public class FirstAidAdapter  extends RecyclerView.Adapter<FirstAidAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<FirstAidModel> firstAidModelList;
    private static final String TAG = "OwnerListAdapter";
    private FirstAidAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(FirstAidModel item);
    }

    public FirstAidAdapter(Context context, List<FirstAidModel> firstAidModelList) {
        this.mContaxt = context;
        this.firstAidModelList=firstAidModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FirstAidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_first_aid, null);

        return new FirstAidAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final FirstAidAdapter.ViewHolder holder, int position) {
        final FirstAidModel baseResponse = firstAidModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
        String traingList="";
//
        if (baseResponse.getCuts()!=null && baseResponse.getCuts().equalsIgnoreCase("1"))
            traingList=traingList+" "+mContaxt.getResources().getString(R.string.cuts)+",";
        if (baseResponse.getHeart_attacks()!=null && baseResponse.getHeart_attacks().equalsIgnoreCase("1"))
            traingList=traingList+" "+mContaxt.getResources().getString(R.string.heart_attack)+",";
        if (baseResponse.getUnconscious()!=null && baseResponse.getUnconscious().equalsIgnoreCase("1"))
            traingList=traingList+" "+mContaxt.getResources().getString(R.string.unconsc)+",";
        if (baseResponse.getSnake_bites()!=null &&  baseResponse.getSnake_bites().equalsIgnoreCase("1"))
            traingList=traingList+" "+mContaxt.getResources().getString(R.string.snake_bite)+",";
        if (baseResponse.getOther()!=null && baseResponse.getOther().equalsIgnoreCase("1"))
            traingList=traingList+" "+mContaxt.getResources().getString(R.string.other)+"";

        Log.d(TAG, "onBindViewHolder: "+traingList);


        if(baseResponse.getPatient_name()!=null){
            holder.tv_name.setText(baseResponse.getPatient_name());
        }else {
            holder.tv_name.setText(baseResponse.getGuest_patient_name());
        }

        holder.tv_id.setText(String.valueOf(position+1));;
        holder.tv_emergency_training.setText(traingList);

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(firstAidModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+firstAidModelList.size());
        return firstAidModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_name,tv_emergency_training;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_name=itemView.findViewById(R.id.tv_householder);
            tv_emergency_training=itemView.findViewById(R.id.tv_emergency_training);

            editpen=itemView.findViewById(R.id.editbtn);
//            vieweye=itemView.findViewById(R.id.viewbtn);
        }


        public void bind(FirstAidModel firstAidModelList, FirstAidAdapter.OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    FirstAidAdd.openActitvity(firstAidModelList,"view");
                    Log.d(TAG, "onClick: edit"+firstAidModelList.getHead_id()+"\n");;
                    Intent intent = new Intent(mContaxt, FirstAidAdd.class);
                    Log.d(TAG, "onClick: hjgf: "+firstAidModelList.toString());
                    intent.putExtra("m_data", firstAidModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });



        }
    }



}


