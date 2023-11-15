package com.ispl.ekalarogya.training.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.models.PlantModel;
import com.ispl.ekalarogya.training.utils.ExtraUtils;
import com.ispl.ekalarogya.training.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantListSlectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final static int PIC_CLICK_REQUST=111;
    private List<PlantModel> planList=new ArrayList<>();
    private boolean on_attach = true;
    private Context context;
    int position = 0,index;
    String imgurl=null;
    int count=0;

    PlantListSlectedAdapter plantListSlectedAdapter;



    public interface  OnItemClickListner{
        void onItemClick(PlantModel PlantModel);

//        void onItemClick(PlantModel PlantModel, String type, int position, int count);
    }
    public PlantListSlectedAdapter.OnItemClickListner onItemClickListner;
    public PlantListSlectedAdapter(Context context, List<PlantModel> planLis, PlantListSlectedAdapter.OnItemClickListner onItemClickListner) {
        this.context = context;
//        this.planList = planList;

        this.onItemClickListner = onItemClickListner;
        for (int i=0;i<planLis.size();i++){
            if (planLis.get(i).isSelect()){
                planList.add(planLis.get(i));
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_plant, parent, false);
        return new PlantListSlectedAdapter.PlanViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PlantListSlectedAdapter.PlanViewHolder) holder).bindData(planList.get(position),position);


    }


    @Override
    public int getItemCount() {
        return planList.size();
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
        TextView namePlant;
        @BindView(R.id.noPlant)
        EditText countPlant;


        @BindView(R.id.addImage)
        ImageView addImage;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindData(PlantModel plantModel, int position) {
//            bottom_ll.setVisibility(View.GONE);
            namePlant.setText(plantModel.getPlant_name().toString());

             countPlant.setText(""+plantModel.getCount());

//            Log.d("TAG", "bindData: "+plantModel.getPlant_img());
            if (plantModel.isSelect()&&plantModel.getCount()>0&&plantModel.getPlant_img()!=null) {
//
                Picasso.with(context).load(ExtraUtils.baseImg+plantModel.getPlant_img()).error(R.id.addImage).into(addImage);
            }
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countPlant.getText().toString()!=null&& Integer.parseInt(countPlant.getText().toString())>0){
                        count=Integer.parseInt(String.valueOf(countPlant.getText()));
//                       plantModel.setCount(count);
//                        notifyDataSetChanged();
                        Log.d("TAG", "onClick: "+position+plantModel.toString());
                        if(onItemClickListner!=null) {

                            plantModel.setCount(count);
                            onItemClickListner.onItemClick(plantModel);
                        }
                    }else {
                        ToastUtils.shortToast(context.getResources().getString(R.string.add_some_plant));
                    }

                }
            });

//            list_Image_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    plantModelList.setPlant_name(null);
//                    Log.d("TAG", "onClick: dele"+plantModelList.toString());
//                    images.setImageResource(R.drawable.add_image);
//                    list_Image_delete.setVisibility(View.GONE);
//
//                }
//            });

        }

    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
