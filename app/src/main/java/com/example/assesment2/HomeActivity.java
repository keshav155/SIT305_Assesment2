package com.example.assesment2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.assesment2.Authentication.ChooseLoginOrSignUpActivity;
import com.example.assesment2.Cards.arrayAdapter;
import com.example.assesment2.Cards.cards;
import com.example.assesment2.Matches.MatchesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    // Defining all variables
    cards cards_data[];
    private com.example.assesment2.Cards.arrayAdapter arrayAdapter;
    private int i;
    Button logOutButton;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String currentUId;
    DatabaseReference usersDb;
    ListView listView;
    List<cards> rowItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Defining runtime variables
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        mFirebaseAuth=FirebaseAuth.getInstance();
        currentUId = mFirebaseAuth.getCurrentUser().getUid();
        logOutButton = findViewById(R.id.logout);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent proceedToHomePage = new Intent(HomeActivity.this, ChooseLoginOrSignUpActivity.class);
                startActivity(proceedToHomePage);
                finish();
                return;
            }
        });

        checkUserRole();

        //Setting up adapter for viewing cards
        rowItems = new ArrayList<cards>();

        arrayAdapter = new arrayAdapter(this, R.layout.item,  rowItems );

        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connections").child("nope").child(currentUId).setValue(true);
                Toast.makeText(HomeActivity.this, "Left!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("connections").child("yep").child(currentUId).setValue(true);
                isConnectionMatch(userId);
                Toast.makeText(HomeActivity.this, "Right!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(HomeActivity.this, "Clicked!",Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Function to display match to user if required
    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = usersDb.child(currentUId).child("connections").child("yep").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(HomeActivity.this,"You have a new match!",Toast.LENGTH_LONG).show();
                    String key = FirebaseDatabase.getInstance().getReference().child("Chat").push().getKey();

                    usersDb.child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).child("ChatId").setValue(key);
                    usersDb.child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).child("ChatId").setValue(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //Function to get user's role from database and set the opposite role accordingly
    String userRole;
    String oppositeUserRole;
    private void checkUserRole() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userDb = usersDb.child(user.getUid());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        if(dataSnapshot.child("role").getValue()!=null){
                            userRole = dataSnapshot.child("role").getValue().toString();
                            switch (userRole){
                                case "Job Seeker":
                                    oppositeUserRole = "Employer";
                                    break;
                                case "Employer":
                                    oppositeUserRole = "Job Seeker";
                                    break;
                            }
                            getOppositeRoleUsers();
                        }
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    //Function to get user of opposite role , if you are job seekers, you will be able
    //to see employer cards only and vice versa
    public void getOppositeRoleUsers(){

        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("role").getValue()!=null){
                    if (dataSnapshot.exists() && !(dataSnapshot.child("connections").child("nope").hasChild(currentUId)) && !(dataSnapshot.child("connections").child("yep").hasChild(currentUId))&& dataSnapshot.child("role").getValue().toString().equals(oppositeUserRole)){
                        String profileImageUrl = "default";
                        if (!dataSnapshot.child("profileImageUrl").getValue().equals("default")){
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        cards Item = new cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), profileImageUrl);
                        rowItems.add(Item);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    // functions for transitioning between screens
    public void goToSettings(View view) {
        Intent proceedToSettings = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(proceedToSettings);
        return;
    }

    public void goToMatches(View view) {
        Intent proceedToMatches = new Intent(HomeActivity.this, MatchesActivity.class);
        startActivity(proceedToMatches);
        return;
    }
}
