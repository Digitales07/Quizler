package com.assorttech.myquizler.TenthComplete.TenthChapters;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;

public class TenthMathChapters extends AppCompatActivity implements View.OnClickListener {

    MySoundPool mMySoundPool;
    CardView mOne, mTwo, mThree, mFour, mFive, mSix, mSeven, mEight,mNine, mTen, mEleven, mTwelve,mthirteen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenth_math_chapters);

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
    }
    public void onClick(View position)
    {
        if (position == mOne) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter1");
            startActivity(intent);

        }
        else if (position==mTwo){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter2");
            startActivity(intent);
        }
        else if (position==mThree){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter3");
            startActivity(intent);
        }
        else if (position==mFour){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter4");
            startActivity(intent);
        }
        else if (position==mFive){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter5");
            startActivity(intent);
        }
        else if (position==mSix){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter6");
            startActivity(intent);
        }
        else if (position==mSeven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter7");
            startActivity(intent);
        }
        else if (position==mEight){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter8");
            startActivity(intent);
        }
        else if (position==mNine){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter9");
            startActivity(intent);
        }

        else if (position==mTen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter10");
            startActivity(intent);
        }
        else if (position==mEleven){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter11");
            startActivity(intent);

        }
        else if (position==mTwelve){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter12");
            startActivity(intent);
        }
        else if (position==mthirteen){

            mMySoundPool.ClickSound();
            Intent intent = new Intent(TenthMathChapters.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "TenthMathChapter13");
            startActivity(intent);
        }
    }
}


