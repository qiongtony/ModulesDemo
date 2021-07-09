package com.example.annotation.model;

import javax.lang.model.element.Element;

/**
 * 路由路径Path的封装类
 * 比如：app分组中的MainActivity对象
 */
public class RouterBean {

    public enum Type{
        ACTIVITY,
        CALL
    }

    // 类型：Activity，后续可能支持Fragment
    public Type mType;
    // 类节点，用在APT里获取类
    private Element mElement;

    private Class<?> mClass;
    private String mPath;
    private String mGroup;

    private RouterBean(Builder builder){
        this.mType = builder.mType;
        this.mElement = builder.mElement;
        this.mClass = builder.mClass;
        this.mPath = builder.mPath;
        this.mGroup = builder.mGroup;
    }

    public static RouterBean createSimple(
            Type type, String group, String path, Class<?> clazz){
        return new Builder()
                .setType(type)
                .setGroup(group)
                .setPath(path)
                .setClazz(clazz)
                .build();

    }

    public void setType(Type type) {
        mType = type;
    }

    public Type getType() {
        return mType;
    }

    public Element getElement() {
        return mElement;
    }

    public Class<?> getClazz() {
        return mClass;
    }

    public String getPath() {
        return mPath;
    }

    public String getGroup() {
        return mGroup;
    }

    public static final class Builder {
        private Type mType;
        private Element mElement;
        private Class mClass;

        private String mPath;
        private String mGroup;

        public Builder() {
        }

        public Builder setType(Type type) {
            mType = type;
            return this;
        }

        public Builder setElement(Element element) {
            mElement = element;
            return this;
        }

        public Builder setClazz(Class aClass) {
            mClass = aClass;
            return this;
        }

        public Builder setPath(String path) {
            mPath = path;
            return this;
        }

        public Builder setGroup(String group) {
            mGroup = group;
            return this;
        }

        public RouterBean build(){
            if (mPath == null || mPath.length() == 0){
                throw new IllegalArgumentException("path为空，如/app/MainActivity");

            }
            return new RouterBean(this);
        }
    }

    @Override
    public String toString() {
        return "RouterBean{" +
                "mPath='" + mPath + '\'' +
                ", mGroup='" + mGroup + '\'' +
                '}';
    }
}
