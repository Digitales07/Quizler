package com.assorttech.myquizler.TenthComplete.TenthChapters;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class TenthBioChapters extends AppCompatActivity implements View.OnClickListener{

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight, mNine ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenth_bio_chapters);

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

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mFive.setOnClickListener(this);
        mSix.setOnClickListener(this);
        mSeven.setOnClickListener(this);
        mEight.setOnClickListener(this);
        mNine.setOnClickListener(this);

    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthBioChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthBioChapter9");
            startActivity(intent);
        }
    }
}


