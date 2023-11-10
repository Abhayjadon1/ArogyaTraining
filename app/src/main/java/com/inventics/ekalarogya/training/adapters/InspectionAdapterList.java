package com.inventics.ekalarogya.training.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.models.InspectionModel;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.List;

public class InspectionAdapterList extends RecyclerView.Adapter<InspectionAdapterList.ViewHolder> {

    private final Context mContaxt;
    String type;
    private final List<InspectionModel> villageListModel;
    private static final String TAG = "OwnerListAdapter";
    private InspectionAdapterList.OnItemClickListener listener;



    public interface OnItemClickListener {
        void onItemClick(InspectionModel item);
    }

    public InspectionAdapterList(Context context, List<InspectionModel> villageListModel, String typeIntent) {
        this.mContaxt = context;
        this.villageListModel=villageListModel;
        this.listener = listener;
        this.type = typeIntent;
    }

    @NonNull
    @Override
    public InspectionAdapterList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_subject_inspection, null);
        return new InspectionAdapterList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InspectionAdapterList.ViewHolder holder, int position) {
        final InspectionModel baseResponse = villageListModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());
        holder.et_grade.setText("");
        if (!(type.equalsIgnoreCase("add"))){
            holder.et_grade.setEnabled(false);
        }

//        holder.tv_id.setText(String.valueOf(position+1));;
        holder.tv_name.setText(baseResponse.getTitle());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);
        holder.et_grade.setText(baseResponse.getCode());
        holder.bind(villageListModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+villageListModel.size());
        return villageListModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id,tv_name,tv_total;
        EditText et_grade;
        CardView cardRv;
        int slect=0;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            tv_id=itemView.findViewById(R.id.list_id);
            tv_name=itemView.findViewById(R.id.tvName);
            et_grade=itemView.findViewById(R.id.et_grade);
            cardRv=itemView.findViewById(R.id.cardRv);

//            editpen=itemView.findViewById(R.id.editbtn);
//            vieweye=itemView.findViewById(R.id.viewbtn);

        }


        public void bind(InspectionModel villageListModel, InspectionAdapterList.OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                if(et_grade.getText().toString()==null&&slect==0){
//                    slect=1;
//                    cardRv.setCardBackgroundColor(mContaxt.getResources().getColor(R.color.light_grey));
//
//                }else{
//                    slect=0;
//                    cardRv.setCardBackgroundColor(mContaxt.getResources().getColor(R.color.white));
//
//                }
                }
            });

           et_grade.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   if (et_grade.getText().toString()!=null&&et_grade.getText().toString().length()>0){
                       String text=et_grade.getText().toString();
                       if (text.equalsIgnoreCase("a")||text.equalsIgnoreCase("b")||text.equalsIgnoreCase("c")){
                           villageListModel.setCode(text);
                           cardRv.setCardBackgroundColor(mContaxt.getResources().getColor(R.color.light_grey));

                           Log.d(TAG, "onTextChanged: "+villageListModel.toString());
                       }else{
                           ToastUtils.shortToast(mContaxt.getResources().getString(R.string.input_grade));
                           et_grade.setText("");
                           cardRv.setCardBackgroundColor(mContaxt.getResources().getColor(R.color.white));
                       }
                   }else{
                       cardRv.setCardBackgroundColor(mContaxt.getResources().getColor(R.color.white));

                   }
               }

               @Override
               public void afterTextChanged(Editable editable) {

               }
           });
        }
    }



}
