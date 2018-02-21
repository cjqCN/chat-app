package com.example.a15656.test5.cjq.action.response;

/**
 * Created by 15656 on 2017/5/1.
 */

enum SendType{
	success,
	failure	
}

public class SendResponse extends ResponseImpl{
	
	private SendType sendType;
	
	SendResponse(){
		sendType = SendType.success;
	}
}
