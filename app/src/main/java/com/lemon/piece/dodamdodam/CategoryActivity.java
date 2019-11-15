package com.lemon.piece.dodamdodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lemon.piece.dodamdodam.category.DiaryActivity;
import com.lemon.piece.dodamdodam.category.GraphActivity;
import com.lemon.piece.dodamdodam.category.MessageActivity;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    String id;
    String name;

    Button message;
    Button graph;
    Button community;
    Button diary;
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
}
