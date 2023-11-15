package com.ispl.ekalarogya.training.main.dashotheractivity.family;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.spinner.MartialAdapter;
import com.ispl.ekalarogya.training.adapters.spinner.OccupationAdapter;
import com.ispl.ekalarogya.training.adapters.spinner.RelationArrayAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.ispl.ekalarogya.training.models.FamilyHeadDetailsModelResponse;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;
import com.ispl.ekalarogya.training.models.MaritalStatus;
import com.ispl.ekalarogya.training.models.OccupationModel;
import com.ispl.ekalarogya.training.models.RelationModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.GetAllListResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ExtraUtils;
import com.ispl.ekalarogya.training.utils.ImageUtils;
import com.ispl.ekalarogya.training.utils.ToastUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class  AddFamilyMember extends AppCompatActivity implements View.OnClickListener, RetroAPICallback {
    @BindView(R.id.memeber_et_name)
    EditText et_name;
    @BindView(R.id.memeber_spinner_age)
    EditText spinner_age;
//    @BindView(R.id.et_mobile_no)
//    EditText et_mobile_no;

    File photoFile=null;
    @BindView(R.id.llisPregnant)
    LinearLayout llisPregnant;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_dob)
    TextView tv_dob;

    @BindView(R.id.member_occupation_spinner)
    Spinner member_occupation_spinner;
    @BindView(R.id.memeber_spinner_relation)
    Spinner memeber_spinner_relation;
    @BindView(R.id.memeber_et_adhar)
    EditText et_adhar;
    @BindView(R.id.member_attach_adhar)
    ImageView iv_attach_adhar;
    @BindView(R.id.memeber_btn_submit)
    Button btn_submit;
    @BindView(R.id.memeber_radio_group_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radio_button_male)
    RadioButton radio_button_male;
    @BindView(R.id.radio_button_female)
    RadioButton radio_button_female;
    @BindView(R.id.radio_button_other)
    RadioButton radio_button_other;


    @BindView(R.id.memeber_spinner_married)
    Spinner memeber_spinner_married;

    @BindView(R.id.memeber_radio_group_isPregnant)
    RadioGroup radioGroupIsPregnant;
    @BindView(R.id.radio_isPregnant_yes)
    RadioButton radio_isPregnant_yes;
    @BindView(R.id.radio_isPregnant_no)
    RadioButton radio_isPregnant_no;

    private List<OccupationModel> occupationModelList = new ArrayList<>();
    private List<RelationModel> relationModels = new ArrayList<>();
    private List<MaritalStatus> maritalStatuses = new ArrayList<>();
    OccupationAdapter o_adapter;
    RelationArrayAdapter r_adapter;
    MartialAdapter m_adapter;


    Bitmap photoMember;
    String photoHeadString,photoMemberString,longitude,latitude,village_id,head_id,head_code,member_id,occupation,gender,marital,isPregnant="0",relation,status="0";
    private static final String TAG = AddFamilyMember.class.getSimpleName();

    LocationManager locationManager;
    private static final int UPLOAD_IMAGE=123;
    private static final int REQUEST_LOCATION = 1;
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private static final int ADD_MEMBER_FAMILY = 131;
    private static final int VIEW_MEMBER_FAMILY = 131;
    private static final int OCCUPATION_LIST = 141;
    boolean isEdit=false;
    FamilyMembersModel data;
    FamilyHeadDetailsModelResponse h_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_family_member_dialog);
        ButterKnife.bind(this);
        initdata();
        ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            switch(getIntent().getStringExtra("type")){
                case "add":
                    h_data=i.getParcelableExtra("h_data");
                    head_id=h_data.getId();
                    head_code=h_data.getHead_code();
                    village_id=h_data.getVillage_id();
                    tv_title.setText(R.string.add_member);
                    status="0";
                    tv_dob.setText(R.string.age);

                    Log.d("TAG", "onCreate: getintent add village_id"+" "+village_id);
                    isEdit=true;
                    break;
                case "view":
                    tv_dob.setText(R.string.age);

                    head_id=i.getStringExtra("h_id");
                    data=getIntent().getParcelableExtra("m_data");
                    member_id=data.getId();
                    tv_title.setText(getResources().getString(R.string.view_member));
                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+"  member_id "+member_id);
                    isEdit=false;
//                    viewMemberDetails(head_id,member_id);
//                    setupMemberData(data);
                    break;
                case "edit":
                    tv_dob.setText(R.string.age);
                    isEdit=true;
                    head_id=i.getStringExtra("h_id");
                    data=getIntent().getParcelableExtra("m_data");
                    member_id=data.getId();
                    tv_title.setText(R.string.update_village_family);
                    status="1";
                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+"  member_id "+member_id);
//                    viewMemberDetails(head_id,member_id);
//                    setupMemberData(data);

                    break;

            }
        }else{
            ToastUtils.shortToast(getResources().getString(R.string.id_not_found));
            finish();
        }


        initview();


    }

    private void setupMemberData(FamilyMembersModel data) {

        try {
            Log.d(TAG, "setupMemberData: "+data.toString());
            occupation=data.getOccupation();
            relation=data.getRelation();
            marital=data.getMarital_status();
            et_adhar.setText(data.getAadhar_num());
            et_name.setText( data.getName());
            photoMemberString=data.getAadhar_photo();
            gender=data.getGender();
            village_id=data.getVillage_id();
            isPregnant=data.getIs_pregnant();
            Log.d(TAG, "setupMemberData: \n"+relation+" "+relationModels.size()+" relation \n"+ marital+" "+maritalStatuses.size()+" marital\n"+occupation+" "+occupationModelList.size()+"  occupation");

            if (gender.equalsIgnoreCase("male")){
                radio_button_male.setChecked(true);
            }else if (gender.equalsIgnoreCase("Female")){
                radio_button_female.setChecked(true);
            }else if (gender.equalsIgnoreCase("Other")){
                radio_button_other.setChecked(true);
            }
            if (isEdit){
                Log.d(TAG, "setupMemberData: isEdit");
                //spinner_age.setText( data.getDob());
                spinner_age.setText( data.getAge());
            }else {
                spinner_age.setText( data.getAge());
                tv_dob.setText(getResources().getString(R.string.age));
            }
//            spinner_age.setText(data.getAge());

            for (int i=0;i<occupationModelList.size();i++){
                Log.d(TAG, "setupMemberData: li : "+occupation.equalsIgnoreCase(occupationModelList.get(i).getOccupation().toString()));
                if (occupation.equalsIgnoreCase(occupationModelList.get(i).getOccupation().toString())){
                    member_occupation_spinner.setSelection(i);
                    Log.d(TAG, "onResponse: listttt"+occupation+"  "+occupationModelList.get(i).toString());

                }else {
                    Log.d(TAG, "onResponse listttt : nothiii");
                }
            }
            for (int i=0;i<relationModels.size();i++){
                if (relation.equalsIgnoreCase(relationModels.get(i).getId().toString())){
                    memeber_spinner_relation.setSelection(i);
                    Log.d(TAG, "onResponse: listttt"+relation+"  "+relationModels.get(i).toString());

                }else {
                    Log.d(TAG, "onResponse listttt : nothiii");
                }
            }
            for (int i=0;i<maritalStatuses.size();i++){
                if (marital.equalsIgnoreCase(maritalStatuses.get(i).getMarital_status().toString())){
                    memeber_spinner_married.setSelection(i);
                    Log.d(TAG, "onResponse: listttt"+marital+"  "+maritalStatuses.get(i).toString());

                }else {
                    Log.d(TAG, "onResponse listttt : nothiii");
                }
            }
//            member_occupation_spinner.setSelection(Integer.parseInt(occupation));

//            memeber_spinner_relation.setSelection(Integer.parseInt(relation));
            Picasso.with(this).load(ExtraUtils.baseImg+photoMemberString).error(R.drawable.add_image).into(iv_attach_adhar);
           // iv_attach_adhar.setImageBitmap(data.getAadhar_photo());
        }catch (Exception e){
            Log.d(TAG, "setupMemberData: "+e.getMessage());
        }
//        showHide();

    }


    private void initdata() {
        getOccupationList();
    }



    private void initview() {
        if (!isEdit){
            Log.d(TAG, "onCreate initview: isedit true");
            member_occupation_spinner.setEnabled(false);
            memeber_spinner_relation.setEnabled(false);
            memeber_spinner_married.setEnabled(false);
            et_adhar.setEnabled(false);
            et_name.setEnabled( false);
            radioGroupGender.setEnabled(false);
            radio_button_female.setEnabled(false);
            radio_button_male.setEnabled(false);
            radio_button_other.setEnabled(false);
            spinner_age.setEnabled(false);
            iv_attach_adhar.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radio_button_male.isChecked()){
                    gender="Male";
//                    showHide();
                }else if(radio_button_female.isChecked()){
                    gender="Female";
//                    showHide();
                }else if(radio_button_other.isChecked()){
                    gender="Other";
//                    showHide();
                }
            }

        });

        radioGroupIsPregnant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radio_isPregnant_no.isChecked()){
                    isPregnant="0";
                }else if(radio_isPregnant_yes.isChecked()){
                    isPregnant="1";
                }else {
                    isPregnant=null;
                }
            }
        });
        //Check listenr done

        spinner_age.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        member_occupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                OccupationModel clickedItem = (OccupationModel)
                        parent.getItemAtPosition(position);

                String name = clickedItem.getOccupation();
                occupation= clickedItem.getId();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        memeber_spinner_relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RelationModel clickedItem = (RelationModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getRelation();
                relation= clickedItem.getId();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        memeber_spinner_married.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MaritalStatus clickedItem = (MaritalStatus)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getMarital_status();
                marital= clickedItem.getId();
//                showHide();

//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        if (data!=null){
           et_name.setText(data.getName());
           et_adhar.setText(data.getAadhar_num());
           spinner_age.setText(data.getAge());
           photoMemberString=data.getAadhar_photo();

           gender=data.getGender();
            if (gender.equalsIgnoreCase("Male")){
                radio_button_male.setChecked(true);
                radio_button_female.setChecked(false);
                radio_button_other.setChecked(false);
            }else if(gender.equalsIgnoreCase("Female")){
                radio_button_female.setChecked(true);
                radio_button_male.setChecked(false);
                radio_button_other.setChecked(false);
            }else if(gender.equalsIgnoreCase("other")){
                radio_button_other.setChecked(true);
                radio_button_female.setChecked(false);
                radio_button_male.setChecked(false);
            }
           occupation=data.getOccupation();
       }


        iv_attach_adhar.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void showHide() {
        Log.d(TAG, "showHide: "+marital+" "+gender);
        try{
            if (gender.equalsIgnoreCase("Male")){
                llisPregnant.setVisibility(View.GONE);
            }else  if (gender.equalsIgnoreCase("Female")&&marital.equalsIgnoreCase("1")||marital.equalsIgnoreCase("3")||marital.equalsIgnoreCase("4")){
                llisPregnant.setVisibility(View.VISIBLE);
                if(isPregnant.equalsIgnoreCase("yes")) {
                    radio_isPregnant_yes.setChecked(true);
                }else {
                    radio_isPregnant_yes.setChecked(false);
                }
            }else{
                llisPregnant.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Log.d(TAG, "showHide: "+e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.memeber_btn_submit:
//                if (validateForHead()){
                if (validate()){
                    submitmember();
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.plz_complete_form));
                }
//                }
                break;
            case R.id.iv_add_member:

                break;
            case R.id.member_attach_adhar:
                 dexterPerm(PIC_ID_MEMBER_DIALOG);
                break;
            case R.id.memeber_spinner_age:
                getDateDialog();
                break;
            case R.id.iv_back:
                startActivity(new Intent(AddFamilyMember.this, AddFamily.class));
                finish();
                break;

        }

    }

    private boolean validate() {
        if(et_name.getText().toString().trim().length()<=0){
            et_name.setError("Enter name");
            return false;
        }
//        if(et_adhar.getText().toString()==null){
//            et_adhar.setError("Enter adhaar no");
//            return false;
//        }
        if(spinner_age.getText().toString().trim().length()<=0){
//            spinner_age.setError("Enter age");
            return false;
        }
        if(gender==null){
            ToastUtils.shortToast(getResources().getString(R.string.gender_choose));
            return false;
        }
        if(marital==null||marital.equalsIgnoreCase("select")){
            ToastUtils.shortToast(getResources().getString(R.string.marital_status_choose));
            return false;
        }

        if(relation==null){
            ToastUtils.shortToast(getResources().getString(R.string.relation_choose));
            return false;
        }
        if(occupation==null){
            ToastUtils.shortToast(getResources().getString(R.string.occupation_choose));
            return false;
        }
//        if(photoMemberString==null){
//            ToastUtils.shortToast("Add adhaar");
//            return false;
//        }

        return true;
    }

    private void getLatitude() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//
//        } else {
//            getLocation();
//        }


        submitmember();

    }

    private void getOccupationList() {
        UserService.getOccupationList(AddFamilyMember.this, ArogyaApplication.getCurrentLocale(this),this,OCCUPATION_LIST);
    }

    private void submitmember() {
        Log.d("TAG", "submitMember: "+
                head_id+" "+
                head_code+" "+
                marital+" "+
                et_name.getText().toString()+" "+
                gender+" "+
                spinner_age.getText()+" "+
                occupation+" "+
                relation+" "+
                et_adhar.getText()+" "+
                isPregnant+" "+
                village_id+" "+
                member_id+" "+
//                et_address.getText().toString()+" "+
                latitude+" "+
                longitude+" "+
                photoHeadString+" "+"");

        UserService.addFamilyMember(AddFamilyMember.this,head_id,status,head_code,et_name.getText().toString(),marital,gender,spinner_age.getText().toString(),occupation,relation,et_adhar.getText().toString(), String.valueOf(radio_isPregnant_yes.isChecked()?1:0),village_id,member_id,photoMemberString,this,ADD_MEMBER_FAMILY);

    }


    private void openCameraToClick(int pic_id) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (camera_intent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        try {

            photoFile = createImageFile();
//                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
//                Log.i("Tag",photoFile.getAbsolutePath());

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.inventics.ekalarogya.training.file_provider",
                        photoFile);
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                camera_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                camera_intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(camera_intent, pic_id);
            }
        } catch (Exception ex) {
            // Error occurred while creating the File
            ToastUtils.shortToast(ex.getMessage());
        }


//        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoHeadString = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_ID_MEMBER_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap= ImageUtils.getResizedBitmap(myBitmap,500);
                    photoMemberString=ImageUtils.convertBitmapIntoBase64(myBitmap);
//                    addIV.setImageDrawable(getResources().getDrawable(R.drawable.upload));

//                    Log.d("TAG", "onActivityResult:photoHeadString "+photoHead.getWidth()+"x"+photoHead.getHeight()+"  \n" +photoHeadString);
//                    uploadImg("/village/family/head/aadhar",photoHeadString);
//                    photoMember = (Bitmap) data.getExtras().get("data");
//                    photoMemberString= ImageUtils.convertBitmapIntoBase64(photoMember);
//                    iv_attach_adhar.setImageBitmap(ImageUtils.convertBase64ToBitmap(photoMemberString));
//                    Log.d("TAG", "onActivityResult:photoMemberString "+photoMemberString);
                    uploadImg("/village/family/member/aadhar",photoMemberString);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.add_image));
                }

                //click_image_id.setImageBitmap(photo);
                break;
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                AddFamilyMember.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                AddFamilyMember.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        } else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(longi);
//                Log.d("Your Location: " ,"\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case OCCUPATION_LIST:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

                if (response.isSuccessful()) {
                    GetAllListResponse getAllListResponse = (GetAllListResponse) response.body();
                    if (getAllListResponse != null) {
                        if (getAllListResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: Occupation" + getAllListResponse.getOccupation_list().size());
                            String[] OccupationCategory = new String[getAllListResponse.getOccupation_list().size()];
                            if(getAllListResponse.getOccupation_list().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+getAllListResponse.getOccupation_list().size());
                                member_occupation_spinner.setAdapter(o_adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=getAllListResponse.getOccupation_list().size(); i++){
                                        occupationModelList.add(getAllListResponse.getOccupation_list().get(i));
                                        Log.d("TAG", "onResponse: size "+getAllListResponse.getOccupation_list().get(i).getOccupation());
                                        o_adapter = new OccupationAdapter(this, occupationModelList);
                                        o_adapter.notifyDataSetChanged();

                                    }
                                    Log.d(TAG, "onResponse: "+occupationModelList.toString());
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                member_occupation_spinner.setAdapter(o_adapter);

                            }

                            Log.d(TAG, "onResponse: Relation" + getAllListResponse.getRelationModelList().size());
                            if(getAllListResponse.getRelationModelList().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+getAllListResponse.getRelationModelList().size());
                                memeber_spinner_relation.setAdapter(r_adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=getAllListResponse.getRelationModelList().size(); i++){
                                        relationModels.add(getAllListResponse.getRelationModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+getAllListResponse.getRelationModelList().get(i).getRelation());
                                        r_adapter = new RelationArrayAdapter(this, relationModels);
                                        r_adapter.notifyDataSetChanged();

                                    }
                                    Log.d(TAG, "onResponse: "+occupationModelList.toString());
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                memeber_spinner_relation.setAdapter(r_adapter);

                            }

                            Log.d(TAG, "onResponse: Marital" + getAllListResponse.getMaritalStatusList().size());
                            if(getAllListResponse.getMaritalStatusList().size()>0) {
                                // we pass our item list and context to our Adapter.

                                Log.d("TAG", "onResponse: size " + getAllListResponse.getMaritalStatusList().size());
                                memeber_spinner_married.setAdapter(m_adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    maritalStatuses.clear();
                                    maritalStatuses.add(new MaritalStatus("Select",getResources().getString(R.string.select),"Select"));
                                    for (int i = 0; i <= getAllListResponse.getMaritalStatusList().size(); i++) {
                                        maritalStatuses.add(getAllListResponse.getMaritalStatusList().get(i));
                                        Log.d("TAG", "onResponse: size " + getAllListResponse.getMaritalStatusList().get(i).getMarital_status());
                                        m_adapter = new MartialAdapter(this, maritalStatuses);
                                        m_adapter.notifyDataSetChanged();

                                    }
                                    Log.d(TAG, "onResponse: " + occupationModelList.toString());
                                } catch (Exception e) {
                                    Log.d("TAG", "onResponse: exception" + e.getMessage());
                                }
                                memeber_spinner_married.setAdapter(m_adapter);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                if (data != null) {
                                    Log.d(TAG, "onCreate: setupMemberData data!=null");
                                    setupMemberData(data);
                                }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                                }
                            }
                            }else{
                            Log.d(TAG, "onResponse: ");
                        }
                    }
                }
                break;
            case  ADD_MEMBER_FAMILY:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

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
            case UPLOAD_IMAGE:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            imgList.add(new ImageList(baseResponse.getInspection_img()));

                            photoMemberString=baseResponse.getInspection_img();
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getInspection_img()).error(R.drawable.add_image).into(iv_attach_adhar);

//                            plantListSlectedAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        Log.d(TAG, "onFailure: "+t.getMessage());
        ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

        ToastUtils.shortToast(t.getMessage());
    }

    @Override
    public void onNoNetwork(int requestCode) {
        ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AddFamilyMember.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                spinner_age.setText(date);
                Log.d(TAG, "onDateSet: "+date);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }
    public void dexterPerm(int PERM){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            openCameraToClick(PERM);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }
    private void uploadImg(String path,String base64) {
        UserService.uploadImageApi(this,path,base64,this,UPLOAD_IMAGE);
    }

}