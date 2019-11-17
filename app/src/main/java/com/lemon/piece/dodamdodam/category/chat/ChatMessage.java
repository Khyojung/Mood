package com.lemon.piece.dodamdodam.category.chat;

/**
 * Created by hyojung on 2019-11-15.
 */

public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
