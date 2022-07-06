package com.assorttech.myquizler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.assorttech.myquizler.Model.ModelClass;
import com.assorttech.myquizler.Model.StatModel;
import com.assorttech.myquizler.Model.UserDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainQuizScreen extends AppCompatActivity {

    DatabaseReference mRootRef;
    //MediaPlayer

    int pp=0,ss=0,gg=0,tq=0,pc=0,xp=0;
    int totalQuestions;
    int numberOfQuestions;
    String PImage, PName;
    int temp, percent;

    FirebaseAuth mAuth;
    DatabaseReference mUserRef;

    ProgressBar pb;

    private int totalTime;
    private Button mNext;
    Boolean loadOnce=true;
    Intent mIntent;
    Intent mIntentTwo;

    ArrayList<Integer> list;

    String secondRef;

    // timer resource declaration
    private static final long START_TIME_IN_MILLIS = 15000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean mTimerRunning;
    private MediaPlayer mp;

    private boolean volume = true;
    private boolean musicVolume= true;

    ImageView mSound;
    ImageView mBackMusic;

    private DatabaseReference mDatabaseReference;
    private TextView mQuestionTextView, mScoreTextView, mQuestionNumber;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    String mQuestion, mOption1, mOption2, mOption3, mOption4, mAnswer;
    int mScore, i, mNumber;

    private ProgressDialog mProgressDialog;
    MySoundPool mSoundPool;

    List<ModelClass> multipleQuestions;

    ModelClass mModelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quiz_screen);

        mAuth = FirebaseAuth.getInstance();

        //progress timer

        pb = (ProgressBar) findViewById(R.id.progressBarToday);
//        Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
//        an.setFillAfter(true);
//        pb.startAnimation(an);

        Animation aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        pb.startAnimation(aniRotateClk);


        //Media Player
        mp = MediaPlayer.create(this, R.raw.files);
        mp.setLooping(true);
        mp.setVolume(0.5f,0.5f);


        list  =new ArrayList<Integer>();

        mModelClass =new ModelClass();


        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mSoundPool = new MySoundPool(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ref = getIntent().getExtras().get("Ref").toString();
        secondRef = getIntent().getExtras().get("Ref").toString();
        temp = getIntent().getIntExtra("temp", 0);

        mScoreTextView = findViewById(R.id.score);
        mQuestionNumber = findViewById(R.id.question_no);
        mNext = findViewById(R.id.next);


        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            i = savedInstanceState.getInt("IndexKey");
            mNumber = savedInstanceState.getInt("QuestionNumber");

        } else {
            mScore = 0;
            i = 0;
            mNumber = 1;
        }

        mQuestionTextView = findViewById(R.id.qusetionView);
        btnOption1 = findViewById(R.id.button1);
        btnOption2 = findViewById(R.id.button2);
        btnOption3 = findViewById(R.id.button3);
        btnOption4 = findViewById(R.id.button4);

        //  internetToast();

        mProgressDialog =new ProgressDialog(MainQuizScreen.this);
        mProgressDialog.setTitle("Please Wait ");
        mProgressDialog.setMessage("Retrieving Questions");

        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        //Ninth Start
        // Ninth Physics references Start

        if(ref.equals("NinthPastPaper"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthFullBook"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthHalfFirstHalf"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("NinthSecondHalf"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Second_half");
        }
        //Ninth Physics Chapters

        else if (ref.equals("NinthPhysicsChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("NinthPhysicsChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("NinthPhysicsChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("NinthPhysicsChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("NinthPhysicsChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("NinthPhysicsChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("NinthPhysicsChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("NinthPhysicsChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("NinthPhysicsChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }

        // 9th Chemistry References Start

        else if (ref.equals("NinthChemistryPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthChemistryFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthChemistryFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("NinthChemistrySecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Second_half");
        }

        //Ninth chemistry Chapters

        else if (ref.equals("NinthChemistryChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("NinthChemistryChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("NinthChemistryChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("NinthChemistryChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("NinthChemistryChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("NinthChemistryChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("NinthChemistryChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("NinthChemistryChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }

        else if (ref.equals("NinthChemistryChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }

        // end of ninth chemistry

        //start of ninth Bio

        else if(ref.equals("NinthBioPastPaper"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthBioFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthBioFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("NinthBioSecondHalf"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Second_half");
        }

        //Ninth Bio Chapters

        else if (ref.equals("NinthBioChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("NinthBioChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("NinthBioChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("NinthBioChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("NinthBioChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("NinthBioChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("NinthBioChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("NinthBioChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("NinthBioChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }

        // end of ninth bio
        // Start of ninth computer

        else if(ref.equals("NinthComputerPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthComputerFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthComputerFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("NinthComputerSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Second_half");
        }

        //Ninth computer Chapters

        else if (ref.equals("NinthComputerChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("NinthComputerChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("NinthComputerChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("NinthComputerChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("NinthComputerChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("NinthComputerChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("NinthComputerChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("NinthComputerChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }

        // end of ninth Computer

        //start of ninth English

        else if(ref.equals("NinthEnglishPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthEnglishFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthEnglishFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("NinthEnglishSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Second_half");
        }

        //Ninth English Chapters

        else if (ref.equals("NinthEnglishChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("NinthEnglishChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("NinthEnglishChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("NinthEnglishChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("NinthEnglishChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("NinthEnglishChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("NinthEnglishChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("NinthEnglishChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("NinthEnglishChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("NinthEnglishChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("NinthEnglishChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("NinthEnglishChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }

        //end of ninth english
        //start of ninth math

        else if (ref.equals("NinthMathPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthMathFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("NinthMathFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("NinthMathSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Second_half");
        }

        //Ninth Math Chapters

        else if (ref.equals("NinthMathChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("NinthMathChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("NinthMathChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("NinthMathChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("NinthMathChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("NinthMathChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("NinthMathChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("NinthMathChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("NinthMathChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("NinthMathChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("NinthMathChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("NinthMathChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("NinthMathChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("NinthMathChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }
        else if (ref.equals("NinthMathChapter15"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_15");
        }
        else if (ref.equals("NinthMathChapter16"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_16");
        }
        else if (ref.equals("NinthMathChapter17"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Ninth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_17");
        }

        //  end of Ninth math
        //Tenth Start
        // Tenth Physics references Start

        else if(ref.equals("TenthPhysicsPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthPhysicsFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthPhysicsFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("TenthPhysicsSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Second_half");
        }
        //Tenth Physics Chapters

        else if (ref.equals("TenthPhysicsChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("TenthPhysicsChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("TenthPhysicsChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("TenthPhysicsChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("TenthPhysicsChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("TenthPhysicsChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("TenthPhysicsChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("TenthPhysicsChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }


        // 10th Chemistry References Start

        else if (ref.equals("TenthChemistryPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthChemistryFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthChemistryFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("TenthChemistrySecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Second_half");
        }

        //Tenth chemistry Chapters

        else if (ref.equals("TenthChemistryChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("TenthChemistryChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("TenthChemistryChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("Ch_3");
        }
        else if (ref.equals("TenthChemistryChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("Ch_4");
        }
        else if (ref.equals("TenthChemistryChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("Ch_5");
        }
        else if (ref.equals("TenthChemistryChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("Ch_6");
        }
        else if (ref.equals("TenthChemistryChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("Ch_7");
        }
        else if (ref.equals("TenthChemistryChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("Ch_8");
        }
        // end of Tenth chemistry

        //start of Tenth Bio

        else if(ref.equals("TenthBioPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthBioFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthBioFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("TenthBioSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Second_half");
        }

        //Ninth Bio Chapters

        else if (ref.equals("TenthBioChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("TenthBioChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("TenthBioChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_3");
        }
        else if (ref.equals("TenthBioChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_4");
        }
        else if (ref.equals("TenthBioChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_5");
        }
        else if (ref.equals("TenthBioChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_6");
        }
        else if (ref.equals("TenthBioChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_7");
        }
        else if (ref.equals("TenthBioChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_8");
        }
        else if (ref.equals("TenthBioChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Biology")
                    .child("Type").child("Chapter_wise").child("Ch_9");
        }

        // end of Tenth bio
        // Start of Tenth computer

        else if(ref.equals("TenthComputerPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthComputerFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthComputerFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("TenthComputerSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Second_half");
        }

        //Tenth computer Chapters

        else if (ref.equals("TenthComputerChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("TenthComputerChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref


                .equals("TenthComputerChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("TenthComputerChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("TenthComputerChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("TenthComputerChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("TenthComputerChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }

        // end of Tenth Computer

        //start of Tenth English

        else if (ref.equals("TenthEnglishPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthEnglishFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthEnglishFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("TenthEnglishSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Second_half");
        }

        //Tenth English Chapters

        else if (ref.equals("TenthEnglishChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("TenthEnglishChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("TenthEnglishChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("TenthEnglishChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("TenthEnglishChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("TenthEnglishChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("TenthEnglishChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("TenthEnglishChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("TenthEnglishChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("TenthEnglishChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("TenthEnglishChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("TenthEnglishChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("TenthEnglishChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("English")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }


        //end of Tenth english
        //start of Tenth math

        else if(ref.equals("TenthMathPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthMathFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("TenthMathFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("TenthMathSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Second_half");
        }

        //Tenth Math Chapters

        else if (ref.equals("TenthMathChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("TenthMathChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("TenthMathChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("TenthMathChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("TenthMathChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("TenthMathChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("TenthMathChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("TenthMathChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("TenthMathChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("TenthMathChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("TenthMathChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("TenthMathChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("TenthMathChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Tenth").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }

        //  end of math Tenth

        // First Year Start
        // First Year Physics references Start

        else if(ref.equals("FirstYearPhysicsPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearPhysicsFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearPhysicsFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("FirstYearPhysicsSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Second_half");
        }
        //FirstYear Physics Chapters

        else if (ref.equals("FirstYearPhysicsChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("FirstYearPhysicsChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("FirstYearPhysicsChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("FirstYearPhysicsChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("FirstYearPhysicsChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("FirstYearPhysicsChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("FirstYearPhysicsChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("FirstYearPhysicsChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("FirstYearPhysicsChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("FirstYearPhysicsChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("FirstYearPhysicsChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }

        // FirstYear Chemistry References Start

        if(ref.equals("FirstYearChemistryPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearChemistryFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearChemistryFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("FirstYearChemistrySecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Second_half");
        }

        //FirstYear chemistry Chapters

        else if (ref.equals("FirstYearChemistryChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("FirstYearChemistryChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("FirstYearChemistryChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("FirstYearChemistryChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("FirstYearChemistryChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("FirstYearChemistryChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("FirstYearChemistryChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("FirstYearChemistryChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("FirstYearChemistryChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("FirstYearChemistryChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("FirstYearChemistryChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        // end of FirstYear chemistry

        //start of FirstYear Bio

        if(ref.equals("FirstYearBioPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearBioFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearBioFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("FirstYearBioSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Second_half");
        }

        //First_year Bio Chapters

        else if (ref.equals("FirstYearBioChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("FirstYearBioChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("FirstYearBioChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("FirstYearBioChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("FirstYearBioChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("FirstYearBioChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("FirstYearBioChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("FirstYearBioChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("FirstYearBioChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("FirstYearBioChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("FirstYearBioChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("FirstYearBioChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("FirstYearBioChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("FirstYearBioChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }

        // end of FirstYear bio
        // Start of FirstYear computer

        if(ref.equals("FirstYearComputerPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearComputerFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearComputerFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("FirstYearComputerSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Second_half");
        }

        //FirstYear computer Chapters

        else if (ref.equals("FirstYearComputerChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("FirstYearComputerChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("FirstYearComputerChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("FirstYearComputerChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("FirstYearComputerChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("FirstYearComputerChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("FirstYearComputerChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("FirstYearComputerChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("FirstYearComputerChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("FirstYearComputerChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }

        // end of FirstYear Computer

        //start of FirstYear English

        if(ref.equals("FirstYearEnglishPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearEnglishFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearEnglishFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("FirstYearEnglishSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Second_half");
        }

        //FirstYear English Chapters

        else if (ref.equals("FirstYearEnglishChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("FirstYearEnglishChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("FirstYearEnglishChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("FirstYearEnglishChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("FirstYearEnglishChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("FirstYearEnglishChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("FirstYearEnglishChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("FirstYearEnglishChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("FirstYearEnglishChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("FirstYearEnglishChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("FirstYearEnglishChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("FirstYearEnglishChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("FirstYearEnglishChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("FirstYearEnglishChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }
        else if (ref.equals("FirstYearEnglishChapter15"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_15");
        }
        //end of FirstYear english
        //start of FirstYear math

        if(ref.equals("FirstYearMathPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearMathFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("FirstYearMathFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("FirstYearMathSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Second_half");
        }
        //FirstYear Math Chapters

        else if (ref.equals("FirstYearMathChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("FirstYearMathChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("FirstYearMathChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("FirstYearMathChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("FirstYearMathChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("FirstYearMathChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("FirstYearMathChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("FirstYearMathChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("FirstYearMathChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("FirstYearMathChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("FirstYearMathChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("FirstYearMathChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("FirstYearMathChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("FirstYearMathChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("First_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }

        // End of FirstYear Math
        // FirstYear Completed

        // Second Year Start
        // Second Year Physics references Start

        else if(ref.equals("SecondYearPhysicsPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearPhysicsFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearPhysicsFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("SecondYearPhysicsSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Second_half");
        }
        //FirstYear Physics Chapters

        else if (ref.equals("SecondYearPhysicsChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("SecondYearPhysicsChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("SecondYearPhysicsChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("SecondYearPhysicsChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("SecondYearPhysicsChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("SecondYearPhysicsChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("SecondYearPhysicsChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("SecondYearPhysicsChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("SecondYearPhysicsChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Physics")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }

        // SecondYear Chemistry References Start

        if(ref.equals("SecondYearChemistryPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearChemistryFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearChemistryFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("SecondYearChemistrySecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Second_half");
        }

        //SecondYear chemistry Chapters
        else if (ref.equals("SecondYearChemistryChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("SecondYearChemistryChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("SecondYearChemistryChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("SecondYearChemistryChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("SecondYearChemistryChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("SecondYearChemistryChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("SecondYearChemistryChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("SecondYearChemistryChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("SecondYearChemistryChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("SecondYearChemistryChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("SecondYearChemistryChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("SecondYearChemistryChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("SecondYearChemistryChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("SecondYearChemistryChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }
        else if (ref.equals("SecondYearChemistryChapter15"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_15");
        }
        else if (ref.equals("SecondYearChemistryChapter16"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Chemistry")
                    .child("Type").child("Chapter_wise").child("chap_16");
        }
        // end of SecondYear chemistry

        //start of SecondYear Bio

        if(ref.equals("SecondYearBioPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearBioFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearBioFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("SecondYearBioSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Second_half");
        }

        //Second_year Bio Chapters

        else if (ref.equals("SecondYearBioChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("SecondYearBioChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("SecondYearBioChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("SecondYearBioChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("SecondYearBioChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("SecondYearBioChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("SecondYearBioChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("SecondYearBioChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("SecondYearBioChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("SecondYearBioChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("SecondYearBioChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("SecondYearBioChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("SecondYearBioChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Biology")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }

        // end of SecondYear bio
        // Start of SecondYear computer

        if(ref.equals("SecondYearComputerPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearComputerFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearComputerFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("SecondYearComputerSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Second_half");
        }

        //SecondYear computer Chapters

        else if (ref.equals("SecondYearComputerChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("SecondYearComputerChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("SecondYearComputerChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("SecondYearComputerChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("SecondYearComputerChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("SecondYearComputerChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("SecondYearComputerChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("SecondYearComputerChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("SecondYearComputerChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("SecondYearComputerChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("SecondYearComputerChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("SecondYearComputerChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("SecondYearComputerChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("SecondYearComputerChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Computer")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }


        // end of SecondYear Computer

        //start of SecondYear English

        if(ref.equals("SecondYearEnglishPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearEnglishFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearEnglishFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("SecondYearEnglishSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Second_half");
        }

        //SecondYear English Chapters

        else if (ref.equals("SecondYearEnglishChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("SecondYearEnglishChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("SecondYearEnglishChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("SecondYearEnglishChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("SecondYearEnglishChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("SecondYearEnglishChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("SecondYearEnglishChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }
        else if (ref.equals("SecondYearEnglishChapter8"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_8");
        }
        else if (ref.equals("SecondYearEnglishChapter9"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_9");
        }
        else if (ref.equals("SecondYearEnglishChapter10"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_10");
        }
        else if (ref.equals("SecondYearEnglishChapter11"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_11");
        }
        else if (ref.equals("SecondYearEnglishChapter12"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_12");
        }
        else if (ref.equals("SecondYearEnglishChapter13"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_13");
        }
        else if (ref.equals("SecondYearEnglishChapter14"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_14");
        }
        else if (ref.equals("SecondYearEnglishChapter15"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("English")
                    .child("Type").child("Chapter_wise").child("chap_15");
        }
        //end of SecondYear english
        //start of SecondYear math

        if(ref.equals("SecondYearMathPastPaper"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearMathFullBook"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Full_book");
        }
        else if (ref.equals("SecondYearMathFirstHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("First_half");
        }
        else if (ref.equals("SecondYearMathSecondHalf"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Second_half");
        }
        //SecondYear Math Chapters

        else if (ref.equals("SecondYearMathChapter1"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_1");
        }

        else if (ref.equals("SecondYearMathChapter2"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_2");
        }
        else if (ref.equals("SecondYearMathChapter3"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_3");
        }
        else if (ref.equals("SecondYearMathChapter4"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_4");
        }
        else if (ref.equals("SecondYearMathChapter5"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_5");
        }
        else if (ref.equals("SecondYearMathChapter6"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_6");
        }
        else if (ref.equals("SecondYearMathChapter7"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Classes").child("Second_year").child("Math")
                    .child("Type").child("Chapter_wise").child("chap_7");
        }

        //for general knowledge 07
        else if (ref.equals("science"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Science");
        }
        else if (ref.equals("history"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("History");
        }
        else if (ref.equals("sports"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Sports");
        }
        else if (ref.equals("entertainment"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Entertainmmet");
        }

        // End of general knowledge


        //Start of NTS

        else if (ref.equals("nts_english"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("NTS_English");
        }
        else if (ref.equals("nts_science"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("NTS_Science");
        }
        else if (ref.equals("islamiyat"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("NTS_Islamiyat");
        }
        else if (ref.equals("current"))
        {
            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Current_Affairs");
        }

        else if(ref.equals("gk"))
        {

            mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("NTS_gk");

        }
        else if (ref.equals("pak"))

        {
            mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("NTS_pak");
        }


        Query query = mDatabaseReference.orderByKey();

        if(isInternetWorking()) {

            query.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        multipleQuestions = new ArrayList<>();

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            ModelClass uInfo = new ModelClass();
                            uInfo.setQuestion(ds.getValue(ModelClass.class).getQuestion().trim());
                            uInfo.setOption1(ds.getValue(ModelClass.class).getOption1().trim());
                            uInfo.setOption2(ds.getValue(ModelClass.class).getOption2().trim());
                            uInfo.setOption3(ds.getValue(ModelClass.class).getOption3().trim());
                            uInfo.setOption4(ds.getValue(ModelClass.class).getOption4().trim());
                            uInfo.setAnswer(ds.getValue(ModelClass.class).getAnswer().trim());

                            multipleQuestions.add(uInfo);
                        }

                        mProgressDialog.dismiss();

                        Random randomNumberGenerator = new Random();
                        if (multipleQuestions.size()>temp) {
                            int num = randomNumberGenerator.nextInt(multipleQuestions.size());
                            list.add(num);
                            while(list.size()<temp){
                                int rnum = randomNumberGenerator.nextInt(multipleQuestions.size());
                                int status=0;
                                for(int i=0;i<list.size();i++){
                                    if(list.get(i)==rnum){
                                        status=1;
                                    }
                                }
                                if(status==0){
                                    list.add(rnum);
                                }
                            }
                            // Toast.makeText(QuizScreenActivity.this, "aray list : "+list.size(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            int num = randomNumberGenerator.nextInt(multipleQuestions.size());
                            list.add(num);
                            while(list.size()<multipleQuestions.size()){
                                int rnum = randomNumberGenerator.nextInt(multipleQuestions.size());
                                int status=0;
                                for(int i=0;i<list.size();i++){
                                    if(list.get(i)==rnum){
                                        status=1;
                                    }
                                }
                                if(status==0){
                                    list.add(rnum);
                                }
                            }
                            // Toast.makeText(QuizScreenActivity.this, "aray list : "+list.size(), Toast.LENGTH_SHORT).show();

                        }


                        mQuestion = multipleQuestions.get(list.get(i)).getQuestion().trim();
                        mOption1 = multipleQuestions.get(list.get(i)).getOption1().trim();
                        mOption2 = multipleQuestions.get(list.get(i)).getOption2().trim();
                        mOption3 = multipleQuestions.get(list.get(i)).getOption3().trim();
                        mOption4 = multipleQuestions.get(list.get(i)).getOption4().trim();
                        mAnswer = multipleQuestions.get(list.get(i)).getAnswer().trim();

                        mQuestionTextView.setText(mQuestion);
                        btnOption1.setText(mOption1);
                        btnOption2.setText(mOption2);
                        btnOption3.setText(mOption3);
                        btnOption4.setText(mOption4);

                        mScoreTextView.setText("Score: " + mScore);
                        mQuestionNumber.setText("Q#"+mNumber);
                        setSameSize();
                        startTimer();

                        btnOption1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption1 = multipleQuestions.get(list.get(i)).getOption1();
                                checkAnswer(mOption1,mOption2,mOption3,mOption4,mOption1,btnOption1);
                            }
                        });
                        btnOption2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption2 = multipleQuestions.get(list.get(i)).getOption2();
                                checkAnswer(mOption1,mOption2,mOption3,mOption4,mOption2,btnOption2);

                            }
                        });
                        btnOption3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption3 = multipleQuestions.get(list.get(i)).getOption3();
                                checkAnswer(mOption1,mOption2,mOption3,mOption4,mOption3,btnOption3);

                            }
                        });
                        btnOption4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption4 = multipleQuestions.get(list.get(i)).getOption4();
                                checkAnswer(mOption1,mOption2,mOption3,mOption4,mOption4,btnOption4);

                            }
                        });

//               for (ModelClass mc : multipleQuestions){
//                   Toast.makeText(NinthPastPapers.this, "values : "+ mc.getQuestion(), Toast.LENGTH_SHORT).show();
//                }
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(MainQuizScreen.this, "Something went wrong! please check your internet connection", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mProgressDialog.dismiss();
                    Toast.makeText(MainQuizScreen.this, "Something went wrong! please check your internet connection", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            mProgressDialog.dismiss();
            Toast.makeText(MainQuizScreen.this, "Something went wrong! please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }
    private  void updateQuestion(){
        mNext.setVisibility(View.GONE);
        //i=(i+1)%multipleQuestions.size();
        i=i+1;
        if (i >= list.size()){
            int size=list.size();
            numberOfQuestions=list.size();
            Double p = (double) ((mScore*100) / size);
            int s=(int) Math.round(p);
            percent=s;
            if(loadOnce){
                mFireBaseFunction();
                loadOnce =false;
                mIntent = new Intent(MainQuizScreen.this,Report.class);
                mIntent.putExtra("s",s);
                mIntent.putExtra("ra",mScore);
                mIntent.putExtra("TotalQuestions", list.size());
                mIntent.putExtra("secondRef", secondRef);
                mCountDownTimer.cancel();
                startActivity(mIntent);
                finish();
            }

        }

        else {
            mQuestion = multipleQuestions.get(list.get(i)).getQuestion().trim();
            mOption1 = multipleQuestions.get(list.get(i)).getOption1().trim();
            mOption2 = multipleQuestions.get(list.get(i)).getOption2().trim();
            mOption3 = multipleQuestions.get(list.get(i)).getOption3().trim();
            mOption4 = multipleQuestions.get(list.get(i)).getOption4().trim();
            mAnswer = multipleQuestions.get(list.get(i)).getAnswer().trim();

            mQuestionTextView.setText(mQuestion);
            btnOption1.setText(mOption1);
            btnOption2.setText(mOption2);
            btnOption3.setText(mOption3);
            btnOption4.setText(mOption4);

            mScoreTextView.setText("Score: " + mScore);
            mQuestionNumber.setText("Q#" + mNumber);
            setSameSize();
            startTimer();
        }
    }

    private void mFireBaseFunction() {
        DatabaseReference mref= FirebaseDatabase.getInstance().getReference().child("stat").child(mAuth.getCurrentUser().getUid());
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                    StatModel userStat = dataSnapshot.getValue(StatModel.class);
                    tq = userStat.getTotal_ques();
                    pc = userStat.getScore();
                    ss = userStat.getSilver();
                    gg = userStat.getGold();
                    pp = userStat.getPlatinum();
                    xp = userStat.getXp();
                    PName=userStat.getPersonName();
                    PImage=userStat.getJobProfile();
                    totalQuestions=userStat.getTotalQuestionsAttempted();

                    totalQuestions=totalQuestions+numberOfQuestions;
                    tq=tq+1;

                    if (percent>=90)
                    {
                        mScore*=4;
                        pp=pp+1;
                        xp = xp+4;
                    }
                    else if (percent>=80)
                    {
                        mScore*=3;
                        gg=gg+1;
                        xp = xp+3;
                    }
                    else if (percent>=70)
                    {
                        mScore*=2;
                        ss=ss+1;
                        xp= xp+2;
                    }
                    else if (percent>=0)
                    {
                        xp=xp+1;
                    }

                    pc=pc+mScore;

                    mRootRef = FirebaseDatabase.getInstance().getReference();
                    mRootRef.child("Users").child(mAuth.getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){

                                        UserDataModel user = dataSnapshot.getValue(UserDataModel.class);
                                        PImage=user.getUser_image();
                                        PName=user.getUser_name();


                                        StatModel mmModel = new StatModel(totalQuestions, tq,pc,ss,gg,pp, xp, PName,PImage,mAuth.getCurrentUser().getUid());

                                        FirebaseDatabase.getInstance().getReference().child("stat").child(mAuth.getCurrentUser().getUid()).setValue(mmModel);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                }

                else
                {
                    totalQuestions=numberOfQuestions;
                    tq=tq+1;

                    if (percent>=80)

                    {
                        mScore*=4;
                        pp=pp+1;
                        xp=xp+4;
                    }
                    else if (percent>=70)
                    {
                        mScore*=3;
                        gg=gg+1;
                        xp=xp+3;
                    }
                    else if (percent>=60)
                    {
                        mScore*=2;
                        ss=ss+1;
                        xp=xp+2;
                    }
                    else if(percent>=0)
                    {
                        xp=xp+1;
                    }
                    pc=mScore;

                    //start

                    mRootRef = FirebaseDatabase.getInstance().getReference();
                    mRootRef.child("Users").child(mAuth.getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){

                                        UserDataModel user = dataSnapshot.getValue(UserDataModel.class);
                                        PImage=user.getUser_image();
                                        PName=user.getUser_name();


                                        StatModel mmModel = new StatModel(totalQuestions, tq,pc,ss,gg,pp, xp, PName,PImage,mAuth.getCurrentUser().getUid());
                                        //tq= total question, pc = percentage

                                        FirebaseDatabase.getInstance().getReference().child("stat").child(mAuth.getCurrentUser().getUid()).setValue(mmModel);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void checkAnswer(String p1,String p2,String p3,String p4,String userSelection, Button btnTrue) {

        mNext.setVisibility(View.VISIBLE);

        String correctAnswer = multipleQuestions.get(list.get(i)).getAnswer();

        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);
        btnOption3.setEnabled(false);
        btnOption4.setEnabled(false);

        if (userSelection.equals(correctAnswer)) {
            if (volume) {
                mSoundPool.CorrectSound();
            }

            btnTrue.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            mScore=mScore+1;

//            final Dialog dialog =new Dialog(QuizScreenActivity.this);
//            dialog.setContentView(R.layout.wrong_answer_dialog);
//            dialog.setCancelable(false);
//            dialog.show();
//
//            Button btn =dialog.findViewById(R.id.ok);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
        }

        else {

            if (volume) {
                mSoundPool.WrongSound();
            }

            btnTrue.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            if(p1.equals(correctAnswer)){
                btnOption1.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if(p2.equals(correctAnswer)){
                btnOption2.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if(p3.equals(correctAnswer)){
                btnOption3.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }
            if(p4.equals(correctAnswer)){
                btnOption4.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            }

//            mNumber=mNumber+1;
//            setSameSize();

//            final Dialog dialog =new Dialog(QuizScreenActivity.this);
//            dialog.setContentView(R.layout.dialog);
//            dialog.setCancelable(false);
//            dialog.show();
//
//            TextView textView=dialog.findViewById(R.id.get_answer);
//            Button btn =dialog.findViewById(R.id.ok);
//            textView.setText(correctAnswer);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
            // Toast.makeText(QuizScreenActivity.this, "Wrong! correct answer is " + correctAnswer, Toast.LENGTH_LONG).show();
        }
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCountDownTimer.cancel();
                updateQuestion();
                mNext.setVisibility(View.GONE);
            }
        });
    }
    public void internetToast() {
        if (!connectionOnline()) {
            Toast.makeText(MainQuizScreen.this, "You're not connected to internet. Connect to internet and try again!", Toast.LENGTH_LONG).show();
        }else if(!isInternetWorking())
        {
            Toast.makeText(MainQuizScreen.this, "Something went wrong, Check your connection!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean connectionOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public boolean isInternetWorking() {
        boolean success = false;
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Android");
            connection.setRequestProperty("Connection", "close");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.connect();
            if (connection.getResponseCode() == 200) success = true;
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }



    public void setSameSize(){

        if (mNumber>multipleQuestions.size()){
            mNumber=multipleQuestions.size();
        }
    }

    private void startTimer() {

        resetTimer();

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                upDateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mCountDownTimer.cancel();
                updateQuestion();

            }
        }.start();

        mTimerRunning=true;
        mNumber=mNumber+1;
        setSameSize();
        btnOption1.setEnabled(true);
        btnOption2.setEnabled(true);
        btnOption3.setEnabled(true);
        btnOption4.setEnabled(true);

        btnOption1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        btnOption2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        btnOption3.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        btnOption4.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
    }

    private void upDateCountDownText(){
        int minuts=(int)(mTimeLeftInMillis/1000)/60;
        int seconds=(int)(mTimeLeftInMillis/1000)%60;

       // long seconds = leftTimeInMilliseconds / 1000;
        pb.setProgress((int)seconds);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d",seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    private void resetTimer()
    {
        mTimeLeftInMillis=START_TIME_IN_MILLIS;
        upDateCountDownText();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mp.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainQuizScreen.this);
        musicVolume = preferences.getBoolean("music", true);
        volume = preferences.getBoolean("btn_sound", true);
        if (musicVolume) {
//            musicVolume = false;
            mp.release();
            mp = MediaPlayer.create(this, R.raw.files);
            mp.setLooping(true);
            mp.setVolume(0.5f,0.5f);
            mp.start();

        } else {
//              musicVolume = true;
            mp.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            mp.release();
            mCountDownTimer.cancel();
        }

        catch (Exception e){
            Log.d("TAG",e.toString());
        }
    }

}
