package com.miracle.michael.naoh.switcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.miracle.michael.naoh.MainActivity;
import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.AppConfig;
import com.miracle.michael.naoh.base.GOTO;
import com.miracle.michael.naoh.common.network.ZCallback;
import com.miracle.michael.naoh.common.network.ZClientFootBall;
import com.miracle.michael.naoh.common.network.ZResponse;
import com.miracle.michael.naoh.common.util.CommonUtils;
import com.miracle.michael.naoh.common.util.Encryptor;
import com.miracle.michael.naoh.common.util.GsonUtil;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteKey;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteUtil;
import com.miracle.michael.naoh.login.ServiceLogin;
import com.miracle.michael.naoh.login.activity.LoginActivity;
import com.miracle.michael.naoh.login.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Administrator on 2018/5/10.
 */

public class WelcomeActivity extends Activity {
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundResource(R.mipmap.astart);
        setContentView(linearLayout);
        SharedPreferences setting = getSharedPreferences("FIRST", 0);
        mUrl = setting.getString("URL", "");
        if (hasNetwork()) {
            reqData();
        } else {
            nextActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private boolean hasNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void reqData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://860790.com/v1/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(ZService.class).reqSwitcher(getPackageName()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    DBEntity body = GsonUtil.json2Obj(response.body(), DBEntity.class);
                    if (body.getCode() == 1) {
                        DBEntity.DataBean data = body.getData();
                        if (data.getRflag() == 1) {
                            mUrl = data.getRurl();
                            if (data.getUflag() == 1) {
                                mUrl = data.getUurl();
                            }
                            getSharedPreferences("FIRST", 0).edit().putString("URL", mUrl).apply();
                            startActivity(new Intent(WelcomeActivity.this, GameActivity.class).putExtra("url", mUrl));
                            finish();
                        } else {
                            nextActivity();
                        }
                    } else {
                        nextActivity();
                    }
                } catch (Exception e) {
                    nextActivity();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                nextActivity();
            }
        });
    }

    private void nextActivity() {
        if (TextUtils.isEmpty(mUrl)) {
            goNative();
        } else {
            startActivity(new Intent(this, GameActivity.class).putExtra("url", mUrl));
            finish();
        }
    }

    private void goNative() {
        if (SQLiteUtil.getBoolean(SQLiteKey.AUTOLOGIN)) {
            autoLogin();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void autoLogin() {
        User user = GsonUtil.json2Obj(SQLiteUtil.getString(SQLiteKey.USER), User.class);
        final String userName = user.getUsername();
        final String pwd = SQLiteUtil.getEncryptedString(SQLiteKey.PASSWORD);
        ZClientFootBall.getService(ServiceLogin.class).login(userName, pwd).enqueue(new ZCallback<ZResponse<User>>() {
            @Override
            public void onSuccess(ZResponse<User> data) {
                CommonUtils.EMLogin(userName, pwd);
                User user = data.getData();
                AppConfig.USER_ID = String.valueOf(user.getUserId());
                SQLiteUtil.saveString(SQLiteKey.TOKEN, Encryptor.encryptString(user.getToken()));
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ZResponse<User>> call, Throwable t) {
                super.onFailure(call, t);
                GOTO.LoginActivity();
                finish();
            }
        });
    }

    private interface ZService {
        @POST("{packageName}")
        Call<String> reqSwitcher(@Path("packageName") String packageName);
    }
}