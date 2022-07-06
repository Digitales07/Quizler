package com.assorttech.myquizler.Classes;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;

import com.assorttech.myquizler.FirstYearComplete.FirstYearSubjects.FirstYearBio;
import com.assorttech.myquizler.FirstYearComplete.FirstYearSubjects.FirstYearChemistry;
import com.assorttech.myquizler.FirstYearComplete.FirstYearSubjects.FirstYearComputer;
import com.assorttech.myquizler.FirstYearComplete.FirstYearSubjects.FirstYearEnglish;
import com.assorttech.myquizler.FirstYearComplete.FirstYearSubjects.FirstYearMath;
import com.assorttech.myquizler.FirstYearComplete.FirstYearSubjects.FirstYearPhysics;
import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.R;

public class FirstYear extends AppCompatActivity implements View.OnClickListener{

    MySoundPool mMySoundPool;
    CardView mPhysics, mChemistry, mComputer, mBiology, mEnglish, mMath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_year);

        mMySoundPool = new MySoundPool(this);

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
            startActivity(new Intent(FirstYear.this, FirstYearPhysics.class));
        }
        else if (position==mChemistry){

            mMySoundPool.ClickSound();
            startActivity(new Intent(FirstYear.this, FirstYearChemistry.class));
        }
        else if (position==mComputer){

            mMySoundPool.ClickSound();
            startActivity(new Intent(FirstYear.this, FirstYearComputer.class));
        }
        else if (position==mBiology){

            mMySoundPool.ClickSound();
            startActivity(new Intent(FirstYear.this, FirstYearBio.class));
        }
        else if (position==mEnglish){

            mMySoundPool.ClickSound();
           startActivity(new Intent(FirstYear.this, FirstYearEnglish.class));

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
        }

        else if (position==mMath){

            mMySoundPool.ClickSound();
            startActivity(new Intent(FirstYear.this, FirstYearMath.class));
        }
    }
}


