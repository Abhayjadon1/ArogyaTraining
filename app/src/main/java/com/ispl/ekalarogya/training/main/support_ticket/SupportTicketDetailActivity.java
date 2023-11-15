package com.ispl.ekalarogya.training.main.support_ticket;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ispl.ekalarogya.training.BuildConfig;;
import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.ispl.ekalarogya.training.databinding.ActivitySupportTicketDetailBinding;
//import com.inventics.ekalarogya.network.RetroAPICallback;
//import com.inventics.ekalarogya.network.UserService;
//import com.inventics.ekalarogya.network.reponse.BaseResponse;
//import com.inventics.ekalarogya.utils.AppUrl;
import com.ispl.ekalarogya.training.rest.response.BaseResponse;
import com.ispl.ekalarogya.training.rest.service.UserService;
import com.ispl.ekalarogya.training.utils.DateUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Response;

public class SupportTicketDetailActivity extends AppCompatActivity implements RetroAPICallback {

    public static final int GET_GRAMOTTHAN_DETAIL=1;
    public static final int ADD_CHAT=2;
    private static final String TAG = SupportTicketActivity.class.getSimpleName();
    ActivitySupportTicketDetailBinding binding;

    String ticket_id;
    String status="open";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySupportTicketDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Support Detail");
        if(getIntent().hasExtra("id")){
            ticket_id =  getIntent().getStringExtra("id");
            Log.d(TAG, "ticket_id: "+ticket_id);
            getDetail();
        }
    }

    private void getDetail() {
        UserService.spportTicketDetail(SupportTicketDetailActivity.this, ticket_id,this, GET_GRAMOTTHAN_DETAIL);
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode) {
            case GET_GRAMOTTHAN_DETAIL:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatusHelp().equalsIgnoreCase("success")) {
                            if (baseResponse.getViewTicket()!=null) {
                                setData(baseResponse.getViewTicket());
                               if ( baseResponse.getStatusHelp().equalsIgnoreCase("Closed")){
                                   binding.etRemark.setEnabled(false);
                                   binding.radioButtonClose.setEnabled(false);
                                   binding.radioButtonOpen.setEnabled(false);
                                   binding.btnChatSubmit.setEnabled(false);
                                   binding.radioButtonClose.setChecked(true);
                               }
                            }
                            if(baseResponse.getViewTicket().getChat().size()>0){
                                binding.conversationCard.setVisibility(View.VISIBLE);
                            }else{
                                binding.conversationCard.setVisibility(View.GONE);
                            }


                        }
                    }
                }
                break;

                case ADD_CHAT:
                if (response.isSuccessful()) {
                    BaseResponse baseResponse = (BaseResponse) response.body();
                    if (baseResponse != null) {
                        if (baseResponse.getStatusHelp().equalsIgnoreCase("success")) {
                            Toast.makeText(this, baseResponse.getStatusMessageforhelp(), Toast.LENGTH_SHORT).show();
                            binding.etRemark.setText("");
                            getDetail();
                        }
                    }
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setData(SupportTicket viewTicket) {
        binding.msg.setText(viewTicket.getMessage());
        binding.ticketId.setText(viewTicket.getTicket_id());
        binding.ticketSub.setText(viewTicket.getSubject());
        binding.tvStatus.setText(viewTicket.getStatus());
        if (viewTicket.status.equalsIgnoreCase("Closed"))
            binding.tvStatus.setText(getResources().getString(R.string.closed));
        else if(viewTicket.status.equalsIgnoreCase("New"))
            binding.tvStatus.setText(getResources().getString(R.string.new_ticket));

        binding.tvDateTicket.setText(DateUtils.parseDate(viewTicket.getCreated_at(),DateUtils.DATE_FORMATE,DateUtils.SERVER_FORMAT2));
       // binding.tvDateTicket.setText(DateUtils.parseStringDate(viewTicket.getCreated_at(),DateUtils.BOOKING_SERVER_DATE_FORMAT,DateUtils.REPORT_DATE_FORMAT));

        Log.d(TAG, "guestticket====: "+BuildConfig.HELP_IMG_SUPPORT+"public/uploads/guestticket/"+viewTicket.getFile_name());
//        Glide.with(SupportTicketDetailActivity.this).load(BuildConfig.BASE_URL+"/public/uploads/guestticket/"+viewTicket.getFile_name())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(binding.ivImage);

//        if (viewTicket.getFile_name().contains(""))
        Picasso.with(this).load(BuildConfig.HELP_IMG_SUPPORT+viewTicket.getFile_name()).error(R.drawable.add_photo).into(binding.ivImage);




        binding.supportCnversation.setLayoutManager(new LinearLayoutManager(this));

        binding.supportCnversation.setAdapter(new ConversationAdapter(SupportTicketDetailActivity.this,viewTicket.getChat()));

    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {

    }

    @Override
    public void onNoNetwork(int requestCode) {

    }


    @Override
    public void onResume() {
        super.onResume();

        binding.radioGroupGenter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 int id = radioGroup.getCheckedRadioButtonId();
                 if(id == R.id.radio_button_open){
                     status = "open";
                 }else{
                     status = "close";
                 }
            }
        });

        binding.btnChatSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etRemark.getText().toString())){
                    binding.etRemark.setError(getResources().getString(R.string.enter_ticket_name));
                }else{
                    addChatMsg();
                }
            }
        });
    }

    private void addChatMsg(){
        UserService.sendConversationMsg(SupportTicketDetailActivity.this, binding.etRemark.getText().toString(),status,ticket_id,this, ADD_CHAT);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}