package com.example.hoseoclub;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClubCheckBoxViewHolder extends RecyclerView.ViewHolder {

    public CheckBox clubCheckBox;


    public ClubCheckBoxViewHolder(@NonNull View itemView) {
        super(itemView);

        clubCheckBox = itemView.findViewById(R.id.clubCheckBox);

    }

    public void setCheckBoxText(String text) {
        clubCheckBox.setText(text);
    }
}
