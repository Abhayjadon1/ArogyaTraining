package com.ispl.ekalarogya.training.main.dashboardFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.DashboardAdapter;
import com.ispl.ekalarogya.training.app_base.BaseFragment;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.DashBoardModel;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sonu on 12:08, 2019-06-12
 * Copyright (c) 2019 . All rights reserved.
 */
public class HomeFragment extends BaseFragment implements RetroAPICallback{
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int DASHBOARD_REQ_CODE = 11;
    List<DashBoardModel> dashBoardModel;
    private DashboardAdapter dashboardAdapter;
    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.home_fragment;
    }
    private Context context;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindView(R.id.recyclerviewDash)
    RecyclerView recyclerviewDash;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    DashboardAdapter dashboardAdaptr;



    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this,view);
        initComponents(view);

    }

    public void initComponents(View view) {
        context = getContext();
        setUpRefreshLayout();
        setUpRecyclerView();
        getModuleVisibility();
    }
    private void getModuleVisibility() {
        UserService.getDashboard(getActivity(),this,DASHBOARD_REQ_CODE);
    }

    private void setUpRecyclerView() {
        recyclerviewDash.setHasFixedSize(true);
        dashboardAdapter = new DashboardAdapter(context,dashBoardModel);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerviewDash.setLayoutManager(gridLayoutManager);
        recyclerviewDash.setNestedScrollingEnabled(false);
    }

    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getModuleVisibility();
                dashSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case DASHBOARD_REQ_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getDashBoardModelList().size());

                            if(baseResponseModule.getDashBoardModelList().size()>0)
                            {
                                dashBoardModel =baseResponseModule.getDashBoardModelList();
                                DashBoardModel dashNew =
                                        new DashBoardModel("Support", "14", getContext().getResources().getString(R.string.support));

                                dashBoardModel.add(dashNew);

                                Log.d(TAG, "onResponse: "+baseResponseModule.getDashBoardModelList().toString());
                                dashboardAdaptr = new DashboardAdapter(context,baseResponseModule.getDashBoardModelList());
                                recyclerviewDash.setAdapter(dashboardAdaptr);
                                dashboardAdapter.notifyDataSetChanged();
//                                recyclerviewDash.setVisibility(View.VISIBLE);
//                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
//                                recyclerviewDash.setLayoutManager(gridLayoutManager);
//                                DashboardAdapter adapter=new DashboardAdapter(context,baseResponseModule.getDashBoardModelList());
//                                recyclerviewDash.setAdapter(adapter);
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
