package com.lemon.piece.dodamdodam.category;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.lemon.piece.dodamdodam.R;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    ArrayList<String> array = new ArrayList<>();
    ListView lv;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        lv = findViewById(R.id.chat_list);
        editText = findViewById(R.id.chat_edit);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, array);
        lv.setAdapter(adapter);

        ImageButton btn = findViewById(R.id.chat_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                array.add(editText.getText().toString());
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });


    }
}
