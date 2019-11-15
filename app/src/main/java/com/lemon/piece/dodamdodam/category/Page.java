package com.lemon.piece.dodamdodam.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lemon.piece.dodamdodam.R;

public class Page extends android.support.v4.app.Fragment {
    String text;
    public void setText(String s){
        text = s;
    }

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relativeLayout=(RelativeLayout) inflater.inflate(R.layout.community_item,container,false);

        TextView page_num=(TextView)relativeLayout.findViewById(R.id.community_text);
        page_num.setText(text);

        return relativeLayout;
    }

}
