package com.inventics.ekalarogya.training.main.dashotheractivity.herbalremedies.ayurvedic;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.spinner.TreatmentAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.HerbalRemediesModel;
import com.inventics.ekalarogya.training.models.TreatmentTypeModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.GetAllListResponse;
import com.inventics.ekalarogya.training.rest.response.HerbalRemediesResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.DateUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AyurvedicAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
    private Handler handler;
    @BindView(R.id.spin_month)
    Spinner spin_month;
    TextView tv_title,total_pateint,tv_date;
    EditText et_no_male,et_no_female,et_no_child,total_cured;
    Spinner village_spin,treatment_type_spin;
    Button btnSubmit;
    ImageView iv_back;
    String selectYear,selectMonth;
    @BindArray(R.array.months_array)
    String[] monthsArray;
    private final static int ADD_DATA_AYUVEDIC=111;
    private final static int GET_ALL_LIST=131;
    private static final int GET_VILLAGE_LIST=115;
    private List<VillageListModel> villName = new ArrayList<>();
    private List<TreatmentTypeModel> treatmentName = new ArrayList<>();
    boolean isEdit=false;
    VillageListArrayAdapter adapter;
    TreatmentAdapter treatmentAdapter;
    HerbalRemediesModel data;
    String camp_month,village,no_male="0",no_frmale="0",no_child="0",total_pt="0",total_cure="0",treat_type,status="0",herbal_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayurvedic_add);
        ButterKnife.bind(this);
//        treat_type="Telemedicine";
//        populateMonthlySpinner();
//        getDateDialog();
        tv_title=findViewById(R.id.tv_title);
        tv_date=findViewById(R.id.tv_date);
        total_pateint=findViewById(R.id.tv_total_patient);
        et_no_male=findViewById(R.id.et_no_of_male);
        et_no_female=findViewById(R.id.et_no_of_female);
        et_no_child=findViewById(R.id.et_no_of_children);
        total_cured=findViewById(R.id.et_cured);
        village_spin=findViewById(R.id.spin_village_name);
        spin_month=findViewById(R.id.spin_month);
        treatment_type_spin=findViewById(R.id.treat_type_spin);
        btnSubmit=findViewById(R.id.btn_submit);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });

//        spin_month.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateDialog();
            }
        });

        et_no_female.addTextChangedListener(textWatcher);
        et_no_male.addTextChangedListener(textWatcher);
        et_no_child.addTextChangedListener(textWatcher);

        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            switch(getIntent().getStringExtra("type")){
                case "add":
                    isEdit=true;
                    break;
                case "view":
                    data=getIntent().getParcelableExtra("m_data");
//                    tv_title.setText("Anemia Checkup result");
                    Log.d("TAG", "onCreate: getintent view ");
                    isEdit=false;
//                    viewMemberDetails(head_id,member_id);
                    setupMemberData(data);
                    Log.d("TAG", "onCreate: setupm "+data.toString());
                    break;
                case "edit":
                    status="1";
                    Log.d("TAG", "onCreate: getintent edit ");
                    isEdit=true;
                    data=getIntent().getParcelableExtra("m_data");
                    setupMemberData(data);
                    Log.d("TAG", "onCreate: setupm "+data.toString());

                    break;

            }
        }else{
            ToastUtils.shortToast(getResources().getString(R.string.no_data));
            finish();
        }
        getVillageList();
        getAllList();

        village_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                VillageListModel clickedItem = (VillageListModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getVillage_name();
                village= clickedItem.getVillage_id();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        treatment_type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TreatmentTypeModel clickedItem = (TreatmentTypeModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getTitle();
                treat_type= clickedItem.getId();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getAllList() {
        Log.d("TAG", "getAllList: ");
        UserService.getOccupationList(this,ArogyaApplication.getCurrentLocale(this),this,GET_ALL_LIST);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupMemberData(HerbalRemediesModel data) {
        if(!isEdit){
            Log.d("TAG", "setupMemberData: onCreate");
            spin_month.setEnabled(false);
            village_spin.setEnabled(false);
            et_no_child.setEnabled(false);
            et_no_male.setEnabled(false);
            et_no_female.setEnabled(false);
            treatment_type_spin.setEnabled(false);
            total_cured.setEnabled(false);
            btnSubmit.setVisibility(View.GONE);
        }
        herbal_id=data.getId();
//        spin_month.setText(data.getCamp_month());
        village=data.getVillage_id();
        treat_type=data.getTreatment_type();
        camp_month=data.getCamp_month();
        et_no_child.setText(data.getChildren_present());
        et_no_male.setText(data.getMale_present());
        et_no_female.setText(data.getFemale_present());
//        treatment_type_spin.setText(data.getCamp_month());
        total_cured.setText(data.getTotal_cured());
        total_pateint.setText(data.getTotal_present());
        tv_date.setText(data.getCamp_month());
//        btnSubmit.setVisibility(View.VISIBLE);

        if (villName!=null&&villName.size()>0){
            Log.d("TAG", "setupMemberData:Village set "+villName.size()+ "    "+villName.indexOf(village));
//            village_spin.setSelection(villName.indexOf(village));
            for (int i=0;i<villName.size();i++){
                if (villName.get(i).getVillage_id().equalsIgnoreCase(village)){
                    village_spin.setSelection(i);
                }
            }
        }else{
            startDelayHandler();
        }

        if (treatmentName!=null&&treatmentName.size()>0){
            Log.d("TAG", "setupMemberData:treat set "+treatmentName.size()+ "    "+treatmentName.indexOf(treat_type));
//            village_spin.setSelection(villName.indexOf(village));
            for (int i=0;i<treatmentName.size();i++){
                if (treatmentName.get(i).getTitle().equalsIgnoreCase(treat_type)){
                    treatment_type_spin.setSelection(i);
                }
            }
        }else{
            startDelayHandler();
        }

//        if (monthsArray!=null&&monthsArray.length>0){
//            for (int i=0;i<monthsArray.length;i++){
////                String x = String.valueOf(Array.get(monthsArray, i));
////                Log.d("TAG", "setupMemberData: x"+x);
//                if (String.valueOf(Array.get(monthsArray, i)).equalsIgnoreCase(camp_month)){
//                    Log.d("TAG", "setupMemberData: "+i);
//                    spin_month.setSelection(i);
//                }
//            }
////            village_spin.setSelection(villName.indexOf(village));
//        }
    }
    private void startDelayHandler() {
        handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    Runnable runnable = () -> {
        Log.d("TAG", "runnnnble: ");
       setupMemberData(data);
    };

    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case GET_VILLAGE_LIST:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getVillageListModelList().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().size());
                                village_spin.setAdapter(adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getVillageListModelList().size(); i++){
                                        villName.add(baseResponse.getVillageListModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().get(i).getVillage_name());
                                        adapter = new VillageListArrayAdapter(this, villName);
                                        adapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                village_spin.setAdapter(adapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;

            case GET_ALL_LIST:
                if (response.isSuccessful()){
                    GetAllListResponse baseResponse=(GetAllListResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getTreatmentTypes().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getTreatmentTypes().size());
                                treatment_type_spin.setAdapter(treatmentAdapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getTreatmentTypes().size(); i++){
                                        treatmentName.add(baseResponse.getTreatmentTypes().get(i));
                                        Log.d("TAG", "onResponse: size treatment "+baseResponse.getTreatmentTypes().get(i).getTitle());
                                        treatmentAdapter = new TreatmentAdapter(this, treatmentName);
                                        treatmentAdapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                treatment_type_spin.setAdapter(treatmentAdapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getMessage());
                        }
                    }
                }
                break;
            case ADD_DATA_AYUVEDIC:
                if (response.isSuccessful()){
                    HerbalRemediesResponse baseResponse=(HerbalRemediesResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatus());
                            finish();

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatus());
                        }
                    }
                }else{
                    ToastUtils.shortToast(response.message());
                }
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
            case R.id.btn_submit:
                if(validate()){
                    submitdata();
                }
                break;
            case R.id.spin_month:
                populateMonthlySpinner();
                break;

        }

    }

    private boolean validate() {
        if (selectMonth==null){
            ToastUtils.shortToast(getResources().getString(R.string.enter_date));
            return false;}
        if (et_no_child.getText().length()==0){
            et_no_child.setError(getResources().getString(R.string.no_of_child));
            return false;}
        if (et_no_male.getText().length()==0){
            et_no_male.setError(getResources().getString(R.string.no_of_male));
            return false;}
        if (et_no_female.getText().length()==0){
            et_no_female.setError(getResources().getString(R.string.no_of_female));
            return false;}
        if (total_cured.getText().length()==0){
            total_cured.setError(getResources().getString(R.string.cured_no));
            return false;}
        if (village==null){
            ToastUtils.shortToast(getResources().getString(R.string.village));
            return false;}
//        if (treat_type==null){
//            ToastUtils.shortToast("Select Treatment type");
//            return false;}

        total_cure=total_cured.getText().toString();


        return true;
    }

    private void submitdata() {
        UserService.addAyurvedicTreatment(this,village,selectMonth,no_male,no_frmale,no_child,total_pt,treat_type,total_cure,status,herbal_id,this,ADD_DATA_AYUVEDIC);
    }

//    private int getDateDialog() {
////
////            MonthYearPickerDialogAssisnment pickerDialog = new MonthYearPickerDialogAssisnment();
////
////            pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
////                @Override
////                public void onDateSet(DatePicker datePicker, int newyear, int yearOfmonth, int i2) {
////                    Log.v("TAG", "onDateSet: "+newyear+" "+yearOfmonth);
////                    selectYear= String.valueOf(newyear).trim();
////                    spin_month.setText(selectYear);
////
////                }
////            });
////            pickerDialog.show(this.getSupportFragmentManager(), "MonthYearPickerDialogAssisnment");
//
//
//        String currentMonth = DateUtils.getDateAndTime(System.currentTimeMillis(), DateUtils.MONTH_FORMAT);
//        if (TextUtils.isEmpty(currentMonth))
//            return 0;
//        for (int i = 0; i < monthsArray.length; i++) {
//            if (monthsArray[i].equalsIgnoreCase(currentMonth)) {
//                return i;
//            }
//        }
//        return 0;
//
//
////        MonthYearPickerDialog dialog=new MonthYearPickerDialog();
////        dialog.setListener(new DatePickerDialog.OnDateSetListener() {
////            @Override
////            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
////                selectYear=year+"";
////                selectMonth=(month)+"";
////                spin_month.setText(selectMonth+"-"+selectYear);
////            }
////        });
////        dialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
//
//    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AyurvedicAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                tv_date.setText(date);
                selectMonth=date;
                Log.d("TAG", "onDateSet: "+date);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }
    private void populateMonthlySpinner() {
        //monthSpinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.weekly_spinner_custom_row, monthsArray);
        spin_month.setAdapter(adapter);
        spin_month.setSelection(getCurrentMonthIndex());
        spin_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemSelected: " + monthsArray[i]);
//                selectMonth= DateUtils.getTodayDateFromMonth(i  );
                selectMonth=String.valueOf(i);
                Log.d("TAG", "onItemSelected: "+selectMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private int getCurrentMonthIndex() {
        String currentMonth = DateUtils.getDateAndTime(System.currentTimeMillis(), DateUtils.MONTH_FORMAT);
        if (TextUtils.isEmpty(currentMonth))
            return 0;
        for (int i = 0; i < monthsArray.length; i++) {
            if (monthsArray[i].equalsIgnoreCase(currentMonth)) {
                return i;
            }
        }
        return 0;
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get the content of both the edit text
//
            try{
                no_child = et_no_child.getText().toString();
                no_frmale = et_no_female.getText().toString();
                no_male = et_no_male.getText().toString();

                total_pt= String.valueOf(Integer.parseInt(no_child)+Integer.parseInt(no_frmale)+Integer.parseInt(no_male));
                Log.d("TAG", "onTextChanged: "+total_pt);
                total_pateint.setText(String.valueOf(total_pt));

            }catch (Exception e){

            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                no_child = et_no_child.getText().toString();
                no_frmale = et_no_female.getText().toString();
                no_male = et_no_male.getText().toString();

                total_pt= String.valueOf(Integer.parseInt(no_child)+Integer.parseInt(no_frmale)+Integer.parseInt(no_male));
                Log.d("TAG", "onTextChanged: "+total_pt);
                total_pateint.setText(String.valueOf(total_pt));

            }catch (Exception e){

            }
        }
    };


}