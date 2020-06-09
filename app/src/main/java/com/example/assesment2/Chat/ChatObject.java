package com.example.assesment2.Chat;
//Object class for a chat message
public class ChatObject {
    private String message;
    private Boolean currentUser;
    //Default constructor
    public ChatObject(String message,Boolean currentUser){
        this.message = message;
        this.currentUser = currentUser;
    }

    //Get and set methods
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public Boolean getCurrentUser(){
        return currentUser;
    }
    public void setCurrentUser(Boolean currentUser){
        this.currentUser = currentUser;
    }
}
