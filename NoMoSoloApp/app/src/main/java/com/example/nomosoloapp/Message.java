package com.example.nomosoloapp;

import java.util.Date;

public class Message {
    String fromUserId, toUserId, message;
    Date messageDate;

    public Message(String fromUserId, String toUserId, String message, Date messageDate) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.messageDate = messageDate;
    }

    public Message(String fromUserId, String toUserId, String message) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
    }

    public Message() {
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }
}
