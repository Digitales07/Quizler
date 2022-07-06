package com.assorttech.myquizler.Classes;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.assorttech.myquizler.Model.CoolFactsModel;
import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.R;

import java.util.Random;

public class FSC extends AppCompatActivity {

    ImageView fiirstYear, secondYear;

    private TextView mFactsView;

    // TODO: cool facts bank
    private CoolFactsModel[] mCoolFacts = new CoolFactsModel[]{
            new CoolFactsModel(R.string.fact_1),
            new CoolFactsModel(R.string.fact_2),
            new CoolFactsModel(R.string.fact_3),
            new CoolFactsModel(R.string.fact_4),
            new CoolFactsModel(R.string.fact_5),
            new CoolFactsModel(R.string.fact_6),
            new CoolFactsModel(R.string.fact_7),
            new CoolFactsModel(R.string.fact_8),
            new CoolFactsModel(R.string.fact_9),
            new CoolFactsModel(R.string.fact_10),
            new CoolFactsModel(R.string.fact_11),
            new CoolFactsModel(R.string.fact_12),
            new CoolFactsModel(R.string.fact_13)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsc);

        mFactsView=findViewById(R.id.fact);

        Random random = new Random();

        int maxIndex = mCoolFacts.length;
        int generatedIndex = random.nextInt(maxIndex);

        mFactsView.setText(mCoolFacts[generatedIndex].getFacts());


        final MySoundPool mySoundPool= new MySoundPool(this);

       fiirstYear = findViewById(R.id.first_year);
       secondYear=findViewById(R.id.second_year);

        fiirstYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mySoundPool.ClickSound();

                startActivity(new Intent(FSC.this,FirstYear.class));
            }
        });

        secondYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mySoundPool.ClickSound();
                Intent intent= new Intent(FSC.this,SecondYear.class);
                startActivity(intent);
            }
        });

    }
}
