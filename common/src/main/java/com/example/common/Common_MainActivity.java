package com.example.common;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.annotation.ARouter;

import example.library.router.PathRecordManager;
import example.library.router.RouterManager;

@ARouter(group = "common", path = "/common/Common_MainActivity")
public class Common_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_main);
    }

    public void jumpToMain(View view){
        RouterManager.getInstance().build("/app/MainActivity")
                .setResult(true)
                .setCode(Activity.RESULT_OK)
                .withString("result", "common callback!")
                .navigation(this);
    }

    public void jumpToOrder(View view){
        RouterManager.getInstance()
                .build("/order/Order_MainActivity")
                .withString("orderId", "id@11111")
                .withLong("value", 654321L)
                .navigation(this);
    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}