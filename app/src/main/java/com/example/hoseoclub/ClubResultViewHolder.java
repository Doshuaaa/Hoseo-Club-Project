package com.example.hoseoclub;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ClubResultViewHolder extends RecyclerView.ViewHolder {

    public ImageView clubImage;
    public TextView clubIntroText;

    public LinearLayout clubLinearLayout;


    public ClubResultViewHolder(@NonNull View itemView) {
        super(itemView);
        clubImage = itemView.findViewById(R.id.clubImage);
        clubIntroText = itemView.findViewById(R.id.clubNameText);
        clubLinearLayout = itemView.findViewById(R.id.clubLinearLayout);


    }

    public void setClubInformation(Context context, String text, String image) {
        clubIntroText.setText(text);
        Glide.with(context).load(image).into(clubImage);
    }
}
