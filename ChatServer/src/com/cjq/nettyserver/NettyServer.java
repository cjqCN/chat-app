package com.cjq.nettyserver;

import org.apache.log4j.Logger;

import com.cjq.nettyserver.impl.AbstractSever;
import com.cjq.serverhandler.SubscribeServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class NettyServer implements AbstractSever{

	@Override
	public void bind(int port) throws InterruptedException {
		// TODO Auto-generated method stub
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		ServerBootstrap  b = new ServerBootstrap();
		try{
		b.group(bossGroup, workerGroup)
    	.channel(NioServerSocketChannel.class)
    	.option(ChannelOption.SO_BACKLOG, 1024)
    	.childOption(ChannelOption.SO_KEEPALIVE, true)
    	.handler(new LoggingHandler(LogLevel.INFO))
       	.childHandler(new ChildChannelHandler());
		
		//绑定端口，同步等待成功
		ChannelFuture f = b.bind(port).sync();			
		
		f.channel().closeFuture().sync();
					
		} finally {
			//退出，释放线程池
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}	
	}
   
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			Logger.getRootLogger().info("客户端接入.......");	        
	        ch.pipeline().addLast(new ObjectEncoder());
            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
	        ch.pipeline().addLast(new SubscribeServerHandler());	
		}
	}
	
}
