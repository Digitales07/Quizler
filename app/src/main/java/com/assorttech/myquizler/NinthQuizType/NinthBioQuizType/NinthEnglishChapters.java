package com.assorttech.myquizler.NinthQuizType.NinthBioQuizType;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class NinthEnglishChapters extends AppCompatActivity implements View.OnClickListener{

    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight,mNine,mTen,mEleven,mTwelve;
    MySoundPool mMySoundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth_english_chapters);

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
        mTen=findViewById(R.id.nine_ch_ten);
        mEleven=findViewById(R.id.nine_ch_eleven);
        mTwelve=findViewById(R.id.nine_ch_twelve);

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
        mTwelve.setOnClickListener(this);
    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter3");
            startActivity(intent);

        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter9");
            startActivity(intent);
        }
        else if (position==mTen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter10");
            startActivity(intent);
        }
        else if (position==mEleven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter11");
            startActivity(intent);
        }
        else if (position==mTwelve){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthEnglishChapter12");
            startActivity(intent);
        }


    }
}


