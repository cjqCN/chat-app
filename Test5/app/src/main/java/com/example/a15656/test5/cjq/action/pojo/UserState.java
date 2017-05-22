package com.example.a15656.test5.cjq.action.pojo;

import java.io.Serializable;

/**
 * Created by 15656 on 2017/5/1.
 */

public class UserState implements Serializable {

    private String userName;
    private int state;  //0表示离线    1表示在线

    public UserState(String userName){
        this.setUserName(userName);
    }

    public UserState(String userName,int state){
        this.setUserName(userName);
        this.setState(state);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public String toString(){
        return userName+":"+(state==1?"在线":"离线");
    }
}
