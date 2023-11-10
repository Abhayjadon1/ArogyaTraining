package com.inventics.ekalarogya.training.main.dashotheractivity.firstaid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.FirstAidAdapter;
import com.inventics.ekalarogya.training.adapters.FirstAidVillageListAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.FirstAidModel;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class FirstAid extends AppCompatActivity implements RetroAPICallback {
    private static final int VILLAGE_FISRT_AID_REQUEST_CODE = 111;
    private static final int SINGLE_FISRT_AID_REQUEST_CODE = 151;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final String TAG = FirstAid.class.getSimpleName();
    FirstAidAdapter firstAidAdapter;
    List<FirstAidModel> firstAidModelList = new ArrayList<>();
    FirstAidVillageListAdapter villageListAdapter;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.addMember)
    FloatingActionButton addMember;
    @BindView(R.id.tv_list_id)
    TextView tv_list_id;
    @BindView(R.id.tv_list_name)
    TextView tv_list_name;
    @BindView(R.id.tv_list_total)
    TextView tv_list_total;
    String type,id,v_id,h_id;

    @BindView(R.id.llHead_firstAid)
    LinearLayout llHead_firstAid;
    @BindView(R.id.llHead_vill)
    LinearLayout llHead_vill;


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_firstAid_recyclerview)
    RecyclerView rv_firstAid_recyclerview;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    List<VillageFamilyModel> villageFamilyModels;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0;
    int page=1;
    private boolean isNoMoreItems = false;
    String current_list;
    VillageListModel v_data;
    FirstAidModel m_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        ButterKnife.bind(this);

        llHead_firstAid.setVisibility(View.GONE);
        addMember.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FirstAid.this, 1);
        rv_firstAid_recyclerview.setLayoutManager(gridLayoutManager);
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            v_data=i.getParcelableExtra("v_data");

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
                    llHead_firstAid.setVisibility(View.VISIBLE);
                    v_id=v_data.getVillage_id();
                    if (v_id!=null){
                        getFirstAidList(v_id);
                    }
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                llHead_vill.setVisibility(View.VISIBLE);
                llHead_firstAid.setVisibility(View.GONE);
                getVillageList();
            }
        }
        setUpRefreshLayout();


        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FirstAid.this,R.style.MyAlertDialogStyle);
                builder.setTitle(getResources().getString(R.string.pateint_type));
                builder.setCancelable(true);
                // add a list
                String[] animals = {getResources().getString(R.string.member), getResources().getString(R.string.guest)};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(FirstAid.this, FirstAidFamily.class);
                                intent.putExtra("v_id", v_id);
                                intent.putExtra("type", "add");
                                startActivity(intent);
                                break;
                            case 1: // cow
                                Intent intent2 = new Intent(FirstAid.this, FirstAidAdd.class);
//                                intent.putExtra("m_data",familyMemberListModelList);
                                intent2.putExtra("v_id",v_id);
                                intent2.putExtra("type","add");
//                                Log.d(TAG, "onClick: member"+headid+" "+familyMemberListModelList.getId());
                                startActivity(intent2);
                                break;

                        }
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();




//                Intent intent = new Intent(FirstAid.this, FirstAidFamily.class);
//                intent.putExtra("v_id", v_id);
//                intent.putExtra("type", "add");
//                startActivity(intent);
            }
        });

        rv_firstAid_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                   if (v_id!=null){
                       getFirstAidList(v_id);
                   }else {
                       getVillageList();
                   }
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
                dashSwipeRefreshLayout.setRefreshing(false);
                page=1;
                if (v_id!=null){
                    firstAidModelList.clear();
                    getFirstAidList(v_id);
                }else if(v_id==null){
//                    villageFamilyModels.clear();
                    getVillageList();
                }
                //
            }
        });
    }

//    private void getSingleFamilyMedGarden() {
//        UserService.getSingleFirstAidList(FirstAid.this,h_id,String.valueOf(page),this,SINGLE_FISRT_AID_REQUEST_CODE);
//
//    }

    private void getFirstAidList(String v_id) {
        UserService.getFirstAidList(FirstAid.this,v_id,String.valueOf(page),this,VILLAGE_FISRT_AID_REQUEST_CODE);

    }


    private void getVillageList() {
        UserService.getVillageList(FirstAid.this,this,VILLAGE_LIST_REQUEST_CODE);
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
            case VILLAGE_FISRT_AID_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getFirstAidModelList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);

                            if (!(baseResponseModule.getFirstAidModelList().size() > 0)) {
                                tvEmpty.setVisibility(View.GONE);
//                                    rv_firstAid_recyclerview.setVisibility(View.GONE);
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page>1) {
                                    if (!(firstAidModelList.contains(baseResponseModule.getFirstAidModelList()))){

                                    }else {
                                        firstAidModelList.addAll(baseResponseModule.getFirstAidModelList());
                                        firstAidAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    firstAidModelList.addAll(baseResponseModule.getFirstAidModelList());
                                    firstAidAdapter = new FirstAidAdapter(FirstAid.this,firstAidModelList);
                                    rv_firstAid_recyclerview.setAdapter(firstAidAdapter);
                                }
                                rv_firstAid_recyclerview.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);

                            } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getFirstAidModelList().toString());
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
                            tvEmpty.setVisibility(View.GONE);
                            rv_firstAid_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_firstAid_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new FirstAidVillageListAdapter(FirstAid.this,baseResponseModule.getVillageListModelList());
                            rv_firstAid_recyclerview.setAdapter(villageListAdapter);
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

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: ");
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume: ");
//
//        page=1;
//        if (v_id!=null){
//            getFirstAidList(v_id);
//        }else{
//            getVillageList();
//        }
//
//    }
}