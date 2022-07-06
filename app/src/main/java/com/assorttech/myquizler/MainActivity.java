package com.assorttech.myquizler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private CountryCodePicker mCountryCode;
    String countryCode="92";
    private static final String TAG = "PhoneAuth";
    private EditText mEditTextNumber;
    private EditText mEditTextCode;
    private Button mButtonRegisteUser;
  //  private Button verifyButton;
   // private Button resendButton;
    private FirebaseAuth mAuth;
    private String phoneVerificationId;
    private ProgressDialog mProgressDialog;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private Dialog mVerificationDialog;

    EditText etPasswordUser;
    TextView timer_view;
    Button resend;
    Button verify;

    // Timer components

    private static final long START_TIME_IN_MILLIS = 600000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(this);
        mEditTextNumber = findViewById(R.id.etPhoneNoUser);
        mCountryCode = findViewById(R.id.etCountryCode);
        mEditTextCode = findViewById(R.id.etPasswordUser);
        mButtonRegisteUser = findViewById(R.id.btn_RegisterUser);

        mAuth = FirebaseAuth.getInstance();

        mCountryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = mCountryCode.getSelectedCountryCode();
            }
        });
        mButtonRegisteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                checkFields();
            }
        });

    }

    private void checkFields()
    {

        String phone = mEditTextNumber.getText().toString().trim();

        if(phone.length() < 4)
        {
            mEditTextNumber.setError("Please enter a valid phone number.");
            mEditTextNumber.requestFocus();
            return;
        }
        else{
            showConfirmationDialog();}


    }

    private void showConfirmationDialog() {
        String phone = mEditTextNumber.getText().toString().trim();
        final String Number = "+"+countryCode+phone;
        AlertDialog.Builder mAlert = new AlertDialog.Builder(this);
        mAlert.setTitle("Is this your number?");
        mAlert.setMessage("We'll send the verification code to "+Number+" to verify the number.");
        mAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                sendCode(Number);

            }
        });
        mAlert.show();

    }

    private void checkUserAndRedirect() {
       String uid= mAuth.getCurrentUser().getUid();
       FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("user_gender").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   startActivity(new Intent(MainActivity.this,FirstMainScreen.class));
                   finish();
               }
               else
                   {

                       String phone = mEditTextNumber.getText().toString().trim();
                       String Number = "+"+countryCode+phone;
                       Intent i=new Intent(MainActivity.this,SignUpActivity.class);
                       i.putExtra("number", Number);
                       startActivity(i);
                       finish();
                   }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    private void sendCode(String Number) {
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        setUpVerificatonCallbacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);


    }
    private void setUpVerificatonCallbacks() {

        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(
                    PhoneAuthCredential credential) {

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                mProgressDialog.dismiss();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d(TAG, "Invalid credential: "
                            + e.getLocalizedMessage());


                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // SMS quota exceeded
                    Log.d(TAG, "SMS Quota exceeded.");
                }
                Toast.makeText(MainActivity.this, "Verification Failed! Try Again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                startTimer();

                mProgressDialog.dismiss();

                mVerificationDialog = new Dialog(MainActivity.this, android.R.style.Theme_Light);
                mVerificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mVerificationDialog.setContentView(R.layout.activity_verification_code);
                TextView mText = mVerificationDialog.findViewById(R.id.tv1);

                String phone = mEditTextNumber.getText().toString().trim();
                final String Number = "+"+countryCode+phone;

                mText.setText("Please enter 6 digit code send on Number "+Number);
                etPasswordUser= mVerificationDialog.findViewById(R.id.etPasswordUser);

                verify = mVerificationDialog.findViewById(R.id.btn_verify);
                resend = mVerificationDialog.findViewById(R.id.btn_resend);
                timer_view = mVerificationDialog.findViewById(R.id.timerView);

                mVerificationDialog.show();

                phoneVerificationId = verificationId;
                resendToken = token;

                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        verifyCode();
                    }
                });
                mVerificationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        mCountDownTimer.cancel();

                    }
                });
//                resend.setEnabled(true);
//                resend.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mVerificationDialog.dismiss();
//                        resendCode();
//                    }
//                });
            }
        };
    }

    public void verifyCode() {

        String code = etPasswordUser.getText().toString().trim();

        // String code = mEditTextCode.getText().toString();
        if(code.length()<6)
        {
            Toast.makeText(this, "Please Enter a valid code", Toast.LENGTH_SHORT).show();

        }

        else{
            mVerificationDialog.dismiss();
            try {
                PhoneAuthCredential credential =
                        PhoneAuthProvider.getCredential(phoneVerificationId, code);
                signInWithPhoneAuthCredential(credential);
            }
            catch (Exception e)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("please enter valid code");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
      }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            checkUserAndRedirect();

                        } else {
                            mProgressDialog.dismiss();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(MainActivity.this, "Invalid Code!", Toast.LENGTH_SHORT).show();
//                                mEditTextCode.setError("Enter Valid Code");
//                                mEditTextCode.requestFocus();
                            }
                        }
                    }
                });
    }
    public void resendCode() {
        String phone = mEditTextNumber.getText().toString().trim();
        String Number = "+"+countryCode+phone;
        setUpVerificatonCallbacks();
        mVerificationDialog.dismiss();
        Toast.makeText(this, "Resending Code...", Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Number,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    private void startTimer() {
        mTimeLeftInMillis=60000;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
//                mTimerRunning = false;

                mCountDownTimer.cancel();
                resend.setVisibility(View.VISIBLE);
                resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resendCode();
                        mCountDownTimer.start();
                        resend.setVisibility(View.INVISIBLE);

                    }
                });

               // Toast.makeText(MainActivity.this, " i m in finish timer ", Toast.LENGTH_SHORT).show();
            }
        }.start();

        mTimerRunning = true;

       // Toast.makeText(this, "i m in resend timer ", Toast.LENGTH_SHORT).show();
    }

    private void updateCountDownText() {

        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d",  seconds);
        timer_view.setText("Resend in "+timeLeftFormatted +" seconds");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{

            mCountDownTimer.cancel();
        }
        catch (Exception e){
            Log.d("TAG",e.toString());
        }
    }
}
