package com.lemon.piece.dodamdodam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WriteCommunityActivity extends AppCompatActivity {

    String id;
    String name;

    String text;
    int select_button = -1;
    String select = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_community);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");

        final EditText editText = findViewById(R.id.write_community_text);

        final RadioGroup radioGroup= findViewById(R.id.write_feeling);



        ImageButton imageButton = findViewById(R.id.write_community_heart);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_button = radioGroup.getCheckedRadioButtonId();
                if(select_button == -1){
                    Toast.makeText(WriteCommunityActivity.this, "기분 카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    switch (select_button){
                        case R.id.write_happy:
                            select = "happy";
                            break;
                        case R.id.write_angry:
                            select = "angry";
                            break;
                        case R.id.write_dis:
                            select = "dis";
                            break;
                        case R.id.write_sad:
                            select = "sad";
                            break;
                    }
                    text = editText.getText().toString();
                    try{
                        InsertWriteData task = new InsertWriteData(WriteCommunityActivity.this);
                        task.execute("http://168.188.126.175/dodam/write_diary.php", id, name, select, "0", text, "0");
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        Toast.makeText(WriteCommunityActivity.this, "게시글을 작성했다냥!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }

            }
        });
    }
}
class InsertWriteData extends AsyncTask<String, Void, String> {

    private Context context;
    String te = null;

    public InsertWriteData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        String id = (String)params[1];
        String name = (String)params[2];
        String category = (String)params[3];
        String num = (String)params[4];
        String message = (String)params[5];
        String heart = (String)params[6];

        String serverURL = (String)params[0];
        String postParameters = "ID=" + id + "&NAME=" + name+ "&CATEGORY=" + category+ "&MESSAGE_NUM=" + num+ "&MESSAGE=" + message+ "&HEART=" + heart+ "";


        try {

            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();


            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }


            bufferedReader.close();

            Log.e("write_diary",sb.toString());

            return sb.toString();


        } catch (Exception e) {

            return new String("Error: " + e.getMessage());
        }

    }
}