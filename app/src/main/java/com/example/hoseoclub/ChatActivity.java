package com.example.hoseoclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String interlocutor = intent.getStringExtra("interlocutor");

        ImageButton chatListBackButton = findViewById(R.id.chatListBackButton);
        RecyclerView chatListRecyclerView = findViewById(R.id.chatListRecyclerView);
        TextView textView = findViewById(R.id.receiverTextView);


        ImageButton startSendActivityButton = findViewById(R.id.startSendActivityButton);
        ImageButton popupButton = findViewById(R.id.popupButton);
        RecyclerView recyclerView = findViewById(R.id.chatRecyclerView);


        textView.setText(interlocutor);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter<ChatAdapter.ChatAdapterViewHolder> adapter = new ChatAdapter(this, interlocutor);
        recyclerView.setAdapter(adapter);



        startSendActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendActivityIntent = new Intent(ChatActivity.this, SendChatActivity.class);

            }
        });

    }
}
