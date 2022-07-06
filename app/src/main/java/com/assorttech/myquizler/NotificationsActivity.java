package com.assorttech.myquizler;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assorttech.myquizler.Model.Noti;
import com.assorttech.myquizler.Profile.ProfileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView mNotiList;
    private DatabaseReference mNotiDatabase;
    private DatabaseReference mUsersDatabase;
    private FirebaseRecyclerAdapter adapter;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        mNotiList =  findViewById(R.id.noti_list);
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mNotiDatabase = FirebaseDatabase.getInstance().getReference().child("notifications").child(mCurrent_user_id);

        mNotiDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mNotiList.setHasFixedSize(true);
        mNotiList.setLayoutManager(new LinearLayoutManager(this));
        mNotiList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        fetch();

    }

    private void fetch() {
        Query query = mNotiDatabase;
        FirebaseRecyclerOptions<Noti> options =
                new FirebaseRecyclerOptions.Builder<Noti>()
                        .setQuery(query, new SnapshotParser<Noti>() {
                            @NonNull
                            @Override
                            public Noti parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Noti(snapshot.child("from").getValue().toString(),snapshot.child("type").getValue().toString());

                            }
                        })
                        .build();
        adapter = new FirebaseRecyclerAdapter<Noti, NotificationsActivity.ViewHolder>(options) {


            @Override
            public NotificationsActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.notifcation_list, parent, false);

                return new NotificationsActivity.ViewHolder(view) ;
            }

            @Override
            protected void onBindViewHolder(final NotificationsActivity.ViewHolder holder, final int position, final Noti model) {
                final String list_user_id = model.getFrom();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        final String userName = dataSnapshot.child("user_display_name").getValue().toString();
                        String userThumb = dataSnapshot.child("user_image").getValue().toString();;



                        holder.setName(userName);
                        holder.setUserImage(userThumb);
                        holder.setStatus(model.getType());

                        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent profileIntent = new Intent(NotificationsActivity.this, ProfileActivity.class);
                                profileIntent.putExtra("user_id", list_user_id);
                                startActivity(profileIntent);
                            }
                        });
                    }
                    //
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };


        mNotiList.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.mRoot);




        }

        public void setStatus(String status){


            TextView userStatusView = itemView.findViewById(R.id.user_single_status);
            if(status.equals("request")) {
                userStatusView.setText("Sending you request");
            }
            else {
                userStatusView.setText("Sending you a message");
            }

        }
        public void setName(String name){

            TextView userNameView = itemView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setUserImage(String thumb_image){

            CircleImageView userImageView = itemView.findViewById(R.id.user_single_image);
            Picasso.get().load(thumb_image).placeholder(R.drawable.default_pic).into(userImageView);

        }
    }
}