package com.example.a15656.test5.cjq.interfacelayer.Item;

/**
 * Created by 15656 on 2017/4/26.
 */
public class FriendListItem {

    private int avatar_img;
    private String nickname;
    private String friend_info;


    public FriendListItem(int avatar_img, String nickname, String friend_info) {
        this.avatar_img = avatar_img;
        this.nickname = nickname;
        this.friend_info = friend_info;
    }

    public int getAvatar_img() {
        return avatar_img;
    }

    public void setAvatar_img(int avatar_img) {
        this.avatar_img = avatar_img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFriend_info() {
        return friend_info;
    }

    public void setFriend_info(String friend_info) {
        this.friend_info = friend_info;
    }

}
