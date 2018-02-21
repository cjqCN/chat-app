package com.example.a15656.test5.cjq.interfacelayer.Fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.action.pojo.UserState;
import com.example.a15656.test5.cjq.interfacelayer.Adapter.FriendListAdapter;
import com.example.a15656.test5.cjq.interfacelayer.AddFriendActivty;
import com.example.a15656.test5.cjq.interfacelayer.Item.FriendListItem;
import com.example.a15656.test5.cjq.interfacelayer.Item.MsgListItem;
import com.example.a15656.test5.cjq.interfacelayer.MainActivity;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by 15656 on 2017/4/26.
 */
public class ContactFragment extends Fragment {

    private View view;
    private List<FriendListItem> list;
    private FriendListAdapter friendListAdapter;
    private ListView listView;


    private static ContactFragment instance;
    private static List<UserState> userFriendList;

    public static ContactFragment getinstance(List<UserState> userFriendlist){
        if(instance == null) {
            instance = new ContactFragment();
            userFriendList = userFriendlist;
        }
        return instance;
    }

    public static ContactFragment getinstance(){
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_contact_fragment,null);
        setTitle();

        list = new ArrayList<FriendListItem>();
        listView = (ListView) view.findViewById(R.id.friend_list);
        friendListAdapter = new FriendListAdapter(getActivity(),R.id.friend_list,list);

        initFriendList(userFriendList);
        return view;
    }

    void setTitle(){
        TextView title = (TextView) view.findViewById(R.id.title_tv);
        Button button = (Button) view.findViewById(R.id.header_imgbtn);
        title.setText("联系人");
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setText("添加");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MainActivity.mainActivity.toAddFriendHandler.sendMessage(new Message());
            }
        });
    }

    void InitFrendList(){

        list = new ArrayList<FriendListItem>();
        listView = (ListView) view.findViewById(R.id.friend_list);

        friendListAdapter = new FriendListAdapter(getActivity(),R.id.friend_list,list);
        FriendListItem friendListItem = new FriendListItem(R.mipmap.login,"cjq","[在线]");
        list.add(friendListItem);

        friendListItem = new FriendListItem(R.mipmap.login,"张宝宝","[在线]");
        list.add(friendListItem);

        friendListItem = new FriendListItem(R.mipmap.login,"肚腩儿","[在线]");
        list.add(friendListItem);

        friendListItem = new FriendListItem(R.mipmap.login,"ASD","[离线]");
        list.add(friendListItem);

        listView.setAdapter(friendListAdapter);
    }

   public void  initFriendList(List<UserState> userFriendList){

       if(userFriendList == null)  return;
        int size = userFriendList.size();
       FriendListItem friendListItem;
       for(int i = 0;i<size;i++){
            UserState temp = userFriendList.get(i);
            String state = temp.getState()==1?"[在线]":"[离线]";
            friendListItem = new FriendListItem(R.mipmap.login,temp.getUserName(),state);
            list.add(friendListItem);
        }
       listView.setAdapter(friendListAdapter);
    }

    public void  addItems(String fname){
        for(FriendListItem temp :list){
            if(temp.getNickname().equals(fname))
                return;
        }
        FriendListItem friendListItem =  new FriendListItem(R.mipmap.login,fname,"离线");
        list.add(friendListItem);
        friendListAdapter.notifyDataSetChanged();
    }
}
