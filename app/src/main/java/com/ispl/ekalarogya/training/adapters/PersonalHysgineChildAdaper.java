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
import com.ispl.ekalarogya.training.main.dashotheractivity.preventives.hygiene.PersonalHygineCheckup;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;

import java.util.List;

public class PersonalHysgineChildAdaper  extends RecyclerView.Adapter<PersonalHysgineChildAdaper.ViewHolder> {

    private final Context mContaxt;
    private final List<FamilyMembersModel> personlHygnChildResponses;
    private static final String TAG = "OwnerListAdapter";
    private PersonalHysgineChildAdaper.OnItemClickListener listener;
    VillageFamilyModel headid;
    String v_id;



    public interface OnItemClickListener {
        void onItemClick(FamilyMembersModel item);
    }

    public PersonalHysgineChildAdaper(Context context, VillageFamilyModel head_id,String vid, List<FamilyMembersModel>personlHygnChildResponses) {
        this.mContaxt = context;
        this.personlHygnChildResponses=personlHygnChildResponses;
        this.listener = listener;
        this.headid=head_id;
        this.v_id=vid;
    }

    @NonNull
    @Override
    public PersonalHysgineChildAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_member, null);

        return new PersonalHysgineChildAdaper.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonalHysgineChildAdaper.ViewHolder holder, int position) {
        final FamilyMembersModel baseResponse = personlHygnChildResponses.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tvName.setText(baseResponse.getName() +"   ");

//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(personlHygnChildResponses.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+personlHygnChildResponses.size());
        return personlHygnChildResponses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView viewdata;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            viewdata=itemView.findViewById(R.id.viewdata);


        }


        public void bind(FamilyMembersModel personlHygnChildResponses, PersonalHysgineChildAdaper.OnItemClickListener listener, int position) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    PersonalHygineCheckup.openActivity(personlHygnChildResponses,"add");
//                    mContaxt.startActivity(new Intent(mContaxt,PersonalHygineCheckup.class));
                    Intent intent = new Intent(mContaxt, PersonalHygineCheckup.class);
                    intent.putExtra("m_data",personlHygnChildResponses);
                    intent.putExtra("h_data",headid);
                    intent.putExtra("v_id",v_id);
                    intent.putExtra("type","add");
                    Log.d(TAG, "onClick: "+headid+" "+personlHygnChildResponses.getId()+v_id+" "+headid);
                    mContaxt.startActivity(intent);
                }
            });
//            viewdata.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContaxt, PersonalHygineCheckup.class);
//                    intent.putExtra("m_data",personlHygnChildResponses);
//                    intent.putExtra("h_id",headid);
//                    intent.putExtra("type","view");
//                    Log.d(TAG, "onClick: "+headid+" "+personlHygnChildResponses.getId());
//                    mContaxt.startActivity(intent);
//
//                }
//            });




        }
    }



}
