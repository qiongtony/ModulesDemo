package com.example.modulesdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.annotation.ARouter;
import com.example.annotation.Parameter;
import com.example.annotation.model.RouterBean;
import com.example.apt.ARouter$$Group$$order;
import com.example.arouter_api.ARouterLoadParameter;
import com.example.arouter_api.ARouterLoadPath;

import java.util.Map;

import example.library.router.ParameterManager;
import example.library.router.PathRecordManager;
import example.library.router.RouterManager;

@ARouter(group = "app",path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {


    public static final int COMMON_REQUEST_CODE = 133;
    public static final int REQUEST_CODE_ORDER = 144;
    @Parameter()
    String name;
    @Parameter(name = "agex")
    int age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParameterManager.getInstance().loadParameter(this);
        /*ARouterLoadParameter parameter = new MainActivity$$Parameter();
        parameter.loadParameter(this);*/
        Log.e(getClass().getSimpleName(), "WWS name = " + name  + " age = " + age);
        // 获取buildConfig类的属性
        String type = BuildConfig.host;
    }

    public void jumpToCommon(View view){
        RouterManager.getInstance().build("/common/Common_MainActivity")
                .setCode(COMMON_REQUEST_CODE)
                .withString("userName", "wuweishan")
                .navigation(this);
    }

    public void jumpToOrder(View view){
        RouterManager.getInstance()
                .build("/order/Order_MainActivity")
                .withString("orderId", "id$@2223222")
                .withLong("value", 123456L)
                .setCode(REQUEST_CODE_ORDER)
                .navigation(this);
    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_ORDER ||requestCode == COMMON_REQUEST_CODE) && data != null){
            String result = data.getExtras().getString("result");
            Log.e(getClass().getSimpleName(), "WWS onActivityResult result = " + result);
        }
    }
}