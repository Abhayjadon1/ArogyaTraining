package com.ispl.ekalarogya.training.main.dashotheractivity.jagaranprogram;

import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.ImageAdapter;
import com.ispl.ekalarogya.training.adapters.JagranProgramListAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;

import com.ispl.ekalarogya.training.databinding.ActivityJagaranProgramAddBinding;
import com.ispl.ekalarogya.training.dialogs.ProgressDialogFragment;
import com.ispl.ekalarogya.training.models.ImageList;
import com.ispl.ekalarogya.training.models.JagranProgramList;
import com.ispl.ekalarogya.training.models.JagranProgramModel;
import com.ispl.ekalarogya.training.models.VillageListModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.GetAllListResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ImageUtils;
import com.ispl.ekalarogya.training.utils.ToastUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

public class JagaranProgramAdd extends AppCompatActivity implements RetroAPICallback,JagranProgramListAdapter.OnItemClickListner{
    String imageurl;
    ImageAdapter imageAdapter;
    List<ImageList> imgList=new ArrayList<ImageList>();
    List<String> jplist=new ArrayList<>();
    ImageList imgModel;
    int indexId,plantCount=0,listId;
    List<JagranProgramList> jagranProgramLists1=new ArrayList<>();


    private static final int ADD_JAGARAN_DATA=111;
    private static final int VIEW_JAGARAN_DATA=123;
    public static final int IMAGE_JAGARAN_DATA=124;
    private static final int UPLOAD_IMAGE=1123;
    final static int GET_ALL_LIST=119;
    JagranProgramListAdapter jagranProgramListAdapter;

    ImageList imageList;
    VillageListModel v_data;
    String v_id,status="0",jagran_id,village_id,program_date,program_data,data,images;
    int healthJagran,soakPit,wastePit,wallWriting,peopleToilet,medicinePlant,nutritionPlant,filterUses,treePlanting;
    boolean isEdit=false;
    JagranProgramModel m_data;
    List<JagranProgramList> jagranProgramLists=new ArrayList<>();



    private static final String TAG = JagaranProgramAdd.class.getSimpleName();
    String type,id,arogya_topic,week,posterCount,postersImg,village;

    Context context = (Context)this;
    private ActivityJagaranProgramAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_jagaran_program_add);
//        ButterKnife.bind(this);
        binding=ActivityJagaranProgramAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        imageListUpdater();

        if (getIntent().getStringExtra("type") != null) {
            Intent i = getIntent();
            type = i.getStringExtra("type");
            switch (type) {
                case "add":
                    isEdit = true;
                    v_data=i.getParcelableExtra("v_id");
                    v_id=v_data.getVillage_id();
                    binding.tvVillageName.setText(v_data.getVillage_name());
                    break;
                case "view":
                    isEdit = false;
                    m_data = i.getParcelableExtra("m_data");
                    viewData(m_data);
                    break;
                case "edit":
                    isEdit = true;
                    m_data = i.getParcelableExtra("m_data");
                    viewData(m_data);
                    break;
            }

        }

        getAllList();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.tvDate.getText()!=null){
                    submit();
                }else {
                    ToastUtils.shortToast(getResources().getString(R.string.enter_date));
                }
            }
        });
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateDialog();
            }
        });
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    private void viewData(JagranProgramModel m_data) {
        UserService.getJagranDetails(this,m_data.getId(),this,VIEW_JAGARAN_DATA);
    }
    private void uploadImg(String path,String base64) {

        UserService.uploadImageApi(this,path,base64,this,UPLOAD_IMAGE);
    }

    private void submit() {

        images=null;
        List<JagranProgramList>jagranProgramLists2=new ArrayList<>();
        if (jagranProgramLists!=null&&jagranProgramLists.size()>0){
            for (int i=0;i<jagranProgramLists.size();i++){
                if (jagranProgramLists.get(i).getImage()!=null){
                    jagranProgramLists2.add(new JagranProgramList(jagranProgramLists.get(i).getId(),jagranProgramLists.get(i).getCurrent_present(),jagranProgramLists.get(i).getProgram_title(),jagranProgramLists.get(i).getImage()));
                }
//                Log.d(TAG, "submit: imgaes count item "+i+"  total size"+imgList.size());
            }
        }
        Gson gson = new GsonBuilder().create();
        JsonArray program_data = gson.toJsonTree(jagranProgramLists2).getAsJsonArray();

        Log.d(TAG, "submit: "+program_data.toString());
        //status,jagran_id,village_id,program_date,data,images
        UserService.addJagranDetail(this,status,jagran_id,v_id,binding.tvDate.getText().toString(),String.valueOf(program_data),this,ADD_JAGARAN_DATA);
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case UPLOAD_IMAGE:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            for (int i=0;i<jagranProgramLists.size();i++){
                                if (listId==Integer.parseInt(jagranProgramLists.get(i).getId())){
                                    jagranProgramLists.get(i).setImage(baseResponse.getInspection_img());
//                                   plankModelList1.get(i).setCount(plantCount);
                                    Log.d(TAG, "onResponse: from if "+ jagranProgramLists.get(i).toString());
                                }
                            }
//                        imgList.add(new ImageList(baseResponse.getInspection_img()));
//                        imageAdapter=new ImageAdapter(this,imgList);
//                        binding.rvImgList.setAdapter(imageAdapter);
//                        imageAdapter.notifyDataSetChanged();
                            jagranProgramListAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            case ADD_JAGARAN_DATA:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                            startActivity(new Intent(JagaranProgramAdd.this,JagranProgram.class));
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
            case GET_ALL_LIST:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                if (response.isSuccessful()) {
                    GetAllListResponse baseResponse = (GetAllListResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");

                            if (baseResponse.getJagranProgramLists().size() > 0) {
                                for(int i=0;i<1;i++){
//                                    if(baseResponse.getJagranProgramLists().get(i).getProgram_title().equalsIgnoreCase("Health and cleanliness Jagran Rally")
//                                            ||  baseResponse.getJagranProgramLists().get(i).getProgram_title().equalsIgnoreCase(context.getResources().getString(R.string.health_clean_rally))){
                                        jagranProgramLists.add(baseResponse.getJagranProgramLists().get(i));
                                        binding.rvJagarnProgramRecyclerview.setHasFixedSize(true);
                                        GridLayoutManager gridLayoutManager = new GridLayoutManager(JagaranProgramAdd.this, 1);
                                        binding.rvJagarnProgramRecyclerview.setLayoutManager(gridLayoutManager);
                                        jagranProgramListAdapter=new JagranProgramListAdapter(this,jagranProgramLists,this);
                                        binding.rvJagarnProgramRecyclerview.setAdapter(jagranProgramListAdapter);
//                                    }
                                }




//                                if (jplist!=null&&jplist.size()>0){
//                                    for (int i=0;i<jplist.size();i++){
//                                        for (int j=0;j<jagranProgramLists.size();j++){
//                                            if (jplist.get(i).equalsIgnoreCase(jagranProgramLists.get(j).getId())){
//                                                jagranProgramLists.get(j).setSelect(true);
//                                            }
//                                        }
//                                    }
//                                }
//                                if (m_data!=null&&m_data.getProgram_details()!=null){
//                                    String str=m_data.getProgram_details();
//                                    jplist= new ArrayList(Arrays.asList(str.split(",")));
//                                    Log.d(TAG, "onResponse: vchsvcj "+jplist.toString());
//                                    for (int i=0;i<jplist.size();i++){
//                                        for (int j=0;j<jagranProgramLists.size();j++){
//                                            if (jplist.get(i).equalsIgnoreCase(jagranProgramLists.get(j).getProgram_title())){
//                                                Log.d(TAG, "onResponse: vchsvcjzx ncsdc "+jagranProgramLists.get(j).getProgram_title());
//
//                                                jagranProgramLists.get(j).setSelect(true);
//                                            }
//                                        }
//                                    }
//                                }


                                updateui();

                               if(jagranProgramListAdapter!=null){
                                   jagranProgramListAdapter.notifyDataSetChanged();
                               }



                            } else {
                                ToastUtils.shortToast(baseResponse.getStatus() + "\n" + baseResponse.getStatus());
                            }

//

                        }
                    }
                }
                break;
            case VIEW_JAGARAN_DATA:
                ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            JagranProgramModel jagranProgramModel=baseResponse.getJagranProgramModelDetails();
                            binding.tvDate.setText(jagranProgramModel.getProgram_date());
                            binding.tvVillageName.setText(jagranProgramModel.getVillage_name());
//                            binding.etMemberCount.setText(jagranProgramModel.getTotal());
                            jagran_id=jagranProgramModel.getId();
                            v_id=jagranProgramModel.getVillage_id();
                            jplist=jagranProgramModel.getProgram_ids();
                            status="1";
                            imgList=jagranProgramModel.getImages();
                            if (jagranProgramModel.getProgram_list()!=null&&jagranProgramModel.getProgram_list().size()>0){
                                jagranProgramLists1.addAll(jagranProgramModel.getProgram_list());

                                Log.d(TAG, "onResponse: vchsvcj jpList :"+jagranProgramLists1.toString());

//                                jagranProgramListAdapter.notifyDataSetChanged();
                                updateui();
                            }
//                            if (m_data!=null&&m_data.getProgram_details()!=null){
//                                String str=m_data.getProgram_details();
//                                jplist= new ArrayList(Arrays.asList(str.split(",")));
//                                for (int i=0;i<jplist.size();i++){
//                                    for (int j=0;j<jagranProgramLists.size();j++){
//                                        if (jplist.get(i).equalsIgnoreCase(jagranProgramLists.get(j).getId())){
//                                            jagranProgramLists.get(j).setSelect(true);
//                                        }
//                                    }
//                                }
//                            }


//                            updateUI();
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

    private void updateui() {
        if (jagranProgramLists1!=null&&jagranProgramLists1.size()>0){

            for (int i=0;i<jagranProgramLists1.size();i++){
                Log.d(TAG, "onResponse: ");
                for (int j=0;j<jagranProgramLists.size();j++){
                    Log.d(TAG, "onResponse: vchsvcj if "+jagranProgramLists.get(j).toString());

                    if (jagranProgramLists1.get(i).getTitle().equalsIgnoreCase(jagranProgramLists.get(j).getProgram_title())){

                        jagranProgramLists.get(j).setSelect(true);
                        jagranProgramLists.get(j).setImage(jagranProgramLists1.get(i).getImage());
                        jagranProgramLists.get(j).setCurrent_present(jagranProgramLists1.get(i).getCurrent_present());


                    }
                }
            }
        }

    }


    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        ToastUtils.shortToast(t.getMessage());
        Log.d(TAG, "onFailure: "+requestCode+" "+t.getMessage());
        ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());

    }

    @Override
    public void onNoNetwork(int requestCode) {
        ProgressDialogFragment.dismissProgress(((AppCompatActivity) context).getSupportFragmentManager());


    }


    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(JagaranProgramAdd.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String dateString =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
//                String dateString =String.valueOf(monthOfYear + 1)+ "-"+ String.valueOf(year) ;
                binding.tvDate.setText(dateString);
//                program_date=dateString;
                Log.d("TAG", "onDateSet: "+dateString);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    private void openCameraToClick(int pic_id) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(camera_intent, pic_id);
        someActivityResultLauncher.launch(camera_intent);
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        if (data!=null){

                                Bitmap mybpm = (Bitmap) data.getExtras().get("data");
//                            photoToiletString= ImageUtils.convertBitmapIntoBase64(photoToilet);
                                images= ImageUtils.convertBitmapIntoBase64(mybpm);
//                               imgList.add(new ImageList(images));
                                uploadImg("/jagran/jp",images);
                        }
                    }
                }
            });

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);;
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        imageurl = image.getAbsolutePath();

        Log.d("TAG", "createImageFile: hghg "+imageurl+"\n ");

        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode){
                case IMAGE_JAGARAN_DATA:
                    if (data!=null){
                        Log.d("TAG", "onActivityResult: sw "+data);
                        if (resultCode == Activity.RESULT_OK) {
                            Bitmap mybpm = (Bitmap) data.getExtras().get("data");
//                            photoToiletString= ImageUtils.convertBitmapIntoBase64(photoToilet);

                            images= ImageUtils.convertBitmapIntoBase64(mybpm);
//                               imgList.add(new ImageList(images));
                            uploadImg("/jagran/jp",images);
                        }
                    }

                    break;
            }
        }catch (Exception e){
            Log.d("TAG", "onActivityResult: "+e.getMessage());
        }
    }
    private void getAllList() {
        UserService.getOccupationList(this, ArogyaApplication.getCurrentLocale(this),this,GET_ALL_LIST);
    }

    @Override
    public void onItemClick(JagranProgramList jgranProgram,boolean data) {
        jgranProgram.setSelect(data);
        listId= Integer.parseInt(jgranProgram.getId());
        Log.d(TAG, "onItemClick: ");
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            openCameraToClick(IMAGE_JAGARAN_DATA);
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


//        jagranProgramListAdapter.notifyDataSetChanged();
    }
}