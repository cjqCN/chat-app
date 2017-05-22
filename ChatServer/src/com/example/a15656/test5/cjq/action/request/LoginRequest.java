package com.example.a15656.test5.cjq.action.request;

/**
 * Created by 15656 on 2017/4/18.
 */
public class LoginRequest extends RequestImpl {
   
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

    LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
        setRequestType(RequestType.login);
    }
    public static LoginRequest createLoginRequest(String username,String password){
        return new LoginRequest(username,password);
    }
    public String toString(){
    	return "RequestType:"+getRequestType()+"   username:"+getUsername()+"   password:"+getPassword();
    }

}
