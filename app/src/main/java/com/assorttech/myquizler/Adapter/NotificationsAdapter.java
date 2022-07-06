package com.assorttech.myquizler.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.assorttech.myquizler.Model.Noti;
import com.assorttech.myquizler.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import morxander.zaman.ZamanTextView;


public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>{


    private List<Noti> mNotiList;
    private DatabaseReference mUserDatabase;

    public NotificationsAdapter(List<Noti> mNotiList) {

        this.mNotiList = mNotiList;

    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifcation_list ,parent, false);

        return new NotificationViewHolder(v);

    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView profileImage;
        public TextView displayName;

        public NotificationViewHolder(View view) {
            super(view);

            profileImage = view.findViewById(R.id.user_single_image);
            displayName =  view.findViewById(R.id.user_single_name);
        }
    }

    @Override
    public void onBindViewHolder(final NotificationViewHolder viewHolder, int i) {

        Noti c = mNotiList.get(i);

        String from_user = c.getFrom();


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("user_name").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();
                viewHolder.displayName.setText(name);
                Picasso.get().load(image).placeholder(R.drawable.default_pic).into(viewHolder.profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return mNotiList.size();
    }
}
