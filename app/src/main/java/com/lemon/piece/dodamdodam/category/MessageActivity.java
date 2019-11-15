package com.lemon.piece.dodamdodam.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.lemon.piece.dodamdodam.ChatMessage;
import com.lemon.piece.dodamdodam.ChatMessageAdapter;
import com.lemon.piece.dodamdodam.R;

public class MessageActivity extends AppCompatActivity {
    private ChatMessageAdapter adapter;

    private EditText chatText;

    Intent intent;
    private boolean side = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_message);
        final RecyclerView recyclerView = findViewById(R.id.chat_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new ChatMessageAdapter();
        recyclerView.setAdapter(adapter);

        chatText = (EditText) findViewById(R.id.chat_edit);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ChatMessage chatMessage = new ChatMessage(side, chatText.getText().toString());
                    adapter.addItem(chatMessage);
                    chatText.setText("");
                    side = !side;
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    return true;
                }
                return false;
            }
        });




    }

}
