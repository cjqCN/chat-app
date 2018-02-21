package com.example.a15656.test5.cjq.action.request;

import com.example.a15656.test5.cjq.action.impl.ActionImpl;

/**
 * Created by 15656 on 2017/4/12.
 */



public class RequestImpl extends ActionImpl{
    protected RequestType requestType;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }


}
