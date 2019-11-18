package com.lemon.piece.dodamdodam.category.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lemon.piece.dodamdodam.R;

import java.util.ArrayList;

/**
 * Created by hyojung on 2019-11-15.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String[] text_set = {"m","b","u","m","u","m","b","b","u","m","u","m","b","u","m","b","u","m","b","b","u","m","b","b","m","b","b","m","b","b"};

    // adapter에 들어갈 list 입니다.
    private ArrayList<ChatMessage> listData = new ArrayList<>();
    private TextView textView2;
    private TextView textView1;
    private TextView textView3;

    boolean test;
    public void setTest(boolean b){
        test = b;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        switch (viewType){
            case 0:
                View mandarin_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_mandarin, parent, false);
                return new MandarinViewHolder(mandarin_view);
            case 1:
                View me_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_message, parent, false);
                return new MeViewHolder(me_view);
            case 2:
                View mandarin_visible_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_mandarin_visible, parent, false);
                return new MandarinVisibleViewHolder(mandarin_visible_view);
        }
        return null;

    }
    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        int returnVal = 0;
        if(text_set[position].equals("u")){
            returnVal = 1;
        }else if(text_set[position].equals("b")){
            returnVal = 2;
        }
        return returnVal;
        //return position % 2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (this.getItemViewType(position)){
            case 0:
                textView1.setText(listData.get(position).getMessage());
                break;
            case 1:

                textView2.setText(listData.get(position).getMessage());
                break;
            case 2:

                textView3.setText(listData.get(position).getMessage());
                break;
        }

    }


    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(ChatMessage data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class MandarinViewHolder extends RecyclerView.ViewHolder {

        MandarinViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.chat_mandarin);
        }

        void onBind(ChatMessage data) {
            textView1.setText(data.getMessage());
        }
    }
    class MeViewHolder extends RecyclerView.ViewHolder {



        MeViewHolder(View itemView) {
            super(itemView);

            textView2 = itemView.findViewById(R.id.chat_me);
        }

        void onBind(ChatMessage data) {
            textView2.setText(data.getMessage());
        }
    }
    class MandarinVisibleViewHolder extends RecyclerView.ViewHolder {



        MandarinVisibleViewHolder(View itemView) {
            super(itemView);

            textView3 = itemView.findViewById(R.id.chat_mandarin_visible);
        }

        void onBind(ChatMessage data) {
            textView2.setText(data.getMessage());
        }
    }
}
