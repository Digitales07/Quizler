package com.assorttech.myquizler;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.assorttech.myquizler.Model.Images;
import com.assorttech.myquizler.Model.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    private EditText mEditTextDisplayName;
    private EditText mEditTextUserName;
    private Button mButtonRegisterUser;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    String user_gender = "Male";
    String user_phone_no="";
    String randomMaleImage, randomGirlsImage;
    String user_image;

    // TODO: cool facts bank
    private Images[] malesImages = new Images[]{
            new Images("https://firebasestorage.googleapis.com/v0/b/my-quizler.appspot.com/o/boy_two.png?alt=media&token=ea64248a-3731-4b04-8947-f6b6fb6301da"),
            new Images("https://firebasestorage.googleapis.com/v0/b/my-quizler.appspot.com/o/Users%2Fprofile_pictures%2Fboy_one.png?alt=media&token=c7c6272b-e6be-4b6e-acec-0e4a23857c1b")
    };

    private Images[] girlsImages = new Images[]{

            new Images("https://firebasestorage.googleapis.com/v0/b/my-quizler.appspot.com/o/Users%2Fprofile_pictures%2Fg_i_one.png?alt=media&token=a90a2848-cf6d-4260-b15c-3f59293d7f71"),
            new Images("https://firebasestorage.googleapis.com/v0/b/my-quizler.appspot.com/o/Users%2Fprofile_pictures%2Fg_i_two.png?alt=media&token=6989cf1a-1200-4bca-b50d-c470f0a63b28"),
            new Images("https://firebasestorage.googleapis.com/v0/b/my-quizler.appspot.com/o/g_i_three.png?alt=media&token=636fcc06-5776-459e-ada8-ea2418d8ed64")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

// Random function male
        Random random_one = new Random();

        int maxIndex = malesImages.length;
        int generatedIndex = random_one.nextInt(maxIndex);
        randomMaleImage =malesImages[generatedIndex].getImageUrl();

        Random random_two = new Random();

        int maxIndextwo = girlsImages.length;
        int generatedIndextwo = random_one.nextInt(maxIndextwo);
        randomGirlsImage =girlsImages[generatedIndextwo].getImageUrl();

        user_phone_no = getIntent().getStringExtra("number");
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGender);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioMale:
                        user_gender = "Male";
                        break;
                    case R.id.radioFemale:
                        user_gender = "Female";
                        break;

                }
            }
        });
        mProgressDialog = new ProgressDialog(this);
        mEditTextDisplayName = findViewById(R.id.etFullNameUser);
        mEditTextUserName = findViewById(R.id.etUserName);
        mButtonRegisterUser = findViewById(R.id.btn_register_user);
        mAuth = FirebaseAuth.getInstance();
        mButtonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFields();
            }
        });
    }

    private void checkFields()
    {
        String userName =  mEditTextUserName.getText().toString().trim().toLowerCase();
        String name= mEditTextDisplayName.getText().toString();
        if(userName.length() <5)
        {
            mEditTextUserName.setError("User name must be 5 characters at least.");
            mEditTextUserName.requestFocus();
            return;
        }
        if(name.length() <4)
        {
            mEditTextDisplayName.setError("Please Enter Full Name.");
            mEditTextDisplayName.requestFocus();
            return;
        }
        else{
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.show();
            checkUniqueName();}
    }

    private void checkUniqueName() {
        String userName =  mEditTextUserName.getText().toString().trim().toLowerCase();
        FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("user_name").equalTo(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mProgressDialog.dismiss();
                    mEditTextUserName.setError("User name already exists please try a new one.");
                    mEditTextUserName.requestFocus();
                }
                else {
                    registerUser();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registerUser() {
        String user_city,user_favourite_subject,user_nick_name;
        user_city="";
        user_favourite_subject="";
        user_nick_name="";

        if (user_gender=="Male")
        {
            user_image=randomMaleImage;
        }
        else if (user_gender == "Female")
        {
            user_image=randomGirlsImage;
        }

//        String defaultProfilePic = "https://firebasestorage.googleapis.com/v0/b/my-quizler.appspot.com/o/Users%2Fprofile_pictures%2Fdefault_pic.jpg?alt=media&token=8dda3934-8260-4e39-8696-8ec5f107094d";
        String uid= mAuth.getCurrentUser().getUid();
        final String user_name = mEditTextUserName.getText().toString().trim().toLowerCase();
        final String  user_display_name = mEditTextDisplayName.getText().toString().trim();
        final UserDataModel mUser = new UserDataModel(user_display_name,user_name,user_phone_no,user_gender,user_image,user_city,user_favourite_subject,user_nick_name);
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mProgressDialog.dismiss();
                    startActivity(new Intent(SignUpActivity.this,FirstMainScreen.class));
                    finish();
                }
                else {
                    mProgressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Something went wrong!"+mUser, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
