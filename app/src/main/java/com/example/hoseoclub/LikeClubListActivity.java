package com.example.hoseoclub;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LikeClubListActivity extends AppCompatActivity {

    private RecyclerView likedClubRecyclerView;

    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_club_list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference likedClubDatabaseReference =
                database.getReference("User").child(((MainActivity) MainActivity.contextMain).loginId).child("likedClub");

        DatabaseReference clubInformDatabaseReference = database.getReference("Club").child(((LoginActivity)LoginActivity.contextLogin).universityName);

        ArrayList<LikedClub> likedClubList = new ArrayList<>();
        ArrayList<ClubInformationList> clubInformationLists = new ArrayList<>();

        likedClubRecyclerView = findViewById(R.id.likeClubListRecyclerView);
        clubInformationLists.clear();

        likedClubDatabaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            likedClubList.add(new LikedClub(dataSnapshot.getValue(String.class), dataSnapshot.getKey()));
                        }

                        clubInformDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int i = 0;
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                    for(LikedClub likedClub : likedClubList) {
                                        if(likedClub.getLikedClubType().equals(dataSnapshot.getKey())) {
                                            String image = dataSnapshot
                                                    .child(likedClub.getLikedClubName()).child("image").getValue(String.class);

                                            String text = dataSnapshot
                                                    .child(likedClub.getLikedClubName()).child("text").getValue(String.class);

                                            clubInformationLists.add(new ClubInformationList(likedClub.getLikedClubName(),
                                                    likedClub.getLikedClubType(), image, text));


                                        }
                                    }

                                   // Club clubInformation =
                                   //         dataSnapshot.child(likedClubList.get(i).getLikedClubType()).child(likedClubList.get(i).getLikedClubName()).getValue(Club.class);


                                }
                                adapter = new RecyclerView.Adapter() {
                                    @NonNull
                                    @Override
                                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        View view = View.inflate(LikeClubListActivity.this, R.layout.club_result_viewholder, null);
                                        RecyclerView.ViewHolder viewHolder = new ClubResultViewHolder(view);

                                        return viewHolder;
                                    }

                                    @Override
                                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                                        ClubResultViewHolder viewHolder = (ClubResultViewHolder) holder;
                                        String s = clubInformationLists.get(position).getClubName();
                                        viewHolder.setClubInformation(LikeClubListActivity.this,
                                                clubInformationLists.get(position).getClubName(),
                                                clubInformationLists.get(position).getClubImage());

                                        viewHolder.clubLinearLayout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });
                                    }

                                    @Override
                                    public int getItemCount() {
                                        return clubInformationLists.size();
                                    }
                                };
                                likedClubRecyclerView.setLayoutManager(new GridLayoutManager(LikeClubListActivity.this, 3));
                                likedClubRecyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}
