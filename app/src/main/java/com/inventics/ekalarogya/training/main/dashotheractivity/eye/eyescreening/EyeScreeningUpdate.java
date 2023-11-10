package com.inventics.ekalarogya.training.main.dashotheractivity.eye.eyescreening;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.spinner.HeadArrayAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.MemberArrayListAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.EyeVanModel;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.FamilyMembersModelResponse;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


public class EyeScreeningUpdate extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
    String searchData;
    private static EyeVanModel m_data;
    private  EyeVanModel m_data2;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.tv_title)
    TextView tv_title;
    //    @BindView(R.id.tvEmpty)
//    TextView tvEmpty;
    @BindView(R.id.tv_date)
    TextView tv_date;
//    @BindView(R.id.tv_age)
//    TextView tv_age;

    @BindView(R.id.tvFamilyHeadName)
    TextView tvFamilyHeadName;
    @BindView(R.id.etGuestName)
    EditText etGuestName;
    @BindView(R.id.etGuestMob)
    EditText etGuestMob;
    @BindView(R.id.tv_age)
    EditText tv_age;

    @BindView(R.id.et_remarks)
    EditText et_remark;

    @BindView(R.id.btn_submit)
    Button btn_submit;


    @BindView(R.id.spin_village_name)
    Spinner spinner_village;
    @BindView(R.id.spinner_member_name)
    Spinner spinner_member_name;
    @BindView(R.id.spin_head_name)
    Spinner spin_head_name;

    @BindView(R.id.radio_button_male)
    RadioButton radio_button_male;
    @BindView(R.id.radio_button_female)
    RadioButton radio_button_female;

    @BindView(R.id.radio_button_other)
    RadioButton radio_button_other;
    @BindView(R.id.radio_vision_yes)
    RadioButton radio_vision_yes;
    @BindView(R.id.radio_vision_no)
    RadioButton radio_vision_no;

    private Handler handler;

    private final static int ADD_DATA_REQUEST=111;
    private final static int VIEW_DATA_REQUEST=171;
    private static final int GET_VILLAGE_LIST=115;
    private static final int GET_VILLAGE_HEAD_LIST=116;
    private static final int GET_VILLAGE_MEMBER_LIST=245;
    private static final int GET_EYE_SCREENING_DETAILS=215;
    private List<VillageListModel> villName = new ArrayList<>();
    private List<VillageFamilyModel> headName = new ArrayList<>();
    private List<FamilyMembersModel> memberName = new ArrayList<>();
    boolean isEdit=false;
    VillageListArrayAdapter v_adapter;
    MemberArrayListAdapter m_adapter;
    HeadArrayAdapter h_adapter;

    private static final String TAG = EyeScreeningUpdate.class.getSimpleName();
    int page;
    String type,visionDefect="0",meeting_date,remark,village,member_id,head_id,status="0",gender,patient_id,member_name,es_id,age;
    FamilyMembersModel f_data;
    private static String typee;
    public static void openActivity(EyeVanModel eyeVanModelList, String view) {
        m_data=eyeVanModelList;
        typee=view;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_screening_add);
        ButterKnife.bind(this);
        getVillageList();
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            Log.d(TAG, "onCreate: "+type);
            switch (type) {
//                case "1":
//                    tv_title.setText("Update owner details");
//                    break;
//                case "add":
//                    isEdit=true;
//                    tv_title.setText("Add Eye Screening");
////                    m_data=i.getParcelableExtra("m_data");
//                    break;
                case "view":
                    isEdit=false;
                    tv_age.setEnabled(false);
                    m_data=i.getParcelableExtra("m_data");
                    tv_title.setText(getResources().getString(R.string.view_eye_screen));

                    break;
                case "edit":
                    isEdit=false;
                    m_data=i.getParcelableExtra("m_data");
                    tv_title.setText(getResources().getString(R.string.view_eye_screen));

                    break;
            }
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        radio_vision_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visionDefect="yes";
                updateVisionUi();
            }
        });
        radio_vision_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visionDefect="no";
                updateVisionUi();
            }
        });
        radio_button_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="male";
                updateGenderUI();

            }
        });
        radio_button_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="male";
                updateGenderUI();

            }
        });

        radio_button_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="female";
                updateGenderUI();
            }
        });
        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                VillageListModel clickedItem = (VillageListModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getVillage_name();
//                    if (h_adapter!=null){
//                        h_adapter.clear();
//                    }
                village= clickedItem.getVillage_id();
//                    getHeadList(village);
//                }

                Log.d(TAG, "onItemSelected: "+village);
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: ");
            }
        });

        spin_head_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                VillageFamilyModel clickedItem = (VillageFamilyModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getFamily_head();
                head_id = clickedItem.getFamily_head_id();

//                    if (memberName != null) {
//                        memberName.clear();
//                    }
//                    getMemberList(head_id);
                Log.d(TAG, "onItemSelected: " + head_id);

//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_member_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                FamilyMembersModel clickedItem = (FamilyMembersModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getName();
                if (clickedItem.getId() != null) {
                    member_id = clickedItem.getId();
                } else {
                    member_id = clickedItem.getHead_id();
                }
                if (type.equalsIgnoreCase("view") || type.equalsIgnoreCase("edit")) {

                } else {
                    radio_button_female.setChecked(false);
                    radio_button_male.setChecked(false);
                    radio_button_other.setChecked(false);
                    radio_vision_no.setChecked(false);
                    radio_vision_yes.setChecked(false);
                }
//                getMemberList(member_id);
                if (member_id != null) {
                    setupMemberDetails(member_id);
                }

                Log.d(TAG, "onItemSelected: " + member_id);
//                }
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        tv_date.setOnClickListener(this);
        btn_submit.setOnClickListener(this);



        if (!isEdit){
            et_remark.setEnabled(false);
            tv_date.setEnabled(false);
            spinner_village.setEnabled(false);
            spinner_member_name.setEnabled(false);
            spin_head_name.setEnabled(false);
            radio_button_male.setEnabled(false);
            radio_button_other.setEnabled(false);
            radio_button_female.setEnabled(false);
            radio_vision_yes.setEnabled(false);
            radio_vision_no.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }
        if (type.equalsIgnoreCase("view")){
            isEdit=false;
            setupDATA();


        }else if(type.equalsIgnoreCase("edit")){
            isEdit=true;
            setupDATA();

        }
    }

    private void setupDATA() {
//        getVillageList();
//        getHeadList(m_data.getVillage_id());
//        getMemberList(m_data.getHead_id());
//
//        eyeScreenDataInit(m_data);
    }

    private void eyeScreenDataInit(EyeVanModel m_data) {
        Log.d(TAG, "eyeScreenDataInit: "+m_data.toString());
        //status,village,patient_id,tv_date.getText().toString(),visionDefect,remark
        status="1";
        Log.d(TAG, "eyeScreenDataInit: bvcgscvh "+m_data.getTest_date());
        tv_date.setText(m_data.getTest_date());
        village=m_data.getVillage_id();
        head_id=m_data.getHead_id();
        visionDefect=m_data.getVision_defect();
        remark=m_data.getRemark();
        member_name=m_data.getMember_name();
        es_id=m_data.getId();

        updateVisionUi();



        if (remark!=null){
            et_remark.setText(remark);
        }
//
//       if (villName!=null&&villName.size()>0){
//           for (int i=0;i<villName.size();i++){
//               if (villName.get(i).getVillage_id().equalsIgnoreCase(village)){
//                   spinner_village.setSelection(i);
//                   Log.d(TAG, "eyeScreenDataInit: village done"+village);
////                   headName.clear();
//                   headLoad();
//               }
//           }
//       }else{
//
//           Log.d(TAG, "eyeScreenDataInit:  if (villName!=null&&villName.size()>0) : ");
////           getVillageList();
//
//       }

    }

    private void headLoad(){
        Log.d(TAG, " eyeScreenDataInit headLoad: "+village +headName.size());
        if (headName!=null&&headName.size()>0&&headName.contains(head_id)) {
            for (int i = 0; i < headName.size(); i++) {
                if (headName.get(i).getFamily_head_id().equalsIgnoreCase(head_id)) {
                    spin_head_name.setSelection(i);
                    memberName.clear();
                    Log.d(TAG, "eyeScreenDataInit: haed done"+village+" "+head_id);
                    memberLoad();
                }
            }
        }else{
            Log.d(TAG, "eyeScreenDataInit:  need  head load for village id " +village);

        }
    }
    private void memberLoad() {
        Log.d(TAG, "eyeScreenDataInit: memberName.size()  "+memberName.size());
        if (memberName!=null&&memberName.size()>0&&memberName.contains(member_id)){
            for (int i=0;i<memberName.size();i++){
                if (memberName.get(i).getName().equalsIgnoreCase(member_name)||
                        memberName.get(i).getName().equalsIgnoreCase(member_name)) {
                    spinner_member_name.setSelection(i);
                }
            }
            Log.d(TAG, "memberLoad: "+visionDefect.toString());
            updateVisionUi();
        }else{
//           getMemberList(head_id);
            startDelayHandler();
        }

    }

    private void updateVisionUi() {
        if (visionDefect!=null && visionDefect.equalsIgnoreCase("yes")){
            radio_vision_yes.setChecked(true);
            radio_vision_no.setChecked(false);
            visionDefect="1";
        }else if (visionDefect!=null && visionDefect.equalsIgnoreCase("no")){
            radio_vision_yes.setChecked(false);
            radio_vision_no.setChecked(true);
            visionDefect="0";
        }
    }

    private void setupMemberDetails(String member_id) {
        int id= Integer.parseInt(member_id);
        for (int i=0;i<memberName.size();i++){
            if (memberName.get(i).getId()!=null){
                if (memberName.get(i).getId().equalsIgnoreCase(member_id)){
//                try{
                    gender=memberName.get(i).getGender();
                    updateGenderUI();

                    tv_age.setText(memberName.get(i).getAge());

//                    if (memberName.get(id).getMember_code()==null){
//                        patient_id=memberName.get(id).getHead_code();
//                    }else{
                    patient_id= memberName.get(i).getMember_code();
//                    }

//                }catch (Exception e){
//                    Log.d(TAG, "setupMemberDetails: "+e.getMessage());
//                }
                }
            }else if(memberName.get(i).getHead_id()!=null){
                if(memberName.get(i).getHead_id().equalsIgnoreCase(member_id)){
                    Log.d(TAG, "setupMemberDetails: "+memberName.get(i).toString());
//                try{
//                    Log.d(TAG, "setupMemberDetails: "+memberName.get(id).toString());
                    tv_age.setText(memberName.get(i).getAge());

//                    if (memberName.get(id).getMember_code()!=null){
//                        patient_id= memberName.get(id).getMember_code();
//                    }else{
                    patient_id=memberName.get(i).getHead_code();

//                    }
                    gender=memberName.get(i).getGender();
                    updateGenderUI();

//                }catch (Exception e){
//                    Log.d(TAG, "setupMemberDetails: "+e.getMessage());
//                }
                }
            }
        }


    }

    private void updateGenderUI() {
        if(gender.equalsIgnoreCase("Male")){
            radio_button_male.setChecked(true);
            radio_button_female.setChecked(false);
            radio_button_other.setChecked(false);
        }else if(gender.equalsIgnoreCase("female")){
            radio_button_female.setChecked(true);
            radio_button_male.setChecked(false);
            radio_button_other.setChecked(false);
        }else if(gender.equalsIgnoreCase("other")){
            radio_button_other.setChecked(true);
            radio_button_female.setChecked(false);
            radio_button_male.setChecked(false);
        }
    }

    private void getHeadList(String vid) {
        UserService.getVillageHeadList(this,vid,searchData,page,this,GET_VILLAGE_HEAD_LIST);
    }
    private void getMemberList() { //1:Member listing with head included , 2:Member listing with head excluded
        UserService.getVillageFamilyMembersList(this,village,head_id,"1",this,GET_VILLAGE_MEMBER_LIST);
    }
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
                                spinner_village.setAdapter(v_adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getVillageListModelList().size(); i++){
                                        villName.add(baseResponse.getVillageListModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().get(i).getVillage_name());
                                        v_adapter = new VillageListArrayAdapter(this, villName);
                                        v_adapter.notifyDataSetChanged();

                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                spinner_village.setAdapter(v_adapter);
                                for (int i=0;i<villName.size();i++){
                                    if (villName.get(i).getVillage_id().equalsIgnoreCase(m_data.getVillage_id())){
                                        spinner_village.setSelection(i);
                                        village=villName.get(i).getVillage_id();
                                        if (m_data.getGuest_patient_name()==null){
                                            getHeadList(village);
                                        }else{
//                                            isGuest =true;
                                            tvFamilyHeadName.setVisibility(View.GONE);
                                            spin_head_name.setVisibility(View.GONE);
                                            spinner_member_name.setVisibility(View.GONE);
                                            etGuestName.setVisibility(View.VISIBLE);
                                            etGuestMob.setVisibility(View.VISIBLE);

                                            etGuestMob.setText(m_data.getPatient_mobile());
                                            etGuestName.setText(m_data.getGuest_patient_name());
                                            tv_age.setText(m_data.getAge());
                                            tv_date.setText(m_data.getTest_date());
                                            gender=m_data.getGender();
                                            updateGenderUI();
                                            visionDefect=m_data.getVision_defect();
                                            updateVisionUi();
                                            et_remark.setText(m_data.getRemark());

                                        }
                                        Log.d(TAG, "eyeScreenDataInit: village done"+village);
                                    }
                                }
                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;

            case GET_VILLAGE_HEAD_LIST:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getVillageFamilyModelList().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getVillageFamilyModelList().size());
                                spin_head_name.setAdapter(h_adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getVillageFamilyModelList().size(); i++){
                                        headName.add(baseResponse.getVillageFamilyModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+baseResponse.getVillageFamilyModelList().get(i).getFamily_head());
                                        h_adapter = new HeadArrayAdapter(this, headName);
                                        h_adapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                spin_head_name.setAdapter(h_adapter);

                                for (int i=0;i<headName.size();i++){
                                    if (headName.get(i).getFamily_head_id().equalsIgnoreCase(m_data.getHead_id())){
                                        spin_head_name.setSelection(i);
                                        head_id=headName.get(i).getFamily_head_id();
                                        Log.d(TAG, "eyeScreenDataInit: headName.get(i).getFamily_head_id() done"+headName.get(i).getFamily_head_id());
                                        getMemberList();
                                    }
                                }
                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;
            case GET_VILLAGE_MEMBER_LIST:
                if (response.isSuccessful()){
                    FamilyMembersModelResponse baseResponse=(FamilyMembersModelResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getFamilyMemberListModels().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getFamilyMemberListModels().size());
                                spinner_member_name.setAdapter(m_adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
//                                try {
                                memberName=baseResponse.getFamilyMemberListModels();
//                                    for(int i=0; i <=baseResponse.getFamilyMemberListModels().size(); i++){
//                                        memberName.add(baseResponse.getFamilyMemberListModels().get(i));
//                                        Log.d("TAG", "onResponse: size "+baseResponse.getFamilyMemberListModels().get(i).getName());
                                m_adapter = new MemberArrayListAdapter(this, memberName);
                                m_adapter.notifyDataSetChanged();
//                                    }
//                                }catch (Exception e){
//                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
//                                }
                                spinner_member_name.setAdapter(m_adapter);

                                for (int i=0;i<memberName.size();i++){
                                    if (memberName.get(i).getHead_id()!=null&&memberName.get(i).getHead_id().equalsIgnoreCase(m_data.getMember_id())){
                                        spinner_member_name.setSelection(i);
                                        member_id=memberName.get(i).getId();
                                        eyeScreenDataInit(m_data);
                                        Log.d(TAG, "eyeScreenDataInit: village done"+memberName);
                                    }else if(memberName.get(i).getId()!=null&&memberName.get(i).getId().equalsIgnoreCase(m_data.getMember_id())){
                                        spinner_member_name.setSelection(i);
                                        member_id=memberName.get(i).getId();
                                        Log.d(TAG, "eyeScreenDataInit: village done"+memberName);
                                        eyeScreenDataInit(m_data);
                                    }
                                }
                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatus());
                        }
                    }
                }
                break;

            case ADD_DATA_REQUEST:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            finish();
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                        }else{
                            ToastUtils.shortToast(baseResponse.getStatusMessage());

                        }
                    }
                }else{
                    ToastUtils.shortToast(response.message());
                }
            case VIEW_DATA_REQUEST:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            finish();
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                        }else{
                            ToastUtils.shortToast(baseResponse.getStatusMessage());

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
        Log.d(TAG, "onFailure: "+t.getMessage());
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
            case R.id.tv_date:
                getDateDialog();
                break;

        }

    }

    private boolean validate() {
        if (tv_date.getText().length()==0){
            tv_date.setError(getResources().getString(R.string.select)+getResources()+" "+getResources().getString(R.string.date));
            ToastUtils.shortToast(getResources().getString(R.string.select)+getResources()+getResources().getString(R.string.date));
            return false;}
        if (village==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_village));
            return false;}
        if (patient_id==null){
            ToastUtils.shortToast(getResources().getString(R.string.select)+" "+getResources().getString(R.string.pateint_name));
            return false;}
        if (visionDefect==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_vision_defect));
            return false;}

        try{
            remark=et_remark.getText().toString();
        }catch (Exception e){

        }


        return true;
    }

    private void submitdata() {
        UserService.addEyeScreening(this,status,village,patient_id,member_id,head_id,etGuestName.getText().toString(),member_name,etGuestMob.getText().toString(),gender,tv_date.getText().toString(),visionDefect,tv_age.getText().toString(),es_id,et_remark.getText().toString(),this,ADD_DATA_REQUEST);
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(EyeScreeningUpdate.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);


                String monthnm=String.valueOf(monthOfYear + 1);
                Log.d("TAG", "onDateSet: "+date);
                Log.d("TAG", "onDateSet: "+monthnm);
//                camp_month= String.valueOf(monthOfYear);
                tv_date.setText(date);
                meeting_date=date;
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
        Log.d(TAG, "runnnnble: ");
        memberLoad();
    };

}
