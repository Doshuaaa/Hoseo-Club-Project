package com.example.hoseoclub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    private RecyclerView clubListRecyclerView;
    private RecyclerView clubInformRecyclerView;
    private ImageButton likeButton, listSettingButton;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.Adapter informAdapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String universityName = null;
    private ArrayList<String> clubNameList;
    public static ArrayList<String> clubTypeList;
    private ArrayList<ClubInformationList> clubInformationLists;

    private ArrayList<ClubCheckBoxViewHolder> clubCheckBoxList;
    private int lastPosition = 0;


    public static String selectedClubName = "null";
    public static String selectedClubImage = "null";
    public static String selectedClubText = "null";
    //public static Context contextListFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences pref;
    private SharedPreferences.Editor edit;


    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_list, container, false);

        clubListRecyclerView = inflate.findViewById(R.id.clubListRecyclerView);
        clubInformRecyclerView = inflate.findViewById(R.id.clubInformationRecyclerView);
        likeButton = inflate.findViewById(R.id.likeClubImageButton);
        listSettingButton = inflate.findViewById(R.id.listSettingButton);

        universityName = ((LoginActivity)LoginActivity.contextLogin).universityName;

        clubNameList = new ArrayList<>();
        clubTypeList = new ArrayList<>();
        clubCheckBoxList = new ArrayList<>();
        clubInformationLists = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        databaseReference.child("Club").child(universityName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    clubTypeList.add(dataSnapshot.getKey());
                }

                listAdapter = new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = View.inflate(ListFragment.this.getContext(), R.layout.viewholder_club_checkbox, null);
                        RecyclerView.ViewHolder viewHolder = new ClubCheckBoxViewHolder(view);
                        return viewHolder;
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                        ClubCheckBoxViewHolder viewHolder = (ClubCheckBoxViewHolder) holder;
                        clubCheckBoxList.add(viewHolder);


                        viewHolder.setCheckBoxText(clubTypeList.get(position));
                        viewHolder.clubCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if(lastPosition == holder.getAdapterPosition())
                                    return;
                                clubCheckBoxList.get(lastPosition).clubCheckBox.setChecked(false);
                                lastPosition = holder.getAdapterPosition();

                                if(buttonView.isChecked()) {

                                    databaseReference.child("Club")
                                            .child(universityName)
                                            .child(buttonView.getText().toString()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    clubInformationLists.clear();
                                                    clubNameList.clear();
                                                    String name = null;
                                                    String text = null;
                                                    String image = null;
                                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                        name = dataSnapshot.getKey();
                                                        text = dataSnapshot.child("text").getValue(String.class);
                                                        image = dataSnapshot.child("image").getValue(String.class);
                                                        clubNameList.add(name);
                                                     clubInformationLists.add(new ClubInformationList(name, image, text));
                                                    }
                                                    informAdapter.notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                }
                            }

                        });

                    }

                    @Override
                    public int getItemCount() {
                        return clubTypeList.size();
                    }
                };

                //recyclerView.setLayoutManager(new LinearLayoutManager(ListFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false));
                clubListRecyclerView.setLayoutManager(new GridLayoutManager(ListFragment.this.getContext(), 3));
                clubListRecyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        informAdapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = View.inflate(ListFragment.this.getContext(), R.layout.club_result_viewholder, null);
                RecyclerView.ViewHolder viewHolder1 = new ClubResultViewHolder(view);
                return viewHolder1;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ClubResultViewHolder viewHolder1 = (ClubResultViewHolder) holder;
                viewHolder1.setClubInformation(ListFragment.this.getContext(),
                        clubInformationLists.get(position).getClubName(),
                        clubInformationLists.get(position).getClubImage());
                ((ClubResultViewHolder) holder).clubLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedClubName = clubInformationLists.get(viewHolder1.getLayoutPosition()).getClubName();
                        selectedClubImage = clubInformationLists.get(viewHolder1.getLayoutPosition()).getClubImage();
                        selectedClubText = clubInformationLists.get(viewHolder1.getLayoutPosition()).getClubIntro();
                        Intent intent = new Intent(ListFragment.this.getContext(), IntroduceClubActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return clubInformationLists.size();
            }
        };
        clubInformRecyclerView.setLayoutManager(new GridLayoutManager(ListFragment.this.getContext(), 3));
        clubInformRecyclerView.setAdapter(informAdapter);

        // databaseReference.child("Club").child(universityName)

        likeButton.setOnClickListener(this);
        return inflate;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.likeClubImageButton:
                Intent intent = new Intent(ListFragment.this.getContext(), LikeClubListActivity.class);
                startActivity(intent);
                break;
            case R.id.listSettingButton:

        }
    }
}