package com.example.hoseoclub;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroduceClubActivity extends AppCompatActivity {


    private ImageButton backButton;
    private ImageView clubImage;
    private TextView clubNameText;
    private TextView clubIntroduce;
    private String clubName = ListFragment.selectedClubName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduceclub);

        backButton = findViewById(R.id.backImageButton);
        clubImage = findViewById(R.id.clubImage);
        clubNameText = findViewById(R.id.clubNameText);
        clubIntroduce = findViewById(R.id.introduceClubTextView);

        clubNameText.setText(clubName);
    }


}
