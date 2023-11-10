package com.inventics.ekalarogya.training.main.dashotheractivity.villagevisit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.inventics.ekalarogya.training.adapters.VillageVisitAdapter;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.VillageVisitModel;
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

public class VillageVisit  extends AppCompatActivity implements RetroAPICallback {
    private static final int VILLAGE_VISIT_REQUEST_CODE = 111;
    private static final int SINGLE_FISRT_AID_REQUEST_CODE = 151;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final String TAG = VillageVisit.class.getSimpleName();
    VillageVisitAdapter villageVisitAdapter;
    List<VillageVisitModel> villageVisitModelList = new ArrayList<>();

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.addMember)
    FloatingActionButton addMember;
//    @BindView(R.id.tv_list_id)
//    TextView tv_list_id;
//    @BindView(R.id.tv_list_name)
//    TextView tv_list_name;
//    @BindView(R.id.tv_list_total)
//    TextView tv_list_total;
    String type,id,v_id,h_id;

    @BindView(R.id.llVillage_visit)
    LinearLayout llVillage_visit;



    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_villageVisit_recyclerview)
    RecyclerView rv_villageVisit_recyclerview;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0;
    int page=1;
    private boolean isNoMoreItems = false;
    String current_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidyalay_visit);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(VillageVisit.this, 1);
        rv_villageVisit_recyclerview.setLayoutManager(gridLayoutManager);
        setUpRefreshLayout();
//        getVillagevisitList();

        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VillageVisit.this, VillageVisitAdd.class);
                intent.putExtra("v_id", v_id);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        rv_villageVisit_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        getVillagevisitList();
                    }
                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                page=1;
                if (villageVisitModelList!=null||villageVisitModelList.size()>0){
                    villageVisitModelList.clear();
                }
                getVillagevisitList();
            }
        });
    }

    private void getVillagevisitList() {
        UserService.getVillagevisitList(VillageVisit.this,this,VILLAGE_VISIT_REQUEST_CODE);

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
            case VILLAGE_VISIT_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getVillageVisitModelList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);

                            tvEmpty.setVisibility(View.GONE);
                            if (!(baseResponseModule.getVillageVisitModelList().size() > 0)) {
//                                    rv_villageVisit_recyclerview.setVisibility(View.GONE);
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page>1) {
                                    villageVisitModelList.addAll(baseResponseModule.getVillageVisitModelList());
                                    villageVisitAdapter.notifyDataSetChanged();
                                } else {
                                    villageVisitModelList.addAll(baseResponseModule.getVillageVisitModelList());
                                    villageVisitAdapter = new VillageVisitAdapter(VillageVisit.this,villageVisitModelList);
                                    rv_villageVisit_recyclerview.setAdapter(villageVisitAdapter);
                                }
                                rv_villageVisit_recyclerview.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);

                            } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageVisitModelList().toString());
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
        if (villageVisitModelList!=null&villageVisitModelList.size()>0) {
            villageVisitModelList.clear();
            villageVisitAdapter.notifyDataSetChanged();
        }

        getVillagevisitList();
    }
}