package com.inventics.ekalarogya.training.main.dashotheractivity.inspectionday;

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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.InspectionAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.InspectionStayModel;
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

public class InspectionDay extends AppCompatActivity implements RetroAPICallback {

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    private static final String TAG = InspectionDay.class.getSimpleName();
    String type, id;

    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindView(R.id.rv_samitiMeeting_recyclerview)
    RecyclerView rv_samitiMeeting_recyclerview;
    @BindView(R.id.addMember)
    FloatingActionButton addMember;

    InspectionAdapter inspectionAdapter;
    private static final int INSPECTION_LIST_REQUEST_CODE = 112;

    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    List<InspectionStayModel> inspectionStayModelList = new ArrayList<>();
    ;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount = 0;
    int page = 1;
    private boolean isNoMoreItems = false;
    String current_list;
    VillageListModel v_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_day);
        ButterKnife.bind(this);
        tv_title.setText(getResources().getString(R.string.inspection_report));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(InspectionDay.this, 1);
        rv_samitiMeeting_recyclerview.setLayoutManager(gridLayoutManager);
        if (getIntent().getStringExtra("type") != null) {
            Intent i = getIntent();
            type = i.getStringExtra("type");
            v_data = i.getParcelableExtra("v_data");

            switch (type) {
//                case "1":
//                    tv_title.setText("Update owner details");
//                    break;
//                case "2":
//                    tv_title.setText("Add owner details");
//                    break;
                case "10":

                    break;
            }
        } else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")) {
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
        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InspectionDay.this, InspectionDayAdd.class);
                intent.putExtra("v_id", v_data);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });

        rv_samitiMeeting_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    // getVillageList();

                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                page = 1;

                inspectionStayModelList.clear();
                getVillageList();

                //
            }
        });
    }


    private void getVillageList() {
        UserService.getInspectionList(InspectionDay.this,  this, INSPECTION_LIST_REQUEST_CODE);
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
        switch (requestCode) {
            case INSPECTION_LIST_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d(TAG, "onResponse: " + "RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: " + baseResponseModule.getInspectionStayModelList().size());
                            current_list = baseResponseModule.getList_name();
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);

                            tvEmpty.setVisibility(View.GONE);
                            if (!(baseResponseModule.getInspectionStayModelList().size() > 0)) {
//                                    rv_samitiMeeting_recyclerview.setVisibility(View.GONE);
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page > 1) {
                                    inspectionStayModelList.addAll(baseResponseModule.getInspectionStayModelList());
                                    inspectionAdapter.notifyDataSetChanged();
                                } else {
                                    inspectionStayModelList.addAll(baseResponseModule.getInspectionStayModelList());
                                    inspectionAdapter = new InspectionAdapter(InspectionDay.this, inspectionStayModelList);
                                    rv_samitiMeeting_recyclerview.setAdapter(inspectionAdapter);
                                }
                                rv_samitiMeeting_recyclerview.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);

                            }
                            Log.d(TAG, "onResponse: setadpetr" + baseResponseModule.getInspectionStayModelList().toString());
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
//        page=1;
//        inspectionStayModelList.clear();
//        getVillageList();
    }
}