package com.assorttech.myquizler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

public class GeneralKnowledge extends AppCompatActivity implements View.OnClickListener {

    CardView mSports, mScience, mHistory, mEntertainment;

    MySoundPool mMySoundPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_knowledge);


        mMySoundPool = new MySoundPool(this);

        mSports=findViewById(R.id.sports);
        mScience=findViewById(R.id.science);
        mHistory=findViewById(R.id.history);
        mEntertainment=findViewById(R.id.entertainment);

        mSports.setOnClickListener(this);
        mScience.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mEntertainment.setOnClickListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Name", "");


        if(!name.equalsIgnoreCase(""))
        {
            name = name + "  Sethi";  /* Edit the value here*/
        }

    }
    public void onClick(View position)
    {
        if (position == mSports) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(GeneralKnowledge.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "sports");
            startActivity(intent);

        } else if (position == mScience) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(GeneralKnowledge.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "science");
            startActivity(intent);

        } else if (position == mHistory) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(GeneralKnowledge.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "history");
            startActivity(intent);
        }

        else if (position == mEntertainment) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(GeneralKnowledge.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "entertainment");
            startActivity(intent);

        }
    }
}



