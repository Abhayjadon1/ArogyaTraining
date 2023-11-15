package com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.medicinegarden;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.SinglePlantMedFamAdapter;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.models.MedicineGardenModel;
import com.ispl.ekalarogya.training.models.PlantModel;
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

public class SingleFamilyGarden extends AppCompatActivity implements RetroAPICallback {
    private static final int SINGLE_MED_GARDEN_REQUEST_CODE = 151;
    private static final String TAG = SingleFamilyGarden.class.getSimpleName();
    SinglePlantMedFamAdapter medicineGardenAdapter;
    List<MedicineGardenModel> medicineGardenModelList = new ArrayList<>();
    List<PlantModel> plantModelList = new ArrayList<>();

    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.addMember)
    FloatingActionButton addMember;

    String type,id,v_id,h_id;

    @BindView(R.id.tv_family_head)
    TextView tv_family_head;
    @BindView(R.id.tv_family_id)
    TextView tv_family_id;

    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_edit)
    TextView tv_edit;


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_medicineGarden_recyclerview)
    RecyclerView rv_medicineGarden_recyclerview;
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
    MedicineGardenModel data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_family_med_garden);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(SingleFamilyGarden.this, 1);
        rv_medicineGarden_recyclerview.setLayoutManager(gridLayoutManager);
//        if (getIntent().getStringExtra("type")!=null) {
            Intent i = getIntent();
            data=i.getParcelableExtra("m_data");
            h_id=data.getHead_id();
            tv_family_head.setText(data.getName());
            tv_family_id.setText("ID"+data.getHead_id());
            getSingleFamilyMedGarden();

//        }



        setUpRefreshLayout();
        addMember.setVisibility(View.GONE);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(SingleFamilyGarden.this,AddMedicineGarden.class);
                i.putExtra("v_id",v_id);
                i.putExtra("h_id",h_id);
                Log.d(TAG, "onClick: hvgjkhjvgj : "+h_id+"    "+v_id);
                i.putExtra("type","add");
                startActivity(i);

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SingleFamilyGarden.this,MedicineGarden.class));
                finish();
            }
        });
        rv_medicineGarden_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    getSingleFamilyMedGarden();
                }
            }


        });

        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashSwipeRefreshLayout.setRefreshing(false);
                medicineGardenModelList.clear();
                page=1;
                getSingleFamilyMedGarden();

            }
        });
    }

    private void getSingleFamilyMedGarden() {
        UserService.getSingleMedicineGardenList(SingleFamilyGarden.this,h_id,String.valueOf(page),this,SINGLE_MED_GARDEN_REQUEST_CODE);

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
            case SINGLE_MED_GARDEN_REQUEST_CODE:
                if (response.isSuccessful()) {
                    addMember.setVisibility(View.GONE);

                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getMedicineGardenModels().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
//                            addMember.setVisibility(View.VISIBLE);
                            if(baseResponseModule.getMedicineGardenModels().size()>0)
                            {
                                Log.d(TAG, "onResponse: b hcxg  : "+baseResponseModule.getMedicineGardenModels());
                                if(baseResponseModule.getMedicineGardenModels().get(0).getVillage_id()!=null){
                                    v_id=baseResponseModule.getMedicineGardenModels().get(0).getVillage_id();
                                }else{
                                    addMember.setVisibility(View.GONE);
                                }
                                tvEmpty.setVisibility(View.GONE);
                                if (!(baseResponseModule.getMedicineGardenModels().size() > 0)) {
                                    rv_medicineGarden_recyclerview.setVisibility(View.GONE);
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    isNoMoreItems = true;

                                } else {
                                    if (page>1) {
                                        medicineGardenModelList.addAll(baseResponseModule.getMedicineGardenModels());
                                        medicineGardenAdapter.notifyDataSetChanged();
                                    } else {
                                        medicineGardenModelList.addAll(baseResponseModule.getMedicineGardenModels());
                                        medicineGardenAdapter = new SinglePlantMedFamAdapter(SingleFamilyGarden.this,medicineGardenModelList);
                                        rv_medicineGarden_recyclerview.setAdapter(medicineGardenAdapter);
                                    }
                                    rv_medicineGarden_recyclerview.setVisibility(View.VISIBLE);
                                    tvEmpty.setVisibility(View.GONE);

                                } Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getMedicineGardenModels().toString());
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