package com.example.assesment2.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assesment2.R;

public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView message;
    LinearLayout container;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        message = itemView.findViewById(R.id.message);
        container = itemView.findViewById(R.id.container);
    }

    @Override
    public void onClick(View v) {

    }
}
