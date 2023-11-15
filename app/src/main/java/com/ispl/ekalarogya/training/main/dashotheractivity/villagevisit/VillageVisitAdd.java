package com.ispl.ekalarogya.training.main.dashotheractivity.villagevisit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.spinner.PurposeAdapter;
import com.ispl.ekalarogya.training.adapters.spinner.SevikaAdapter;
import com.ispl.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.PurposeModel;
import com.ispl.ekalarogya.training.models.SevikaModel;
import com.ispl.ekalarogya.training.models.VillageListModel;
import com.ispl.ekalarogya.training.models.VillageVisitModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.GetAllListResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class VillageVisitAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener{
    private Handler handler;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
//    @BindView(R.id.et_resposnse)
//    EditText et_resposnse;
    @BindView(R.id.spin_village_name)
    Spinner spin_village_name;
    @BindView(R.id.spin_sanyojika_name)
    Spinner spin_sanyojika_name;
    @BindView(R.id.spin_purspose)
    Spinner spin_purspose;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.etSanyojika)
    EditText etSanyojika;

    @BindView(R.id.cb_visit_yes)
    RadioButton cb_visit_yes;
    @BindView(R.id.cb_visit_no)
    RadioButton cb_visit_no;

    final static int GET_ALL_LIST=111;
    final static int GET_ALL_DETAILS_LIST=161;
    final static int ADD_ALL_DETAIL=131;
    final static int GET_SANYOJIKA=141;
    private static final int GET_VILLAGE_LIST=115;
    private List<VillageListModel> villName = new ArrayList<>();
    private List<PurposeModel> purposeModels = new ArrayList<>();
    private List<SevikaModel> sevikaModels = new ArrayList<>();
    boolean isEdit=false;
    VillageVisitModel v_model;
    VillageListArrayAdapter villageArrayAdapter;
    PurposeAdapter purposeAdapter;
    SevikaAdapter sevikaAdapter;

String visit_date,visit_yes_no,responsebility,purpose,sanyohika_name,village,status="0",visit_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_visit_add);
        ButterKnife.bind(this);

//        getSanyojika();
        getVillageList();
        getAllList();
        if (getIntent().hasExtra("type")){
            String type= getIntent().getStringExtra("type");
            switch (type){
                case "view":
                    isEdit=false;
                    v_model=getIntent().getParcelableExtra("m_data");
                    getdata(v_model.getId());
                    village=v_model.getVillage_id();
                    Log.d("TAG", "onCreate: "+v_model.toString());
                    break;
                case "edit":
                    isEdit=true;
                    v_model=getIntent().getParcelableExtra("m_data");
                    getdata(v_model.getId());
                    village=v_model.getVillage_id();
                    Log.d("TAG", "onCreate: "+v_model.toString());
                    break;
                case "add":
                    isEdit=true;

                    village=getIntent().getStringExtra("v_id");
                    break;

            }
        }
        btn_submit.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        cb_visit_no.setOnClickListener(this);
        cb_visit_yes.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        spin_village_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

//        spin_sanyojika_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                SevikaModel clickedItem = (SevikaModel)
//                        parent.getItemAtPosition(position);
//                String name = clickedItem.getName();
//                sanyohika_name= clickedItem.getName();
////                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

         spin_purspose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PurposeModel clickedItem = (PurposeModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getTitle();
                purpose= clickedItem.getId();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

         if(!isEdit){
             spin_purspose.setEnabled(false);
             spin_village_name.setEnabled(false);
             spin_sanyojika_name.setEnabled(false);
             cb_visit_yes.setEnabled(false);
             cb_visit_no.setEnabled(false);
             tv_date.setEnabled(false);
             btn_submit.setVisibility(View.GONE);
             tv_date.setEnabled(false);
         }

    }

    private void getSanyojika() {
        UserService.getSanyojikaList(this,this,GET_SANYOJIKA);
    }

    private void getAllList() {
        UserService.getOccupationList(this, ArogyaApplication.getCurrentLocale(this),this,GET_ALL_LIST);
    }
    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
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

                            if (baseResponse.getPurposeModels().size() > 0) {
                                spin_purspose.setAdapter(purposeAdapter);
                                try {
                                    for (int i = 0; i <= baseResponse.getPurposeModels().size(); i++) {
                                        purposeModels.add(baseResponse.getPurposeModels().get(i));
                                        Log.d("TAG", "onResponse: size " + baseResponse.getPurposeModels().get(i).getTitle());
                                        purposeAdapter = new PurposeAdapter(this, purposeModels);
                                        purposeAdapter.notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    Log.d("TAG", "onResponse: exception" + e.getMessage());
                                }
                                spin_purspose.setAdapter(purposeAdapter);
                            } else {
                                ToastUtils.shortToast(baseResponse.getStatus() + "\n" + baseResponse.getStatus());
                            }

//

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
                                spin_sanyojika_name.setAdapter(sevikaAdapter);
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
                                spin_sanyojika_name.setAdapter(sevikaAdapter);
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
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            finish();
                            ToastUtils.shortToast(response.message());
                        }

                    }
                }

                break;

            case GET_VILLAGE_LIST:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {

                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getVillageListModelList().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().size());
                                spin_village_name.setAdapter(villageArrayAdapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getVillageListModelList().size(); i++){
                                        villName.add(baseResponse.getVillageListModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().get(i).getVillage_name());
                                        villageArrayAdapter = new VillageListArrayAdapter(this, villName);
                                        villageArrayAdapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                spin_village_name.setAdapter(villageArrayAdapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;

            case GET_ALL_DETAILS_LIST:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            status="1";
                            VillageListModel villageListModel=baseResponse.getVillageListModelDetails();
                            //village,sanyohika_name,visit_yes_no,visit_date,responsebility,purpose,status,visit_id
                            village=villageListModel.getVillage_id();
                            visit_id=villageListModel.getId();
                            sanyohika_name=villageListModel.getSanyojika_name();
                            etSanyojika.setText(sanyohika_name);
                            visit_yes_no=villageListModel.getVisit();
                            visit_date=villageListModel.getDate();
                            responsebility=villageListModel.getResponsibility();
                            purpose=villageListModel.getPurpose();
//                            visit_id=villageListModel.getVillage_id();

                            tv_date.setText(visit_date);
//                            tv_title.setText(visit_date);
                          upadteVist();



//                            for (int i=0;i<purposeModels.size();i++){
//                                if (purpose.equalsIgnoreCase(purposeModels.get(i).getTitle())){
//                                    spin_purspose.setSelection(i);
//                                    Log.d("TAG", "onResponse: listttt"+purposeModels+"  "+purposeModels.get(i).toString());
//
//                                }else {
//                                    Log.d("TAG", "onResponse listttt : nothiii");
//                                }
//                            }

                        }
                    }
                }else{
                    ToastUtils.shortToast(response.message());
                }

                break;
        }
    }

    private void upadteVist() {
        if (visit_yes_no.equalsIgnoreCase("no")){
            cb_visit_no.setChecked(true);
            cb_visit_yes.setChecked(false);
            visit_yes_no="0";
        }else if(visit_yes_no.equalsIgnoreCase("yes")){
            cb_visit_no.setChecked(false);
            cb_visit_yes.setChecked(true);
            visit_yes_no="1";
        }
        if (villName!=null||villName.size()>0){
         updatePSinner();
        }else{
            startDelayHandler();
        }
    }

    private void updatePSinner() {
        for (int i=0;i<villName.size();i++){
            if (visit_id.equalsIgnoreCase(villName.get(i).getVillage_id())){
                spin_village_name.setSelection(i);
                Log.d("TAG", "onResponse: listttt"+villName+"  "+villName.get(i).toString());

            }else {
                Log.d("TAG", "onResponse listttt : nothiii");
            }
        }
        for (int i=0;i<purposeModels.size();i++){
            if (purpose.equalsIgnoreCase(purposeModels.get(i).getTitle())){
                spin_purspose.setSelection(i);
                Log.d("TAG", "onResponse: listttt"+purposeModels+"  "+purposeModels.get(i).toString());

            }else {
                Log.d("TAG", "onResponse listttt : nothiii");
            }
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
                if (validate()){
                    submitdata();
                }
                break;
            case R.id.tv_date:
                getDateDialog();

                break;
            case R.id.cb_visit_yes:
                visit_yes_no="1";
                cb_visit_no.setChecked(false);
                break;
            case R.id.cb_visit_no:
                visit_yes_no="0";
                cb_visit_yes.setChecked(false);
                break;
            case R.id.iv_back:
//                startActivity(new Intent(this,VillageVisit.class));
                finish();
                break;

        }

    }

    private boolean validate() {
        sanyohika_name=etSanyojika.getText().toString();
        if (village==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_village));
            return false;
        }
        if (visit_yes_no==null){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;
        }
        if (sanyohika_name==null){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;
        }
        if (purpose==null){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;
        }
        if (tv_date.getText().toString()==null){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;
        }else {
            visit_date=tv_date.getText().toString();
        }

        responsebility="Major ";//responsiblity box is hidden
//        if (village==null){
//            ToastUtils.shortToast("Select village");
//            return false;
//        }


        return true;
    }

    private void submitdata() {
        UserService.addVillagevisitData(this,village,sanyohika_name,visit_yes_no,visit_date,responsebility,purpose,status,visit_id,this,ADD_ALL_DETAIL);

    }
    private void getdata(String id) {
        visit_id=id;

        UserService.getVillagevisitDetails(this,visit_id,this,GET_ALL_DETAILS_LIST);

    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(VillageVisitAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);


                String monthnm=String.valueOf(monthOfYear + 1);
                Log.d("TAG", "onDateSet: "+date);
                Log.d("TAG", "onDateSet: "+monthnm);
//                camp_month= String.valueOf(monthOfYear);
                tv_date.setText(date);
                visit_date=date;
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }
    private void startDelayHandler() {
        handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    Runnable runnable = () -> {
        Log.d("TAG", "runnnnble: ");
//        ToastUtils.shortToast("Getting data from server");
        dataLoad();
    };

    private void dataLoad() {
//        getSanyojika();
        getVillageList();
        getAllList();
    }

    @Override
    protected void onResume() {

        super.onResume();

    }
}