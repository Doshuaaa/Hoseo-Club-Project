package com.example.hoseoclub;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    public static boolean isChatCheckBoxChecked = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button deleteChatButton;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_message, container, false);

        String loginID = ((MainActivity)MainActivity.contextMain).loginId;

        ImageButton chatSettingButton = inflate.findViewById(R.id.chatSettingButton);
        RecyclerView chatListRecyclerView = inflate.findViewById(R.id.chatListRecyclerView);

        deleteChatButton = inflate.findViewById(R.id.deleteChatButton);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatFragment.this.getContext());
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        chatListRecyclerView.setLayoutManager(linearLayoutManager);
        RecentChatAdapter adapter = new RecentChatAdapter(this, ChatFragment.this.getContext(), loginID);
        chatListRecyclerView.setAdapter(adapter);

        deleteChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    adapter.deleteCheckedChat();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        chatSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isChatCheckBoxChecked = true;
                adapter.notifyDataSetChanged();
            }
        });

//        final OnBackPressedCallback onBackPressedCallback =  new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                if(RecentChatAdapter.isCheckBoxVisible) {
//                    isChatCheckBoxChecked = false;
//                    adapter.notifyDataSetChanged();
//                }
//                else {
//                    getActivity().finish();
//                }
//            }
//        };



//        Intent intent = new Intent(ChatFragment.this.getContext(), ChatActivity.class);
//        intent.putExtra("수신자", "");
//        startActivity(intent);


        return inflate;
    }


    public void setDeleteChatCheckBoxVisible(boolean currentVisibility) {

        if(currentVisibility) {
            deleteChatButton.setVisibility(View.VISIBLE);
        }
    }

}