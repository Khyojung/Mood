package com.lemon.piece.dodamdodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signup = (Button)findViewById(R.id.signUp);
        signup.setOnClickListener(this);
        Button signin = (Button)findViewById(R.id.signIn);
        signin.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.signUp:
                intent = new Intent(this, SignUpActivity.class);
                break;
            case R.id.signIn:
                intent = new Intent(this, SignInActivity.class);
                break;
        }
        startActivity(intent);

    }
}
