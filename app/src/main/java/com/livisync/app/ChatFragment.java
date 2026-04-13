package com.livisync.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    RecyclerView rvChats;
    FirebaseFirestore db;
    String myUid;
    List<ChatItem> chatList = new ArrayList<>();
    ChatListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        db = FirebaseFirestore.getInstance();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        rvChats = view.findViewById(R.id.rvChats);
        rvChats.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ChatListAdapter(chatList, item -> {
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("matchId", item.getMatchId());
            intent.putExtra("otherUid", item.getOtherUid());
            intent.putExtra("otherName", item.getOtherName());
            startActivity(intent);
        });
        rvChats.setAdapter(adapter);

        loadMatches();
        return view;
    }

    private void loadMatches() {
        // Load matches where I am user1
        db.collection("matches")
                .whereEqualTo("user1", myUid)
                .get()
                .addOnSuccessListener(snap -> {
                    for (DocumentSnapshot doc : snap.getDocuments()) {
                        String otherUid = doc.getString("user2");
                        loadChatItem(doc.getId(), otherUid);
                    }
                });

        // Load matches where I am user2
        db.collection("matches")
                .whereEqualTo("user2", myUid)
                .get()
                .addOnSuccessListener(snap -> {
                    for (DocumentSnapshot doc : snap.getDocuments()) {
                        String otherUid = doc.getString("user1");
                        loadChatItem(doc.getId(), otherUid);
                    }
                });
    }

    private void loadChatItem(String matchId, String otherUid) {
        db.collection("users").document(otherUid).get()
                .addOnSuccessListener(userDoc -> {
                    String name = userDoc.getString("name");

                    // Get last message
                    db.collection("messages").document(matchId)
                            .collection("chats")
                            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                            .limit(1)
                            .get()
                            .addOnSuccessListener(msgSnap -> {
                                String lastMsg = "Say hello!";
                                if (!msgSnap.isEmpty()) {
                                    lastMsg = msgSnap.getDocuments().get(0).getString("text");
                                }
                                chatList.add(new ChatItem(matchId, otherUid, name, lastMsg));
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(() -> adapter.updateList(chatList));
                                }
                            });
                });
    }
}