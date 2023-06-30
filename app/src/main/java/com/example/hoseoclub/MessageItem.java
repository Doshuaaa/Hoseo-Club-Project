package com.example.hoseoclub;

import java.io.Serializable;
import java.util.Comparator;

public class MessageItem {

    String receiver;
    String message;
    Object time;

    String interlocutor;

    int flag;

    public MessageItem(String message, Object time, int flag) {

        // send message = 0, receive message = 1
        this.message = message;
        this.time = time;
        this.flag = flag;
    }


    public MessageItem(String interlocutor, String message, Object time, int flag) {
        this.interlocutor = interlocutor;
        this.message = message;
        this.time = time;
        this.flag = flag;
    }

    public MessageItem() {

    }


    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}

class MessageTimeComparator implements  Comparator<MessageItem> {
    @Override
    public int compare(MessageItem o1, MessageItem o2) {

        if( (int) o1.time < (int) o2.time) {
            return 1;
        }

        else if( (int) o1.time > (int) o2.time) {
            return -1;
        }
        else{
        return 0;
        }
    }
}
