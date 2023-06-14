package com.example.hoseoclub;

public class ClubInformationList {

    private String clubIntro;
    private String clubImage;

    private String clubType;

    private String clubName;


    ClubInformationList(String clubName, String clubType, String clubImage, String clubIntro) {
        this.clubName = clubName;
        this.clubType = clubType;
        this.clubIntro = clubIntro;
        this.clubImage = clubImage;
    }

    public String getClubIntro() {
        return clubIntro;
    }

    public String getClubImage() {
        return clubImage;
    }

    public String getClubName() {
        return clubName;
    }

    public String getClubType() { return clubType; }
}
