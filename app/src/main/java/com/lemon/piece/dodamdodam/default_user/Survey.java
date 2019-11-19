package com.lemon.piece.dodamdodam.default_user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lemon.piece.dodamdodam.CategoryActivity;
import com.lemon.piece.dodamdodam.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Survey extends AppCompatActivity {

    int count =2;
    String id, name;
    Button buttonNext, buttonBefore;
    String happyness="0" , sadness="0" , annoyed="0" , depressed ="0"; // 설문 1번
    String[] intimacy = new String[5]; // 설문 2번
    String survey3; // 설문 3번
    String[] place = new String[5]; // 설문 4번

    String[] survey_result = new String[4];

    View view;
    RelativeLayout relativeLayout;
    Boolean view1 = null, view2 = null, view3 = null, view4 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");
        view = null;
        count = 0;
        happyness = "5";
        sadness = "5";
        annoyed = "5";
        depressed = "5";

        relativeLayout = findViewById(R.id.survey_background);
        buttonNext  = (Button)findViewById(R.id.survey_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count + 1;
                changeView(count);

            }
        });
        buttonBefore  = (Button)findViewById(R.id.survey_before);
        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = count - 1;
                changeView(count);

            }
        });

        changeView(count);
    }

    private void changeView(int index) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout frame = (FrameLayout) findViewById(R.id.survey);
        if (frame.getChildCount() > 0) {
            // FrameLayout에서 뷰 삭제.
            frame.removeViewAt(0);
        }

        // XML에 작성된 레이아웃을 View 객체로 변환.

        RadioButton radioButton1, radioButton2;


        switch (index) {
            case 0:
                view = inflater.inflate(R.layout.survey_1, frame, false) ;
                view1 = true;
                buttonBefore.setVisibility(View.INVISIBLE);
                buttonNext.setVisibility(View.VISIBLE);
                break;

            case 1:
                if(view1){
                    EditText happy = (EditText)view.findViewById(R.id.survey_11);
                    happyness = happy.getText().toString();
                    EditText dis = (EditText)view.findViewById(R.id.survey_12);
                    depressed = dis.getText().toString();
                    EditText sad = (EditText)view.findViewById(R.id.survey_13);
                    sadness = sad.getText().toString();
                    EditText angry = (EditText)view.findViewById(R.id.survey_14);
                    annoyed = angry.getText().toString();
                }
                view1 = false;


                view = inflater.inflate(R.layout.survey_2, frame, false) ;
                buttonBefore.setVisibility(View.VISIBLE);
                buttonNext.setVisibility(View.INVISIBLE);



                final CheckBox survey2_1 = view.findViewById(R.id.survey2_1);
                final CheckBox survey2_2 = view.findViewById(R.id.survey2_2);
                final CheckBox survey2_3 = view.findViewById(R.id.survey2_3);
                final CheckBox survey2_4 = view.findViewById(R.id.survey2_4);
                final CheckBox survey2_5 = view.findViewById(R.id.survey2_5);
                final int[] count = {0};
                survey2_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intimacy[count[0]] = "친구";
                        count[0]++;
                        survey2_1.setEnabled(false);
                        if(count[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey2_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intimacy[count[0]] = "평소알고지내지않았던인물";
                        count[0]++;
                        survey2_2.setEnabled(false);
                        if(count[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey2_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intimacy[count[0]] = "가족";
                        count[0]++;
                        survey2_3.setEnabled(false);
                        if(count[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey2_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intimacy[count[0]] = "애인";
                        count[0]++;
                        survey2_4.setEnabled(false);
                        if(count[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey2_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intimacy[count[0]] = "혼자있는게좋음";
                        count[0]++;
                        survey2_5.setEnabled(false);
                        if(count[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });

                break;
            case 2:
                view = inflater.inflate(R.layout.survey_3, frame, false) ;
                view3 = true;
                buttonNext.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    buttonNext.setBackground(getBaseContext().getDrawable(R.drawable.next_button));
                }
                RadioGroup radioGroup = view.findViewById(R.id.survey3_radio);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        buttonNext.setVisibility(View.VISIBLE);
                    }
                });

                break;
            case 3:
                if(view3){
                    radioButton1 = view.findViewById(R.id.survey3_1);
                    radioButton2 = view.findViewById(R.id.survey3_2);
                    if(radioButton1.isChecked()) survey3 = "일을처리할때성급하고빠른편이다";
                    if(radioButton2.isChecked()) survey3 = "일을처리할때느린편이다";
                }
                view3 = false;

                view = inflater.inflate(R.layout.survey_4, frame, false) ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    buttonNext.setBackground(getBaseContext().getDrawable(R.drawable.cat_save));
                }
                buttonNext.setVisibility(View.INVISIBLE);
                final CheckBox survey4_1 = view.findViewById(R.id.survey4_1);
                final CheckBox survey4_2 = view.findViewById(R.id.survey4_2);
                final CheckBox survey4_3 = view.findViewById(R.id.survey4_3);
                final CheckBox survey4_4 = view.findViewById(R.id.survey4_4);
                final CheckBox survey4_5 = view.findViewById(R.id.survey4_5);
                final int[] count4 = {0};
                survey4_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        place[count4[0]] = "카페";
                        count4[0]++;
                        survey4_1.setEnabled(false);
                        if(count4[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey4_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        place[count4[0]] = "도서관,PC방등의취미생활공간";
                        count4[0]++;
                        survey4_2.setEnabled(false);
                        if(count4[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey4_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        place[count4[0]] = "음식점";
                        count4[0]++;
                        survey4_3.setEnabled(false);
                        if(count4[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey4_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        place[count4[0]] = "집혹은기숙사";
                        count4[0]++;
                        survey4_4.setEnabled(false);
                        if(count4[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });
                survey4_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        place[count4[0]] = "회사혹은학교";
                        count4[0]++;
                        survey4_5.setEnabled(false);
                        if(count4[0] == 5){
                            buttonNext.setVisibility(View.VISIBLE);
                        }
                    }
                });

                break;
            case 4:
                survey_result[0] = "&base_happyness=" + happyness + "&base_sadness="+sadness + "&base_annoyed="+annoyed + "&base_depressed="+depressed;
                survey_result[1] ="&first_intimacy=" + intimacy[0] + "&second_intimacy="+intimacy[1] + "&third_intimacy="+intimacy[2] + "&fourth_intimacy="+intimacy[3] + "&fifth_intimacy="+intimacy[4];
                survey_result[2] ="&characteristic=" + survey3;
                survey_result[3] = "&first_place=" + place[0] + "&second_place="+place[1] + "&third_place="+place[2] + "&fourth_place="+place[3] + "&fifth_place="+place[4]+"";
                view = inflater.inflate(R.layout.finish_survey, frame, false) ;
                frame.removeAllViews();
                buttonBefore.setVisibility(View.INVISIBLE);
                buttonNext.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    relativeLayout.setBackground(getBaseContext().getDrawable(R.drawable.finish_survey));
                }
                setSurveyDataJSON setSurveyDataJSON = new setSurveyDataJSON(Survey.this);
                setSurveyDataJSON.execute("http://168.188.126.175/dodam/user_base.php",id, name, survey_result[0], survey_result[1], survey_result[2], survey_result[3]);


                break;
        }
        if (view != null) {
            frame.addView(view) ;
        }
    }

}

class setSurveyDataJSON extends AsyncTask<String, Void, String> {
    private Context context;
    String te = null;
    String id, name;

    public setSurveyDataJSON(Context con){
        this.context = con;
    }
    @Override
    protected String doInBackground(String... params) {
        name = params[2];
        String survey1 = params[3];
        String survey2 = params[4];
        String survey3 = params[5];
        String survey4 = params[6];
        id = params[1];

        String param = "id="+id+"&name=" + name+ survey1 + survey2 + survey3 + survey4;
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
        try {
            Thread.sleep(3000);
            if(te.equals("success")){
                Toast.makeText(context, "설문을 종료한다냥!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name",name);
                context.startActivity(intent);
                ((Activity)context).finish();
            }else{
                Toast.makeText(context, "설문을 다시 해주세요.", Toast.LENGTH_LONG).show();
                ((Activity)context).finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }



}
