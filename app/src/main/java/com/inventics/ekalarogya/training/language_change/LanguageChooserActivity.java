package com.inventics.ekalarogya.training.language_change;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.app_base.ArogyaApplication;
import com.inventics.ekalarogya.training.app_base.BaseActivity;
import com.inventics.ekalarogya.training.app_base.SessionHandler;
import com.inventics.ekalarogya.training.helper.Constant;
import com.inventics.ekalarogya.training.helper.LocaleManager;
import com.inventics.ekalarogya.training.main.MainActivity;
import com.inventics.ekalarogya.training.main.SplashActivity;


public class LanguageChooserActivity extends BaseActivity implements View.OnClickListener, LanguageAdapter.OnItemClickListener {

    private static final String TAG = LanguageChooserActivity.class.getSimpleName();
    private Button saveButtonLayout;
    private RecyclerView mRecycleView;
    private String lang;
    private LocaleManager localeManager;
    private boolean isFromBehind=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_language_chooser);
        localeManager = ArogyaApplication.localeManager;
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(getResources().getString(R.string.change_language));
        init();
    }

    protected int getLayoutResource() {
        return R.layout.activity_language_chooser;
    }


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(LanguageChooserActivity.this, MainActivity.class));
        finish();
        return true;
    }

    private void init() {
        mRecycleView = findViewById(R.id.rvlanguage);
        mRecycleView.setLayoutManager(new GridLayoutManager(LanguageChooserActivity.this,2));
        mRecycleView.setAdapter(new LanguageAdapter(LanguageGenerator.getListData(),LanguageChooserActivity.this,LanguageChooserActivity.this));
        //button
        saveButtonLayout = findViewById(R.id.language_choose_button);
        saveButtonLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //super.o(v);
        if(v==saveButtonLayout){
            Log.d(TAG, "onClick: "+lang);
            if(!TextUtils.isEmpty(lang)){
                changeLocale(lang);
            }else  Toast.makeText(this, getResources().getString(R.string.select_your_preferred_language), Toast.LENGTH_SHORT).show();
        }
    }


    //Change Locale
    public void changeLocale(String lang) {
        if (TextUtils.isEmpty(lang)) {
            Toast.makeText(this, getResources().getString(R.string.select_your_preferred_language), Toast.LENGTH_SHORT).show();
            return;
        }
        if (localeManager != null) {
            localeManager.setNewLocale(this, lang);
            Toast.makeText(this, getResources().getString(R.string.language_changed_success), Toast.LENGTH_SHORT).show();
            restartApp();
        } else {
            Toast.makeText(this, getResources().getString(R.string.failed_slect_language), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void restartApp() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();

    }

    @Override
    public void onItemClick(String name) {
        Log.d(TAG, "onItemClick: "+name);
        if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("English")){
            lang = LocaleManager.DEFAULT_LOCALE;

        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("Hindi")){
            lang = LocaleManager.HINDI_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("ಕನ್ನಡ")){
            lang = LocaleManager.KANNADA_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("ગુજરાતી")){
            lang = LocaleManager.GUJARATI_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("தமிழ்")){
            lang = LocaleManager.TAMIL_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("తెలుగు")){
            lang = LocaleManager.TELUGU_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("اردو")){
            lang = LocaleManager.URDU_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("বাংলা")){
            lang = LocaleManager.BANGLA_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("ਪੰਜਾਬੀ")){
            lang = LocaleManager.PANJABHI_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("ভাষা")){
            lang = LocaleManager.ASSAMESE_LOCALE;
        }else if(!TextUtils.isEmpty(name) && name.equalsIgnoreCase("ଓଡିଆ")){
            lang = LocaleManager.ORIYA_LOCALE;
        }

        SessionHandler.getInstance().save(LanguageChooserActivity.this, Constant.LANGUAGE_CHOOSER,lang);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LanguageChooserActivity.this,MainActivity.class));
        finish();
    }
}