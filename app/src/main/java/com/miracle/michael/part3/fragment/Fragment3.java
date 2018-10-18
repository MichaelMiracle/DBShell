package com.miracle.michael.part3.fragment;

import android.content.Intent;
import android.view.View;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPageResult;
import com.hyphenate.exceptions.HyphenateException;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.GOTO;
import com.miracle.base.util.ThreadUtil;
import com.miracle.databinding.Fragment3Binding;
import com.miracle.base.im.ui.ChatActivity;

import java.util.List;

public class Fragment3 extends BaseFragment<Fragment3Binding> {
    private EMChatRoom room;


    @Override
    public int getLayout() {
        return R.layout.fragment3;
    }

    @Override
    public void initView() {
        loadAndShowData();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initListener() {
        binding.ibGroupChat.setOnClickListener(this);
        binding.ibCustomerService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibGroupChat:
                if (room == null) {
                    loadAndShowData();
                } else {
                    startActivity(new Intent(mContext, ChatActivity.class).putExtra("chatType", 3).putExtra("userId", room.getId()));
                }
                break;
            case R.id.ibCustomerService:
                GOTO.CustomerServiceActivity();
                break;
        }
    }

    private void loadAndShowData() {
        ThreadUtil.runInBackGroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMPageResult<EMChatRoom> result = EMClient.getInstance().chatroomManager().fetchPublicChatRoomsFromServer(1, 10);
                    List<EMChatRoom> chatRooms = result.getData();
                    room = chatRooms.get(0);
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
