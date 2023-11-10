package com.inventics.ekalarogya.training.main.dashotheractivity.arogyacamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.ArogyaCampAdapter;
import com.inventics.ekalarogya.training.adapters.ArogyaCampVillageListAdpater;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.ArogyaCampModel;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.DateUtils;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class ArogyaCamp  extends AppCompatActivity implements RetroAPICallback {
    private static final int VILLAGE_AROGYA_CAMP_REQUEST_CODE = 111;
    private static final int SINGLE_FISRT_AID_REQUEST_CODE = 151;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int GET_VILLAGE_LIST = 117;
    private static final String TAG = ArogyaCamp.class.getSimpleName();
    ArogyaCampAdapter arogyaCampAdapter;
    List<ArogyaCampModel> arogyaCampModel = new ArrayList<>();
    ArogyaCampVillageListAdpater villageListAdapter;
    VillageListArrayAdapter adapter;
    private List<VillageListModel> villName = new ArrayList<>();


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

    @BindView(R.id.llHead_firstAid)
    LinearLayout llHead_firstAid;
    @BindView(R.id.llHead_vill)
    LinearLayout llHead_vill;


    @BindView(R.id.iv_filter)
    ImageView iv_filter;
    @BindView(R.id.card_filter)
    CardView card_filter;
    @BindView(R.id.spinner_village)
    Spinner spinner_village;
    @BindView(R.id.spinner_month)
    Spinner spinner_month;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_reset)
    TextView tv_reset;
    @BindArray(R.array.months_array)
    String[] monthsArray;


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_arogya_camp_recyclerview)
    RecyclerView rv_arogya_camp_recyclerview;
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
    String current_list,search_village,months;;
    VillageListModel v_data;
    ArogyaCampModel m_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arogya_camp);
        ButterKnife.bind(this);

        iv_filter.setVisibility(View.GONE);
        llHead_firstAid.setVisibility(View.VISIBLE);
        llHead_vill.setVisibility(View.GONE);
//        getVillageList();
        addMember.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ArogyaCamp.this, 1);
        rv_arogya_camp_recyclerview.setLayoutManager(gridLayoutManager);
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
                    llHead_firstAid.setVisibility(View.VISIBLE);
                    v_id=v_data.getVillage_id();
//                    if (v_id!=null){
//                        getArogyaCamp(v_id);
//                    }
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                llHead_vill.setVisibility(View.GONE);
                llHead_firstAid.setVisibility(View.VISIBLE);
                getArogyaCamp(v_id);
//                getVillageList();
            }
        }
        setUpRefreshLayout();
        populateMonthlySpinner();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArogyaCamp.this, ArogyaCampAdd.class);
                intent.putExtra("v_id", v_id);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card_filter.getVisibility()==View.VISIBLE){

                    card_filter.setVisibility(View.GONE);
                    search_village=null;
                    months=null;
                    page=1;
                    arogyaCampModel.clear();
                    getArogyaCamp(v_id);

                }else if (card_filter.getVisibility()==View.GONE){
                    card_filter.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                arogyaCampModel.clear();
                getArogyaCamp(v_id);
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                card_filter.setVisibility(View.GONE);
                search_village=null;
                months=null;
                arogyaCampModel.clear();
                getArogyaCamp(v_id);
            }
        });

        rv_arogya_camp_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

//                    if (v_id!=null){
//                        getArogyaCamp(v_id);
//                    }else {
//                        getVillageList();
//                    }
                    getArogyaCamp(v_id);

                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                page=1;
                arogyaCampModel.clear();
                getArogyaCamp(v_id);
                //
            }
        });
    }

//    private void getSingleFamilyMedGarden() {
//        UserService.getSingleFirstAidList(ArogyaCamp.this,h_id,String.valueOf(page),this,SINGLE_FISRT_AID_REQUEST_CODE);
//
//    }

    private void getArogyaCamp(String v_id) {
        UserService.getArogyaCampList(ArogyaCamp.this,v_id,this,VILLAGE_AROGYA_CAMP_REQUEST_CODE);

    }


    private void getVillageList() {
        UserService.getVillageList(ArogyaCamp.this,this,GET_VILLAGE_LIST);
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

            case VILLAGE_AROGYA_CAMP_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getArogyaCampModels().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);

                            tvEmpty.setVisibility(View.GONE);
                            if (!(baseResponseModule.getArogyaCampModels().size() > 0)) {
//                                    rv_arogya_camp_recyclerview.setVisibility(View.GONE);
                                tvEmpty.setVisibility(View.VISIBLE);
                                isNoMoreItems = true;

                            } else {
                                if (page>1) {
                                    if (!(arogyaCampModel.contains(baseResponseModule.getArogyaCampModels()))){

                                    }else{
                                        arogyaCampModel.addAll(baseResponseModule.getArogyaCampModels());
                                        arogyaCampAdapter.notifyDataSetChanged();

                                    }
                                } else {
                                    arogyaCampModel.addAll(baseResponseModule.getArogyaCampModels());
                                    arogyaCampAdapter = new ArogyaCampAdapter(ArogyaCamp.this,arogyaCampModel);
                                    rv_arogya_camp_recyclerview.setAdapter(arogyaCampAdapter);
                                    arogyaCampAdapter.notifyDataSetChanged();
                                }
                                rv_arogya_camp_recyclerview.setVisibility(View.VISIBLE);
                                tvEmpty.setVisibility(View.GONE);

                            } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getArogyaCampModels().toString());
                        }

                    }
                }
                break;

            case GET_VILLAGE_LIST:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            if(baseResponse.getVillageListModelList().size()>0){
                                // we pass our item list and context to our Adapter.
                                Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().size());
                                spinner_village.setAdapter(adapter);
//                                List<VillageListModel> villageListModelList = new ArrayList<>();
                                try {
                                    for(int i=0; i <=baseResponse.getVillageListModelList().size(); i++){
                                        villName.add(baseResponse.getVillageListModelList().get(i));
                                        Log.d("TAG", "onResponse: size "+baseResponse.getVillageListModelList().get(i).getVillage_name());
                                        adapter = new VillageListArrayAdapter(this, villName);
                                        adapter.notifyDataSetChanged();
                                    }
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: exception"+e.getMessage());
                                }
                                spinner_village.setAdapter(adapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
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
                            rv_arogya_camp_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                            rv_arogya_camp_recyclerview.setHasFixedSize(true);
                            villageListAdapter = new ArogyaCampVillageListAdpater(ArogyaCamp.this,baseResponseModule.getVillageListModelList());
                            rv_arogya_camp_recyclerview.setAdapter(villageListAdapter);
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


    private void populateMonthlySpinner() {
        //monthSpinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_dropdown, monthsArray);
        spinner_month.setAdapter(adapter);
        spinner_month.setSelection(getCurrentMonthIndex());
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemSelected: " + monthsArray[i]);
//                selectMonth= DateUtils.getTodayDateFromMonth(i  );
                months=String.valueOf(i);
                Log.d("TAG", "onItemSelected: "+months);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private int getCurrentMonthIndex() {
        String currentMonth = DateUtils.getDateAndTime(System.currentTimeMillis(), DateUtils.MONTH_FORMAT);
        if (TextUtils.isEmpty(currentMonth))
            return 0;
        for (int i = 0; i < monthsArray.length; i++) {
            if (monthsArray[i].equalsIgnoreCase(currentMonth)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        page=1;
        if (arogyaCampModel!=null){
            arogyaCampModel.
                    clear();
        }
        getArogyaCamp(v_id);
    }
}