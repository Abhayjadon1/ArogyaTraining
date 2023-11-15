package com.ispl.ekalarogya.training.main.dashotheractivity.preventives.familyvisit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ispl.ekalarogya.training.R;

import com.ispl.ekalarogya.training.adapters.FamilyVisitListAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
import com.ispl.ekalarogya.training.rest.RestUtils;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.response.FamilyVisitModel;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class FamilyVisitList extends AppCompatActivity implements RetroAPICallback {
    private static final int HERBAL_LIST_REQUEST_CODE = 112;

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
    String type,id,v_id;

//    @BindView(R.id.llHead_malnutrition)
//    LinearLayout llHead_malnutrition;
//    @BindView(R.id.llHead_vill)
//    LinearLayout llHead_vill;


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_herbal_checkup_recyclerview)
    RecyclerView rv_herbal_checkup_recyclerview;
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


    private static final String TAG = FamilyVisitList.class.getSimpleName();
    FamilyVisitListAdapter familyVisitListAdapter;
    List<FamilyVisitModel> familyVisitModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_visit_list);
        ButterKnife.bind(this);


        if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
            tv_title.setText(getResources().getString(R.string.family_visit));
//            getHerbalRemedies();
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FamilyVisitList.this, 1);
        rv_herbal_checkup_recyclerview.setLayoutManager(gridLayoutManager);
        setUpRefreshLayout();


        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

//                AlertDialog.Builder builder = new AlertDialog.Builder(HerbalRemediesList.this);
//                builder.setTitle("Patient Type");
//                builder.setCancelable(true);
//                // add a list
//                String[] animals = {"Member", "Guest"};
//                builder.setItems(animals, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                Intent intent = new Intent(HerbalRemediesList.this, VanAushadhiAddMember.class);
//                                intent.putExtra("v_id", v_id);
//                                intent.putExtra("type", "add");
//                                startActivity(intent);
//                                break;
//                            case 1: // cow
//                                Intent intent2 = new Intent(HerbalRemediesList.this, AyurvedicAdd.class);
//                                intent2.putExtra("v_id", v_id);
//                                intent2.putExtra("type", "add");
//                                startActivity(intent2);
//                                break;
//
//                        }
//                    }
//                });
//
//                // create and show the alert dialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
                
                Intent intent = new Intent(FamilyVisitList.this, FamilyVisitAdd.class);
                intent.putExtra("type", "add");
                startActivity(intent);

            }

//                Intent intent = new Intent(HerbalRemediesList.this, AyurvedicAdd.class);
//                intent.putExtra("type", "add");
//                startActivity(intent);

        });

        rv_herbal_checkup_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    getHerbalRemedies();
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
                familyVisitModelList.clear();
                page=1;
                //
                getHerbalRemedies();
            }
        });
        getHerbalRemedies();
    }

    private void getHerbalRemedies() {
        UserService.getfamilyvisitList(this,String.valueOf(page),this,HERBAL_LIST_REQUEST_CODE);
    }

    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHerbalRemedies();
                dashSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case HERBAL_LIST_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseresponse = (BaseResponse) response.body();
                    if (baseresponse != null) {
                        if (baseresponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseresponse.getFamilyVisitModelList().size());
                            current_list=baseresponse.getList_name();
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);
                            if(baseresponse.getFamilyVisitModelList().size()>0)
                            {

                                tvEmpty.setVisibility(View.GONE);
                                if (!(baseresponse.getFamilyVisitModelList().size() > 0)) {
                                    rv_herbal_checkup_recyclerview.setVisibility(View.GONE);
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    isNoMoreItems = true;

                                } else {
                                    if (page>1) {
                                        familyVisitModelList.addAll(baseresponse.getFamilyVisitModelList());
                                        familyVisitListAdapter.notifyDataSetChanged();
                                    } else {
                                        familyVisitModelList.clear();
                                        familyVisitModelList.addAll(baseresponse.getFamilyVisitModelList());
                                        familyVisitListAdapter = new FamilyVisitListAdapter(FamilyVisitList.this,familyVisitModelList);
                                        rv_herbal_checkup_recyclerview.setAdapter(familyVisitListAdapter);
                                    }
                                    rv_herbal_checkup_recyclerview.setVisibility(View.VISIBLE);
                                    tvEmpty.setVisibility(View.GONE);

                                } Log.d(TAG, "onResponse: setadpetr"+baseresponse.getFamilyVisitModelList().toString());
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

    @Override
    protected void onResume() {
        super.onResume();
//        page=1;
//
//        getHerbalRemedies();
    }
}