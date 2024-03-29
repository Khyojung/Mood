package com.lemon.piece.dodamdodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.lemon.piece.dodamdodam.category.CommunityActivity;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener{

    String category;
    String id;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");

        ImageButton imageButton = findViewById(R.id.write_community);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent write = new Intent(NavigationActivity.this, WriteCommunityActivity.class);
                write.putExtra("id", id);
                write.putExtra("name", name);
                startActivity(write);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.nav_view);
        //View view = getLayoutInflater().inflate(R.layout.navigation_menu, null);

        ImageButton nav_happy = findViewById(R.id.nav_menu_happy);
        ImageButton nav_sad = findViewById(R.id.nav_menu_sad);
        ImageButton nav_angry = findViewById(R.id.nav_menu_angry);
        ImageButton nav_dis = findViewById(R.id.nav_menu_dis);

        nav_angry.setOnClickListener(this);
        nav_dis.setOnClickListener(this);
        nav_sad.setOnClickListener(this);
        nav_happy.setOnClickListener(this);


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CommunityActivity.class);
        switch (view.getId()){
            case R.id.nav_menu_angry:
                category = "angry";
                break;
            case R.id.nav_menu_dis:
                category = "dis";
                break;
            case R.id.nav_menu_sad:
                category = "sad";
                break;
            case R.id.nav_menu_happy:
                category = "happy";
                break;
        }
        intent.putExtra("id",id);
        intent.putExtra("name", name);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}
