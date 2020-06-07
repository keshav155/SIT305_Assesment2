package com.example.assesment2.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assesment2.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    List <ChatObject> chatObjectList;
    Context context;


    public ChatAdapter(List<ChatObject> matchesObjectList, Context context){
        this.chatObjectList = matchesObjectList;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matches,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolder rcv = new ChatViewHolder((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return chatObjectList.size();
    }
}
