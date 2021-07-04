package com.example.arouter_api;

import com.example.annotation.model.RouterBean;

import java.util.Map;

public interface ARouterLoadPath {

    /**
     * 获取路径的path map
     * key为path，value为对应的Router
     * @return
     */
    Map<String, RouterBean> loadPath();
}
