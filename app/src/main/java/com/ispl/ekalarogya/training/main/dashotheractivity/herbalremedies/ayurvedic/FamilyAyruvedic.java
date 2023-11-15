package com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.ayurvedic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.MedicineGardenFamilyAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.models.FamilyMembersModelResponse;
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

public class FamilyAyruvedic  extends AppCompatActivity implements RetroAPICallback {
    private static final int MEMBER_ANIM_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int VILLAGE_FAMILY_REQUEST_CODE = 113;

    @BindView(R.id.search_src_text)
    EditText search_src_text;
    @BindView(R.id.search_btn)
    Button search_btn;
    String searchData;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    private static final String TAG = FamilyAyruvedic.class.getSimpleName();
    MedicineGardenFamilyAdapter villageFamilyAdapter;
    //    FirstAidFamilyMemberAdapter firstAidFamilyMemberAdapter;
    List<VillageFamilyModel> villageMobileList = new ArrayList<>();
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    String type,id,h_id,v_id;



    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_family_list_recyclerview)
    RecyclerView rv_family_list_recyclerview;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    List<VillageFamilyModel> villageFamilyModels;

    String current_list;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0;
    int page=1;
    private boolean isNoMoreItems = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_hygine_family);
        ButterKnife.bind(this);

        if (getIntent().getStringExtra("type")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            v_id=i.getStringExtra("v_id");
            Log.d("TAG", "onCreate: getintent "+" "+type+id);
            switch (type) {
//                case "1":
//                    tv_title.setText("Update owner details");
//                    break;
//                case "2":
//                    tv_title.setText("Add owner details");
//                    break;
                case "add":
//                    tv_title.setText("Emergency Head List");
                    ll_search.setVisibility(View.VISIBLE);
                    getVillageheadList(v_id);
                    break;
                case "head":
//                    tv_title.setText("Emergency Member List");
                    h_id=i.getStringExtra("h_id");
                    v_id=i.getStringExtra("v_id");
                    ll_search.setVisibility(View.GONE);
                    getVillageChildList();
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                getVillageheadList(v_id);
            }
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        setUpRefreshLayout();
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (search_src_text.getText().toString()!=null){
                    searchData=search_src_text.getText().toString();
                    page=1;
                    villageMobileList.clear();
                    getVillageheadList(v_id);
                }else{
                    ToastUtils.shortToast(getResources().getString(R.string.search_box_empty));
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FamilyAyruvedic.this, 1);
        rv_family_list_recyclerview.setLayoutManager(gridLayoutManager);

        rv_family_list_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                if (userScrolled && !isNoMoreItems && (visibleItemCount + pastVisibleItems) == totalItemCount) {
                    userScrolled = false;
                    page++;

                    getVillageheadList(v_id);
                }
            }


        });

    }

    private void getVillageChildList() {//1:Member listing with head included , 2:Member listing with head excluded
        UserService.getVillageFamilyMembersList(FamilyAyruvedic.this,v_id,h_id,"1",this,MEMBER_ANIM_REQUEST_CODE);

    }

    private void getVillageheadList(String v_id) {
        UserService.getVillageHeadList(FamilyAyruvedic.this,v_id,searchData,page,this,VILLAGE_FAMILY_REQUEST_CODE);

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
            case MEMBER_ANIM_REQUEST_CODE:
                if (response.isSuccessful()) {
                    FamilyMembersModelResponse baseResponseModule = (FamilyMembersModelResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getFamilyMemberListModels().size());
                            current_list=baseResponseModule.getList_name();
                            //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
                            Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            if(baseResponseModule.getFamilyMemberListModels().size()>0)
                            {
                                tvEmpty.setVisibility(View.GONE);
                                rv_family_list_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_family_list_recyclerview.setHasFixedSize(true);
//                                firstAidFamilyMemberAdapter = new FirstAidFamilyMemberAdapter(FamilyAyruvedic.this,id,baseResponseModule.getFamilyMemberListModels());
//                                rv_family_list_recyclerview.setAdapter(firstAidFamilyMemberAdapter);
                                Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getFamilyMemberListModels().toString());
                            }
//                            tvEmpty.setVisibility(View.VISIBLE);
//                            dashSwipeRefreshLayout.setVisibility(View.GONE);
                        }else if(villageFamilyAdapter!=null&&villageFamilyAdapter.getItemCount()==0){
                            rv_family_list_recyclerview.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case VILLAGE_FAMILY_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: " + baseResponseModule.getVillageFamilyModelList().size());
                            current_list = baseResponseModule.getList_name();
                            //tv_title.setText(current_list);
                            if (!(baseResponseModule.getVillageFamilyModelList().size() > 0)) {
                                rv_family_list_recyclerview.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page > 1) {
                                    villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                    villageFamilyAdapter.notifyDataSetChanged();
                                } else {
                                    villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                    villageFamilyAdapter = new MedicineGardenFamilyAdapter(FamilyAyruvedic.this,v_id, villageMobileList);
                                    Log.d(TAG, "onResponse: adtpet dataset with vid"+v_id);
                                    rv_family_list_recyclerview.setAdapter(villageFamilyAdapter);
                                }
                                rv_family_list_recyclerview.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);

                            }
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




}

