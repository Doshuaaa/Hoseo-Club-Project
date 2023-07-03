package com.example.hoseoclub;

import java.util.Comparator;

public class MessageItem {

    String receiver;
    String message;



    Object time;
    long longTime;

    String interlocutor;

    int flag;



    public MessageItem(String message, Object time, int flag) {
        this.message = message;
        this.time = time;
        this.flag = flag;
    }

    public MessageItem(String message, long time, int flag) {
        this.message = message;
        longTime = time;
        this.flag = flag;
    }



    public MessageItem(String interlocutor, String message, long time, int flag) {
        this.interlocutor = interlocutor;
        this.message = message;
        this.longTime = time;
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

    public long getLongTime() {
        return longTime;
    }

    public void setLongTime(long longTime) {
        this.longTime = longTime;
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

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

}

class MessageTimeComparator implements  Comparator<MessageItem> {
    @Override
    public int compare(MessageItem o1, MessageItem o2) {

        Long a = o1.longTime;
        Long b = o2.longTime;
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
