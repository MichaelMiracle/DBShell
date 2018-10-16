package com.miracle.michael.naoh.part4.activity;

import android.text.TextUtils;
import android.view.View;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.ToastUtil;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteKey;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteUtil;
import com.miracle.michael.naoh.databinding.ActivityModifypasswordBinding;
import com.miracle.michael.naoh.part4.Service4;


/**
 * Created by Administrator on 2017/11/15.
 */

public class ModifyPasswordActivity extends BaseActivity<ActivityModifypasswordBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_modifypassword;
    }

    @Override
    public void initView() {
        setTitle("修改密码");
    }

    @Override
    public void initListener() {
        binding.btModifyPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btModifyPassword:
                String oldPassword = binding.etOldPassword.getText();
                final String newPassword = binding.etNewPassword.getText();

                if (TextUtils.isEmpty(oldPassword)) {
                    ToastUtil.toast("请输入旧密码！");
                    return;
                }
                if (TextUtils.isEmpty(newPassword)) {
                    ToastUtil.toast("请输入新密码！");
                    return;
                }
//                if (!CommonUtils.loginInputCheck(oldPassword, 8, 16)) {
//                    ToastUtil.toast("旧密码格式错误！");
//                    return;
//                }
//                if (!CommonUtils.loginInputCheck(newPassword, 8, 16)) {
//                    ToastUtil.toast("新密码格式错误！");
//                    return;
//                }
                ZClientFootBall.getService(Service4.class).modifyPassword(oldPassword, newPassword).enqueue(new ZCallback<ZResponse>() {
                    @Override
                    public void onSuccess(ZResponse data) {
                        SQLiteUtil.saveEncryptedString(SQLiteKey.PASSWORD, newPassword);
                        ToastUtil.toast(data.getMessage());
                        finish();
                    }
                });
                break;
        }
    }
}
