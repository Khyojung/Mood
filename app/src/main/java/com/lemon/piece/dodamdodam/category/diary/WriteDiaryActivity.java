package com.lemon.piece.dodamdodam.category.diary;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.lemon.piece.dodamdodam.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteDiaryActivity extends AppCompatActivity {
    SignaturePad signaturePad;
    Button saveButton;
    String id;
    String name;
    String title, write, save_day;
    EditText diary_name, diary_write;
    char[] day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");
        title = "title";
        write = "write";
        saveButton = findViewById(R.id.saveButton);
        diary_name = findViewById(R.id.diary_name);
        diary_write = findViewById(R.id.diary_write);





        save_day = this.getDay();
        day = save_day.toCharArray();
        this.setDay(day);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for saving the signature here
                CustomDialog customDialog = new CustomDialog(WriteDiaryActivity.this);
                customDialog.setDialogListener(new CustomDialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked(boolean re) {
                        if(re){
                            WriteDiaryActivity.this.finish();
                        }
                    }
                });
                if(diary_name.getText().toString()!=""){
                    title = diary_name.getText().toString();
                }
                if(diary_write.getText().toString() !=""){
                    write = diary_write.getText().toString();
                }


                WriteDiaryData task = new WriteDiaryData(WriteDiaryActivity.this);
                task.execute("http://168.188.126.175/dodam/diary.php", id, name, title, write, save_day);
                // 커스텀 다이얼로그를 호출한다.
                // 커스텀 다이얼로그의 결과를 출력할 TextView를 매개변수로 같이 넘겨준다.
                customDialog.callFunction();

            }
        });


    }
    public String getDay(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);
        return year+month+day;
    }
    public void setDay(char[] day){
        TextView textView1 = findViewById(R.id.diary_day_1);
        TextView textView2 = findViewById(R.id.diary_day_2);
        TextView textView3 = findViewById(R.id.diary_day_3);
        TextView textView4 = findViewById(R.id.diary_day_4);
        TextView textView5 = findViewById(R.id.diary_day_5);
        TextView textView6 = findViewById(R.id.diary_day_6);
        TextView textView7 = findViewById(R.id.diary_day_7);
        TextView textView8 = findViewById(R.id.diary_day_8);

        textView1.setText(String.valueOf(day[0]));
        textView2.setText(String.valueOf(day[1]));
        textView3.setText(String.valueOf(day[2]));
        textView4.setText(String.valueOf(day[3]));
        textView5.setText(String.valueOf(day[4]));
        textView6.setText(String.valueOf(day[5]));
        textView7.setText(String.valueOf(day[6]));
        textView8.setText(String.valueOf(day[7]));
    }
}

class WriteDiaryData extends AsyncTask<String, Void, String> {

    private Context context;
    String te = null;

    public WriteDiaryData(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        String id = (String)params[1];
        String name = (String)params[2];
        String title = (String)params[3];
        String contents = (String)params[4];
        String day = (String)params[5];

        String serverURL = (String)params[0];
        String postParameters = "ID=" + id + "&NAME=" + name+  "&TITLE=" + title+ "&CONTENTS=" + contents+ "&DAY=" + day+ "";


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