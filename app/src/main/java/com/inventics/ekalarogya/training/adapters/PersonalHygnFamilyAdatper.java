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
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.hygiene.PersonalHygineFamily;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;

import java.util.List;

public class PersonalHygnFamilyAdatper extends RecyclerView.Adapter<PersonalHygnFamilyAdatper.ViewHolder> {

    private final Context mContaxt;
    private final List<VillageFamilyModel> villageFamilyModelList;
    private static final String TAG = "OwnerListAdapter";
    private PersonalHygnFamilyAdatper.OnItemClickListener listener;
    String headid;



    public interface OnItemClickListener {
        void onItemClick(VillageFamilyModel item);
    }

    public PersonalHygnFamilyAdatper(Context context, List<VillageFamilyModel> villageFamilyModelList) {
        this.mContaxt = context;
        this.villageFamilyModelList=villageFamilyModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonalHygnFamilyAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new PersonalHygnFamilyAdatper.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonalHygnFamilyAdatper.ViewHolder holder, int position) {
        final VillageFamilyModel baseResponse = villageFamilyModelList.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(baseResponse.getFamily_head());

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(villageFamilyModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+villageFamilyModelList.size());
        return villageFamilyModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView viewdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            viewdata=itemView.findViewById(R.id.viewdata);


        }


        public void bind(VillageFamilyModel villageFamilyModelList, PersonalHygnFamilyAdatper.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+headid+"\n");
                    Intent intent = new Intent(mContaxt, PersonalHygineFamily.class);
                    intent.putExtra("h_data",villageFamilyModelList);
                    intent.putExtra("type", "head");
                    Log.d(TAG, "onClick: "+villageFamilyModelList.getFamily_head_id());
                    mContaxt.startActivity(intent);

                }
            });




        }
    }



}
