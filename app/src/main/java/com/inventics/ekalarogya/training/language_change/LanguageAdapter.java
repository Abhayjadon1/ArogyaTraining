package com.inventics.ekalarogya.training.language_change;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;

import java.util.List;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageviewHolder> {


    private List<LanguageModel> mlist;
    private Context context;
    int lasPos=-1;
    private OnItemClickListener mListener;

    public LanguageAdapter(List<LanguageModel> mlist, Context context, OnItemClickListener mListener) {
        this.mlist = mlist;
        this.context = context;
        this.mListener=mListener;
    }

    @NonNull
    @Override
    public LanguageviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inflater_language, parent, false);
        return new LanguageviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageviewHolder holder, int position) {
        holder.ivLanguage.setImageResource(mlist.get(position).getIvLang());
//        Log.d(TAG, "onBindViewHolder: "+mlist.get(position).ge);
        holder.tvLanguage.setText(mlist.get(position).getTvLang());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lasPos=position;
                notifyDataSetChanged();
            }
        });
        if(lasPos==position) {
            if(mListener!=null)
            {
                if(lasPos!= RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mlist.get(position).getTvLang());
                }
            }
            holder.checkLanguage.setImageResource(R.drawable.selected);
            mlist.get(position).setSelected(true);
        }
        else {
            mlist.get(position).setSelected(false);
            holder.checkLanguage.setImageResource(R.drawable.nutral);
        }

        Log.d("TAG", "onBindViewHolder: "+ mlist.get(position).getLng()+"   "+ ArogyaApplication.localeManager.getLanguage());
        if(ArogyaApplication.localeManager != null && ArogyaApplication.localeManager.getLanguage().equalsIgnoreCase(mlist.get(position).getLng()) || mlist.get(position).isSelected()) {

            holder.checkLanguage.setImageResource(R.drawable.selected);
            mlist.get(position).setSelected(true);
        }
        else {
            mlist.get(position).setSelected(false);
            holder.checkLanguage.setImageResource(R.drawable.nutral);
        }

        if(mlist.get(position).isSelected()){
            holder.checkLanguage.setImageResource(R.drawable.selected);
        }else {
            mlist.get(position).setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class LanguageviewHolder extends RecyclerView.ViewHolder {

        private TextView tvLanguage;
        private ImageView ivLanguage,checkLanguage;
        private RelativeLayout rlLanguage;
        public LanguageviewHolder(@NonNull View itemView) {
            super(itemView);
            ivLanguage= itemView.findViewById(R.id.ivEng);
            checkLanguage = itemView.findViewById(R.id.ivEngSelect);
            tvLanguage= itemView.findViewById(R.id.tvEnglish);
            rlLanguage= itemView.findViewById(R.id.rlEnglish);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String id);
    }
}
