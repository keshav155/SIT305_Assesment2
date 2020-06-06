package com.example.assesment2.Cards;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.assesment2.Cards.cards;
import com.example.assesment2.R;

import java.util.List;


public class arrayAdapter extends ArrayAdapter<cards>{

    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items){
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        cards card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name =  convertView.findViewById(R.id.userName);
        ImageView image =  convertView.findViewById(R.id.userImage);

        name.setText(card_item.getName());
        switch(card_item.getProfileImageUrl()){
            case "default":
                Glide.with(convertView.getContext()).load("https://i.stack.imgur.com/l60Hf.png").into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(card_item.getProfileImageUrl()).into(image);
                break;
        }


        return convertView;

    }
}