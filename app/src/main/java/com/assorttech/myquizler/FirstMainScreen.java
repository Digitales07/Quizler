package com.assorttech.myquizler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;

import com.assorttech.myquizler.Profile.ProfileActivity;
import com.assorttech.myquizler.services.Converter;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.assorttech.myquizler.Classes.FSC;
import com.assorttech.myquizler.Classes.Metriculation;
import com.assorttech.myquizler.Classes.NTS;
import com.assorttech.myquizler.Model.UserDataModel;
import com.assorttech.myquizler.services.Receiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstMainScreen extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    MySoundPool mMySoundPool;
    CardView mMet, mFSC, mGk, mNTS;
    String mToolbarTitle = "Quizler";
    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    long count = 0;

    String saveName;

    SharedPreferences preferences;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main_screen);

        preferences  = PreferenceManager.getDefaultSharedPreferences(this);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        final CircleImageView navImageView =  headerView.findViewById(R.id.user_image_civ);
        //final CircleImageView staticsImageView = findViewById(R.id.statics_image_view);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_tv_name);

        navImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(mToolbarTitle.equals("Quizler"))
                    {
                        Toast.makeText(FirstMainScreen.this, "Check your Connection and try again!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(FirstMainScreen.this, ProfileActivity.class);
                        intent.putExtra("user_id", mAuth.getCurrentUser().getUid().toString());  // pass your values and retrieve them in the other Activity using keyName
                        startActivity(intent);
                    }
            }
        });
//        staticsImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(mToolbarTitle.equals("Quizler"))
//                {
//                    Toast.makeText(FirstMainScreen.this, "Check your Connection and try again!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Intent intent = new Intent(FirstMainScreen.this,AllUserActivity.class);
//                    intent.putExtra("user_name", mToolbarTitle);  // pass your values and retrieve them in the other Activity using keyName
//                    startActivity(intent);
//                }
//            }
//        });

       navigationView.setNavigationItemSelectedListener(this);
        mMySoundPool=new MySoundPool(this);




        final Toolbar toolbar = findViewById(R.id.toolBarMain);
        toolbar.setTitle("Quizler");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mMet=findViewById(R.id.metriculation);
        mFSC=findViewById(R.id.fsc);
        mGk=findViewById(R.id.gk);
        mNTS=findViewById(R.id.nts);
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mUserRef.keepSynced(true);
        mUserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                          //  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final UserDataModel user = dataSnapshot.getValue(UserDataModel.class);
                                mToolbarTitle = user.getUser_display_name();
                                navUsername.setText(user.getUser_display_name()+"  ("+user.getUser_name()+")");

                                saveName=user.getUser_display_name();

                            if (saveName!=null) {

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Name",saveName);
                                editor.apply();
                            }
                            else {

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Name"," Dear User ");
                                editor.apply();
                            }

                                Picasso.get().load(user.getUser_image()).networkPolicy(NetworkPolicy.OFFLINE).into(navImageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Picasso.get().load(user.getUser_image()).into(navImageView);

                                    }
                                });
//                                Picasso.get().load(user.getUser_image()).networkPolicy(NetworkPolicy.OFFLINE).into(staticsImageView, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//
//                                    }
//                                    @Override
//                                    public void onError(Exception e) {
//
//                                        Picasso.get().load(user.getUser_image()).into(staticsImageView);
//
//                                    }
//                                });
                         //   }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        mMet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMySoundPool.ClickSound();
                startActivity(new Intent(FirstMainScreen.this, Metriculation.class));
            }
        });

        mFSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMySoundPool.ClickSound();
                startActivity(new Intent(FirstMainScreen.this, FSC.class));
            }
        });
        mGk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMySoundPool.ClickSound();
                startActivity(new Intent(FirstMainScreen.this, GeneralKnowledge.class));
            }
        });
        mNTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMySoundPool.ClickSound();
                startActivity(new Intent(FirstMainScreen.this, NTS.class));
            }
        });


     //Notification Start
        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 20);
        alarmStartTime.set(Calendar.MINUTE, 30);
        alarmStartTime.set(Calendar.SECOND, 0);
        if (now.after(alarmStartTime)) {
            Log.d("Hey","Added a day");
            alarmStartTime.add(Calendar.DATE, 1);
        }
        Intent intent = new Intent(FirstMainScreen.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(FirstMainScreen.this, 271, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("Alarm","Alarms set for everyday 8 am.");
        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 5);
        Intent intent = new Intent(FirstMainScreen.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(FirstMainScreen.this, 271, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);*/
        //notification end

        getNotificationCount();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        else
            {

        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.wrong_answer_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button yes = dialogView.findViewById(R.id.yes);
        final Button no = dialogView.findViewById(R.id.no);

        final androidx.appcompat.app.AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_change_name:{
                if(mToolbarTitle.equals("Quizler"))
                {
                    Toast.makeText(this, "Check your Connection and try again!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(FirstMainScreen.this,UpdateProfileActivity.class);
                    intent.putExtra("user_name", mToolbarTitle);  // pass your values and retrieve them in the other Activity using keyName
                    startActivity(intent);
                }
                break;
            }

            case R.id.stats:{
                if(mToolbarTitle.equals("Quizler"))
                {
                    Toast.makeText(this, "Check your Connection and try again!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(FirstMainScreen.this, UsersActivity.class);
                    intent.putExtra("user_name", mToolbarTitle);  // pass your values and retrieve them in the other Activity using keyName
                    startActivity(intent);
                }
                break;
            }
            case R.id.followers:{
                if(mToolbarTitle.equals("Quizler"))
                {
                    Toast.makeText(this, "Check your Connection and try again!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(FirstMainScreen.this,FollowerActivity.class);
                    intent.putExtra("user_name", mToolbarTitle);  // pass your values and retrieve them in the other Activity using keyName
                    startActivity(intent);
                }
                break;
            }
            case R.id.leader_board:{
                if(mToolbarTitle.equals("Quizler"))
                {
                    Toast.makeText(this, "Check your Connection and try again!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(FirstMainScreen.this,LeaderBoardScreenActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.action_settings:
            {
                Intent intent = new Intent(FirstMainScreen.this,SettingsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.action_share:
            {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quizler");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey! Check this awesome app and test your skills. Download the app from play store now: https://play.google.com/store/apps/details?id=com.assorttech.myquizler");
                startActivity(Intent.createChooser(sharingIntent, "Quizler Share"));
                break;
            }
            case R.id.action_rate:
            {
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
                break;
            }

            case R.id.action_feedback:
            { Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("plain/text");
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL,  new String[] { "quizler@assorttech.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for Quizler");
                intent.putExtra(Intent.EXTRA_TEXT, "Your Text Here...");
                startActivity(Intent.createChooser(intent, "Send Email"));

                break;}

            case R.id.action_help:
            {
                Intent i = new Intent(FirstMainScreen.this,AboutUsWebView.class);
                startActivity(i);
                break;}

            case R.id.action_about:
            {
                Intent i = new Intent(FirstMainScreen.this,AboutUsWebView.class);
                startActivity(i);
                break;}

            case R.id.privacy:
            {
                Intent i = new Intent(FirstMainScreen.this,PrivacyPolicy.class);
                startActivity(i);
                break;}

            case R.id.action_log_out:
            {
                androidx.appcompat.app.AlertDialog.Builder mLogOutAlrt = new androidx.appcompat.app.AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.wrong_answer_dialog, null);
                mLogOutAlrt.setView(dialogView);
                TextView mText = dialogView.findViewById(R.id.text);
                mText.setText("Are you sure you want to log out?");
                final Button yes = dialogView.findViewById(R.id.yes);
                final Button no = dialogView.findViewById(R.id.no);
                final androidx.appcompat.app.AlertDialog alertDialog = mLogOutAlrt.create();
                alertDialog.show();
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
                        mAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        alertDialog.dismiss();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                break;
            }
        }
        // close drawer when item is tappe
        mDrawerLayout.closeDrawers();
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }
    }

    public void getNotificationCount() {


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem2 = menu.findItem(R.id.notification_action);
        FirebaseDatabase.getInstance().getReference().child("notifications").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    count = dataSnapshot.getChildrenCount();
                    Toast.makeText(FirstMainScreen.this,String.valueOf(count),Toast.LENGTH_SHORT).show();
                    menuItem2.setIcon(Converter.convertLayoutToImage(FirstMainScreen.this,count,R.drawable.ic_notifications_white_24dp));

                }else{
                    menuItem2.setIcon(Converter.convertLayoutToImage(FirstMainScreen.this,0,R.drawable.ic_notifications_white_24dp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent notifyIntent = new Intent(FirstMainScreen.this,NotificationsActivity.class);
                startActivity(notifyIntent);

                return false;
            }
        });
        return true;
    }
}
