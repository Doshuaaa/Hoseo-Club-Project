package com.example.hoseoclub;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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

public class RecentChatAdapter extends RecyclerView.Adapter <RecentChatAdapter.RecentChatViewHolder> {

    String loginID;
    FirebaseDatabase database;
    DatabaseReference reference;

    ArrayList<String> interlocutorList;

    ArrayList<MessageItem> chatList, recentChatList;

    Context context;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");


    public RecentChatAdapter(Context context, String loginID) {

        this.context = context;


        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("User").child(loginID).child("Chat");

        interlocutorList = new ArrayList<>();
        chatList = new ArrayList<>();
        recentChatList = new ArrayList<>();
        this.loginID = loginID;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    interlocutorList.add(dataSnapshot.getKey().toString());
                }
                reference.removeEventListener(this);

                for(String interlocutor : interlocutorList) {

                    reference.child(interlocutor).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String message = dataSnapshot.child("message").getValue(String.class);
                                Long longTime = dataSnapshot.child("time").getValue(Long.class);
                                Integer flag = dataSnapshot.child("flag").getValue(Integer.class);


                                chatList.add(new MessageItem(interlocutor, message, longTime, flag));

                            }
                            recentChatList.add(chatList.get(chatList.size() - 1));
                            reference.removeEventListener(this);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                Collections.sort(chatList, new MessageTimeComparator());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @NonNull
    @Override
    public RecentChatAdapter.RecentChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.viewholder_recentchat, null);
        RecentChatAdapter.RecentChatViewHolder viewHolder = new RecentChatViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatAdapter.RecentChatViewHolder holder, int position) {
        RecentChatViewHolder viewHolder = (RecentChatViewHolder) holder;


        Collections.sort(recentChatList, new MessageTimeComparator());

        MessageItem messageItem = recentChatList.get(position);

        Date date = new Date(messageItem.longTime);

        String stringTime = simpleDateFormat.format(date);

        viewHolder.initRecentChat(messageItem.interlocutor, messageItem.message, stringTime);

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("interlocutor", recentChatList.get(position).interlocutor);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentChatList.size();
    }

    public class RecentChatViewHolder extends RecyclerView.ViewHolder{


        private final TextView interlocutorTextView;
        private final TextView chatContentTextView;
        private final TextView dateTextView;

        public RelativeLayout relativeLayout;

        public RecentChatViewHolder(@NonNull View itemView) {
            super(itemView);

            interlocutorTextView = itemView.findViewById(R.id.interlocutorTextView);
            chatContentTextView = itemView.findViewById(R.id.chatContentTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            relativeLayout = itemView.findViewById(R.id.recentChatRelativeLayout);
        }


        public void initRecentChat(String interlocutor, String chatContentText, String dateText) {
            interlocutorTextView.setText(interlocutor);
            chatContentTextView.setText(chatContentText);
            dateTextView.setText(dateText);
        }
    }

//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = View.inflate(parent.getContext(), R.layout.viewholder_recentchat, null);
//        RecyclerView.ViewHolder viewHolder = new RecentChatViewHolder(v);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        RecentChatViewHolder viewHolder = (RecentChatViewHolder) holder;
//
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    interlocutorList.add(dataSnapshot.getKey().toString());
//                }
//                reference.removeEventListener(this);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        for(String interlocutor : interlocutorList) {
//
//            Query query = reference.child(interlocutor).orderByChild("time");
//
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        String message = dataSnapshot.child("message").getValue(String.class);
//                        Long longTime = dataSnapshot.child("time").getValue(Long.class);
//                        Integer flag = dataSnapshot.child("flag").getValue(Integer.class);
//                        String stringTime = longTime.toString();
//
//                        recentChatList.add(new MessageItem(interlocutor, message, stringTime, flag));
//                        query.removeEventListener(this);
//                        break;
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        Collections.sort(recentChatList, new MessageTimeComparator());
//    }
//
//    @Override
//    public int getItemCount() {
//        return interlocutorList.size();
//    }
}
