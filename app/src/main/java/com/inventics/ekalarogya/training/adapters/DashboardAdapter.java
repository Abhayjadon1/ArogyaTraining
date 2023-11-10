package com.inventics.ekalarogya.training.adapters;

import static com.inventics.ekalarogya.training.helper.LocaleManager.LOCALE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;

import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.helper.Constant;
import com.inventics.ekalarogya.training.helper.LocaleManager;
import com.inventics.ekalarogya.training.main.dashotheractivity.arogyacamp.ArogyaCamp;
import com.inventics.ekalarogya.training.main.dashotheractivity.deseaseprevention.DiseasePrevention;
import com.inventics.ekalarogya.training.main.dashotheractivity.eye.EyeCheckup;
import com.inventics.ekalarogya.training.main.dashotheractivity.firstaid.FirstAid;
import com.inventics.ekalarogya.training.main.dashotheractivity.herbalremedies.HerbalRemedies;
import com.inventics.ekalarogya.training.main.dashotheractivity.inspectionday.InspectionDay;
import com.inventics.ekalarogya.training.main.dashotheractivity.jagaranprogram.JagranProgram;
import com.inventics.ekalarogya.training.main.dashotheractivity.malnutrition.Malnutrition;
import com.inventics.ekalarogya.training.main.dashotheractivity.posterdisplay.PosterDisplay;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.Preventive;
import com.inventics.ekalarogya.training.main.dashotheractivity.samitimeeting.SamitiMeeting;
import com.inventics.ekalarogya.training.main.dashotheractivity.villagevisit.VillageVisit;
import com.inventics.ekalarogya.training.main.dashotheractivity.family.VillageFamily;
import com.inventics.ekalarogya.training.main.support_ticket.SupportTicketActivity;
import com.inventics.ekalarogya.training.models.DashBoardModel;
import com.inventics.ekalarogya.training.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private final Context mContaxt;
    private final List<DashBoardModel> dashBoardModel;
    private static final String TAG = "OwnerListAdapter";
    private DashboardAdapter.OnItemClickListener listener;



    public interface OnItemClickListener {

        void onItemClick(DashBoardModel item);
    }

    public DashboardAdapter(Context context, List<DashBoardModel> dashBordModel) {
        this.mContaxt = context;
        this.dashBoardModel=dashBordModel;
        this.listener = listener;





    }

    @NonNull
    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContaxt);
        View view = inflater.inflate(R.layout.home_dash_data_model, null);


        return new DashboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DashboardAdapter.ViewHolder holder, int position) {

        final DashBoardModel baseResponse = dashBoardModel.get(position);

        String[] textcolors = {"#56ADB5","#E0824F","#CF6855","#4EC387",};
        int textcolor = position % textcolors.length;
        int intTextColor = Color.parseColor(textcolors[textcolor]);

        Resources res = mContaxt.getResources();
        Configuration conf = res.getConfiguration();
        Locale savedLocale = conf.locale;
        conf.locale = new Locale("en"); // whatever you want here
        res.updateConfiguration(conf, null); // second arg null means don't change

// retrieve resources from desired locale
//        String str = res.getString(R.string.dash1);
        String name="";
        if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash1))){
            // trans(R.string.dash1);
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash1);

        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash2))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash2);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash3))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash3);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash4))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash4);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash5))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash5);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash6))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash6);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash7))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash7);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash8))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash8);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash9))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash9);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash10))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash10);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash11))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash11);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash12))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash12);
        }else if (baseResponse.getTitle().equalsIgnoreCase(res.getString(R.string.dash13))){
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=mContaxt.getResources().getString(R.string.dash13);
        }else {
            conf.locale = savedLocale;
            res.updateConfiguration(conf, null);
            name=baseResponse.getTitle();
        }

        holder.tvName.setText(name);


        conf.locale = savedLocale;
        res.updateConfiguration(conf, null);
        SharedPreferences sharedPreferences = mContaxt.getSharedPreferences(Constant.LOGIN_SHARED, Context.MODE_PRIVATE);
        conf.locale = new Locale(sharedPreferences.getString(LOCALE, LocaleManager.DEFAULT_LOCALE));
        res.updateConfiguration(conf, null);






        if (baseResponse.getPath().equalsIgnoreCase("Support")){
            Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.support_ticket).error(R.drawable.support_ticket).into(holder.dash_imagee);
        }else{
            Picasso.with(mContaxt).load(baseResponse.getPath()).placeholder(R.drawable.arogya_vector).error(R.drawable.arogya_vector).into(holder.dash_imagee);
        }

       
        holder.bind(dashBoardModel.get(position),listener, position);
    }

    @Override
    public int getItemCount() {
        return dashBoardModel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView dash_imagee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.dash_name);

            dash_imagee=itemView.findViewById(R.id.dash_imagee);


        }

        public void bind(DashBoardModel dashBoardModel, OnItemClickListener listener, int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ArogyaApplication.getPreferenceManager().getBooleanValue(Constant.IS_ONLINE)){
                        switch (dashBoardModel.getId()) {
                            case "1":
                                mContaxt.startActivity(new Intent(mContaxt, VillageFamily.class));
                                break;
                            case "2":
                                mContaxt.startActivity(new Intent(mContaxt, Preventive.class));
                                break;
                            case "3":
                                mContaxt.startActivity(new Intent(mContaxt, DiseasePrevention.class));
                                break;
                            case "4":
                                mContaxt.startActivity(new Intent(mContaxt, Malnutrition.class));
                                break;
                            case "5":
                                mContaxt.startActivity(new Intent(mContaxt, HerbalRemedies.class));
                                break;
                            case "6":
                                mContaxt.startActivity(new Intent(mContaxt, FirstAid.class));
                                break;
                            case "7":
                                mContaxt.startActivity(new Intent(mContaxt, EyeCheckup.class));
                                break;
                            case "8":
                                mContaxt.startActivity(new Intent(mContaxt, SamitiMeeting.class));
                                break;
                            case "9":
                                mContaxt.startActivity(new Intent(mContaxt, PosterDisplay.class));
                                break;
                            case "10":
                                mContaxt.startActivity(new Intent(mContaxt, VillageVisit.class));
                                break;
                            case "11":
                                mContaxt.startActivity(new Intent(mContaxt, ArogyaCamp.class));
                                break;
                            case "12":
                                mContaxt.startActivity(new Intent(mContaxt, JagranProgram.class));
                                break;
                            case "13":
                                mContaxt.startActivity(new Intent(mContaxt, InspectionDay.class));
                                break;
                            case "14":
                                mContaxt.startActivity(new Intent(mContaxt, SupportTicketActivity.class));
                                break;


                        }
                    }else{
                        ToastUtils.shortToast(mContaxt.getResources().getString(R.string.plz_mark_attendace_first));
                    }
//                    Toast.makeText(mcontext, "Click"+dashBoardModel.getModule_name(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }



}
