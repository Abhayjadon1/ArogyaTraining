package com.inventics.ekalarogya.training.main.dashotheractivity.malnutrition.malnutrion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.inventics.ekalarogya.training.adapters.MalnutrionVillageListAdapter;
import com.inventics.ekalarogya.training.adapters.MalnutritionCheckupAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.MalnutrinCheckupModelResponse;
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

public class MalnutritionCheckup  extends AppCompatActivity implements RetroAPICallback {
    private static final int VILLAGE_MALNUTRITION_CHECK_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final String TAG = MalnutritionCheckup.class.getSimpleName();
    MalnutritionCheckupAdapter malnutritionCheckupAdapter;
    List<MalnutrinCheckupModelResponse> malnutrinCheckupModelList = new ArrayList<>();
    MalnutrionVillageListAdapter villageListAdapter;
    @BindView(R.id.tv_add)
    TextView tv_add;
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
    String type,id,vid;

    @BindView(R.id.llHead_malnutrition)
    LinearLayout llHead_malnutrition;
    @BindView(R.id.llHead_vill)
    LinearLayout llHead_vill;


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_malnutrition_checkup_recyclerview)
    RecyclerView rv_malnutrition_checkup_recyclerview;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    List<VillageFamilyModel> villageFamilyModels;
    VillageFamilyModel h_data;
    FamilyMembersModel m_data;


    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0;
    int page=1;
    private boolean isNoMoreItems = false;
    String current_list;
    VillageListModel v_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal_nutrition_checkup);
        ButterKnife.bind(this);

        llHead_malnutrition.setVisibility(View.GONE);
        addMember.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MalnutritionCheckup.this, 1);
        rv_malnutrition_checkup_recyclerview.setLayoutManager(gridLayoutManager);
        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            switch (type) {
                case "1":
//                    tv_title.setText("Update owner details");
                    break;
                case "2":
//                    tv_title.setText("Add owner details");
                    break;
                case "vill":
                    tv_title.setText(getResources().getString(R.string.malnutrition_checkup_list));
                    llHead_vill.setVisibility(View.GONE);
                    llHead_malnutrition.setVisibility(View.VISIBLE);
                    v_data=i.getParcelableExtra("v_data");
                    vid=v_data.getVillage_id();
                    getVillageheadList(vid);
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                tv_title.setText(getResources().getString(R.string.malnutrition_checkup));
                llHead_vill.setVisibility(View.VISIBLE);
                llHead_malnutrition.setVisibility(View.GONE);
                getVillageList();
            }
        }
        setUpRefreshLayout();


        // getVillagefamily();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MalnutritionCheckup.this, MalnutritionFamily.class);
                intent.putExtra("v_id", vid);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });

        rv_malnutrition_checkup_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    if (vid!=null){
                        getVillageheadList(vid);
                    }else{
                        getVillageList();
                    }
                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);

                page=1;
                if (vid!=null){
                    malnutrinCheckupModelList.clear();
                    getVillageheadList(vid);
                }else{
                   // villageFamilyModels.clear();
                    getVillageList();
                }
                //
            }
        });
    }

    private void getVillageheadList(String v_id) {
        UserService.getMalnutritionCheckupList(MalnutritionCheckup.this,v_id, String.valueOf(page),this,VILLAGE_MALNUTRITION_CHECK_REQUEST_CODE);

    }


    private void getVillageList() {
        UserService.getVillageList(MalnutritionCheckup.this,this,VILLAGE_LIST_REQUEST_CODE);
    }


    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (vid!=null){
                    getVillageheadList(vid);
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
            case VILLAGE_MALNUTRITION_CHECK_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getMalnutrinCheckupModelResponsesList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);
                            if(baseResponseModule.getMalnutrinCheckupModelResponsesList().size()>0)
                            {

                                tvEmpty.setVisibility(View.GONE);
                                if (!(baseResponseModule.getMalnutrinCheckupModelResponsesList().size() > 0)) {
                                    rv_malnutrition_checkup_recyclerview.setVisibility(View.GONE);
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    isNoMoreItems = true;

                                } else {
                                    if (page>1) {
                                        Log.d(TAG, "onResponse: if "+page);
                                        if (malnutrinCheckupModelList.contains(baseResponseModule.getMalnutrinAnemiaCheckupModelResponseList())){
                                            Log.d(TAG, "onResponse: hghghgchghc "+page);
                                            malnutrinCheckupModelList.addAll(baseResponseModule.getMalnutrinCheckupModelResponsesList());
                                            malnutritionCheckupAdapter.notifyDataSetChanged();
                                        }else {

                                        }
                                    } else {
                                        malnutrinCheckupModelList.clear();
                                        Log.d(TAG, "onResponse: else "+page);
                                        if (malnutrinCheckupModelList.size()<1){

                                            malnutrinCheckupModelList.addAll(baseResponseModule.getMalnutrinCheckupModelResponsesList());
                                        }
                                        malnutritionCheckupAdapter = new MalnutritionCheckupAdapter(MalnutritionCheckup.this,v_data,malnutrinCheckupModelList);
                                        rv_malnutrition_checkup_recyclerview.setAdapter(malnutritionCheckupAdapter);
                                    }
                                    rv_malnutrition_checkup_recyclerview.setVisibility(View.VISIBLE);
                                    tvEmpty.setVisibility(View.GONE);
                                }
                                Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getMalnutrinCheckupModelResponsesList().toString());
                            }
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
                            rv_malnutrition_checkup_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_malnutrition_checkup_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new MalnutrionVillageListAdapter(MalnutritionCheckup.this,baseResponseModule.getVillageListModelList());
                            rv_malnutrition_checkup_recyclerview.setAdapter(villageListAdapter);
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
    protected void onResume() {
        super.onResume();
        page=1;
        if (vid!=null){
            malnutrinCheckupModelList.clear();
            getVillageheadList(vid);
        }
    }
}