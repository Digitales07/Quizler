package com.assorttech.myquizler;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Report extends AppCompatActivity implements View.OnClickListener{

    int xp=0;
    int totalScore=0;

    ImageView mPraise,mBanner,mHome, mLeaderBoard,mPlayAgain,mShare;
    TextView mResult,mTotal,mRightAnswers,gainedXp,totalGainedScore;
    String Ref;
    int result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mPraise=findViewById(R.id.prise);
        mHome=findViewById(R.id.home);
        mLeaderBoard =findViewById(R.id.rateUs);
        mPlayAgain=findViewById(R.id.playAgain);
        mShare=findViewById(R.id.share);
        gainedXp=findViewById(R.id.xp_store);
        totalGainedScore=findViewById(R.id.total_calculation);

        mRightAnswers=findViewById(R.id.correct_answers);
        mResult=findViewById(R.id.result);
        mTotal=findViewById(R.id.total_questions);

        mHome.setOnClickListener(this);
        mLeaderBoard.setOnClickListener(this);
        mPlayAgain.setOnClickListener(this);
        mShare.setOnClickListener(this);

        Intent intent = getIntent();
        final int result = intent.getIntExtra("s",0);
        int totalQuestions = intent.getIntExtra("TotalQuestions", 0);
        int rightAns = intent.getIntExtra("ra", 0);
        Ref = getIntent().getExtras().getString("secondRef","defaultKey");

        result2=result;

        mResult.setText(""+result+" %");
        mTotal.setText(""+totalQuestions);
        mRightAnswers.setText(""+rightAns);

        if (result>=80){

            mPraise.setImageResource(R.drawable.excellent_fn);//first_trophy
            xp=4;

            gainedXp.setText(Integer.toString(xp));
            totalScore=rightAns*xp;
            totalGainedScore.setText(rightAns+" X "+xp+" = "+Integer.toString(totalScore));

        }

        else if (result>=70)
        {
            mPraise.setImageResource(R.drawable.very_good);

            xp=3;
            gainedXp.setText(Integer.toString(xp));
            totalScore=rightAns*xp;
            totalGainedScore.setText(rightAns+" X "+xp+" = "+Integer.toString(totalScore));
        }

        else if (result>=60)
        {
            mPraise.setImageResource(R.drawable.good);

            xp=2;
            gainedXp.setText(Integer.toString(xp));
            totalScore=rightAns*xp;
            totalGainedScore.setText(rightAns+" X "+xp+" = "+Integer.toString(totalScore));
        }

        else if (result>=50)
        {

            mPraise.setImageResource(R.drawable.average);
            xp=1;
            gainedXp.setText(Integer.toString(xp));
            totalScore=rightAns*xp;
            totalGainedScore.setText(rightAns+" X "+xp+" = "+Integer.toString(totalScore));
        }

        else
        {

            xp=1;
            mPraise.setImageResource(R.drawable.poor);
            gainedXp.setText(Integer.toString(xp));
            totalScore=rightAns*xp;
            totalGainedScore.setText(rightAns+" x "+xp+" = "+Integer.toString(totalScore));
        }
    }

    @Override
    public void onClick(View position) {
        if (position==mHome)
        {
            Intent intent = new Intent(getApplicationContext(), FirstMainScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else if (position== mLeaderBoard)
        {
            Intent intent = new Intent(Report.this,LeaderBoardScreenActivity.class);
            startActivity(intent);
            finish();
        }
        else if (position==mShare)
        {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quizzler");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I have scored: "+result2+" Percent in Quizzler. Beat me if you can. Download the app from play store now: https://play.google.com/store/apps/details?id=com.assorttech.myquizler");
            startActivity(Intent.createChooser(sharingIntent, "Quizzler Report Share"));

        }
        else if (position==mPlayAgain)
        {
            Intent intent = new Intent(Report.this,QuizScreenActivity.class);
            intent.putExtra("Ref", Ref);
            startActivity(intent);
            finish();
        }
    }
}
