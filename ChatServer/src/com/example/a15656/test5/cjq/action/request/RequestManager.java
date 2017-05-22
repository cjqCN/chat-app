package com.example.a15656.test5.cjq.action.request;

import com.example.a15656.test5.cjq.action.impl.ActionImpl;
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

         }else{
            ctx.writeAndFlush(obj);
         }
    }

}
