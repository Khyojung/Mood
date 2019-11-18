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

    Handler mandarin_handler, userHandler;
    Runnable mandarin_runnable, user_runnable;
    boolean mandarin_chat = false;
    Intent intent;
    private boolean side = false;
    LinearLayout layout;

    Button yes, no;
    RecyclerView recyclerView;
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_message);
        recyclerView = (RecyclerView) findViewById(R.id.chat_list);

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

        LinearLayout button = findViewById(R.id.button_layout);
        button.setVisibility(View.VISIBLE);


        yes = findViewById(R.id.chat_yes);
        no = findViewById(R.id.chat_no);
        no.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(count == 2){
                    String temp = "아니";
                    mandarin_chat = false;
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    //adapter.notifyDataSetChanged();

                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
                if(count == 3 || count == 4){
                    String temp = "멘탈관리자?";
                    mandarin_chat = false;
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    //adapter.notifyDataSetChanged();

                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }

            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 2){
                    String temp = "응";
                    mandarin_chat = true;
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();


                }
                if(count == 3 || count == 4){
                    String temp = "그게뭔데?";
                    mandarin_chat = false;
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();

                }
                mandarin_handler.postDelayed(mandarin_runnable, 2000);


                recyclerView.scrollToPosition(adapter.getItemCount()-1);

            }
        });

        chatText = (EditText) findViewById(R.id.chat_edit);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String temp = chatText.getText().toString();
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
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
            //응이라고 대답했을때
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest2"), "string", getPackageName())));
            adapter.addItem(chatMessage);
            //adapter.notifyDataSetChanged();
            count=4;
            yes.setText("그게뭔데?");
            no.setText("멘탈관리자?");
        }else if(count == 2 && mandarin_chat == false) {
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest3"), "string", getPackageName())));
            adapter.addItem(chatMessage);
            count = 4;
            yes.setText("그게뭔데?");
            no.setText("멘탈관리자?");
        }else if(count == 4 || count == 5 || count == 6){
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count=count+1;
            adapter.notifyDataSetChanged();
        }else{
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count=count+1;
        }
        //adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(adapter.getItemCount()-1);
        //message.arg1 = 0;
    }
    @Override
    protected void onDestroy() {
        Log.i("test", "onDstory()");
        mandarin_handler.removeCallbacks(mandarin_runnable);
        super.onDestroy();
    }

}