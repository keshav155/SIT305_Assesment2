package com.example.assesment2.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assesment2.R;

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView matchID,matchName;
    ImageView matchImage;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
