package com.example.assesment2.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.assesment2.Chat.ChatAdapter;
import com.example.assesment2.Chat.ChatObject;
import com.example.assesment2.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    RecyclerView chatRecyclerView;
    RecyclerView.Adapter chatAdapter;
    RecyclerView.LayoutManager chatLayoutManager;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        chatRecyclerView = findViewById(R.id.recyclerViewOfMatches);
        chatRecyclerView.setNestedScrollingEnabled(false);
        chatRecyclerView.setHasFixedSize(true);
        chatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);
        chatAdapter = new ChatAdapter(getDataSetChat(),ChatActivity.this);
        chatRecyclerView.setAdapter(chatAdapter);
    }
    ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>();
    private List<ChatObject> getDataSetChat() {
        return resultsChat;
    }
}
