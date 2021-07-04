package com.example.compiler;

public interface Constants {

    // 注解类的路径
    String ANNOTATION_CLASS = "com.example.annotation.ARouter";

    // 包名
    String MODULE_NAME  = "moduleName";

    // apt生成类文件包名
    String PACKAGE_NAME_FOR_APT = "packageNameForAPT";


    String AROUTER_GROUP = "com.example.arouter_api.ARouterLoadGroup";

    String AROUTER_PATH = "com.example.arouter_api.ARouterLoadPath";

    String GROUP_PREFIX = "ARouter$$Group$$";

    String PATH_PREFIX = "ARouter$$Path$$";

    String PATH_PARAMETER_NAME = "pathMap";

    String GROUP_PARAMTETER_NAME = "groupMap";
}
