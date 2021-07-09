package com.example.modulesdemo;

import com.example.annotation.model.RouterBean;
import com.example.arouter_api.ARouterLoadPath;

import java.util.HashMap;
import java.util.Map;

import example.library.app.drawable.AppDrawable;

public class ARouter$$Path$$app implements ARouterLoadPath {
    @Override
    public Map<String, RouterBean> loadPath() {
        Map<String, RouterBean> pathMap = new HashMap<>();
        pathMap.put(
                "app/MainActivity",
                RouterBean.createSimple(
                        RouterBean.Type.ACTIVITY,
                        "app",
                        "app/MainActivity",
                        MainActivity.class
                )
        );
        pathMap.put("app/getDrawable",
                RouterBean.createSimple(
                        RouterBean.Type.CALL,
                        "app",
                        "app/getDrawable",
                        AppDrawable.class
                )
        );
        return pathMap;
    }
}
