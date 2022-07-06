package com.assorttech.myquizler.SecondYearComplete.SecondYearChapters;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class SecondYearChemistryChapters extends AppCompatActivity implements View.OnClickListener {

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight,mNine, mTen, mEleven, mTwelve,mThirteen,mFourteen,mFifteen,mSixteen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_year_chemistry_chapters);

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
        mThirteen=findViewById(R.id.thirteen);
        mFourteen=findViewById(R.id.fourteen);
        mFifteen=findViewById(R.id.fifteen);
        mSixteen=findViewById(R.id.sixteen);

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
        mThirteen.setOnClickListener(this);
        mFourteen.setOnClickListener(this);
        mFifteen.setOnClickListener(this);
        mSixteen.setOnClickListener(this);

    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter9");
            startActivity(intent);
        }

        else if (position==mTen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter10");
            startActivity(intent);
        }
        else if (position==mEleven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter11");
            startActivity(intent);

        }
        else if (position==mTwelve){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter12");
            startActivity(intent);
        }
        else if (position==mThirteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter13");
            startActivity(intent);
        }
        else if (position==mFourteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter14");
            startActivity(intent);
        }
        else if (position==mFifteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter15");
            startActivity(intent);
        }
        else if (position==mSixteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearChemistryChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearChemistryChapter16");
            startActivity(intent);
        }
    }
}


