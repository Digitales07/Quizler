package com.assorttech.myquizler.SecondYearComplete.SecondYearSubjects;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import com.assorttech.myquizler.MySoundPool;
import com.assorttech.myquizler.QuizScreenActivity;
import com.assorttech.myquizler.R;
import com.assorttech.myquizler.SecondYearComplete.SecondYearChapters.SecondYearMathChapters;

public class SecondYearMath extends AppCompatActivity implements View.OnClickListener {

    CardView mChapterWise, mPastPaper, mFirstHalf, mSecondHalf, mFullBook;
    MySoundPool mMySoundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_year_math);

        mMySoundPool = new MySoundPool(this);
        mChapterWise = findViewById(R.id.chapter_wise);
        mPastPaper = findViewById(R.id.past_paper);
        mFirstHalf = findViewById(R.id.first_half);
        mSecondHalf = findViewById(R.id.second_half);
        mFullBook = findViewById(R.id.full_book);

        mChapterWise.setOnClickListener(this);
        mPastPaper.setOnClickListener(this);
        mFirstHalf.setOnClickListener(this);
        mSecondHalf.setOnClickListener(this);
        mFullBook.setOnClickListener(this);
    }

    public void onClick(View position)
    {
        if (position == mChapterWise) {

            mMySoundPool.ClickSound();
            startActivity(new Intent(SecondYearMath.this, SecondYearMathChapters.class));

        } else if (position == mPastPaper) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMath.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathPastPaper");
            startActivity(intent);

        } else if (position == mFirstHalf) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMath.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathFirstHalf");
            startActivity(intent);

        } else if (position == mSecondHalf) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMath.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathSecondHalf");
            startActivity(intent);

        } else if (position == mFullBook) {

            mMySoundPool.ClickSound();
            Intent intent = new Intent(SecondYearMath.this, QuizScreenActivity.class);
            intent.putExtra("Ref", "SecondYearMathFullBook");
            startActivity(intent);
        }
    }
}

