package com.example.hoseoclub;

public class UniversityList {

    private String name;
    private String color;
    private String image;

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getImage() {
        return image;
    }


    UniversityList (String name, String image, String color) {
        this.name = name;
        this.color = color;
        this.image = image;
    }


}
