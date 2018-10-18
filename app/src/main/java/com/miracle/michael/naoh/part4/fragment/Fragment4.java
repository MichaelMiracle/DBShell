package com.miracle.michael.naoh.part4.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPageResult;
import com.hyphenate.exceptions.HyphenateException;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseFragment;
import com.miracle.michael.naoh.base.GOTO;
import com.miracle.michael.naoh.common.network.GlideApp;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.common.util.ThreadUtil;
import com.miracle.michael.naoh.databinding.Fragment4Binding;
import com.miracle.michael.naoh.im.ui.ChatActivity;
import com.miracle.michael.naoh.login.entity.User;
import com.miracle.michael.naoh.part4.Service4;
import com.miracle.michael.naoh.part4.entity.UserInfo;

import java.util.List;

public class Fragment4 extends BaseFragment<Fragment4Binding> {
    private UserInfo userInfo;
    private EMChatRoom room;

    @Override
    public int getLayout() {
        return R.layout.fragment4;
    }

    @Override
    public void initView() {
        loadAndShowData();
        reqData();
    }

    private void reqData() {
        User user = CommonUtils.getUser();
        if (user != null)
            ZClientFootBall.getService(Service4.class).getUserInfo(user.getUserId()).enqueue(new ZCallback<ZResponse<UserInfo>>(binding.swipeLayout) {
                @Override
                public void onSuccess(ZResponse<UserInfo> data) {
                    userInfo = data.getData();
                    binding.tvName.setText(userInfo.getNickname());
                    GlideApp.with(mContext).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHeadImg);
                }
            });
    }

    @Override
    public void initListener() {
        binding.llMe.setOnClickListener(this);
        binding.ibOrderManage.setOnClickListener(this);
        binding.ibBailManage.setOnClickListener(this);
        binding.ibSettings.setOnClickListener(this);
        binding.ibGroupChat.setOnClickListener(this);
        binding.ibCustomerService.setOnClickListener(this);
        binding.ibShare.setOnClickListener(this);
        binding.ibAboutUs.setOnClickListener(this);
        binding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reqData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMe:
                if (userInfo == null) {
                    GOTO.LoginActivity();
                } else {
                    GOTO.MeInfoActivity(userInfo);
                }
                break;
            case R.id.ibOrderManage:
//                startActivity(new Intent(mContext, SaiShiFenXilerActivity.class).putExtra(Constant.REQKEY, "zqbfyc").putExtra(Constant.TITLE, "赛事分析"));
                break;
            case R.id.ibBailManage:
                if (userInfo == null) {
                    GOTO.LoginActivity();
                } else {
                    GOTO.MyCollectionsActivity();
                }
                break;
            case R.id.ibSettings:
                GOTO.SettingActivity();
                break;
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
            case R.id.ibShare:
                if (userInfo == null) {
                    GOTO.LoginActivity();
                } else {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, userInfo.getNickname() + "邀请你加入" + CommonUtils.getAppName(mContext));
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "分享"));
                }
                break;

            case R.id.ibAboutUs:
                GOTO.AboutUsActivity();
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
                    if (chatRooms != null && !chatRooms.isEmpty())
                        room = chatRooms.get(0);
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
