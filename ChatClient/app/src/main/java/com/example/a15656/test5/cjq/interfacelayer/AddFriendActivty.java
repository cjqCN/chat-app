package com.example.a15656.test5.cjq.interfacelayer;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.action.request.AddFriendRequest;
import com.example.a15656.test5.cjq.action.request.RequestManager;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.ContactFragment;
import com.example.a15656.test5.cjq.interfacelayer.Fragment.MsgFragment;

import org.w3c.dom.Text;

public class AddFriendActivty extends AppCompatActivity {

    public static AddFriendActivty addFriendActivty;
    private Button rebutton;
    private Button addfbutton;
    private TextView fnametv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_activty);
        addFriendActivty = this;
        rebutton = (Button)findViewById(R.id.return_bn2);
        addfbutton = (Button)findViewById(R.id.addFButton);
        fnametv = (TextView)findViewById(R.id.addfnametv);
        rebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName comp = new ComponentName(AddFriendActivty.this,MainActivity.class);
                intent.setComponent(comp);
                startActivity(intent);
            }
        });
        addfbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addfname = fnametv.getText().toString();
                RequestManager.getInstance().sendMsg(new AddFriendRequest(MainActivity.userName,addfname));
            }
        });
    }
    //获取结果Handler
    public Handler toTipHandler = new Handler(){
        public void handleMessage(Message msg){
         Bundle bundle = msg.getData();
         String res =bundle.getString("tip");
            Toast.makeText(AddFriendActivty.this,
                    res,
                    Toast.LENGTH_SHORT).show();
            String fname =bundle.getString("fname");
            if(fname != null){
                ContactFragment.getinstance().addItems(fname);
            }
        }


    };
}
