package com.miracle.michael.naoh.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.App;
import com.miracle.michael.naoh.base.AppConfig;
import com.miracle.michael.naoh.base.GOTO;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.GsonUtil;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteKey;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteUtil;
import com.miracle.michael.naoh.login.entity.User;


/**
 * Created by Administrator on 2017/8/24.
 */

public class RegisterSuccessDialog extends Dialog {

    private String userName;
    private String password;
    private Context context;

    public RegisterSuccessDialog(@NonNull final Context context) {
        super(context, R.style.commondialog);
        this.context = context;
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_register_success);
        findViewById(R.id.btNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZClientFootBall.getService(ServiceLogin.class).login(userName, password).enqueue(new ZCallback<ZResponse<User>>() {
                    @Override
                    public void onSuccess(ZResponse<User> data) {
                        User user = data.getData();
                        AppConfig.USER_ID = String.valueOf(user.getUserId());
                        SQLiteUtil.saveString(SQLiteKey.USER, GsonUtil.obj2Json(user));
                        SQLiteUtil.saveEncryptedString(SQLiteKey.PASSWORD, password);
                        SQLiteUtil.saveBoolean(SQLiteKey.AUTOLOGIN, true);
                        GOTO.MainActivity();
                        App.getApp().finishAllActivity();
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                });
            }
        });
    }

    public void setLoginParams(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
