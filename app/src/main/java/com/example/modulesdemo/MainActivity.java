package com.example.modulesdemo;

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

@ARouter(group = "app",path = "app/MainActivity")
public class MainActivity extends AppCompatActivity {


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
        //            Class<?> clazz = Class.forName("com.example.common.Common_MainActivity");
//        Class<?> clazz = PathRecordManager.getClass("common", "Common_MainActivity");
        Class<?> clazz = MainActivity$$ARouter.findTargetClass("app", "app/MainActivity");
        openPage(clazz);
    }

    public void jumpToOrder(View view){
        /* Class<?> clazz = Class.forName("com.example.order.Order_MainActivity");
        Class<?> clazz = PathRecordManager.getClass("order", "Order_MainActivity");
        openPage(clazz);*/
        // 使用APT生成的路由文件进行跳转
        ARouter$$Group$$order orderGroup = new ARouter$$Group$$order();
        Class<? extends ARouterLoadPath> orderPathClazz = orderGroup.loadGroup()
                .get("order");
        try {
            ARouterLoadPath loadPath = orderPathClazz.newInstance();
            Map<String, RouterBean> stringRouterBeanMap = loadPath.loadPath();
            RouterBean routerBean = stringRouterBeanMap.get("order/Order_MainActivity");
            Intent intent = new Intent(this, routerBean.getClazz());
            intent.putExtra("orderId", "123456");
            intent.putExtra("value", 100L);
            startActivity(intent);

//            openPage(routerBean.getClazz());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void openPage(Class<?> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}