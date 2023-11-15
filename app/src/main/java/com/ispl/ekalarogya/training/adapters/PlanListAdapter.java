package com.ispl.ekalarogya.training.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.models.PlantModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final static int PIC_CLICK_REQUST=111;
    private List<PlantModel> plantModel;
    private boolean on_attach = true;
    private Context context;
    int position = 0,indexId;
    boolean select=false;
    String data="0";
    PlanListAdapter PlanListAdapter;

    public interface  OnItemClickListner{
        void onItemClick(PlantModel PlantModel, String type,int data);
    }
    public PlanListAdapter.OnItemClickListner onItemClickListner;
    public PlanListAdapter(Context context, List<PlantModel> plantModel, PlanListAdapter.OnItemClickListner onItemClickListner) {
        this.context = context;
        this.plantModel = plantModel;
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_plant_list, parent, false);
        return new PlanListAdapter.PlanViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PlanListAdapter.PlanViewHolder) holder).bindData(plantModel.get(position));


    }


    @Override
    public int getItemCount() {
        return plantModel.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d("TAG", "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }


    class PlanViewHolder extends RecyclerView.ViewHolder {
        private static final int BUY_NOW_REQ_CODE = 2;
        @BindView(R.id.namePlant)
        TextView tvName;


        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindData(PlantModel plantModelList) {
//            bottom_ll.setVisibility(View.GONE);
            tvName.setText(plantModelList.getPlant_name().toString());
//            Log.d("TAG", "bindData: "+plantModelList.getImgs());
            if(plantModelList.isSelect()){
                tvName.setBackgroundResource(R.color.colorPrimaryDark);
                tvName.setTextColor(R.color.white);
                plantModelList.setSelect(true);
                data="1";
            }
            tvName.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    if (plantModelList.isSelect()){
                        tvName.setBackgroundResource(R.color.white);
                        tvName.setTextColor(R.color.black);
                        plantModelList.setSelect(false);
                        data="0";
                    }else if(!(plantModelList.isSelect())){
                        tvName.setBackgroundResource(R.color.colorPrimaryDark);
                        tvName.setTextColor(R.color.white);
                        plantModelList.setSelect(true);
                        data="1";
                    }
                    Log.d("TAG", "onClick: "+data);
                    if(onItemClickListner!=null) {
                        onItemClickListner.onItemClick(plantModelList,"plantList",position);

                    }
                }
            });

           
        }

    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
