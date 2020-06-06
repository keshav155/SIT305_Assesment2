package com.example.assesment2.Matches;

public class MatchesObject {
    private String userId;
    private String name;
    private String profileImageUrl = "default";
    public MatchesObject (String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userID){
        this.userId = userID;
    }

}
