package com.inventics.ekalarogya.training.main.dashotheractivity.arogyacamp;

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
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.ArogyaCampModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ArogyaCampAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private Handler handler;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvSanch)
    TextView tvSanch;
    @BindView(R.id.tv_VillVistcount)
    TextView tv_VillVistcount;
    File photoFile = null;

    @BindView(R.id.spin_village_name)
    Spinner spin_village_name;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.et_total_present)
    TextView et_total_present;

    @BindView(R.id.et_boys)
    EditText et_boys;
    @BindView(R.id.et_girls)
    EditText et_girls;
    @BindView(R.id.et_no_of_children)
    EditText et_no_of_children;
    @BindView(R.id.et_no_of_female)
    EditText et_no_of_female;
    @BindView(R.id.et_no_of_male)
    EditText et_no_of_male;
    @BindView(R.id.et_no_of_doctor)
    EditText et_no_of_doctor;
    @BindView(R.id.et_prvt_ref)
    EditText et_prvt_ref;
    @BindView(R.id.et_govt_ref)
    EditText et_govt_ref;




    @BindView(R.id.addImage)
    ImageView addImage;
    @BindView(R.id.previous)
    ImageView previous;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.list_Image_delete)
    ImageView list_Image_delete;
    @BindView(R.id.images)
    ImageView images;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    private final static int ADD_DATA_REQUEST=111;
    private static final int GET_VILLAGE_LIST=115;
    private static final int GET_CAMP_DETAILS=215;
    private static final int GET_ALL_LIST=216;
    private static final int REQUEST_LOCATION = 104;
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private List<VillageListModel> villName = new ArrayList<>();
    boolean isEdit=false;
    VillageListArrayAdapter adapter;

    Bitmap photoPosterClick;
    String photos;

    private static final String TAG = ArogyaCampAdd.class.getSimpleName();
    String type,id,total="00",child="0",male="0",female="0",boys="0",girls="0",date_camp,village,camp_photos,status="0",camp_id,doctor="0",refGovt="0",refPvt="0";
    ArogyaCampModel m_data;


    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    TextView totalImages;
    ArrayList<String> mArrayUri;
    int position = 0;
    List<String> imagesEncodedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arogya_camp_add);
        ButterKnife.bind(this);
        getVillageList();
        if (getIntent().getStringExtra("type") != null) {
            Intent i = getIntent();
            type = i.getStringExtra("type");

            mArrayUri = new ArrayList<String>();

            if (mArrayUri.size() <= 0) {
                Log.d(TAG, "onCreate: ");
                list_Image_delete.setVisibility(View.GONE);
            } else {
                list_Image_delete.setVisibility(View.VISIBLE);
            }
            switch (type) {
                case "add":
                    isEdit = true;
                    break;
                case "view":
                    isEdit = false;
                    m_data = i.getParcelableExtra("m_data");
                    break;
                case "edit":
                    isEdit = true;
                    m_data = i.getParcelableExtra("m_data");
                    break;
            }

        }

        et_boys.addTextChangedListener(textWatcher);
        et_girls.addTextChangedListener(textWatcher);
        et_no_of_children.addTextChangedListener(textWatcher);
        et_no_of_male.addTextChangedListener(textWatcher);
        et_no_of_female.addTextChangedListener(textWatcher);
//        et_total_present.addTextChangedListener(textWatcher);
//        et_govt_ref.addTextChangedListener(textWatcher);
//        et_prvt_ref.addTextChangedListener(textWatcher);
//        et_no_of_doctor.addTextChangedListener(textWatcher);

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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (!isEdit){
            et_boys.setEnabled(false);
            et_girls.setEnabled(false);
            et_no_of_children.setEnabled(false);
            et_no_of_male.setEnabled(false);
            et_no_of_female.setEnabled(false);
            et_total_present.setEnabled(false);
            et_govt_ref.setEnabled(false);
            et_prvt_ref.setEnabled(false);
            et_no_of_doctor.setEnabled(false);

            tv_date.setEnabled(false);
            spin_village_name.setEnabled(false);
            addImage.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }


        tv_date.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        addImage.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        list_Image_delete.setOnClickListener(this);


//        try {

//        }catch (Exception e){
//            Log.d(TAG, "onTextChanged: "+e.getMessage());
//        }

        if (type.equalsIgnoreCase("view")||type.equalsIgnoreCase("edit")){
            getArogyaDetail(m_data);
        }
    }

    private void getArogyaDetail(ArogyaCampModel m_data) {
     UserService.getArogyaCampDetails(this,m_data.getId(),this,GET_CAMP_DETAILS);
    }

    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }

    
    
    private void setupdata(ArogyaCampModel posterDisplayModel) {
        int postion=0;
        status="1";
        total=posterDisplayModel.getTotal_patient();
        date_camp=posterDisplayModel.getMeeting_date();
        village=posterDisplayModel.getVillage_id();
        child=posterDisplayModel.getTotal_children();
        boys=posterDisplayModel.getBoy_patient();
        girls=posterDisplayModel.getGirl_patient();
        male=posterDisplayModel.getMale_patient();
        female=posterDisplayModel.getFemale_patient();
        doctor=posterDisplayModel.getNo_of_doctor();
        refPvt=posterDisplayModel.getReferred_pvt();
        refGovt=posterDisplayModel.getReferred_govt();
        photos=posterDisplayModel.getCamp_photo();
        camp_id=posterDisplayModel.getCamp_id();
        tv_VillVistcount.setText(getResources().getString(R.string.count)+" : "+posterDisplayModel.getVillage_visit_count());
        tvSanch.setText(getResources().getString(R.string.sanch_name)+" : "+posterDisplayModel.getSanch_name());

        et_total_present.setText(total);
//        et_boys.setText(boys);
//        et_girls.setText(girls);
        et_no_of_children.setText(child);
        et_no_of_female.setText(posterDisplayModel.getFemale_patient());
        et_no_of_male.setText(posterDisplayModel.getMale_patient());
        et_no_of_doctor.setText(doctor);
        et_prvt_ref.setText(refPvt);
        et_govt_ref.setText(refGovt);
        tv_date.setText(date_camp);
        Picasso.with(this).load(ExtraUtils.baseImg+photos).into(images);

        if (villName!=null){
            for (int i=0;i<villName.size();i++){
                if (villName.get(i).getVillage_id().equalsIgnoreCase(village)){
                    spin_village_name.setSelection(i);
                }
            }
        }else{
            startDelayHandler();
//            ToastUtils.shortToast("Village"+" spinner not load");
        }


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
                                spin_village_name.setAdapter(adapter);
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
                                spin_village_name.setAdapter(adapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }
                break;
//            case GET_ALL_LIST:
//                if (response.isSuccessful()) {
//                    GetAllListResponse getAllListResponse = (GetAllListResponse) response.body();
//                    if (getAllListResponse != null) {
//                        if (getAllListResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            Log.d(TAG, "onResponse: " + getAllListResponse.getOccupation_list().size());
//                            String[] CategoryTopic = new String[getAllListResponse.getOccupation_list().size()];
//                            for (int i = 0; i < getAllListResponse.getOccupation_list().size(); i++) {
//                                CategoryTopic[i] = getAllListResponse.getOccupation_list().get(i).getOccupation();
//                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ArogyaCampAdd.this, android.R.layout.simple_spinner_item, CategoryTopic);
//                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//                                spin_topic.setAdapter(spinnerArrayAdapter);
//                            }
//
//                            Log.d(TAG, "onResponse: " + getAllListResponse.getOccupation_list().size());
//                            String[] CategoryWeek = new String[getAllListResponse.getOccupation_list().size()];
//                            for (int i = 0; i < getAllListResponse.getOccupation_list().size(); i++) {
//                                CategoryWeek[i] = getAllListResponse.getOccupation_list().get(i).getOccupation();
//                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ArogyaCampAdd.this, android.R.layout.simple_spinner_item, CategoryWeek);
//                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
//                                spin_week.setAdapter(spinnerArrayAdapter);
//                            }
//                        }
//                    }
//                }
//                break;
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
            case GET_CAMP_DETAILS:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                            if ( baseResponse.getArogyaCampModel()!=null){
                                ToastUtils.shortToast(baseResponse.getStatusMessage());
                                setupdata(baseResponse.getArogyaCampModel());
                            }

                        }else{
                            ToastUtils.shortToast(baseResponse.getStatusMessage());

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
                Log.d(TAG, "onClick: "+mArrayUri.toString());
                if(validate()){
                    submitdata();
                }
                break;
            case R.id.addImage:
                 dexterPerm(PIC_ID_MEMBER_DIALOG);
                break;
            case R.id.next:
                if (position>0){
                    previous.setEnabled(true);}
                if (position < mArrayUri.size() - 1) {
                    position++;
                    images.setImageBitmap(convertBase64ToBitmap(mArrayUri.get(position)));
                } else {
                    Toast.makeText(ArogyaCampAdd.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.previous:

                if (position > 0) {
                    position--;
                    images.setImageBitmap(convertBase64ToBitmap(mArrayUri.get(position)));
                }
                if (position==0){
                    previous.setEnabled(false);}else{previous.setEnabled(true);}
                break;
            case R.id.list_Image_delete:
                // mArrayUri.remove(imgPos);
                if (mArrayUri.size()>0){
                    mArrayUri.remove(position);
                    if (mArrayUri.size()>0){
                        images.setImageBitmap(convertBase64ToBitmap(mArrayUri.get(0)));
                    }else {
                        images.setImageResource(R.drawable.add_image);
                        list_Image_delete.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.tv_date:
                getDateDialog();
                break;


        }

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
        photos = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_ID_MEMBER_DIALOG:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);
                    photos=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    Log.d("TAG", "onActivityResult:photoMemberString " + photos);
                    Log.d("TAG", "onActivityResult:photo marray Size " + mArrayUri.size());
                    if (photos != null) {
                        mArrayUri.add(photos);
//                    Picasso.with(this).load(mArrayUri.get(0)).into(images);
                        String img = mArrayUri.get(0);
                        images.setImageBitmap(convertBase64ToBitmap(img));
                        position = 0;
                        list_Image_delete.setVisibility(View.VISIBLE);

                    }
                    Log.d("TAG", "onActivityResult:photo marray Size " + mArrayUri.size());
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                //click_image_id.setImageBitmap(photo);
                break;
        }
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
    private boolean validate() {

        if (village==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_village));
            return false;}
        if (mArrayUri.size()==0){
            ToastUtils.shortToast(getResources().getString(R.string.add_image));
            return false;}
//        if (posterCount==null){
//            ToastUtils.shortToast(getResources().getString(R.string.add_image));
//            return false;}
        if (et_no_of_female.getText().toString()==null){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_boys.getText().toString().toString()==null){
            ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_no_of_children.getText().toString()==null){
            et_no_of_children.setError(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_no_of_male.getText().toString()==null){
            et_no_of_male.setError(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_girls.getText().toString()==null){
            et_girls.setError(getResources().getString(R.string.fill_detail));
            return false;}

        if (et_total_present.getText().toString()==null){
            et_total_present.setError(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_no_of_doctor.getText().toString()==null){
            et_no_of_doctor.setError(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_govt_ref.getText().toString()==null){
            et_govt_ref.setError(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_prvt_ref.getText().toString()==null){
            et_prvt_ref.setError(getResources().getString(R.string.fill_detail));
            return false;}


        girls=et_girls.getText().toString();
        boys=et_boys.getText().toString();
        male=et_no_of_male.getText().toString();
        female=et_no_of_female.getText().toString();
        child=et_no_of_children.getText().toString();
        total=et_total_present.getText().toString();
        doctor=et_no_of_doctor.getText().toString();
        refGovt=et_govt_ref.getText().toString();
        refPvt=et_prvt_ref.getText().toString();
        camp_photos=mArrayUri.toString();


        return true;
    }

    private void submitdata() {
        UserService.addArogyaCampData(this,status,camp_id,village,date_camp,boys,girls,male,female,child,total,doctor,refGovt,refPvt,camp_photos,this,ADD_DATA_REQUEST);
    }
    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(ArogyaCampAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateString =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                tv_date.setText(dateString);
                date_camp=dateString;
                Log.d("TAG", "onDateSet: "+dateString);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            try{
                total= String.valueOf(Integer.parseInt(child)+Integer.parseInt(female)+Integer.parseInt(male)+Integer.parseInt(boys)+Integer.parseInt(girls));
                Log.d("TAG", "onTextChanged Total: "+total);
//                if (total==null){
//                    et_total_present.setText("00 null");
//                }else {
                    et_total_present.setText(total);
//                }
            }catch (Exception e){
                Log.d(TAG, "onTextChanged: "+e.getMessage());
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get the content of both the edit text
           try {
//               if (et_no_of_children.getText().toString()==null){
//                   child="0";
//               }else{
                   child = et_no_of_children.getText().toString();
//               }

//               if (et_girls.getText().toString()==null){
//                   child="0";
//               }else{
                   girls = et_girls.getText().toString();
//               }

//               if (et_no_of_female.getText().toString()==null){
//                   child="0";
//               }else{
                   female = et_no_of_female.getText().toString();
//               }

//               if (et_no_of_male.getText().toString()==null){
//                   child="0";
//               }else{
                   male = et_no_of_male.getText().toString();
//           }

//               if (et_boys.getText().toString()==null){
//                   child="0";
//               }else{
                   boys = et_boys.getText().toString();
//           }

           }catch (Exception e){
               Log.d(TAG, "onTextChanged: "+e.getMessage());
           }

            try{
                total= String.valueOf(Integer.parseInt(et_no_of_male.getText().toString())+Integer.parseInt(et_no_of_female.getText().toString())+Integer.parseInt(et_no_of_children.getText().toString()));
                Log.d("TAG", "onTextChanged Total: "+total);
//                if (total==null){
//                    et_total_present.setText("00 null");
//                }else {
                    et_total_present.setText(total);
//                }
            }catch (Exception e){
                Log.d(TAG, "onTextChanged: "+e.getMessage());
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                total= String.valueOf(Integer.parseInt(child)+Integer.parseInt(female)+Integer.parseInt(male)+Integer.parseInt(boys)+Integer.parseInt(girls));
                Log.d("TAG", "onTextChanged Total: "+total);
//                if (total==null){
//                    et_total_present.setText("00");
//                }else {
                et_total_present.setText(total);
//                }
            }catch (Exception e){
                Log.d(TAG, "onTextChanged: "+e.getMessage());
            }

        }
    };
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
        getVillageList();
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