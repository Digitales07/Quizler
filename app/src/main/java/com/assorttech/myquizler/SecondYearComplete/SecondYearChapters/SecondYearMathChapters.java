package com.assorttech.myquizler.SecondYearComplete.SecondYearChapters;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class SecondYearMathChapters extends AppCompatActivity implements View.OnClickListener{

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_year_math_chapters);

        mMySoundPool= new MySoundPool(this);

        mOne=findViewById(R.id.one);
        mTwo=findViewById(R.id.two);
        mThree=findViewById(R.id.three);
        mFour=findViewById(R.id.four);
        mFive=findViewById(R.id.five);
        mSix=findViewById(R.id.six);
        mSeven=findViewById(R.id.seven);

        mOne.setOnClickListener(this);
        mTwo.setOnClickListener(this);
        mThree.setOnClickListener(this);
        mFour.setOnClickListener(this);
        mFive.setOnClickListener(this);
        mSix.setOnClickListener(this);
        mSeven.setOnClickListener(this);

    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathChapter7");
            startActivity(intent);
        }
    }
}


