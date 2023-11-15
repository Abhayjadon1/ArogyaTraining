package com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ispl.ekalarogya.training.R;
import com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.ayurvedic.HerbalRemediesList;
import com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.medicinegarden.MedicineGarden;
import com.ispl.ekalarogya.training.main.dashotheractivity.herbalremedies.medprep.VanAushadhi;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HerbalRemedies extends AppCompatActivity implements View.OnClickListener {
@BindView(R.id.cv_ayurvedic_treatment)
CardView cv_ayurvedic_treatment;
@BindView(R.id.cv_medicine_garden)
CardView cv_medicine_garden;
@BindView(R.id.cv_medicine_preparation)
CardView cv_medicine_preparation;
int i=1;
@BindView(R.id.iv_back)
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbal_remedies);
        ButterKnife.bind(this);
        cv_ayurvedic_treatment.setOnClickListener(this);
        cv_medicine_garden.setOnClickListener(this);
        cv_medicine_preparation.setOnClickListener(this);

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
            case R.id.cv_ayurvedic_treatment:
                startActivity(new Intent(HerbalRemedies.this, HerbalRemediesList.class));
                break;
            case R.id.cv_medicine_garden:
                startActivity(new Intent(HerbalRemedies.this, MedicineGarden.class));

                break;
            case R.id.cv_medicine_preparation:
                startActivity(new Intent(HerbalRemedies.this, VanAushadhi.class));

                break;

        }

    }
}