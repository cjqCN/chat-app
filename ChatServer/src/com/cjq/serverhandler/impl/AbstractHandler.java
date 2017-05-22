package com.cjq.serverhandler.impl;

import io.netty.channel.ChannelHandlerContext;

public interface AbstractHandler {
	
	public void channelRead(ChannelHandlerContext ctx, Object msg);
	public void channelReadComplete(ChannelHandlerContext ctx);
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
}
