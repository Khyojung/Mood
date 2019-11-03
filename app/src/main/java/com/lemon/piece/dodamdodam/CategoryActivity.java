package com.lemon.piece.dodamdodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lemon.piece.dodamdodam.category.CommunityActivity;
import com.lemon.piece.dodamdodam.category.EditActivity;
import com.lemon.piece.dodamdodam.category.GraphActivity;
import com.lemon.piece.dodamdodam.category.MessageActivity;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    Button message;
    Button graph;
    Button community;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        message = findViewById(R.id.message);
        graph = findViewById(R.id.graph);
        community = findViewById(R.id.community);
        edit = findViewById(R.id.edit);

        message.setOnClickListener(this);
        graph.setOnClickListener(this);
        community.setOnClickListener(this);
        edit.setOnClickListener(this);

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
                intent = new Intent(this, CommunityActivity.class);
                break;
            case R.id.edit:
                intent = new Intent(this, EditActivity.class);
                break;
        }
        startActivity(intent);
    }
}
