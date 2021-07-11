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

    String PARAMETERS_CLASS = "com.example.annotation.Parameter";

    // 生成的参数加载类，类名的后缀，如：MainActivity $$Parameter
    String PARAMETER_NAME_SUFFIX = "$$Parameter";

    String PARAMETER_INTERFACE_PATH = "com.example.arouter_api.ARouterLoadParameter";

    String ACTIVITY_NAME = "activity";

    String STRING_CLASS_NAME = "java.lang.String";

    String APP_COMPAT_ACTIVITY_FULL_NAME = "androidx.appcompat.app.AppCompatActivity";

    String ACTIVITY_FULL_NAME = "android.app.Activity";

    String ROUTER_MANAGER_CLASS_NAME = "example.library.router.RouterManager";

    String NAVIGATION_METHOD_NAME = "navigation";

}
