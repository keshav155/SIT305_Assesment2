package com.example.assesment2.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.assesment2.Chat.ChatAdapter;
import com.example.assesment2.Chat.ChatObject;
import com.example.assesment2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    //defining variables
    RecyclerView chatRecyclerView;
    RecyclerView.Adapter chatAdapter;
    RecyclerView.LayoutManager chatLayoutManager;
    String currentUserID,matchID,chatID;
    EditText sendEditText;
    Button sendButton;
    DatabaseReference databaseUser, databaseChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //setting up runtime variables
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        matchID = getIntent().getExtras().getString("matchID");
        databaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("connections").child("matches").child(matchID).child("ChatId");
        databaseChat = FirebaseDatabase.getInstance().getReference().child("Chat");

        getChatId();

        chatRecyclerView = findViewById(R.id.recyclerViewOfChat);
        chatRecyclerView.setNestedScrollingEnabled(false);
        chatRecyclerView.setHasFixedSize(false);
        chatLayoutManager = new LinearLayoutManager(ChatActivity.this);
        chatRecyclerView.setLayoutManager(chatLayoutManager);
        chatAdapter = new ChatAdapter(getDataSetChat(),ChatActivity.this);
        chatRecyclerView.setAdapter(chatAdapter);

        sendEditText = findViewById(R.id.message);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }
    //method to send message
    private void sendMessage() {
        String sendMessageText = sendEditText.getText().toString();

        if(!sendMessageText.isEmpty()){
            DatabaseReference newMessageDB = databaseChat.push();

            Map newMessage = new HashMap();
            newMessage.put("createdByUser",currentUserID);
            newMessage.put("text",sendMessageText);

            newMessageDB.setValue(newMessage);
        }
        sendEditText.setText(null);
    }
    //method to get chatID from firebase database
    private void getChatId(){
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    chatID = dataSnapshot.getValue().toString();
                    databaseChat = databaseChat.child(chatID);
                    getChatMessage();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    //method to get chat messages from opposite user from firebase database
    private void getChatMessage() {
        databaseChat.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if(dataSnapshot.exists()){
                        String message = null;
                        String createdByUser = null;

                        if(dataSnapshot.child("text").getValue()!=null){
                            message = dataSnapshot.child("text").getValue().toString();
                        }
                        if(dataSnapshot.child("createdByUser").getValue()!=null){
                            createdByUser = dataSnapshot.child("createdByUser").getValue().toString();
                        }

                        if(message!=null && createdByUser!=null){
                            Boolean currentUserBoolean = false;
                            if(createdByUser.equals(currentUserID)){
                                currentUserBoolean = true;
                            }
                            ChatObject newMessage = new ChatObject(message,currentUserBoolean);
                            resultsChat.add(newMessage);
                            chatAdapter.notifyDataSetChanged();
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


    ArrayList<ChatObject> resultsChat = new ArrayList<ChatObject>();
    private List<ChatObject> getDataSetChat() {
        return resultsChat;
    }
}
