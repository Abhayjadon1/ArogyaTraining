package com.ispl.ekalarogya.training.main.dashotheractivity.eye.eyevan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.EyeVanAdapter;
import com.ispl.ekalarogya.training.adapters.EyeVanVillageAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.main.dashotheractivity.eye.eyescreening.EyeScreeningAdd;
import com.ispl.ekalarogya.training.models.EyeVanModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class EyeVan extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {


    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.tv_adult)
    TextView tv_adult;
    @BindView(R.id.tv_kids)
    TextView tv_kids;
    @BindView(R.id.tv_others)
    TextView tv_others;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.addMember)
    FloatingActionButton addMember;
    @BindView(R.id.rv_eye_van_recyclerview)
    RecyclerView rv_eye_van_recyclerview;
    @BindView(R.id.rlCured)
    RelativeLayout rlCured;
    @BindView(R.id.tv_list_id)
    TextView tv_list_id;
    @BindView(R.id.tv_list_name)
    TextView tv_list_name;
    @BindView(R.id.tv_list_total)
    TextView tv_list_total;
    String type,id,v_id,h_id;

    @BindView(R.id.llHead)
    LinearLayout llHead;
    @BindView(R.id.llHead_vill)
    LinearLayout llHead_vill;
 @BindView(R.id.llVAn)
    LinearLayout llVAn;

    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int VILLAGE_EYE_VAN = 115;
    private static final String TAG = EyeVan.class.getSimpleName();
    EyeVanAdapter eyeVanAdapter;
    List<EyeVanModel> eyeVanModelList = new ArrayList<>();
    EyeVanVillageAdapter villageListAdapter;

    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0,flag=0;//flag : 0:Adults, 1:Kids
    int page=1;
    private boolean isNoMoreItems = false;
    String current_list;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_van);
        ButterKnife.bind(this);
        addMember.setVisibility(View.GONE);
        rlCured.setVisibility(View.GONE);
        llHead.setVisibility(View.GONE);
        llVAn.setVisibility(View.GONE);
        iv_filter.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(EyeVan.this, 1);
        rv_eye_van_recyclerview.setLayoutManager(gridLayoutManager);
        tv_adult.setOnClickListener(this);
        tv_others.setOnClickListener(this);
        tv_kids.setOnClickListener(this);

        if (getIntent().getStringExtra("id")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            v_id=i.getStringExtra("id");
            h_id=i.getStringExtra("h_id");

            Log.d("TAG", "onCreate: getintent "+" "+type+v_id+h_id);
            switch (type) {
//                case "1":
//                    tv_title.setText("Update owner details");
//                    break;
//                case "2":
//                    tv_title.setText("Add owner details");
//                    break;
                case "10":
                    llHead_vill.setVisibility(View.GONE);
                    llVAn.setVisibility(View.VISIBLE);
                    rlCured.setVisibility(View.VISIBLE);
                    if (h_id!=null){
//                        getSingleFamilyMedGarden();
                    }else if(v_id!=null){
                        llHead.setVisibility(View.GONE);
                        getVillageTreatmentList(v_id);



                    }
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                llHead_vill.setVisibility(View.VISIBLE);
                llVAn.setVisibility(View.GONE);
                getVillageList();
            }
        }

        setUpRefreshLayout();


        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EyeVan.this, EyeScreeningAdd.class);
                intent.putExtra("v_id", v_id);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });

        rv_eye_van_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // If scroll state is touch scroll then set userScrolled
                // true
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                // Now check if userScrolled is true and also check if
                // the item is end then update recycler view and set
                // userScrolled to false
                if (userScrolled && !isNoMoreItems && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    userScrolled = false;
                    page++;

                    getVillageTreatmentList(v_id);
                }
            }


        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                if (v_id!=null){
                    if (eyeVanModelList.size()>0){
                        eyeVanModelList.clear();
                    }
                    getVillageTreatmentList(v_id);
                }else {

                    getVillageList();
                }
                dashSwipeRefreshLayout.setRefreshing(false);
                //
            }
        });
    }




    private void getVillageTreatmentList(String v_id) {
        UserService.getEyeVanList(EyeVan.this,v_id, String.valueOf(flag),this,VILLAGE_EYE_VAN);

    }


    private void getVillageList() {
        UserService.getVillageList(EyeVan.this,this,VILLAGE_LIST_REQUEST_CODE);
    }


    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                if (v_id!=null){
                    getVillageTreatmentList(v_id);


                }else {
                    getVillageList();
                }
                dashSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case VILLAGE_EYE_VAN:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.GONE);

                            if (!(baseResponseModule.getEyeVanList().size() > 0)) {
//                                tvEmpty.setVisibility(View.GONE);
//                                    rv_firstAid_recyclerview.setVisibility(View.GONE);
//                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page>1) {
                                    if (!(eyeVanModelList.contains(baseResponseModule.getEyeVanList()))){

                                    }else {
                                        eyeVanModelList.addAll(baseResponseModule.getEyeVanList());
                                        eyeVanAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    eyeVanModelList.addAll(baseResponseModule.getEyeVanList());
                                    eyeVanAdapter = new EyeVanAdapter(EyeVan.this,eyeVanModelList);
                                    rv_eye_van_recyclerview.setAdapter(eyeVanAdapter);
                                }
                                rv_eye_van_recyclerview.setVisibility(View.VISIBLE);
//                                tvEmpty.setVisibility(View.GONE);


                            } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getEyeVanList().toString());
                        }

                    }
                }
                break;

            case VILLAGE_LIST_REQUEST_CODE:
                BaseResponse baseResponseModule = (BaseResponse) response.body();
                if (baseResponseModule != null) {
                    if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                        Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                        Log.d(TAG, "onResponse: "+baseResponseModule.getVillageListModelList().size());
                         current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                        //tv_title.setText(current_list);
                        tv_list_id.setText(R.string.id);
                        tv_list_name.setText(R.string.village);
                        tv_list_total.setText(R.string.family);
                        if(baseResponseModule.getVillageListModelList().size()>0) {
//                            tvEmpty.setVisibility(View.GONE);
                            rv_eye_van_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_eye_van_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new EyeVanVillageAdapter(EyeVan.this,baseResponseModule.getVillageListModelList());
                            rv_eye_van_recyclerview.setAdapter(villageListAdapter);
                            Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageListModelList().toString());
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){//0:Adults, 1:Kids
            case R.id.tv_adult:
                flag=0;

                updateUi();
                break;
            case R.id.tv_kids:
                flag=1;
                updateUi();
                break;
            case R.id.tv_others:
                flag=2;
                updateUi();
                break;


        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void updateUi(){
        Log.d(TAG, "updateUi: "+eyeVanModelList.size());
        eyeVanModelList.clear();
        Log.d(TAG, "updateUi: aftr "+eyeVanModelList.size());
      //  eyeVanAdapter.notifyDataSetChanged();
        if (flag==0){
            tv_adult.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33B5E5")));//#00E0E0E0
            tv_kids.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
        }else if(flag==1) {
            tv_kids.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33B5E5")));
            tv_adult.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
        }
        eyeVanModelList.clear();
        page=1;
        getVillageTreatmentList(v_id);
    }
}