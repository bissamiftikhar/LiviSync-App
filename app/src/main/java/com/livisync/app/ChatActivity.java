package com.livisync.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    RecyclerView rvMessages;
    EditText etMessage;
    Button btnSend, btnBack;
    TextView tvChatName;

    FirebaseFirestore db;
    String myUid, matchId, otherUid, otherName;
    List<MessageItem> messageList = new ArrayList<>();
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        matchId = getIntent().getStringExtra("matchId");
        otherUid = getIntent().getStringExtra("otherUid");
        otherName = getIntent().getStringExtra("otherName");

        rvMessages = findViewById(R.id.rvMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
        tvChatName = findViewById(R.id.tvChatName);

        tvChatName.setText(otherName);

        adapter = new MessageAdapter(messageList, myUid);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(adapter);

        listenForMessages();

        btnSend.setOnClickListener(v -> sendMessage());
        btnBack.setOnClickListener(v -> finish());
    }

    private void listenForMessages() {
        // Real time listener — updates instantly when new message arrives
        db.collection("messages")
                .document(matchId)
                .collection("chats")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((snap, e) -> {
                    if (snap == null) return;
                    messageList.clear();
                    for (var doc : snap.getDocuments()) {
                        String text = doc.getString("text");
                        String sender = doc.getString("senderUid");
                        Long ts = doc.getLong("timestamp");
                        messageList.add(new MessageItem(text, sender, ts != null ? ts : 0));
                    }
                    adapter.updateList(messageList);
                    // Scroll to bottom
                    if (!messageList.isEmpty()) {
                        rvMessages.scrollToPosition(messageList.size() - 1);
                    }
                });
    }

    private void sendMessage() {
        String text = etMessage.getText().toString().trim();
        if (text.isEmpty()) return;

        Map<String, Object> message = new HashMap<>();
        message.put("text", text);
        message.put("senderUid", myUid);
        message.put("timestamp", System.currentTimeMillis());

        db.collection("messages")
                .document(matchId)
                .collection("chats")
                .add(message)
                .addOnSuccessListener(ref -> {
                    etMessage.setText("");
                });
    }
}