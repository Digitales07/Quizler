package com.assorttech.myquizler.NinthQuizType;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class NinthMathChapters extends AppCompatActivity implements View.OnClickListener {

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight,mNine,mTen,mEleven,mTwelve,mThirteen,mFourteen,mFifteen,mSixteen,mSeventeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth_math_chapters);

        mMySoundPool=new MySoundPool(this);

        mOne=findViewById(R.id.one);
        mTwo=findViewById(R.id.two);
        mThree=findViewById(R.id.three);
        mFour=findViewById(R.id.four);
        mFive=findViewById(R.id.five);
        mSix=findViewById(R.id.six);
        mSeven=findViewById(R.id.seven);
        mEight=findViewById(R.id.eight);
        mNine=findViewById(R.id.nine);
        mTen = findViewById(R.id.tenn);
        mEleven=findViewById(R.id.eleven_n);
        mTwelve=findViewById(R.id.twelve_e);
        mThirteen=findViewById(R.id.thirteen_n);
        mFourteen=findViewById(R.id.fourteen_n);
        mFifteen=findViewById(R.id.fifteen_n);
        mSixteen=findViewById(R.id.sixteen_n);
        mSeventeen = findViewById(R.id.seventeen_n);

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
        mSeventeen.setOnClickListener(this);
    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter9");
            startActivity(intent);
        }

        else if (position==mTen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter10");
            startActivity(intent);
        }

        else if (position==mEleven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter11");
            startActivity(intent);
        }

        else if (position==mTwelve){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter12");
            startActivity(intent);
        }

        else if (position==mThirteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter13");
            startActivity(intent);
        }

        else if (position==mFourteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter14");
            startActivity(intent);
        }

        else if (position==mFifteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter15");
            startActivity(intent);
        }

        else if (position==mSixteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter16");
            startActivity(intent);
        }

        else if (position==mSeventeen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(NinthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "NinthMathChapter17");
            startActivity(intent);
        }
    }
}


