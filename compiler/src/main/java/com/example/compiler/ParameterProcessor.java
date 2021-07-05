package com.example.compiler;

import com.example.annotation.Parameter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
@SupportedAnnotationTypes({Constants.PARAMETERS_CLASS})
// 支持的java版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ParameterProcessor extends AbstractProcessor {
    private Elements mElements;
    private Types typeUtils;
    private Messager mMessager;
    private Filer mFiler;
    private String mModuleName;
    private String mPackageNameForAPT;
    private Map<TypeElement, List<Element>> mTempMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElements = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();

        mModuleName = processingEnv.getOptions().get(Constants.MODULE_NAME);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "ParameterProcessor init");
        mPackageNameForAPT = processingEnv.getOptions().get(Constants.PACKAGE_NAME_FOR_APT);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "ParameterProcessor packageNameForAPT = " + mPackageNameForAPT);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Parameter.class);
        if (annotations == null || annotations.size() == 0){
            return false;
        }
        TypeElement parameterElement = mElements.getTypeElement(Constants.PARAMETER_INTERFACE_PATH);
        for (Element element : elements){
            // 父节点
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            if (mTempMap.containsKey(typeElement)){
                mTempMap.get(typeElement).add(element);
            }else {
                List<Element> elementList = new ArrayList<>();
                elementList.add(element);
                mTempMap.put(typeElement, elementList);
            }
        }

        if (mTempMap.size() > 0){
            for (Map.Entry<TypeElement, List<Element>> typeElementListEntry:
                 mTempMap.entrySet()) {
                String qualifiedName = typeElementListEntry.getKey().getQualifiedName().toString();
                String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
                String clazzName = typeElementListEntry.getKey().getSimpleName() + Constants.PARAMETER_NAME_SUFFIX;
                mMessager.printMessage(Diagnostic.Kind.NOTE, "packageName = " + packageName + " clazzName = " + clazzName);
                /**
                 * @Override
                 *     public void loadParameter(Object target) {
                 */
                MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("loadParameter")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(ParameterSpec.builder(Object.class, "target").build());
                // MainActivity activity = (MainActivity) target;
                methodSpecBuilder.addStatement("$T $N = ($T) $N", ClassName.get(typeElementListEntry.getKey())
                    , "activity", ClassName.get(typeElementListEntry.getKey()), "target"
                );

                for (Element element : typeElementListEntry.getValue()){
                    //  activity.age = activity.getIntent().getIntExtra("agex", activity.age);M
                    Parameter parameter = element.getAnnotation(Parameter.class);
                    TypeElement typeElement = mElements.getTypeElement(String.class.getName());

                    String parameterName = (parameter.name() == null || parameter.name().length() == 0 ) ? element.getSimpleName().toString() :  parameter.name();
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "element.kind = " + element.asType().getKind().toString() + " parameterName = " + parameterName);
                    mMessager.printMessage(Diagnostic.Kind.NOTE, "clazzName = " + (element.asType().toString()));
                    switch (element.asType().getKind()){
                        case INT:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getIntExtra($S,$N.$N)",
                            Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }

                        case BYTE:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getByteExtra($S,$N.$N)",
                                    Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }

                        case CHAR:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getCharExtra($S,$N.$N)",
                                    Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }

                        case LONG:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getLongExtra($S,$N.$N)",
                                    Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }

                        case FLOAT:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getFloatExtra($S,$N.$N)",
                                    Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }
                        case DOUBLE:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getDoubleExtra($S,$N.$N)",
                                    Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }

                        case BOOLEAN:{
                            methodSpecBuilder.addStatement(
                                    "$N.$N = $N.getIntent().getBooleanExtra($S,$N.$N)",
                                    Constants.ACTIVITY_NAME,  element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName, Constants.ACTIVITY_NAME, element.getSimpleName().toString());
                            break;
                        }

                        case DECLARED:{
                            // 判断String类型
                            if (element.asType().toString().equals(Constants.STRING_CLASS_NAME)) {
                                methodSpecBuilder.addStatement(
                                        "$N.$N = $N.getIntent().getStringExtra($S)",
                                        Constants.ACTIVITY_NAME, element.getSimpleName().toString(), Constants.ACTIVITY_NAME, parameterName);
                            }
                            break;
                        }

                        // 数组、序列化对象呢？

                    }
                }

                TypeSpec typeSpec = TypeSpec.classBuilder(clazzName)
                        .addModifiers(Modifier.PUBLIC)
                        .addSuperinterface(ClassName.get(parameterElement))
                        .addMethod(methodSpecBuilder.build())
                        .build();
                try {
                    JavaFile.builder(packageName,
                            typeSpec).build().writeTo(mFiler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }


}
