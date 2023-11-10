package com.inventics.ekalarogya.training.main.dashotheractivity.eye;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.main.dashotheractivity.eye.eyescreening.EyeScreening;
import com.inventics.ekalarogya.training.main.dashotheractivity.eye.eyevan.EyeVan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EyeCheckup extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.cv_eye_screening)
    CardView cv_eye_screening;
    @BindView(R.id.cv_eye_van)
    CardView cv_eye_van;

    @BindView(R.id.iv_back)
    ImageView iv_back;
    int i=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye);
        ButterKnife.bind(this);
        cv_eye_screening.setOnClickListener(this);
        cv_eye_van.setOnClickListener(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cv_eye_van:
                startActivity(new Intent(EyeCheckup.this, EyeVan.class));
                break;
            case R.id.cv_eye_screening:
                startActivity(new Intent(EyeCheckup.this, EyeScreening.class));
                break;

        }

    }
}