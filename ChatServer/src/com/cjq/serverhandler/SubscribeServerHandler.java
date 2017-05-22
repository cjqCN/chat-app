package com.cjq.serverhandler;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cjq.serverhandler.impl.AbstractHandler;
import com.example.a15656.test5.cjq.action.pojo.UserState;
import com.example.a15656.test5.cjq.action.request.LoginRequest;
import com.example.a15656.test5.cjq.action.request.RegisterRequest;
import com.example.a15656.test5.cjq.action.request.AddFriendRequest;
import com.example.a15656.test5.cjq.action.request.RequestImpl;
import com.example.a15656.test5.cjq.action.request.SendRequest;
import com.example.a15656.test5.cjq.action.response.ChatMessage;
import com.example.a15656.test5.cjq.action.response.LoginResponse;
import com.example.a15656.test5.cjq.action.response.AddFriendResponse;
import com.example.a15656.test5.cjq.action.response.ResponseBuilder;
import com.example.a15656.test5.cjq.action.response.ResponseImpl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubscribeServerHandler extends ChannelHandlerAdapter implements AbstractHandler {
	
	private static Map<String,ChannelHandlerContext> clientOnLineMap = new HashMap<String,ChannelHandlerContext>();
	private static List<String> clientOnLineList = new LinkedList<String>();
	
	private String id;
	private static Logger logger = Logger.getLogger(SubscribeServerHandler.class);  
	public  SubscribeServerHandler() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
	  
	    if(msg instanceof RequestImpl){
	    	RequestImpl re = (RequestImpl)msg;
    		logger.info(re.getClass());
    		ResponseImpl builder = ResponseBuilder.getInstance().createResponse(re);
	    	if(msg instanceof LoginRequest){
	    		LoginResponse rs =	(LoginResponse)builder;
	    		if(rs.isPermitLogin()){
	    			id = ((LoginRequest)msg).getUsername();
	    			setOnLineInfo(id,ctx);
	    			setUserState(rs.getFriendList());
	    		}
	    		ctx.write(builder);
	    	}else if(msg instanceof RegisterRequest){
	    		ctx.write(builder);
	    	}else if(msg instanceof SendRequest){
	    		SendRequest sre = (SendRequest)msg;
	    		logger.info(sre);
	    		ctx.write(builder);
	    		ChannelHandlerContext fctx = clientOnLineMap.get(sre.getReceiverName());
	    		if(fctx!=null){		
	    		ChatMessage chatmessage = new ChatMessage(sre.getSenderName(), sre.getContext());	
	    		fctx.writeAndFlush(chatmessage);
	    		logger.info(chatmessage);
	    		}
	    	}else if(msg instanceof AddFriendRequest){
	    		logger.info(builder);
	    		ctx.write(builder);
	    	}
	    } 	    
	    return;
	    
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx){
		ctx.flush();
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		cause.printStackTrace();
		logger.warn(id+"  ¿Îœﬂ");
		clientOnLineMap.put(id, null);
		getClientOnLineList().remove(id);
		ctx.close();	
	}
	
	
	public static void printClentName(){
		if(clientOnLineMap!=null){
    		Iterator iter = clientOnLineMap.entrySet().iterator();
    		while(iter.hasNext()){
    			Map.Entry entry = (Map.Entry) iter.next();
    			logger.info(entry.getKey());
    			}
	    	}
	}

	public static List<String> getClientOnLineList() {
		return clientOnLineList;
	}

	public static void setClientOnLineList(List<String> clientOnLineList) {
		SubscribeServerHandler.clientOnLineList = clientOnLineList;
	}
	
	private void setOnLineInfo(String id,ChannelHandlerContext ctx){
		clientOnLineMap.put(id, ctx);
		getClientOnLineList().add(id);
		logger.info(id+" …œœﬂ");
	}
	
    private void setUserState(List<UserState> userFriendList){
    	if(userFriendList == null)  return;
    	int size = userFriendList.size();
    	for(int i = 0;i<size;i++){
    		UserState temp = userFriendList.get(i);
    		for(int j = 0;j<clientOnLineList.size();j++){
    			if(clientOnLineList.get(j).equals(temp.getUserName())){
    				temp.setState(1);
    			}
    		}
    	}   	
    }
}
