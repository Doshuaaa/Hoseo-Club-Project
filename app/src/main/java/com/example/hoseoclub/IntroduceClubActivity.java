package com.example.hoseoclub;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class IntroduceClubActivity extends AppCompatActivity {


    private ImageButton backButton;
    private ImageView clubImage;
    private TextView clubNameText;
    private TextView clubIntroduceText;
    private String clubName = ListFragment.selectedClubName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduceclub);

        backButton = findViewById(R.id.backImageButton);
        clubImage = findViewById(R.id.clubImageView);
        clubNameText = findViewById(R.id.clubNameTextView);
        clubIntroduceText = findViewById(R.id.introduceClubTextView);

        clubNameText.setText(clubName);
        Glide.with(this).load(ListFragment.selectedClubImage).into(clubImage);
        clubIntroduceText.setText(ListFragment.selectedClubText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
