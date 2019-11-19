package com.lemon.piece.dodamdodam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lemon.piece.dodamdodam.category.chat.MessageActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MiddleActivity extends AppCompatActivity {
    String[] feels, second_preference, third, fourth_preference;
    String id, name;
    int count;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");
        count = 0;

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent send = new Intent(MiddleActivity.this, MessageActivity.class);
                send.putExtra("id",id);
                send.putExtra("name",name);
                send.putExtra("feels", feels);
                send.putExtra("fourth_preference", fourth_preference);
                send.putExtra("third", third);
                send.putExtra("second_preference", second_preference);
                startActivity(send);
                finish();
            }
        };


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GetTotalBaseData getTotalBaseData = new GetTotalBaseData(MiddleActivity.this);
                getTotalBaseData.execute("http://168.188.126.175/dodam/get_total_base_data.php", id);
                while(true){
                    if(getTotalBaseData.te != null){
                        String[] te = getTotalBaseData.getData();
                        feels = new String[]{te[0], te[1], te[2], te[3]};
                        fourth_preference = new String[]{te[4], te[5], te[6], te[7], te[8]};
                        third = new String[]{te[9]};
                        second_preference = new String[]{te[10], te[11], te[12], te[13], te[14]};
                        handler.postDelayed(runnable, 3000);
                        break;
                    }
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class GetTotalBaseData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String id = null;
    String re;

    public GetTotalBaseData(Context con){
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
            Log.e("total_base_data", sb.toString());
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }
    @Override
    public void onPostExecute(String s){

    }

    public String[] getData(){
        return this.te;
    }

}
