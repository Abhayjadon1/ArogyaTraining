package com.ispl.ekalarogya.training.main.dashotheractivity.preventives.hygiene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.adapters.PersonalHysgineAdaper;
import com.ispl.ekalarogya.training.adapters.VillageFamilyAdapter;
import com.ispl.ekalarogya.training.adapters.PersnalHygineVillageListAdapter;
import com.ispl.ekalarogya.training.app_base.ArogyaApplication;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.helper.PreferenceManger;
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

public class PersonalHygine extends AppCompatActivity implements RetroAPICallback {
    private static final int CHILD_HYGINE_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int VILLAGE_FAMILY_REQUEST_CODE = 113;

    private static final String TAG = PersonalHygine.class.getSimpleName();
    PersonalHysgineAdaper personalHysgineAdaper;
    PersnalHygineVillageListAdapter villageListAdapter;
    VillageFamilyAdapter villageFamilyAdapter;
    List<VillageFamilyModel> villageMobileList = new ArrayList<>();


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
    String type,id;
    @BindView(R.id.ll_village_listing_head)
    LinearLayout ll_village_listing;

    @BindView(R.id.ll_hygine_listing_head)
    LinearLayout ll_personal_hygeine;
    int page=1;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_village_family_recyclerview)
    RecyclerView rv_village_family_recyclerview;
    @BindView(R.id.dashSwipeRefreshLayout)
    SwipeRefreshLayout dashSwipeRefreshLayout;
    @BindArray(R.array.swipe_refresh_color_scheme_array)
    int[] swipeRefreshColorSchemeArray;
    List<VillageFamilyModel> villageFamilyModels;

    String current_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_hygine);
        ButterKnife.bind(this);
        addMember.setVisibility(View.GONE);
        ll_personal_hygeine.setVisibility(View.GONE);
        ll_village_listing.setVisibility(View.VISIBLE);

        if (getIntent().getStringExtra("id")!=null){
            Intent i = getIntent();
            type=i.getStringExtra("type");
            id=i.getStringExtra("id");
            Log.d("TAG", "onCreate: getintent "+" "+type+id);
            switch (type) {
                case "1":
                    tv_title.setText(R.string.update_owner);
                    break;
                case "2":
                    tv_title.setText(R.string.add_owner_details);
                    break;
                case "10":
                    tv_title.setText(R.string.family_list);
                    ll_personal_hygeine.setVisibility(View.VISIBLE);
                    ll_village_listing.setVisibility(View.GONE);
                    getVillageChildList(id);
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                getVillageList();
            }
        }
        setUpRefreshLayout();


        // getVillagefamily();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PersonalHygine.this, Preventive.class));
                finish();
            }
        });
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalHygine.this,PersonalHygineFamily.class);
                intent.putExtra("v_id",id);
                Log.d(TAG, "onCreate putExtra: "+id);
                startActivity(intent);
            }
        });
    }

    private void getVillageChildList(String id) {
        UserService.getPersonalHygineList(PersonalHygine.this,id,this,CHILD_HYGINE_REQUEST_CODE);

    }


    private void getVillageList() {
        UserService.getVillageList(PersonalHygine.this,this,VILLAGE_LIST_REQUEST_CODE);
    }


    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (id!=null){
                    villageMobileList.clear();
                    getVillageChildList(id);

                }else{
                    getVillageList();
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
                            Log.d(TAG, "onResponse: "+baseResponseModule.getPersonal_hygiene_list().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            addMember.setVisibility(View.VISIBLE);
                            if(baseResponseModule.getPersonal_hygiene_list().size()>0)
                            {
                                tvEmpty.setVisibility(View.GONE);
                                rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_village_family_recyclerview.setHasFixedSize(true);
                                personalHysgineAdaper = new PersonalHysgineAdaper(PersonalHygine.this,baseResponseModule.getPersonal_hygiene_list());
                                rv_village_family_recyclerview.setAdapter(personalHysgineAdaper);
                                Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getPersonal_hygiene_list().toString());
                            }
                        }
                    }
                }
                break;
            case VILLAGE_LIST_REQUEST_CODE:
                if (response.isSuccessful()){
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
                                rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_village_family_recyclerview.setHasFixedSize(true);
                                villageListAdapter = new PersnalHygineVillageListAdapter(PersonalHygine.this,baseResponseModule.getVillageListModelList());
                                rv_village_family_recyclerview.setAdapter(villageListAdapter);
                                Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageListModelList().toString());
                            }
                        }
                    }
                }
                break;
            case VILLAGE_FAMILY_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getVillageFamilyModelList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            if(baseResponseModule.getVillageFamilyModelList().size()>0)
                            {
                                tvEmpty.setVisibility(View.GONE);
                                rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_village_family_recyclerview.setHasFixedSize(true);
                                villageFamilyAdapter = new VillageFamilyAdapter(PersonalHygine.this,baseResponseModule.getVillageFamilyModelList());
//                                rv_village_family_recyclerview.setAdapter(villageFamilyAdapter);
                                Log.d(TAG, "onResponse: setadpetr"+baseResponseModule.getVillageFamilyModelList().toString());
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
        if (id!=null){
            villageMobileList.clear();
            getVillageChildList(id);
        }
    }
}