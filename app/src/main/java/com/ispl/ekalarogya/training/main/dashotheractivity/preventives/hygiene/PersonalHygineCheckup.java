package com.ispl.ekalarogya.training.main.dashotheractivity.preventives.hygiene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;
import com.ispl.ekalarogya.training.models.PersonalHygineListModel;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.PersonlHygnChildResponse;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class PersonalHygineCheckup extends AppCompatActivity implements View.OnClickListener, RetroAPICallback{
String head_id,hygieneId,name,member_id;
Uri imageUri;
    String imageurl;

    File photoFile=null;
@BindView(R.id.et_inspection_date)
    TextView et_inspection_date;
@BindView(R.id.cb_nails_yes)
    CheckBox cb_nails_yes;
@BindView(R.id.cb_nails_no)
    CheckBox cb_nails_no;
@BindView(R.id.cb_health_yes)
    CheckBox cb_health_yes;
@BindView(R.id.cb_health_no)
    CheckBox cb_health_no;
@BindView(R.id.iv_nails_image)
    ImageView iv_nails_image;
@BindView(R.id.iv_health_image)
ImageView iv_health_image;
    @BindView(R.id.iv_back)
    ImageView iv_back;
@BindView(R.id.btn_submit)
    AppCompatButton btn_submit;
@BindView(R.id.memeber_name)
TextView memeber_name;
    private static final int VIEW_HYGN_CHEKUP_DATA=111;
    private static final int ADD_HYGN_CHEKUP_DATA=131;
    private static final int PIC_ID_HEALTH=115;
    private static final int PIC_ID_NAILS=117;
    boolean isEdit=false;
    Bitmap photoHealth,photoNails;
    String photoHealthString,photoNailsString,nail,health,status,v_id;

    PersonlHygnChildResponse data;
    PersonalHygineListModel dataModel;
    VillageFamilyModel h_data;
    FamilyMembersModel membersModelDAta;

    private static FamilyMembersModel familyMembersModel;
    static String type="";
    public static void openActivity(FamilyMembersModel personlHygnChildResponses, String typee) {
        familyMembersModel=personlHygnChildResponses;
        type=typee;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_hygine_checkup);
        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if(getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();

            switch (getIntent().getStringExtra("type")){
                case "view":
                    isEdit=false;
                    dataModel=getIntent().getParcelableExtra("m_data");
                    hygieneId=dataModel.getId();//
                    head_id=dataModel.getHead_id();
                    v_id=dataModel.getVillage_id();
                    member_id=dataModel.getMember_id();
                    Log.d("TAG", "onCreate: getintent "+"  view" + dataModel.toString());
                    getPersonalHygnCheckupDetail(hygieneId);
                    break;
                case "edit":
                    status="1";
                    isEdit=true;
                    dataModel=getIntent().getParcelableExtra("m_data");
                    head_id=dataModel.getHead_id();
                    v_id=dataModel.getVillage_id();
                    hygieneId=dataModel.getId();
                    member_id=dataModel.getMember_id();
                    Log.d("TAG", "onCreate: getintent "+"  edit" + dataModel.toString());
                    getPersonalHygnCheckupDetail(hygieneId);
                    break;
                case "add":
                    //Add
                    isEdit=true;
                    status="0";
                    membersModelDAta=getIntent().getParcelableExtra("m_data");
                    h_data=getIntent().getParcelableExtra("h_data");
                    member_id=membersModelDAta.getId();
                    v_id=membersModelDAta.getVillage_id();
                    head_id=membersModelDAta.getHead_id();
                    Log.d("TAG", "onCreate: "+head_id);
                    break;
            }

        }

        if(!isEdit){
            btn_submit.setVisibility(View.GONE);
            iv_nails_image.setEnabled(false);
            iv_health_image.setEnabled(false);
            et_inspection_date.setEnabled(false);

            cb_health_yes.setEnabled(false);
            cb_health_no.setEnabled(false);
            cb_nails_yes.setEnabled(false);
            cb_nails_no.setEnabled(false);
        }
      try{
          if (type.equalsIgnoreCase("add")){
              isEdit=false;
              dataModel=getIntent().getParcelableExtra("m_data");
              hygieneId=dataModel.getId();//
              head_id=dataModel.getHead_id();
              v_id=dataModel.getVillage_id();
              member_id=dataModel.getMember_id();
              Log.d("TAG", "onCreate: getintent "+"  view" + dataModel.toString());
              getPersonalHygnCheckupDetail(hygieneId);
          }
      }catch (Exception e){
          Log.d("TAG", "onCreate: "+e.getMessage());

      }
        initview();

    }

    private void initview() {

        try{
            if (data!=null){
                name=membersModelDAta.getName();
            }else if(membersModelDAta!=null){
                name=membersModelDAta.getName();
            }else{
                name=dataModel.getName();
            }
            memeber_name.setText(name);
        }catch (Exception e){
            Log.d("TAG", "initview: "+e.getMessage());
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PersonalHygineCheckup.this, PersonalHygine.class));
                finish();
            }
        });
        cb_health_yes.setOnClickListener(this);
        cb_nails_yes.setOnClickListener(this);
        cb_health_no.setOnClickListener(this);
        cb_nails_no.setOnClickListener(this);
        iv_nails_image.setOnClickListener(this);
        iv_health_image.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void getPersonalHygnCheckupDetail(String hygieneId) {
        UserService.getDetailsHygineCheckup(this,hygieneId,this,VIEW_HYGN_CHEKUP_DATA);
    }
    private void addPersonalHygnCheckupDetail() {
        UserService.addPersonalHygineChild(this,head_id,v_id,member_id,nail,health,photoHealthString,et_inspection_date.getText().toString(),status,hygieneId,photoNailsString,this,ADD_HYGN_CHEKUP_DATA);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cb_health_yes:

                health="1";

                cb_health_no.setChecked(false);
                break;
            case R.id.cb_health_no:

                health="0";
                photoHealthString="";
                cb_health_yes.setChecked(false);

                break;

            case R.id.cb_nails_yes:

                nail="1";
                cb_nails_no.setChecked(false);

                break;
            case R.id.cb_nails_no:

                nail="0";
                cb_nails_yes.setChecked(false);
                photoNailsString="";
                break;
            case R.id.iv_health_image:
                dexterPerm(PIC_ID_HEALTH);
                break;
            case R.id.iv_nails_image:
                dexterPerm(PIC_ID_NAILS);
                break;
            case R.id.et_inspection_date:
                getDateDialog();
                break;
            case R.id.btn_submit:
                if(validate()){
                    addPersonalHygnCheckupDetail();
                }
                break;

        }
    }

    private boolean validate() {
        if (nail==null){
            ToastUtils.shortToast(getResources().getString(R.string.nail_checkup));
            return false;}
        if (health==null){
            ToastUtils.shortToast(getResources().getString(R.string.health_checkup));
            return false;}
        if (health.equalsIgnoreCase("1")&&photoHealthString.toString().length()<=0){
            ToastUtils.shortToast(getResources().getString(R.string.add_image));
            return false;}
        if (nail.equalsIgnoreCase("1")&&photoNailsString.toString().length()<=0){
            ToastUtils.shortToast(getResources().getString(R.string.add_image));
            return false;}
        if (et_inspection_date.getText().toString().length()<=0){
            ToastUtils.shortToast(getResources().getString(R.string.enter_date));
            return false;}

//        if (v_id==null){
//            ToastUtils.shortToast("Invalid Village ID");
//            return false;}

        return  true;
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(PersonalHygineCheckup.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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


    private void openCameraToClick(int pic_id) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (camera_intent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        try {

            photoFile = createImageFile();

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.inventics.ekalarogya.training.file_provider",
                        photoFile);
                Log.d("TAG", "openCameraToClick: "+photoURI);
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
        Log.d("TAG", "createImageFile: "+storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        photoHealthString = image.getAbsolutePath();
        return image;
    }
//    private File createImageFile() throws IOException {
//        String timeStamp =
//                new SimpleDateFormat("yyyyMMdd_HHmmss",
//                        Locale.getDefault()).format(new Date());
//        String imageFileName = "IMG_" + timeStamp + "_";
////        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);;
//        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
//
//        imageurl = image.getAbsolutePath();
//
//        Log.d("TAG", "createImageFile: hghg "+imageurl+"\n ");
//
//        return image;
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      try {
          switch (requestCode){
              case PIC_ID_HEALTH:
                  if(resultCode == Activity.RESULT_OK) {
                      Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                      Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                      myBitmap= ImageUtils.getResizedBitmap(myBitmap,240);
                      photoHealthString=ImageUtils.convertBitmapIntoBase64(myBitmap);
//                      Log.d("TAG", "onActivityResult:photoMemberString "+photoHealthString);
                      iv_health_image.setImageBitmap(myBitmap);
                  }else{
                      ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                  }
//                  if (resultCode == Activity.RESULT_OK) {
//                      try {
////                              Bitmap myBitmap = BitmapFactory.decodeFile(imageurl);//                              Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
//                          FileInputStream in = new FileInputStream(imageurl);
//                          BitmapFactory.Options options = new BitmapFactory.Options();
//                          options.inSampleSize = 10;
//                          Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
//                          //img = (ImageView) findViewById(R.id.imageView1);
//                          int nh = (int) ( bmp.getHeight() * (512.0 / bmp.getWidth()) );
//                          Bitmap scaled = Bitmap.createScaledBitmap(bmp, 512, nh, true);
//                          iv_health_image.setImageBitmap(scaled);
//                          photoHealthString= ImageUtils.convertBitmapIntoBase64(scaled);
//
//
//
////                             imageurl = getRealPathFromURI(imageUri);
////                              Log.d("TAG", "onActivityResult: ngfgyhgfg "+imageurl);
//                      } catch (Exception e) {
//                          e.printStackTrace();
//                      }
//
//                  }

                  break;
              case PIC_ID_NAILS:
                  if (resultCode == Activity.RESULT_OK){
                      Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                      myBitmap=ImageUtils.getResizedBitmap(myBitmap,240);
                      photoNailsString= ImageUtils.convertBitmapIntoBase64(myBitmap);
//                      Log.d("TAG", "onActivityResult:photoMemberString "+photoNailsString);
                      iv_nails_image.setImageBitmap(myBitmap);
                  }else{
                      ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                  }
                  break;
          }
      }catch (Exception e){
          Log.d("TAG", "onActivityResult: "+e.getMessage());
      }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case VIEW_HYGN_CHEKUP_DATA:
                if(response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            memeber_name.setText(baseResponse.getPersonalHygieneCheckupResponse().getName());
                            et_inspection_date.setText(baseResponse.getPersonalHygieneCheckupResponse().getInspection_date());
                            nail=baseResponse.getPersonalHygieneCheckupResponse().getNail();
                            health=baseResponse.getPersonalHygieneCheckupResponse().getHealth();
                            photoHealthString=baseResponse.getPersonalHygieneCheckupResponse().getHealth_photo();
                            photoNailsString=baseResponse.getPersonalHygieneCheckupResponse().getNail_photo();
                            if (nail.equalsIgnoreCase("Yes")){
                                cb_nails_yes.setChecked(true);
                                nail="1";
                            }else{
                                cb_nails_no.setChecked(true);
                                nail="0";

                            }
                            if (health.equalsIgnoreCase("Yes")){
                                cb_health_yes.setChecked(true);
                                health="1";
                            }
                            else{
                                cb_health_no.setChecked(true);
                                health="0";

                            }
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getPersonalHygieneCheckupResponse().getNail_photo()).error(R.drawable.add_image).into(iv_nails_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getPersonalHygieneCheckupResponse().getHealth_photo()).error(R.drawable.add_image).into(iv_health_image);
//                          }else {

                        }
                    }else {
//                        ProgressDialogFragment.dismissProgress(getSupportFragmentManager());
                        ToastUtils.shortToast(baseResponse.getStatusMessage());}

                }else {
//                    ProgressDialogFragment.dismissProgress(getSupportFragmentManager());
                    ToastUtils.shortToast(response.message());
            }
                break;
            case ADD_HYGN_CHEKUP_DATA:
                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
//                            startActivity(new Intent(PersonalHygineCheckup.this,PersonalHygine.class));
                            finish();
                        } else {
//                            ProgressDialogFragment.getInstance().dismiss();
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                        }
                    }
                } else {
//                    ProgressDialogFragment.getInstance().dismiss();
                    ToastUtils.shortToast(response.message());
                }
                break;
        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        ProgressDialogFragment.dismissProgress(getSupportFragmentManager());

        ToastUtils.shortToast(t.getMessage());
        Log.d("TAG", "onFailure: "+t.getMessage());

    }

    @Override
    public void onNoNetwork(int requestCode) {
//        ProgressDialogFragment.getInstance().dismiss();
        Log.d("TAG", "onNoNetwork: ");

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

}