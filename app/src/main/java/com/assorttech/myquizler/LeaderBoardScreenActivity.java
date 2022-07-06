package com.assorttech.myquizler;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assorttech.myquizler.Model.StatModel;
import com.assorttech.myquizler.Profile.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardScreenActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private String BID;
    private ProgressDialog mProgressDialog;
    private LinearLayout mShareResult;
    private ImageView mImageView;
    RelativeLayout mButtonMyRankdown, mButtonMyRankUp;


    TextView arrowUp,arrowDown;

    boolean currentUserPosition=false;

    int currentUserP=0;

    int finalCurrentUserPositon=-1;

    int firstViewPosition=0;
    int lastViewPosition=0;

    private String mName, mPosition, mXp, mScore;
    int checkExistenceXp;

    private DatabaseReference mDatabaseReference;

    String currUserId;

    List<StatModel> statModels;

    StatModel statModelForSingle;

    MyRecyclerViewAdapter adapter;
    LinearLayoutManager mLinearManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board_activity);

        statModelForSingle = new StatModel();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("please wait!");
        mProgressDialog.show();

        mButtonMyRankdown = findViewById(R.id.tv_rank_down);
        mButtonMyRankUp = findViewById(R.id.tv_rank_up);

        arrowDown = findViewById(R.id.arrow_down);
        arrowUp = findViewById(R.id.arrow_up);

        mShareResult = findViewById(R.id.share_result);
        //toolbar
        Toolbar toolBar = findViewById(R.id.toolBarAllStats);

        mRecyclerView = findViewById(R.id.all_Stats_list);

        toolBar.setTitle("Leader Board");
        setSupportActionBar(toolBar);


        mAuth = FirebaseAuth.getInstance();

        currUserId = mAuth.getCurrentUser().getUid();

//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setAdapter(new AdapterLeaderBoard(this));

        readData(new MyCallback() {
            @Override
            public void onCallback(boolean value) {

                mLinearManager = new LinearLayoutManager(LeaderBoardScreenActivity.this);
                mRecyclerView.setLayoutManager(mLinearManager);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));
                adapter = new MyRecyclerViewAdapter(getApplicationContext(), statModels);

                mRecyclerView.setAdapter(adapter);

                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        Log.d("myTAG", "m"+mLinearManager.findFirstCompletelyVisibleItemPosition());
                        Log.d("myTAG", "m"+mLinearManager.findLastCompletelyVisibleItemPosition());
                        firstViewPosition = mLinearManager.findFirstCompletelyVisibleItemPosition();
                        lastViewPosition = mLinearManager.findLastCompletelyVisibleItemPosition();

                        if (finalCurrentUserPositon>lastViewPosition)
                        {
                            mButtonMyRankdown.setVisibility(View.VISIBLE);
                            final int finalCurrentUserPositonTwo = finalCurrentUserPositon+1;
                            arrowDown.setText("you are at position: "+finalCurrentUserPositonTwo);
                            mButtonMyRankdown.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (finalCurrentUserPositon>4&&(finalCurrentUserPositon+4)<statModels.size())
                                    {
                                        //int finalCurrentPosition=firstViewPosition+3;
                                        mRecyclerView.scrollToPosition(finalCurrentUserPositon+4);
                                    }
                                    else {
                                        mRecyclerView.scrollToPosition(finalCurrentUserPositon);
                                    }
                                }
                            });
                        }
                        else if (finalCurrentUserPositon<lastViewPosition&&finalCurrentUserPositon>firstViewPosition)
                        {
                            mButtonMyRankdown.setVisibility(View.GONE);
                            mButtonMyRankUp.setVisibility(View.GONE);
                            int finalCurrentUserPositonTwo = finalCurrentUserPositon+1;
                            arrowUp.setText("you are at position: "+finalCurrentUserPositonTwo);
                        }
                        else if (finalCurrentUserPositon<firstViewPosition&&finalCurrentUserPositon>-1)
                        {
                            mButtonMyRankUp.setVisibility(View.VISIBLE);
                            int finalCurrentUserPositonTwo = finalCurrentUserPositon+1;
                            arrowUp.setText("you are at position: "+finalCurrentUserPositonTwo);
                            mButtonMyRankUp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (finalCurrentUserPositon>4)
                                    {
                                        mRecyclerView.scrollToPosition(finalCurrentUserPositon-3);

                                    }
                                    else {
                                        mRecyclerView.scrollToPosition(finalCurrentUserPositon);
                                    }
                                }
                            });
                        }
                        else if (finalCurrentUserPositon==-1)
                        {
                            Toast.makeText(LeaderBoardScreenActivity.this, "you are not exist here yet! please take a quiz for show in leader board", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mButtonMyRankUp.setVisibility(View.GONE);
                            mButtonMyRankdown.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public boolean isInternetWorking() {
        boolean success = false;
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            success = connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }


    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }


    public interface MyCallback {
        void onCallback(boolean value);
    }


    public void readData(final MyCallback myCallback) {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("stat");

        Query query = mDatabaseReference.orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    statModels = new ArrayList<>();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        StatModel mStatModel = new StatModel();

                        mStatModel.setTotal_ques(ds.getValue(StatModel.class).getTotal_ques());
                        mStatModel.setSilver(ds.getValue(StatModel.class).getSilver());
                        mStatModel.setGold(ds.getValue(StatModel.class).getGold());
                        mStatModel.setPlatinum(ds.getValue(StatModel.class).getPlatinum());
                        mStatModel.setXp(ds.getValue(StatModel.class).getXp());
                        mStatModel.setPersonName(ds.getValue(StatModel.class).getPersonName());
                        mStatModel.setJobProfile(ds.getValue(StatModel.class).getJobProfile());
                        mStatModel.setScore(ds.getValue(StatModel.class).getScore());
                        mStatModel.setTotalQuestionsAttempted(ds.getValue(StatModel.class).getTotalQuestionsAttempted());
                        mStatModel.setUser_id(ds.getValue(StatModel.class).getUser_id());
                        statModels.add(mStatModel);


                        Collections.sort(statModels, new FooComparator());

                        for (int i = 0; i<statModels.size();i++)
                        {
                            if (statModels.get(i).getUser_id().equals(currUserId))
                            {
                                finalCurrentUserPositon=i;
                                break;

                            }
                        }
                    }
                }
                myCallback.onCallback(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Adapter

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<StatModel> mData;
        private LayoutInflater mInflater;

        StatModel statModelForSingleObject;


        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<StatModel> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.all_stats_view_holder, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);

            Log.d("myTAG", "m"+mLinearManager.findFirstCompletelyVisibleItemPosition());
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            mProgressDialog.dismiss();
            int userScore = mData.get(position).getScore();
            String userProfile = mData.get(position).getJobProfile();
            String userName = mData.get(position).getPersonName();
            int userXp= mData.get(position).getXp();

            holder.myTextView.setText(Integer.toString(position+1));
            Picasso.get().load(userProfile).into( holder.userProfileImage);
            holder.userProfileName.setText(userName);
            String userrXp=Integer.toString(userXp);
            holder.userXp.setText(userrXp+"xp");
            holder.statScore.setText(Integer.toString(userScore));

            if (finalCurrentUserPositon==position)
            {
                holder.setCardColor(true);

                mShareResult.setVisibility(View.VISIBLE);

                mScore=String.valueOf(statModels.get(position).getScore());
                mName=statModels.get(position).getPersonName();
                mXp=Integer.toString(statModels.get(position).getXp());
                mPosition=Integer.toString(position+1);


                mShareResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //share result code

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Quizzler");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey! I am at Number "+ mPosition +" worldwide in Quizler leader board. My score is "+ mScore +
                                " with "+ mXp +" Xp's. beat me if you can."+
                                " Download the app from play store now: https://play.google.com/store/apps/details?id=com.assorttech.myquizler");
                        startActivity(Intent.createChooser(sharingIntent, "Quizzler Report Share"));
                    }
                });
            }

            else {

                holder.setCardColorWhite(true);

            }

            // set leaders trophies

            if (position ==0)
            {

                holder.setImage(R.drawable.leader_first);
            }
            if (position ==1)
            {

                holder.setImage(R.drawable.leader_second);
            }
            if (position ==2)
            {

                holder.setImage(R.drawable.leader_third);

            }
            if (position>2)
            {
                holder.setImage(0);
            }

        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder{

            View mView;

            TextView myTextView;
            CircleImageView userProfileImage;
            TextView userProfileName;
            TextView userXp,statScore;

            ViewHolder(View itemView) {
                super(itemView);
                mView=itemView;

                myTextView = itemView.findViewById(R.id.count);
                userProfileImage = itemView.findViewById(R.id.stat_profile_image);
                userProfileName = itemView.findViewById(R.id.stat_profile_name);
                userXp = itemView.findViewById(R.id.xp);
                statScore = itemView.findViewById(R.id.stat_score);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StatModel userProfileObject = statModels.get(getAdapterPosition());
                        //To pass:
                        Intent profileIntent = new Intent(LeaderBoardScreenActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user_id", userProfileObject.getUser_id());
                        startActivity(profileIntent);

//                        Toast.makeText(LeaderBoardScreenActivity.this, ""+userProfileObject.getScore(), Toast.LENGTH_SHORT).show();
//
//                        String userProfile = statModels.get(getAdapterPosition()).getJobProfile();
//                        String name = statModels.get(getAdapterPosition()).getPersonName();
//                        int userXp = statModels.get(getAdapterPosition()).getXp();
//                        int totalScore = statModels.get(getAdapterPosition()).getScore();
//                        int totalQuizez = statModels.get(getAdapterPosition()).getTotal_ques();
//                        int totalGold = statModels.get(getAdapterPosition()).getGold();
//                        int totalSliver = statModels.get(getAdapterPosition()).getSilver();

                    }
                });
            }

            public void setCardBorder(Boolean choice) {
                LinearLayout mLayout =  mView.findViewById(R.id.card_linear_layout);
                if(choice){
                    mLayout.setBackgroundResource(R.color.card_back);
                }
                else
                {
                    mLayout.setBackgroundResource(0);
                }
            }

            public void setCardColor(Boolean choice) {
                LinearLayout mLayout =  mView.findViewById(R.id.card_linear_layout);
                if(choice){
                    mLayout.setBackgroundResource(R.color.green);
                }
                else
                {
                    mLayout.setBackgroundResource(0);
                }
            }

            public void setCardColorWhite(Boolean choice) {
                LinearLayout mLayout =  mView.findViewById(R.id.card_linear_layout);
                if(choice){
                    mLayout.setBackgroundResource(R.color.white);
                }
                else
                {
                    mLayout.setBackgroundResource(0);
                }
            }

            public void setImage(int image) {
                ImageView imageView =  mView.findViewById(R.id.image);
                imageView.setImageResource(image);
            }
        }
    }

    public class FooComparator implements Comparator<StatModel> {
        public int compare(StatModel a, StatModel b) {
            int dateComparison = Integer.valueOf( b.getXp()).compareTo(a.getXp());

            //  Integer.valueOf(a.getXp()).compareTo(Integer.valueOf(b.getXp()));
            return dateComparison == 0 ? Integer.valueOf(b.getScore()).compareTo(a.getScore()) : dateComparison;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}















