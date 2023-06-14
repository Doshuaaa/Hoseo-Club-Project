package com.example.hoseoclub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LikeClubListActivity extends AppCompatActivity {

    private RecyclerView likedClubRecyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private RecyclerView.Adapter adapter;

    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_club_list);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Club").child(((LoginActivity)LoginActivity.contextLogin).universityName);
        Map map = new HashMap();
        ArrayList<ClubInformationList> clubInformationLists = new ArrayList<>();

        //pref = getSharedPreferences("LIKE_CLUB_LIST")

       // Set<String> likedClubSet
         //       = ((MainActivity)MainActivity.contextMain).pref.getStringSet("LIKE_CLUB_LIST", null);

        likedClubRecyclerView = findViewById(R.id.likeClubListRecyclerView);
        clubInformationLists.clear();

//        databaseReference
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int i = 0;
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            //String s = dataSnapshot.child(clubTypeList.get(i)).getKey();
//                            for(DataSnapshot dataSnapshot1 : snapshot.child(s).getChildren()) {
//                                String s1 = dataSnapshot1.getKey();
////                                if(likedClubSet.contains(s1)) {
////                                    clubInformationLists.add(new ClubInformationList(dataSnapshot1.getKey()
////                                            , dataSnapshot1.child("image").getValue(String.class)
////                                            , dataSnapshot1.child("text").getValue(String.class)));
////                                }
//                            }
//                            i++;
//                        }
//                        adapter = new RecyclerView.Adapter() {
//                            @NonNull
//                            @Override
//                            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                                View view = View.inflate(LikeClubListActivity.this, R.layout.club_result_viewholder, null);
//                                RecyclerView.ViewHolder viewHolder = new ClubResultViewHolder(view);
//
//                                return viewHolder;
//                            }
//
//                            @Override
//                            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//                                ClubResultViewHolder viewHolder = (ClubResultViewHolder) holder;
//                                String s = clubInformationLists.get(position).getClubName();
//                                viewHolder.setClubInformation(LikeClubListActivity.this,
//                                        clubInformationLists.get(position).getClubName(),
//                                        clubInformationLists.get(position).getClubImage());
//                            }
//
//                            @Override
//                            public int getItemCount() {
//                                return clubInformationLists.size();
//                            }
//                        };
//                        likedClubRecyclerView.setLayoutManager(new GridLayoutManager(LikeClubListActivity.this, 3));
//                        likedClubRecyclerView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });







    }
}
