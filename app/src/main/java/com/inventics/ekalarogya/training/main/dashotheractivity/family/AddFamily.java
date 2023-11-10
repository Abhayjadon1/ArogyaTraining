package com.inventics.ekalarogya.training.main.dashotheractivity.family;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.inventics.ekalarogya.training.R;

import com.inventics.ekalarogya.training.adapters.VillageFamilymemberAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.MartialAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.OccupationAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.inventics.ekalarogya.training.models.FamilyHeadDetailsModelResponse;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.MaritalStatus;
import com.inventics.ekalarogya.training.models.OccupationModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.GetAllListResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ExtraUtils;
import com.inventics.ekalarogya.training.utils.GPSTracker;
import com.inventics.ekalarogya.training.utils.ImageUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;
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

public class AddFamily extends AppCompatActivity implements View.OnClickListener, RetroAPICallback {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.spinner_age)
    EditText spinner_age;
    @BindView(R.id.et_mobile_no)
    EditText et_mobile_no;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_occupation)
    Spinner et_occupation;
    @BindView(R.id.et_adhar)
    EditText et_adhar;

    @BindView(R.id.llisPregnant)
    LinearLayout llisPregnant;

    @BindView(R.id.iv_attach_adhar)
    ImageView iv_attach_adhar;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_dob)
    TextView tv_dob;

    File photoFile = null;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.radio_group_gender)
    RadioGroup radioGroupGender;
    @BindView(R.id.radio_button_male)
    RadioButton radio_button_male;
    @BindView(R.id.radio_button_female)
    RadioButton radio_button_female;
    @BindView(R.id.radio_button_other)
    RadioButton radio_button_other;


    @BindView(R.id.iv_add_member)
    ImageView iv_add_member;
    @BindView(R.id.rv_member)
    RecyclerView rv_member;
    OccupationAdapter o_adapter;
    Bitmap photoHead,photoMember;
    String occ = null,rel=null;

    private File tempFileFromSource = null;
    private Uri tempUriFromSource = null;
    private List<MaritalStatus> maritalStatuses = new ArrayList<>();
    FamilyHeadDetailsModelResponse familyHeadDetailsModelResponse;
    String village_name,photoHeadString,photoMemberString,village_id,head_id,occupation,gender,marital,status="0",isPregnant="0",head_code;
    private static final String TAG = AddFamily.class.getSimpleName();

    @BindView(R.id.memeber_spinner_married)
    Spinner memeber_spinner_married;

    @BindView(R.id.memeber_radio_group_isPregnant)
    RadioGroup radioGroupIsPregnant;
    @BindView(R.id.radio_isPregnant_yes)
    RadioButton radio_isPregnant_yes;
    @BindView(R.id.radio_isPregnant_no)
    RadioButton radio_isPregnant_no;
    double longitude,latitude;
    LocationManager locationManager;
    private static final int UPLOAD_IMAGE=123;
    private static final int REQUEST_LOCATION = 1;
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private static final int PIC_ID_HEAD_FAMILY = 111;
    private static final int ADD_HEAD_FAMILY = 101;
    private static final int VIEW_HEAD_FAMILY = 117;
    private static final int ADD_MEMBER_FAMILY = 131;
    private static final int VIEW_MEMBER_FAMILY = 131;
    private static final int OCCUPATION_LIST = 141;
    boolean isEdit;

    CharSequence tempEt_adhar = "" ;
    MartialAdapter m_adapter;
    VillageFamilymemberAdapter villageFamilymemberAdapter;
    List<FamilyMembersModel> familyMembersModelList;
    private List<OccupationModel> occupationModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_village_family);
        ButterKnife.bind(this);
        getOccupationList();

        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            switch(getIntent().getStringExtra("type")){
                case "add":
                    iv_add_member.setEnabled(false);
                    iv_add_member.setVisibility(View.GONE);
                    village_id=i.getStringExtra("id");
                    village_name=i.getStringExtra("village_name");
                    et_address.setText(village_name);
                    tv_title.setText(R.string.add_village_family);
                    tv_dob.setText(R.string.age);
                    Log.d("TAG", "onCreate: getintent add village_id"+" "+village_id);
                    isEdit=true;
                    status="0";
                    break;
                case "view":
                    status="1";
                    tv_dob.setText(R.string.age);
                    tv_title.setText(R.string.view_village_family);
                    isEdit=false;
                    head_id=i.getStringExtra("id");
                    Log.d("TAG", "onCreate: getintent view head_id"+" "+head_id+village_id);
//                    viewHeadDetails(head_id);
                    break;
                case "edit":
                    isEdit=true;
                    tv_dob.setText(R.string.age);
                    status="1";
                    tv_title.setText(R.string.update_village_family);
                    head_id=i.getStringExtra("id");
                    Log.d("TAG", "onCreate: getintent edit head_id"+" "+head_id);
//                    viewHeadDetails(head_id);
                    break;

            }
        }else{
//            ToastUtils.shortToast("Village ID not found!");
            finish();
        }

        initview();
        initdata();


//        et_adhar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//               tempEt_adhar =  charSequence;
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


    }



    private void initdata() {
       if(head_id!=null){
           viewHeadDetails(head_id);
       }
    }



    private void initview() {
        iv_back.setOnClickListener(this);
        if (!isEdit){
            Log.d(TAG, "onCreate initview: isedit true");
            et_occupation.setEnabled(false);
            et_address.setEnabled(false);
            et_adhar.setEnabled(false);
            et_mobile_no.setEnabled(false);
            et_name.setEnabled( false);
            radioGroupGender.setEnabled(false);
            radio_button_female.setEnabled(false);
            radio_button_male.setEnabled(false);
            radio_button_other.setEnabled(false);
            spinner_age.setEnabled(false);
            iv_attach_adhar.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
            iv_add_member.setVisibility(View.GONE);
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

            et_occupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        iv_attach_adhar.setOnClickListener(this);
         btn_submit.setOnClickListener(this);
        iv_add_member.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                if (validateForHead()){
                    getLatitude();
                }
                break;
            case R.id.iv_add_member:
                Log.d(TAG, "onClick: view"+head_id+"   v_id"+village_id);
                Intent intent = new Intent(AddFamily.this, AddFamilyMember.class);
                intent.putExtra("h_data", familyHeadDetailsModelResponse);
                intent.putExtra("type", "add");
                Log.d(TAG, "onClick: "+head_id+" "+village_id);
                startActivity(intent);

                break;
            case R.id.iv_attach_adhar:
               tempEt_adhar = et_adhar.getText();
                dexterPerm(PIC_ID_HEAD_FAMILY);
//                openCameraToClick(PIC_ID_HEAD_FAMILY);
                break;
            case R.id.spinner_age:
                getDateDialog();
                break;

            case R.id.iv_back:
//                startActivity(new Intent(AddFamily.this,VillageFamily.class));
                finish();
                break;



        }

    }

    private void getLatitude() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();

        } else {
            getLocation();
        }

        if (validateForHead())
            submitHead();

    }

    private void getOccupationList() {

        UserService.getOccupationList(AddFamily.this, ArogyaApplication.getCurrentLocale(this),this,OCCUPATION_LIST);
    }
    private void viewHeadDetails(String head_id) {

        UserService.getFamilyHeadDetails(AddFamily.this,head_id,this,VIEW_HEAD_FAMILY);
    }

    private void submitHead() {
        Log.d("TAG", "submitHead:  :"+et_name.getText().toString()+"name  "+
                gender+"gender "+
                spinner_age.getText()+"age "+
                occupation+"occupation "+
                et_address.getText().toString()+"addss "+
                latitude+"lat "+
                longitude+"long "+
                et_adhar.getText()+"adhar "+
                photoHeadString+" "+
                village_id+"vid ");

        UserService.addFamilyHead(AddFamily.this,status,et_name.getText().toString(),
                gender,spinner_age.getText().toString(),et_mobile_no.getText().toString(),occupation,et_address.getText().toString(),
                String.valueOf(latitude),String.valueOf(longitude),et_adhar.getText().toString(),photoHeadString,village_id,head_id,this,ADD_HEAD_FAMILY);

    }

    private boolean validateForHead() {
        if (et_name.getText().toString().trim().length()<=0){
            ToastUtils.shortToast(getResources().getString(R.string.name));
            return false;}
            if (spinner_age.getText().toString().trim().length()<=0){
                ToastUtils.shortToast(getResources().getString(R.string.age));
                return false;}
            if(et_mobile_no.getText().toString().trim().length()<=0){
                ToastUtils.shortToast(getResources().getString(R.string.mobile_no));
                return false;}
            if(occupation.length()<=0){
                ToastUtils.shortToast(getResources().getString(R.string.occupation_choose));
                return false;}
            if(gender.length()<=0){
                ToastUtils.shortToast(getResources().getString(R.string.gender_choose));
                return false;}
//            if(occupation.length()<=0){
//                ToastUtils.shortToast("Please enter mobile no.");
//                return false;}


//            if (status.equalsIgnoreCase("0")&&et_adhar.getText()==null){
//                ToastUtils.shortToast("Please enter adhaar");
//                return false;
//            }

        if (et_address.getText().toString().trim().length()<=0){
            ToastUtils.shortToast(getResources().getString(R.string.no_address_found));
            return false;}
        if (occupation==null||occupation.equalsIgnoreCase("select")){
            ToastUtils.shortToast(getResources().getString(R.string.occupation_choose));
            return false;}
        ////////////////////////////////////////////////////
//        if(status.equalsIgnoreCase("0")&&photoHeadString==null&&photoHead==null){
//            ToastUtils.shortToast("Please add adhar photo");
//            return false;}
        /////////////////////////////////////////////
        if(village_id==null){
            ToastUtils.shortToast(getResources().getString(R.string.no_data));
            return false;}

        return true;
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
                if(resultCode == Activity.RESULT_OK)  {
                    photoMember = (Bitmap) data.getExtras().get("data");
                    photoMemberString = ImageUtils.convertBitmapIntoBase64(photoMember);
                    uploadImg("/village/family/head/aadhar",photoMemberString);
                    Log.d("TAG", "onActivityResult:photoMemberString " + photoMemberString);
                    et_adhar.setText(tempEt_adhar);
                }else{
                    et_adhar.setText(tempEt_adhar);
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                // click_image_id.setImageBitmap(photo);
                break;
            case PIC_ID_HEAD_FAMILY:
                if (resultCode == RESULT_OK){
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                    photoHeadString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    et_adhar.setText(tempEt_adhar);
//                    addIV.setImageDrawable(getResources().getDrawable(R.drawable.upload));

//                    Log.d("TAG", "onActivityResult:photoHeadString "+photoHead.getWidth()+"x"+photoHead.getHeight()+"  \n" +photoHeadString);
                    uploadImg("/village/family/head/aadhar",photoHeadString);

                    iv_attach_adhar.setImageBitmap(photoHead);
                    // click_image_id.setImageBitmap(photo);
                }else{
                    et_adhar.setText(tempEt_adhar);
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }

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
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                AddFamily.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AddFamily.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = lat;
                longitude = longi;

            } else {
                GPSTracker gpsTracker=new GPSTracker(this);
                latitude=gpsTracker.getLatitude();
                longitude=gpsTracker.getLongitude();
                Log.d("Your Location: " ,"\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);

//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

        switch (requestCode){
            case OCCUPATION_LIST:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

                if (response.isSuccessful()) {
                    GetAllListResponse getAllListResponse = (GetAllListResponse) response.body();
                    if (getAllListResponse != null) {

                        if (getAllListResponse.getStatus().equalsIgnoreCase("success")) {
                            Log.d(TAG, "onResponse: getAllListResponse success" + getAllListResponse.getOccupation_list().size());
                            if(getAllListResponse.getOccupation_list().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size occupation"+getAllListResponse.getOccupation_list().size());
                                et_occupation.setAdapter(o_adapter);
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
                                et_occupation.setAdapter(o_adapter);



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
//                                if (data != null) {
//                                    Log.d(TAG, "onCreate: setupMemberData data!=null");
//                                    setupMemberData(data);
//                                }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                                }
                            }
                        }else{
                            Log.d(TAG, "onResponse: ");
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

                           photoHeadString=baseResponse.getInspection_img();
                           Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getInspection_img()).error(R.drawable.add_image).into(iv_attach_adhar);
//                            plantListSlectedAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            case ADD_HEAD_FAMILY:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

                if (response.isSuccessful()) {
                    BaseResponse occupationResponse = (BaseResponse) response.body();
                    if (occupationResponse != null) {
                        if (occupationResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                           ToastUtils.shortToast(occupationResponse.getStatusMessage());
                           finish();
                        }else{
                            ToastUtils.shortToast(occupationResponse.getStatusMessage());
                        }
                    }
                }else{
                    ToastUtils.shortToast(response.message());
                }
                break;
            case  VIEW_HEAD_FAMILY:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) this).getSupportFragmentManager());

                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null){
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
//                           try{
                               Log.d(TAG, "onResponse: VIEW_HEAD_FAMILY"+"RestUtils.SUCCESS getmodule");
                               familyHeadDetailsModelResponse= baseResponse.getFamilyHeadDetailsModel();
                               Log.d(TAG, "onResponse: VIEW_HEAD_FAMILY"+baseResponse.getFamilyHeadDetailsModel().getFamilyMembersModelList().size());
                               head_id=baseResponse.getFamilyHeadDetailsModel().getId();
                               head_code=baseResponse.getFamilyHeadDetailsModel().getHead_code();
                               et_name.setText(baseResponse.getFamilyHeadDetailsModel().getFamily_head());
                               et_mobile_no.setText(baseResponse.getFamilyHeadDetailsModel().getPhone_num());
                               et_adhar.setText(baseResponse.getFamilyHeadDetailsModel().getAadhar());
                               et_address.setText(baseResponse.getFamilyHeadDetailsModel().getAddress());
                               village_id=baseResponse.getFamilyHeadDetailsModel().getVillage_id();
                               if(baseResponse.getFamilyHeadDetailsModel().getDob()!=null){
                                   spinner_age.setText(baseResponse.getFamilyHeadDetailsModel().getDob());
                               }else {
                                   spinner_age.setText(baseResponse.getFamilyHeadDetailsModel().getAge());
                               }
//                               photoHeadString=baseResponse.getFamilyHeadDetailsModel().getAadhar_photo();
//                               photoHead=baseResponse.getFamilyHeadDetailsModel().getAadhar_photo();

                               village_id=baseResponse.getFamilyHeadDetailsModel().getVillage_id();
                               Log.d(TAG, "onResponse: village_id: "+village_id);
                               Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getFamilyHeadDetailsModel().getAadhar_photo()).error(R.drawable.add_image).into(iv_attach_adhar);
                               gender=baseResponse.getFamilyHeadDetailsModel().getGender();
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
                               occupation=baseResponse.getFamilyHeadDetailsModel().getOccupation();
                               Log.d(TAG, "onResponse: listttt data"+occupation);

                               for (int i=0;i<occupationModelList.size();i++){
                                   if (baseResponse.getFamilyHeadDetailsModel().getOccupation().equalsIgnoreCase(occupationModelList.get(i).getId())){
                                       et_occupation.setSelection(i);
                                       Log.d(TAG, "onResponse: listttt"+occupation+"  "+occupationModelList.get(i).toString());

                                   }else {
                                       Log.d(TAG, "onResponse listttt : nothiii");
                                   }
                               }
//                               photoHeadString= String.valueOf(ImageUtils.convertImagePathToBitmap(ExtraUtils.baseImg+baseResponse.getFamilyHeadDetailsModel().getAadhar_photo(),480,240));
//

//                            //tv_title.setText(current_list);
                               if(baseResponse.getFamilyHeadDetailsModel().getFamilyMembersModelList().size()>0) {
//                                   familyMembersModelList.addAll(baseResponse.getFamilyHeadDetailsModel().getFamilyMembersModelList());
                                   rv_member.setVisibility(View.VISIBLE);
                                   // tvEmpty.setVisibility(View.GONE);
                                   rv_member.setLayoutManager(new LinearLayoutManager(this));
                                   rv_member.setHasFixedSize(true);
                                   villageFamilymemberAdapter = new VillageFamilymemberAdapter(AddFamily.this,getIntent().getStringExtra("type"),baseResponse.getFamilyHeadDetailsModel().getId(),baseResponse.getFamilyHeadDetailsModel().getFamilyMembersModelList());
                                   rv_member.setAdapter(villageFamilymemberAdapter);
                                   Log.d(TAG, "onResponse: setadpetr"+baseResponse.getFamilyHeadDetailsModel().getFamilyMembersModelList().size());

                               }

//                           }catch (Exception e){
//                               Log.d(TAG, "onResponse: "+e.getMessage());
//                           }

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
        DatePickerDialog datePicker = new DatePickerDialog(AddFamily.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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

//    @Override
//    public void onItemClick(FamilyMembersModel item) {
//                Log.d(TAG, "onClick: view"+head_id+"\n");
//                Intent intent = new Intent(AddFamily.this, AddFamilyMember.class);
//                intent.putExtra("h_id",head_id);
//                intent.putExtra("m_data", (Parcelable) familyMembersModelList);
//                intent.putExtra("type", "view");
//                startActivity(intent);
//
//            }


    @Override
    protected void onResume() {
        super.onResume();
        initdata();
    }
    @Override
    protected void onPause() {
        super.onPause();

//        initdata();
    }
    private void showHide() {
        Log.d(TAG, "showHide: "+marital+" "+gender);
        try{
            if (gender.equalsIgnoreCase("Male")){
                llisPregnant.setVisibility(View.GONE);
            }else  if (gender.equalsIgnoreCase("Female")&&marital.equalsIgnoreCase("1")||marital.equalsIgnoreCase("3")||marital.equalsIgnoreCase("4")){
                llisPregnant.setVisibility(View.VISIBLE);
            }else{
                llisPregnant.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Log.d(TAG, "showHide: "+e.getMessage());
        }
    }

    public void dexterPerm(int PERM){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION
//                        ,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Log.d(TAG, "onPermissionsChecked: "+PERM);
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
        Log.d(TAG, "uploadImg: "+path+" image: "+base64);
        UserService.uploadImageApi(this,path,base64,this,UPLOAD_IMAGE);
    }
}