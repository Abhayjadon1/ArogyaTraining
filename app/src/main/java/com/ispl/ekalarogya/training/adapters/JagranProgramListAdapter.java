package com.ispl.ekalarogya.training.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.models.JagranProgramList;
import com.ispl.ekalarogya.training.utils.ExtraUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JagranProgramListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final static int PIC_CLICK_REQUST=111;
    private List<JagranProgramList> planList;
    private boolean on_attach = true;
    private Context context;
    int position = 0;
    JagranProgramListAdapter jagranProgramListAdapter;

    public interface  OnItemClickListner{

        void onItemClick(JagranProgramList JagranProgramList, boolean data);
    }
    public OnItemClickListner onItemClickListner;
    public JagranProgramListAdapter(Context context, List<JagranProgramList> planList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.planList = planList;
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jagaran_new, parent, false);
        return new JagranProgramListAdapter.PlanViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((JagranProgramListAdapter.PlanViewHolder) holder).bindData(planList.get(position));


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

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.llMore)
        LinearLayout llMore;
        @BindView(R.id.etCount)
        EditText etCount;
        @BindView(R.id.ivAdd)
        ImageView ivAdd;


        @BindView(R.id.cbYes)
        CheckBox cbYes;
        @BindView(R.id.cbNo)
        CheckBox cbNo;


        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindData(JagranProgramList jagranProgramList) {
//            bottom_ll.setVisibility(View.GONE);
            if (jagranProgramList.getProgram_title()!=null){
//
//                tvName.setText(jagranProgramList.getProgram_title());
                if (jagranProgramList.getProgram_title().equalsIgnoreCase("Health and cleanliness Jagran Rally")||
                        jagranProgramList.getProgram_title().equalsIgnoreCase(context.getResources().getString(R.string.health_clean_rally))){
                    tvName.setText(context.getResources().getString(R.string.health_clean_rally));
                }
            }
            if (jagranProgramList.getCurrent_present()!=null){
                etCount.setText(jagranProgramList.getCurrent_present());
            }


//            cbYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    cbYes.setChecked(true);
//                    cbNo.setChecked(false);
//                    llMore.setVisibility(View.VISIBLE);
//                    jagranProgramList.setSelect(true);
////                    if (onItemClickListner!=null){
////                        onItemClickListner.onItemClick(jagranProgramList,true);
////                    }
//                }
//            });
//            cbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    llMore.setVisibility(View.GONE);
//                    cbYes.setChecked(false);
//                    cbNo.setChecked(true);
//                    jagranProgramList.setSelect(false);
////                    if (onItemClickListner!=null){
////                        onItemClickListner.onItemClick(jagranProgramList,false);
////                    }
//
//                }
//            });
            Log.d("TAG", "bindData: "+ ExtraUtils.baseImg+jagranProgramList.getImage());
            Picasso.with(context).load(ExtraUtils.baseImg+jagranProgramList.getImage()).error(R.drawable.add_image).into(ivAdd);

            cbYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "onClick:cbYes ");
                        cbYes.setChecked(true);
                        cbNo.setChecked(false);
                        llMore.setVisibility(View.VISIBLE);
                        jagranProgramList.setSelect(true);
//                        if (onItemClickListner!=null){
//                            onItemClickListner.onItemClick(jagranProgramList,true);
//                        }
                }
            });
            cbNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    llMore.setVisibility(View.GONE);
                    cbYes.setChecked(false);
                    cbNo.setChecked(true);
                    jagranProgramList.setSelect(false);
                }
            });
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("TAG", "onClick: ");
                    if (etCount.getText().toString()!=null) {
                        Log.d("TAG", "onClick: ");
                        if (onItemClickListner != null) {
                            Log.d("TAG", "onClick: ");
                            jagranProgramList.setSelect(true);
                            jagranProgramList.setCurrent_present(etCount.getText().toString());
                            onItemClickListner.onItemClick(jagranProgramList, true);
                        }
                    } else {
                        etCount.setError("Count error");
                    }
                }
            });


            if (jagranProgramList.isSelect()){
                cbYes.setChecked(true);
                cbNo.setChecked(false);
                llMore.setVisibility(View.VISIBLE);
            }else if (!jagranProgramList.isSelect()){
                cbYes.setChecked(false);
                cbNo.setChecked(true);
                llMore.setVisibility(View.GONE);
            }

        }

    }


}
