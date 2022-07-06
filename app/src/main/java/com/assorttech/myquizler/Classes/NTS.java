package com.assorttech.myquizler.Classes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;

import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class NTS extends AppCompatActivity implements View.OnClickListener {

    CardView mIslam, mPak, mCurrentAffairs, mScience, mEnglish, mGk;

    MySoundPool mMySoundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nts);

        mMySoundPool = new MySoundPool(this);

        mIslam=findViewById(R.id.islam);
        mScience=findViewById(R.id.sc);
        mPak=findViewById(R.id.pak);
        mCurrentAffairs=findViewById(R.id.current);
        mEnglish=findViewById(R.id.eng);
        mGk=findViewById(R.id.g_k);

        mIslam.setOnClickListener(this);
        mScience.setOnClickListener(this);
        mPak.setOnClickListener(this);
        mCurrentAffairs.setOnClickListener(this);
        mEnglish.setOnClickListener(this);
        mGk.setOnClickListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Name", "");


        if(!name.equalsIgnoreCase(""))
        {
            name = name + "  Sethi";  /* Edit the value here*/
        }

    }
    public void onClick(View position)
    {
        if (position == mEnglish) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NTS.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "nts_english");
            startActivity(intent);

        } else if (position == mScience) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NTS.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "nts_science");
            startActivity(intent);

        } else if (position == mIslam) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NTS.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "islamiyat");
            startActivity(intent);
        }

        else if (position == mCurrentAffairs) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NTS.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "current");
            startActivity(intent);

        }

        else if (position == mGk) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NTS.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "gk");
            startActivity(intent);

        }

        else if (position == mPak) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NTS.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "pak");
            startActivity(intent);

        }
    }
}



