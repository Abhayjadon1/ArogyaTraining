package com.ispl.ekalarogya.training.main.dashotheractivity.preventives.hygiene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.PersonalHygnFamilyAdatper;
import com.ispl.ekalarogya.training.adapters.PersonalHysgineChildAdaper;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.FamilyMembersModel;
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

public class PersonalHygineFamily extends AppCompatActivity implements RetroAPICallback {
    private static final int CHILD_HYGINE_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int VILLAGE_FAMILY_REQUEST_CODE = 113;

    private static final String TAG = PersonalHygineFamily.class.getSimpleName();
    PersonalHygnFamilyAdatper villageFamilyAdapter;
    PersonalHysgineChildAdaper personalHysgineChildAdaper;

    List<VillageFamilyModel> villageMobileList = new ArrayList<>();
    List<FamilyMembersModel> familyMembersModelList = new ArrayList<>();
    @BindView(R.id.cardRv)
    CardView cardRv;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    @BindView(R.id.search_src_text)
    EditText search_src_text;


    @BindView(R.id.search_btn)
    Button search_btn;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;


    String type,id;
    


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_family_list_recyclerview)
    RecyclerView rv_family_list_recyclerview;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    List<VillageFamilyModel> villageFamilyModels;

    String current_list,v_id,h_id,searchData;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0;
    int page=1;
    private boolean isNoMoreItems = false;

    VillageFamilyModel h_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_hygine_family);
        ButterKnife.bind(this);
        type=getIntent().getStringExtra("type");

        if (type!=null){
            Intent i = getIntent();
            h_data=i.getParcelableExtra("h_data");
            h_id=h_data.getFamily_head_id();
            v_id=i.getStringExtra("v_id");
            ll_search.setVisibility(View.GONE);

            getVillageChildList(h_id);
            Log.d(TAG, "onCreate: "+h_id);
        }else if (getIntent().getStringExtra("v_id")!=null){
            Intent i = getIntent();
            ll_search.setVisibility(View.VISIBLE);
            v_id=i.getStringExtra("v_id");
            getVillageheadList(v_id);
            Log.d(TAG, "onCreate: "+v_id);
        }

        setUpRefreshLayout();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PersonalHygineFamily.this, PersonalHygine.class));
                finish();
            }
        });
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PersonalHygineFamily.this, 1);
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

                    if (h_id!=null){
                        getVillageChildList(h_id);
                    }else if(v_id!=null){
                        getVillageheadList(v_id);
                    }
                }
            }


        });

    }

    private void getVillageChildList(String id) {
        Log.d(TAG, "getVillageChildList: "+id);
        UserService.getPersonalHygineChildList(PersonalHygineFamily.this,page,id,this,CHILD_HYGINE_REQUEST_CODE);

    }

    private void getVillageheadList(String id) {
        UserService.getVillageHeadList(PersonalHygineFamily.this,id,searchData,page,this,VILLAGE_FAMILY_REQUEST_CODE);

    }

    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               if (h_id!=null){
                   familyMembersModelList.clear();
                   getVillageChildList(h_id);
               }else if(v_id!=null){
                   villageMobileList.clear();
                   getVillageheadList(v_id);
               }
                dashSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case CHILD_HYGINE_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getPersonlHygnChildResponses().size());
                             current_list=baseResponseModule.getList_name();
//  current_list= ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            tv_title.setText(getResources().getString(R.string.personal_hygiene));
                            if(baseResponseModule.getPersonlHygnChildResponses().size()>0) {
                                tvEmpty.setVisibility(View.GONE);
                                familyMembersModelList.addAll(baseResponseModule.getPersonlHygnChildResponses());
                                rv_family_list_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_family_list_recyclerview.setHasFixedSize(true);
                                personalHysgineChildAdaper = new PersonalHysgineChildAdaper(PersonalHygineFamily.this,h_data,v_id,familyMembersModelList);
                                rv_family_list_recyclerview.setAdapter(personalHysgineChildAdaper);
                                Log.d(TAG, "onResponse: setadpetr"+familyMembersModelList.size());
                            }
                        }
                    }
                    if(familyMembersModelList.size()<1){
                        cardRv.setVisibility(View.GONE);
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
                                rv_family_list_recyclerview.setVisibility(View.VISIBLE);////////
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page > 1) {
                                    villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                    villageFamilyAdapter.notifyDataSetChanged();
                                } else {
                                    villageMobileList.addAll(baseResponseModule.getVillageFamilyModelList());
                                    villageFamilyAdapter = new PersonalHygnFamilyAdatper(PersonalHygineFamily.this, villageMobileList);
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