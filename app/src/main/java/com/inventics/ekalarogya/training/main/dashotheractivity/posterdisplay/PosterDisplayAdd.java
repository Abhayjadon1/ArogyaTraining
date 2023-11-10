package com.inventics.ekalarogya.training.main.dashotheractivity.posterdisplay;

import androidx.appcompat.app.AppCompatActivity;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.inventics.ekalarogya.training.BuildConfig;;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.ImageAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.ImageList;
import com.inventics.ekalarogya.training.models.PosterDisplayModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.GetAllListResponse;
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

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class PosterDisplayAdd extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;

    File photoFile=null;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.spin_village_name)
    Spinner spin_village_name;
    @BindView(R.id.spin_topic)
    Spinner spin_topic;
    @BindView(R.id.spin_week)
    Spinner spin_week;

    @BindView(R.id.et_no_of_poster)
    EditText et_no_of_poster;
    @BindView(R.id.et_total_present)
    EditText et_total_present;
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
    @BindArray(R.array.weak_list)
    String[] weak_list;
    @BindArray(R.array.arogya_topic)
    String[] arogya_topicList;
    private RequestQueue requestQueue;
    ImageAdapter imageAdapter;
    List<ImageList> imgList=new ArrayList<ImageList>();
    private final static int ADD_DATA_REQUEST=111;
    private static final int GET_VILLAGE_LIST=115;
    private static final int GET_POSTER_DETAILS=215;
    private static final int GET_ALL_LIST=216;
    private static final int REQUEST_LOCATION = 104;
    private static final int PIC_ID_MEMBER_DIALOG = 121;
    private static final int UPLOAD_IMAGE=123;
    private List<VillageListModel> villName = new ArrayList<>();
    boolean isEdit=false;
    VillageListArrayAdapter adapter;

    Bitmap photoPosterClick;
    String photos;

    private static final String TAG = PosterDisplayAdd.class.getSimpleName();
    String type,id,total,arogya_topic,week,posterCount,village,status="0",poster_display_id;
    PosterDisplayModel m_data;
    String postersImg;


    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    TextView totalImages;
    ArrayList<String> mArrayUri;
    int position = 0;
    List<String> imagesEncodedList;
//    ArrayAdapter<String> adapterWeak = new ArrayAdapter<>(this, R.layout.weekly_spinner_custom_row, weak_list);
//    ArrayAdapter<String> adapterTopic = new ArrayAdapter<>(this, R.layout.weekly_spinner_custom_row, arogya_topicList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_display_add);
        ButterKnife.bind(this);


        getVillageList();
        getAllList();
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            mArrayUri = new ArrayList<String>();

            if (mArrayUri.size()<=0){
                Log.d(TAG, "onCreate: ");
                list_Image_delete.setVisibility(View.GONE);
            }else{
               list_Image_delete.setVisibility(View.VISIBLE);
            }


////            images.setImageResource(R.drawable.add_image);
//            images.setFactory(new ViewSwitcher.ViewFactory() {
//                @Override
//                public View makeView() {
//                    ImageView imageView1 = new ImageView(getApplicationContext());
//                    return imageView1;
//                }
//            });
            switch (type) {
                case "add":
                    status="0";
                    isEdit=true;
                    break;
                case "view":
                    status="1";
                    isEdit=true;
                    m_data=i.getParcelableExtra("m_data");
                    getPosterData(m_data.getId());
                    break;
            }
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
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
        ArrayAdapter<String> adapterWeak = new ArrayAdapter<>(this, R.layout.weekly_spinner_custom_row, weak_list);
        spin_week.setAdapter(adapterWeak);
        spin_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                week=String.valueOf(position+1);

                String itemname = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

        ArrayAdapter<String> adapterTopic = new ArrayAdapter<>(this, R.layout.weekly_spinner_custom_row, arogya_topicList);
        spin_topic.setAdapter(adapterTopic);
        spin_topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arogya_topic=parent.getItemAtPosition(position).toString();

                String itemname = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });



        if (!isEdit){
            et_no_of_poster.setEnabled(false);
            et_total_present.setEnabled(false);
            spin_topic.setEnabled(false);
            spin_week.setEnabled(false);
            spin_village_name.setEnabled(false);
            addImage.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
        }

        btn_submit.setOnClickListener(this);
        addImage.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        list_Image_delete.setOnClickListener(this);


    }

    private void getAllList() {
        UserService.getOccupationList(this, ArogyaApplication.getCurrentLocale(this),this,GET_ALL_LIST);
    }

    private void getPosterData(String meeting_id) {
        UserService.getPosterDisplayDetails(this,meeting_id,this,GET_POSTER_DETAILS);
    }

    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }
    private void setupdata(PosterDisplayModel posterDisplayModel) {
//        String posterList=posterDisplayModel.getPosters();
        poster_display_id=posterDisplayModel.getId();
        Log.d(TAG, "setupdata: "+posterDisplayModel.toString());
//        posterList=posterList.replace("[","");
//        posterList=posterList.replace("]","");
//        posterList=posterList.replace("\"poster\\", ExtraUtils.baseImg);
//        posterList=posterList.replace("\"", "");
//        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(posterList.split(",")));
//        Log.d(TAG, "setupdata: myList setup : "+myList.size()+" "+myList.toString());
//        mArrayUri=myList;
        //         "id": 30,
        //        "village_id": 1,
        //        "sevika_id": 22,
        //        "arogya_topic": "Health awareness",
        //        "week": "First",
        //        "poster_count": 2,
        //        "total_present": 100,

//        Picasso.with(this).load(ExtraUtils.baseImg+posterDisplayModel.getPosters()).error(R.drawable.add_image).into(images);
        if (posterDisplayModel.getPosters()!=null&&posterDisplayModel.getPosters().size()>0){
            for(int i=0;i<posterDisplayModel.getPosters().size();i++){
                mArrayUri.add(posterDisplayModel.getPosters().get(i));
            }
            Log.d(TAG, "setupdata: "+mArrayUri.size());
            String img=mArrayUri.get(0);
            Picasso.with(this).load(ExtraUtils.baseImg+img).into(images);
            position = 0;
            list_Image_delete.setVisibility(View.VISIBLE);
        }
        et_total_present.setText(posterDisplayModel.getTotal_present());
        et_no_of_poster.setText(posterDisplayModel.getPoster_count());

        village=posterDisplayModel.getVillage_id();
        arogya_topic=posterDisplayModel.getArogya_topic();
        week=posterDisplayModel.getWeek();

        Log.d(TAG, "setupdata village: "+village);
        for (int i=0;i<villName.size();i++){
            if (village.equalsIgnoreCase(villName.get(i).getVillage_id())){
                spin_village_name.setSelection(i);
            }
        }
       if (arogya_topic.equalsIgnoreCase("Health awareness")){
            spin_topic.setSelection(arogya_topicList.length-2);
        }else if (arogya_topic.equalsIgnoreCase("Routine")){
           spin_topic.setSelection(arogya_topicList.length-1);
//        }else if (week.equalsIgnoreCase("Third")){
//            spin_week.setSelection(arogya_topicList.length-2);
//        }else if (week.equalsIgnoreCase("Fourth")){
//            spin_week.setSelection(arogya_topicList.length-1);
        }else {
            Log.d(TAG, "setupdata: ghkhghjghgjhcgj");
        }
        Log.d(TAG, "setupdata: ghjhgvgh "+week +" "+String.valueOf(weak_list.length));
        if (week.equalsIgnoreCase("First")){
            spin_week.setSelection(weak_list.length-4);
        }else if (week.equalsIgnoreCase("Second")){
            spin_week.setSelection(weak_list.length-3);
        }else if (week.equalsIgnoreCase("Third")){
            spin_week.setSelection(weak_list.length-2);
        }else if (week.equalsIgnoreCase("Fourth")){
            spin_week.setSelection(weak_list.length-1);
        }else {
            Log.d(TAG, "setupdata: ghkhghjghgjhcgj");
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
            case GET_ALL_LIST:
                if (response.isSuccessful()) {
                    GetAllListResponse getAllListResponse = (GetAllListResponse) response.body();
                    if (getAllListResponse != null) {
                        if (getAllListResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + getAllListResponse.getOccupation_list().size());
//                            spin_topic.setAdapter(h_adapter);
////                                List<VillageListModel> villageListModelList = new ArrayList<>();
//                            try {
//                                for(int i=0; i <=baseResponse.getVillageFamilyModelList().size(); i++){
//                                    headName.add(baseResponse.getVillageFamilyModelList().get(i));
//                                    Log.d("TAG", "onResponse: size "+baseResponse.getVillageFamilyModelList().get(i).getFamily_head());
//                                    h_adapter = new HeadArrayAdapter(this, headName);
//                                    h_adapter.notifyDataSetChanged();
//                                }
//                            }catch (Exception e){
//                                Log.d("TAG", "onResponse: exception"+e.getMessage());
//                            }
//                            spin_head_name.setAdapter(h_adapter);


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
            case GET_POSTER_DETAILS:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
//                            ToastUtils.shortToast(baseResponse.getStatusMessage());
                            setupdata(baseResponse.getPosterDisplayModel());

                            if ( baseResponse.getPosterDisplayModel()!=null){
                                ToastUtils.shortToast(baseResponse.getStatusMessage());
//                                setupdata(baseResponse.getPosterDisplayModel());
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
//                            imgList.add(new ImageList(baseResponse.getInspection_img()));
                            mArrayUri.add(baseResponse.getInspection_img());
//                    Picasso.with(this).load(mArrayUri.get(0)).error(R.drawable.add_image).into(images);

                        String img=mArrayUri.get(0);
                        Picasso.with(this).load(ExtraUtils.baseImg+img).into(images);
                        position = 0;
                        list_Image_delete.setVisibility(View.VISIBLE);
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
//                Log.d(TAG, "onClick: "+mArrayUri.toString());
                if(validate()){

//
                  String request = BuildConfig.STAGING_END_POINT+"api/arogya-poster/add";

                    try  {
                        final RequestQueue requestQueue=Volley.newRequestQueue(PosterDisplayAdd.this);

                        //Post params to be sent to the server
                        JSONObject hm = new JSONObject();
//                        try {
                            hm.put("connection_id", ArogyaApplication.getPreferenceManager().getStringValue(PreferenceManger.CONNECTION_ID));
                            hm.put("auth_code",ArogyaApplication.getPreferenceManager().getAuthCode());
                            hm.put("village_id",village);
                            hm.put("arogya_topic",arogya_topic);
                            hm.put("week",week);
                            hm.put("poster_count",posterCount);
                            hm.put("total_present",total);
                            hm.put("posters",postersImg);
                            hm.put("status",status);
//                                hm.put("poster_count",AppUrl);
//                                hm.put("poster_count",AppUrl);
//                            Log.v("PostData : ", hm + "");
//
//                            Log.d(TAG, "initiateTransection: "+hm);


//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        JsonObjectRequest request_json=new JsonObjectRequest(Request.Method.POST, request, hm, new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, "onResponse:"+response.toString()+"\n");
                                finish();

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse: "+error.getMessage());
                            }
                        });
                        Log.d(TAG, "run: "+hm);


//                        {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                HashMap<String,String> hashMapHeader=new HashMap<>();
//                                hashMapHeader.put("Content-Type","application/json");
//                                hashMapHeader.put("x-mid",PreferenceManger.M_ID);
//                                hashMapHeader.put("x-checksum",x_checksum);
//                                Log.d(TAG, "getHeaders: "+ hashMapHeader);
//                                return hashMapHeader;
//                            }
//                        };
                        requestQueue.add(request_json);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    }else{
//                    submitdata();}
                }
                break;
            case R.id.addImage:
                 dexterPerm(PIC_ID_MEMBER_DIALOG);
                break;
            case R.id.next:
//                if (position>0){
//                    previous.setEnabled(true);}
                if (position < mArrayUri.size() - 1) {
                    position++;
//                    if (mArrayUri.get(position).contains(".jpeg")){
                        Picasso.with(getApplicationContext()).load(ExtraUtils.baseImg+mArrayUri.get(position)).error(R.drawable.add_image).into(images);
//                    }else{
//                       images.setImageBitmap(convertBase64ToBitmap(mArrayUri.get(position)));}
                }
                else {
                    Toast.makeText(PosterDisplayAdd.this, R.string.last_image, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.previous:
                if (position > 0) {
                    position--;
//                    if (mArrayUri.get(position).contains(".jpeg")){
                        Picasso.with(getApplicationContext()).load(ExtraUtils.baseImg+mArrayUri.get(position)).error(R.drawable.add_image).into(images);
//                    }else{
//                        images.setImageBitmap(convertBase64ToBitmap(mArrayUri.get(position)));}

                } else {
                    ToastUtils.shortToast(R.string.no_prev_img);
                }

//                if (position==0){
//                    previous.setEnabled(false);}else{previous.setEnabled(true);}
                break;
            case R.id.list_Image_delete:
               // mArrayUri.remove(imgPos);
                if (mArrayUri.size()>0){
                    mArrayUri.remove(position);
                    if (mArrayUri.size()>0){
                        if (mArrayUri.contains("http")){
                            Picasso.with(getApplicationContext()).load(mArrayUri.get(position));
                        }else{
                            images.setImageBitmap(convertBase64ToBitmap(mArrayUri.get(position)));}
                    }else {
                        images.setImageResource(R.drawable.add_image);
                        list_Image_delete.setVisibility(View.GONE);
                    }
                }
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


                    uploadImg("/poster/jagran_program/",photos);

//                    }
                    Log.d("TAG", "onActivityResult:photo marray Size "+mArrayUri.size());

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
//village,arogya_topic,week,posterCount,total,postersImg,status,poster_display_id
        if (village==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_village));
            return false;}
        if (mArrayUri.size()==0){
            ToastUtils.shortToast(getResources().getString(R.string.add_image));
            return false;}
//        if (posterCount==null){
//            ToastUtils.shortToast(getResources().getString(R.string.add_image));
//            return false;}
        if (week==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_week));
            return false;}
        if (arogya_topic==null){
            ToastUtils.shortToast(getResources().getString(R.string.select_topic));
            return false;}
        if (et_no_of_poster.getText().length()==0){
            et_no_of_poster.setError(getResources().getString(R.string.fill_detail));
            return false;}
        if (et_total_present.getText().length()==0){
            et_total_present.setError(getResources().getString(R.string.fill_detail));
            return false;}

//



        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(mArrayUri).getAsJsonArray();
        postersImg=myCustomArray.toString();
        posterCount=et_no_of_poster.getText().toString();
        total=et_total_present.getText().toString();
//        postersImg= gson.toJson(mArrayUri);//gson.toJson(numbers)
//        Log.d(TAG, "validate: postesimg : "+postersImg);


        return true;
    }

    private void submitdata() {
        Log.d(TAG, "submitdata: ");// photo marray Size
        UserService.addPosterDisplay(this,village,arogya_topic,week,posterCount,total,postersImg,status,poster_display_id,this,ADD_DATA_REQUEST);
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