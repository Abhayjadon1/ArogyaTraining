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
import com.ispl.ekalarogya.training.main.dashboardFragments.TaskDetails;
import com.ispl.ekalarogya.training.models.TaskListModel;
import com.ispl.ekalarogya.training.rest.response.TaskListResponse;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private static final int TASK_DETAIL_REQUEST_CODE = 111;
    String progress_status;
//    private static final int TASK_LIST_REQUEST_CODE = 112;
    private static final int UPDATE_REQUEST_CODE = 115;
    private final Context mContaxt;
    TaskListResponse taskModel;
    private final List<TaskListModel> taskListModel;
    private static final String TAG = "OwnerListAdapter";
    private TaskListAdapter.OnItemClickListener listener;




    public interface OnItemClickListener {
        void onItemClick(TaskListModel item);
    }

    public TaskListAdapter(Context context, List<TaskListModel> taskListModel) {
        this.mContaxt = context;
        this.taskListModel=taskListModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.row_task_list, null);

        return new TaskListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskListAdapter.ViewHolder holder, int position)  {
        final TaskListModel baseResponse = taskListModel.get(position);
        Log.d(TAG, "onBindViewHolder: "+baseResponse.toString());

        holder.tv_id.setText(mContaxt.getResources().getString(R.string.task_id)+" "+baseResponse.getId());
        holder.task_name.setText(baseResponse.getTask_title());
        holder.task_details.setText(baseResponse.getTask_description());
        holder.start_date.setText(mContaxt.getResources().getString(R.string.start_date)+"\n"+baseResponse.getStart_date());
        holder.end_date.setText(mContaxt.getResources().getString(R.string.end_date)+"\n"+baseResponse.getEnd_date());
        holder.tv_progress.setText(baseResponse.getStatus());
//        Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);

        holder.bind(taskListModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: "+taskListModel.size());
        return taskListModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,task_name,task_details,start_date,end_date,tv_progress;
        ImageView editpen,vieweye;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id=itemView.findViewById(R.id.tv_id);
            task_name=itemView.findViewById(R.id.task_name);
            task_details=itemView.findViewById(R.id.task_details);
            start_date=itemView.findViewById(R.id.start_date);
            end_date=itemView.findViewById(R.id.end_date);
            tv_progress=itemView.findViewById(R.id.tv_progress);

        }


        public void bind(TaskListModel taskListModel, TaskListAdapter.OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: view"+taskListModel.toString()+"\n");
                    Intent intent=new Intent(mContaxt,TaskDetails.class);
                    intent.putExtra("taskID",taskListModel.getId());
                    mContaxt.startActivity(intent);
//
                }
            });

        }
    }
}
