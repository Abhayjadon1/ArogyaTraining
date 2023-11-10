package com.inventics.ekalarogya.training.main.dashotheractivity.herbalremedies.medprep;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.VanaushadhiModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.VanAushadhiDeialResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.Calendar;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class VanAushadhiAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
@BindView(R.id.tv_title) TextView tv_title;
@BindView(R.id.iv_back) ImageView iv_back;

@BindView(R.id.tv_date) TextView tv_date;
@BindView(R.id.pat_name) EditText pat_name;
@BindView(R.id.pat_age) EditText pat_age;
@BindView(R.id.cbMale) CheckBox cbMale;
@BindView(R.id.cbFemale) CheckBox cbFemale;
@BindView(R.id.pat_problem) EditText pat_problem;
@BindView(R.id.pat_prescription) EditText pat_prescription;
@BindView(R.id.pat_fee) EditText pat_fee;
@BindView(R.id.treatStatus)
Spinner treatStatus;
@BindView(R.id.btn_submit)
Button btn_submit;
@BindView(R.id.pat_phone_et)
EditText pat_phone_et;

    @BindArray(R.array.treat_option)
    String[] treat_option;
    //village_id,date,head_id,member_name,pat_phone,age,problem,prescription,fees,treatment_status,status,patient_id
String name,age,gender,head_id="0",pat_phone,problm,prescription,fee="0",date,treat_status,patient_id;//patId for update only else null
String type,v_id,eap_id;
boolean isEdit=false;
VanaushadhiModel data;
int status;


private static final String TAG = VanAushadhiAdd.class.getSimpleName();
private final static int VIEW_PATIENT_DETAILS=111;
private final static int ADD_PATIENT_DETAILS=131;
private final static int EDIT_PATIENT_DETAILS=161;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_aushadhi_add);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("type")){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            switch (type){
                case "add":
                    isEdit=true;
                    status=0;
                    v_id=i.getStringExtra("v_id");
                    break;
                case "view":
                    data=i.getParcelableExtra("m_data");
                    getPatientDetails(data.getId());//id is pateint id
                    v_id=data.getVillage_id();
                    break;
                case "edit":
                    status=1;
                    data=i.getParcelableExtra("m_data");
                    isEdit=true;
                    getPatientDetails(data.getId());//id is pateint id
                    v_id=data.getVillage_id();
                    break;

            }
        }
        treat_option = getResources().getStringArray(R.array.treat_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.weekly_spinner_custom_row, treat_option);
        treatStatus.setAdapter(adapter);
        treatStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), treat_option[i], Toast.LENGTH_LONG).show();
                Log.d(TAG, "onItemSelected: "+i);
                if(i==1){
                    treat_status="1";
                }else if(i==2){
                    treat_status="0";
                }else if(i==3){
                    treat_status="2";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        if (!isEdit){
            tv_date.setEnabled(false);
            pat_problem.setEnabled(false);
            pat_prescription.setEnabled(false);
            pat_fee.setEnabled(false);
            pat_name.setEnabled(false);
            pat_age.setEnabled(false);
            cbMale.setEnabled(false);
            cbFemale.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }

        cbFemale.setOnClickListener(this);
        cbMale.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_date.setOnClickListener(this);

       try{
           if (gender.equalsIgnoreCase("1")){
               cbFemale.setChecked(true);
           }else if (gender.equalsIgnoreCase("0")){
               cbMale.setChecked(true);
           }
       }catch (Exception e){
           Log.d(TAG, "onCreate: trycatch"+e.getMessage());
       }
    }

    private void getPatientDetails(String id) {
        UserService.getvanAushadhiDetails(this,id,this,VIEW_PATIENT_DETAILS);
    }
    private void getPatientAdd() {//village_id,date,head_id,member_name,pat_phone,age,problem,prescription,fees,treatment_status,status,patient_id
        UserService.getvanAushadhiAdd(this,v_id,tv_date.getText().toString(),head_id,null,pat_name.getText().toString(),pat_phone,age,problm,prescription,fee,treat_status, String.valueOf(status),patient_id,this,ADD_PATIENT_DETAILS);
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case VIEW_PATIENT_DETAILS:
                if (response.isSuccessful()){
                    VanAushadhiDeialResponse vanAushadhiDeialResponse=(VanAushadhiDeialResponse) response.body();
                    if (vanAushadhiDeialResponse!=null) {
                        if (vanAushadhiDeialResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
//                            tv_title.setText(vanAushadhiDeialResponse.getList_name());
                            eap_id=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getEap_id();
                            date=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getDate();

                            tv_date.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getAppointment_date());
                            gender=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getGender();
                            pat_age.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getAge());

//                          if(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getPatient_name()!=null){
                                pat_name.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getPatient_name());
//                                gender=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getMember_gender();
//                            }
//                            else if(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getHead_name()!=null){
//                                pat_name.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getHead_name());
//                                gender=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getHead_gender();
//                            }
                            pat_phone_et.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getPatient_mobile());

//                            pat_name.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getPat_name());
                            pat_fee.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getFees());
                            pat_prescription.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getPrescription());
                            pat_problem.setText(vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getProblem());
                            treat_status=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getCured();
                            patient_id=vanAushadhiDeialResponse.getVanAusadhiDetailModelRsp().getId();

                            if(gender!=null){
                                if (gender.equalsIgnoreCase("female")){
                                    cbFemale.setChecked(true);
                                    gender="1";
                                }else if (gender.equalsIgnoreCase("male")){
                                    cbMale.setChecked(true);
                                    gender="0";
                                }
                            }

                            if (treat_status.equalsIgnoreCase("yes")){
                                treatStatus.setSelection(1);
                            }else if(treat_status.equalsIgnoreCase("no")){
                                treatStatus.setSelection(2);
                            }else if(treat_status.equalsIgnoreCase("referred")){
                                treatStatus.setSelection(3);
                            }

                        }else {
                            ToastUtils.shortToast(vanAushadhiDeialResponse.getMessage());
                        }
                    }
                }
                break;
            case ADD_PATIENT_DETAILS:
                if (response.isSuccessful()){
                    VanAushadhiDeialResponse vanAushadhiDeialResponse=(VanAushadhiDeialResponse) response.body();
                    if (vanAushadhiDeialResponse!=null) {
                        if (vanAushadhiDeialResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
                            ToastUtils.shortToast(vanAushadhiDeialResponse.getMessage());
                            finish();
                        }else {
                            ToastUtils.shortToast(vanAushadhiDeialResponse.getMessage());
                        }
                    }else {
                        ToastUtils.shortToast(vanAushadhiDeialResponse.getMessage());

                    }
                }
                break;
            case EDIT_PATIENT_DETAILS:

                break;

        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        ToastUtils.shortToast(t.getMessage());
        Log.d(TAG, "onFailure: "+t.getMessage());
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                if(validate()){
                    getPatientAdd();
                }
                break;
            case R.id.cbFemale:
                gender="1";
                cbMale.setChecked(false);
                break;
            case R.id.cbMale:
                gender="0";
                cbFemale.setChecked(false);

                break;
            case R.id.tv_date:
                getDateDialog();
                break;

        }
    }

    private boolean validate() {
        date=tv_date.getText().toString();
        name=pat_name.getText().toString();
        prescription=pat_prescription.getText().toString();
        problm=pat_problem.getText().toString();
        fee=pat_fee.getText().toString();
        age=pat_age.getText().toString();

//        if (gender==null){
//            ToastUtils.shortToast("Please select gender");
//            return false;
//        }
        age=pat_age.getText().toString();
         if (age==null){
            pat_age.setError("Enter age");
             return false;

         }
         pat_phone=pat_phone_et.getText().toString();
         if (pat_phone==null){
             pat_phone_et.setError("Enter Mob no.");
             return false;

         }

         name=pat_name.getText().toString().trim();
         if (name==null){
            pat_name.setError("Enter Name");
             return false;

         }
        fee=pat_fee.getText().toString().trim();
        if (fee==null){
             pat_fee.setError("Enter fees");
             return false;


         }
        prescription=pat_prescription.getText().toString();
         if (prescription==null){
             pat_prescription.setError("Fill prescription");
             return false;
         }

        date=tv_date.getText().toString();
        if (date==null){
            tv_date.setError("Enter Date");
            return false;
        }
        if (pat_phone_et.getText().toString()==null){
            pat_phone_et.setError("Enter Phone no.");
            return false;
        }

//        if (gender==null){
//            ToastUtils.shortToast("Select gender");
//            return false;
//        }
        if (treat_status==null||treat_status.equalsIgnoreCase(treat_option[0])){
            ToastUtils.shortToast(getResources().getString(R.string.select));
            return false;
        }


        return true;
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(VanAushadhiAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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