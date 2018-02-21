package com.example.a15656.test5.cjq.action.request;

public class AddFriendRequest extends RequestImpl {

	private String sendName;
	private String addfname;

	public AddFriendRequest(String sendName,String addfname){

		this.sendName = sendName;
		this.addfname = addfname;
		setRequestType(RequestType.addFriend);
	}

	public String getUserName(){
		return sendName;
	}

	public String getAddfname(){
		return addfname;
	}

	public String toString(){
		return sendName+" 请求添加好友："+addfname;
	}

}