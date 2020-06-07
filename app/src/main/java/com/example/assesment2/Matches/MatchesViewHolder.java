package com.example.assesment2.Matches;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assesment2.R;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView matchID,matchName;
    ImageView matchImage;
    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        matchID = itemView.findViewById(R.id.matchID);
        matchName = itemView.findViewById(R.id.matchName);
        matchImage = itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View v) {

    }
}
