package com.example.modulesdemo;

public class MainActivity$$ARouter {

    public static Class<?> findTargetClass(String groupName, String pathName){
        if (groupName.equalsIgnoreCase("app") && pathName.equalsIgnoreCase("app/MainActivity")){
            return MainActivity.class;
        }
        return null;
    }
}
