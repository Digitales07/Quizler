package com.assorttech.myquizler.Adapter;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.assorttech.myquizler.LeaderBoardScreenActivity;
import com.assorttech.myquizler.Profile.ProfileActivity;
import com.assorttech.myquizler.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import morxander.zaman.ZamanTextView;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> userNameList;
    ArrayList<String> profilePicList;
    ArrayList<String> uId;
    ProgressDialog asyncDialog;

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_single_layout, parent, false);

        return new SearchAdapter.SearchViewHolder(v);
    }


        public class SearchViewHolder extends RecyclerView.ViewHolder {

            public CircleImageView profileImage;
            public TextView displayName;
            public LinearLayout mLayout;
            public ProgressBar bar;


            public SearchViewHolder(View view) {
                super(view);


                profileImage = view.findViewById(R.id.user_single_image);
                displayName =  view.findViewById(R.id.user_single_name);
                bar = view.findViewById(R.id.bar1);
                mLayout = view.findViewById(R.id.mRoot);
            }
    }

    public SearchAdapter(Context context, ArrayList<String> userNameList, ArrayList<String> profilePicList,ArrayList<String> uId) {
        this.context = context;
        this.userNameList = userNameList;
        this.profilePicList = profilePicList;
        this.uId = uId;
    }


    @Override
    public void onBindViewHolder(final SearchViewHolder holder, final int position) {
        holder.displayName.setText(userNameList.get(position));
        asyncDialog = new ProgressDialog(context);
        Picasso.get().load(profilePicList.get(position)).placeholder(R.drawable.default_pic).into(holder.profileImage);


        holder.displayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bar.setVisibility(View.VISIBLE);
                Intent profileIntent = new Intent(context, ProfileActivity.class);
                profileIntent.putExtra("user_id", uId.get(position));
                context.startActivity(profileIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userNameList.size();
    }

}