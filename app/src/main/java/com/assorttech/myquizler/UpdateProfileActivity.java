package com.assorttech.myquizler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assorttech.myquizler.Model.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText mEditText;
    private EditText mEditTextCity;
    private EditText mEditTextFavSubject;
    private EditText mEditTextNickName;
    private Button mButton;
    FirebaseAuth mAuth;
    DatabaseReference mUserRef;
    CircleImageView mImageViewProfilePic;
    private ProgressDialog mProgressDialog;
    Bitmap thumb_bitmap = null;
    StorageReference userImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        setContentView(R.layout.update_profile_activity);

        String data = getIntent().getExtras().getString("user_name", " ");
        mEditText = findViewById(R.id.etFullNewNameUser);
        mEditTextCity = findViewById(R.id.etUserCity);
        mEditTextFavSubject = findViewById(R.id.etFavSubject);
        mEditTextNickName = findViewById(R.id.etNickName);
        mButton = findViewById(R.id.btn_UpdateUserName);
        mImageViewProfilePic = findViewById(R.id.user_image_civ_update);
        mEditText.setText(data);
        mProgressDialog = new ProgressDialog(this);
        userImageRef = FirebaseStorage.getInstance().getReference().child("Users").child("profile_pictures");
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    UserDataModel user = dataSnapshot.getValue(UserDataModel.class);
                    Picasso.get().load(user.getUser_image()).into(mImageViewProfilePic);
                    mEditTextCity.setText(user.getUser_city());
                    mEditTextFavSubject.setText(user.getUser_favourite_subject());
                    mEditTextNickName.setText(user.getUser_nick_name());
                    mProgressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mImageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(UpdateProfileActivity.this);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEditText.getText().toString().trim();
                String city = mEditTextCity.getText().toString().trim();
                String favSubject = mEditTextFavSubject.getText().toString().trim();
                String nickName = mEditTextNickName.getText().toString().trim();
                if (name.length() < 3) {
                    mEditText.setError("Please enter a valid name");
                    mEditText.requestFocus();
                    return;
                }
                if (city.length() < 3) {
                    mEditTextCity.setError("Please enter a valid city name.");
                    mEditTextCity.requestFocus();
                    return;
                }
                if (favSubject.length() < 3) {
                    mEditTextFavSubject.setError("Please enter a valid subject.");
                    mEditTextFavSubject.requestFocus();
                    return;
                }
                if (nickName.length() < 3) {
                    mEditTextNickName.setError("Please enter a valid nick name.");
                    mEditTextNickName.requestFocus();
                }else {
                    Toast.makeText(UpdateProfileActivity.this, "Updating Profile...", Toast.LENGTH_SHORT).show();
                    Map update_user_data= new HashMap();
                    update_user_data.put("user_display_name",name);
                    update_user_data.put("user_favourite_subject",favSubject);
                    update_user_data.put("user_city",city);
                    update_user_data.put("user_nick_name",nickName);
                    mUserRef.child(mAuth.getCurrentUser().getUid()).updateChildren(update_user_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdateProfileActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mProgressDialog.setTitle("Updating Profile Image");
                mProgressDialog.setMessage("Please Wait While Profile is Updated");
                mProgressDialog.show();

                Uri resultUri = result.getUri();
                //compression
                File thumb_file_path_uri = new File(resultUri.getPath());

                final String user_id = mAuth.getCurrentUser().getUid();
                try {
                    thumb_bitmap = new id.zelory.compressor.Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(50)
                            .compressToBitmap(thumb_file_path_uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();


                //end compression

                final StorageReference thumb_filePath = userImageRef.child(user_id + ".jpg");

                UploadTask mUploadTask = thumb_filePath.putBytes(thumb_byte);
                mUploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {
                        mProgressDialog.dismiss();
                        userImageRef.child(user_id + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                final String thumbImageURL = uri.toString();
                                FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("user_image").setValue(thumbImageURL);
                               final DatabaseReference mRef  =  FirebaseDatabase.getInstance().getReference().child("stat").child(user_id);
                                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            mRef.child("jobProfile").setValue(thumbImageURL);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                Toast.makeText(getApplicationContext(), "Profile Picture Updated Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                mProgressDialog.dismiss();
                                Toast.makeText(UpdateProfileActivity.this, "Profile Picture Update Failed. Please Try Again!", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

            }
        }
    }
