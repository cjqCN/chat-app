package com.example.a15656.test5.cjq.interfacelayer.Fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.a15656.test5.R;
import com.example.a15656.test5.cjq.action.request.LoginRequest;
import com.example.a15656.test5.cjq.action.request.RequestManager;
import com.example.a15656.test5.cjq.action.request.SendRequest;
import com.example.a15656.test5.cjq.interfacelayer.Adapter.ChatAdapter;
import com.example.a15656.test5.cjq.interfacelayer.ChatActivity;
import com.example.a15656.test5.cjq.interfacelayer.Item.ChatModel;
import com.example.a15656.test5.cjq.interfacelayer.Item.ItemModel;
import com.example.a15656.test5.cjq.interfacelayer.MainActivity;

import java.util.ArrayList;



public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText et;
    private TextView tvSend;
    private String content;
    private Button rebutton;
    private String fname;
    ArrayList<ItemModel> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,null);
        et = (EditText) view.findViewById(R.id.et);
        tvSend = (TextView) view.findViewById(R.id.tvSend);
        rebutton = (Button) view.findViewById(R.id.return_bn);
        recyclerView = (RecyclerView)view.findViewById(R.id.recylerView);

        rebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.chatActivity.toMainHandler.sendMessage(new Message());
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new ChatAdapter());

      //   adapter.replaceAll(TestData.getTestAdData());
        adapter.replaceAll(data);
        initData();

        TextView frendnametv = (TextView) view.findViewById(R.id.frendname);
        frendnametv.setText(fname);

        return view;
    }

    //获取结果Handler
    public Handler sendSuccessHandler = new Handler(){
        public void handleMessage(Message msg){
            ChatModel model = new ChatModel();
            model.setContent(content);
            data.add(new ItemModel(ItemModel.CHAT_B, model));
            adapter.replaceAll(data);
            System.out.println(content);
            et.setText("");
            hideKeyBorad(et);

        }

    };

    //接受信息
    public Handler receivedHandler = new Handler(){
        public void handleMessage(Message msg){
            Bundle bundle = msg.getData();
            String mes = bundle.getString("context");
            ChatModel model = new ChatModel();
            model.setContent(mes);
            data.add(new ItemModel(ItemModel.CHAT_A, model));
            adapter.replaceAll(data);
        }

    };

    private void initData() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                RequestManager.getInstance().sendMsg(new SendRequest(MainActivity.userName,fname,content));
//                ChatModel model = new ChatModel();
//                model.setContent(content);
//                data.add(new ItemModel(ItemModel.CHAT_B, model));
//                adapter.replaceAll(data);
//                System.out.println(content);
//                et.setText("");
//                hideKeyBorad(et);
            }
        });

    }


    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    public void setFname(String fname){
        this.fname = fname;
    }
}
