package com.example.hoseoclub;

public class ClubInformationList {

    private String clubName;
    private String clubImage;

    ClubInformationList(String clubName, String clubImage) {
        this.clubName = clubName;
        this.clubImage = clubImage;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubImage() {
        return clubImage;
    }
}
