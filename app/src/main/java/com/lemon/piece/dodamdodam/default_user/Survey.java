package com.lemon.piece.dodamdodam.default_user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.lemon.piece.dodamdodam.R;

public class Survey extends AppCompatActivity {

    int count =2;
    String id, name;
    Button buttonNext, buttonBefore;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Intent intent = getIntent();
        id = "918fall";//intent.getExtras().getString("id");
        name = "khj";//intent.getExtras().getString("name");
        view = null;
        count = 0;

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

        switch (index) {
            case 0:
                view = inflater.inflate(R.layout.survey_1, frame, false) ;
                buttonBefore.setEnabled(false);

                break;
            case 1:
                view = inflater.inflate(R.layout.survey_2, frame, false) ;
                buttonBefore.setEnabled(true);
                break;
            case 2:
                view = inflater.inflate(R.layout.survey_3, frame, false) ;
                buttonNext.setEnabled(true);
                break;
            case 3:
                view = inflater.inflate(R.layout.survey_4, frame, false) ;
                buttonNext.setEnabled(false);
                break;
        }
        if (view != null) {
            frame.addView(view) ;
        }
    }

}
