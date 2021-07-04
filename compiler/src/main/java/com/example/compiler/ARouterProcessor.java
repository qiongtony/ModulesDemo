package com.example.compiler;

import com.example.annotation.ARouter;
import com.example.annotation.model.RouterBean;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
// 扫描的注解类型
@SupportedAnnotationTypes({Constants.ANNOTATION_CLASS})
// 支持的java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions({Constants.MODULE_NAME, Constants.PACKAGE_NAME_FOR_APT})
public class ARouterProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types  typeUtils;
    private Messager mMessager;
    private Filer mFiler;
    private String mModuleName;
    private String mPackageNameForAPT;

    // 临时存储
    private Map<String, List<RouterBean>> mMap = new HashMap<>();

    // 临时map存储，存放路由group信息
    private Map<String, String> tempGroupMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElements = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();

        mModuleName = processingEnv.getOptions().get(Constants.MODULE_NAME);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "moduleName = " + mModuleName);
        mPackageNameForAPT = processingEnv.getOptions().get(Constants.PACKAGE_NAME_FOR_APT);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "packageNameForAPT = " + mPackageNameForAPT);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ARouter.class);
        if (elements == null){
            return false;
        }
        for (Element element : elements){
            // 获取包名
            String packageName = mElements.getPackageOf(element).getQualifiedName().toString();
            //
            String className = element.getSimpleName().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "被@ARouter注解的类有：" + className);

            TypeMirror typeMirror = element.asType();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "遍历元素信息：" + typeMirror.toString());
            // 获取注解值
            ARouter aRouter = element.getAnnotation(ARouter.class);

            // 生成路由实体类
            RouterBean routerBean = new RouterBean.Builder()
                    .setGroup(aRouter.group())
                    .setPath(aRouter.path())
                    .setElement(element)
                    .build();
            routerBean.setType(RouterBean.Type.ACTIVITY);

            valueOfPathMap(routerBean);
        }

        //获取LoadGroup、LoadPath接口的类型，生成类时需要实现接口
        TypeElement routerGroupElement = mElements.getTypeElement(Constants.AROUTER_GROUP);
        TypeElement routerPathElement = mElements.getTypeElement(Constants.AROUTER_PATH);
        try {
            createPathFile(routerPathElement);
            createGroupFile(routerGroupElement, routerPathElement);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void createGroupFile(TypeElement routerGroupElement, TypeElement routerPathElement) throws IOException {
        // Map<String, Class<? extends ARouterLoadPath>>
        TypeName returnTypeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(
                        ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(routerPathElement))
                )
        );
        /**
         * @Override
         *     public Map<String, Class<? extends ARouterLoadPath>> loadGroup() {
         */
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(
                "loadGroup"
        ).addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnTypeName);
        // Map<String, Class<? extends ARouterLoadPath>> groupMap = new HashMap<>();
        methodBuilder.addStatement(
                "$T $N = new $T<>()",
                returnTypeName,
                Constants.GROUP_PARAMTETER_NAME, HashMap.class
        );

        String finalGroupName = Constants.GROUP_PREFIX + mModuleName;
        mMessager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由组Group类文件：" +
                mPackageNameForAPT + "." + finalGroupName);


        for (Map.Entry<String, String> entry : tempGroupMap.entrySet()){
            // 循环   groupMap.put("app", ARouter$$Path$$app.class);
            methodBuilder.addStatement("$N.put($S, $T.class)",
                    Constants.GROUP_PARAMTETER_NAME,
                    entry.getKey(),
                    ClassName.get(mPackageNameForAPT, entry.getValue()));
        }

        // 返回语句：return groupMap;
        methodBuilder.addStatement("return $N", Constants.GROUP_PARAMTETER_NAME);
        // 生成类文件：ARouter$$Group$$app
        JavaFile.builder(
                // 包名
                mPackageNameForAPT,
                // 类名
                TypeSpec.classBuilder(finalGroupName)
                        // 实现ARouterLoadGroup接口
                        .addSuperinterface(ClassName.get(routerGroupElement))
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(methodBuilder.build())
                        .build()
        ).build().writeTo(mFiler);
    }

    private void createPathFile(TypeElement routerPathElement) throws IOException {
        if (mMap == null || mMap.size() == 0){
            return;
        }
        // 生成Map<String,RouterBean>类型
        TypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(RouterBean.class)
        );
        for (Map.Entry<String, List<RouterBean>> entry: mMap.entrySet()){
            String fileName = Constants.PATH_PREFIX + entry.getKey();
            /**
             * @Override
             *     public Map<String, RouterBean> loadPath() {
             */
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("loadPath")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(parameterizedTypeName)
                    .addAnnotation(Override.class);
            // Map<String, RouterBean> pathMap = new HashMap<>();
            methodBuilder.addStatement(
                    "$T<$T, $T> $N = new $T<>()",
                    /*ClassName.get(*/Map.class /*)*/,
                    /*ClassName.get(*/String.class/*)*/,
                    /*ClassName.get(*/RouterBean.class/*)*/,
                    Constants.PATH_PARAMETER_NAME,
                    HashMap.class
            );
            for (RouterBean routerBean : entry.getValue()){
                /**
                 *   pathMap.put(
                 *                 "app/MainActivity",
                 *                 RouterBean.createSimple(
                 *                         RouterBean.Type.ACTIVITY,
                 *                         "app",
                 *                         "app/MainActivity",
                 *                         MainActivity.class
                 *                 )
                 *         );
                 */
                methodBuilder
                        .addStatement(
                                "$N.put($S,$T.createSimple(" +
                                        "$T.$L,$S,$S,$T.class))",
                                "pathMap", routerBean.getPath(), ClassName.get(RouterBean.class),
                                ClassName.get(RouterBean.Type.class),// RouterBean.Type
                                routerBean.getType(),//
                                routerBean.getGroup(),
                                routerBean.getPath(),
                                ClassName.get((TypeElement) routerBean.getElement())
                        );
            }
            methodBuilder.addStatement("return $N", Constants.PATH_PARAMETER_NAME);
            TypeSpec typeSpec = TypeSpec.classBuilder(fileName)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(ClassName.get(routerPathElement))
                    .addMethod(methodBuilder.build())
                    .build();

            JavaFile.builder(mPackageNameForAPT, typeSpec)
                    .build().writeTo(mFiler);

            mMessager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Path类文件：" +
                    mPackageNameForAPT + "." + fileName);
            // 存储path的文件，用以group类的生成
            tempGroupMap.put(entry.getKey(), fileName);
        }


    }

    private void valueOfPathMap(RouterBean routerBean) {
        List<RouterBean> routerBeans = mMap.get(routerBean.getGroup());
        if (routerBeans == null){
            routerBeans = new ArrayList<>();
        }
        if (!routerBeans.contains(routerBean)){
            routerBeans.add(routerBean);
        }
        mMap.put(routerBean.getGroup(), routerBeans);
    }
}
