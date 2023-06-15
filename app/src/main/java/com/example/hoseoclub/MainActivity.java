package com.example.hoseoclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton listImgBtn;
    private ImageButton chatImgBtn;
    private ImageButton seeMoreImgBtn;
    public static Context contextMain;
    public String loginId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextMain = this;

        Intent intent = getIntent();
        loginId = intent.getStringExtra("loginId");

        listImgBtn = findViewById(R.id.listTabImageButton);
        chatImgBtn = findViewById(R.id.chatTabImageButton);
        seeMoreImgBtn = findViewById(R.id.moreTabImageButton);

        listImgBtn.setOnClickListener(this);
        chatImgBtn.setOnClickListener(this);
        seeMoreImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listTabImageButton:
                callFragment(0);
                break;

            case R.id.chatTabImageButton:
                callFragment(1);
                break;

            case R.id.moreTabImageButton:
                callFragment(2);
                break;
        }

    }

    private void callFragment(int numFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(numFragment) {
            case 0:
                ListFragment listFragment = new ListFragment();
                transaction.replace(R.id.fragmentContainer, listFragment);
                transaction.commit();
                break;

            case 1:
                ChatFragment chatFragment = new ChatFragment();
                transaction.replace(R.id.fragmentContainer, chatFragment);
                transaction.commit();
                break;

            case 2:
                SeeMoreFragment seeMoreFragment = new SeeMoreFragment();
                transaction.replace(R.id.fragmentContainer, seeMoreFragment);
                transaction.commit();
                break;
        }
    }
}