package com.ispl.ekalarogya.training.main.dashotheractivity.family;

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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.VillageFamilyAdapter;
import com.ispl.ekalarogya.training.adapters.VillageListAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.models.VillageFamilyModel;
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

public class VillageFamily extends AppCompatActivity implements RetroAPICallback {
    private static final int VILLAGE_FAMILY_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final String TAG = VillageFamily.class.getSimpleName();
    VillageFamilyAdapter villageFamilyAdapter;
    List<VillageFamilyModel> villageMobileList = new ArrayList<>();
    VillageListAdapter villageListAdapter;
    String searchData;
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
    String type,id;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_village_family_recyclerview)
    RecyclerView rv_village_family_recyclerview;
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
    String current_list,village_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_family);
        ButterKnife.bind(this);
        addMember.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(VillageFamily.this, 1);
        rv_village_family_recyclerview.setLayoutManager(gridLayoutManager);
        if (getIntent().getStringExtra("id")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            id=i.getStringExtra("id");
            village_name=i.getStringExtra("village_name");
            Log.d("TAG", "onCreate: getintent "+" "+type+id);
            switch (type) {
                case "1":
                    tv_title.setText(R.string.update_owner);
                    break;
                case "2":
                    tv_title.setText(R.string.add_owner_details);
                    break;
                case "10":
                    tv_title.setText(R.string.family_list);
//                    getVillageheadList(id);
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                getVillageList();
            }
        }
        setUpRefreshLayout();


       // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VillageFamily.this, AddFamily.class);
                intent.putExtra("id", id);
                intent.putExtra("village_name", village_name);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });

        rv_village_family_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    getVillageheadList(id);
                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                page=1;
               if (id!=null){
                   villageMobileList.clear();
                   getVillageheadList(id);
               }else{

                   getVillageList();
               }

               //
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(VillageFamily.this, MainActivity.class));
                finish();
            }
        });
    }

    private void getVillageheadList(String id) {
        UserService.getVillageHeadList(VillageFamily.this,id,searchData,page,this,VILLAGE_FAMILY_REQUEST_CODE);

    }


    private void getVillageList() {
        UserService.getVillageList(VillageFamily.this,this,VILLAGE_LIST_REQUEST_CODE);
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
            case VILLAGE_FAMILY_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            addMember.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onResponse: "+baseResponseModule.getVillageFamilyModelList().size());
                            // current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
                            Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            if(baseResponseModule.getVillageFamilyModelList().size()>0) {
                                tvEmpty.setVisibility(View.GONE);
//                                rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
//                                rv_village_family_recyclerview.setHasFixedSize(true);
                                if (!(baseResponseModule.getVillageFamilyModelList().size() > 0)) {
                                    rv_village_family_recyclerview.setVisibility(View.GONE);
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    isNoMoreItems = true;

                                } else {
                                    if (page>1) {
                                        Log.d(TAG, "onResponse: if"+page);
                                        if (villageMobileList.contains(baseResponseModule.getVillageFamilyModelList())){
//                                            villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());

                                        }else if(!(villageMobileList.contains(baseResponseModule.getVillageFamilyModelList()))){
                                            villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());

                                        }
//                                        villageFamilyAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "onResponse: else"+page);
                                        villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                        villageFamilyAdapter = new VillageFamilyAdapter(VillageFamily.this,villageMobileList);
                                        rv_village_family_recyclerview.setAdapter(villageFamilyAdapter);
                                    }
                                    rv_village_family_recyclerview.setVisibility(View.VISIBLE);
                                    villageFamilyAdapter.notifyDataSetChanged();
                                    tvEmpty.setVisibility(View.GONE);

                                } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageFamilyModelList().toString());
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
//                         current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                        //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
                        Log.d(TAG, "onResponse: current_list "+current_list);
                        //tv_title.setText(current_list);
                        tv_list_id.setText(R.string.id);
                        tv_list_name.setText(R.string.village);
                        tv_list_total.setText(R.string.family);
                        if(baseResponseModule.getVillageListModelList().size()>0)
                        {
                            tvEmpty.setVisibility(View.GONE);
                            rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_village_family_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new VillageListAdapter(VillageFamily.this,baseResponseModule.getVillageListModelList());
                            rv_village_family_recyclerview.setAdapter(villageListAdapter);
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
        if (id!=null){
            villageMobileList.clear();
            getVillageheadList(id);
        }else{

            getVillageList();
        }
    }
}