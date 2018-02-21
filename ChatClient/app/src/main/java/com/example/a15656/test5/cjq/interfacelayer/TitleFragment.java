package com.example.a15656.test5.cjq.interfacelayer;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.a15656.test5.R;

public class TitleFragment extends Fragment
{

    private ImageButton mLeftMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_chat_title, container, false);
        return view;
    }
}
