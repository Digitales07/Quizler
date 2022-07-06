package com.assorttech.myquizler;

import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assorttech.myquizler.Adapter.SearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    SearchView searchView;;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> userNameList;
    ArrayList<String> profilePicList;
    SearchAdapter searchAdapter;
    private Toolbar mUserToolbar;
    ArrayList<String> uId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);

        mUserToolbar = findViewById(R.id.users_appBar);
        mUserToolbar.setTitle("FIND USER");
        mUserToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mUserToolbar);


        searchView = findViewById(R.id.searchView);
        recyclerView =  findViewById(R.id.users_list);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
         * Create a array list for each node you want to use
         * */
        userNameList = new ArrayList<>();
        profilePicList = new ArrayList<>();
        uId = new ArrayList<>();

        searchView.setQueryHint("Search by Username");

        setAdapter("a");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.toString().isEmpty()) {
                    setAdapter(query.toString());
                } else {

                    setAdapter("a");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.toString().isEmpty()) {
                    setAdapter(newText.toString());
                } else {

                    setAdapter("a");
                }
                return false;
            }
        });



    }

    private void setAdapter(final String searchedString) {
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                 * Clear the list for every new search
                 * */
                userNameList.clear();
                profilePicList.clear();
                uId.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                /*
                 * Search all users for matching searched string
                 * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String user_name = snapshot.child("user_name").getValue(String.class);
                    String profile_pic = snapshot.child("user_image").getValue(String.class);

                    if (user_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        userNameList.add(user_name);
                        profilePicList.add(profile_pic);
                        uId.add(uid);
                        counter++;
                    } else if (user_name.toLowerCase().contains(searchedString.toLowerCase())) {
                        userNameList.add(user_name);
                        profilePicList.add(profile_pic);
                        uId.add(uid);
                        counter++;
                    }

                    /*
                     * Get maximum of 15 searched results only
                     * */
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(getApplicationContext(), userNameList, profilePicList,uId);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}