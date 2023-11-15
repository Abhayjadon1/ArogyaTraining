package com.ispl.ekalarogya.training.main.dashboardFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.TaskStatus;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.TaskListResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class TaskDetails extends AppCompatActivity implements RetroAPICallback {
    private static final int TASK_DETAIL_REQUEST_CODE = 111;
    private static final int TASK_LIST_REQUEST_CODE = 112;
    private static final int UPDATE_REQUEST_CODE = 115;
    private static final String TAG = TaskDetails.class.getSimpleName();
    String current_list,id,taskProgress;
    TaskStatus taskStatus;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.task_name)
    TextView task_name;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.task_details)
    TextView task_details;
    @BindView(R.id.start_date)
    TextView start_date;
    @BindView(R.id.end_date)
    TextView end_date;
    @BindView(R.id.spin_progress)
    Spinner spin_progress;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindArray(R.array.progress)
    String [] progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        ButterKnife.bind(this);
        if (getIntent()!=null){
            Intent i=getIntent();
            id=i.getStringExtra("taskID");
            progress_bar.setVisibility(View.VISIBLE);
            UserService.getTaskDetails(this,id,this,TASK_DETAIL_REQUEST_CODE);
        }else{
            ToastUtils.shortToast(getResources().getString(R.string.try_again));
        }

        iv_back.setOnClickListener(view -> {startActivity(new Intent(TaskDetails.this,TaskActivity.class));finish();;});

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_progress_list, progress);
        spin_progress.setAdapter(adapter);
        spin_progress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), treat_option[i], Toast.LENGTH_LONG).show();
                taskProgress=String.valueOf(i);
                Log.d(TAG, "onItemSelected: "+i+"    progress "+progress[i]);
//                if(progress[i].equalsIgnoreCase("Select")){
//                    taskProgress="1";
//                }else if(progress[i].equalsIgnoreCase("Not Cured")){
//                    taskProgress="0";
//                }else if(progress[i].equalsIgnoreCase("Not Cured")){
//                    taskProgress="0";
//                }else if(progress[i].equalsIgnoreCase("Not Cured")){
//                    taskProgress="0";
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btn_submit.setOnClickListener(view -> { progress_bar.setVisibility(View.VISIBLE);UserService.updateTask(this,id,taskProgress,this,UPDATE_REQUEST_CODE);});
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case TASK_DETAIL_REQUEST_CODE:
                if (response!=null){
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            progress_bar.setVisibility(View.GONE);
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            TaskListResponse taskListResponse=baseResponseModule.getTask_detail();
                             current_list=baseResponseModule.getList_name();
//  current_list= ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            tv_title.setText(getResources().getString(R.string.task_details));
                            tv_id.setText(taskListResponse.getId());
                            task_name.setText(taskListResponse.getTask_title());
                            start_date.setText(getResources().getString(R.string.start_date)+"\n"+taskListResponse.getStart_date());
                            end_date.setText(getResources().getString(R.string.end_date)+"\n"+taskListResponse.getEnd_date());
                            task_details.setText(taskListResponse.getTask_description());
                            taskStatus=taskListResponse.getTask_status();
                            taskProgress=taskStatus.getId();

                            spin_progress.setSelection(Integer.parseInt(taskProgress));
                        }else{
                            ToastUtils.shortToast(baseResponseModule.getStatus());
                            Log.d(TAG, "onResponse: failed "+baseResponseModule.getStatusMessage());
                        }
                    }

                }

                break;

            case UPDATE_REQUEST_CODE:
                if (response!=null){
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            ToastUtils.shortToast(response.message());
                            progress_bar.setVisibility(View.GONE);
                            finish();
                        }else{
                            ToastUtils.shortToast(baseResponseModule.getStatus());
                            Log.d(TAG, "onResponse: failed "+baseResponseModule.getStatusMessage());
                        }
                    }

                }
                break;


        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        Log.d(TAG, "onFailure: "+t.getMessage());
        ToastUtils.shortToast(t.getMessage());
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }
}
