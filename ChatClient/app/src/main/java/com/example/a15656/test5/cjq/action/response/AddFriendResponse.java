package com.example.a15656.test5.cjq.action.response;

public class AddFriendResponse extends ResponseImpl{

	private int state;  // 0 为添加失败，1为添加成功。
	private String fname;
	public AddFriendResponse(int state,String fname){
		this.state = state;
		this.fname = fname;
	}
	public String getFname(){
		return fname;
	}

	public String toString(){
		return (state == 0?"添加失败":state == 1?"添加成功":"已存在好友");
	}

}
