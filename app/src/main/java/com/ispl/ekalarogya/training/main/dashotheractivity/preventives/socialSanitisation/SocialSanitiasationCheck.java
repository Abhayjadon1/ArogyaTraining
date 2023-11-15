package com.ispl.ekalarogya.training.main.dashotheractivity.preventives.socialSanitisation;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.SocialSanitisationModel;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.SocialSanitisationDetailResponse;
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

public class SocialSanitiasationCheck extends AppCompatActivity implements View.OnClickListener, RetroAPICallback {
    String head_id,sanitizeId,name,village_id;
    @BindView(R.id.et_inspection_date)
    TextView et_inspection_date;
    @BindView(R.id.cb_soakpit_no)
    CheckBox cb_soakpit_no;
    @BindView(R.id.cb_soakpit_yes)
    CheckBox cb_soakpit_yes;
    @BindView(R.id.cb_toilet_no)
    CheckBox cb_toilet_no;
    @BindView(R.id.cb_toilet_yes)
    CheckBox cb_toilet_yes;
    @BindView(R.id.cb_waterTreatment_no)
    CheckBox cb_waterTreatment_no;
    @BindView(R.id.cb_waterTreatment_yes)
    CheckBox cb_waterTreatment_yes;
    @BindView(R.id.cb_compost_no)
    CheckBox cb_compost_no;
    @BindView(R.id.cb_compost_yes)
    CheckBox cb_compost_yes;

    @BindView(R.id.cb_wall_no)
    CheckBox cb_wall_no;
    @BindView(R.id.cb_wall_yes)
    CheckBox cb_wall_yes;

    @BindView(R.id.cb_noPeople_no)
    CheckBox cb_noPeople_no;
    @BindView(R.id.cb_noPeople_yes)
    CheckBox cb_noPeople_yes;

    @BindView(R.id.cb_medicinePlant_no)
    CheckBox cb_medicinePlant_no;
    @BindView(R.id.cb_medicinePlant_yes)
    CheckBox cb_medicinePlant_yes;

    @BindView(R.id.cb_nutrition_plantation_no)
    CheckBox cb_nutrition_plantation_no;
    @BindView(R.id.cb_nutrition_plantation_yes)
    CheckBox cb_nutrition_plantation_yes;

    @BindView(R.id.cb_filterUses_no)
    CheckBox cb_filterUses_no;
    @BindView(R.id.cb_filterUses_yes)
    CheckBox cb_filterUses_yes;

    @BindView(R.id.cb_tree_planting_no)
    CheckBox cb_tree_planting_no;
    @BindView(R.id.cb_tree_planting_yes)
    CheckBox cb_tree_planting_yes;


    File photoFile=null;


    @BindView(R.id.iv_back)
    ImageView iv_back;


    @BindView(R.id.iv_compost_image)
    ImageView iv_compost_image;
    @BindView(R.id.iv_toilet_image)
    ImageView iv_toilet_image;
    @BindView(R.id.iv_waterTreatment_image)
    ImageView iv_waterTreatment_image;
    @BindView(R.id.iv_soakpit_image)
    ImageView iv_soakpit_image;
    @BindView(R.id.iv_wallWriting_image)
    ImageView iv_wallWriting_image;
    @BindView(R.id.iv_noofpeple_image)
    ImageView iv_noofpeple_image;
    @BindView(R.id.iv_medicinePlant_image)
    ImageView iv_medicinePlant_image;
    @BindView(R.id.iv_nutrition_plantation_image)
    ImageView iv_nutrition_plantation_image;
    @BindView(R.id.iv_filterUses_image)
    ImageView iv_filterUses_image;
    @BindView(R.id.iv_tree_planting_image)
    ImageView iv_tree_planting_image;





    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.memeber_name)
    TextView memeber_name;
    Boolean isEdit=false;
    
    private static final int VIEW_SS_CHEKUP_DATA=111;
    private static final int ADD_SS_CHEKUP_DATA=131;
    private static final int PIC_ID_TOILET=115;
    private static final int PIC_ID_WATER=151;
    private static final int PIC_ID_COMPOST=172;
    private static final int PIC_ID_SOAKPIT=117;
    private static final int PIC_ID_WALL=118;
    private static final int  PIC_ID_NOOFPEOPLE =119;
    private static final int  PIC_ID_MEDICINE  =120;
    private static final int PIC_ID_TREE_PLANTING  =121;
    private static final int  PIC_ID_NUTRITION  =122;
    private static final int  PIC_ID_FILTER_USGAE  =123;

    Bitmap photoSoakpit,photoCompost,photoWaterTreatment,photoToilet,photoWall,photoPeople,photoMedicine,photoNutrition,photoFilter,photoTree;
    String photoSoakpitString,photoCompostString,photoWaterTreatmentString,photoToiletString,photoWallString,photoNoOfString,photoMedicineString,photoNutritionString,photoFilterUsageString,photoTreeString,toilet,soakpit,compost,waterTreat,status="0",dateSubmit,sanitation_id,wall,no_ofPeople="0",medicinePLsnt,nutrituionPlant,treePlant,filterUsage;

    VillageFamilyModel data;
    SocialSanitisationModel dataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_sanitiasation_check);
        ButterKnife.bind(this);




        if(getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            Log.d("TAG", "onCreate: "+"typpepe");
            head_id=i.getStringExtra("h_id");
            switch (getIntent().getStringExtra("type")){
                case "view":
                    status="1";
                    dataModel=getIntent().getParcelableExtra("m_data");
                    sanitizeId=dataModel.getId();//
                    Log.d("TAG", "onCreate: getintent "+"  view" + dataModel.toString());
                    getPersonalHygnCheckupDetail(sanitizeId);
                    break;
                case "edit":
                    isEdit=true;
                    status="1";
                    sanitizeId="10";
                    dataModel=getIntent().getParcelableExtra("m_data");
                    sanitizeId=dataModel.getId();//
                    Log.d("TAG", "onCreate: getintent "+"  edit" + dataModel.toString());
                    getPersonalHygnCheckupDetail(sanitizeId);
                    break;
                case "add":
                    isEdit=true;

                    //Add
                    status="0";
                    data=getIntent().getParcelableExtra("m_data");
                    head_id=data.getFamily_head_id();
                    village_id=getIntent().getStringExtra("v_id");
                    Log.d("TAG", "onCreate: getintent "+"  add" + data.toString()+ " villID"+village_id);

                    break;
            }
        }

        initview();
        checkData();
    }

    private void initview() {
        if(isEdit!=true){
            et_inspection_date.setEnabled(false);
            cb_compost_no.setEnabled(false);
            cb_compost_yes.setEnabled(false);

            cb_soakpit_no.setEnabled(false);
            cb_soakpit_yes.setEnabled(false);

            cb_toilet_no.setEnabled(false);
            cb_toilet_yes.setEnabled(false);

            cb_waterTreatment_no.setEnabled(false);
            cb_waterTreatment_yes.setEnabled(false);

            cb_wall_yes.setEnabled(false);
            cb_wall_no.setEnabled(false);

            cb_noPeople_yes.setEnabled(false);
            cb_noPeople_no.setEnabled(false);

            cb_medicinePlant_yes.setEnabled(false);
            cb_medicinePlant_no.setEnabled(false);

            cb_nutrition_plantation_yes.setEnabled(false);
            cb_nutrition_plantation_no.setEnabled(false);

            cb_filterUses_yes.setEnabled(false);
            cb_filterUses_no.setEnabled(false);

            cb_tree_planting_yes.setEnabled(false);
            cb_tree_planting_no.setEnabled(false);

            iv_compost_image.setEnabled(false);
            iv_soakpit_image.setEnabled(false);
            iv_toilet_image.setEnabled(false);
            iv_waterTreatment_image.setEnabled(false);
            iv_wallWriting_image.setEnabled(false);
            iv_nutrition_plantation_image.setEnabled(false);
            iv_medicinePlant_image.setEnabled(false);
            iv_filterUses_image.setEnabled(false);
            iv_tree_planting_image.setEnabled(false);

            btn_submit.setVisibility(View.GONE);
        }
        try{
            if (data!=null){
                name=data.getFamily_head();
            }else{
                name=dataModel.getHead_name();
            }
            memeber_name.setText(name);
        }catch (Exception e){
            Log.d("TAG", "initview: "+e.getMessage());
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitiasationCheck.this, SocialSanitisation.class));
                finish();
            }
        });
        cb_compost_no.setOnClickListener(this);
        cb_compost_yes.setOnClickListener(this);
        cb_soakpit_no.setOnClickListener(this);
        cb_soakpit_yes.setOnClickListener(this);
        cb_toilet_no.setOnClickListener(this);
        cb_toilet_yes.setOnClickListener(this);
        cb_toilet_yes.setOnClickListener(this);
        cb_waterTreatment_no.setOnClickListener(this);
        cb_waterTreatment_yes.setOnClickListener(this);
        cb_filterUses_no.setOnClickListener(this);
        cb_filterUses_yes.setOnClickListener(this);
        cb_medicinePlant_no.setOnClickListener(this);
        cb_medicinePlant_yes.setOnClickListener(this);
        cb_noPeople_no.setOnClickListener(this);
        cb_noPeople_yes.setOnClickListener(this);
        cb_nutrition_plantation_no.setOnClickListener(this);
        cb_nutrition_plantation_yes.setOnClickListener(this);
        cb_tree_planting_no.setOnClickListener(this);
        cb_tree_planting_yes.setOnClickListener(this);
        cb_wall_no.setOnClickListener(this);
        cb_wall_yes.setOnClickListener(this);


        iv_compost_image.setOnClickListener(this);
        iv_soakpit_image.setOnClickListener(this);
        iv_toilet_image.setOnClickListener(this);
        iv_waterTreatment_image.setOnClickListener(this);
        iv_wallWriting_image.setOnClickListener(this);
        iv_noofpeple_image.setOnClickListener(this);
        iv_medicinePlant_image.setOnClickListener(this);
        iv_nutrition_plantation_image.setOnClickListener(this);
        iv_filterUses_image.setOnClickListener(this);
        iv_tree_planting_image.setOnClickListener(this);

        btn_submit.setOnClickListener(this);
    }

    private void getPersonalHygnCheckupDetail(String head_id) {
        UserService.getSocialSanitisationDetails(this,head_id,this,VIEW_SS_CHEKUP_DATA);
    }
    private void addPersonalHygnCheckupDetail() {
        UserService.addSocialSanitisation(this,village_id,head_id,soakpit,photoSoakpitString,compost,toilet,waterTreat,photoToiletString,wall,no_ofPeople="0",medicinePLsnt,nutrituionPlant,filterUsage,treePlant,photoWallString,photoNoOfString,photoMedicineString,photoNutritionString,photoFilterUsageString,photoTreeString,photoWaterTreatmentString,photoCompostString,dateSubmit,sanitation_id,status,this,ADD_SS_CHEKUP_DATA);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cb_compost_no:
                compost="0";
                cb_compost_no.setChecked(true);
                cb_compost_yes.setChecked(false);
                break;
            case R.id.cb_compost_yes:
                compost="1";
                cb_compost_yes.setChecked(true);
                cb_compost_no.setChecked(false);
                break;
            case R.id.cb_toilet_no:
                toilet="0";
                cb_toilet_yes.setChecked(false);
                cb_toilet_no.setChecked(true);
                break;
            case R.id.cb_toilet_yes:
                toilet="1";
                cb_toilet_yes.setChecked(true);
                cb_toilet_no.setChecked(false);
                break;
            case R.id.cb_soakpit_no:
                soakpit="0";
                cb_soakpit_yes.setChecked(false);
                cb_soakpit_no.setChecked(true);
                break;
            case R.id.cb_soakpit_yes:
                soakpit="1";
                cb_soakpit_yes.setChecked(true);
                cb_soakpit_no.setChecked(false);
                break;
            case R.id.cb_waterTreatment_no:
                waterTreat="0";
                cb_waterTreatment_yes.setChecked(false);
                cb_waterTreatment_no.setChecked(true);
                break;
            case R.id.cb_waterTreatment_yes:
                waterTreat="1";
                cb_waterTreatment_yes.setChecked(true);
                cb_waterTreatment_no.setChecked(false);
                break;
            case R.id.cb_wall_no:
                wall="0";
                cb_wall_yes.setChecked(false);
                cb_wall_no.setChecked(true);
                break;
            case R.id.cb_wall_yes:
                wall="1";//
                cb_wall_yes.setChecked(true);
                cb_wall_no.setChecked(false);
                break;
            case R.id.cb_noPeople_no:
                no_ofPeople="0";
                cb_noPeople_yes.setChecked(false);
                cb_noPeople_no.setChecked(true);
                break;
            case R.id.cb_noPeople_yes:
                no_ofPeople="1";
                cb_noPeople_yes.setChecked(true);
                cb_noPeople_no.setChecked(false);
                break;
            case R.id.cb_medicinePlant_no:
                medicinePLsnt="0";
                cb_medicinePlant_yes.setChecked(false);
                cb_medicinePlant_no.setChecked(true);
                break;
            case R.id.cb_medicinePlant_yes:
                medicinePLsnt="1";
                cb_medicinePlant_yes.setChecked(true);
                cb_medicinePlant_no.setChecked(false);
                break;
            case R.id.cb_nutrition_plantation_no:
                nutrituionPlant="0";
                cb_nutrition_plantation_yes.setChecked(false);
                cb_nutrition_plantation_no.setChecked(true);
                break;
            case R.id.cb_nutrition_plantation_yes:
                nutrituionPlant="1";
                cb_nutrition_plantation_yes.setChecked(true);
                cb_nutrition_plantation_no.setChecked(false);
                break;
            case R.id.cb_filterUses_no:
                filterUsage="0";
                cb_filterUses_yes.setChecked(false);
                cb_filterUses_no.setChecked(true);
                break;
            case R.id.cb_filterUses_yes:
                filterUsage="1";
                cb_filterUses_yes.setChecked(true);
                cb_filterUses_no.setChecked(false);
                break;
            case R.id.cb_tree_planting_no:
                treePlant="0";
                cb_tree_planting_yes.setChecked(false);
                cb_tree_planting_no.setChecked(true);
                break;
            case R.id.cb_tree_planting_yes:
                treePlant="1";
                cb_tree_planting_yes.setChecked(true);
                cb_tree_planting_no.setChecked(false);
                break;

                
            case R.id.iv_toilet_image:
                 dexterPerm(PIC_ID_TOILET);
                break;
            case R.id.iv_soakpit_image:
                 dexterPerm(PIC_ID_SOAKPIT);
                break;
            case R.id.iv_waterTreatment_image:
                 dexterPerm(PIC_ID_WATER);
                break;
            case R.id.iv_compost_image:
                 dexterPerm(PIC_ID_COMPOST);
                break;
            case R.id.iv_wallWriting_image:
                 dexterPerm(PIC_ID_WALL);//
                break;
            case R.id.iv_noofpeple_image:
                 dexterPerm(PIC_ID_NOOFPEOPLE);
                break;
            case R.id.iv_medicinePlant_image:
                 dexterPerm(PIC_ID_MEDICINE);
                break;
            case R.id.iv_nutrition_plantation_image:
                 dexterPerm(PIC_ID_NUTRITION);
                break;
            case R.id.iv_filterUses_image:
                 dexterPerm(PIC_ID_FILTER_USGAE);
                break;
            case R.id.iv_tree_planting_image:
                 dexterPerm(PIC_ID_TREE_PLANTING);
                break;

            case R.id.et_inspection_date:
                getDateDialog();
                break;
            case R.id.btn_submit:
//                if(toilet!=null&&soakpit!=null&&compost!=null&&waterTreat!=null&&head_id!=null&&village_id!=null&&photoCompostString!=null&&photoToiletString!=null&&photoWaterTreatmentString!=null&&photoSoakpitString!=null){
                    if(validate()){
                        addPersonalHygnCheckupDetail();
//                    }
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
                }
                break;

        }

    }

    public void checkData(){
        if(cb_compost_yes.isChecked()){
            compost="1";
        }else {
            compost="0";
        }
        if(cb_toilet_yes.isChecked()){
            toilet="1";
        }else {
            toilet="0";
        }
        if(cb_waterTreatment_yes.isChecked()){
            waterTreat="1";
        }else {
            waterTreat="0";
        }
        if(cb_soakpit_yes.isChecked()){
            soakpit="1";
        }else {
            soakpit="0";
        }

    }

    private boolean validate() {
        if(dateSubmit==null){
            ToastUtils.shortToast(getResources().getString(R.string.enter_date));
            return false;
        }
        if(soakpit!=null&&soakpit.equalsIgnoreCase("1")) {
            if (photoSoakpitString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }
        if(toilet!=null&&toilet.equalsIgnoreCase("1")) {
            if (photoToiletString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }
        if(waterTreat!=null&&waterTreat.equalsIgnoreCase("1")) {
            if (photoWaterTreatmentString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }
        if(compost!=null&&compost.equalsIgnoreCase("1")) {
            if (photoCompostString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }

        if(wall!=null&&wall.equalsIgnoreCase("1")||wall.equalsIgnoreCase("yes")) {
            if (photoWallString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }

        if(no_ofPeople!=null&&no_ofPeople.equalsIgnoreCase("1")||no_ofPeople.equalsIgnoreCase("yes")) {
            if (photoNoOfString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }

        if(medicinePLsnt!=null&&medicinePLsnt.equalsIgnoreCase("1")||medicinePLsnt.equalsIgnoreCase("yes")) {
            if (photoMedicineString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }

        if(nutrituionPlant!=null&&nutrituionPlant.equalsIgnoreCase("1")||nutrituionPlant.equalsIgnoreCase("yes")) {
            if (photoNutritionString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));

                return false;
            }
        }

        if(filterUsage!=null&&filterUsage.equalsIgnoreCase("1")||filterUsage.equalsIgnoreCase("yes")) {
            if (photoFilterUsageString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }

        if(treePlant!=null&&treePlant.equalsIgnoreCase("1")||treePlant.equalsIgnoreCase("yes")) {
            if (photoTreeString == null) {
                ToastUtils.shortToast(getResources().getString(R.string.add_image));
                return false;
            }
        }



        if (waterTreat!=null&&waterTreat.equalsIgnoreCase("Yes")||waterTreat.equalsIgnoreCase("1")){
            waterTreat="1";
            ;
        }else{
            waterTreat="0";
            cb_waterTreatment_no.setChecked(true);
        }
        if (soakpit!=null&&soakpit.contains("Yes")||soakpit.equalsIgnoreCase("1")){
            soakpit="1";
            cb_soakpit_yes.setChecked(true);
        }else{
            soakpit="0";
            cb_soakpit_no.setChecked(true);
        }
        if (compost!=null&&compost.equalsIgnoreCase("Yes")||compost.equalsIgnoreCase("1")){
            compost="1";
            cb_compost_yes.setChecked(true);
        }else{
            compost="0";
            cb_compost_no.setChecked(true);
        }
        if (toilet!=null&&toilet.equalsIgnoreCase("Yes")||toilet.equalsIgnoreCase("1")){
            toilet="1";
            cb_toilet_yes.setChecked(true);
        }else{
            toilet="0";
            cb_toilet_no.setChecked(true);
        }


        return true;
    }

    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(SocialSanitiasationCheck.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                et_inspection_date.setText(date);
                dateSubmit=date;
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
        photoNoOfString = image.getAbsolutePath();
        return image;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       try{
           switch (requestCode){
               case PIC_ID_TOILET:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);
                       photoToiletString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoMemberString "+photoToiletString);
                       iv_toilet_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }

                   break;
               case PIC_ID_COMPOST:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoCompostString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoMemberString "+photoCompostString);
                       iv_compost_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }

                   break;
               case PIC_ID_WATER:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoWaterTreatmentString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoMemberString "+photoWaterTreatmentString);
                       iv_waterTreatment_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }

                   break;
               case PIC_ID_SOAKPIT:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoSoakpitString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoMemberString "+photoSoakpitString);
                       iv_soakpit_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }

                   break;
               case PIC_ID_WALL:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoWallString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoMemberString "+photoWallString);
                       iv_wallWriting_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }
                   break;
               case PIC_ID_NOOFPEOPLE:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoNoOfString= ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoHeadString "+photoNoOfString);
                       iv_noofpeple_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }
                   break;
               case PIC_ID_MEDICINE:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoMedicineString= ImageUtils.convertBitmapIntoBase64(myBitmap);
                       Log.d("TAG", "onActivityResult:photoHeadString "+photoMedicineString);
                       iv_medicinePlant_image.setImageBitmap(photoMedicine);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }
                   break;
               case PIC_ID_NUTRITION:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoNutritionString= ImageUtils.convertBitmapIntoBase64(myBitmap);
//                       photoNutritionString= ImageUtils.convertBitmapIntoBase64(photoNutrition);
                       Log.d("TAG", "onActivityResult:photoHeadString "+photoNutritionString);
                       iv_nutrition_plantation_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }
                   break;
               case PIC_ID_FILTER_USGAE:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoFilterUsageString= ImageUtils.convertBitmapIntoBase64(myBitmap);
//                       photoFilterUsageString= ImageUtils.convertBitmapIntoBase64(photoFilter);
                       Log.d("TAG", "onActivityResult:photoHeadString "+photoFilterUsageString);
                       iv_filterUses_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }
                   break;
               case PIC_ID_TREE_PLANTING:
                   if(resultCode == Activity.RESULT_OK) {
                       Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                       Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                       myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                       photoTreeString= ImageUtils.convertBitmapIntoBase64(myBitmap);
//                       photoTreeString= ImageUtils.convertBitmapIntoBase64(photoTree);
                       Log.d("TAG", "onActivityResult:photoHeadString "+photoTreeString);
                       iv_tree_planting_image.setImageBitmap(myBitmap);
                   }else{
                       ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                   }
                   break;
           }
       }catch (Exception e){
           Log.d("TAG", "onActivityResult: exception "+e.getMessage());
       }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case VIEW_SS_CHEKUP_DATA:
                if(response.isSuccessful()){
                    SocialSanitisationDetailResponse baseResponse=(SocialSanitisationDetailResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: Success");
                            memeber_name.setText(baseResponse.getSocialSanitisationData().getHead_name());
                            village_id=baseResponse.getSocialSanitisationData().getVillage_id();
                            head_id=baseResponse.getSocialSanitisationData().getHead_id();
                            dateSubmit=baseResponse.getSocialSanitisationData().getInspection_date();
                            try {
                                et_inspection_date.setText(baseResponse.getSocialSanitisationData().getInspection_date());
                            }catch (Exception e){

                            }

                            toilet=baseResponse.getSocialSanitisationData().getToilet();
                            sanitation_id=baseResponse.getSocialSanitisationData().getId();
                            compost=baseResponse.getSocialSanitisationData().getCompost_pit();
                            waterTreat=baseResponse.getSocialSanitisationData().getWater_treatment();
                            soakpit=baseResponse.getSocialSanitisationData().getSoakpit();

                            wall=baseResponse.getSocialSanitisationData().getWall_writing();
                            no_ofPeople="0";//baseResponse.getSocialSanitisationData().getPeople_use_toilet();
                            medicinePLsnt=baseResponse.getSocialSanitisationData().getMedicine_plantation();
                            nutrituionPlant=baseResponse.getSocialSanitisationData().getNutrition_plantation();
                            filterUsage=baseResponse.getSocialSanitisationData().getFilter_uses();
                            treePlant=baseResponse.getSocialSanitisationData().getTree_planting();

                            photoToiletString=baseResponse.getSocialSanitisationData().getToilet_photo();
                            photoCompostString=baseResponse.getSocialSanitisationData().getCompost_pit_photo();
                            photoWaterTreatmentString=baseResponse.getSocialSanitisationData().getWater_treatment_photo();
                            photoSoakpitString=baseResponse.getSocialSanitisationData().getSoakpit_photo();

                            photoWallString=baseResponse.getSocialSanitisationData().getWall_writing_photo();
                            photoNoOfString=baseResponse.getSocialSanitisationData().getPeople_use_toilet_photo();
                            photoMedicineString=baseResponse.getSocialSanitisationData().getMedicine_plantation_photo();
                            photoNutritionString=baseResponse.getSocialSanitisationData().getNutrition_plantation_photo();
                            photoFilterUsageString=baseResponse.getSocialSanitisationData().getFilter_uses_photo();
                            photoTreeString=baseResponse.getSocialSanitisationData().getTree_planting_photo();


                            if (waterTreat!=null&&waterTreat.equalsIgnoreCase("Yes")){
                                waterTreat="1";
                                cb_waterTreatment_yes.setChecked(true);
                            }else{
                                waterTreat="0";
                                cb_waterTreatment_no.setChecked(true);
                            }
                            if (soakpit!=null&&soakpit.contains("Yes")){
                                soakpit="1";
                                cb_soakpit_yes.setChecked(true);
                            }else{
                                soakpit="0";
                                cb_soakpit_no.setChecked(true);
                            }
                            if (compost!=null&&compost.equalsIgnoreCase("Yes")){
                                compost="1";
                                cb_compost_yes.setChecked(true);
                            }else{
                                compost="0";
                                cb_compost_no.setChecked(true);
                            }
                            if (toilet!=null&&toilet.equalsIgnoreCase("Yes")){
                                toilet="1";
                                cb_toilet_yes.setChecked(true);
                            }else{
                                toilet="0";
                                cb_toilet_no.setChecked(true);
                            }

                            if (wall!=null&&wall.equalsIgnoreCase("Yes")){
                                wall="1";
                                cb_wall_yes.setChecked(true);
                            }else{
                                wall="0";
                                cb_wall_no.setChecked(true);
                            }
                            if (no_ofPeople!=null&&no_ofPeople.equalsIgnoreCase("Yes")){
                                 no_ofPeople="1";
                                cb_noPeople_yes.setChecked(true);
                            }else{
                                 no_ofPeople="0";
                                 cb_noPeople_no.setChecked(true);
                            }
                            if (medicinePLsnt!=null&&medicinePLsnt.equalsIgnoreCase("Yes")){
                                medicinePLsnt="1";
                                cb_medicinePlant_yes.setChecked(true);
                            }else{
                                medicinePLsnt="0";
                                cb_medicinePlant_no.setChecked(true);
                            }
                            if (nutrituionPlant!=null&&nutrituionPlant.equalsIgnoreCase("Yes")){
                                nutrituionPlant="1";
                                cb_nutrition_plantation_yes.setChecked(true);
                            }else{
                                nutrituionPlant="0";
                                cb_nutrition_plantation_no.setChecked(true);
                            }
                            if (filterUsage!=null&&filterUsage.equalsIgnoreCase("Yes")){
                                filterUsage="1";
                                cb_filterUses_yes.setChecked(true);
                            }else{
                                filterUsage="0";
                                cb_filterUses_no.setChecked(true);
                            }
                             if (treePlant!=null&&treePlant.equalsIgnoreCase("Yes")){
                                 treePlant="1";
                                cb_tree_planting_yes.setChecked(true);
                            }else{
                                 treePlant="0";
                                 cb_tree_planting_no.setChecked(true);
                            }


                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getToilet_photo()).error(R.drawable.add_image).into(iv_toilet_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getCompost_pit_photo()).error(R.drawable.add_image).into(iv_compost_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getSoakpit_photo()).error(R.drawable.add_image).into(iv_soakpit_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getWater_treatment_photo()).error(R.drawable.add_image).into(iv_waterTreatment_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getWall_writing_photo()).error(R.drawable.add_image).into(iv_wallWriting_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getPeople_use_toilet_photo()).error(R.drawable.add_image).into(iv_noofpeple_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getMedicine_plantation()).error(R.drawable.add_image).into(iv_medicinePlant_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getNutrition_plantation_photo()).error(R.drawable.add_image).into(iv_nutrition_plantation_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getFilter_uses_photo()).error(R.drawable.add_image).into(iv_filterUses_image);
                            Picasso.with(this).load(ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getTree_planting_photo()).error(R.drawable.add_image).into(iv_tree_planting_image);

                            Log.d("TAG", "onResponse: "+ExtraUtils.baseImg+baseResponse.getSocialSanitisationData().getWater_treatment_photo());
//                            iv_health_image.setImageBitmap(ImageUtils.convertImagePathToBitmap(baseResponse.getPersonalHygieneCheckupResponse().getHealth_photo(),200,200));
//                            iv_nails_image.setImageBitmap(ImageUtils.convertImagePathToBitmap(baseResponse.getPersonalHygieneCheckupResponse().getNail_photo(),200,200));
                        }else {

                        }
                    }

                }
                break;
            case ADD_SS_CHEKUP_DATA:
                if(response.isSuccessful()) {
                    SocialSanitisationModel baseResponse = (SocialSanitisationModel) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: success");
                            ToastUtils.shortToast(baseResponse.getStatusMessage());
//                            startActivity(new Intent(SocialSanitiasationCheck.this,SocialSanitisation.class));
                            finish();
                        } else {
//                            ProgressDialogFragment.getInstance().dismiss();
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
        Log.d("TAG", "onFailure: "+t.getMessage());
    }

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

}