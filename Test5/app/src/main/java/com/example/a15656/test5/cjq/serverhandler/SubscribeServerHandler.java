package com.example.a15656.test5.cjq.serverhandler;

import com.example.a15656.test5.cjq.action.request.RequestManager;
import com.example.a15656.test5.cjq.action.response.ResponseImpl;
import com.example.a15656.test5.cjq.action.response.ResponseManager;
import com.example.a15656.test5.cjq.serverhandler.impl.AbstractHandler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubscribeServerHandler extends ChannelHandlerAdapter implements AbstractHandler {
	
	public  SubscribeServerHandler() {
		// TODO Auto-generated constructor stub
		super();
	}

    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		RequestManager.getInstance().setChannel(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){

		if(msg instanceof ResponseImpl){
			ResponseManager.getInstance().addResponse((ResponseImpl)msg);
		}

	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		ctx.flush();
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){

		cause.printStackTrace();
		ctx.close();	
	}
}
