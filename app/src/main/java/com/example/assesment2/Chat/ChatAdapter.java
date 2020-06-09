package com.example.assesment2.Chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assesment2.R;

import java.util.List;
//Adapter class for chat messages recyclerview
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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolder rcv = new ChatViewHolder((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.message.setText(chatObjectList.get(position).getMessage());
        if(chatObjectList.get(position).getCurrentUser()){
            holder.message.setGravity(Gravity.END);
            holder.message.setTextColor(Color.parseColor("#404040"));
            holder.container.setBackgroundColor(Color.parseColor("#F4F4F4"));
        }
        else
        {
            holder.message.setGravity(Gravity.START);
            holder.message.setTextColor(Color.parseColor("#FFFFFF"));
            holder.container.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }
    }

    @Override
    public int getItemCount() {
        return chatObjectList.size();
    }
}
