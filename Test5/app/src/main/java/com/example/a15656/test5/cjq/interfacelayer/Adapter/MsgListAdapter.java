package com.example.a15656.test5.cjq.interfacelayer.Adapter;

import android.content.Context;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.interfacelayer.CustomerView.AvatarImageView;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.ChatFragment;
import com.example.a15656.test5.cjq.interfacelayer.Item.MsgListItem;
import com.example.a15656.test5.cjq.interfacelayer.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 15656 on 2017/4/26.
 */

public class MsgListAdapter extends ArrayAdapter<MsgListItem> {

    private static Map<String,ChatFragment> chatFragmentMap = new HashMap<String,ChatFragment>();
    private static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Context context;

    public MsgListAdapter(Context context, int resource, List<MsgListItem> objects) {
        super(context, resource, objects);
        this.context = context;
 //     fragmentManager = MsgFragment.getinstance().getFragmentManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgListItem msgListItem = getItem(position);
        View view;
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){
            view = View.inflate(context, R.layout.msg_list_item,null);
            viewHolder.avatarImageView = (AvatarImageView) view.findViewById(R.id.friend_avatar);
            viewHolder.nickname = (TextView) view.findViewById(R.id.friend_nickname);
            viewHolder.msg_info = (TextView) view.findViewById(R.id.msg_info);
            viewHolder.msg_num = (TextView) view.findViewById(R.id.msg_number);
            viewHolder.time = (TextView) view.findViewById(R.id.msg_time);
            view.setTag(viewHolder);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String fname = ((TextView)v.findViewById(R.id.friend_nickname)).getText().toString();
                    Message msg =  new Message();
                    msg.obj = fname;
                    MainActivity.mainActivity.toChatHandler.sendMessage(msg);

//                    fragmentManager = MainActivity.getfragmentManager();
//                    fragmentTransaction = fragmentManager.beginTransaction();
//
//                    String fname = ((TextView)v.findViewById(R.id.friend_nickname)).getText().toString();
//                    ChatFragment temp =  chatFragmentMap.get(fname);
//
//                    if(temp == null){
//                        temp = new ChatFragment();
//                        temp.setFname(fname);
//                        fragmentTransaction.add(R.id.fragment,temp, fname);
//                    }else{
//                        fragmentTransaction.show(temp);
//                    }
//                    fragmentTransaction.commit();
                }
            });
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        setViewData(viewHolder,msgListItem);

        return view;
    }
    void setViewData(ViewHolder viewHolder,MsgListItem msgListItem) {
        viewHolder.avatarImageView.setImageResource(msgListItem.getAvatar_img());
        viewHolder.nickname.setText(msgListItem.getNickname());
        viewHolder.msg_num.setText(msgListItem.getMsg_number());
        viewHolder.msg_info.setText(msgListItem.getMsg_info());
        viewHolder.time.setText(msgListItem.getTime());
    }
    class ViewHolder{
        AvatarImageView avatarImageView;
        TextView nickname;
        TextView msg_info;
        TextView time;
        TextView msg_num;
    }
}
