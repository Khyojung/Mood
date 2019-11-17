package com.lemon.piece.dodamdodam.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lemon.piece.dodamdodam.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunityActivity extends AppCompatActivity {

    String id;
    String category;
    String[] community_text = null;

    int MAX_PAGE = 3;
    Fragment fragment = new Fragment();
    Handler handler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        category = intent.getExtras().getString("category");

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.arg1 == 1000){
                    MAX_PAGE = community_text.length;
                    ViewPager viewPager = findViewById(R.id.viewpager);
                    viewPager.setAdapter((new adapter(getSupportFragmentManager())));
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDataCommunity community = new GetDataCommunity(CommunityActivity.this);
                community.execute("http://168.188.126.175/dodam/get_community.php", category);
                while(true){
                    community_text = community.te;
                    if(community_text!= null){
                        Message message = handler.obtainMessage();
                        message.arg1 = 1000;
                        handler.sendMessage(message);
                        break;
                    }
                }

            }
        }).start();



    }
    private class adapter extends FragmentPagerAdapter{
        @Override
        public Fragment getItem(int position) {
            if(position < 0 || MAX_PAGE <= position)
                return null;
            Page page = new Page();
            page.setText(community_text[position]);
            fragment = page;
            return fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }

        public adapter(FragmentManager fm){
            super(fm);
        }
    }
}
class GetDataCommunity extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String category = null;
    Boolean re = false;

    public GetDataCommunity(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        category = params[1];
        String param = "category=" + category+ "";
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

