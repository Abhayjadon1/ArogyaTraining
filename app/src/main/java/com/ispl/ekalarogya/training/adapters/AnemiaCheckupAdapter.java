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
import com.ispl.ekalarogya.training.models.AnemiaCheckupModel;

import java.util.List;

public class AnemiaCheckupAdapter extends RecyclerView.Adapter<AnemiaCheckupAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<AnemiaCheckupModel> anemiaCheckupModel;
    private static final String TAG = "OwnerListAdapter";
    private AnemiaCheckupAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(AnemiaCheckupModel item);
    }

    public AnemiaCheckupAdapter(Context context, List<AnemiaCheckupModel> anemiaCheckupModel) {
        this.mContaxt = context;
        this.anemiaCheckupModel=anemiaCheckupModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnemiaCheckupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_anemia_in_pregnancy, null);

        return new AnemiaCheckupAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AnemiaCheckupAdapter.ViewHolder holder, int position) {
        final AnemiaCheckupModel baseResponse = anemiaCheckupModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_name.setText(baseResponse.getName());
        holder.tv_date.setText(baseResponse.getInspection_date());
        holder.tv_anemia_test.setText(baseResponse.getAnemia_test());
        holder.tv_is_anemic.setText(baseResponse.getIs_anemic());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).error(R.drawable.add_image).into(holder.dash_imagee);

        holder.bind(anemiaCheckupModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+anemiaCheckupModel.size());
        return anemiaCheckupModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_date,tv_name,tv_anemia_test,tv_is_anemic;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_anemia_test=itemView.findViewById(R.id.tv_anemia_test);
            tv_is_anemic=itemView.findViewById(R.id.tv_anemic);
            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(AnemiaCheckupModel anemiaCheckupModel, AnemiaCheckupAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+anemiaCheckupModel.getHead_id()+"\n");;
                    Intent i =new Intent(mContaxt, AnemiaCheckupAdd.class);
                    i.putExtra("m_data",anemiaCheckupModel);
                    i.putExtra("type","edit");
                    mContaxt.startActivity(i);

                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+anemiaCheckupModel.getHead_id()+"\n");;
                    Intent i =new Intent(mContaxt, AnemiaCheckupAdd.class);
                    i.putExtra("m_data",anemiaCheckupModel);
                    i.putExtra("type","edit");
                    mContaxt.startActivity(i);
                }
            });

        }
    }



}
