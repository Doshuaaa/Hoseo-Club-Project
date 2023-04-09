package com.example.hoseoclub;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClubResultViewHolder extends RecyclerView.ViewHolder {

    public ImageView clubImage;
    public TextView clubNameText;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public ClubResultViewHolder(@NonNull View itemView) {
        super(itemView);
        clubImage = itemView.findViewById(R.id.clubImage);
        clubNameText = itemView.findViewById(R.id.clubNameText);
    }

    public void setClubInformation(Context context, String text, String image) {
        clubNameText.setText(text);
        Glide.with(context).load(image).into(clubImage);
    }
}
