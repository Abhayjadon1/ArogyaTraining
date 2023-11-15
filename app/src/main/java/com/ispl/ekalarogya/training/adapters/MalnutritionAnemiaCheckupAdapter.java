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
import com.ispl.ekalarogya.training.models.MalnutrinAnemiaCheckupModelResponse;

import java.util.List;

public class MalnutritionAnemiaCheckupAdapter  extends RecyclerView.Adapter<MalnutritionAnemiaCheckupAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<MalnutrinAnemiaCheckupModelResponse> malnutrinAnemiaCheckupModelResponseList;
    private static final String TAG = "OwnerListAdapter";
    private MalnutritionAnemiaCheckupAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(MalnutrinAnemiaCheckupModelResponse item);
    }

    public MalnutritionAnemiaCheckupAdapter(Context context, List<MalnutrinAnemiaCheckupModelResponse> malnutrinAnemiaCheckupModelResponseList) {
        this.mContaxt = context;
        this.malnutrinAnemiaCheckupModelResponseList=malnutrinAnemiaCheckupModelResponseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MalnutritionAnemiaCheckupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_anemia_in_pregnancy, null);

        return new MalnutritionAnemiaCheckupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MalnutritionAnemiaCheckupAdapter.ViewHolder holder, int position) {
        final MalnutrinAnemiaCheckupModelResponse baseResponse = malnutrinAnemiaCheckupModelResponseList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(String.valueOf(position+1));//baseResponse.getMember_id()
        holder.tv_date.setText(baseResponse.getDate());
        if (baseResponse.getAnemia_test()!=null && baseResponse.getAnemia_test().equalsIgnoreCase("Yes")){
            holder.tv_anemia_test.setText(mContaxt.getResources().getString(R.string.yes));
        }else{
            holder.tv_anemia_test.setText(mContaxt.getResources().getString(R.string.no));

        }

        holder.tv_name.setText(baseResponse.getName());

        holder.bind(malnutrinAnemiaCheckupModelResponseList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+malnutrinAnemiaCheckupModelResponseList.size());
        return malnutrinAnemiaCheckupModelResponseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_date,tv_name,tv_anemia_test,tv_anemic;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_anemia_test=itemView.findViewById(R.id.tv_anemia_test);
            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(MalnutrinAnemiaCheckupModelResponse malnutrinAnemiaCheckupModelResponseList, MalnutritionAnemiaCheckupAdapter.OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+malnutrinAnemiaCheckupModelResponseList.getId()+"\n");;
                    Intent intent = new Intent(mContaxt, AnemiaCheckupAdd.class);
                    intent.putExtra("m_data", malnutrinAnemiaCheckupModelResponseList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });

            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+malnutrinAnemiaCheckupModelResponseList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, AnemiaCheckupAdd.class);
                    intent.putExtra("m_data", malnutrinAnemiaCheckupModelResponseList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}
