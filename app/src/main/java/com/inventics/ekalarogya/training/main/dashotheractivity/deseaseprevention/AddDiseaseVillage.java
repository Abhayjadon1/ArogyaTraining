package com.inventics.ekalarogya.training.main.dashotheractivity.deseaseprevention;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.adapters.spinner.VillageListArrayAdapter;
import com.inventics.ekalarogya.training.app_interfaces.RetroAPICallback;
import com.inventics.ekalarogya.training.models.VillageListModel;
import com.inventics.ekalarogya.training.rest.RestUtils;
import com.inventics.ekalarogya.training.rest.response.BaseResponse;
import com.inventics.ekalarogya.training.rest.service.UserService;
import com.inventics.ekalarogya.training.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class AddDiseaseVillage extends AppCompatActivity implements RetroAPICallback, View.OnClickListener {
@BindView(R.id.ivDate)
    ImageView ivDate;
@BindView(R.id.et_inspection_date)
TextView et_inspection_date;
@BindView(R.id.iv_back)
ImageView iv_back;

@BindView(R.id.cb_cleanliness)
CheckBox cb_cleanliness;
@BindView(R.id.cb_maleria)
CheckBox cb_maleria;
@BindView(R.id.cb_nutrition)
CheckBox cb_nutrition;
@BindView(R.id.cb_vaccination)
CheckBox cb_vaccination;
@BindView(R.id.cb_maternal_safty)
CheckBox cb_maternal_safty;
@BindView(R.id.cb_child_safty)
CheckBox cb_child_safty;
    @BindView(R.id.cb_other)
    CheckBox cb_other;
//
//@BindView(R.id.et_no_of_famity)
//EditText et_no_of_famity;
@BindView(R.id.et_no_of_male)
EditText et_no_of_male;
@BindView(R.id.et_no_of_female)
EditText et_no_of_female;
@BindView(R.id.et_no_of_children)
EditText et_no_of_children;
@BindView(R.id.otherEt)
EditText otherEt;


@BindView(R.id.tv_total_presense)
TextView tv_total_presense;

@BindView(R.id.btn_submit)
    Button btn_submit;
@BindView(R.id.spin_village_name)
    Spinner spin_village_name;

private static final int ADD_DISEASE_CHECKUP=111;
private static final int GET_VILLAGE_LIST=115;

String inspection_date,clean="0",maleria="0",child="0",other="0",maternal="0",vaccinantion="0",nutrition="0",no_family="0",no_male="0",no_female="0",no_child="0",v_id="0",dateSubmit;
int total_no;
  List<VillageListModel> villageListModelList;
    private List<VillageListModel> villName = new ArrayList<>();

    VillageListArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disease_village);
        ButterKnife.bind(this);

        ivDate.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        et_inspection_date.setOnClickListener(this);

        cb_cleanliness.setOnClickListener(this);
        cb_child_safty.setOnClickListener(this);
        cb_other.setOnClickListener(this);
        cb_maleria.setOnClickListener(this);
        cb_nutrition.setOnClickListener(this);
        cb_maternal_safty.setOnClickListener(this);
        cb_vaccination.setOnClickListener(this);

        et_no_of_children.addTextChangedListener(textWatcher);
//        et_no_of_famity.addTextChangedListener(textWatcher);
        et_no_of_female.addTextChangedListener(textWatcher);
        et_no_of_male.addTextChangedListener(textWatcher);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
        getVillageList();


        spin_village_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                VillageListModel clickedItem = (VillageListModel)
                        parent.getItemAtPosition(position);
                String name = clickedItem.getVillage_name();
                v_id= clickedItem.getVillage_id();
//                Toast.makeText(AddDiseaseVillage.this, name + clickedItem.getVillage_id()+" selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // get the content of both the edit text
//
            try{
                no_child = et_no_of_children.getText().toString();
//             no_family = et_no_of_famity.getText().toString();
            no_female = et_no_of_female.getText().toString();
            no_male = et_no_of_male.getText().toString();

            total_no= Integer.parseInt(no_child)+Integer.parseInt(no_female)+Integer.parseInt(no_male);
            Log.d("TAG", "onTextChanged: "+total_no);
            tv_total_presense.setText(String.valueOf(total_no));

            }catch (Exception e){

            }

           }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void getVillageList() {
        UserService.getVillageList(this,this,GET_VILLAGE_LIST);
    }

    public void addDiseseInspection(){
        UserService.addDiseasePrevention(this,v_id,inspection_date,clean,maleria,nutrition,vaccinantion,maternal,child,other,no_family,no_male,no_female,no_child, String.valueOf(total_no),this,ADD_DISEASE_CHECKUP);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                if(inspection_date!=null&&
                       child!=null&&
//                        et_no_of_famity.getText()!=null&&
                        no_female!=null&&
                        no_male!=null){

                    addDiseseInspection();

                }else {
                    ToastUtils.shortToast(getResources().getString(R.string.fill_detail));
                }

                break;
            case R.id.et_inspection_date:
                getDateDialog();
                break;
            case R.id.cb_cleanliness:
                if(!cb_cleanliness.isChecked()){
                    clean="0";
                    Log.d("TAG", "onClick: if"+clean);
                    cb_cleanliness.setChecked(false);
                }else{
                    clean="1";
                    Log.d("TAG", "onClick: else"+clean);
                    cb_cleanliness.setChecked(true);}
                break;
            case R.id.cb_maleria:
                if(!cb_maleria.isChecked()){
                    maleria="0";
                    cb_maleria.setChecked(false);
                }else{
                    maleria="1";
                    cb_maleria.setChecked(true);}
                break;
            case R.id.cb_child_safty:
                if(!cb_child_safty.isChecked()){
                    child="0";
                    cb_child_safty.setChecked(false);
                }else{
                    child="1";
                    cb_child_safty.setChecked(true);}
                break;
            case R.id.cb_other:
                if(!cb_other.isChecked()){
                    other="0";
                    otherEt.setVisibility(View.GONE);

                    cb_other.setChecked(false);
                }else{
                    other="1";
                    otherEt.setVisibility(View.VISIBLE);
                    cb_other.setChecked(true);}
                break;

            case R.id.cb_maternal_safty:
                if(!cb_maternal_safty.isChecked()){
                    maternal="0";
                    cb_maternal_safty.setChecked(false);
                }else{
                    maternal="1";
                    cb_maternal_safty.setChecked(true);}
                break;
            case R.id.cb_vaccination:
                if(!cb_vaccination.isChecked()){
                    vaccinantion="0";
                    cb_vaccination.setChecked(false);
                }else{
                    vaccinantion="1";
                    cb_vaccination.setChecked(true);}
                break;
            case R.id.cb_nutrition:
                if(!cb_nutrition.isChecked()){
                    nutrition="0";
                    cb_nutrition.setChecked(false);
                }else{
                    nutrition="1";
                    cb_nutrition.setChecked(true);}
                break;

        }
    }

    @Override
    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request) {
        switch (requestCode){
            case ADD_DISEASE_CHECKUP:
                if (response.isSuccessful()){
                    BaseResponse baseResponse=(BaseResponse) response.body();
                    if (baseResponse!=null) {
                        if (baseResponse.getStatus().equalsIgnoreCase(RestUtils.SUCCESS)) {
                            Log.d("TAG", "onResponse: VIEW_HEAD_FAMILY" + "RestUtils.SUCCESS getmodule");
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                            finish();

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }else{
                    ToastUtils.shortToast(response.message());
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
                                spin_village_name.setAdapter(adapter);
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
                                spin_village_name.setAdapter(adapter);

                            }

                        }else {
                            ToastUtils.shortToast(baseResponse.getStatus() +"\n"+baseResponse.getStatusMessage());
                        }
                    }
                }else {
                    ToastUtils.shortToast(response.message());
                }
                break;
        }

    }

    @Override
    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request) {
        Log.d("TAG", "onFailure: "+t.getMessage());
        ToastUtils.shortToast(t.getMessage());//test
    }

    @Override
    public void onNoNetwork(int requestCode) {

    }
    private void getDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(AddDiseaseVillage.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date =  String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1)+ "-"+String.valueOf(dayOfMonth);
                et_inspection_date.setText(date);
                inspection_date=date;
                Log.d("TAG", "onDateSet: "+date);
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

}