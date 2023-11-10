package com.inventics.ekalarogya.training.main.dashotheractivity.deseaseprevention;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.DiseasePreventionAdapter;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.response.DiseasePreventionListResponse;
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

public class DiseasePrevention extends AppCompatActivity implements RetroAPICallback {
    private static final int DISEASE_PREVENTION_REQUEST_CODE = 111;
    @BindView(R.id.rv_disease_prevention_recyclerview)
    RecyclerView rv_disease_prevention_recyclerview;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

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

    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    @BindView(R.id.addMember)
    FloatingActionButton addMember;
    private static final int GET_VILLAGE_LIST=115;
    private static final int GET_MEETING_DETAILS=215;
    private List<VillageListModel> villName = new ArrayList<>();
    boolean isEdit=false;
    VillageListArrayAdapter adapter;
    String vid;
    List<DiseasePreventionListResponse> diseasePreventionListResponses;

    private boolean userScrolled = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    int empCount=0;
    int page=1;
    private boolean isNoMoreItems = false;
    String current_list,search_village,months;
    private static final String TAG = DiseasePrevention.class.getSimpleName();
    DiseasePreventionAdapter diseasePreventionAdapter;
    List<DiseasePreventionListResponse> diseasePreventionListResponsesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_prevention);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(DiseasePrevention.this, 1);
        rv_disease_prevention_recyclerview.setLayoutManager(gridLayoutManager);

        getVillageDiseasePrevention();

        getVillageList();
        populateMonthlySpinner();

        setUpRefreshLayout();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                VillageListModel clickedItem = (VillageListModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getVillage_name();
                search_village= clickedItem.getVillage_id();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogFragment newDiloag=new DialogFragment(R.layout.order_place_dialog_layout);
//                newDiloag.show(getSupportFragmentManager(),"Select Village");
                Intent intent = new Intent(DiseasePrevention.this, AddDiseaseVillage.class);
                intent.putExtra("id", vid);
                intent.putExtra("type", "add");
                startActivity(intent);
            }
        });
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                page=1;
                diseasePreventionListResponsesList.clear();
                getVillageDiseasePrevention();

                //
            }
        });
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card_filter.getVisibility()==View.VISIBLE){

                   card_filter.setVisibility(View.GONE);
                    search_village=null;
                    months=null;
                    diseasePreventionListResponsesList.clear();
                    getVillageDiseasePrevention();

                }else if (card_filter.getVisibility()==View.GONE){
                    card_filter.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                diseasePreventionListResponsesList.clear();
                diseasePreventionAdapter.notifyDataSetChanged();
                getVillageDiseasePrevention();
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page=1;
                card_filter.setVisibility(View.GONE);
                search_village=null;
                months=null;
                diseasePreventionListResponsesList.clear();
                getVillageDiseasePrevention();
            }
        });
        rv_disease_prevention_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    getVillageDiseasePrevention();
                }
            }

          


        });

      
    }
    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }
    private void getVillageDiseasePrevention() {
        UserService.getDiseasePreventionList(DiseasePrevention.this,page,search_village,months,this,DISEASE_PREVENTION_REQUEST_CODE);
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
            case DISEASE_PREVENTION_REQUEST_CODE:
                if (response.isSuccessful()) {

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getDiseasePreventionListResponseList().size());
                             current_list=baseResponseModule.getList_name();
//  current_list= ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                           try {
                               tv_title.setText(getResources().getString(R.string.disease_prevention));
                           }catch (Exception e){
                               Log.d(TAG, "onResponse: "+e.getMessage());
                           }

                            if(baseResponseModule.getDiseasePreventionListResponseList().size()>0) {
                                tvEmpty.setVisibility(View.GONE);
//                                rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
//                                rv_village_family_recyclerview.setHasFixedSize(true);
                                if (!(baseResponseModule.getDiseasePreventionListResponseList().size() > 0)) {
                                    rv_disease_prevention_recyclerview.setVisibility(View.GONE);
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    isNoMoreItems = true;

                                } else {
                                    if (page>1) {
                                            if (!(diseasePreventionListResponsesList.contains(baseResponseModule.getDiseasePreventionListResponseList()))){
                                                diseasePreventionListResponsesList.addAll(baseResponseModule.getDiseasePreventionListResponseList());
                                            }else{

                                            }
                                        diseasePreventionAdapter.notifyDataSetChanged();
                                        } else {
                                        if (diseasePreventionListResponsesList.size()<1){
                                            diseasePreventionListResponsesList.addAll(baseResponseModule.getDiseasePreventionListResponseList());
                                        }else {
//                                              diseasePreventionListResponsesList.addAll(baseResponseModule.getDiseasePreventionListResponseList());
                                        }
                                        diseasePreventionAdapter = new DiseasePreventionAdapter(DiseasePrevention.this,diseasePreventionListResponsesList);
                                        rv_disease_prevention_recyclerview.setAdapter(diseasePreventionAdapter);
                                    }
                                    rv_disease_prevention_recyclerview.setVisibility(View.VISIBLE);
                                    tvEmpty.setVisibility(View.GONE);

                                } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getDiseasePreventionListResponseList().toString());
                            }

                        }
                    }
                }

                break;
        }

    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        ToastUtils.shortToast(t.getMessage());
        Log.d(TAG, "onFailure: "+t.getMessage());
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }

    protected void onResume() {
        super.onResume();
//        diseasePreventionListResponsesList.clear();
////        diseasePreventionAdapter.notifyDataSetChanged();
//        page=1;
//
//       getVillageDiseasePrevention();
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        page=1;
//        getVillageDiseasePrevention();
//    }
    private void populateMonthlySpinner() {
        //monthSpinner.getBackground().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_dropdown, monthsArray);
        spinner_month.setAdapter(adapter);
        spinner_month.setSelection(getCurrentMonthIndex());
        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                months=String.valueOf(adapterView.getItemAtPosition(i));
                Log.d("TAG", "onItemSelected: " + months);
//                selectMonth= DateUtils.getTodayDateFromMonth(i  );
//                months=String.valueOf(i);
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
}