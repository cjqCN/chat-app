package com.example.a15656.test5.cjq.action.request;

/**
 * Created by 15656 on 2017/4/18.
 */
public class RegisterRequest extends RequestImpl {

    private String username ;
    private String password ;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    RegisterRequest(String username, String password){
        this.username = username;
        this.password = password;
        setRequestType(RequestType.register);
    }
    public static RegisterRequest createRequest(String username,String password){
        return new RegisterRequest(username,password);
    }
    public String toString(){
        return "RequestType:"+getRequestType()+"   username:"+getUsername()+"   password:"+getPassword();
    }

}