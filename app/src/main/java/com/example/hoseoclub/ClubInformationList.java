package com.example.hoseoclub;

public class ClubInformationList {

    private String clubIntro;
    private String clubImage;


    private String clubName;


    ClubInformationList(String clubName, String clubImage, String clubIntro) {
        this.clubName = clubName;
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

}
