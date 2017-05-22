package com.example.a15656.test5.cjq.action.response;

import java.util.Date;

/**
 * Created by 15656 on 2017/5/1.
 */
public class ChatMessage  extends ResponseImpl {
		
	private String sender;
	private String context;
	private String time;
	
	public ChatMessage(String sender,String context){
		this.sender = sender;
		this.context = context;
		this.time = new Date().toString();	
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getContext(){
		return context;
	}
	
	public String getTime(){
		return time;
	}

	@Override
	public String toString(){
		return getTime()+": "+getSender()+":"+getContext();
	}
	
	

}
