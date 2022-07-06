package com.assorttech.myquizler.NinthChemistryQuizType.NinthChemistryChapterWise;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class NinthChemistryChapters extends AppCompatActivity implements View.OnClickListener {

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth_chemistry_chapters);

        mMySoundPool=new MySoundPool(this);

        mOne=findViewById(R.id.one);
        mTwo=findViewById(R.id.two);
        mThree=findViewById(R.id.three);
        mFour=findViewById(R.id.four);
        mFive=findViewById(R.id.five);
        mSix=findViewById(R.id.six);
        mSeven=findViewById(R.id.seven);
        mEight=findViewById(R.id.eight);

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mFive.setOnClickListener(this);
        mSix.setOnClickListener(this);
        mSeven.setOnClickListener(this);
        mEight.setOnClickListener(this);

    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter1");
            startActivity(intent);
        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthChemistryChapter8");
            startActivity(intent);
        }
    }
}
