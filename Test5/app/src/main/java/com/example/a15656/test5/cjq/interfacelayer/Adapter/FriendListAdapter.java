package com.example.a15656.test5.cjq.interfacelayer.Adapter;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.interfacelayer.CustomerView.AvatarImageView;
import com.example.a15656.test5.cjq.interfacelayer.Item.FriendListItem;
import com.example.a15656.test5.cjq.interfacelayer.Item.MsgListItem;
import com.example.a15656.test5.cjq.interfacelayer.MainActivity;

import java.util.List;

/**
 * Created by 15656 on 2017/4/26.
 */

public class FriendListAdapter extends ArrayAdapter<FriendListItem> {
    Context context;
    public FriendListAdapter(Context context, int resource, List<FriendListItem> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FriendListItem friendListItem = getItem(position);
        View view;
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){
            view = View.inflate(context, R.layout.friend_list_item,null);
            viewHolder.avatarImageView = (AvatarImageView) view.findViewById(R.id.friend_avatar);
            viewHolder.nickname = (TextView) view.findViewById(R.id.friend_nickname);
            viewHolder.friend_info = (TextView) view.findViewById(R.id.friend_info);
            view.setTag(viewHolder);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String fname = ((TextView)v.findViewById(R.id.friend_nickname)).getText().toString();
                    Message msg =  new Message();
                    msg.obj = fname;
                    MainActivity.mainActivity.toChatHandler.sendMessage(msg);
                }
            });
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        setViewData(viewHolder,friendListItem);
        return view;
    }
    void setViewData(ViewHolder viewHolder,FriendListItem friendListItem) {
        viewHolder.avatarImageView.setImageResource(friendListItem.getAvatar_img());
        viewHolder.nickname.setText(friendListItem.getNickname());
        viewHolder.friend_info.setText(friendListItem.getFriend_info());
    }
    class ViewHolder{
        AvatarImageView avatarImageView;
        TextView nickname;
        TextView friend_info;
    }
}
