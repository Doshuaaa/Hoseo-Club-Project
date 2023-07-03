package com.example.hoseoclub;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class ChatAdapter extends RecyclerView.Adapter {

    private final ArrayList<MessageItem> chatList;
    private final SimpleDateFormat simpleDateFormat;
    Context context;
    String interlocutor;

    public ChatAdapter(Context context, String interlocutor) {
        this.context = context;
        this.interlocutor = interlocutor;

        String longinID = ((MainActivity)MainActivity.contextMain).loginId;

        chatList = new ArrayList<>();

        simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chatReference = database.getReference().child("User").child(longinID).child("Chat").child(interlocutor);

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String message = dataSnapshot.child("message").getValue(String.class);
                    long time = dataSnapshot.child("time").getValue(Long.class);
                    int flag = dataSnapshot.child("flag").getValue(Integer.class);

                    chatList.add(new MessageItem(message, time, flag));
                }
                Collections.sort(chatList, new MessageTimeComparator());
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.viewholder_chat, null);
        ChatAdapterViewHolder viewHolder = new ChatAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatAdapterViewHolder viewHolder = (ChatAdapterViewHolder) holder;
        MessageItem messageItem = chatList.get(position);
        Date date = new Date(messageItem.longTime);

        viewHolder.initChat(chatList.get(position).message, simpleDateFormat.format(date), messageItem.flag);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView flagTextView;
        private final TextView timeTextView;
        private final TextView messageTextView;

        public ChatAdapterViewHolder(@NonNull View itemView) {

            super(itemView);

            flagTextView = itemView.findViewById(R.id.flagTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);


        }

        public void initChat(String message, String time, int flag) {
            if(flag == 0) {
                flagTextView.setTextColor(Color.parseColor("#08A7EF"));
                flagTextView.setText("보낸 쪽지");
            }
            else {
                flagTextView.setTextColor(Color.parseColor("#EAD203"));
                flagTextView.setText("받은 쪽지");
            }
            messageTextView.setText(message);
            timeTextView.setText(time);
        }
    }
}
