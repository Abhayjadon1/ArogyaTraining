package com.inventics.ekalarogya.training.app_base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
//import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by sonu on 25/12/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutResource();

    @Override
    protected void attachBaseContext(Context newBase) {
        Context newContext = ArogyaApplication.localeManager != null ? ArogyaApplication.localeManager.setLocale(newBase) : newBase;
        super.attachBaseContext(newContext);

//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
