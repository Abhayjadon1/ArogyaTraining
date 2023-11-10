package com.inventics.ekalarogya.training.main.dashotheractivity.firstaid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.FirstAidModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class FirstAidAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
     @BindView(R.id.tv_date)
    TextView tv_date;
     @BindView(R.id.tv_family_head)
    TextView tv_family_head;

    @BindView(R.id.et_remarks)
    EditText et_remarks;
    @BindView(R.id.guestName)
    EditText guestName;

    @BindView(R.id.btn_submit)
    Button btn_submit;

     @BindView(R.id.cb_cuts)
     CheckBox cb_cuts;
    @BindView(R.id.cb_heart_attacks)
    CheckBox cb_heart_attacks;
    @BindView(R.id.cb_snake_bites)
    CheckBox cb_snake_bites;
    @BindView(R.id.cb_uncounsious)
    CheckBox cb_uncounsious;
    @BindView(R.id.cb_other)
    CheckBox cb_other;

    @BindView(R.id.cb_training_of_various_record)
    CheckBox cb_training_of_various_record;
    @BindView(R.id.cb_incident_record_keeping)
    CheckBox cb_incident_record_keeping;

    String TAG=FirstAidAdd.class.getSimpleName();
    private final static int EMERGENCY_ADD=111;
    private final static int EMERGENCY_VIEW=151;

    boolean isEdit = false;
String type=null,date=null,remark=null,headname="",vid=null,hid="",membername="",guestname=null,cut="0",snake_bite="0",other="0",heat_attck="0",unconsious="0",traning="0",record="0",status="0",eh_id; //status 0 new,1 update
    FamilyMembersModel m_data;
    FirstAidModel f_a_data;
    public static void openActitvity(FirstAidModel firstAidModelList, String view) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid_add);
        ButterKnife.bind(this);

        cb_incident_record_keeping.setVisibility(View.GONE);
        cb_training_of_various_record.setVisibility(View.GONE);
        if (getIntent().hasExtra("type")){
            Intent i = getIntent();
            type=i.getStringExtra("type");

            switch (type){
                case "add":
                    isEdit=true;
                   if(i.getStringExtra("v_id")==null){
                       m_data=i.getParcelableExtra("m_data");
                       vid=m_data.getVillage_id();

                       hid=m_data.getHead_id();
                       if(m_data.getName()!=null){
                           membername=m_data.getName();
                       }else{
                           membername=m_data.getHead_name();
                       }

                       tv_family_head.setText(m_data.getHead_name()+" ("+m_data.getName()+")");
                   }else{
                       //GUEST
                       vid=i.getStringExtra("v_id");
                       hid="";
//                       vid=i.getStringExtra("v_id");
                       tv_family_head.setText(R.string.guest);
                   }
                    Log.d(TAG, "onCreate: ");
                    if (m_data==null){
                        vid=i.getStringExtra("v_id");
                        guestName.setVisibility(View.VISIBLE);
                    }
                    Log.d(TAG, "onCreate: vid "+vid);
                    break;
                case "view":
                    isEdit=false;

                    f_a_data=i.getParcelableExtra("m_data");
                    getEmegencyView();
                    break;
                case "edit":
                    isEdit=true;
                    f_a_data=i.getParcelableExtra("m_data");

                    getEmegencyView();

                    break;
            }

            viewInit();

        }

        tv_date.setOnClickListener(this);
        cb_cuts.setOnClickListener(this);
        cb_heart_attacks.setOnClickListener(this);
        cb_uncounsious.setOnClickListener(this);
        cb_other.setOnClickListener(this);
        cb_snake_bites.setOnClickListener(this);
        cb_training_of_various_record.setOnClickListener(this);
        cb_incident_record_keeping.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
    }

    private void getEmegencyView() {

        status="1";
        Log.d(TAG, "getEmegencyView: "+f_a_data.toString());
        eh_id=f_a_data.getId();
        vid=f_a_data.getVillage_id();
        hid=f_a_data.getHead_id();
        headname=f_a_data.getHead_name();
        String all=f_a_data.getAll();
        date=f_a_data.getInspection_date();
//        me=f_a_data.get
//        Head_id();
        remark=f_a_data.getRemarks();

        if(all.contains("Cuts")){
            cut="1";
            cb_cuts.setChecked(true);
        }
        if(all.contains("Heart attack")){
            heat_attck="1";
            cb_heart_attacks.setChecked(true);}
        if(all.contains("Unconscious")){
            unconsious="1";
            cb_uncounsious.setChecked(true);}
        if(all.contains("Other")){
            other="1";
            cb_other.setChecked(true);}

        if(all.contains("Snake bites")){
            snake_bite="1";
            cb_snake_bites.setChecked(true);}
        if(all.contains("Training")){
            traning="1";
            cb_training_of_various_record.setChecked(true);}
        if(all.contains("Incident Record")){
            record="1";
            cb_incident_record_keeping.setChecked(true);}


        et_remarks.setText(remark);
        tv_date.setText(date);
        tv_family_head.setText(headname);


        if (f_a_data.getGuest_patient_name()!=null){
            guestName.setVisibility(View.VISIBLE);
            guestName.setText(f_a_data.getGuest_patient_name());
        }


    }

    private void viewInit() {
        if (!isEdit){
            tv_date.setEnabled(false);
            cb_cuts.setEnabled(false);
            cb_heart_attacks.setEnabled(false);
            cb_uncounsious.setEnabled(false);
            cb_other.setEnabled(false);
            cb_snake_bites.setEnabled(false);
            cb_training_of_various_record.setEnabled(false);
            cb_incident_record_keeping.setEnabled(false);
            btn_submit.setEnabled(false);
        }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case EMERGENCY_ADD:
                if (response.isSuccessful()){
                    BaseResponse baseResponse =(BaseResponse) response.body();
                    if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                        ToastUtils.shortToast(baseResponse.getStatusMessage());
                        finish();
                    }else {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());
                    }
                }else {
                    ToastUtils.shortToast(response.message());
                }
                break;
            case EMERGENCY_VIEW:
                if (response.isSuccessful()){
                    BaseResponse baseResponse =(BaseResponse) response.body();
                    if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                        if (baseResponse.getEmergencyDetail()!=null){
                            FirstAidModel firstAidModel=baseResponse.getEmergencyDetail();
                            eh_id=firstAidModel.getId();
                            tv_date.setText(firstAidModel.getDate());
                            et_remarks.setText(firstAidModel.getRemarks());
                            if (firstAidModel.getCuts().equalsIgnoreCase("1")){
                                cb_cuts.setChecked(true);
                            }
                            if (firstAidModel.getHeart_attacks().equalsIgnoreCase("1")){
                                cb_heart_attacks.setChecked(true);
                            }
                            if (firstAidModel.getUnconscious().equalsIgnoreCase("1")){
                                cb_uncounsious.setChecked(true);
                            }
                            if (firstAidModel.getOther().equalsIgnoreCase("1")){
                                cb_other.setChecked(true);
                            }

                            if (firstAidModel.getSnake_bites().equalsIgnoreCase("1")){
                                cb_snake_bites.setChecked(true);
                            }
                            if (firstAidModel.getTraining().equalsIgnoreCase("1")){
                                cb_training_of_various_record.setChecked(true);
                            }
                            if (firstAidModel.getIncident_record().equalsIgnoreCase("1")){
                                cb_incident_record_keeping.setChecked(true);
                            }
                        }

                    }else {
                        ToastUtils.shortToast(baseResponse.getStatusMessage());
                    }
                }else {
                    ToastUtils.shortToast(response.message());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_date:
                getDateDialog();
                break;
            case R.id.cb_cuts:
                if (cut.equalsIgnoreCase("1")){
                    cut="0";
                    cb_cuts.setChecked(false);
                }else{
                    cut="1";
                    cb_cuts.setChecked(true);
                }
                break;
            case R.id.cb_heart_attacks:
                if (heat_attck.equalsIgnoreCase("1")){
                    heat_attck="0";
                    cb_heart_attacks.setChecked(false);
                }else{
                    heat_attck="1";
                    cb_heart_attacks.setChecked(true);
                }
                break;
            case R.id.cb_uncounsious:
                if (unconsious.equalsIgnoreCase("1")){
                    unconsious="0";
                    cb_uncounsious.setChecked(false);
                }else{
                    unconsious="1";
                    cb_uncounsious.setChecked(true);
                }
            case R.id.cb_other:
                if (other.equalsIgnoreCase("1")){
                    other="0";
                    cb_other.setChecked(false);
                }else{
                    other="1";
                    cb_other.setChecked(true);
                }

                break;
            case R.id.cb_snake_bites:
                if (snake_bite.equalsIgnoreCase("1")){
                    snake_bite="0";
                    cb_snake_bites.setChecked(false);
                }else{
                    snake_bite="1";
                    cb_snake_bites.setChecked(true);
                }
                break;
            case R.id.cb_training_of_various_record:
                if (traning.equalsIgnoreCase("1")){
                    traning="0";
                    cb_training_of_various_record.setChecked(false);
                }else{
                    traning="1";
                    cb_training_of_various_record.setChecked(true);
                }
                break;
            case R.id.cb_incident_record_keeping:
                if (record.equalsIgnoreCase("1")){
                    record="0";
                    cb_incident_record_keeping.setChecked(false);
                }else{
                    record="1";
                    cb_incident_record_keeping.setChecked(true);
                }
                break;
            case R.id.btn_submit:
                if(validate()){
                    addData();
                }
                break;
        }

    }

    private boolean validate() {
        if (tv_date.getText().toString().equalsIgnoreCase(null)||tv_date.getText().toString().equalsIgnoreCase("")){
            tv_date.setError("select date");
            return false;}

        if (et_remarks.getText().toString()==null){
            et_remarks.setError("Add Remark");
            return false;
        }

        remark=et_remarks.getText().toString();
        return true;
    }

    private void addData() {
        UserService.addFirstAiddetails(this,vid,hid,membername,guestName.getText().toString(),date,cut,heat_attck,unconsious,snake_bite,other,traning,record,remark,eh_id,status,this,EMERGENCY_ADD);
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(FirstAidAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateString =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                tv_date.setText(dateString);
                date=dateString;
                Log.d("TAG", "onDateSet: "+dateString);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

}