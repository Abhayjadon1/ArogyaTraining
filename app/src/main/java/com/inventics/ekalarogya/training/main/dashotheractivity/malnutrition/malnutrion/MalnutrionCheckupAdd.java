package com.inventics.ekalarogya.training.main.dashotheractivity.malnutrition.malnutrion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.MalnutrinCheckupModelResponse;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.Calendar;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class MalnutrionCheckupAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {

    private static final int MALNURISHED_CHECKUP_DEATILS=111;
    private static final int MALNURISHED_CHECKUP_ADD=117;
    private static final String TAG = MalnutrionCheckupAdd.class.getSimpleName();

    //    @BindView(R.id.tvHeadName)
//    TextView tvHeadName;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.relationTv)
    TextView relationTv;
    @BindView(R.id.gendertv)
    TextView gendertv;
@BindView(R.id.ivDate)
ImageView ivDate;
@BindView(R.id.iv_back)
ImageView iv_back;

    @BindView(R.id.et_inspection_date)
    TextView et_inspection_date;
    @BindView(R.id.tv_title)
    TextView tv_title;


    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.mid_arm_spinner)
    Spinner mid_arm_spinner;

    @BindView(R.id.llanemiatype)
    LinearLayout llanemiatype;


    @BindView(R.id.cb_malnutrition_test_no)
    CheckBox cb_malnutrition_test_no;
    @BindView(R.id.cb_malnutrition_test_yes)
    CheckBox cb_malnutrition_test_yes;

@BindArray(R.array.mid_arm)
String[] mid_arm;

    String village_id,head_id,member_id,test,date,age,malnurished_id,current_list,isMalnurished,manutrition_test,status="0",gender,inspection_date,checkup,checkup_id,mid_arm_measurement;
    boolean isEdit=false;
    MalnutrinCheckupModelResponse data;

    VillageListModel v_data;
    VillageFamilyModel h_data;
    FamilyMembersModel m_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malnutrion_checkup_add);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            switch(getIntent().getStringExtra("type")){
                case "add":
//                    data=getIntent().getParcelableExtra("m_data");
                    m_data=getIntent().getParcelableExtra("m_data");
                    m_data.toString();
                    tv_title.setText(R.string.malnutrition_checkup_add);
                    Log.d("TAG", "onCreate: getintent add village_id"+" "+village_id);
                    isEdit=true;
                    age=m_data.getAge();
                    tvAge.setText(getResources().getString(R.string.age) +" : "+age);
                    status="0";
                    gender=m_data.getGender();
                    gendertv.setText(getResources().getString(R.string.gender) +" : "+gender);
                    tvName.setText(getResources().getString(R.string.name) +" : "+m_data.getName());
                    if(m_data.getVillage_id()!=null){
                        village_id=m_data.getVillage_id();
                    }
                    if(m_data.getHead_id()!=null){
                        head_id=m_data.getHead_id();
                    }
                    if(m_data.getId()!=null){
                        member_id=m_data.getId();
                    }else{
                        member_id=m_data.getMember_id();
                    }
//                    if(m_data.getHead_id()!=null){
//                        head_id=m_data.getId();
//                    }
//                    if(m_data.getMember_id()!=null){
//                        member_id=m_data.getMember_id();
//                    }
                    Log.d(TAG, "onCreate: get intent "+member_id +"  "+head_id +"  "+village_id+"\n"+m_data.toString());
                    relationTv.setText(getResources().getString(R.string.relation)+" : "+m_data.getRelation_with_head());
//                    setupMemberData(malnurished_id);
                    break;
                case "view":
                    data=getIntent().getParcelableExtra("m_data");
                    malnurished_id=data.getId();
                    tv_title.setText(R.string.malnutrition_checkup_result);
                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+"  member_id "+member_id);
                    isEdit=false;
//                    viewMemberDetails(head_id,member_id);
                    setupMemberData(malnurished_id);
                    break;
                case "edit":
                    status="1";
                    data=getIntent().getParcelableExtra("m_data");
                    malnurished_id=data.getId();
                    tv_title.setText(R.string.malnutrition_checkup);

                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+"  member_id "+member_id);
//                    viewMemberDetails(head_id,member_id);
                    setupMemberData(malnurished_id);
                    isEdit=true;
                    break;

            }
        }else{
//            ToastUtils.shortToast("Request ID not found!");
            finish();
        }

        llanemiatype.setVisibility(View.GONE);
        initView();
    }

    private void setupMemberData(String malnutrion_id) {
        UserService.getMalnutritionCheckupDetails(this,malnutrion_id,this,MALNURISHED_CHECKUP_DEATILS);
    }


    private void initView() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        ivDate.setOnClickListener(this);
        et_inspection_date.setOnClickListener(this);
        cb_malnutrition_test_no.setOnClickListener(this);
        cb_malnutrition_test_yes.setOnClickListener(this);
        btn_submit.setOnClickListener(this);


        ArrayAdapter adapter=new ArrayAdapter(MalnutrionCheckupAdd.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.mid_arm));
        mid_arm_spinner.setAdapter(adapter);
        mid_arm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mid_arm_measurement=  adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        ivDate.setOnClickListener(this);

        if (!isEdit){
            ivDate.setEnabled(false);
            et_inspection_date.setEnabled(false);
            cb_malnutrition_test_no.setEnabled(false);
            cb_malnutrition_test_yes.setEnabled(false);
            mid_arm_spinner.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }

    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case MALNURISHED_CHECKUP_ADD:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            finish();
                            ToastUtils.shortToast(baseResponseModule.getStatusMessage());
                        }else{
                            ToastUtils.shortToast(baseResponseModule.getStatusMessage());

                        }
                    }
                }
                break;
            case MALNURISHED_CHECKUP_DEATILS:
                if (response.isSuccessful()) {
                BaseResponse baseResponseModule = (BaseResponse) response.body();
                if (baseResponseModule != null) {
                    if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                        Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
                        Log.d(TAG, "onResponse: " + baseResponseModule.getMalnutrinDetailResponses());
                        current_list = baseResponseModule.getList_name();
                        checkup_id = malnurished_id;
                        age=baseResponseModule.getMalnutrinDetailResponses().getAge();
                        tvAge.setText(getResources().getString(R.string.age) +" : "+ baseResponseModule.getMalnutrinDetailResponses().getAge());
                        tvName.setText(getResources().getString(R.string.name )+" : "+baseResponseModule.getMalnutrinDetailResponses().getMember_name());
                        village_id=baseResponseModule.getMalnutrinDetailResponses().getVillage_id();
                        head_id=baseResponseModule.getMalnutrinDetailResponses().getHead_id() ;
                        member_id=baseResponseModule.getMalnutrinDetailResponses().getMember_id();
                        relationTv.setText(getResources().getString(R.string.relation) +" : "+baseResponseModule.getMalnutrinDetailResponses().getRelation_with_head());
                        gendertv.setText(getResources().getString(R.string.gender) +" : "+baseResponseModule.getMalnutrinDetailResponses().getGender());
                        et_inspection_date.setText(baseResponseModule.getMalnutrinDetailResponses().getInspection_date());
                        mid_arm_measurement=baseResponseModule.getMalnutrinDetailResponses().getMid_arm_measurement();

                        Log.d(TAG, "setupdata: ghjhgvgh " +" "+String.valueOf(mid_arm.length));
                        if (mid_arm_measurement.equalsIgnoreCase("12-13")){
                            mid_arm_spinner.setSelection(mid_arm.length-3);
                        }else if (mid_arm_measurement.equalsIgnoreCase("13-14")){
                            mid_arm_spinner.setSelection(mid_arm.length-2);
                        }else if (mid_arm_measurement.equalsIgnoreCase("14-15")){
                            mid_arm_spinner.setSelection(mid_arm.length-1);
                        }else {
                            Log.d(TAG, "setupdata: ghkhghjghgjhcgj");
                        }
//                      .setText(baseResponseModule.getMalnutrinDetailResponses().getAge());
                        if (baseResponseModule.getMalnutrinDetailResponses().getMalnourished().equalsIgnoreCase("Yes")
                                ||baseResponseModule.getMalnutrinDetailResponses().getMalnourished().equalsIgnoreCase("1")){
                            cb_malnutrition_test_yes.setChecked(true);
                            llanemiatype.setVisibility(View.VISIBLE);
                            manutrition_test="1";
                        }else {
                            cb_malnutrition_test_no.setChecked(true);
                            manutrition_test="0";
                            llanemiatype.setVisibility(View.GONE);

                        }

                        break;
                    }

                }
                }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_inspection_date:
                getDateDialog();
                break;
            case R.id.btn_submit:
                if (validat()){
                    submitData();
                }
                break;
            case R.id.cb_malnutrition_test_yes:
                manutrition_test="1";
                cb_malnutrition_test_no.setChecked(false);
                llanemiatype.setVisibility(View.VISIBLE);
                break;
            case R.id.cb_malnutrition_test_no:
                manutrition_test="0";
                cb_malnutrition_test_yes.setChecked(false);
                llanemiatype.setVisibility(View.GONE);
                break;

        }

    }

    private boolean validat() {
        if (et_inspection_date.getText().length()<=3){
            ToastUtils.shortToast(R.string.enter_date);
            return false;
        }
        if (manutrition_test==null){
            ToastUtils.shortToast(R.string.choose_malnutrition_yes_no);
            return false;
        }
        if (mid_arm_measurement==null){
            ToastUtils.shortToast(R.string.plz_select_mid_arm);
            return false;
        }
        date=et_inspection_date.getText().toString();


        return true;
    }

    private void submitData() {
        UserService.addMalnurishData(this,status,age,gender,village_id,head_id,member_id,et_inspection_date.getText().toString(),manutrition_test,checkup_id,mid_arm_measurement,this,MALNURISHED_CHECKUP_ADD);
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(MalnutrionCheckupAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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