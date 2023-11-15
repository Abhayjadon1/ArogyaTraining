package com.ispl.ekalarogya.training.main.dashotheractivity.samitimeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.SamitiModel;
import com.ispl.ekalarogya.training.models.VillageListModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.SamitiDetailResponse;
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

public class SamitiMeetingAdd extends AppCompatActivity implements RetroAPICallback,View.OnClickListener {

    private static final int PIC_DIALOG = 121;
    private static final int UPLOAD_IMAGE = 131;
    Bitmap photoBm;
    File photoFile=null;
    String photoString;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.tv_title)
    TextView tv_title;
//    @BindView(R.id.tvEmpty)
//    TextView tvEmpty;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.et_arogya_samiiti)
    EditText et_arogya_samiiti;
    @BindView(R.id.et_arogya_sevika)
    EditText et_arogya_sevika;
    @BindView(R.id.et_sanyojika)
    EditText et_sanyojika;
    @BindView(R.id.et_sevavriti)
    EditText et_sevavriti;
    @BindView(R.id.et_remarks)
    EditText et_remark;
    @BindView(R.id.addImage)
    ImageView addImage;

    @BindView(R.id.et_sanch_coordinator)
    EditText et_sanch_coordinator;

    @BindView(R.id.btn_submit)
    Button btn_submit;


    @BindView(R.id.spinner_village)
    Spinner spinner_village;

    private final static int ADD_DATA_REQUEST=111;
    private static final int GET_VILLAGE_LIST=115;
    private static final int GET_MEETING_DETAILS=215;
    private List<VillageListModel> villName = new ArrayList<>();
    boolean isEdit=false;
    VillageListArrayAdapter adapter;

    private static final String TAG = SamitiMeeting.class.getSimpleName();
    String type,id,sevika,sanyojika,total,sevavriti,sanch_coordinator,samiti,meeting_date,remark,village,status="0",samiti_id;
    SamitiModel m_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samiti_meeting_add);
        ButterKnife.bind(this);
        getVillageList();
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");

            switch (type) {
//                case "1":
//                    tv_title.setText("Update owner details");
//                    break;
                case "add":
                    isEdit=true;
                    tv_title.setText(R.string.add_samiti_meeting);

                    break;
                case "view":
                    isEdit=false;
                    m_data=i.getParcelableExtra("m_data");
                    tv_title.setText(R.string.view_samiti_details);
                    getMeetingData(m_data.getId());

                    break;
                case "edit":
                    isEdit=true;
                    status="1";
                    m_data=i.getParcelableExtra("m_data");
                    tv_title.setText(R.string.update_samiti_details);
                    getMeetingData(m_data.getId());
                    samiti_id=m_data.getId();
                    break;
            }
        }

        et_sevavriti.addTextChangedListener(textWatcher);
        et_sanyojika.addTextChangedListener(textWatcher);
        et_arogya_sevika.addTextChangedListener(textWatcher);
        et_arogya_samiiti.addTextChangedListener(textWatcher);
        et_sanch_coordinator.addTextChangedListener(textWatcher);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dexterPerm(PIC_DIALOG);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        if (!isEdit){
            et_arogya_samiiti.setEnabled(false);
            et_sanch_coordinator.setEnabled(false);
            et_sanyojika.setEnabled(false);
            et_sevavriti.setEnabled(false);
            et_arogya_sevika.setEnabled(false);
            et_remark.setEnabled(false);
            addImage.setEnabled(false);
            tv_date.setEnabled(false);
            spinner_village.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }

        tv_date.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void getMeetingData(String meeting_id) {
        UserService.getSamitiDetails(this,meeting_id,this,GET_MEETING_DETAILS);
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
        photoString = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_DIALOG:
                if(resultCode == Activity.RESULT_OK) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);
                    photoString= ImageUtils.convertBitmapIntoBase64(myBitmap);
//                    photoString = ImageUtils.convertBitmapIntoBase64(photoBm);
                    Log.d("TAG", "onActivityResult:photoHeadString " + photoString);
                    addImage.setImageBitmap(photoBm);
                    uploadImg("/eyevan/eye_van/ev",photoString);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                // click_image_id.setImageBitmap(photo);
                break;
        }
    }
    private void uploadImg(String path,String base64) {
        UserService.uploadImageApi(this,path,base64,this,UPLOAD_IMAGE);
    }

    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }
    private void setupdata(SamitiDetailResponse samitiDetailResponse) {
        Log.d(TAG, "setupdata: "+samitiDetailResponse.toString());
        int postion=0;
        village=samitiDetailResponse.getVillage_id();
        et_arogya_samiiti.setText(samitiDetailResponse.getSamiti());
        et_sanch_coordinator.setText(samitiDetailResponse.getSanch_coordinator());
        et_sanyojika.setText(samitiDetailResponse.getSanyojak());
        et_sevavriti.setText(samitiDetailResponse.getSevavriti());
        et_arogya_sevika.setText(samitiDetailResponse.getSevika());
        et_remark.setText(samitiDetailResponse.getRemark());
        tv_date.setText(samitiDetailResponse.getMeeting_date());
        Picasso.with(this).load(ExtraUtils.baseImg+samitiDetailResponse.getImage()).into(addImage);
//        for (int i=0;i<villName.size();i++){
//            if (villName.get(i).getVillage_id()==samitiDetailResponse.getVillage()){
//                postion=villName.indexOf(i);
//                Log.d(TAG, "setupdata village: "+postion);
//            }
//            Log.d(TAG, "setupdata: "+postion);
//        }

      for(int i=0;i<villName.size();i++){
          if (villName.get(i).getVillage_id().equalsIgnoreCase(village)){
              spinner_village.setSelection(i);
          }
      }
//        spinner_village.setSelection(adapter.getPosition("");
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
                                spinner_village.setAdapter(adapter);
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
                                spinner_village.setAdapter(adapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
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

                break;
            case GET_MEETING_DETAILS:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                            if ( baseResponse.getSamitiDetailResponse()!=null){
                                ToastUtils.shortToast(baseResponse.getStatusMessage());
                                setupdata(baseResponse.getSamitiDetailResponse());
                            }

                        }else{
                            ToastUtils.shortToast(baseResponse.getStatusMessage());

                        }
                    }
                }

                break;
            case UPLOAD_IMAGE:
                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            photoString= baseResponse.getInspection_img();
                            Log.d(TAG, "onResponse: "+ ExtraUtils.baseImg+photoString);
                            Picasso.with(this).load(ExtraUtils.baseImg+photoString).into(addImage);
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
        if (tv_date.getText().toString().length()<=0){
            tv_date.setError("Please select date");
            return false;}
//        if (photoString.length()==0){
//            ToastUtils.shortToast(R.string.add_image);
//            return false;}
        if (village==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_village));
            return false;}
        if (et_arogya_samiiti.getText().length()==0){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));

            return false;}
        if (et_arogya_sevika.getText().length()==0){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));

            return false;}
        if (et_sanyojika.getText().length()==0){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));

            return false;}
        if (et_sevavriti.getText().length()==0){
//            et_sevavriti.setError("Enter total no. of Sevavriti");
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;}
        if (village==null){
//            ToastUtils.shortToast("Select Village");
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;}
        if (total==null){
//            ToastUtils.shortToast("Total Member");
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;}


        try{
            remark=et_remark.getText().toString();
        }catch (Exception e){

        }

        try{
            sanch_coordinator=et_sanch_coordinator.getText().toString();
        }catch (Exception e){

        }


        return true;
    }

    private void submitdata() {
        UserService.addSamitiDetails(this,status,samiti_id,village,meeting_date,samiti,sevika,sanyojika,sevavriti,sanch_coordinator,total,photoString,remark,this,ADD_DATA_REQUEST);
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(SamitiMeetingAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            try{
                total= String.valueOf(Integer.parseInt(samiti)+Integer.parseInt(sevika)+Integer.parseInt(sevavriti)+Integer.parseInt(sanyojika));
                Log.d("TAG", "onTextChanged: "+total);
                tv_total.setText(String.valueOf(total));
            }catch (Exception e){
                Log.d(TAG, "afterTextChanged: "+e.getMessage());
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get the content of both the edit text
//
            try{
                samiti = et_arogya_samiiti.getText().toString();
                sevavriti = et_sevavriti.getText().toString();
                sevika = et_arogya_sevika.getText().toString();
                sanyojika = et_sanyojika.getText().toString();
                sanch_coordinator = et_sanch_coordinator.getText().toString();

            }catch (Exception e){

            }
            try{
                total= String.valueOf(Integer.parseInt(samiti)+Integer.parseInt(sevika)+Integer.parseInt(sevavriti)+Integer.parseInt(sanyojika)+Integer.parseInt(sanch_coordinator));
                Log.d("TAG", "onTextChanged: "+total);
                tv_total.setText(String.valueOf(total));
            }catch (Exception e){
                Log.d(TAG, "afterTextChanged: "+e.getMessage());
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
           try{
               total= String.valueOf(Integer.parseInt(samiti)+Integer.parseInt(sevika)+Integer.parseInt(sevavriti)+Integer.parseInt(sanyojika)+Integer.parseInt(sanch_coordinator));
               Log.d("TAG", "onTextChanged: "+total);
               tv_total.setText(String.valueOf(total));
           }catch (Exception e){
               Log.d(TAG, "afterTextChanged: "+e.getMessage());
           }

        }
    };

}