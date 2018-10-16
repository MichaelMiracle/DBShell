package com.miracle.michael.naoh.base;

import android.content.Intent;

import com.miracle.michael.naoh.MainActivity;
import com.miracle.michael.naoh.common.util.ContextHolder;
import com.miracle.michael.naoh.login.activity.CircleTurntableActivity;
import com.miracle.michael.naoh.login.activity.LoginActivity;
import com.miracle.michael.naoh.login.activity.RegisterActivity;
import com.miracle.michael.naoh.part4.activity.AboutUsActivity;
import com.miracle.michael.naoh.part4.activity.CustomerServiceActivity;
import com.miracle.michael.naoh.part4.activity.MeInfoActivity;
import com.miracle.michael.naoh.part4.activity.ModifyPasswordActivity;
import com.miracle.michael.naoh.part4.activity.MyCollectionsActivity;
import com.miracle.michael.naoh.part4.activity.SettingActivity;
import com.miracle.michael.naoh.part4.entity.UserInfo;
import com.miracle.michael.naoh.switcher.WelcomeActivity;

public class GOTO {
    public static void WelcomeActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), WelcomeActivity.class));
    }

    public static void MainActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), MainActivity.class));
    }

    public static void RegisterActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), RegisterActivity.class));
    }

    public static void LoginActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), LoginActivity.class));
    }

    public static void MyCollectionsActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), MyCollectionsActivity.class));
    }

    public static void SettingActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), SettingActivity.class));
    }
    public static void CustomerServiceActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), CustomerServiceActivity.class));
    }
    public static void CircleTurntableActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), CircleTurntableActivity.class));
    }

    public static void MeInfoActivity(UserInfo userInfo) {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), MeInfoActivity.class).putExtra(Constant.USER_INFO, userInfo));
    }

    public static void ModifyPasswordActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), ModifyPasswordActivity.class));
    }

    public static void AboutUsActivity() {
        ContextHolder.getContext().startActivity(new Intent(ContextHolder.getContext(), AboutUsActivity.class));
    }
}
