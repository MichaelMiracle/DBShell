package com.miracle.michael.naoh.login.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.AppConfig;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.base.GOTO;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.common.util.GsonUtil;
import com.miracle.michael.naoh.common.util.ToastUtil;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteKey;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteUtil;
import com.miracle.michael.naoh.databinding.ActivityLoginBinding;
import com.miracle.michael.naoh.databinding.LoginPopupwindowBinding;
import com.miracle.michael.naoh.login.ServiceLogin;
import com.miracle.michael.naoh.login.entity.User;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;


/**
 * Created by Administrator on 2017/6/6.
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private PopupWindow popupWindow;
    private ProgressDialog dialogLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        hideTitle();
        dialogLogin = new ProgressDialog(mContext);
        dialogLogin.setMessage("登录中...");
        initPopupWindow();
    }

    @Override
    public void initListener() {
        binding.tvForget.setOnClickListener(this);
        binding.tvRegister.setOnClickListener(this);
        binding.btLogin.setOnClickListener(this);
    }


    private void initPopupWindow() {
        LoginPopupwindowBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.login_popupwindow, null, false);
        popBinding.tvCellphoneRetrieve.setOnClickListener(this);
        popBinding.tvRealNameRetrieve.setOnClickListener(this);
        popBinding.tvCancel.setOnClickListener(this);
        popupWindow = new PopupWindow(popBinding.getRoot(), MATCH_PARENT, WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvForget:
                popupWindow.showAtLocation(binding.rootView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tvRegister:
                GOTO.RegisterActivity();
                break;
            case R.id.btLogin:
                if (binding.etAccount.isEmpty()) {
                    ToastUtil.toast("请输入用户名");
                    return;
                }
                if (binding.etPassword.isEmpty()) {
                    ToastUtil.toast("请输入密码");
                    return;
                }
                login();
                break;

            case R.id.tvCellphoneRetrieve:
            case R.id.tvRealNameRetrieve:
                ToastUtil.toast("敬请期待");
                popupWindow.dismiss();
                break;
//            case R.id.tvCellphoneRetrieve:
//                StartActivity.getInstance().startPhoneRetrieveActivity(mContext);
//                popupWindow.dismiss();
//                break;
//            case R.id.tvRealNameRetrieve:
//                StartActivity.getInstance().startEmailRetrieveActivity(mContext);
//                popupWindow.dismiss();
//                break;
            case R.id.tvCancel:
                popupWindow.dismiss();
                break;
        }
    }

    private void login() {
        dialogLogin.show();
        ZClientFootBall.getService(ServiceLogin.class).login(binding.etAccount.getText(), binding.etPassword.getText()).enqueue(new ZCallback<ZResponse<User>>(dialogLogin) {
            @Override
            public void onSuccess(ZResponse<User> data) {
                CommonUtils.EMLogin(binding.etAccount.getText(), binding.etPassword.getText());
                User user = data.getData();
                AppConfig.USER_ID = String.valueOf(user.getUserId());
                SQLiteUtil.saveString(SQLiteKey.USER, GsonUtil.obj2Json(user));
                SQLiteUtil.saveEncryptedString(SQLiteKey.PASSWORD, binding.etPassword.getText());
                SQLiteUtil.saveBoolean(SQLiteKey.AUTOLOGIN, true);
                GOTO.MainActivity();
                finish();
            }
        });
    }
}
