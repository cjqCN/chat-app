package com.example.a15656.test5.cjq.action.response;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjq.util.JsonUtil;
import com.example.a15656.test5.cjq.action.request.AddFriendRequest;
import com.example.a15656.test5.cjq.action.request.LoginRequest;
import com.example.a15656.test5.cjq.action.request.RegisterRequest;
import com.example.a15656.test5.cjq.action.request.RequestImpl;

public class ResponseBuilder {
	 private static ResponseBuilder responseBuilder ;
	 private static JsonUtil Jsonutil;
	 
	 public static ResponseBuilder getInstance(){
	        if(responseBuilder==null) {
	        	responseBuilder = new ResponseBuilder();
	        	Jsonutil = JsonUtil.getInstance();
	        }
	       return responseBuilder;
	  }
	 
	 public ResponseImpl createResponse(RequestImpl re){
		switch (re.getRequestType()) {
		case login:
			return createLoginResponse((LoginRequest)re);
		case register:
		    return createRegisterResponse((RegisterRequest)re);
		case send:
		    return new SendResponse();
		case addFriend:
		    return createAddFriendResponse((AddFriendRequest)re);
		default:
			break;
		}
		return null;
	 }
	 
	 private LoginResponse createLoginResponse(LoginRequest re) {
		// TODO Auto-generated method stub
		String username = re.getUsername();
		String password = re.getPassword();
        int res = Jsonutil.userlogin(username, password);
        		  
        switch (res) {
		case 0:
			return  new LoginResponse(LoginType.errpassword);
		case 1:	
			return  new LoginResponse(LoginType.permit,Jsonutil.getUserFriends(username));
		case 2:
			return  new LoginResponse(LoginType.permit,Jsonutil.getUserFriends(username));
		//	return  new LoginResponse(LoginType.onlined);
		default:
			break;
		}
        return null;
	 }
	 
	 private AddFriendResponse createAddFriendResponse(AddFriendRequest re){
		 String username = re.getUserName();
		 String afname = re.getAddfname();
		 int result = JsonUtil.getInstance().addFriends(username, afname); 
		 switch (result) {
		case 0:
			 return new AddFriendResponse(0,afname);	
		case 1:
			 return new AddFriendResponse(1,afname);
		case 2:
			 return new AddFriendResponse(2,afname);
		}
		 return new AddFriendResponse(0,afname);
	 }
	 

	 
	private RegisterResponse createRegisterResponse(RegisterRequest re){
		String username = re.getUsername();
		String password = re.getPassword();
		int res = Jsonutil.userRegister(username, password);
		  switch (res) {
			case 0:
				return  new RegisterResponse(RegsiterType.refused);
			case 1:	
				return  new RegisterResponse(RegsiterType.permit);
			default:
				break;
		  }
		  return null;
	}
}