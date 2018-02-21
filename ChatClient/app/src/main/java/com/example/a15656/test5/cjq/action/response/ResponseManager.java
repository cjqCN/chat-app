package com.example.a15656.test5.cjq.action.response;

import android.os.Bundle;
import android.os.Message;

import com.example.a15656.test5.cjq.interfacelayer.AddFriendActivty;
import com.example.a15656.test5.cjq.interfacelayer.ChatActivity;
import com.example.a15656.test5.cjq.interfacelayer.LoginActivity;
import com.example.a15656.test5.cjq.interfacelayer.MainActivity;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 15656 on 2017/4/12.
 */

public class ResponseManager {
    private static ResponseManager responseManager ;
    private  LinkedBlockingQueue  responseList;

    public static ResponseManager getInstance(){
        if(responseManager==null) {
            responseManager = new ResponseManager();
            responseManager.responseList = new LinkedBlockingQueue<ResponseImpl>();
        }
        return responseManager;
    }

    public synchronized ResponseImpl getResponse(){

        ResponseImpl re = null;
        try{
            re =(ResponseImpl) responseList.take();
        }catch (Exception ex){
        }
        return re;
    }

    public synchronized void addResponse(ResponseImpl rs){
        System.out.println(rs.getClass());
        System.out.println(rs.toString());
        if(rs instanceof LoginResponse) {
            Message message = new Message();
            LoginResponse loginRs = (LoginResponse) rs;
            if(loginRs.getLoginType()==LoginType.permit){
                message.what = 1;
                MainActivity.setFriendList(loginRs.getFriendList());
            }else if(loginRs.getLoginType()==LoginType.errpassword){
                message.what = 0;
            }else if(loginRs.getLoginType()==LoginType.onlined) {
                message.what = 2;
            }
            LoginActivity.loginActivity.loginHandler.sendMessage(message);  //结果回传
        }else if(rs instanceof RegisterResponse){
            Message message = new Message();
            RegisterResponse registerRe = (RegisterResponse) rs;
            if(registerRe.getRegisterType()==RegsiterType.permit){
                message.what = 1;
            }else if(registerRe.getRegisterType()==RegsiterType.refused){
                message.what = 0;
            }
            LoginActivity.loginActivity.registerHandler.sendMessage(message);  //结果回传
        }else if(rs instanceof SendResponse){
            ChatActivity.chatActivity.getCurrentChatFragment().sendSuccessHandler.sendMessage(new Message());
        }else if(rs instanceof ChatMessage){
            ChatMessage crs = (ChatMessage)rs;
            ChatActivity.receivedToChatFragment(crs.getSender(),crs.getContext());
        }
        else if(rs instanceof AddFriendResponse){
            AddFriendResponse crs = (AddFriendResponse)rs;
            Message msg = new Message();
            Bundle bundle  = new Bundle();
            bundle.putString("tip",crs.toString());
            bundle.putString("fname",crs.getFname());
            msg.setData(bundle);
            AddFriendActivty.addFriendActivty.toTipHandler.sendMessage(msg);
        }

    }

}
