package com.example.a15656.test5.cjq.action.request;

import java.util.Date;

/**
 * Created by 15656 on 2017/5/1.
 */
public class SendRequest extends RequestImpl{

    private String senderName;
    private String receiverName;
    private String context;
    private String time;

    public SendRequest(String senderName, String receiverName, String context){
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.context = context;
        this.time = new Date().toString();
        setRequestType(RequestType.send);
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getContext() {
        return context;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString(){
        return getTime()+": "+getSenderName()+"对"
                +getReceiverName()+"说："+getContext();
    }
}
