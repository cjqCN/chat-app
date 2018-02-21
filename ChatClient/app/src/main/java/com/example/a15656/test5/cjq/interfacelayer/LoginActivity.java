package com.example.a15656.test5.cjq.interfacelayer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.action.request.LoginRequest;
import com.example.a15656.test5.cjq.action.request.RegisterRequest;
import com.example.a15656.test5.cjq.action.request.RequestManager;
import com.example.a15656.test5.cjq.clientstart.ClientStart;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static EditText username_inputbox;
    private static EditText password_inputbox;
    private static Button login_btn;
    private static Button register_btn;
    private View mProgressView;
    private View mLoginFormView;

    public static LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivity = this;
        init();
        ClientStart.getInstance().startClient();
    }

    /**
     * 初始化
     */
    private void init(){

        //初始化控件
        password_inputbox = (EditText) findViewById(R.id.password_inputbox);
        username_inputbox = (EditText) findViewById(R.id.username_inputbox);

        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        //添加添加监听
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String username = username_inputbox.getText().toString();
        String password = password_inputbox.getText().toString();
        switch (v.getId()){
            case  R.id.login_btn:
                if(!RequestManager.getInstance().isConnectSuccess()){
                    Toast.makeText(LoginActivity.this,
                            "连接服务器失败",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgress(true);
                RequestManager.getInstance().sendMsg(LoginRequest.createRequest(username,password));
                break;

            case  R.id.register_btn:
                if(!RequestManager.getInstance().isConnectSuccess()){
                    Toast.makeText(LoginActivity.this,
                            "连接服务器失败",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgress(true);
                RequestManager.getInstance().sendMsg(RegisterRequest.createRequest(username,password));

                break;
        }
    }



    //获取结果Handler
    public Handler loginHandler = new Handler(){
        public void handleMessage(Message msg){

            switch (msg.what){
                case 0:
                    showProgress(false);
                    Toast.makeText(LoginActivity.this,
                            "用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    showProgress(false);
                    Toast.makeText(LoginActivity.this,
                            "登录成功",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username_inputbox.getText().toString());
                    intent.putExtras(bundle);
                    ComponentName comp = new ComponentName(LoginActivity.this,MainActivity.class);
                    intent.setComponent(comp);
                    startActivity(intent);
                    break;
                case 2:
                    showProgress(false);
                    Toast.makeText(LoginActivity.this,
                            "此用户已在线",
                            Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    };

    public Handler showProgressHandler = new Handler(){
        public void handleMessage(Message msg){
            showProgress(true);
        }

    };

    public Handler registerHandler = new Handler(){
        public void handleMessage(Message msg){

            switch (msg.what){
                case 0:
                    Toast.makeText(LoginActivity.this,
                            "用户已存在",
                            Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this,
                            "注册成功",
                            Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    break;
            }
        }

    };

    public Handler connectSuorHandler = new Handler(){
        public void handleMessage(Message msg){

            switch (msg.what){
                case 0:
                    Toast.makeText(LoginActivity.this,
                            "连接服务器失败",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this,
                            "连接服务器成功",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
