package com.example.a15656.test5.cjq.interfacelayer.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.interfacelayer.Adapter.MsgListAdapter;
import com.example.a15656.test5.cjq.interfacelayer.Item.MsgListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15656 on 2017/4/26.
 */
public class MsgFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private List<MsgListItem> list;
    private MsgListAdapter msgListAdapter;
    private ListView listView;
    private SwipeRefreshLayout swipeContainer;//下拉刷新

    private static MsgFragment instance;

    private static Map<String,ChatFragment> chatwinmap = new HashMap<String,ChatFragment>();

    public static MsgFragment getinstance(){
        if(instance == null) {
            instance = new MsgFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_msg_fragment,container,false);
        initData();
        setTitle();
        return view;
    }

    void setTitle(){
        TextView title = (TextView) view.findViewById(R.id.title_tv);
        title.setText("消息");
        Button button = (Button) view.findViewById(R.id.header_imgbtn);
        button.setBackgroundResource(R.mipmap.header_btn_more);
        button.setText("");
    }
    void initData(){

        listView = (ListView) view.findViewById(R.id.msg_list);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        list = new ArrayList<MsgListItem>();
        msgListAdapter = new MsgListAdapter(getActivity(),R.id.msg_list,list);

//        MsgListItem msgListItem = new MsgListItem(R.mipmap.login,"jcq","helloworld!","21:32","4");
//        list.add(msgListItem);
//
//        msgListItem = new MsgListItem(R.mipmap.login,"肚腩儿","不错哈","13:03","99+");
//        list.add(msgListItem);
//
//        msgListItem = new MsgListItem(R.mipmap.login,"肥猪","约吗？","20:03","2");
//        list.add(msgListItem);

        listView.setAdapter(msgListAdapter);
    }

    public void  addItems(String fname){
    for(MsgListItem temp :list){
        if(temp.getNickname().equals(fname))
            return;
    }
        MsgListItem msgListItem =  new MsgListItem(R.mipmap.login,fname,"","","0");
        list.add(msgListItem);
        msgListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
 //               addItems();
                msgListAdapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        }, 1000);
    }
}
