package com.assorttech.myquizler.Adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.assorttech.myquizler.Model.Messages;
import com.assorttech.myquizler.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import morxander.zaman.ZamanTextView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messeages ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public ZamanTextView timmer;
        public RelativeLayout mLayout;
        public LinearLayout mLinearLayout;

        public MessageViewHolder(View view) {
            super(view);

            messageText =  view.findViewById(R.id.messageTextBody);
            timmer = view.findViewById(R.id.ztvTime1);
            mLayout = view.findViewById(R.id.mLayout);
            mLinearLayout = view.findViewById(R.id.mLinearLayout);
        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);

        final String from_user = c.getFrom();
        String message_type = c.getType();


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("user_name").getValue().toString();
                String image = dataSnapshot.child("user_image").getValue().toString();

                if (from_user.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    viewHolder.mLayout.setGravity(Gravity.END);
                    viewHolder.mLinearLayout.setBackgroundResource(R.drawable.my_message);
                    viewHolder.messageText.setTextColor(Color.WHITE);
                    viewHolder.timmer.setTextColor(Color.WHITE);

                }else{
                    viewHolder.mLayout.setGravity(Gravity.START);
                    viewHolder.mLinearLayout.setBackgroundResource(R.drawable.their_message);
                    viewHolder.messageText.setTextColor(Color.BLACK);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(message_type.equals("text")) {

            Messages messages = new Messages() ;
            viewHolder.messageText.setText(c.getMessage());
            Long time1 =c.getTime();
            time1 = time1/1000; //time format conversion
            viewHolder.timmer.setTimeStamp(time1);


        } else {
            viewHolder.messageText.setVisibility(View.INVISIBLE);
//            Picasso.get().load(c.getMessage())
//                    .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
