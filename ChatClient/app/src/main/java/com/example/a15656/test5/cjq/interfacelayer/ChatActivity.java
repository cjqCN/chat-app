package com.example.a15656.test5.cjq.interfacelayer;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.ChatFragment;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    public static ChatActivity chatActivity;

    private static FragmentManager fragmentManager ;
    private static Map<String,ChatFragment> chatFragmentMap = new HashMap<String,ChatFragment>(); ;
    private static ChatFragment currentChatFragment;

    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatActivity = this;
        setContentView(R.layout.activity_chat);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String fname = bundle.getString("fname");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ChatFragment temp =chatFragmentMap.get(fname);
        if(temp == null) {
            temp =  new ChatFragment();
            chatFragmentMap.put(fname,temp);
            temp.setFname(fname);
        }
        fragmentTransaction.add(R.id.chatContext, temp,fname);
        currentChatFragment = temp;
        fragmentTransaction.commit();
    }

    //获取结果Handler
    public Handler toMainHandler = new Handler(){
        public void handleMessage(Message msg){
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(ChatActivity.this,MainActivity.class);
            intent.setComponent(comp);
            startActivity(intent);
        }

    };

    public ChatFragment getCurrentChatFragment(){
        return currentChatFragment;
    }

//
//    static Handler createChatFragmet = new Handler(){
//        @Override
//        public void handleMessage(Message msg){
//            Bundle data = msg.getData();
//            String fname = data.getString("fname");
//            ChatFragment fchatFragment = new ChatFragment();
//            chatFragmentMap.put(fname,fchatFragment);
//            fchatFragment.setFname(fname);
//            fchatFragment.receivedHandler.sendMessage(msg);
//        }
//
//    };

    public static void receivedToChatFragment(String senderName,String context){
        if(chatFragmentMap == null) return;
        ChatFragment fchatFragment = chatFragmentMap.get(senderName);
        Message message = new Message();
        Bundle data = new Bundle();
        data.putString("context",context);
        data.putString("fname",senderName);
        message.setData(data);
        if(fchatFragment == null) {
            return ;
            //createChatFragmet.sendMessage(message);
        }else{
            fchatFragment.receivedHandler.sendMessage(message);
        }
    }

    public static Map<String,ChatFragment> getChatFragmentMap(){
        return chatFragmentMap;
    }

}
