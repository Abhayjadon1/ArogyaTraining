package com.inventics.ekalarogya.training.main.dashotheractivity.preventives.socialSanitisation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.SocialSanitisationVillageListAdapter;
import com.inventics.ekalarogya.training.adapters.SocialSanitisationAdapter;
import com.inventics.ekalarogya.training.adapters.VillageFamilyAdapter;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;

import com.inventics.ekalarogya.training.helper.PreferenceManger;
import com.inventics.ekalarogya.training.models.SocialSanitisationModel;
import com.inventics.ekalarogya.training.models.VillageFamilyModel;
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

public class SocialSanitisation extends AppCompatActivity implements RetroAPICallback {
    private static final int SOCIAL_SANITISATION_REQUEST_CODE = 111;
    private static final int VILLAGE_LIST_REQUEST_CODE = 112;
    private static final int VILLAGE_FAMILY_REQUEST_CODE = 113;

    private static final String TAG = SocialSanitisation.class.getSimpleName();
    SocialSanitisationAdapter socialSanitisationAdapter;
    List<SocialSanitisationModel> socialSanitisationModelList=new ArrayList<>();
    SocialSanitisationVillageListAdapter socialSanitisationVillageListAdapter;
    VillageFamilyAdapter villageFamilyAdapter;

    int page=1;
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
    @BindView(R.id.switchButton)
    SwitchCompat switchButton;

    String type,id;
    @BindView(R.id.ll_village_listing_head)
    LinearLayout ll_village_listing;
    @BindView(R.id.ll_ss)
    LinearLayout ll_ss;

    @BindView(R.id.ll_ssanitisation_listing_head)
    LinearLayout ll_ssanitisation_listing_head;

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
    int status=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_sanitisation);
        ButterKnife.bind(this);
        ll_ssanitisation_listing_head.setVisibility(View.GONE);
        ll_village_listing.setVisibility(View.VISIBLE);
        addMember.setVisibility(View.GONE);
        switchButton.setVisibility(View.GONE);
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
                    ll_ssanitisation_listing_head.setVisibility(View.VISIBLE);
                    ll_village_listing.setVisibility(View.GONE);
                    addMember.setVisibility(View.VISIBLE);
                    switchButton.setVisibility(View.GONE);
                    switchButton.setChecked(true);
//                    getVillageChildList(id);
                    break;
            }
        }else {
            if (ArogyaApplication.getPreferenceManager().getUserManager(PreferenceManger.LOGIN_DETAILS).getRole_id().equalsIgnoreCase("9")){
                getVillageList();
            }
        }
        setUpRefreshLayout();
        ll_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SocialSanitisation.this,R.style.MyAlertDialogStyle);
                builder.setMessage(R.string.tckw);
                builder.setTitle(getResources().getString(R.string.social_sanitation));
                builder.setCancelable(true);
                builder.setPositiveButton(getResources().getString(R.string.yes), (DialogInterface.OnClickListener) (dialog, which) -> {
                   dialog.dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                socialSanitisationModelList.clear();

                page=1;
                if (switchButton.isChecked()){
                    status=1;
                    if (socialSanitisationAdapter!=null)
                        socialSanitisationAdapter.notifyDataSetChanged();
                    getVillageChildList(id);
                }else{
                    status=0;
                    if (socialSanitisationAdapter!=null)
                        socialSanitisationAdapter.notifyDataSetChanged();
                    getVillageChildList(id);
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
        // getVillagefamily();

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SocialSanitisation.this, SocialSanitisationFamily.class);
                intent.putExtra("v_id",id);
                Log.d(TAG, "onCreate putExtra: "+id);
                startActivity(intent);
            }
        });
    }

    private void getVillageChildList(String id) {
        UserService.getSocialSanitisationList(SocialSanitisation.this, String.valueOf(status),id,this,SOCIAL_SANITISATION_REQUEST_CODE);

    }

    private void getVillageList() {
        UserService.getVillageList(SocialSanitisation.this,this,VILLAGE_LIST_REQUEST_CODE);
    }


    private void setUpRefreshLayout() {
        dashSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColorSchemeArray[0], swipeRefreshColorSchemeArray[1], swipeRefreshColorSchemeArray[2], swipeRefreshColorSchemeArray[3], swipeRefreshColorSchemeArray[4], swipeRefreshColorSchemeArray[5],
                swipeRefreshColorSchemeArray[6]);
        dashSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(type!=null&&type.equalsIgnoreCase("10")){
                    socialSanitisationModelList.clear();
                    getVillageChildList(id);
                }
                else if (getIntent().getStringExtra("id")!=null){
                    getVillageList();
                }
                dashSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case SOCIAL_SANITISATION_REQUEST_CODE:
                if (response.isSuccessful()) {
                    BaseResponse baseResponseModule = (BaseResponse) response.body();
                    if (baseResponseModule != null) {
                        if (baseResponseModule.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)){
                            Log.d(TAG, "onResponse: "+"RestUtils.SUCCESS getmodule");
                            Log.d(TAG, "onResponse: "+baseResponseModule.getSocialSanitisationModelList().size());
                             current_list=baseResponseModule.getList_name();
  //current_list=ArogyaApplication.getTranslate(this,baseResponseModule.getList_name());
   Log.d(TAG, "onResponse: current_list "+current_list);
                            //tv_title.setText(current_list);
                            if(baseResponseModule.getSocialSanitisationModelList().size()>0)
                            {
                                if (!socialSanitisationModelList.containsAll(baseResponseModule.getSocialSanitisationModelList())){
                                    socialSanitisationModelList.addAll(baseResponseModule.getSocialSanitisationModelList());
                                    socialSanitisationAdapter = new SocialSanitisationAdapter(SocialSanitisation.this,socialSanitisationModelList);
                                    rv_village_family_recyclerview.setAdapter(socialSanitisationAdapter);
                                }else{

                                }

                                tvEmpty.setVisibility(View.GONE);
                                rv_village_family_recyclerview.setLayoutManager(new LinearLayoutManager(this));
                                rv_village_family_recyclerview.setHasFixedSize(true);

                                socialSanitisationAdapter.notifyDataSetChanged();
                                Log.d(TAG, "onResponse: setadpetr"+socialSanitisationModelList);
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
                                socialSanitisationVillageListAdapter = new SocialSanitisationVillageListAdapter(SocialSanitisation.this,baseResponseModule.getVillageListModelList());
                                rv_village_family_recyclerview.setAdapter(socialSanitisationVillageListAdapter);
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
                                villageFamilyAdapter = new VillageFamilyAdapter(SocialSanitisation.this,baseResponseModule.getVillageFamilyModelList());
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
//        page=1;
//        if(type!=null&&type.equalsIgnoreCase("10")){
//            socialSanitisationModelList.clear();
//            getVillageChildList(id);
//        }
//        else if (getIntent().getStringExtra("id")!=null){
//            getVillageList();
//        }
    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if(type!=null&&type.equalsIgnoreCase("10")){
//            getVillageChildList(id);
//        }
//        else if (getIntent().getStringExtra("id")!=null){
//            getVillageList();
//        }
//    }

}