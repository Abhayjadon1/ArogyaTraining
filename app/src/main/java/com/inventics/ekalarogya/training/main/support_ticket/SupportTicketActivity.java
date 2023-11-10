package com.inventics.ekalarogya.training.main.support_ticket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.helper.ProductImagesPagerAdapter;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SupportTicketActivity extends AppCompatActivity implements View.OnClickListener, RetroAPICallback, ProductImagesPagerAdapter.DeleteImage {

    private static final String TAG = SupportTicketActivity.class.getSimpleName();
    private static final int GET_GRAMOTTHAN_DETAIL = 1;
    RecyclerView rvMilitary;
    TextView errorText;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_type);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Support Ticket");
        fab = findViewById(R.id.fab);
        rvMilitary = findViewById(R.id.rvMilitary);
        errorText = findViewById(R.id.tvError);
        fab.setOnClickListener(this);

    }

//    @Override
//    protected int getLayoutResource() {
//        return 0;
//    }

    private void getGramotthanDetails() {
        UserService.spportTicket(SupportTicketActivity.this, this, GET_GRAMOTTHAN_DETAIL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void GetMilitaryList(List<SupportTicket> arogyaTypeModels) {
        if (arogyaTypeModels.size() > 0) {

            rvMilitary.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rvMilitary.setVisibility(View.VISIBLE);
            SupportTicketAdapter adapter1 = new SupportTicketAdapter(getApplicationContext(), arogyaTypeModels, new SupportTicketAdapter.OnItemClickListner() {
                @Override
                public void onItemClick(SupportTicket gramotthanModel) {
                    Intent intent = new Intent(SupportTicketActivity.this,SupportTicketDetailActivity.class);
                    intent.putExtra("id",gramotthanModel.ticket_id);
                    startActivity(intent);
                }
            });
            rvMilitary.setAdapter(adapter1);
        } else {
            rvMilitary.setVisibility(View.GONE);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                //PupupDialog(false, null);
                startActivity(new Intent(this,AddSupportTicketActivity.class));
                break;

        }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case GET_GRAMOTTHAN_DETAIL:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                       try {
                           if (baseResponse.getStatusHelp().equalsIgnoreCase("success")) {
                               if (baseResponse.getTicket_list()!=null && baseResponse.getTicket_list().size()>0) {
                                   Log.d(TAG, "getTicket_list: "+baseResponse.getTicket_list().toString());
                                   errorText.setVisibility(View.GONE);
                                   rvMilitary.setVisibility(View.VISIBLE);
                                   GetMilitaryList(baseResponse.getTicket_list());
                               }else{
                                   rvMilitary.setVisibility(View.GONE);
                                   errorText.setVisibility(View.VISIBLE);
                                   errorText.setText(baseResponse.getStatusMessageforhelp());
                               }
                           }
                       }catch (Exception e){
                           Log.d(TAG, "onResponse: exception : "+e.getMessage());
                       }
                    }
                }
                break;
        }
    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {

    }

    @Override
    public void onNoNetwork(int requestCode) {

    }

    @Override
    public void deleteListner(String id) {

    }
    @Override
    public void onResume() {
        super.onResume();
        getGramotthanDetails();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }



}