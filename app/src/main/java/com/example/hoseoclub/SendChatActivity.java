package com.example.hoseoclub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class SendChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference senderReference, receiverReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendchat);

        Intent intent = getIntent();

        database = FirebaseDatabase.getInstance();

        String receiverID = intent.getStringExtra("receiverID");
        String receiverName = intent.getStringExtra("receiverName") + " 회장";

        senderReference = database.getReference().child("User").child(((MainActivity) MainActivity.contextMain).loginId).child("Chat").child(receiverID);
        receiverReference = database.getReference().child("User").child(receiverID).child("Chat").child(((MainActivity) MainActivity.contextMain).loginId);

        Button sendButton = findViewById(R.id.sendButton);
        EditText chatEditText = findViewById(R.id.chatEditText);
        TextView receiverTextView = findViewById(R.id.receiverSendChatTextView);

        receiverTextView.setText(receiverName);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = chatEditText.getText().toString();
                Object time = ServerValue.TIMESTAMP;
                MessageItem item = new MessageItem(content, time, 0);
                senderReference.push().setValue(item);
                item = new MessageItem(content,time, 1);
                receiverReference.push().setValue(item);
                finish();
            }
        });
    }
}
