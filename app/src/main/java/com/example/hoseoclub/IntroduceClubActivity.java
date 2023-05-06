package com.example.hoseoclub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.HashSet;
import java.util.Set;

public class IntroduceClubActivity extends AppCompatActivity {


    private ImageButton backButton;
    private ImageView clubImage;
    private TextView clubNameText;
    private TextView clubIntroduceText;
    private String clubName = ListFragment.selectedClubName;
    private Button likeButton, chatButton;
    private Set<String> likedClubSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduceclub);

        backButton = findViewById(R.id.backImageButton);
        likeButton = findViewById(R.id.likeButton);
        chatButton = findViewById(R.id.chatButton);
        clubImage = findViewById(R.id.clubImageView);
        clubNameText = findViewById(R.id.clubNameTextView);
        clubIntroduceText = findViewById(R.id.introduceClubTextView);
        likedClubSet = ((MainActivity)MainActivity.contextMain).pref.getStringSet("LIKE_CLUB_LIST", new HashSet<>());


        clubNameText.setText(clubName);
        Glide.with(this).load(ListFragment.selectedClubImage).into(clubImage);
        clubIntroduceText.setText(ListFragment.selectedClubText);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likedClubSet.add(clubName);
                ((MainActivity)MainActivity.contextMain).pref.edit().putStringSet("LIKE_CLUB_LIST", likedClubSet).commit();
                Toast.makeText(IntroduceClubActivity.this, "관심있는 동아리에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
