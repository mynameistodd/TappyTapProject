package com.mynameistodd.tappytap;

import java.util.Date;

/**
 * Created by todd on 9/30/13.
 */
public class Message {
    private int id;
    private String sender;
    private String messageText;
    private String received;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public Message() {
    }

    public Message(int id, String sender, String messageText, String received) {
        this.id = id;
        this.sender = sender;
        this.messageText = messageText;
        this.received = received;
    }
}
