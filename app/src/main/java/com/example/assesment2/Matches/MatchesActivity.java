package com.example.assesment2.Matches;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.assesment2.R;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    RecyclerView matchesRecyclerView;
    RecyclerView.Adapter matchesAdapter;
    RecyclerView.LayoutManager matchesLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        matchesRecyclerView = findViewById(R.id.recyclerViewOfMatches);
        matchesRecyclerView.setNestedScrollingEnabled(false);
        matchesRecyclerView.setHasFixedSize(true);
        matchesLayoutManager = new LinearLayoutManager(MatchesActivity.this);
        matchesRecyclerView.setLayoutManager(matchesLayoutManager);
        matchesAdapter = new MatchesAdapter(getDataSetMatches(),MatchesActivity.this);
        matchesRecyclerView.setAdapter(matchesAdapter);

        for(int i = 0; i<100 ; i++){
            MatchesObject obj = new MatchesObject(Integer.toString(i));
            resultsMatches.add(obj);
        }


        matchesAdapter.notifyDataSetChanged();

    }
    ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultsMatches;
    }
}
