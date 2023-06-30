package com.example.hoseoclub;

import java.io.Serializable;
import java.util.Comparator;

public class MessageItem {

    String receiver;
    String message;
    long time;

    String interlocutor;

    int flag;

    public MessageItem(String message, Long time, int flag) {

        // send message = 0, receive message = 1
        this.message = message;
        this.time = time;
        this.flag = flag;
    }


    public MessageItem(String interlocutor, String message, long time, int flag) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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

        Long a = o1.time;
        Long b = o2.time;
        int a1 = a.intValue();
        int b1 = b.intValue();
        if( a1 < b1) {

            return 1;
        }

        else if( a1 > b1) {
            return -1;
        }
        else{
        return 0;
        }
    }
}
