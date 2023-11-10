package com.inventics.ekalarogya.training.main.dashotheractivity.malnutrition.malnutrion;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.MalnutrionCheckupFamilyAdapter;
import com.inventics.ekalarogya.training.adapters.MalnutrionFamilyMemberAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.FamilyMemberListModel;
import com.inventics.ekalarogya.training.models.FamilyMembersModel;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
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

public class MalnutritionFamily extends AppCompatActivity implements RetroAPICallback {
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

    private static final String TAG = MalnutritionFamily.class.getSimpleName();
    MalnutrionCheckupFamilyAdapter malnutrionCheckupFamilyAdapter;
    MalnutrionFamilyMemberAdapter malnutrionFamilyMemberAdapter;
    List<VillageFamilyModel> villageMobileList = new ArrayList<>();
    List<FamilyMembersModel> familyMembersModelList = new ArrayList<>();
    @BindView(R.id.cardRv)
    CardView cardRv;
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


    FamilyMemberListModel data_member;
    VillageFamilyModel data_head;
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
                    tv_title.setText(R.string.malnutrition_checkup_head);
                    ll_search.setVisibility(View.VISIBLE);
                    getVillageheadList(v_id);
                    break;
                case "head":
                    tv_title.setText(R.string.malnutrition_checkup_member);
                    data_head=i.getParcelableExtra("h_data");
                    h_id=data_head.getFamily_head_id();
                    ll_search.setVisibility(View.GONE);
                    v_id=i.getStringExtra("v_id");
                    getVillageChildList();
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                getVillageheadList(v_id);
            }
        }
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
//                    ToastUtils.shortToast("Search box is blank");
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MalnutritionFamily.this, 1);
        rv_family_list_recyclerview.setLayoutManager(gridLayoutManager);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
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
        //UserService.getVillageFamilyMembersList(MalnutritionFamily.this,v_id,h_id,"2",this,MEMBER_ANIM_REQUEST_CODE);
//        UserService.getchildWomenList(MalnutritionFamily.this,v_id,h_id, String.valueOf(page),"status",this,MEMBER_ANIM_REQUEST_CODE);
        UserService.getVillageFamilyMembersListNew(MalnutritionFamily.this,h_id,"2",page,this,MEMBER_ANIM_REQUEST_CODE);

    }

    private void getVillageheadList(String v_id) {
        UserService.getVillageHeadList(MalnutritionFamily.this,v_id,searchData,page,this,VILLAGE_FAMILY_REQUEST_CODE);

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
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getFamilyMembersModelListNew().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            if(baseResponseModule.getFamilyMembersModelListNew().size()>0)
                            {
                                familyMembersModelList.addAll(baseResponseModule.getFamilyMembersModelListNew());
                                tvEmpty.setVisibility(View.GONE);
                                rv_family_list_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_family_list_recyclerview.setHasFixedSize(true);
                                malnutrionFamilyMemberAdapter = new MalnutrionFamilyMemberAdapter(MalnutritionFamily.this,id,v_id,familyMembersModelList);
                                rv_family_list_recyclerview.setAdapter(malnutrionFamilyMemberAdapter);
                                Log.d(TAG, "onResponse: setadpetr"+familyMembersModelList.toString());
                            }
                            if(familyMembersModelList.size()<1){
                                cardRv.setVisibility(View.GONE);
                            }
//                            tvEmpty.setVisibility(View.VISIBLE);
//                            dashSwipeRefreshLayout.setVisibility(View.GONE);
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
                                rv_family_list_recyclerview.setVisibility(View.VISIBLE );
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page > 1) {
                                    villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                    malnutrionCheckupFamilyAdapter.notifyDataSetChanged();
                                } else {
                                    villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                    malnutrionCheckupFamilyAdapter = new MalnutrionCheckupFamilyAdapter(MalnutritionFamily.this,v_id, villageMobileList);
                                    rv_family_list_recyclerview.setAdapter(malnutrionCheckupFamilyAdapter);
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