package com.lemon.piece.dodamdodam.category.diary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lemon.piece.dodamdodam.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DiaryActivity extends AppCompatActivity {
    String id, name;
    DiaryAdapter adapter;
    Handler handler;
    String[] diary_text1 = null;
    String[] diary_text2 = null;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.arg1 == 1000){
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DiaryActivity.this);
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapter = new DiaryAdapter();
                    recyclerView.setAdapter(adapter);

                    for(int i = 0; i< diary_text1.length; i++){
                        DiaryData diaryData = new DiaryData();
                        diaryData.setTitle(diary_text1[i]);
                        diaryData.setDay(diary_text2[i]);

                        adapter.addItem(diaryData);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDiaryData community = new GetDiaryData(DiaryActivity.this);
                community.execute("http://168.188.126.175/dodam/get_diary.php", id);
                while(true){
                    diary_text1 = community.te1;
                    diary_text2 = community.te2;
                    if(diary_text1!= null && diary_text2!=null){
                        Message message = handler.obtainMessage();
                        message.arg1 = 1000;
                        handler.sendMessage(message);
                        break;
                    }
                }

            }
        }).start();

        Button button = findViewById(R.id.write_diary);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, WriteDiaryActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name",name);
                startActivity(intent);
                finish();
            }
        });


    }
}
class GetDiaryData extends AsyncTask<String, Void, String> {
    private Context context;
    String te1[] = null;
    String te2[] = null;
    String test[] = null;
    String id = null;
    Boolean re = false;

    public GetDiaryData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String param = "ID=" + id+ "";
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

            //te = new String[2][];
            test = sb.toString().split(" ");
            te1 = test[0].split(",");
            te2 = test[1].split(",");
            Log.e("asdf", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);



    }


}
