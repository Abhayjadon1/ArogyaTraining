package com.inventics.ekalarogya.training.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.socialSanitisation.SocialSanitisation;
import com.inventics.ekalarogya.training.models.VillageListModel;

import java.util.List;

public class SocialSanitisationVillageListAdapter  extends RecyclerView.Adapter<SocialSanitisationVillageListAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<VillageListModel> villageListModel;
    private static final String TAG = "OwnerListAdapter";
    private SocialSanitisationVillageListAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(VillageListModel item);
    }

    public SocialSanitisationVillageListAdapter(Context context, List<VillageListModel> villageListModel) {
        this.mContaxt = context;
        this.villageListModel=villageListModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SocialSanitisationVillageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_listing, null);
        return new SocialSanitisationVillageListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SocialSanitisationVillageListAdapter.ViewHolder holder, int position) {
        final VillageListModel baseResponse = villageListModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_name.setText(baseResponse.getVillage_name());
        holder.tv_total.setText(baseResponse.getTotal_family());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(villageListModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+villageListModel.size());
        return villageListModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_name,tv_total;
        LinearLayout llBody;

        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_total=itemView.findViewById(R.id.tv_total);
            llBody=itemView.findViewById(R.id.llBody);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.viewbtn);

        }


        public void bind(VillageListModel villageListModel, SocialSanitisationVillageListAdapter.OnItemClickListener listener, int position) {
            llBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+villageListModel.getVillage_id()+"\n");;
                    Intent intent=new Intent(mContaxt, SocialSanitisation.class);
                    intent.putExtra("type","10");
                    intent.putExtra("id",villageListModel.getVillage_id());
                    mContaxt.startActivity(intent);
                }
            });



        }
    }



}
