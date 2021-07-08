package com.example.order;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.annotation.ARouter;
import com.example.annotation.Parameter;

import example.library.router.ParameterManager;
import example.library.router.PathRecordManager;
import example.library.router.RouterManager;

@ARouter(group = "order", path = "/order/Order_MainActivity")
public class Order_MainActivity extends AppCompatActivity {

    @Parameter()
    String orderId;

    @Parameter()
    long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterManager.getInstance().loadParameter(this);
//        new Order_MainActivity$$Parameter().loadParameter(this);

        Log.e(getClass().getSimpleName() , "WWS onCreate orderId = " + orderId + " value = " + value);
        setContentView(R.layout.order_activity_main);
    }

    public void jumpToMain(View view){
        RouterManager.getInstance().build("/app/MainActivity")
                .setResult(true)
                .setCode(Activity.RESULT_OK)
                .withString("result", "order success!")
                .navigation(this);
    }

    public void jumpToCommon(View view){
        RouterManager.getInstance().build("/common/Common_MainActivity")
                .withString("userName", "Mr.Order")
                .navigation(this);
    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}