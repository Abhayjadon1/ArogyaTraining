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
import com.ispl.ekalarogya.training.main.dashotheractivity.posterdisplay.PosterDisplayAdd;
import com.ispl.ekalarogya.training.models.PosterDisplayModel;

import java.util.List;

public class PosterAdapter  extends RecyclerView.Adapter<PosterAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<PosterDisplayModel> posterDisplayModelList;
    private static final String TAG = "OwnerListAdapter";
    private PosterAdapter.OnItemClickListener listener;

    String[] arogya_topicList;

    public interface OnItemClickListener {
        void onItemClick(PosterDisplayModel item);
    }

    public PosterAdapter(Context context, List<PosterDisplayModel> posterDisplayModelList) {
        this.mContaxt = context;
        this.posterDisplayModelList=posterDisplayModelList;
        this.listener = listener;


        arogya_topicList = mContaxt.getResources().getStringArray(R.array.weak_list);
    }

    @NonNull
    @Override
    public PosterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_poster_display, null);

        return new PosterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PosterAdapter.ViewHolder holder, int position) {
        final PosterDisplayModel baseResponse = posterDisplayModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());


        holder.tv_posters.setText(baseResponse.getPoster_count());
        holder.tv_village.setText(baseResponse.getVillage_name());
        holder.tv_total_member.setText(baseResponse.getTotal_present());
        String week=baseResponse.getWeek();
        if (week!=null){
            switch (week){
                case "First":
                    holder.tv_week.setText(arogya_topicList[0]);
                    break;
                case "Second":
                    holder.tv_week.setText(arogya_topicList[1]);
                    break;
                case "Third":
                    holder.tv_week.setText(arogya_topicList[2]);
                    break;
                case "Fourth":
                    holder.tv_week.setText(arogya_topicList[3]);
                    break;

            }
        }
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(posterDisplayModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+posterDisplayModelList.size());
        return posterDisplayModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_week,tv_village,tv_posters,tv_total_member;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_week=itemView.findViewById(R.id.tv_week);
            tv_village=itemView.findViewById(R.id.tv_village);
            tv_posters=itemView.findViewById(R.id.tv_posters);
            tv_total_member=itemView.findViewById(R.id.tv_total_member);
            editpen=itemView.findViewById(R.id.editbtn);
            editpen.setVisibility(View.GONE);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(PosterDisplayModel posterDisplayModelList, PosterAdapter.OnItemClickListener listener, int position) {


            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+posterDisplayModelList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, PosterDisplayAdd.class);
                    intent.putExtra("m_data", posterDisplayModelList);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+posterDisplayModelList.getId()+"\n");
                    Intent intent = new Intent(mContaxt, PosterDisplayAdd.class);
                    intent.putExtra("m_data", posterDisplayModelList);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}
