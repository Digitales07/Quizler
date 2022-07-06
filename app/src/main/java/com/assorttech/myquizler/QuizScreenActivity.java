package   com.assorttech.myquizler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.assorttech.myquizler.Model.CoolFactsModel;

import java.util.Random;

public class QuizScreenActivity extends AppCompatActivity {
    private long mLastClickTime = 0;
    private ProgressDialog mProgressDialog;
    private TextView mFactsView;
    int mIndex=0;
    int coolFact;

    MySoundPool mMySoundPool;
    int temp;
    ImageView btnFifteen, btnTwenty,btnThirty;

    // TODO: cool facts bank
    private CoolFactsModel[] mCoolFacts = new CoolFactsModel[]{
            new CoolFactsModel(R.string.fact_1),
            new CoolFactsModel(R.string.fact_2),
            new CoolFactsModel(R.string.fact_3),
            new CoolFactsModel(R.string.fact_4),
            new CoolFactsModel(R.string.fact_5),
            new CoolFactsModel(R.string.fact_6),
            new CoolFactsModel(R.string.fact_7),
            new CoolFactsModel(R.string.fact_8),
            new CoolFactsModel(R.string.fact_9),
            new CoolFactsModel(R.string.fact_10),
            new CoolFactsModel(R.string.fact_11),
            new CoolFactsModel(R.string.fact_12),
            new CoolFactsModel(R.string.fact_13)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_screen);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait... ");

        mMySoundPool = new MySoundPool(this);

        final String Ref = getIntent().getExtras().get("Ref").toString();


         btnFifteen = findViewById(R.id.fifteen);
         btnTwenty =  findViewById(R.id.twenty);
         btnThirty =  findViewById(R.id.thirty);

         mFactsView=findViewById(R.id.fact);


        Random random = new Random();

        int maxIndex = mCoolFacts.length;
        int generatedIndex = random.nextInt(maxIndex);

        mFactsView.setText(mCoolFacts[generatedIndex].getFacts());

//        coolFact= mCoolFacts[mIndex].getFacts();
//         mFactsView.setText(coolFact);
//

//        btnFifteen.getBackground().setColorFilter(getResources().getColor(R.color.blue_one), PorterDuff.Mode.MULTIPLY);
//        btnTwenty.getBackground().setColorFilter(getResources().getColor(R.color.blue_two), PorterDuff.Mode.MULTIPLY);
//        btnThirty.getBackground().setColorFilter(getResources().getColor(R.color.blue_three), PorterDuff.Mode.MULTIPLY);
                btnFifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mProgressDialog.show();
                mLastClickTime = SystemClock.elapsedRealtime();
                temp=15;
                mMySoundPool.ClickSound();
                Intent intent = new Intent(QuizScreenActivity.this, MainQuizScreen.class);
                intent.putExtra("Ref",Ref);
                intent.putExtra("temp",temp);
                mProgressDialog.dismiss();
                finish();
                startActivity(intent);
            }
        });
        btnTwenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mProgressDialog.show();

                mLastClickTime = SystemClock.elapsedRealtime();
                temp=20;
                mMySoundPool.ClickSound();
                Intent intent = new Intent(QuizScreenActivity.this, MainQuizScreen.class);
                intent.putExtra("Ref",Ref);
                intent.putExtra("temp",temp);
                mProgressDialog.dismiss();
                finish();
                startActivity(intent);
            }
        });
        btnThirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mProgressDialog.show();
                mLastClickTime = SystemClock.elapsedRealtime();
                temp=30;
                mMySoundPool.ClickSound();
                Intent intent = new Intent(QuizScreenActivity.this, MainQuizScreen.class);
                intent.putExtra("Ref",Ref);
                intent.putExtra("temp",temp);
                mProgressDialog.dismiss();
                finish();
                startActivity(intent);
            }
        });
    }

    private  void updateFact(){

        mIndex=(mIndex+1)%mCoolFacts.length;
        if (mIndex==0){

            coolFact = mCoolFacts[mIndex].getFacts();
            mFactsView.setText(coolFact);
        }

        coolFact = mCoolFacts[mIndex].getFacts();
        mFactsView.setText(coolFact);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.marqueetext, menu);
//        return true;
//    }
}