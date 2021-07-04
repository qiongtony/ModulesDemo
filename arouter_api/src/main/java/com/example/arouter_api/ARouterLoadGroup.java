package com.example.arouter_api;

import java.util.Map;

public interface ARouterLoadGroup {

    /***
     * 加载路径的group
     * key为group，如：app，value为对应的Path接口类，如ARouter$$Path$$app
     * @return
     */
    Map<String, Class<? extends ARouterLoadPath>> loadGroup();
}
