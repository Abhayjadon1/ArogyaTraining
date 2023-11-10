package com.inventics.ekalarogya.training.main.dashotheractivity.eye.eyevan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.EyeVanModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ExtraUtils;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class EyeVanAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener{
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
     @BindView(R.id.tvName)
    TextView tvName;
     @BindView(R.id.tvAge)
    TextView tvAge;
     @BindView(R.id.tvGender)
    TextView tvGender;
     @BindView(R.id.tv_date)
    TextView tv_date;

     File photoFile=null;

     @BindView(R.id.tvPower)
    TextView tvPower;
     @BindView(R.id.tv_right)
     EditText tv_right;
     @BindView(R.id.tv_left)
    EditText tv_left;


    @BindView(R.id.et_right_d_sph)
    EditText et_right_d_sph;
    @BindView(R.id.et_right_d_cyl)
    EditText et_right_d_cyl;
    @BindView(R.id.et_right_d_axis)
    EditText et_right_d_axis;
    @BindView(R.id.et_left_d_sph)
    EditText et_left_d_sph;
    @BindView(R.id.et_left_d_cyl)
    EditText et_left_d_cyl;
    @BindView(R.id.et_left_d_axis)
    EditText et_left_d_axis;


    @BindView(R.id.et_right_r_sph)
    EditText et_right_r_sph;
    @BindView(R.id.et_right_r_cyl)
    EditText et_right_r_cyl;
     @BindView(R.id.et_right_r_axis)
    EditText et_right_r_axis;
    @BindView(R.id.et_left_r_sph)
    EditText et_left_r_sph;
     @BindView(R.id.et_left_r_cyl)
    EditText et_left_r_cyl;
    @BindView(R.id.et_left_r_axis)
    EditText et_left_r_axis;


    @BindView(R.id.radio_eye_infection)
    RadioButton radio_eye_infection;
    @BindView(R.id.radio_reflection_error)
    RadioButton radio_reflection_error;
    @BindView(R.id.radio_cataract)
    RadioButton radio_cataract;




    @BindView(R.id.ll_infection_error)
    CardView ll_infection_error;
    @BindView(R.id.ll_reflection_error)
    CardView ll_reflection_error;
    @BindView(R.id.ll_cataract_error)
    CardView ll_cataract_error;

    @BindView(R.id.cb_yes_reflection)
    CheckBox cb_yes_reflection;
    @BindView(R.id.cb_no_reflection)
    CheckBox cb_no_reflection;

    @BindView(R.id.cb_yes_infection)
    CheckBox cb_yes_infection;
    @BindView(R.id.cb_no_infection)
    CheckBox cb_no_infection;



    @BindView(R.id.et_fee_refl)
    EditText et_fee_refl;
    @BindView(R.id.et_fee_infec)
    EditText et_fee_infec;

    @BindView(R.id.iv_reflection)
    ImageView iv_reflection;


    @BindView(R.id.spinner_hospitals)
    Spinner spinner_hospitals;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    private static final int PIC_ID_HEAD_FAMILY = 161;

    private static final int ADD_DATA=111;
    private static final int VIEW_DATA=131;
    private static final int CAMERA_INTENT=151;
    private static final int UPLOAD_IMAGE=1123;
    private static final String TAG=EyeVanAdd.class.getSimpleName();
    Bitmap photoHead,photoMember;

    String current_list, type,status="0",eap_id,test_date,referred_hospital,glasses_fee="",glasses_issue="",medicine_issue="",problem_code,r_dist_sph="",r_dist_cyl="",r_dist_axis="",l_dist_sph="",l_dist_cyl="",l_dist_axis="",r_reading_sph="",r_reading_cyl="",r_reading_axis="",l_reading_sph="",l_reading_cyl="",l_reading_axis="",photo="",right_power="0",left_power="0",l_lens="0",medicine_fee=null;

    EyeVanModel e_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_van_add);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            ll_cataract_error.setVisibility(View.GONE);
            ll_reflection_error.setVisibility(View.GONE);
            ll_infection_error.setVisibility(View.GONE);
            switch (type) {
                case "view":
                    e_data=i.getParcelableExtra("m_data");
                    Log.d(TAG, "onCreate: "+e_data.toString());
                    setupData(e_data);
                    break;
                case "edit":
                    status="1";
                    e_data=i.getParcelableExtra("m_data");
                    Log.d(TAG, "onCreate: "+e_data.toString());
                    setupData(e_data);
                    break;
            }
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    submitData();
                }
            }
        });
        radio_cataract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem_code="EP03";
                Log.d(TAG, "onCheckedChanged: go to  updateUI(); "+problem_code);
                updateUI();
            }
        });
        radio_reflection_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem_code="EP02";
                Log.d(TAG, "onCheckedChanged: go to  updateUI(); "+problem_code);
                updateUI();
            }
        });
        radio_eye_infection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problem_code="EP01";
                Log.d(TAG, "onCheckedChanged: go to  updateUI(); "+problem_code);
                updateUI();
            }
        });

        cb_yes_infection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine_issue="1";
                cb_no_infection.setChecked(false);
            }
        });
        cb_no_infection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicine_issue="0";
                cb_yes_infection.setChecked(false);
            }
        });
        cb_yes_reflection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glasses_issue="1";
                cb_no_reflection.setChecked(false);
            }
        });
        cb_no_reflection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glasses_issue="0";
                cb_yes_reflection.setChecked(false);
            }
        });
        iv_reflection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glasses_issue="1";
                cb_yes_reflection.setChecked(true);
                 dexterPerm(PIC_ID_HEAD_FAMILY);

            }
        });

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
        photo = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_ID_HEAD_FAMILY:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);
                    photo=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    Log.d("TAG", "onActivityResult:photoHeadString " + photo);
                    iv_reflection.setImageBitmap(photoHead);
                    uploadImg("/eyevan/eye_van/ev",photo);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                // click_image_id.setImageBitmap(photo);
                break;
        }
    }

    private boolean validate() {
        if (tv_date.getText().toString()==null){
            tv_date.setError("Choose Date");
            return false;
        }

        if (medicine_issue!=null&&medicine_issue.equalsIgnoreCase("yes")){
            medicine_issue="1";
        }
        right_power=tv_right.getText().toString();
        left_power=tv_left.getText().toString();
        l_lens=tv_left.getText().toString();

        r_dist_sph= et_right_d_sph.getText().toString();
        r_dist_cyl= et_right_d_cyl.getText().toString();
        r_dist_axis= et_right_d_axis.getText().toString();

        l_dist_sph= et_left_d_sph.getText().toString();
        l_dist_cyl= et_left_d_sph.getText().toString();
        l_dist_axis= et_left_d_sph.getText().toString();


        r_reading_sph= et_right_r_sph.getText().toString();
        r_reading_cyl= et_right_r_cyl.getText().toString();
        r_reading_axis= et_right_r_axis.getText().toString();

        l_reading_sph= et_left_r_sph.getText().toString();
        l_reading_cyl= et_left_r_cyl.getText().toString();
        l_reading_axis= et_left_r_axis.getText().toString();

        glasses_fee=et_fee_refl.getText().toString();
        test_date=tv_date.getText().toString();
        Log.d(TAG, "validate: "+glasses_fee);

        return true;
    }

    private void submitData() {
        UserService.addEyeVanDetails(this,status,eap_id,test_date,referred_hospital,glasses_fee,glasses_issue,medicine_issue,medicine_fee,problem_code,r_dist_sph,r_dist_cyl,r_dist_axis,l_dist_sph,l_dist_cyl,l_dist_axis,r_reading_sph,r_reading_cyl,r_reading_axis,l_reading_sph,l_reading_cyl,l_reading_axis,right_power,left_power,photo,this,ADD_DATA);
    }

    private void setupData(EyeVanModel e_data) {
        if(e_data.getMember_name()!=null){
            tvName.setText(e_data.getMember_name());
        }else{
            tvName.setText(e_data.getGuest_patient_name());

        }

        tvAge.setText(e_data.getAge());
        tvGender.setText(e_data.getGender());


        eap_id= e_data.getPatient_code();
        test_date= e_data.getTest_date();
        referred_hospital= e_data.getReferred_hospital();

        glasses_fee= e_data.getGlasses_fee();
        glasses_issue= e_data.getGlasses_issue();
        medicine_issue= e_data.getMedicine_issue();
        medicine_fee= e_data.getMedicine_fee();
        problem_code= e_data.getProblem();
        right_power=e_data.getRight_power();
        left_power=e_data.getLeft_power();

        tv_right.setText(right_power);
        tv_left.setText(left_power);
        l_lens=tv_left.getText().toString();

        r_dist_sph= e_data.getR_dist_sph();
        r_dist_cyl= e_data.getR_dist_cyl();
        r_dist_axis= e_data.getR_dist_axis();

        l_dist_sph= e_data.getL_dist_sph();
        l_dist_cyl= e_data.getL_dist_cyl();
        l_dist_axis= e_data.getL_dist_axis();


        r_reading_sph= e_data.getL_reading_sph();
        r_reading_cyl= e_data.getR_reading_cyl();
        r_reading_axis= e_data.getR_reading_axis();

        l_reading_sph= e_data.getL_reading_sph();
        l_reading_cyl= e_data.getL_reading_cyl();
        l_reading_axis= e_data.getL_reading_axis();

        photo=e_data.getPhoto();
        Picasso.with(this).load(ExtraUtils.baseImg+photo).into(iv_reflection);


//        et_fee_refl.setText(glasses_fee);
//        et_fee_infec.setText(glasses_fee);
        tv_date.setText(test_date);

        et_right_d_sph.setText(r_dist_sph);
        et_right_d_cyl.setText(r_dist_cyl);
        et_right_d_axis.setText(r_dist_axis);
        //-------------------------------
        et_left_d_sph.setText(l_dist_sph);
        et_left_d_cyl.setText(l_dist_cyl);
        et_left_d_axis.setText(l_dist_axis);


        //+++++++++++++++++++++++++++++++++++++++


        et_right_r_sph.setText(r_reading_sph);
        et_right_r_cyl.setText(r_reading_cyl);
        et_right_r_axis.setText(r_reading_axis);
        //-------------------------------
        et_left_r_sph.setText(l_reading_sph);
        et_left_r_cyl.setText(l_reading_cyl);
        et_left_r_axis.setText(l_reading_axis);



        if (problem_code!=null){
          updateUI();
        }
        if (photo!=null){
            Picasso.with(this).load(ExtraUtils.baseImg+photo).error(R.drawable.add_image).into(iv_reflection);
        }

    }

    private void updateUI() {
        Log.d(TAG, "updateUI: "+problem_code);
        if (problem_code.equalsIgnoreCase("EP01")){
            radio_eye_infection.setChecked(true);
            radio_reflection_error.setChecked(false);
            radio_cataract.setChecked(false);
            et_fee_infec.setText(glasses_fee);

            ll_cataract_error.setVisibility(View.GONE);
            ll_infection_error.setVisibility(View.VISIBLE);
            ll_reflection_error.setVisibility(View.GONE);

        }else if (problem_code.equalsIgnoreCase("EP02")){
            radio_eye_infection.setChecked(false);
            radio_reflection_error.setChecked(true);
            radio_cataract.setChecked(false);
            et_fee_refl.setText(glasses_fee);

            ll_cataract_error.setVisibility(View.GONE);
            ll_infection_error.setVisibility(View.GONE);
            ll_reflection_error.setVisibility(View.VISIBLE);

        }else if (problem_code.equalsIgnoreCase("EP03")){
            radio_eye_infection.setChecked(false);
            radio_reflection_error.setChecked(false);
            radio_cataract.setChecked(true);

            ll_cataract_error.setVisibility(View.VISIBLE);
            ll_infection_error.setVisibility(View.GONE);
            ll_reflection_error.setVisibility(View.GONE);
        }
        Log.d(TAG, "updateUI: out "+problem_code);
        if (e_data.getMedicine_issue().equalsIgnoreCase("yes")){
            cb_yes_infection.setChecked(true);
            cb_no_infection.setChecked(false);
            et_fee_refl.setText(medicine_fee);
        }else{
            cb_no_infection.setChecked(true);
            cb_yes_infection.setChecked(false);
        }
        if (e_data.getGlasses_issue().equalsIgnoreCase("yes")){
            cb_yes_reflection.setChecked(true);
            cb_no_reflection.setChecked(false);
            et_fee_refl.setText(glasses_fee);
        }else{
            cb_no_reflection.setChecked(true);
            cb_yes_reflection.setChecked(false);
        }
        if (glasses_fee!=null){
            et_fee_refl.setText(glasses_fee);
        }
        if (medicine_fee!=null){
            et_fee_refl.setText(medicine_fee);
        }

        if (photo!=null){
            Picasso.with(this).load(ExtraUtils.baseImg+photo).into(iv_reflection);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case VIEW_DATA:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: " + baseResponseModule.getVillageListModelList().size());
                            //tv_title.setText(current_list);

                        }else {
                            ToastUtils.shortToast(baseResponseModule.getStatus() +"\n"+baseResponseModule.getStatusMessage());
                        }
                    }
                }
                break;
            case ADD_DATA:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
//                            Log.d(TAG, "onResponse: " + baseResponseModule.getEyeVanAdd());
                            ToastUtils.shortToast(response.message());
                          finish();//


                        }else {
                            ToastUtils.shortToast(baseResponseModule.getStatus() +"\n"+baseResponseModule.getStatusMessage());
                        }
                    }
                }
                break;
            case UPLOAD_IMAGE:
                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            photo= baseResponse.getInspection_img();
                            Log.d(TAG, "onResponse: "+ExtraUtils.baseImg+photo);
                            Picasso.with(this).load(ExtraUtils.baseImg+photo).into(iv_reflection);

//                            imgList.add(new ImageList(baseResponse.getInspection_img()));
//                            imageAdapter=new ImageAdapter(this,imgList);
//                            binding.rvImgList.setAdapter(imageAdapter);
//                            imageAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
        }
    }


    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        Log.d(TAG, "onFailure: "+t.getMessage());
        ToastUtils.shortToast(t.getMessage());
    }
///eyevan/eye_van/

    @Override
    public void onNoNetwork(int requestCode) {

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