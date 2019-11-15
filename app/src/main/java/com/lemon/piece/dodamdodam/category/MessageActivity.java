package com.lemon.piece.dodamdodam.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lemon.piece.dodamdodam.R;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {


    ArrayList<TextView> mandarin_text = new ArrayList<>();
    ArrayList<String> mandarin_array = new ArrayList<>();
    ScrollView sv;
    LinearLayout layout;
    EditText editText;

    String quest = "quest";
    int child_count = 0;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        makeMandarinText();

        sv = findViewById(R.id.chat_list);
        layout = findViewById(R.id.chat_layout);
        //sv.addView(layout);
        editText = findViewById(R.id.chat_edit);
        count = 0;

        ArrayAdapter<String> user_array = new ArrayAdapter<String>(this, R.layout.list_item_mandarin);

        ImageButton btn = findViewById(R.id.chat_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                View user_view = View.inflate(MessageActivity.this, R.layout.list_item_mandarin, null);
                //TextView user_temp = user_view.findViewById(R.id.user_text);
                TextView user_temp = new TextView(MessageActivity.this);
                user_temp.setBackgroundResource(R.drawable.me_);
                user_temp.setText(editText.getText().toString());
                layout.addView(user_temp, child_count);
                child_count++;

                editText.setText("");
                layout.addView(mandarin_text.get(count), child_count);
                child_count++;

                count++;

            }
        });


    }
    public void makeMandarinText(){

        View mandarin_view = View.inflate(this, R.layout.list_item_mandarin, null);

        for(int i = 0; i < 9; i++){
            TextView mandarin = new TextView(this);
            mandarin.setBackgroundResource(R.drawable.mandarin_1);
            mandarin.setText(getString(getResources().getIdentifier((quest + i), "string", getPackageName())));

            mandarin_text.add(mandarin);
        }
    }
}
