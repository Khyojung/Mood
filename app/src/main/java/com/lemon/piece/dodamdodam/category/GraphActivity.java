package com.lemon.piece.dodamdodam.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lemon.piece.dodamdodam.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class GraphActivity extends AppCompatActivity {

    String[] statistics;
    String today_feelings, today_size;
    String id, name;
    ImageView cup1, cup2, cup3, cup4, cup5, cup6, cup7;
    String[] mon, tue, wed, thi, fri, sat, sun;
    String day;
    Thread thread;
    Message message;
    GetStatisticsData getStatisticsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");

        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void handleMessage(Message msg){
                if(msg.arg1 == 1000){
                    setMondayCups(mon[0],mon[1]);
                    setTuedayCups(tue[0], tue[1]);
                    setWeddayCups(wed[0], wed[1]);
                    setThidayCups(thi[0], thi[1]);
                    setFridayCups(fri[0], fri[1]);
                    setSatdayCups(sat[0], sat[1]);
                    setSundayCups(sun[0], sun[1]);
                    message.arg1 = 10;
                }

            }



        };
        getStatisticsData = new GetStatisticsData(GraphActivity.this);
        getStatisticsData.te = null;
        getStatisticsData.execute("http://168.188.126.175/dodam/get_statistics_data.php", id);
        while(true){
            Log.e("123","안됩니다");
            if(getStatisticsData.te !=null){
                statistics = getStatisticsData.te;
                day = getDay();
                setDays(statistics);
                message = handler.obtainMessage();
                message.arg1 = 1000;
                handler.sendMessage(message);
                break;
            }

        }




        cup1 = findViewById(R.id.cup_day_1);
        cup2 = findViewById(R.id.cup_day_2);
        cup3 = findViewById(R.id.cup_day_3);
        cup4 = findViewById(R.id.cup_day_4);
        cup5 = findViewById(R.id.cup_day_5);
        cup6 = findViewById(R.id.cup_day_6);
        cup7 = findViewById(R.id.cup_day_7);

        final RelativeLayout relativeLayout = findViewById(R.id.graph_question_visible);
        Button question = findViewById(R.id.button_question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
        Button exit = findViewById(R.id.graph_question_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (getStatisticsData.getStatus() == AsyncTask.Status.RUNNING)
        {
            getStatisticsData.cancel(true);
        }
        finish();
    }

    public String getDay(){
        Calendar cal = Calendar.getInstance();
        String strWeek = null;
        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(nWeek == 1){
            strWeek = "SUNDAY";
        }else if(nWeek == 2){
            strWeek = "MONDAY";
        }else if(nWeek == 3){
            strWeek = "TUESDAY";
        }else if(nWeek == 4){
            strWeek = "WEDNESDAY";
        }else if(nWeek == 5){
            strWeek = "THURSDAY";
        }else if(nWeek == 6){
            strWeek = "FRIDAY";
        }else if(nWeek == 7){
            strWeek = "SATURDAY";
        }
        return strWeek;



    }
    public void setDays(String[] statistics){
        String[][] re = new String[7][6];

        int i = 2;
        for(int count = 0; count < 7; count++){
            for(int count2 = 0; count2<6; count2++){
                re[count][count2] = statistics[i];
                i++;
            }
        }

        if(!re[0].equals("none")){
            mon = new String[6];
            mon = re[0];
        }
        if(!re[1].equals("none")){
            tue = new String[6];
            tue = re[1];
        }
        if(!re[2].equals("none")){
            wed = new String[6];
            wed = re[2];
        }
        if(!re[3].equals("none")){
            thi = new String[6];
            thi = re[3];
        }
        if(!re[4].equals("none")){
            fri = new String[6];
            fri = re[4];
        }
        if(!re[5].equals("none")){
            sat = new String[6];
            sat = re[5];
        }
        if(!re[6].equals("none")){
            sun = new String[6];
            sun = re[6];
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMondayCups(String f, String s){
        if(mon != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_happyness_90));
                    } else if (size >= 6) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_happyness_60));// cup1-happyness-60
                    } else {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_happyness_30));// cup1-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_sadness_90));
                    } else if (size >= 6) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_sadness_60));// cup1-happyness-60
                    } else {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_sadness_30));// cup1-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_annoyed_90));
                    } else if (size >= 6) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_annoyed_60));// cup1-happyness-60
                    } else {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_annoyed_30));// cup1-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_depressed_90));
                    } else if (size >= 6) {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_depressed_60));// cup1-happyness-60
                    } else {
                        cup1.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup1_depressed_30));// cup1-happyness-30
                    }
                    break;

            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setTuedayCups(String f, String s){
        if(tue != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_happyness_90));
                    } else if (size >= 6) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_happyness_60));// cup2-happyness-60
                    } else {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_happyness_30));// cup2-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_sadness_90));
                    } else if (size >= 6) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_sadness_60));// cup2-happyness-60
                    } else {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_sadness_30));// cup2-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_annoyed_90));
                    } else if (size >= 6) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_annoyed_60));// cup2-happyness-60
                    } else {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_annoyed_30));// cup2-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_depressed_90));
                    } else if (size >= 6) {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_depressed_60));// cup2-happyness-60
                    } else {
                        cup2.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup2_depressed_30));// cup2-happyness-30
                    }
                    break;

            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setWeddayCups(String f, String s){
        if(wed != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_happyness_90));
                    } else if (size >= 6) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_happyness_60));// cup3-happyness-60
                    } else {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_happyness_30));// cup3-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_sadness_90));
                    } else if (size >= 6) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_sadness_60));// cup3-happyness-60
                    } else {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_sadness_30));// cup3-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_annoyed_90));
                    } else if (size >= 6) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_annoyed_60));// cup3-happyness-60
                    } else {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_annoyed_30));// cup3-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_depressed_90));
                    } else if (size >= 6) {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_depressed_60));// cup3-happyness-60
                    } else {
                        cup3.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup3_depressed_30));// cup3-happyness-30
                    }
                    break;

            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setThidayCups(String f, String s){
        if(thi != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_happyness_90));
                    } else if (size >= 6) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_happyness_60));// cup4-happyness-60
                    } else {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_happyness_30));// cup4-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_sadness_90));
                    } else if (size >= 6) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_sadness_60));// cup4-happyness-60
                    } else {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_sadness_30));// cup4-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_annoyed_90));
                    } else if (size >= 6) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_annoyed_60));// cup4-happyness-60
                    } else {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_annoyed_30));// cup4-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_depressed_90));
                    } else if (size >= 6) {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_depressed_60));// cup4-happyness-60
                    } else {
                        cup4.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup4_depressed_30));// cup4-happyness-30
                    }
                    break;

            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setFridayCups(String f, String s){
        if(fri != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_happyness_90));
                    } else if (size >= 6) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_happyness_60));// cup5-happyness-60
                    } else {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_happyness_30));// cup5-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_sadness_90));
                    } else if (size >= 6) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_sadness_60));// cup5-happyness-60
                    } else {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_sadness_30));// cup5-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_annoyed_90));
                    } else if (size >= 6) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_annoyed_60));// cup5-happyness-60
                    } else {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_annoyed_30));// cup5-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_depressed_90));
                    } else if (size >= 6) {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_depressed_60));// cup5-happyness-60
                    } else {
                        cup5.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup5_depressed_30));// cup5-happyness-30
                    }
                    break;

            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setSatdayCups(String f, String s){
        if(sat != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_happyness_90));
                    } else if (size >= 6) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_happyness_60));// cup6-happyness-60
                    } else {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_happyness_30));// cup6-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_sadness_90));
                    } else if (size >= 6) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_sadness_60));// cup6-happyness-60
                    } else {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_sadness_30));// cup6-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_annoyed_90));
                    } else if (size >= 6) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_annoyed_60));// cup6-happyness-60
                    } else {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_annoyed_30));// cup6-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_depressed_90));
                    } else if (size >= 6) {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_depressed_60));// cup6-happyness-60
                    } else {
                        cup6.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup6_depressed_30));// cup6-happyness-30
                    }
                    break;

            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setSundayCups(String f, String s){
        if(sun != null){
            String feel = f;
            int size = Integer.parseInt(s);
            switch (feel){
                case "happyness":
                    if (size >= 9) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_happyness_90));
                    } else if (size >= 6) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_happyness_60));// cup7-happyness-60
                    } else {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_happyness_30));// cup7-happyness-30
                    }
                    break;
                case "sadness":
                    if (size >= 9) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_sadness_90));
                    } else if (size >= 6) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_sadness_60));// cup7-happyness-60
                    } else {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_sadness_30));// cup7-happyness-30
                    }
                    break;
                case "annoyed":
                    if (size >= 9) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_annoyed_90));
                    } else if (size >= 6) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_annoyed_60));// cup7-happyness-60
                    } else {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_annoyed_30));// cup7-happyness-30
                    }
                    break;
                case "depressed":
                    if (size >= 9) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_depressed_90));
                    } else if (size >= 6) {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_depressed_60));// cup7-happyness-60
                    } else {
                        cup7.setImageDrawable(getBaseContext().getDrawable(R.drawable.cup7_depressed_30));// cup7-happyness-30
                    }
                    break;

            }

        }
    }
}

class GetStatisticsData extends AsyncTask<String, Void, String> {
    private Context context;
    String te[] = null;
    String id = null;
    Boolean re = false;

    public GetStatisticsData(Context con){
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
            Log.e("total_cups", sb.toString());
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