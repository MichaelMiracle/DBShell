package com.miracle.michael.part4.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.GOTO;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClientFootBall;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.view.arcmenu.GFloatingMenu;
import com.miracle.databinding.FragmentMeBinding;
import com.miracle.michael.login.entity.User;
import com.miracle.michael.part4.Service4;
import com.miracle.michael.part4.entity.UserInfo;

/**
 * Created by Michael on 2018/10/15 10:15 (星期一)
 */
public class MeFragment extends BaseFragment<FragmentMeBinding> {

    private static final String menu1 = "我的收藏";
    private static final String menu2 = "客服";
    private static final String menu3 = "分享";
    private static final String menu4 = "设置";
    private static final String menu5 = "关于我们";

    private GFloatingMenu.OnItemClickListener mListener;

    private UserInfo userInfo;

    @Override
    public int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        reqData();
        binding.flmenu.setParentBlurView(binding.rlParent);
        mListener = new GFloatingMenu.OnItemClickListener() {
            @Override
            public void onItemClick(GFloatingMenu.GFloatingMenuItem view) {
                binding.flmenu.closeMenu();
                switch (view.getItemInfo().text) {
                    case menu1:
                        if (userInfo == null) {
                            GOTO.LoginActivity();
                        } else {
                            GOTO.MyCollectionsActivity();
                        }
                        break;
                    case menu2:
                        GOTO.CustomerServiceActivity();
                        break;
                    case menu3:
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
                    case menu4:
                        GOTO.SettingActivity();
                        break;
                    case menu5:
                        GOTO.AboutUsActivity();
                        break;
                }
            }
        };

        binding.flmenu.AddMenuItem(((BitmapDrawable) getResources().getDrawable(R.drawable.ee_6)).getBitmap(), menu1, mListener);
        binding.flmenu.AddMenuItem(((BitmapDrawable) getResources().getDrawable(R.drawable.ee_1)).getBitmap(), menu2, mListener);
        binding.flmenu.AddMenuItem(((BitmapDrawable) getResources().getDrawable(R.drawable.ee_2)).getBitmap(), menu3, mListener);
        binding.flmenu.AddMenuItem(((BitmapDrawable) getResources().getDrawable(R.drawable.ee_3)).getBitmap(), menu4, mListener);
        binding.flmenu.AddMenuItem(((BitmapDrawable) getResources().getDrawable(R.drawable.ee_4)).getBitmap(), menu5, mListener);
    }

    private void reqData() {
        final User user = CommonUtils.getUser();
        if (user != null)
            ZClientFootBall.getService(Service4.class).getUserInfo(user.getUserId()).enqueue(new ZCallback<ZResponse<UserInfo>>() {
                @Override
                public void onSuccess(ZResponse<UserInfo> data) {
                    userInfo = data.getData();
                    binding.tvName.setText(userInfo.getNickname());
                    GlideApp.with(mContext).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHeadImg);
                }
            });
    }

    @Override
    public void onResume() {
        super.onResume();
        reqData();
    }

    @Override
    public void initListener() {
        binding.llMe.setOnClickListener(this);

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
        }
    }
}
