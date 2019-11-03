package com.lemon.piece.dodamdodam;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class SignInActivity extends AppCompatActivity {

    String id;
    String pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        Button button = (Button)findViewById(R.id.check_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = ((EditText)findViewById(R.id.check_id)).getText().toString();
                pw = ((EditText)findViewById(R.id.check_pw)).getText().toString();
                GetDataJSON getDataJSON = new GetDataJSON(SignInActivity.this);
                getDataJSON.execute("http://168.188.126.175/dodam/login.php", id, pw);
            }
        });

    }
}
class GetDataJSON extends AsyncTask<String, Void, String> {
    private Context context;
    String te = null;
    String id = null;

    public GetDataJSON(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        id = params[1];
        String pw = params[2];
        String param = "u_id=" + id+  "&u_pw="+pw+"";
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


            te = sb.toString();
            Log.e("asdf", te);
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


        if (te.equals("success")) {
            Log.e("RESULT", "성공적으로 처리되었습니다!");
            Log.e("RESULT", "성공적으로 처리되었습니다!");
            Intent intent = new Intent(context, CategoryActivity.class);
            context.startActivity(intent);


        } else if (te.equals("error")) {
            Log.e("RESULT", "비밀번호가 일치하지 않습니다.");
            Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

        }

    }


}


