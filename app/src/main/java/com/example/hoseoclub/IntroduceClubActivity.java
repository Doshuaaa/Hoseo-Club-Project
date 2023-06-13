package com.example.hoseoclub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntroduceClubActivity extends AppCompatActivity {


    private ImageButton backButton;
    private ImageView clubImage;
    private TextView clubNameText;
    private TextView clubIntroduceText;
    private String clubName = ListFragment.selectedClubName;
    private Button likeButton, chatButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    HashMap map;
    int i = 1;
    //private Set<String> likedClubSet;

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
       // likedClubSet = ((MainActivity)MainActivity.contextMain).pref.getStringSet("LIKE_CLUB_LIST", null);

        database = FirebaseDatabase.getInstance();

        clubNameText.setText(clubName);
        Glide.with(this).load(ListFragment.selectedClubImage).into(clubImage);
        clubIntroduceText.setText(ListFragment.selectedClubText);

        String id = getSharedPreferences("loginId", MODE_PRIVATE).getString("loginId", null);

        map = new HashMap();
        databaseReference = database.getReference("User").child(id).child("lickedClub");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    map.put(snapshot1.getKey(), snapshot1.getValue());
                    i = Integer.parseInt(snapshot1.getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                map.put(String.valueOf(i), clubName);
                databaseReference.updateChildren(map);
                //likedClubSet.add(clubName);
                //((MainActivity)MainActivity.contextMain).pref.edit().putStringSet("LIKE_CLUB_LIST", likedClubSet).apply();
                Toast.makeText(IntroduceClubActivity.this, "관심있는 동아리에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                i++;
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
