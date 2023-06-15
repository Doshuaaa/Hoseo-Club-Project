package com.example.hoseoclub;

public class LikedClub {


    private String likedClubType;
    private String likedClubName;

    public LikedClub(String likedClubType, String likedClubName) {
        this.likedClubType = likedClubType;
        this.likedClubName = likedClubName;
    }

    public String getLikedClubType() {
        return likedClubType;
    }

    public String getLikedClubName() {
        return likedClubName;
    }


}
