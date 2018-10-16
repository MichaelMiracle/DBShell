package com.miracle.michael.naoh.part4.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;

import com.miracle.michael.naoh.R;
import com.miracle.michael.naoh.base.App;
import com.miracle.michael.naoh.base.BaseActivity;
import com.miracle.michael.naoh.common.util.ThreadUtil;
import com.miracle.michael.naoh.common.util.ToastUtil;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteKey;
import com.miracle.michael.naoh.common.util.sqlite.SQLiteUtil;
import com.miracle.michael.naoh.databinding.ActivitySettingBinding;

import java.util.Timer;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    private ProgressDialog dialogProgress;

    @Override
    public int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setTitle("设置");
        binding.swAutoLogin.setChecked(SQLiteUtil.getBoolean(SQLiteKey.AUTOLOGIN));

        dialogProgress = new ProgressDialog(mContext);
        dialogProgress.setMessage("清理中...");
//        dialogProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        dialogProgress.setMax(100);
    }

    @Override
    public void initListener() {
        binding.btExit.setOnClickListener(this);
        binding.ibClearCache.setOnClickListener(this);
        binding.swAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoLogin = isChecked;
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialogProgress.dismiss();
            ToastUtil.toast("缓存清理成功!");
        }
    };
    private boolean autoLogin;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btExit:
                App.exit(autoLogin);
                break;

            case R.id.ibClearCache:
//                StartActivity.getInstance().startModifyPasswordActivity(context);
                dialogProgress.show();
                handler.sendEmptyMessageDelayed(0, 1000);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogProgress != null) {
            dialogProgress.dismiss();
            dialogProgress = null;
        }
    }
}
