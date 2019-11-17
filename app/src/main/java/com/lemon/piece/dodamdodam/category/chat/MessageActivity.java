package com.lemon.piece.dodamdodam.category.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lemon.piece.dodamdodam.R;

public class MessageActivity extends AppCompatActivity {
    private ChatMessageAdapter adapter;

    private EditText chatText;
    int count = 0;

    Handler mandarin_handler;
    Runnable mandarin_runnable;
    boolean mandarin_chat = false;
    Intent intent;
    private boolean side = false;
    LinearLayout layout;

    @SuppressLint("HandlerLeak")
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

        mandarin_handler = new Handler();
        mandarin_runnable = new Runnable() {
            @Override
            public void run() {
                chatMandarin();
            }
        };

        chatMandarin();
        chatMandarin();


        Button yes = findViewById(R.id.chat_yes);
        Button no = findViewById(R.id.chat_no);
        chatText = (EditText) findViewById(R.id.chat_edit);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String temp = chatText.getText().toString();
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    if(count == 2 && temp.equals("1")){
                        mandarin_chat = true;
                    }else if(count == 2 && temp.equals("2")){
                        mandarin_chat = false;
                    }
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);

                    chatText.setText("");
                    side = !side;
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    return true;
                }
                return false;
            }
        });




    }
    public void chatMandarin(){
        //Message message = mandarin_handler.obtainMessage();
        if(count == 2 && mandarin_chat == true){
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest2"), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count++;
        }else if(count == 2 && mandarin_chat == false){
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest3"), "string", getPackageName())));
            adapter.addItem(chatMessage);
            count = 4;
        }else{
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count++;
        }

        //message.arg1 = 0;
    }
    @Override
    protected void onDestroy() {
        Log.i("test", "onDstory()");
        mandarin_handler.removeCallbacks(mandarin_runnable);
        super.onDestroy();
    }

}
