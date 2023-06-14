package com.example.hoseoclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class IntroduceClubActivity extends AppCompatActivity {


    String selectedClubName;
    String selectedClubImage;
    String selectedClubText;
    String selectedClubType;

    private DatabaseReference databaseReference;

    HashMap map;
    int i = 1;
    //private Set<String> likedClubSet;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduceclub);

        Intent intent = getIntent();
        selectedClubName = intent.getStringExtra("selectedClubName");
        selectedClubImage = intent.getStringExtra("selectedClubImage");
        selectedClubText = intent.getStringExtra("selectedClubText");
        selectedClubType = intent.getStringExtra("selectedClubType");

        ImageButton backButton = findViewById(R.id.backImageButton);
        Button likeClubButton = findViewById(R.id.likeButton);
        Button chatButton = findViewById(R.id.chatButton);
        ImageView clubImage = findViewById(R.id.clubImageView);
        TextView clubNameText = findViewById(R.id.clubNameTextView);
        TextView clubIntroduceText = findViewById(R.id.introduceClubTextView);
       // likedClubSet = ((MainActivity)MainActivity.contextMain).pref.getStringSet("LIKE_CLUB_LIST", null);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        clubNameText.setText(selectedClubName);
        Glide.with(this).load(selectedClubImage).into(clubImage);
        clubIntroduceText.setText(selectedClubText);

        String id = getSharedPreferences("loginId", MODE_PRIVATE).getString("loginId", null);

        map = new HashMap<String, String>();
        databaseReference = database.getReference("User").child(id).child("likedClub");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    map.put(snapshot1.getKey(), snapshot1.getValue());
                   // i = Integer.parseInt(snapshot1.getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        likeClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( map.size() >= 10 ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IntroduceClubActivity.this);
                    builder.setMessage("동아리 좋아요 표시는 10개까지 가능합니다.");
                    builder.setPositiveButton("확인", null);
                    builder.show();
                }
                else {
                    //map.put(String.valueOf(i), clubName);
                    map.put(selectedClubType, selectedClubName);
                    //likedClubSet.add(clubName);
                    //((MainActivity)MainActivity.contextMain).pref.edit().putStringSet("LIKE_CLUB_LIST", likedClubSet).apply();
                    Toast.makeText(IntroduceClubActivity.this, "관심있는 동아리에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    //i++;
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.updateChildren(map);
    }

}
