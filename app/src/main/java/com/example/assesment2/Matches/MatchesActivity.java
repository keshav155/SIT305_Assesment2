package com.example.assesment2.Matches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.assesment2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    RecyclerView matchesRecyclerView;
    RecyclerView.Adapter matchesAdapter;
    RecyclerView.LayoutManager matchesLayoutManager;

    String currentUserID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        matchesRecyclerView = findViewById(R.id.recyclerViewOfMatches);
        matchesRecyclerView.setNestedScrollingEnabled(false);
        matchesRecyclerView.setHasFixedSize(true);
        matchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        matchesRecyclerView.setLayoutManager(matchesLayoutManager);
        matchesAdapter = new MatchesAdapter(getDataSetMatches(),MatchesActivity.this);
        matchesRecyclerView.setAdapter(matchesAdapter);

        getUserMatchId();






    }

    private void getUserMatchId() {

        DatabaseReference matchDB = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connections").child("matches");
        matchDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot match: dataSnapshot.getChildren()){
                        fetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchMatchInformation(String key) {
        DatabaseReference userDB = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userID = dataSnapshot.getKey();
                    String name = "";
                    String profileImageUrl = "";
                    if (dataSnapshot.child("name").getValue() != null) {
                        name = dataSnapshot.child("name").getValue().toString();
                    }

                    if (dataSnapshot.child("profileImageUrl").getValue() != null) {
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                    }
                    MatchesObject obj = new MatchesObject(userID,name,profileImageUrl);
                    resultsMatches.add(obj);
                    matchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultsMatches;
    }
}
