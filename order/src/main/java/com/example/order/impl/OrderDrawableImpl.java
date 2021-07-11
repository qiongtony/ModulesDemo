package com.example.order.impl;

import androidx.annotation.DrawableRes;

import com.example.annotation.ARouter;
import com.example.order.R;

import example.library.order.drawable.OrderDrawable;

@ARouter(path = "/order/getDrawable")
public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public @DrawableRes int getDrawable() {
        return R.drawable.common_btn_weixin;
    }
}
