package com.inventics.ekalarogya.training.main.dashotheractivity.preventives;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.inventics.ekalarogya.training.R;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.familyvisit.FamilyVisitList;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.hygiene.PersonalHygine;
import com.inventics.ekalarogya.training.main.dashotheractivity.preventives.socialSanitisation.SocialSanitisation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Preventive extends AppCompatActivity {
    @BindView(R.id.cv_personal_hygeine)
    CardView cv_personal_hygeine;
    @BindView(R.id.cv_social_sanitation)
    CardView cv_social_sanitation;
    @BindView(R.id.cv_family_visit)
    CardView cv_family_visit;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive);
        ButterKnife.bind(this);

        cv_personal_hygeine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, PersonalHygine.class));
            }
        });
        cv_social_sanitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, SocialSanitisation.class));
            }
        });
        cv_family_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, FamilyVisitList.class));
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Preventive.this, MainActivity.class));
                finish();
            }
        });
    }
}