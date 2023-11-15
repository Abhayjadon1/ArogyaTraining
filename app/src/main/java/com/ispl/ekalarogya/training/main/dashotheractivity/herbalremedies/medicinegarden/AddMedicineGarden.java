package com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.medicinegarden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.PlanListAdapter;
import com.ispl.ekalarogya.training.adapters.PlantListSlectedAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.MedicineGardenModel;
import com.ispl.ekalarogya.training.models.PlantModel;
import com.ispl.ekalarogya.training.models.PlantModel2;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class  AddMedicineGarden extends AppCompatActivity implements RetroAPICallback,PlanListAdapter.OnItemClickListner , PlantListSlectedAdapter.OnItemClickListner {
    @BindView(R.id.tv_family_head)
    TextView tv_family_head;
    @BindView(R.id.tv_family_id)
    TextView tv_family_id;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.selecteRv)
    RecyclerView selecteRv;
    @BindView(R.id.rv_plant_listdata)
    RecyclerView rv_plant_listdata;

    @BindView(R.id.ll_forPlantation)
    LinearLayout ll_forPlantation;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    boolean isEdit=false;
    private static final int UPLOAD_IMAGE=123;

    int indexId,plantCount=0,listId;
    String v_id,status="0",head_id,plant_id="",date,garden_id="",plant_img="", plant_count="";
    private static final String TAG = AddMedicineGarden.class.getSimpleName();
    PlanListAdapter planListAdapter;
    Bitmap photoPosterClick;
    String photos,type;
    PlantListSlectedAdapter plantListSlectedAdapter;
    List<PlantModel2> plankModelList2 = new ArrayList<>();
    List<PlantModel> plankModelList1 = new ArrayList<>();
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private final static int GET_ALL_LIST=111;
    private final static int ADD_PANTATION_LIST=151;
//    private final static int GET_ALL_LIST=111;

    File photoFile=null;
    VillageFamilyModel h_data;
    MedicineGardenModel m_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine_garden);
        ButterKnife.bind(this);
//        Log.d(TAG, "onCreate: ++ : "+getIntent().getStringExtra("type")+"   "+getIntent().hasExtra("type"));
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            switch (type){
                case "add":
                    isEdit=true;
                    v_id=i.getStringExtra("v_id");
                    head_id=i.getStringExtra("h_id");
                    h_data=i.getParcelableExtra("h_data");
                    setupForAddData(h_data);
                    tv_title.setText(R.string.medicine_garden);
                    break;
                case "view":
//                    data=getIntent().getParcelableExtra("m_data");
//                    tv_title.setText("Anemia Checkup result");
                    Log.d("TAG", "onCreate: getintent view ");
                    isEdit=false;
                    m_data=i.getParcelableExtra("m_data");
//                    viewMemberDetails(head_id,member_id);
//                    setupForUpdateData(data);
                    break;
                case "edit":
//                    status="1";
                    Log.d("TAG", "onCreate: getintent edit ");
                    isEdit=true;
//                    v_id=i.getStringExtra("vid");
                    m_data=i.getParcelableExtra("m_data");

//                    m_data=getIntent().getParcelableExtra("m_data");
                    setupForUpdateData(m_data);
                    break;

            }
        }else{
            ToastUtils.shortToast(getResources().getString(R.string.no_data));
            finish();
        }

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rv_plant_listdata.setLayoutManager(layoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        selecteRv.setLayoutManager(gridLayoutManager);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AddMedicineGarden.this,SingleFamilyGarden.class));
                finish();
            }
        });
        getAllList();
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
                    addData();
                }
            }
        });

    }

    private boolean validate() {
        if (tv_date.getText().toString()==null){
            return false;
        }
        Log.d(TAG, "validate:size "+plankModelList1.size());

        Log.d(TAG, "validate: plantModel Print : "+plankModelList1.toString());
        //status,v_id,head_id,plant_id,date,garden_id,plant_img
//       try {
        plant_id=null;
        plant_img=null;
        ArrayList<String> newArray = new ArrayList<>();
           for (int i=0;i<plankModelList1.size();i++){
//            Log.d(TAG, "validate:plant_id "+plankModelList1.get(i).getId()+"     plantList: "+plant_id);
               if (plankModelList1.get(i).isSelect()&&plankModelList1.get(i).getPlant_img()!=null){
                   Log.d(TAG, "validate: plant id  in if"+plankModelList1.get(i).getId());

                   if (plant_id==null) {
                       plant_id =""+plankModelList1.get(i).getId();
                       plant_count =""+plankModelList1.get(i).getCount();
                       newArray.add(plankModelList1.get(i).getPlant_img());
                   }else{
                       plant_id = plant_id + "," + plankModelList1.get(i).getId();
                       plant_count = plant_count + "," + plankModelList1.get(i).getCount();
                       newArray.add(plankModelList1.get(i).getPlant_img());
                       Log.d(TAG, "validate: plant id+ " + plant_id);

                   }
               }
           }

        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(newArray).getAsJsonArray();
        plant_img=myCustomArray.toString();
//       }catch (Exception e){
//           Log.d(TAG, "validate: Exception "+e.getMessage());
//       }

//        for (int i=0;i<plankModelList1.size();i++){
//            Log.d(TAG, "validate:plant_img  "+plankModelList1.get(i).getId());
//
//            if (plankModelList1.size()==indexId){
//                plant_img.concat(String.valueOf(plankModelList1.get(i).getId()));
//            }else {
//                plant_img.concat(plankModelList1.get(i).getId()+",");
//
//            }
//        }
        if (date==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_week));
            return false;
        }
        date=tv_date.getText().toString();
        if (plant_id==null){
            ToastUtils.shortToast(getResources().getString(R.string.add_some_plant));
            return false;
        }
        if (plant_count==null){
            ToastUtils.shortToast(getResources().getString(R.string.add_plant_count));
            return false;
        }

        if (plant_img==null){
            ToastUtils.shortToast(getResources().getString(R.string.add_plant_images));
            return false;
        }

        Log.d(TAG, "validate: all  plant_id: "+plant_id+"       \nplant_img : "+plant_img+" done");
        return true;
    }

    public void getAllList(){
        UserService.getOccupationList(this, ArogyaApplication.getCurrentLocale(this),this,GET_ALL_LIST);
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case GET_ALL_LIST:
                if (response.isSuccessful()) {
                    GetAllListResponse getAllListResponse = (GetAllListResponse) response.body();
                    if (getAllListResponse != null) {
                        if (getAllListResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + getAllListResponse.getPlantModelList().size());
                            if(getAllListResponse.getPlantModelList().size()>0) {
//                                rv_plant_listdata.setHasFixedSize(true);
                                plankModelList1.addAll(getAllListResponse.getPlantModelList());
                                planListAdapter = new PlanListAdapter(AddMedicineGarden.this, getAllListResponse.getPlantModelList(),this);
                                rv_plant_listdata.setAdapter(planListAdapter);

                                setupForUpdateData(m_data);

//                                plankModelList1=List.copyOf(plankModelList1);
//                                Collections.copy(Arrays.asList(plankModelList1),Arrays.asList(plankModelList1.toArray()));
//                                for (int i=0;i<plankModelList1.size();i++){
//                                    plankModelList1.add(new PlantModel2(
//                                            plankModelList1.get(i).getId(),
//                                            plankModelList1.get(i).getPlant_name(),
//                                            plankModelList1.get(i).getPlant_img(),
//                                            plankModelList1.get(i).getImage(),
//                                            plankModelList1.get(i).getCount(),
//                                            plankModelList1.get(i).isSelect()
//                                    ));
//                                }

//                                Log.d(TAG, "onResponse: setadpetr"+ getAllListResponse.getPlantModelList().toString());
                                Log.d(TAG, "onResponse: setadpetr"+ plankModelList1.toString());
                            }
                        }else {
                            ToastUtils.shortToast(getAllListResponse.getMessage());
                        }
                    }
                }
                break;
            case UPLOAD_IMAGE:
                if(response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            imgList.add(new ImageList(baseResponse.getInspection_img()));
                            Log.d(TAG, "onResponse: "+plantCount+listId);
                           for (int i=0;i<plankModelList1.size();i++){
                               if (listId==plankModelList1.get(i).getId()){
                                   plankModelList1.get(i).setPlant_img(baseResponse.getInspection_img());
//                                   plankModelList1.get(i).setCount(plantCount);
                                   Log.d(TAG, "onResponse: from if "+ plankModelList1.get(i).toString());
                               }
                           }

                           showHidePlantation();
//                            plantListSlectedAdapter.notifyDataSetChanged();
                        }
                    }
                }
                break;
            case ADD_PANTATION_LIST:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            finish();
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                        }else {
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
        ToastUtils.shortToast(getResources().getString(R.string.no_internet_connection));
    }

    public  void addData(){
        UserService.addMedicineGarden(this,status,v_id,head_id,plant_id, plant_count,date,plant_img,garden_id,this,ADD_PANTATION_LIST);
    }
    private void setupForAddData(VillageFamilyModel data) {
        try {
            head_id=data.getFamily_head_id();

           tv_family_head.setText(data.getFamily_head());
           tv_family_id.setText(data.getFamily_head_id());
//           tv_title.setText("Add");
        }catch (Exception e){
            Log.d(TAG, "setupForAddData: exception errror"+e.getMessage());
        }
    }
    private void setupForUpdateData(MedicineGardenModel data) {
        try {
            head_id=data.getHead_id();
            date=data.getMonth();
            v_id=data.getVillage_id();
            tv_family_head.setText(data.getName());
            tv_family_id.setText(data.getHead_id());
            garden_id=data.getId();
            status="1";
            tv_date.setText(data.getMonth_value());
//           tv_title.setText("Add");
        }catch (Exception e){
            Log.d(TAG, "setupForAddData: exception errror"+e.getMessage());
        }

//        plankModelList1=data.getPlantModelList();

//        plantListSlectedAdapter.notifyDataSetChanged();
//        planListAdapter.notifyDataSetChanged();
//        Log.d(TAG, "setupForUpdateData: +"+data.toString());

        if (plankModelList1!=null&&plankModelList1.size()>0&&
                data!=null&&
                data.getPlantModelList().size()>0){
//            plankModelList1=data.getPlantModelList();
            for (int i=0;i<plankModelList1.size();i++){
               for (int j=0;j<data.getPlantModelList().size();j++){
                   if (plankModelList1.get(i).getPlant_name().equalsIgnoreCase(data.getPlantModelList().get(j).getPlant_name())){
                       plankModelList1.get(i).setCount(data.getPlantModelList().get(j).getCount());
                       plankModelList1.get(i).setPlant_img(data.getPlantModelList().get(j).getPlant_img());
                       plankModelList1.get(i).setSelect(true);
                   }
               }
            }
            planListAdapter = new PlanListAdapter(AddMedicineGarden.this, plankModelList1,this);
            rv_plant_listdata.setAdapter(planListAdapter);
            planListAdapter.notifyDataSetChanged();

            plantListSlectedAdapter=new PlantListSlectedAdapter(this,plankModelList1,this);
            selecteRv.setAdapter(plantListSlectedAdapter);
            plantListSlectedAdapter.notifyDataSetChanged();

            Log.d(TAG, "setupForUpdateData >: +"+plankModelList1.toString());
//            ?////
        }else{

        }
    }

    private void setupGardenData(VillageFamilyModel data) {
        if(!isEdit){
            Log.d("TAG", "setupMemberData: onCreate");
            tv_date.setEnabled(false);
            rv_plant_listdata.setEnabled(false);
            selecteRv.setEnabled(false);
            ll_forPlantation.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }
        try {
            tv_family_head.setText(data.getFamily_head());
            tv_family_id.setText(data.getFamily_head_id());
//            tv_date.setText(data.getDate());
        }catch (Exception e){
            Log.d(TAG, "setupGardenData: exception errror"+e.getMessage());
        }


    }



    private void showHidePlantation() {
        Log.d(TAG, "showHidePlantation: "+plankModelList1.size());
        selecteRv.setHasFixedSize(true);
        plantListSlectedAdapter = new PlantListSlectedAdapter(AddMedicineGarden.this, plankModelList1,this);
        selecteRv.setAdapter(plantListSlectedAdapter);
        plantListSlectedAdapter.notifyDataSetChanged();
        Log.d(TAG, "showHidePlantation: "+plankModelList1.get(indexId).toString());
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
//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                        try {
//                            if(resultCode == Activity.RESULT_OK) {
//                                photoPosterClick = (Bitmap) data.getExtras().get("data");
//                                photos= ImageUtils.convertBitmapIntoBase64(photoPosterClick);
//                                Log.d("TAG", "onActivityResult:photoMemberString "+"photos"+"     indexId : "+indexId);
////                mArrayUri.add(photos);
//                                uploadImg("/herbal_remedies/medicine_garden/plant",photos);
//                            }else{
//                                ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
////                     plantListSlectedAdapter.notifyDataSetChanged();
//                            }
//                        }catch (Exception e){
////                 plantListSlectedAdapter.notifyDataSetChanged();
//                            Log.d(TAG, "onActivityResult: "+e.getMessage());
//                        }
//                    }
//                }
//            });


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_ID_MEMBER_DIALOG:
             try {
                 if(resultCode == Activity.RESULT_OK) {
                         Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                         Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                         myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);
                         photos=ImageUtils.convertBitmapIntoBase64(myBitmap);

                     Log.d("TAG", "onActivityResult:photoMemberString "+"photos"+"     indexId : "+indexId);
//                mArrayUri.add(photos);
                     uploadImg("/herbal_remedies/medicine_garden/plant",photos);
                 }else{
                     ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
//                     plantListSlectedAdapter.notifyDataSetChanged();
                 }
             }catch (Exception e){
//                 plantListSlectedAdapter.notifyDataSetChanged();
                 Log.d(TAG, "onActivityResult: "+e.getMessage());
             }
                break;
        }
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AddMedicineGarden.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                String dateString =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                String dateString =String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear + 1)+ "-"+ String.valueOf(year) ;
                tv_date.setText(dateString);
                date=dateString;
                Log.d("TAG", "onDateSet: "+dateString);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();

//        MonthYearPickerDialog dialog = new MonthYearPickerDialog();
//        dialog.setListener(new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
////                selectYear = year + "";
////                selectMonth = (month) + "";
////                tvDate.setText(selectYear + "-" + selectMonth + "-01");
//                tv_date.setText(month+"-"+year);
//                date=month+"-"+year;
//                Log.d("TAG", "onDateSet: "+month+"-"+year);
//            }
//        });
//        dialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
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





    @Override
    public void onItemClick(PlantModel plantModel) {
        Log.d(TAG, "onItemClick: PlantModel 2 ");
        listId = plantModel.getId();
//        indexId=position;
//        plantCount=count;


//        Log.d(TAG, "onItemClick: "+listId+"  indexId: "+indexId+"  data: "+position+"  type: "+type+"  Count :"+count);
//        PlantModel.setCount(count);

//        if (type.equalsIgnoreCase("plantList")) {
////            showHidePlantation();
//        }
//        if (type.equalsIgnoreCase("PIC")){
            photoPosterClick=null;
            photos=null;
            dexterPerm(PIC_ID_MEMBER_DIALOG);
            Log.d(TAG, "onItemClick: pic "+"id "+listId+"     " +photos);
        Log.d(TAG, "onItemClick: "+plankModelList1.toString());
    }

    @Override
    public void onItemClick(PlantModel PlantModel, String type, int data) {
        Log.d(TAG, "onItemClick: PlantModel  ");
        listId = PlantModel.getId();
        indexId=data;
//        plantCount=count;


        Log.d(TAG, "onItemClick: "+listId+"  indexId: "+indexId+"  data: "+data+"  type: "+type);


        if (type.equalsIgnoreCase("plantList")) {
            showHidePlantation();
        }
        if (type.equalsIgnoreCase("PIC")){
            dexterPerm(PIC_ID_MEMBER_DIALOG);
            Log.d(TAG, "onItemClick: pic "+"id "+listId+"     " +photos);}
        Log.d(TAG, "onItemClick: "+plankModelList1.toString());
    }
}