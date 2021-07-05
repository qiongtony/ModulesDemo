# 组件化Demo

实现记录：

2021/6/30实现gradle语法，根据config.gradle配置对module进行统一配置

2021/7/04实现用apt方式根据注解生成代码，第三方库javapoet，类ARouter$$Group$$Xxx和ARouter$$Path$$Xxx
 
疑问点：
    Group类的作用，以后module：group为1:N的情况？
    除了主module，其他module依然无法跳转到另外的module，其他module生成的类无法访问

2021/7/05实现用apt方式根据Parameter注解生成解析Intent内的参数，如MainActivity，生成的类为MainActivity$$Parameter
