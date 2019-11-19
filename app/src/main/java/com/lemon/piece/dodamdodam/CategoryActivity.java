package com.lemon.piece.dodamdodam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lemon.piece.dodamdodam.category.GraphActivity;
import com.lemon.piece.dodamdodam.category.chat.MessageActivity;
import com.lemon.piece.dodamdodam.category.diary.DiaryActivity;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    String id;
    String name;

    Button message;
    Button graph;
    Button community;
    Button diary;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");

        message = findViewById(R.id.message);
        graph = findViewById(R.id.graph);
        community = findViewById(R.id.community);
        diary = findViewById(R.id.diary);
        logout = findViewById(R.id.button_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("가는거냥! 다음에 또 와라냥!");
                builder.setPositiveButton("나갈께!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences re = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor reset = re.edit();
                                reset.clear();
                                reset.commit();

                                startActivity(new Intent(CategoryActivity.this, MainActivity.class));

                                finish();
                            }
                        });
                builder.setNegativeButton("좀더 놀래!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });


        message.setOnClickListener(this);
        graph.setOnClickListener(this);
        community.setOnClickListener(this);
        diary.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.message:
                intent = new Intent(this, MessageActivity.class);

                break;
            case R.id.graph:
                intent = new Intent(this, GraphActivity.class);
                break;
            case R.id.community:
                intent = new Intent(this, NavigationActivity.class);
                break;
            case R.id.diary:
                intent = new Intent(this, DiaryActivity.class);
                break;
        }
        intent.putExtra("id", id);
        intent.putExtra("name",name);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("별도의 로그아웃이 없을 경우 로그인을 유지한다냥! \n또 놀러와라냥!");
        builder.setPositiveButton("또올께!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });

        builder.show();
    }
}
