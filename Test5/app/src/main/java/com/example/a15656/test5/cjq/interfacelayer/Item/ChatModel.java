package com.example.a15656.test5.cjq.interfacelayer.Item;

import java.io.Serializable;

/**
 * Created by WangChang on 2016/4/28.
 */
public class ChatModel implements Serializable {
    private String icon="";
    private String content="";
    private String type="";

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
