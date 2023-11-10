package com.inventics.ekalarogya.training.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.models.JagranProgramList;
import com.inventics.ekalarogya.training.utils.ExtraUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JagranProgramListAdapterAdd extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final static int PIC_CLICK_REQUST=111;
    private List<JagranProgramList> planList;
    private boolean on_attach = true;
    private Context context;
    int position = 0;
    JagranProgramListAdapterAdd jagranProgramListAdapter;

    public interface  OnItemClickListner{
        void onItemClick(JagranProgramList JagranProgramList, String type,String data);
    }
    public OnItemClickListner onItemClickListner;
    public JagranProgramListAdapterAdd(Context context, List<JagranProgramList> planList, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.planList = planList;
        this.onItemClickListner = onItemClickListner;
        Log.d("TAG", "JagranProgramListAdapterAdd: "+planList.toString());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jagaran_program_expandble, parent, false);
        return new JagranProgramListAdapterAdd.PlanViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((JagranProgramListAdapterAdd.PlanViewHolder) holder).bindData(planList.get(position));


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

        @BindView(R.id.et_current_presence)
        EditText et_current_presence;
        @BindView(R.id.et_prev_presence)
        EditText et_prev_presence;

        @BindView(R.id.images)
        ImageView images;
        @BindView(R.id.next)
        ImageView next;
        @BindView(R.id.previous)
        ImageView previous;
        @BindView(R.id.addImage)
        ImageView addImage;
        @BindView(R.id.list_Image_delete)
        ImageView list_Image_delete;

        @BindView(R.id.bottom_ll)
        LinearLayout bottom_ll;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bindData(JagranProgramList jagranProgramList) {
            Log.d("TAG", "bindData: xhvgsd   "+jagranProgramList.toString());
            addImage.setVisibility(View.GONE);
//            bottom_ll.setVisibility(View.GONE);
            et_current_presence.setText(jagranProgramList.getPresent());
            if (jagranProgramList.getTitle()!=null){
                tvName.setText(jagranProgramList.getTitle());
            }else {
                tvName.setText(jagranProgramList.getProgram_title());
            }
//            et_current_presence.setText(jagranProgramList.getCurrent_present());

//            Log.d("TAG", "bindData: "+jagranProgramList.getImgs());
//            if (jagranProgramList.getImgs()!=null||jagranProgramList.getImage()!=null) {
//                images.setImageBitmap(convertBase64ToBitmap(String.valueOf(jagranProgramList.getImgs())));
//
//                if (jagranProgramList.getImgs()!=null){
//                    list_Image_delete.setVisibility(View.VISIBLE);
//                }else {
//                    list_Image_delete.setVisibility(View.GONE);
//                }
//            }else{
                Picasso.with(context).load(ExtraUtils.baseImg+jagranProgramList.getImage()).error(R.drawable.add_image).into(images);
                Log.d("TAG", "bindData:IMAGES "+ExtraUtils.baseImg+jagranProgramList.getImage());
//            }
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottom_ll.getVisibility()!=View.VISIBLE){
                        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.open);
                        bottom_ll.setVisibility(View.VISIBLE);
                        bottom_ll.startAnimation(slideDown);
                    }else {
                        bottom_ll.setVisibility(View.GONE);
                    }
                }
            });
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_current_presence.getText().toString().length()>0){
                        if(onItemClickListner!=null)
                        {
                            onItemClickListner.onItemClick(jagranProgramList,"text", et_current_presence.getText().toString());
                            onItemClickListner.onItemClick(jagranProgramList,"PIC",null);

                        }
                    }else{
                        et_current_presence.setError("Please enter data first");
                        et_current_presence.setFocusable(true);
                    }
                }
            });

            list_Image_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jagranProgramList.setImgs(null);
                    Log.d("TAG", "onClick: dele"+jagranProgramList.toString());
                    images.setImageResource(R.drawable.add_image);
                    list_Image_delete.setVisibility(View.GONE);

                }
            });

//            previous.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (position > 0) {
//                        position--;
//                        images.setImageBitmap(convertBase64ToBitmap(jagranProgramList.getImgs().get(position)));
//                    }
//                    if (position==0){
//                        previous.setEnabled(false);}else{previous.setEnabled(true);
//                    }
//                }
//            });
//            next.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (jagranProgramList.getImgs().size()>0){
//                        if (position>0){
//                            previous.setEnabled(true);}
//                        if (position < jagranProgramList.getImgs().size() - 1) {
//                            position++;
//                            images.setImageBitmap(convertBase64ToBitmap(jagranProgramList.getImgs().get(position)));
//                        } else {
//                            Toast.makeText(context, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            });
        }

    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
