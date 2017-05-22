package com.example.a15656.test5.cjq.client;

import android.os.Message;

import com.example.a15656.test5.cjq.interfacelayer.LoginActivity;
import com.example.a15656.test5.cjq.serverhandler.SubscribeServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


public class TestNettyClient extends INettyClient{

	private Channel ch = null;

	public TestNettyClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}
	
	public void connect()throws Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new NettyClientInitializer());
			try {
				ch = b.connect(host, port).sync().channel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				group.shutdownGracefully();
				b = null;
				Message msg = new Message();
				msg.what = 0;
				LoginActivity.loginActivity.connectSuorHandler.sendMessage(msg);
				return;
			}
			if (ch.isOpen()) {
				isConnectSuccess = true;
				Message msg = new Message();
				msg.what = 1;
				LoginActivity.loginActivity.connectSuorHandler.sendMessage(msg);
			} else {
			}
			ch.closeFuture().sync();
			
		} finally {
			group.shutdownGracefully();
		}	
	}

	private class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {

			ch.pipeline().addLast(new ObjectEncoder());
			ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
			ch.pipeline().addLast(new SubscribeServerHandler());
		}
	}
}
