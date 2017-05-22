package com.example.a15656.test5.cjq.interfacelayer;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.action.pojo.UserState;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.ChatFragment;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.ContactFragment;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.MsgFragment;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.QzoneFragment;


import java.util.List;

/**
 * Created by 15656 on 2017/4/26.
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    public static String userName;

    private static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction ;
    private View mainView , slideView;
    private RadioButton msg_rbtn;
    private RadioGroup radioGroup;

    private static List<UserState> friendList;
    public static MainActivity mainActivity;

    public static List<UserState> getFriendList() {
        return friendList;
    }

    public static void setFriendList(List<UserState> friendlist) {
        friendList = friendlist;
    }

    public static FragmentManager getfragmentManager() {
        return fragmentManager;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //透明状态栏
 //       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
 //       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initData();
    }
    void initData(){
        msg_rbtn = (RadioButton) findViewById(R.id.msg_rbtn);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        msg_rbtn.setChecked(true);
        radioGroup.setOnCheckedChangeListener(this);
        fragmentTransaction.add(R.id.fragment, MsgFragment.getinstance(),"MsgFragment");
        fragmentTransaction.commit();

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        userName = bundle.getString("username");
        TextView userNameTv = (TextView)findViewById(R.id.username);
        userNameTv.setText(userName);

    }

    void switchFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commit();
    }


    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment msgFragment = fragmentManager.findFragmentByTag("MsgFragment");
        Fragment contactFragment = fragmentManager.findFragmentByTag("ContactFragment");
        Fragment qzoneFragment = fragmentManager.findFragmentByTag("QzoneFragment");
        if (msgFragment != null) {
            fragmentTransaction.hide(msgFragment);
        }
        if (contactFragment != null) {
            fragmentTransaction.hide(contactFragment);
        }
        if (qzoneFragment != null) {
            fragmentTransaction.hide(qzoneFragment);
        }

        switch (checkedId) {
            case R.id.msg_rbtn:
                if (msgFragment == null) {
                    msgFragment = MsgFragment.getinstance();
                    fragmentTransaction.add(R.id.fragment, msgFragment, "MsgFragment");
                } else {
                    fragmentTransaction.show(msgFragment);
                }
                break;
            case R.id.contact_rbtn:
                if (contactFragment == null) {
                    contactFragment =  ContactFragment.getinstance(friendList);
                    fragmentTransaction.add(R.id.fragment, contactFragment, "ContactFragment");
                } else {
                    fragmentTransaction.show(contactFragment);
                }
                break;
            case R.id.qzone_rbtn:
                if (qzoneFragment == null) {
                    qzoneFragment = QzoneFragment.getInstance();
               //     qzoneFragment = new ChatFragment();
                    fragmentTransaction.add(R.id.fragment, qzoneFragment, "QzoneFragment");
                } else {
                    fragmentTransaction.show(qzoneFragment);
                }
                break;

            default:
                break;
        }
        fragmentTransaction.commit();
    }

    //获取结果Handler
    public Handler toChatHandler = new Handler(){
        public void handleMessage(Message msg){
            MsgFragment.getinstance().addItems(msg.obj.toString());
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("fname",msg.obj.toString());
            intent.putExtras(bundle);
            ComponentName comp = new ComponentName(MainActivity.this,ChatActivity.class);
            intent.setComponent(comp);
            startActivity(intent);
        }

    };

    //获取结果Handler
    public Handler toAddFriendHandler = new Handler(){
        public void handleMessage(Message msg){

            Intent intent = new Intent();
            ComponentName comp = new ComponentName(MainActivity.this,AddFriendActivty.class);
            intent.setComponent(comp);
            startActivity(intent);
        }

    };


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton mTab = (RadioButton) radioGroup.getChildAt(i);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag((String) mTab.getTag());
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment != null) {
                if (!mTab.isChecked()) {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }
}


