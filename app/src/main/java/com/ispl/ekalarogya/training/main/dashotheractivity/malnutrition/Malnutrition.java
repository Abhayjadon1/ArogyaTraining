package com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition.animea.AnemiaCheckup;
import com.ispl.ekalarogya.training.main.dashotheractivity.malnutrition.malnutrion.MalnutritionCheckup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Malnutrition extends AppCompatActivity {
@BindView(R.id.cv_anemia_in_pregnancy)
    CardView cv_anemia_in_pregnancy;
@BindView(R.id.cv_malnutrition)
    CardView cv_malnutrition;
@BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malnutrition);
        ButterKnife.bind(this);

        cv_malnutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Malnutrition.this, MalnutritionCheckup.class));
            }
        });
        cv_anemia_in_pregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Malnutrition.this, AnemiaCheckup.class));
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SocialSanitisation.this, Preventive.class));
                finish();
            }
        });
    }
}