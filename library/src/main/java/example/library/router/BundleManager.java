package example.library.router;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

public class BundleManager {
    private String path;
    private String group;
    // 存储传参
    private Bundle mBundle = new Bundle();
    // 是否返回值
    private boolean result = false;
    // result为false，为requestCode，result为true，为resultCode
    private int code = - 1;
    private Activity mActivity;
    public BundleManager(String path){
        // 只有一个/，不以/开头
        if (path.lastIndexOf("/") == 0 || !path.startsWith("/") ){
            throw new IllegalArgumentException("path命名有误，类似：/app/Mainactivity");
        }
        this.path = path;
        // 根据path，如/app/MainActivity求出group，app
        group = path.substring(path.indexOf("/") + 1, path.lastIndexOf("/"));
        Log.e("WWS", "group = " + group);
    }

    public BundleManager withString(String key, String value){
        mBundle.putString(key, value);
        return this;
    }

    public BundleManager withLong(String key, long value){
        mBundle.putLong(key, value);
        return this;
    }

    public BundleManager withBundle(@NonNull Bundle bundle){
        this.mBundle = bundle;
        return this;
    }

    public BundleManager setResult(boolean result){
        this.result = result;
        return this;
    }

    public BundleManager setCode(int code){
        this.code = code;
        return this;
    }

    public String getPath() {
        return path;
    }

    public String getGroup() {
        return group;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public boolean isResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public Object navigation(Activity context){
        mActivity = context;
        return RouterManager.getInstance().navigation(this);
    }
}
