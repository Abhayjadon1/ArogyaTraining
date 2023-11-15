package com.ispl.ekalarogya.training.main.dashboardFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_base.BaseFragment;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.main.MainActivity;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

public class ProfileFrag extends BaseFragment implements RetroAPICallback {
    private static final String TAG = ProfileFrag.class.getSimpleName();
    private static final int DASHBOARD_REQ_CODE = 11;
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private static final int PIC_ID_HEAD_FAMILY = 111;
    private static final int ADD_PROFILE_DATA = 113;
    File photoFile = null;


@BindView(R.id.profile_img)
    CircleImageView profile_img;
@BindView(R.id.tvName)
    EditText tvName;
@BindView(R.id.tvEmail)
    EditText tvEmail;
@BindView(R.id.tvMob)
    EditText tvMob;

@BindView(R.id.radio_button_male)
    RadioButton radio_button_male;
@BindView(R.id.radio_button_female)
    RadioButton radio_button_female;

@BindView(R.id.onLine)
TextView onLine;
@BindView(R.id.btn_submit)
TextView btn_submit;

@BindView(R.id.tv_name)
TextView tv_name;
@BindView(R.id.editbtn)
TextView editbtn;
boolean isEdit=false;



    Bitmap photoHead,photoMember;
    String photoHeadString,photoMemberString,gender,aadhar_photo,aadharNumber,panPhoto,panNumber,accountNumber,accHolderName,bankName,bankBranch,ifscCode,bankRegisteredMobile,name,profile_imgString;

    public static void openProfile(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        ((AppCompatActivity) context).finish();
    }
    public static ProfileFrag newInstance() {

        Bundle args = new Bundle();

        ProfileFrag fragment = new ProfileFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.activity_scrolling;
    }
    private Context context;
//    @BindView(R.id.dashSwipeRefreshLayout)
//    SwipeRefreshLayout dashSwipeRefreshLayout;
//    @BindArray(R.array.swipe_refresh_color_scheme_array)
//    int[] swipeRefreshColorSchemeArray;



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this,view);
        initComponents(view);


    }

    public void initComponents(View view) {
        context = getContext();
//        setUpRefreshLayout();
        getProfile();
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dexterPerm(PIC_ID_HEAD_FAMILY);

            }
        });
        if (!isEdit){
            tvName.setEnabled(false);
            tvEmail.setEnabled(false);
            tvMob.setEnabled(false);
            radio_button_male.setEnabled(false);
            radio_button_female.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
            profile_img.setEnabled(false);
            isEdit=false;
            editbtn.setText(getResources().getString(R.string.edit));
        }else{
            tvName.setEnabled(true);
            tvEmail.setEnabled(true);
            tvMob.setEnabled(true);
            radio_button_male.setEnabled(true);
            radio_button_female.setEnabled(true);
            btn_submit.setEnabled(true);
            profile_img.setEnabled(true);
            isEdit=true;
            btn_submit.setVisibility(View.VISIBLE);

            editbtn.setText(getResources().getString(R.string.view));
        }
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit){
                    tvName.setEnabled(false);
                    tvEmail.setEnabled(false);
                    tvMob.setEnabled(false);
                    radio_button_male.setEnabled(false);
                    radio_button_female.setEnabled(false);
                    btn_submit.setVisibility(View.GONE);
                    profile_img.setEnabled(false);
                    isEdit=false;
                    editbtn.setText(getResources().getString(R.string.edit));
                }else{
                    tvName.setEnabled(true);
                    tvEmail.setEnabled(true);
                    tvMob.setEnabled(true);
                    radio_button_male.setEnabled(true);
                    radio_button_female.setEnabled(true);
                    btn_submit.setEnabled(true);
                    profile_img.setEnabled(true);
                    isEdit=true;
                    btn_submit.setVisibility(View.VISIBLE);

                    editbtn.setText(getResources().getString(R.string.edit));
                }
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
        radio_button_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="Female";
                radio_button_male.setChecked(false);
            }
        });
        radio_button_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="Male";
                radio_button_female.setChecked(false);
            }
        });
    }

    private boolean validate() {

        return true;
    }

    private void submitData() {
        UserService.addProfile(context,gender,aadhar_photo,aadharNumber,panPhoto,panNumber,accountNumber,accHolderName,bankName,bankBranch,ifscCode,bankRegisteredMobile,tvName.getText().toString(),tvMob.getText().toString(),photoHeadString,this,ADD_PROFILE_DATA);
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
                Uri photoURI = FileProvider.getUriForFile(context,
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
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoHeadString = image.getAbsolutePath();
        return image;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_ID_MEMBER_DIALOG:
                if(resultCode == Activity.RESULT_OK)  {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap= ImageUtils.getResizedBitmap(myBitmap,480);

//                    photoHeadString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    photoMemberString = ImageUtils.convertBitmapIntoBase64(photoMember);
                    Log.d("TAG", "onActivityResult:photoMemberString " + photoMemberString);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                // click_image_id.setImageBitmap(photo);
                break;
            case PIC_ID_HEAD_FAMILY:
                if(resultCode == Activity.RESULT_OK)  {
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Log.d("TAG", "onActivityResult: "+myBitmap.getHeight()+"x"+myBitmap.getWidth());
                    myBitmap=ImageUtils.getResizedBitmap(myBitmap,480);
                    photoHeadString=ImageUtils.convertBitmapIntoBase64(myBitmap);
                    if (photoHeadString!=null){
                        submitData();
                    }
                Log.d("TAG", "onActivityResult:photoHeadString "+photoHeadString);
                profile_img.setImageBitmap(myBitmap);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.no_capture_image));
                }
                // click_image_id.setImageBitmap(photo);
                break;
        }
    }
    private void getProfile() {
        UserService.getProfile(getActivity(),this,DASHBOARD_REQ_CODE);
    }


//    private void setUpRefreshLayout() {
//        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
//                swipeRefreshColorSchemeArray[6]);
//        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getProfile();
//                dashSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case DASHBOARD_REQ_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            if (baseResponseModule.getProfileModelDetails()!=null){
                                tvEmail.setText(baseResponseModule.getProfileModelDetails().getEmail());
                                tvMob.setText(baseResponseModule.getProfileModelDetails().getMobile_no());
                                tvName.setText(baseResponseModule.getProfileModelDetails().getName());
                                tv_name.setText(baseResponseModule.getProfileModelDetails().getName());
//                               ?
                                if (baseResponseModule.getProfileModelDetails().getOnline()!=null && baseResponseModule.getProfileModelDetails().getOnline().equalsIgnoreCase("yes")){
                                    onLine.setText(context.getResources().getString(R.string.yes));
                                }else{
                                    onLine.setText(context.getResources().getString(R.string.no));
                                }

                                Picasso.with(context).load(ExtraUtils.baseImg+baseResponseModule.getProfileModelDetails().getProfile_img()).error(R.drawable.add_image).into(profile_img);
                                gender=baseResponseModule.getProfileModelDetails().getGender();
                                if (gender.equalsIgnoreCase("male")){
                                    radio_button_male.setChecked(true);
                                }else  if (gender.equalsIgnoreCase("female")){
                                    radio_button_female.setChecked(true);
                                }
                            }


                        }
                    }
                }
                break;
            case ADD_PROFILE_DATA:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            ToastUtils.shortToast(baseResponseModule.getStatusMessage());
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
    public void dexterPerm(int PERM){
        Dexter.withContext(context)
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
