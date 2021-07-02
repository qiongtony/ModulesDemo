package com.example.compiler;

import com.example.annotation.ARouter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

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
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
// 扫描的注解类型
@SupportedAnnotationTypes({"com.example.annotation.ARouter"})
// 支持的java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("content")
public class ARouterProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types  typeUtils;
    private Messager mMessager;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElements = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();

        String content = processingEnv.getOptions().get("content");
        mMessager.printMessage(Diagnostic.Kind.NOTE, content);
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

            String finalClassName = className + "$$ARouter";


        }
        return false;
    }
}
