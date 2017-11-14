package com.example.quan.english.fragment.menu.chat;

public class Chat {
    private Boolean me;
    private String content;

    public Chat(boolean me, String content) {
        this.me = me;
        this.content = content;
    }

    public boolean getMe() {
        return me;
    }

    public String getContent() {
        return content;
    }
}
