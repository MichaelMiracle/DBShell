package com.miracle.michael.naoh;

import android.content.Intent;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.base.GOTO;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.common.util.ThreadUtil;
import com.miracle.michael.naoh.databinding.ActivityMainBinding;
import com.miracle.michael.naoh.im.Constant;
import com.miracle.michael.naoh.im.ui.ChatActivity;
import com.miracle.michael.naoh.part1.fragment.Fragment1;
import com.miracle.michael.naoh.part2.fragment.Fragment2_1;
import com.miracle.michael.naoh.part4.fragment.MeFragment;
import com.miracle.michael.naoh.part6.fragment.Fragment6;

import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private EMGroupInfo groupInfo;


    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        loadAndShowData();
        hideTitle();
        binding.zRadiogroup.setupWithContainerAndFragments(R.id.container, new Fragment1(), new Fragment2_1(), new Fragment6(), new MeFragment());
//        if (!SQLiteUtil.getBoolean(SQLiteKey.HAS_DRAWED + AppConfig.USER_ID))
//            GOTO.CircleTurntableActivity();
    }


    @Override
    public void initListener() {
        binding.tvContactCustomerService.setOnClickListener(this);
        binding.rlGroupChat.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContactCustomerService:
                GOTO.CustomerServiceActivity();
                break;

            case R.id.rlGroupChat:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity();
                } else if (null == groupInfo) {
                    loadAndShowData();
                } else {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    // it is group chat
                    intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
                    intent.putExtra("userId", groupInfo.getGroupId());
                    startActivityForResult(intent, 0);
                }
                break;
        }
    }

    private void loadAndShowData() {
        ThreadUtil.runInBackGroundThread(new Runnable() {
            @Override
            public void run() {
                EMCursorResult<EMGroupInfo> result;
                try {
                    result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(20, "");
                    List<EMGroupInfo> returnGroups = result.getData();
                    if (returnGroups.size() > 0) {
                        groupInfo = returnGroups.get(0);
                        EMClient.getInstance().groupManager().joinGroup(groupInfo.getGroupId());
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
