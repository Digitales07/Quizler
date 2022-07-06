package com.assorttech.myquizler.FirstYearComplete.FirstYearChapters;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class FirstYearChemistryChapters extends AppCompatActivity implements View.OnClickListener{

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight,mNine, mTen, mEleven;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_year_chemistry_chapters);

        mMySoundPool = new MySoundPool(this);

        mOne=findViewById(R.id.one);
        mTwo=findViewById(R.id.two);
        mThree=findViewById(R.id.three);
        mFour=findViewById(R.id.four);
        mFive=findViewById(R.id.five);
        mSix=findViewById(R.id.six);
        mSeven=findViewById(R.id.seven);
        mEight=findViewById(R.id.eight);
        mNine=findViewById(R.id.nine);
        mTen=findViewById(R.id.ten);
        mEleven=findViewById(R.id.eleven);

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mFive.setOnClickListener(this);
        mSix.setOnClickListener(this);
        mSeven.setOnClickListener(this);
        mEight.setOnClickListener(this);
        mNine.setOnClickListener(this);
        mTen.setOnClickListener(this);
        mEleven.setOnClickListener(this);

    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter9");
            startActivity(intent);
        }

        else if (position==mTen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter10");
            startActivity(intent);
        }
        else if (position==mEleven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(FirstYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "FirstYearChemistryChapter11");
            startActivity(intent);

        }

    }
}


