package com.example.modulesdemo;

import com.example.arouter_api.ARouterLoadParameter;

/**
 * 实例代码：要通过APT生成这个样子
 */
public class TestMainActivity$$Parameter implements ARouterLoadParameter {

    @Override
    public void loadParameter(Object target) {
        MainActivity activity = (MainActivity) target;
        activity.age = activity.getIntent().getIntExtra("agex", activity.age);
        activity.name = activity.getIntent().getStringExtra("name");
    }
}
