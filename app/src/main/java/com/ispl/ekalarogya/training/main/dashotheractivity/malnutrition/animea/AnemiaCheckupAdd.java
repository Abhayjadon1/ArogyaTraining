package com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition.animea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;
import com.ispl.ekalarogya.training.models.MalnutrinAnemiaCheckupModelResponse;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class AnemiaCheckupAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
    private static final int ANEMIA_CHECKUP_DEATILS=111;
    private static final int ANEMIA_CHECKUP_ADD=117;
@BindView(R.id.tvHeadName)
    TextView tvHeadName;
@BindView(R.id.tvmemberName)
    TextView tvmemberName;
@BindView(R.id.tvAge)
    TextView tvAge;
@BindView(R.id.et_inspection_date)
    TextView et_inspection_date;
@BindView(R.id.tv_title)
    TextView tv_title;



    @BindView(R.id.cb_anemic_test_yes)
CheckBox cb_anemic_test_yes;
@BindView(R.id.cb_anemic_test_no)
CheckBox cb_anemic_test_no;
@BindView(R.id.cb_anemic_no)
CheckBox cb_anemic_no;
@BindView(R.id.cb_anemic_yes)
CheckBox cb_anemic_yes;
@BindView(R.id.cb_anemic_type_mild)
CheckBox cb_anemic_type_mild;
@BindView(R.id.cb_anemic_type_moderate)
CheckBox cb_anemic_type_moderate;
@BindView(R.id.cb_anemic_type_severe)
CheckBox cb_anemic_type_severe;

@BindView(R.id.btn_submit)
Button btn_submit;

@BindView(R.id.iv_back)
ImageView iv_back;

    @BindView(R.id.llisanemic)
    LinearLayout llisanemic;
    @BindView(R.id.llanemiatype)
    LinearLayout llanemiatype;


String village_id,head_id,member_id,test="0",isanemic="0",date,animictype="none",ispregnent="0",age,status="0",anemic_id;
    boolean isEdit=false;
    MalnutrinAnemiaCheckupModelResponse data;
    FamilyMembersModel data2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anemia_checkup_add);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            switch(getIntent().getStringExtra("type")){
                case "add":
                    data2=getIntent().getParcelableExtra("m_data");
                    member_id=data2.getId();
                    head_id=data2.getHead_id();
                    if (head_id==null){
                        head_id=member_id;
                        member_id=null;
                    }
                    status="0";
                    village_id=data2.getVillage_id();
//                    tv_title.setText("Anemia Checkup Add");
                    Log.d("TAG", "onCreate: "+data2.toString());
                    age=data2.getAge();
                    Log.d("TAG", "onCreate: getintent add village_id"+" "+village_id+" "+head_id+" "+member_id);
                    isEdit=true;
                    break;
                case "view":
                    status="1";
                    data=getIntent().getParcelableExtra("m_data");
                    head_id=data.getId();
                    member_id=data.getMember_id();
//                    tv_title.setText("Anemia Checkup Detail");
                    Log.d("TAG", "onCreate: "+data.toString());
                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+"  member_id "+member_id);
                    isEdit=false;
//                    viewMemberDetails(head_id,member_id);
                    setupMemberData(data);
                    break;
                case "edit":
                    status="1";
                    data=getIntent().getParcelableExtra("m_data");
                    head_id=data.getId();
                    member_id=data.getMember_id();
                    village_id=data.getVillage_id();
                    tv_title.setText(R.string.update_animea);
                    Log.d("TAG", "onCreate: "+data.toString());
                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+"  member_id "+member_id);
//                    viewMemberDetails(head_id,member_id);
                    setupMemberData(data);
                    isEdit=true;
                    break;

            }
        }else{
//            ToastUtils.shortToast("Request ID not found!");
            finish();
        }


        initView();
    }

    private void initView() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });

        try{

            tvAge.setText(age);
            tvmemberName.setText(data2.getName());
            tvHeadName.setText(data2.getHead_name());
            member_id= data.getMember_id();
//        tvmemberName.setText("Head ID :"+data.getName());
        }catch (Exception e){
            Log.d("TAG", "initView: "+e.getMessage());
        }

        llisanemic.setVisibility(View.GONE);
        llanemiatype.setVisibility(View.GONE);

        et_inspection_date.setOnClickListener(this);
        cb_anemic_test_yes.setOnClickListener(this);
        cb_anemic_test_no.setOnClickListener(this);
        cb_anemic_no.setOnClickListener(this);
        cb_anemic_yes.setOnClickListener(this);
        cb_anemic_type_mild.setOnClickListener(this);
        cb_anemic_type_severe.setOnClickListener(this);
        cb_anemic_type_moderate.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        if (!isEdit){
            et_inspection_date.setEnabled(false);
            cb_anemic_test_yes.setEnabled(false);
            cb_anemic_test_no.setEnabled(false);
            cb_anemic_no.setEnabled(false);
            cb_anemic_yes.setEnabled(false);
            cb_anemic_type_mild.setEnabled(false);
            cb_anemic_type_severe.setEnabled(false);
            cb_anemic_type_moderate.setEnabled(false);
            btn_submit.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }


    }

    public void setupMemberData(MalnutrinAnemiaCheckupModelResponse data){
        UserService.getAnemiaCheckupDetails(this,data.getId(),this,ANEMIA_CHECKUP_DEATILS);
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case ANEMIA_CHECKUP_ADD:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null){
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            finish();
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                        }else {
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                        }
                    }
                }
                break;
            case ANEMIA_CHECKUP_DEATILS:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null){
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            village_id=baseResponse.getAnemiaCheckupDetailsResponse().getVillage_id();
                            anemic_id=baseResponse.getAnemiaCheckupDetailsResponse().getId();
                            head_id=baseResponse.getAnemiaCheckupDetailsResponse().getHead_id();
                            member_id=baseResponse.getAnemiaCheckupDetailsResponse().getMember_id();
                           tvmemberName.setText(baseResponse.getAnemiaCheckupDetailsResponse().getMember_name());
                           tvAge.setText(baseResponse.getAnemiaCheckupDetailsResponse().getAge());
                           tvHeadName.setText(baseResponse.getAnemiaCheckupDetailsResponse().getHead_name());
                           et_inspection_date.setText(baseResponse.getAnemiaCheckupDetailsResponse().getInspection_date());
                           ispregnent=baseResponse.getAnemiaCheckupDetailsResponse().getIs_pregnant();
                            ispregnent="0";
                           isanemic=baseResponse.getAnemiaCheckupDetailsResponse().getIs_anemic();
                           test=baseResponse.getAnemiaCheckupDetailsResponse().getAnemia_test();
                            animictype=baseResponse.getAnemiaCheckupDetailsResponse().getAnemia_type();


                            Log.d("TAG", "onResponse: test : "+test);
                           if (test.equalsIgnoreCase("yes")){
                               cb_anemic_test_yes.setChecked(true);
                               test="1";
                               llisanemic.setVisibility(View.VISIBLE);
                               Log.d("TAG", "onResponse: isAnemic : "+isanemic);
                               if (isanemic.equalsIgnoreCase("yes")){
                                   cb_anemic_yes.setChecked(true);
                                   isanemic="1";
                                   llanemiatype.setVisibility(View.VISIBLE);
                                   Log.d("TAG", "onResponse: animictype : "+animictype);
                                   if (animictype.equalsIgnoreCase("mild")){
                                       animictype="mild";
                                       cb_anemic_type_mild.setChecked(true);
                                       cb_anemic_type_moderate.setChecked(false);
                                       cb_anemic_type_severe.setChecked(false);
                                   } else if (animictype.equalsIgnoreCase("moderate")){
                                       animictype="moderate";
                                       cb_anemic_type_mild.setChecked(false);
                                       cb_anemic_type_moderate.setChecked(true);
                                       cb_anemic_type_severe.setChecked(false);
                               } else if (animictype.equalsIgnoreCase("severe")){
                                       animictype="severe";

                                       cb_anemic_type_moderate.setChecked(false);
                                       cb_anemic_type_mild.setChecked(false);
                                        cb_anemic_type_severe.setChecked(true);
                               } else if  (animictype.equalsIgnoreCase("None"))
                                       animictype="None";
//                               cb_anemic_type_severe.setChecked(false);
//                               cb_anemic_type_moderate.setChecked(false);
//                               cb_anemic_type_mild.setChecked(false);
                               }else{
                                   isanemic="0";
                                   cb_anemic_no.setChecked(true);
                           }
                        }else {
                               test="0";
                               cb_anemic_test_no.setChecked(true);
                        }
//                           is.setText(baseResponse.getAnemiaCheckupDetailsResponse().getMember_name());
//                           tvmemberName.setText(baseResponse.getAnemiaCheckupDetailsResponse().getMember_name());
                    }else {
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                   }

                }}
                break;

        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        Log.d("TAG", "onFailure: "+t.getMessage());
        ToastUtils.shortToast(t.getMessage());
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_inspection_date:
                getDateDialog();
                break;
            case R.id.cb_anemic_test_yes:
//                cb_anemic_test_yes.setChecked(true);
                test="1";
                llisanemic.setVisibility(View.VISIBLE);
                cb_anemic_test_no.setChecked(false);
                break;
            case R.id.cb_anemic_test_no:
//                cb_anemic_test_no.setChecked(true);
                test="0";
                llisanemic.setVisibility(View.GONE);
                cb_anemic_test_yes.setChecked(false);
                break;
            case R.id.cb_anemic_no:
//                cb_anemic_no.setChecked(true);
                isanemic="0";
                llanemiatype.setVisibility(View.GONE);
                cb_anemic_yes.setChecked(false);
                break;
            case R.id.cb_anemic_yes:
//                cb_anemic_yes.setChecked(true);
                isanemic="1";
                llanemiatype.setVisibility(View.VISIBLE);
                cb_anemic_no.setChecked(false);
                break;
            case R.id.cb_anemic_type_mild:
//                cb_anemic_type_mild.setChecked(true);
                animictype="Mild";
                cb_anemic_type_severe.setChecked(false);
                cb_anemic_type_moderate.setChecked(false);
                break;
            case R.id.cb_anemic_type_severe:
//                cb_anemic_type_severe.setChecked(true);
                animictype="Severe";
                cb_anemic_type_mild.setChecked(false);
                cb_anemic_type_moderate.setChecked(false);
                break;
            case R.id.cb_anemic_type_moderate:
               // cb_anemic_type_moderate.setChecked(true);
                animictype="Moderate";
                cb_anemic_type_mild.setChecked(false);
                cb_anemic_type_severe.setChecked(false);
                break;
            case R.id.btn_submit:
                if(validate()){
                    submitdata();
                }
                break;
        }

    }

    private boolean validate() {
        if (et_inspection_date.getText().toString()==null) {
            et_inspection_date.setError(getResources().getString(R.string.enter_date));
            return false;
        }
        if(cb_anemic_test_yes.isChecked()&&isanemic==null){
            ToastUtils.shortToast(getResources().getString(R.string.anemia_test));
            return false;
        }
        if(cb_anemic_yes.isChecked()&&animictype==null){
            ToastUtils.shortToast(getResources().getString(R.string.anemic_type));
            return false;
        }


        return true;
    }

    private void submitdata() {
        UserService.addAnemiaCheckup(this,village_id,head_id,member_id,et_inspection_date.getText().toString(),ispregnent,test,isanemic,animictype,status,tvAge.getText().toString(),anemic_id,this,ANEMIA_CHECKUP_ADD);
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AnemiaCheckupAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                et_inspection_date.setText(date);
                Log.d("TAG", "onDateSet: "+date);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

}