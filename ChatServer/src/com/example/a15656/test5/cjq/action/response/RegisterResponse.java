package com.example.a15656.test5.cjq.action.response;

import com.example.a15656.test5.cjq.action.request.LoginRequest;

enum RegsiterType{
	permit,
	refused	
}

public class RegisterResponse extends ResponseImpl {

    private RegsiterType regsiterType;
    RegisterResponse(RegsiterType regsiterType){
        this.regsiterType = regsiterType;
    }
    public RegsiterType getRegisterType(){return regsiterType;}
    public String toString(){return regsiterType.toString();}
}

