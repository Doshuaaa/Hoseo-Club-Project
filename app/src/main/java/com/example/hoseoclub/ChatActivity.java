package com.example.hoseoclub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageButton chatListBackButton = findViewById(R.id.chatListBackButton);
        RecyclerView chatListRecyclerView = findViewById(R.id.chatListRecyclerView);


    }
}
