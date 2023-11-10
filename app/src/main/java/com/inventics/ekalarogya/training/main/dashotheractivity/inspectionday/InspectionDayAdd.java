package com.inventics.ekalarogya.training.main.dashotheractivity.inspectionday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.InspectionAdapterList;
import com.inventics.ekalarogya.training.adapters.spinner.SevikaAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.InspectionModel;
import com.inventics.ekalarogya.training.models.InspectionStayModel;
import com.inventics.ekalarogya.training.models.SevikaModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.models.VillageVisitModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.GetAllListResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class InspectionDayAdd  extends AppCompatActivity implements RetroAPICallback {

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.rv_subjects)
    RecyclerView rv_subjects;

    @BindView(R.id.et_remarks)
    EditText et_remarks;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.spinner_sanyojika)
    Spinner spinner_sanyojika;

    final static int GET_ALL_LIST=111;
    final static int GET_ALL_DETAILS=161;
    final static int ADD_ALL_DETAIL=131;
    final static int GET_SANYOJIKA=141;
    private static final int GET_VILLAGE_LIST=115;
    private List<VillageListModel> villName = new ArrayList<>();
    private List<InspectionModel> inspectionModelsList = new ArrayList<>();
    private List<SevikaModel> sevikaModels = new ArrayList<>();
    boolean isEdit=false;
    VillageVisitModel v_model;
    VillageListArrayAdapter villageArrayAdapter;
    InspectionAdapterList inspectionAdapter;
    InspectionStayModel data;
    SevikaAdapter sevikaAdapter;

   final private static int ADD_INSPECTION=111;

    private static final String TAG = InspectionDay.class.getSimpleName();
    String type, id,headId,village_id,sanyojak,inspection_date,vidyalay_grade,jagran_grade,family_grade,poster_grade,vanaushadhi_grade,other_grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_day_add);
        ButterKnife.bind(this);
        getSanyojika();
        getAllList();
        if (getIntent().hasExtra("type")){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            switch (type){
                case "add":
                    isEdit=true;
                    //status=0;
//                    v_id=i.getStringExtra("v_id");
                    break;
                case "view":
                    isEdit=false;
                    data=i.getParcelableExtra("m_data");
                    break;
                case "edit":
//                    status=1;
//                    data=i.getParcelableExtra("m_data");
//                    isEdit=true;
//                    getPatientDetails(data.getId());//id is pateint id
//                    v_id=data.getVillage_id();
                    break;

            }
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(InspectionDayAdd.this, 1);
        rv_subjects.setLayoutManager(gridLayoutManager);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateDialog();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    addInspection();
                }
            }
        });
        spinner_sanyojika.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                SevikaModel clickedItem = (SevikaModel) parent.getItemAtPosition(i);
                sanyojak=clickedItem.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        if (type.equalsIgnoreCase("view")||type.equalsIgnoreCase("edit")){
//            if (inspectionModelsList!=null&&inspectionModelsList.size()>0){
//                setupData();
//            }else{
//                ToastUtils.shortToast("inspectionModelsList");
//            }
//        }
        if(!isEdit){
            spinner_sanyojika.setEnabled(false);
            tv_date.setEnabled(false);
            et_remarks.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }
    }

    private void setupData() {
        spinner_sanyojika.setEnabled(false);
        rv_subjects.setEnabled(false);
        et_remarks.setEnabled(false);
        tv_date.setEnabled(false);
        btn_submit.setEnabled(false);
        if (data.getInspection_date()!=null){
            tv_date.setText(data.getInspection_date());
            et_remarks.setText(data.getRemark());
        }
//        if (data.get()!=null){
//            tv_date.setText(data.getInspection_date());
//        }
        if (data.getSevika_id()!=null){
           for(int i=0;i<sevikaModels.size();i++){
               if (sevikaModels.get(i).getId().equalsIgnoreCase(data.getSevika_id())){
                   spinner_sanyojika.setSelection(i);
               }
           }
        }
//
            if (data.getVidyalay_grade()!=null){
               vidyalay_grade= data.getVidyalay_grade();

            }
            if (data.getJagran_grade()!=null){
                jagran_grade=data.getJagran_grade();
            }
            if (data.getFamily_grade()!=null){
                family_grade=data.getFamily_grade();
            }
            if (data.getPoster_grade()!=null){
                poster_grade=data.getPoster_grade();
            }
            if (data.getPlantation_grade()!=null){
                vanaushadhi_grade=data.getPlantation_grade();
            }
            if (data.getOther_grade()!=null){
                other_grade=data.getOther_grade();
            }

        for (int i=0;i<inspectionModelsList.size();i++){
            if (i==0){
                inspectionModelsList.get(i).setCode(vidyalay_grade);
                Log.d(TAG, "setupData: vidyalay_grade "+vidyalay_grade);
            }else if (i==1){
                inspectionModelsList.get(i).setCode(jagran_grade);
                Log.d(TAG, "setupData: jagran_grade "+jagran_grade);

            }else if (i==2){
                inspectionModelsList.get(i).setCode(family_grade);
                Log.d(TAG, "setupData: family_grade "+family_grade);

            }else if (i==3){
                inspectionModelsList.get(i).setCode(poster_grade);
                Log.d(TAG, "setupData: poster_grade "+poster_grade);

            }else if (i==4){
                inspectionModelsList.get(i).setCode(vanaushadhi_grade);
                Log.d(TAG, "setupData: vanaushadhi_grade "+vanaushadhi_grade);

            }else if (i==5){
                inspectionModelsList.get(i).setCode(other_grade);
                Log.d(TAG, "setupData: other_grade "+other_grade);

            }
        }
        Log.d(TAG, "setupData: "+inspectionModelsList.toString());
        //Update rv
        inspectionAdapter = new InspectionAdapterList(InspectionDayAdd.this, inspectionModelsList,"view");
        rv_subjects.setAdapter(inspectionAdapter);
        inspectionAdapter.notifyDataSetChanged();

    }

    private boolean validate() {
        //sanyojak,inspection_date,vidyalay_grade,jagran_grade,family_grade,poster_grade,vanaushadhi_grade

        if (tv_date.getText()==null){
            ToastUtils.shortToast(getResources().getString(R.string.enter_date));
            return false;
        }
        if (sanyojak==null){
            ToastUtils.shortToast(getResources().getString(R.string.sanyojika_na));
            return false;
        }
//        if (et_remarks.getText().toString()==null){
//            ToastUtils.shortToast("Add remark ");
//            return false;
//        }
        inspection_date=tv_date.getText().toString();
        for (int i=0;i<inspectionModelsList.size();i++){
            if (i==0&&inspectionModelsList.get(i).getCode()!=null){
                vidyalay_grade=inspectionModelsList.get(i).getCode();
            }else if (i==1&&inspectionModelsList.get(i).getCode()!=null){
                jagran_grade=inspectionModelsList.get(i).getCode();
            }else if (i==2&&inspectionModelsList.get(i).getCode()!=null){
                family_grade=inspectionModelsList.get(i).getCode();
            }else if (i==3&&inspectionModelsList.get(i).getCode()!=null){
                poster_grade=inspectionModelsList.get(i).getCode();
            }else if (i==4&&inspectionModelsList.get(i).getCode()!=null){
                vanaushadhi_grade=inspectionModelsList.get(i).getCode();
            }else if (i==5&&inspectionModelsList.get(i).getCode()!=null){
                other_grade=inspectionModelsList.get(i).getCode();
            }

        }

        return true;
    }

    private void getAllList() {
        UserService.getOccupationList(this, ArogyaApplication.getCurrentLocale(this),this,GET_ALL_LIST);
    }
    private void getSanyojika() {
        UserService.getSanyojikaList(this,this,GET_SANYOJIKA);
    }
    private void addInspection(){
        UserService.addInspectionDetails(this,sanyojak,inspection_date,vidyalay_grade,jagran_grade,family_grade,poster_grade,vanaushadhi_grade,et_remarks.getText().toString(),this,ADD_ALL_DETAIL);
    }
    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case GET_ALL_LIST:
                if (response.isSuccessful()) {
                    GetAllListResponse baseResponse = (GetAllListResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");

                            if (baseResponse.getInspectionModels().size() > 0) {
                                rv_subjects.setVisibility(View.VISIBLE);

                                Log.d(TAG, "onResponse: "+baseResponse.getInspectionModels().size());
                                inspectionModelsList.addAll(baseResponse.getInspectionModels());
                                inspectionAdapter = new InspectionAdapterList(InspectionDayAdd.this, inspectionModelsList, type);
                                inspectionAdapter.notifyDataSetChanged();
                                rv_subjects.setAdapter(inspectionAdapter);
                                if (type.equalsIgnoreCase("view")||type.equalsIgnoreCase("edit")){
                                    if (inspectionModelsList!=null&&inspectionModelsList.size()>0){
                                        setupData();
                                    }else{
//                                        ToastUtils.shortToast("inspectionModelsList");
                                    }
                                }
                            }
//                                    rv_samitiMeeting_recyclerview.setVisibility(View.VISIBLE);
//                                    tvEmpty.setVisibility(View.GONE);

                                } else {
                                ToastUtils.shortToast(baseResponse.getStatus() + "\n" + baseResponse.getStatus());
                            }
                    }
                }
                break;
            case GET_SANYOJIKA:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");

                            if (baseResponse.getSevikaModelList().size() > 0) {
                                spinner_sanyojika.setAdapter(sevikaAdapter);
                                try {
                                    for (int i = 0; i <= baseResponse.getSevikaModelList().size(); i++) {
                                        sevikaModels.add(baseResponse.getSevikaModelList().get(i));
                                        Log.d("TAG", "onResponse: size " + baseResponse.getSevikaModelList().get(i).getName());
                                        sevikaAdapter = new SevikaAdapter(this, sevikaModels);
                                        sevikaAdapter.notifyDataSetChanged();

                                    }
                                } catch (Exception e) {
                                    Log.d("TAG", "onResponse: exception" + e.getMessage());
                                }
                                spinner_sanyojika.setAdapter(sevikaAdapter);

                            } else {
                                ToastUtils.shortToast(baseResponse.getStatus() + "\n" + baseResponse.getStatus());
                            }

//

                        }
                    }
                }
                break;

            case ADD_ALL_DETAIL:
                if (response.isSuccessful()){
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)||baseResponse.getStatus().equalsIgnoreCase("sucess")) {
                            finish();
                            ToastUtils.shortToast(response.message());
                        }else{
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
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
        ToastUtils.shortToast(getResources().getString(R.string.no_internet_connection));
    }
    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(InspectionDayAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateString =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                tv_date.setText(dateString);
//                tv_date=dateString;
                Log.d("TAG", "onDateSet: "+dateString);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }
}