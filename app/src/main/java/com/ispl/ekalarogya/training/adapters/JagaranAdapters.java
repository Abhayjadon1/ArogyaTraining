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
import com.ispl.ekalarogya.training.main.dashotheractivity.jagaranprogram.JagaranProgramAdd;
import com.ispl.ekalarogya.training.models.JagranProgramList;
import com.ispl.ekalarogya.training.models.JagranProgramModel;

import java.util.List;

public class JagaranAdapters extends RecyclerView.Adapter<JagaranAdapters.ViewHolder> {
    JagranProgramList jpmlist;
    private final Context mContaxt;
    private final List<JagranProgramModel> jagranProgramModelList;
//    private List<JagranProgramList> jagranList;
    private static final String TAG = "OwnerListAdapter";
    private JagaranAdapters.OnItemClickListener listener;
    String village_name;


    public interface OnItemClickListener {
        void onItemClick(JagranProgramModel item);
    }

    public JagaranAdapters(Context context, List<JagranProgramModel> jagranProgramModelList, String v_name) {
        this.mContaxt = context;
        this.jagranProgramModelList=jagranProgramModelList;
        this.listener = listener;
        this.village_name = v_name;
//        jagranList= jagranProgramList;
        Log.d(TAG, "onBindViewHolder:  all size "+ jagranProgramModelList.toString()+" ");
//        Log.d(TAG, "onBindViewHolder:  all size 22 "+ jagranList.toString()+" ");


    }

    @NonNull
    @Override
    public JagaranAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_jagaran_program, null);

        return new JagaranAdapters.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final JagaranAdapters.ViewHolder holder, int position) {
        final JagranProgramModel baseResponse = jagranProgramModelList.get(position);
//        final JagranProgramList jagranList = jagranProgramModelList.get(position).getProgram_details();
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
        String traingList="";
        int total=0,total_present=0;
//        Log.d(TAG, "onBindViewHolder:  all size "+jagranProgramModelList.get(position).getProgram_details()+"\n "+jagranList.size());

//        if(jagranList!=null&&jagranList.size()>0){
//            for (int i=0;i<jagranList.size();i++){
//
//                if (total==0){
//                    traingList=jagranList.get(i).getTitle();
//                    total_present=Integer.parseInt(jagranList.get(i).getPresent());
//
//                }else if (total>=1){
//                    if (traingList!=null&& !(traingList.equalsIgnoreCase(""))){
//                        traingList=traingList+","+jagranList.get(i).getTitle();
//                    }else{
//                        traingList=jagranList.get(i).getTitle();
//                    }
//                    total_present=total_present+Integer.parseInt(jagranList.get(i).getPresent());
//                }
//                total++;
//                Log.d(TAG, "onBindViewHolder: trainningData : "+traingList.toString());
//            }
//
//
//        }else{
//            Log.d(TAG, "onBindViewHolder: trainning else: "+baseResponse.toString());
//
//        }

        holder.tv_date.setText(baseResponse.getProgram_date());
        if (baseResponse.getVillage_name()==null){
            holder.tv_village.setText(village_name);
        }else {
            holder.tv_village.setText(baseResponse.getVillage_name());
        }
//        if (traingList==null){
        List<String > programs=null;
        programs=baseResponse.getPlant_names();
//        if (baseResponse.getProgram_details().equalsIgnoreCase("Health and cleanliness Jagran Rally")){
            holder.tv_program.setText(mContaxt.getResources().getString(R.string.health_clean_rally));
//        }

//            holder.tv_program.setText(String.join(",",programs));
//        }else {
//            holder.tv_program.setText(String.valueOf(traingList));
//        }
//        if (total_present<=0){
            holder.tv_total.setText(baseResponse.getProgram_count()+"");

//        }else {
//            holder.tv_total.setText(String.valueOf(total_present));
//
//        }
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(jagranProgramModelList.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+jagranProgramModelList.size());
        return jagranProgramModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date,tv_village,tv_program,tv_total;
        ImageView editpen,vieweye;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_village=itemView.findViewById(R.id.tv_village);
            tv_program=itemView.findViewById(R.id.tv_program);
            tv_total=itemView.findViewById(R.id.tv_total_presense);

            editpen=itemView.findViewById(R.id.editbtn);
            vieweye=itemView.findViewById(R.id.vieweye);
            editpen.setVisibility(View.VISIBLE);
            vieweye.setVisibility(View.GONE);
        }


        public void bind(JagranProgramModel jagranProgramModelList, OnItemClickListener listener, int position) {
            editpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: edit"+jagranProgramModelList.getJagran_id()+"\n");;
//                    if (jagranProgramModelList.getProgram_details()!=null&&jagranProgramModelList.getProgram_details().size()>0){
//                        jagranList=jagranProgramModelList.getProgram_details();
//                    }
                    Log.d(TAG, "onClick: edit"+jagranProgramModelList.getJagran_id()+"\n");;
                    Intent intent = new Intent(mContaxt, JagaranProgramAdd.class);
                    intent.putExtra("m_data", jagranProgramModelList);
//                    intent.putParcelableArrayListExtra("m_data2", (ArrayList<? extends Parcelable>) jagranList);
//                    intent.putExtra("m_data2", baseResponse2);
                    intent.putExtra("type", "edit");
                    mContaxt.startActivity(intent);
                }
            });
            vieweye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                   if (jagranProgramModelList.getProgram_details()!=null&&jagranProgramModelList.getProgram_details().size()>0){
//                       jagranList=jagranProgramModelList.getProgram_details();
//                   }
                    Intent intent = new Intent(mContaxt, JagaranProgramAdd.class);
                    intent.putExtra("m_data", jagranProgramModelList);
//                    intent.putParcelableArrayListExtra("m_data2", (ArrayList<? extends Parcelable>) jagranList);
//                    intent.putExtra("m_data2", baseResponse2);
                    intent.putExtra("type", "view");
//                    Log.d(TAG, "onClick: "+jagranList.toString());
//                    Log.d(TAG, "onClick:   kjk "+jagranList.size());
                    mContaxt.startActivity(intent);
                }
            });
        }
    }
}


