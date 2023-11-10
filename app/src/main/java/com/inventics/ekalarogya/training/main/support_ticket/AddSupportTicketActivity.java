package com.inventics.ekalarogya.training.main.support_ticket;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.databinding.ActivityAddSupportTicketBinding;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ImageUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class AddSupportTicketActivity extends AppCompatActivity implements View.OnClickListener, RetroAPICallback {

    private static final int CAMERA_REQUEST_CODE =1 ;
    private static final int GALLERY_REQUEST =2 ;
    private static final int IMAGE_REQUEST = 4;
    private static final int ADD_SAMITI_PRAVAS_REQUEST = 3;
    ActivityAddSupportTicketBinding binding;
    String imagePhoto,serverImage;

    File photoFile=null;

    private static final String TAG = "AddSupportTicketActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddSupportTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportActionBar().setTitle("Add Support Ticket");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.ivImage.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void addTicket()
    {
        UserService.createTicket(AddSupportTicketActivity.this,
                serverImage,
                binding.etName.getText().toString().trim(),
                binding.etRemark.getText().toString().trim(),"1",this,ADD_SAMITI_PRAVAS_REQUEST);
    }

    private void uploadImage()
    {
        UserService.uploadImage(AddSupportTicketActivity.this,
                imagePhoto,this,IMAGE_REQUEST);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSubmit:
                if(TextUtils.isEmpty(binding.etName.getText().toString()))
                {
                    binding.etName.setError(getResources().getString(R.string.enter_ticket_name));
                }
                else if(TextUtils.isEmpty(binding.etRemark.getText().toString())){
                    binding.etRemark.setError(getResources().getString(R.string.enter_ticket_remark));
                } else if(TextUtils.isEmpty(imagePhoto)){
                    Toast.makeText(this, getResources().getString(R.string.add_image), Toast.LENGTH_SHORT).show();
                }else
                {
                    uploadImage();
                }
                break;
            case R.id.ivImage:
//                showPictureDialog();

                Dexter.withActivity(AddSupportTicketActivity.this)
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    showPictureDialog();
                                }

                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // permission is denied permenantly, navigate user to app settings
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .onSameThread()
                        .check();
                break;
            case R.id.tvDate:
                break;


        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(R.string.select_image));
        String[] pictureDialogItems = {getResources().getString(R.string.select_from_gallary), getResources().getString(R.string.capture_from_camera)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }

    private void takePhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//        }
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {

                photoFile = createImageFile();
//                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                Log.i("Tag",photoFile.getAbsolutePath());

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.inventics.ekalarogya.training.file_provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                }
            } catch (Exception ex) {
                // Error occurred while creating the File
                ToastUtils.shortToast(ex.getMessage());
            }


        }
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
        imagePhoto = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                binding.ivImage.setImageURI(contentURI);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    imagePhoto = ImageUtils.convertBitmapIntoBase64(ImageUtils.getResizedBitmap(bitmap, 1000));
                    //  uploadImageApi(base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (data != null) {

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                binding.ivImage.setImageBitmap(bitmap);
                imagePhoto= ImageUtils.convertBitmapIntoBase64(ImageUtils.getResizedBitmap(bitmap, 1000));
                // uploadImageApi(base64Image);
            }
        }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case ADD_SAMITI_PRAVAS_REQUEST:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        Toast.makeText(AddSupportTicketActivity.this, "" + baseResponse.getStatusMessageforhelp(), Toast.LENGTH_SHORT).show();
                        if (baseResponse.getStatusHelp().equalsIgnoreCase("success")) {
                            finish();
                        }
                    }
                }
                break;

            case IMAGE_REQUEST:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        Toast.makeText(AddSupportTicketActivity.this, "" + baseResponse.getStatusMessageforhelp(), Toast.LENGTH_SHORT).show();
                        if (baseResponse.getStatusHelp().equalsIgnoreCase("success")) {
                           serverImage = baseResponse.getImage();
                            Log.d(TAG, "serverImage====: "+serverImage);
                            addTicket();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {

    }

    @Override
    public void onNoNetwork(int requestCode) {

    }
}