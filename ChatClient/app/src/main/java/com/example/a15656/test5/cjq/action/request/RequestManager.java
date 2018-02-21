package com.example.a15656.test5.cjq.action.request;

import android.os.Message;

import com.example.a15656.test5.cjq.action.impl.ActionImpl;
import com.example.a15656.test5.cjq.clientstart.ClientStart;
import com.example.a15656.test5.cjq.interfacelayer.LoginActivity;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by 15656 on 2017/4/12.
 */

public class RequestManager extends ActionImpl{
    private static RequestManager requestManager;
    private ChannelHandlerContext ctx;

    public static RequestManager getInstance(){
        if(requestManager==null)
            requestManager = new RequestManager();
        return requestManager;
    }
    public void setChannel(ChannelHandlerContext ctx) { this.ctx = ctx;}

    public synchronized void sendMsg(RequestImpl obj){
        if(ctx==null){
            Message msg = new Message();
            msg.what = 0;
            LoginActivity.loginActivity.connectSuorHandler.sendMessage(msg);
         }else{
            ctx.writeAndFlush(obj);
         }
    }

    public boolean isConnectSuccess(){
        if(ctx != null) return  true;
        else return false;
    }

}
