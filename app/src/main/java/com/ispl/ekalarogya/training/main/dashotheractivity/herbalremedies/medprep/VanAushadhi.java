package com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.medprep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
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
import com.ispl.ekalarogya.training.adapters.VanAushadhiAdapter;
import com.ispl.ekalarogya.training.adapters.VanAushadhiVillageAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.models.VanaushadhiModel;
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

public class VanAushadhi extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.tv_cured)
    TextView tv_cured;
    @BindView(R.id.tv_not_cured)
    TextView tv_not_cured;

    @BindView(R.id.tv_referred)
    TextView tv_referred;

    @BindView(R.id.tv_title)
    TextView tv_title;
    
    @BindView(R.id.addMember)
    FloatingActionButton addMember;
    @BindView(R.id.rv_van_aushadhi_recyclerview)
    RecyclerView rv_van_aushadhi_recyclerview;
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

    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int VILLAGE_AUSHADHI_CODE = 115;
    private static final String TAG = VanAushadhi.class.getSimpleName();
    VanAushadhiAdapter vanAushadhiGardenAdapter;
    List<VanaushadhiModel> vanAushadhiGardenModelList = new ArrayList<>();
    VanAushadhiVillageAdapter villageListAdapter;
    
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
  
    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0,status=1;
    int page=1;
    private boolean isNoMoreItems = false;
    String current_list;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_aushadhi);
        ButterKnife.bind(this);
        addMember.setVisibility(View.GONE);
        rlCured.setVisibility(View.GONE);
        llHead.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(VanAushadhi.this, 1);
        rv_van_aushadhi_recyclerview.setLayoutManager(gridLayoutManager);
        tv_cured.setOnClickListener(this);
        tv_not_cured.setOnClickListener(this);
        tv_referred.setOnClickListener(this);
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
                    llHead.setVisibility(View.VISIBLE);
                    rlCured.setVisibility(View.VISIBLE);
                    if (h_id!=null){
//                        getSingleFamilyMedGarden();
                    }else if(v_id!=null){
                        getVillageTreatmentList(v_id);
                    }
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                llHead_vill.setVisibility(View.VISIBLE);
                llHead.setVisibility(View.GONE);
                getVillageList();
            }
        }
        setUpRefreshLayout();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
       updateBtn();
        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(VanAushadhi.this,R.style.MyAlertDialogStyle);
                builder.setTitle(getResources().getString(R.string.pateint_type));
                builder.setCancelable(true);
                // add a list
                String[] animals = {getResources().getString(R.string.member), getResources().getString(R.string.guest)};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(VanAushadhi.this, VanAushadhiAddMember.class);
                                intent.putExtra("v_id", v_id);
                                intent.putExtra("type", "add");
                                startActivity(intent);
                                break;
                            case 1: // cow
                                Intent intent2 = new Intent(VanAushadhi.this, VanAushadhiAdd.class);
                                intent2.putExtra("v_id", v_id);
                                intent2.putExtra("type", "add");
                                startActivity(intent2);
                                break;

                        }
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        rv_van_aushadhi_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                vanAushadhiGardenModelList.clear();
                page=1;
                getVillageTreatmentList(v_id);
                //
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateBtn() {
        if (status==1){
            tv_cured.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33B5E5")));//#00E0E0E0
            tv_not_cured.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
            tv_referred.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
        }else if(status==0) {
            tv_not_cured.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33B5E5")));
            tv_cured.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
            tv_referred.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
        }else if(status==2) {
            tv_referred.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#33B5E5")));
            tv_cured.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
            tv_not_cured.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));
        }
    }


    private void getVillageTreatmentList(String v_id) {
        UserService.getvanAushadhiList(VanAushadhi.this,v_id,String.valueOf(status),String.valueOf(page),this,VILLAGE_AUSHADHI_CODE);

    }


    private void getVillageList() {
        UserService.getVillageList(VanAushadhi.this,this,VILLAGE_LIST_REQUEST_CODE);
    }


    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // getVillagefamily();
                dashSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case VILLAGE_AUSHADHI_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
//                            Log.d(TAG, "onResponse: "+baseResponseModule.getVanaushadhiModelList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);

//                            tvEmpty.setVisibility(View.GONE);
                            if (!(baseResponseModule.getVanaushadhiModelList().size() > 0)) {
//                                    rv_van_aushadhi_recyclerview.setVisibility(View.GONE);
//                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page>1) {
                                   if (!(vanAushadhiGardenModelList.contains(baseResponseModule.getVanaushadhiModelList()))){

                                   }else {
                                       vanAushadhiGardenModelList.addAll(baseResponseModule.getVanaushadhiModelList());
                                       vanAushadhiGardenAdapter.notifyDataSetChanged();
                                   }
                                } else {
                                    vanAushadhiGardenModelList.clear();

                                    vanAushadhiGardenModelList.addAll(baseResponseModule.getVanaushadhiModelList());
                                    vanAushadhiGardenAdapter = new VanAushadhiAdapter(VanAushadhi.this,vanAushadhiGardenModelList);
                                    rv_van_aushadhi_recyclerview.setAdapter(vanAushadhiGardenAdapter);
                                    vanAushadhiGardenAdapter.notifyDataSetChanged();
                                }
                                rv_van_aushadhi_recyclerview.setVisibility(View.VISIBLE);
//                                tvEmpty.setVisibility(View.GONE);

                            } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVanaushadhiModelList().toString());
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
                        if(baseResponseModule.getVillageListModelList().size()>0)
                        {
//                            tvEmpty.setVisibility(View.GONE);
                            rv_van_aushadhi_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_van_aushadhi_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new VanAushadhiVillageAdapter(VanAushadhi.this,baseResponseModule.getVillageListModelList());
                            rv_van_aushadhi_recyclerview.setAdapter(villageListAdapter);
                            Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageListModelList().toString());
                        }
                    }
                }

                break;

        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        ToastUtils.shortToast(t.getMessage());
        Log.d(TAG, "onFailure: "+t.getMessage());
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cured:
                status=1;
                page=1;
                vanAushadhiGardenModelList.clear();
//                vanAushadhiGardenAdapter.notifyDataSetChanged();
                getVillageTreatmentList(v_id);
                updateBtn();
                break;
            case R.id.tv_not_cured:
                status=0;
                page=1;
                vanAushadhiGardenModelList.clear();
//                villageListAdapter.notifyDataSetChanged();
                getVillageTreatmentList(v_id);
                updateBtn();
                break;
            case R.id.tv_referred:
                status=2;
                page=1;
                vanAushadhiGardenModelList.clear();
//                villageListAdapter.notifyDataSetChanged();
                getVillageTreatmentList(v_id);
                updateBtn();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        page=1;
        if (v_id!=null){
            vanAushadhiGardenModelList.clear();
            getVillageTreatmentList(v_id);
        }else{
            getVillageList();
        }
    }
}