package com.lemon.piece.dodamdodam;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    EditText name;
    EditText again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        id = (EditText) findViewById(R.id.edit_id);
        pw = (EditText) findViewById(R.id.edit_pw);
        name = (EditText) findViewById(R.id.edit_name);
        again = findViewById(R.id.edit_pw_again);

        Button button = (Button)findViewById(R.id.edit_button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String _id = id.getText().toString();
                String _pw = pw.getText().toString();
                String _name = name.getText().toString();
                String _pw_again = again.getText().toString();

                if(_pw.equals(_pw_again)){
                    try{
                        InsertData task = new InsertData(SignUpActivity.this);
                        task.execute("http://168.188.126.175/dodam/insert.php", _id, _pw, _name);
                    }catch (Exception e){
                        Log.d("error", String.valueOf(e));
                    }finally {
                        finish();
                    }
                }else{

                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
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
}