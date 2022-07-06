package com.assorttech.myquizler.Classes;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.NinthSubjects.NinthBiology;
import com.assorttech.myquizler.NinthSubjects.NinthChemistry;
import com.assorttech.myquizler.NinthSubjects.NinthComputer;
import com.assorttech.myquizler.NinthSubjects.NinthEnglish;
import com.assorttech.myquizler.NinthSubjects.NinthMath;
import com.assorttech.myquizler.NinthSubjects.NinthPhysics;
import com.assorttech.myquizler.R;

public class Ninth extends AppCompatActivity implements View.OnClickListener {

   CardView mPhysics, mChemistry, mComputer, mBiology, mEnglish, mMath;

    MySoundPool mMySoundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth);

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
            startActivity(new Intent(Ninth.this, NinthPhysics.class));
        }
        else if (position==mChemistry){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Ninth.this, NinthChemistry.class));
        }
        else if (position==mComputer){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Ninth.this, NinthComputer.class));
        }
        else if (position==mBiology){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Ninth.this, NinthBiology.class));
        }
        else if (position==mEnglish){
            mMySoundPool.ClickSound();
            startActivity(new Intent(Ninth.this, NinthEnglish.class));

//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//            LayoutInflater inflater = this.getLayoutInflater();
//            View dialogView = inflater.inflate(R.layout.dialog, null);
//            dialogBuilder.setView(dialogView);
//
//            final Button btn = dialogView.findViewById(R.id.exit);
//
//            final AlertDialog alertDialog = dialogBuilder.create();
//            alertDialog.show();
//            alertDialog.setCancelable(false);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
        }
        else if (position==mMath){

            mMySoundPool.ClickSound();
            startActivity(new Intent(Ninth.this, NinthMath.class));
        }
    }
}


