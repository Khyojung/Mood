package com.lemon.piece.dodamdodam.category.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.lemon.piece.dodamdodam.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

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
    String emotion_result;

    String first_answer, second_answer, third_answer, fourth_answer;

    String[] first_preference, second_preference, third_preference, fourth_preference;
    String[] first = {"집 혹은 기숙사","회사 혹은 학교","음식점","카페","도서관, PC방 등의 취미 생활공간"};
    String[] second = {"혼자 있었어","친구랑 있었어","가족이랑 있었어","애인이랑 있었어","새로운 사람이랑 있었어"};
    String[] third = {"내가 가고 싶어서!","누가 불렀어!","꼭 가야만 하는 자리였어!"};
    String[] fourth = {"행복했어","안 좋은 사건이 생겼어..","생산적이었어!","생각 없어! 평화로워..zz"};

    String[] music, message;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetFirstData getFirstData = new GetFirstData(MessageActivity.this);
                getFirstData.execute("http://168.188.126.175/dodam/get_first_data.php", id);
                while(true){
                    if(getFirstData.te != null){
                        first_preference = getFirstData.getData();
                        happyness = Integer.parseInt(first_preference[0]);
                        sadness = Integer.parseInt(first_preference[1]);
                        annoyed = Integer.parseInt(first_preference[2]);
                        depressed = Integer.parseInt(first_preference[3]);
                        break;
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetSecondData getSecondData = new GetSecondData(MessageActivity.this);
                getSecondData.execute("http://168.188.126.175/dodam/get_second_data.php", id);
                while(true){
                    if(getSecondData.te != null){
                        second_preference = getSecondData.getData();
                        break;
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetThridData getThridData = new GetThridData(MessageActivity.this);
                getThridData.execute("http://168.188.126.175/dodam/get_third_data.php", id);
                while(true){
                    if(getThridData.te != null){
                        third_preference = getThridData.getData();
                        break;
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetFourthData getFourthData = new GetFourthData(MessageActivity.this);
                getFourthData.execute("http://168.188.126.175/dodam/get_fourth_data.php", id);
                while(true){
                    if(getFourthData.te != null){
                        fourth_preference = getFourthData.getData();
                        break;
                    }
                }

            }
        }).start();

        button = findViewById(R.id.button_layout);
        button.setVisibility(View.VISIBLE);

        picker_save = findViewById(R.id.picker_save);
        picker_save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(count == 7){
                    first_answer = first[message_picker.getValue()];
                    String first_check = first_answer.replaceAll(" ","");
                    if(first_check.equals(second_preference[0])){
                        happyness=happyness+3;
                    }else if(first_check.equals(second_preference[1])){
                        happyness=happyness+2;
                    }else if(first_check.equals(second_preference[2])){
                        happyness=happyness+1;
                    }else if(first_check.equals(second_preference[3])){
                        sadness=sadness+1;
                        depressed=depressed+1;
                    }else{
                        sadness=sadness+1;
                        annoyed=annoyed+3;
                        depressed=depressed+2;
                    }
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
                    String second_check;
                    if(second_answer.equals("혼자 있었어")) second_check = "혼자있는게좋음";
                    else if(second_answer.equals("친구랑 있었어")) second_check = "친구";
                    else if(second_answer.equals("가족이랑 있었어")) second_check = "가족";
                    else if(second_answer.equals("애인이랑 있었어")) second_check = "애인";
                    else second_check = "평소알고지내지않았던인물";
                    if(second_check.equals(fourth_preference[0])){
                        happyness=happyness+3;
                    }else if(second_check.equals(fourth_preference[1])){
                        happyness=happyness+2;
                    }else if(second_check.equals(fourth_preference[2])){
                        happyness=happyness+1;
                    }else if(second_check.equals(fourth_preference[3])){
                        sadness=sadness+1;
                        depressed=depressed+1;
                    }else{
                        sadness=sadness+1;
                        annoyed=annoyed+3;
                        depressed=depressed+2;
                    }
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

                    if(third_answer.equals("내가 가고 싶어서!")){
                        happyness=happyness+1;
                    }
                    else if(third_answer.equals("누가 불렀어")){
                        if(third_preference[0].equals("일을처리할때성급하고빠른편이다")){
                            happyness=happyness+1;
                        }else{
                            sadness=sadness+1;
                        }
                    }
                    else if(third_answer.equals("꼭 가야만 하는 자리였어!")){
                        annoyed=annoyed+1;
                    }
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
                    if(fourth_answer.equals("행복했어")){
                        happyness=happyness+2;
                    }
                    else if(fourth_answer.equals("안 좋은 사건이 생겼어..")){
                        sadness=sadness+1;
                        annoyed=annoyed+1;
                        depressed=depressed+1;
                    }
                    else if(fourth_answer.equals("생산적이었어!")){
                        if(third_preference[0].equals("일을처리할때성급하고빠른편이다")){
                            happyness=happyness+1;
                        }else{
                            depressed=depressed+1;
                        }
                    }
                    else if(fourth_answer.equals("생각없음 평화로워..ZZ")){
                        if(third_preference[0].equals("일을처리할때성급하고빠른편이다")){
                            annoyed=annoyed+1;
                        }else{
                            happyness=happyness+1;
                        }
                    }
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
                if(count == 19){ // 노래
                    GetMusicData getMusicData = new GetMusicData(MessageActivity.this);
                    getMusicData.execute("http://168.188.126.175/dodam/get_music_data.php", emotion_result);
                    while(true){
                        if(getMusicData.te != null){
                            music = getMusicData.getData();
                            break;
                        }
                    }
                    int ran = ((int)Math.random()*10);
                    ChatMessage chatMessage = new ChatMessage(side, music[ran]);
                    adapter.addItem(chatMessage);
                    button.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    count = 30;
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                }
                if(count == 40){
                    Toast.makeText(MessageActivity.this, "다음에 또만나자냥!", Toast.LENGTH_LONG);
                    finish();
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
                if(count == 19){ //명언
                    GetMessageData getMessageData = new GetMessageData(MessageActivity.this);
                    getMessageData.execute("http://168.188.126.175/dodam/get_message_data.php", emotion_result);
                    while(true){
                        if(getMessageData.te != null){
                            message = getMessageData.getData();
                            break;
                        }
                    }
                    int ran = ((int)Math.random()*10);
                    ChatMessage chatMessage = new ChatMessage(side, message[ran]);
                    adapter.addItem(chatMessage);
                    button.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    count = 31;
                    mandarin_handler.postDelayed(mandarin_runnable, 2000);
                }
                if(count == 40){
                    Toast.makeText(MessageActivity.this, "다음에 또만나자냥!", Toast.LENGTH_LONG);
                    finish();
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
            int[] temp = {happyness, sadness, annoyed, depressed};
            Arrays.sort(temp);
            if(temp[3] == happyness){
                emotion = "행복";
                emotion_result = "happyness";
            }
            else if(temp[3] == sadness){
                emotion = "슬픔";
                emotion_result = "sadness";
            }
            else if(temp[3] == annoyed) {
                emotion = "짜증";
                emotion_result = "annoyed";
            }
            else if(temp[3] == depressed){
                emotion = "우울";
                emotion_result = "depressed";
            }

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
            no.setText("노래");
            //count=count+1;
        }else if(count == 30){

            //노래
            ChatMessage chatMessage = new ChatMessage(side, "지금 기분에 듣기 좋은 노래다냥! \n 벌써 대화가 다 끝났다냥..\n 그래도 내가 보고싶으면 언제든 다시 와라냥!");
            adapter.addItem(chatMessage);
            button.setVisibility(View.VISIBLE);
            yes.setText("또올께 만다린!");
            no.setText("생각좀 해보고..");
            count = 40;
        }else if(count == 31){
            //명언
            ChatMessage chatMessage = new ChatMessage(side, "지금 기분에 읽기 좋은 명언이다냥! \n 벌써 대화가 다 끝났다냥..\n 그래도 내가 보고싶으면 언제든 다시 와라냥!");
            adapter.addItem(chatMessage);
            button.setVisibility(View.VISIBLE);
            yes.setText("또올께 만다린!");
            no.setText("생각좀 해보고..");
            count = 40;
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
class GetFirstData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String id = null;
    Boolean re = false;

    public GetFirstData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String param = "ID=" + id+"";
        String uri = params[0];
        try{
            URL url = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
/* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
            int responseStatusCode = conn.getResponseCode();
            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();


            te = sb.toString().split(",");
            Log.e("first_preference", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public String[] getData(){
        return this.te;
    }

}

class GetSecondData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String id = null;
    Boolean re = false;

    public GetSecondData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String param = "ID=" + id+"";
        String uri = params[0];
        try{
            URL url = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
/* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
            int responseStatusCode = conn.getResponseCode();
            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();


            te = sb.toString().split(",");
            Log.e("first_preference", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public String[] getData(){
        return this.te;
    }

}

class GetThridData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String id = null;
    Boolean re = false;

    public GetThridData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String param = "ID=" + id+"";
        String uri = params[0];
        try{
            URL url = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
/* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
            int responseStatusCode = conn.getResponseCode();
            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();


            te = sb.toString().split(",");
            Log.e("first_preference", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public String[] getData(){
        return this.te;
    }

}

class GetFourthData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String id = null;
    Boolean re = false;

    public GetFourthData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String param = "ID=" + id+"";
        String uri = params[0];
        try{
            URL url = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
/* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
            int responseStatusCode = conn.getResponseCode();
            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();


            te = sb.toString().split(",");
            Log.e("first_preference", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public String[] getData(){
        return this.te;
    }

}

class GetMessageData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String category = null;
    Boolean re = false;

    public GetMessageData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        category = params[1];
        String param = "FS_category=" + category+"";
        String uri = params[0];
        try{
            URL url = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
/* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
            int responseStatusCode = conn.getResponseCode();
            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();


            te = sb.toString().split(",");
            Log.e("message", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public String[] getData(){
        return this.te;
    }

}
class GetMusicData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String category = null;
    Boolean re = false;

    public GetMusicData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        category = params[1];
        String param = "MUSIC_category=" + category+"";
        String uri = params[0];
        try{
            URL url = new URL(uri);
            HttpURLConnection conn= (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
/* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

/* 서버 -> 안드로이드 파라메터값 전달 */
            int responseStatusCode = conn.getResponseCode();
            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
            }
            else{
                inputStream = conn.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();


            te = sb.toString().split(",");
            Log.e("music", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public String[] getData(){
        return this.te;
    }

}