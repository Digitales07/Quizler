package com.assorttech.myquizler.Classes;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.R;
import com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects.SecondYearBio;
import com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects.SecondYearChemistry;
import com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects.SecondYearComputer;
import com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects.SecondYearEnglish;
import com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects.SecondYearMath;
import com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects.SecondYearPhysics;

public class SecondYear extends AppCompatActivity implements View.OnClickListener{

    CardView mPhysics, mChemistry, mComputer, mBiology, mEnglish, mMath;

    MySoundPool mMySoundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_year);

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
            startActivity(new Intent(SecondYear.this, SecondYearPhysics.class));
        }
        else if (position==mChemistry){

            mMySoundPool.ClickSound();
            startActivity(new Intent(SecondYear.this, SecondYearChemistry.class));
        }
        else if (position==mComputer){

            mMySoundPool.ClickSound();
            startActivity(new Intent(SecondYear.this, SecondYearComputer.class));
        }
        else if (position==mBiology){

            mMySoundPool.ClickSound();
            startActivity(new Intent(SecondYear.this, SecondYearBio.class));
        }
        else if (position==mEnglish){
//
//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//// ...Irrelevant code for customizing the buttons and title
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

            mMySoundPool.ClickSound();
            startActivity(new Intent(SecondYear.this, SecondYearEnglish.class));
//
        }
        else if (position==mMath){

            mMySoundPool.ClickSound();
            startActivity(new Intent(SecondYear.this, SecondYearMath.class));
        }
    }
}



