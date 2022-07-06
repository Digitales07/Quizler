package com.assorttech.myquizler.Classes;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.R;
import com.assorttech.myquizler.TenthComplete.TenthSubjects.TenthBio;
import com.assorttech.myquizler.TenthComplete.TenthSubjects.TenthChemistry;
import com.assorttech.myquizler.TenthComplete.TenthSubjects.TenthComputer;
import com.assorttech.myquizler.TenthComplete.TenthSubjects.TenthEnglish;
import com.assorttech.myquizler.TenthComplete.TenthSubjects.TenthMath;
import com.assorttech.myquizler.TenthComplete.TenthSubjects.TenthPhysics;

public class Tenth extends AppCompatActivity implements View.OnClickListener {

    MySoundPool mMySoundPool;
    CardView mPhysics, mChemistry, mComputer, mBiology, mEnglish, mMath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenth);

        mMySoundPool=new MySoundPool(this);

        mPhysics=findViewById(R.id.physics);
        mChemistry=findViewById(R.id.chemistry);
        mComputer=findViewById(R.id.computer);
        mBiology=findViewById(R.id.biology);
        mEnglish=findViewById(R.id.english);
        mMath=findViewById(R.id.math);

        mPhysics.setOnClickListener(this);
        mChemistry.setOnClickListener(this);
        mComputer.setOnClickListener(this);
        mBiology.setOnClickListener(this);
        mEnglish.setOnClickListener(this);
        mMath.setOnClickListener(this);
    }
    public void onClick(View position)
    {
        if (position == mPhysics) {
            mMySoundPool.ClickSound();
            startActivity(new Intent(Tenth.this, TenthPhysics.class));
        }
        else if (position==mChemistry){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Tenth.this, TenthChemistry.class));
        }
        else if (position==mComputer){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Tenth.this, TenthComputer.class));
        }
        else if (position==mBiology){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Tenth.this, TenthBio.class));
        }
        else if (position==mEnglish){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Tenth.this, TenthEnglish.class));

        }
        else if (position==mMath){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Tenth.this, TenthMath.class));
        }
    }
}


