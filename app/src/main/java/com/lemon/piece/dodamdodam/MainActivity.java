package com.lemon.piece.dodamdodam;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    String id, name, pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button signup = (Button)findViewById(R.id.play);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startLoading();
            }
        });

    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                id = auto.getString("inputId", null);
                pw = auto.getString("inputPwd", null);
                name = auto.getString("inputName",null);
                if(id != null && pw != null){
                    Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("name",name);
                    startActivity(intent);
                    finish();
                }else{
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                    finish();
                }

            }
        }, 1000);
    }



}
