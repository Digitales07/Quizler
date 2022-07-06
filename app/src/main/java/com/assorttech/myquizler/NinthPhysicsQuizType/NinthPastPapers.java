package com.assorttech.myquizler.NinthPhysicsQuizType;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assorttech.myquizler.Model.ModelClass;
import com.assorttech.myquizler.R;
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

public class NinthPastPapers extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private TextView mQuestionTextView, mScoreTextView;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    String mQuestion, mOption1, mOption2, mOption3, mOption4, mAnswer;
    int mScore, i;
    private ProgressDialog mProgressDialog;

    List<ModelClass> multipleQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninth_past_papers);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mScoreTextView = findViewById(R.id.score);

        if (savedInstanceState != null) {

            mScore = savedInstanceState.getInt("ScoreKey");
            i = savedInstanceState.getInt("IndexKey");

        } else {
            mScore = 0;
            i = 0;
        }

        mQuestionTextView = findViewById(R.id.qusetionView);
        btnOption1 = findViewById(R.id.button1);
        btnOption2 = findViewById(R.id.button2);
        btnOption3 = findViewById(R.id.button3);
        btnOption4 = findViewById(R.id.button4);

      //  internetToast();

        mProgressDialog =new ProgressDialog(NinthPastPapers.this);
        mProgressDialog.setTitle("Please Wait ");
        mProgressDialog.setMessage("Retrieving Questions");

        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Query query = mDatabaseReference.child("Classes").child("Ninth").child("Physics")
                .child("Type").child("Past_paper").orderByKey();
        if(isInternetWorking()) {
            query.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        multipleQuestions = new ArrayList<>();

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            ModelClass uInfo = new ModelClass();
                            uInfo.setQuestion(ds.getValue(ModelClass.class).getQuestion());
                            uInfo.setOption1(ds.getValue(ModelClass.class).getOption1());
                            uInfo.setOption2(ds.getValue(ModelClass.class).getOption2());
                            uInfo.setOption3(ds.getValue(ModelClass.class).getOption3());
                            uInfo.setOption4(ds.getValue(ModelClass.class).getOption4());
                            uInfo.setAnswer(ds.getValue(ModelClass.class).getAnswer());

                            multipleQuestions.add(uInfo);
                        }

                        mProgressDialog.dismiss();

                        mQuestion = multipleQuestions.get(i).getQuestion();
                        mOption1 = multipleQuestions.get(i).getOption1();
                        mOption2 = multipleQuestions.get(i).getOption3();
                        mOption3 = multipleQuestions.get(i).getOption4();
                        mOption4 = multipleQuestions.get(i).getOption4();
                        mAnswer = multipleQuestions.get(i).getAnswer();

                        mQuestionTextView.setText(mQuestion);
                        btnOption1.setText(mOption1);
                        btnOption2.setText(mOption2);
                        btnOption3.setText(mOption3);
                        btnOption4.setText(mOption4);

                        mScoreTextView.setText("Score " + mScore + "/" + multipleQuestions.size());

                        btnOption1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption1 = multipleQuestions.get(i).getOption1();

                                checkAnswer(mOption1);
                                updateQuestion();
                            }
                        });
                        btnOption2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption2 = multipleQuestions.get(i).getOption2();
                                checkAnswer(mOption2);
                                updateQuestion();
                            }
                        });
                        btnOption3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption3 = multipleQuestions.get(i).getOption3();
                                checkAnswer(mOption3);
                                updateQuestion();
                            }
                        });
                        btnOption4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mOption4 = multipleQuestions.get(i).getOption4();

                                checkAnswer(mOption4);
                                updateQuestion();
                            }
                        });


//               for (ModelClass mc : multipleQuestions){
//                   Toast.makeText(NinthPastPapers.this, "values : "+ mc.getQuestion(), Toast.LENGTH_SHORT).show();
//                }
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(NinthPastPapers.this, "Something went wrong! please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    mProgressDialog.dismiss();
                    Toast.makeText(NinthPastPapers.this, "Something went wrong! please check your internet connection", Toast.LENGTH_SHORT).show();


                }
            });
        }
        else{
            mProgressDialog.dismiss();
            Toast.makeText(NinthPastPapers.this, "Something went wrong! please check your internet connection", Toast.LENGTH_SHORT).show();


        }
}

    private  void updateQuestion(){

        i=(i+1)%multipleQuestions.size();
        if (i==0){
            final AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("your score"+"  "+mScore+" / "+multipleQuestions.size());
            alert.setPositiveButton("Close  ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });

            alert.setNegativeButton("Restart Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mScore=0;
                    mScoreTextView.setText("Score "+mScore+"/"+multipleQuestions.size());
                    dialog.cancel();
                }
            });

            alert.show();
        }

        mQuestion = multipleQuestions.get(i).getQuestion();
        mOption1 = multipleQuestions.get(i).getOption1();
        mOption2 = multipleQuestions.get(i).getOption2();
        mOption3 = multipleQuestions.get(i).getOption3();
        mOption4 = multipleQuestions.get(i).getOption4();
        mAnswer = multipleQuestions.get(i).getAnswer();

        mQuestionTextView.setText(mQuestion);
        btnOption1.setText(mOption1);
        btnOption2.setText(mOption2);
        btnOption3.setText(mOption3);
        btnOption4.setText(mOption4);

        mScoreTextView.setText("Score "+mScore+"/"+multipleQuestions.size());

    }
    public void checkAnswer(String userSelection) {
        String correctAnswer = multipleQuestions.get(i).getAnswer();
        if (userSelection.equals(correctAnswer)) {

            Log.d("Check","got it"+userSelection);

            Toast.makeText(NinthPastPapers.this, "You got it!", Toast.LENGTH_LONG).show();
            mScore=mScore+1;

        } else {

            Toast.makeText(NinthPastPapers.this, "Wrong! correct answer is "+correctAnswer, Toast.LENGTH_LONG).show();

        }

    }
    public void internetToast() {
        if (!connectionOnline()) {
            Toast.makeText(NinthPastPapers.this, "You're not connected to internet. Connect to internet and try again!", Toast.LENGTH_LONG).show();
        }else if(!isInternetWorking())
        {
            Toast.makeText(NinthPastPapers.this, "Something went wrong, Check your connection!", Toast.LENGTH_LONG).show();
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
}

