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
import com.inventics.ekalarogya.training.main.dashotheractivity.inspectionday.InspectionDayAdd;
import com.inventics.ekalarogya.training.models.InspectionStayModel;

import java.util.List;

public class InspectionAdapter  extends RecyclerView.Adapter<InspectionAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<InspectionStayModel> inspectionStayModel;
    private static final String TAG = "OwnerListAdapter";
    private InspectionAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(InspectionStayModel item);
    }

    public InspectionAdapter(Context context, List<InspectionStayModel> inspectionStayModel) {
        this.mContaxt = context;
        this.inspectionStayModel=inspectionStayModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InspectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_inspection, null);

        return new InspectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InspectionAdapter.ViewHolder holder, int position) {
        final InspectionStayModel baseResponse = inspectionStayModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());



        holder.tv_family_id.setText(baseResponse.getInspection_date());
        holder.tv_family_grade.setText(baseResponse.getFamily_grade());
        holder.tv_vidhyalay_grade.setText(baseResponse.getVidyalay_grade());
        holder.tv_jagaran_grade.setText(baseResponse.getJagran_grade());
        holder.tv_plantation_grades.setText(baseResponse.getPlantation_grade());
        holder.tv_poster_grade.setText(baseResponse.getPoster_grade());
        holder.tv_ohter_grades.setText(baseResponse.getOther_grade());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(inspectionStayModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+inspectionStayModel.size());
        return inspectionStayModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_family_id,tv_vidhyalay_grade,tv_jagaran_grade,tv_family_grade,tv_poster_grade,tv_plantation_grades,tv_ohter_grades;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_family_id=itemView.findViewById(R.id.tv_family_id);
            tv_family_grade=itemView.findViewById(R.id.tv_family_grade);
            tv_vidhyalay_grade=itemView.findViewById(R.id.tv_vidhyalay_grade);
            tv_jagaran_grade=itemView.findViewById(R.id.tv_jagaran_grade);
            tv_poster_grade=itemView.findViewById(R.id.tv_poster_grade);
            tv_plantation_grades=itemView.findViewById(R.id.tv_plantation_grades);
            tv_ohter_grades=itemView.findViewById(R.id.tv_ohter_grades);

            editpen=itemView.findViewById(R.id.editbtn);
            editpen.setVisibility(View.GONE);
            vieweye=itemView.findViewById(R.id.viewbtn);



        }


        public void bind(InspectionStayModel inspectionStayModel, InspectionAdapter.OnItemClickListener listener, int position) {


            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+inspectionStayModel.toString()+"\n");
                    Intent intent = new Intent(mContaxt, InspectionDayAdd.class);
                    intent.putExtra("m_data", inspectionStayModel);
                    intent.putExtra("type", "view");
                    mContaxt.startActivity(intent);

                }
            });

        }
    }



}
