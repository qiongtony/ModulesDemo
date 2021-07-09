package com.example.order.drawable;

import androidx.annotation.DrawableRes;

import com.example.order.R;

import example.library.order.drawable.OrderDrawable;

public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public @DrawableRes int getDrawable() {
        return R.drawable.common_btn_weixin;
    }
}
