package com.lemon.piece.dodamdodam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    EditText name;
    EditText again;
    String _id, _pw, _name , _pw_again;
    String check;
    Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        id = (EditText) findViewById(R.id.edit_id);
        pw = (EditText) findViewById(R.id.edit_pw);
        name = (EditText) findViewById(R.id.edit_name);
        again = findViewById(R.id.edit_pw_again);
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msa){
                if(msa.arg1 == 1000){
                    if(_id.equals("")||_pw.equals("") || _name.equals("")||_pw_again.equals("")){
                        Toast.makeText(SignUpActivity.this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!check.equals("success")){
                            Toast.makeText(SignUpActivity.this, "존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            if(!_pw.equals(_pw_again)){
                                Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                            }else{
                                InsertData task = new InsertData(SignUpActivity.this);
                                task.execute("http://168.188.126.175/dodam/insert.php", _id, _pw, _name);
                            }

                        }
                    }
                }
                message.arg1 = 10;


            }
        };
        Button button = (Button)findViewById(R.id.edit_button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                _id = id.getText().toString();
                _pw = pw.getText().toString();
                _name = name.getText().toString();
                _pw_again = again.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CheckIdData check_data = new CheckIdData(SignUpActivity.this);
                        check_data.execute("http://168.188.126.175/dodam/check_id.php", _id);
                        while(true){
                            if(check_data.te != null){
                                message = handler.obtainMessage();
                                message.arg1 = 1000;
                                check = check_data.getId();
                                handler.sendMessage(message);
                                break;
                            }
                        }
                    }
                }).start();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }









            }
        });

    }
}
class InsertData extends AsyncTask<String, Void, String>{

    private Context context;
    String te = null;

    public InsertData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        String id = (String)params[1];
        String password = (String)params[2];
        String name = (String)params[3];

        String serverURL = (String)params[0];
        String postParameters = "USER_ID=" + id + "&USER_PWD=" + password+ "&USER_NAME=" + name+ "";


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

            Log.e("2222",sb.toString());

            return sb.toString();


        } catch (Exception e) {

            return new String("Error: " + e.getMessage());
        }

    }
    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        ((Activity)context).finish();



    }
}

class CheckIdData extends AsyncTask<String, Void, String>{

    private Context context;
    String te = null;

    String id;
    public CheckIdData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String param = "u_id=" + id+"";
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


            te = sb.toString(); //if te가 success면 DB에 아이디가 존재하는 경우
            Log.e("temp", te);
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }
    public String getId(){
        if(this.te.equals("1")){
            return "error";
        }else{
            return "success";
        }
    }
}