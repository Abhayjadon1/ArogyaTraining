package com.inventics.ekalarogya.training.main.dashotheractivity.jagaranprogram;

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
import com.inventics.ekalarogya.training.adapters.JagaranAdapters;
import com.inventics.ekalarogya.training.adapters.JagaranVillageListAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.JagranProgramList;
import com.inventics.ekalarogya.training.models.JagranProgramModel;
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

public class JagranProgram  extends AppCompatActivity implements RetroAPICallback {
    private static final int JAGARAN_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final String TAG = JagranProgram.class.getSimpleName();
    private static String village_id,v_name;
    JagaranAdapters jagaranAdapters;
    List<JagranProgramModel> jagranProgramModelList = new ArrayList<>();
    List<JagranProgramList> jagranProgramList =new ArrayList<>();
    JagaranVillageListAdapter villageListAdapter;

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
    VillageListModel v_data;


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
    @BindView(R.id.llHead_vill)
    LinearLayout llHead_vill;
    @BindView(R.id.llVillage_visit)
    LinearLayout llVillage_visit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jagran_program);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(JagranProgram.this, 1);
        rv_villageVisit_recyclerview.setLayoutManager(gridLayoutManager);
        setUpRefreshLayout();

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
                    llVillage_visit.setVisibility(View.VISIBLE);
                    v_id=v_data.getVillage_id();
                    v_name=v_data.getVillage_name();
                    if (v_id!=null){
                        addMember.setVisibility(View.VISIBLE);
                        getVillagevisitList(v_id);
                    }
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                llHead_vill.setVisibility(View.VISIBLE);
                addMember.setVisibility(View.GONE);
                llVillage_visit.setVisibility(View.GONE);
                getVillageList();
            }
        }

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
                Intent intent = new Intent(JagranProgram.this, JagaranProgramAdd.class);
                intent.putExtra("v_id", v_data);

                intent.putExtra("type", "add");
                startActivity(intent);
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
                        getVillagevisitList(v_id);
                    }
                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                page=1;

                if (v_id!=null){
                    if (jagranProgramModelList.size()>0){
                        jagranProgramModelList.clear();
                    }
                    getVillagevisitList(v_id);
                }else{
                    getVillageList();
                }
            }
        });
    }



    private void getVillagevisitList(String v_id) {
        UserService.getJagranList(JagranProgram.this,v_id,this,JAGARAN_REQUEST_CODE);

    }



    private void getVillageList() {
        UserService.getVillageList(JagranProgram.this,this,VILLAGE_LIST_REQUEST_CODE);
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
            case JAGARAN_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getJagranProgramModelList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);

                            if (!(baseResponseModule.getJagranProgramModelList().size() > 0)) {
//                                tvEmpty.setVisibility(View.GONE);
//                                    rv_firstAid_recyclerview.setVisibility(View.GONE);
//                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page>1) {
                                    if (!(jagranProgramModelList.contains(baseResponseModule.getJagranProgramModelList()))){

                                    }else {
                                        jagranProgramModelList.addAll(baseResponseModule.getJagranProgramModelList());
//                                        addListData(baseResponseModule.getJagranProgramModelList());

////                                        jagranProgramList.addAll(baseResponseModule.getJagranProgramModelList().);
//                                        for (int i =0 ; i<baseResponseModule.getJagranProgramModelList().get(i).getProgram_details().size();i++){
////                                        if (jagranProgramList!=null&&!(jagranProgramList.contains(baseResponseModule.getJagranProgramModelList().get(i).getProgram_details()))){
////
////                                        }else {
//                                            jagranProgramList.add(baseResponseModule.getJagranProgramModelList().get(i).getProgram_details().get(i));
////                                        }
//                                        }
                                        jagaranAdapters.notifyDataSetChanged();
                                    }
                                } else {

                                    jagranProgramModelList.clear();
                                    jagranProgramModelList.addAll(baseResponseModule.getJagranProgramModelList());
//                                    addListData(baseResponseModule.getJagranProgramModelList());
//                                  try{
//                                      jagranProgramList.clear();
//                                       for (int i =0 ; i<baseResponseModule.getJagranProgramModelList().get(i).getProgram_details().size();i++){
////                                        if (jagranProgramList!=null&&!(jagranProgramList.contains(baseResponseModule.getJagranProgramModelList().get(i).getProgram_details()))){
////
////                                        }else {
//                                           jagranProgramList.add(baseResponseModule.getJagranProgramModelList().get(i).getProgram_details().get(i));
////                                        }
//                                       }
//                                    }catch (Exception e){
//                                      Log.d(TAG, "onResponse: exception logd i<baseResponseModule.getJagranProgramModelList().get(i) : ");
//                                  }
//                                    jagaranAdapters.notifyDataSetChanged();
                                    jagaranAdapters = new JagaranAdapters(JagranProgram.this,jagranProgramModelList,v_name);
                                    rv_villageVisit_recyclerview.setAdapter(jagaranAdapters);
                                    jagaranAdapters.notifyDataSetChanged();

                                }
                                rv_villageVisit_recyclerview.setVisibility(View.VISIBLE);
//                                tvEmpty.setVisibility(View.GONE);


                            } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getJagranProgramModelList().toString());
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
                            rv_villageVisit_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_villageVisit_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new JagaranVillageListAdapter(JagranProgram.this,baseResponseModule.getVillageListModelList());
                            rv_villageVisit_recyclerview.setAdapter(villageListAdapter);
                            Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageListModelList().toString());
                        }
                    }
                }

                break;

        }
    }

//    private void addListData(List<JagranProgramModel> jagranProgramModelList) {
//       for(int i=0;i<jagranProgramModelList.get(i).getProgram_details().size();i++){
//           jagranProgramList.addAll(jagranProgramModelList.get(i).getProgram_details());
//       }
//    }

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
        if (v_id!=null){
            jagranProgramModelList.clear();
            jagranProgramList.clear();
            getVillagevisitList(v_id);
        }else{
            getVillageList();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
//        this.recreate();
    }
}