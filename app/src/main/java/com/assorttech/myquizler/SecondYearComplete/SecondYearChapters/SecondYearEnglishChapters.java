package com.assorttech.myquizler.SecondYearComplete.SecondYearChapters;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class SecondYearEnglishChapters extends AppCompatActivity implements View.OnClickListener{

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight,mNine, mTen, mEleven, mTwelve,mthirteen,mFourteen,mFifteen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_year_english_chapters);

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
        mTwelve=findViewById(R.id.twelve);
        mthirteen=findViewById(R.id.thirteen);
        mFourteen=findViewById(R.id.second_eng_chfourteen);
        mFifteen=findViewById(R.id.second_eng_chfifteen);

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
        mthirteen.setOnClickListener(this);
        mFourteen.setOnClickListener(this);
        mFifteen.setOnClickListener(this);
    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter9");
            startActivity(intent);
        }

        else if (position==mTen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter10");
            startActivity(intent);
        }
        else if (position==mEleven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter11");
            startActivity(intent);

        }
        else if (position==mTwelve){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter12");
            startActivity(intent);
        }
        else if (position==mthirteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter13");
            startActivity(intent);
        }
        else if (position==mFourteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter14");
            startActivity(intent);
        }
        else if (position==mFifteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearEnglishChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearEnglishChapter15");
            startActivity(intent);
        }
    }
}


