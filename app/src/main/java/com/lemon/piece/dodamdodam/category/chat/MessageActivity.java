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
import android.widget.NumberPicker;

import com.lemon.piece.dodamdodam.R;

public class MessageActivity extends AppCompatActivity {
    private ChatMessageAdapter adapter;

    private EditText chatText;
    int count = 0;

    int happyness = 5, sadness = 5, annoyed = 5, depressed = 5;
    Handler mandarin_handler, userHandler;
    Runnable mandarin_runnable, user_runnable;
    boolean mandarin_chat = false;
    Intent intent;
    private boolean side = false;
    LinearLayout picker;

    String emotion = "기쁨";

    String first_answer, second_answer, third_answer, fourth_answer;

    String[] first = {"집 혹은 기숙사","회사 혹은 학교","음식점","카페","도서관, PC방 등의 취미 생활공간"};
    String[] second = {"혼자 있었어","친구랑 있었어","가족이랑 있었어","애인이랑 있었어","새로운 사람이랑 있었어"};
    String[] third = {"내가 가고 싶어서!","누가 불렀어!","꼭 가야만 하는 자리였어!"};
    String[] fourth = {"행복했어","안 좋은 사건이 생겼어..","생산적이었어!","생각 없어! 평화로워..zz"};

    NumberPicker message_picker;

    Button yes, no, picker_save;
    RecyclerView recyclerView;
    LinearLayout button;

    String id, name;
    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = "918fall";//intent.getExtras().getString("id");
        name = "khj";//intent.getExtras().getString("name");
        setContentView(R.layout.activity_message);

        picker = findViewById(R.id.picker);
        message_picker = findViewById(R.id.message_picker);


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

        button = findViewById(R.id.button_layout);
        button.setVisibility(View.VISIBLE);

        picker_save = findViewById(R.id.picker_save);
        picker_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(count == 7){
                    first_answer = first[message_picker.getValue()];;
                    String temp = first[message_picker.getValue()];
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    //mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);

                }
                if(count == 10){
                    second_answer = second[message_picker.getValue()];
                    String temp = second[message_picker.getValue()];
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    //mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
                if(count == 12){
                    third_answer = third[message_picker.getValue()];
                    String temp = third[message_picker.getValue()];
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    mandarin_handler.postDelayed(mandarin_runnable, 3000);
                    //mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
                if(count == 15){
                    fourth_answer = fourth[message_picker.getValue()];
                    String temp = fourth[message_picker.getValue()]; // 안좋은 일이 있었다고 하면 물어봐야함
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    mandarin_handler.postDelayed(mandarin_runnable, 3000);
                    mandarin_handler.postDelayed(mandarin_runnable, 4000);
                    mandarin_handler.postDelayed(mandarin_runnable, 5000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
            }
        });
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
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
                if(count == 3 || count == 4){
                    String temp = "멘탈관리자?";
                    mandarin_chat = false;
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    mandarin_handler.postDelayed(mandarin_runnable, 3000);
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
                    recyclerView.setAdapter(adapter);

                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                }
                if(count == 3 || count == 4){
                    String temp = "그게뭔데?";
                    mandarin_chat = false;
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                    mandarin_handler.postDelayed(mandarin_runnable, 3000);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);

                }



            }
        });

        chatText = (EditText) findViewById(R.id.chat_edit);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String temp = chatText.getText().toString();
                    ChatMessage chatMessage = new ChatMessage(side, temp);
                    adapter.addItem(chatMessage);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    mandarin_handler.postDelayed(mandarin_runnable, 1000);

                    if(count == 8){
                        mandarin_handler.postDelayed(mandarin_runnable, 1000);
                    }
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
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            count=4;
            yes.setText("그게뭔데?");
            no.setText("멘탈관리자?");
        }else if(count == 2 && mandarin_chat == false) {
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest3"), "string", getPackageName())));
            adapter.addItem(chatMessage);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            count = 4;
            yes.setText("그게뭔데?");
            no.setText("멘탈관리자?");
        }else if(count == 4 || count == 5 || count == 6){
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count=count+1;
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            if(count == 7) {
                picker.setVisibility(View.VISIBLE);
                message_picker.setMaxValue(4);
                message_picker.setMinValue(0);
                message_picker.setDisplayedValues(first);
                button.setVisibility(View.INVISIBLE);
                //count=count+1;
            }
        }else if(count == 7){
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count=count+1;
            picker.setVisibility(View.INVISIBLE);
            chatText.setVisibility(View.VISIBLE);
        }else if(count == 9){
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);
            chatText.setVisibility(View.INVISIBLE);
            picker.setVisibility(View.VISIBLE);
            message_picker.setMaxValue(4);
            message_picker.setMinValue(0);
            message_picker.setDisplayedValues(second);
            count=count+1;
        }else if(count == 11){
            ChatMessage chatMessage = new ChatMessage(side, first_answer + getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            message_picker.setDisplayedValues(null);

            message_picker.setMaxValue(third.length-1);
            message_picker.setMinValue(0);
            message_picker.setDisplayedValues(third);
            count=count+1;
        }else if(count == 14){
            ChatMessage chatMessage = new ChatMessage(side, name + getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);
            message_picker.setDisplayedValues(null);
            message_picker.setMaxValue(fourth.length-1);
            message_picker.setMinValue(0);
            message_picker.setDisplayedValues(fourth);
            count=count+1;
        }else if(count == 15){
            if(fourth_answer.equals("안 좋은 사건이 생겼어..")){
                ChatMessage chatMessage = new ChatMessage(side, "헥!,, 무슨일이다냥 나한테만 얘기해줘냥,,");
                adapter.addItem(chatMessage);
                picker.setVisibility(View.INVISIBLE);
                chatText.setVisibility(View.VISIBLE);

            }
            count=count+1;
        }else if(count == 16){
            ChatMessage chatMessage = new ChatMessage(side, "그랬구나,,그랬구나");
            adapter.addItem(chatMessage);
            picker.setVisibility(View.INVISIBLE);
            count=count+1;

        }else if(count == 17){
            ChatMessage chatMessage = new ChatMessage(side, "오늘 너의 하루는 "+emotion+"인 것 같다냥..!");
            adapter.addItem(chatMessage);
            count=count+1;

        }else if(count == 18){
            ChatMessage chatMessage = new ChatMessage(side, "니가 좋으면 나도 좋고 니가 슬프면 나도 슬프다냥,,");
            adapter.addItem(chatMessage);
            count=count+1;
        }else if(count == 19){
            ChatMessage chatMessage = new ChatMessage(side, "함께라서 좋은 " + name + "아 ,내가 오늘 너를 위해 선물을 준비했다냥 \n 골라주라냥");
            adapter.addItem(chatMessage);
            button.setVisibility(View.VISIBLE);
            yes.setText("명언");
            no.setText("시");
            count=count+1;
        }else{
            ChatMessage chatMessage = new ChatMessage(side, getString(getResources().getIdentifier(("quest" + count), "string", getPackageName())));
            adapter.addItem(chatMessage);

            count=count+1;
        }
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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