package com.assorttech.myquizler;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

public class SplashScreen extends AppCompatActivity {
    ImageView mImageView;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth =  FirebaseAuth.getInstance();
        mImageView=findViewById(R.id.image_splash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        mImageView.startAnimation(animation);

        Thread timer= new Thread(){

            @Override
            public void run() {

                try {
                    sleep(6000);
                    super.run();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.start();

           }

    private void checkExistingUserAndRedirect() {
        String uid=mAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("user_gender").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    startActivity(new Intent(SplashScreen.this,FirstMainScreen.class));
                    finish();
                }
                else
                {
                    mAuth.signOut();
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SplashScreen.this, "Something went wrong! Check your connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isOnline()) {

            checkAppVersion();
        }
        else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("Internet Connection Error!");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Log.d(Constants.WIRE_PROTOCOL_VERSION, "Show Dialog: " + e.getMessage());
            }
        }

    }

    private void checkAppVersion() {
        FirebaseDatabase.getInstance().getReference().child("AppVersion").child("VersionName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String version = dataSnapshot.getValue().toString();
                    if(version.equals("2.1.3"))
                    {
                        checkUser();
                    }
                    else {
                        showUpdateAlert();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SplashScreen.this, "Something went wrong! Check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateAlert() {
        AlertDialog.Builder mAlert = new AlertDialog.Builder(this);
        mAlert.setCancelable(false);
        mAlert.setTitle("App Update Required");
        mAlert.setMessage("Please update the app to continue taking Quizzes.");
        mAlert.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        }).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Uri uri = Uri.parse("market://details?id=" + getApplication().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (
                        ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + getApplication().getPackageName())));
                }
                finish();

            }
        });
        mAlert.show();
    }

    private void checkUser() {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser!=null){
            checkExistingUserAndRedirect();
        }
        else {
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
