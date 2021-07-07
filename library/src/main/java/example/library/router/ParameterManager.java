package example.library.router;

import android.app.Activity;
import android.util.LruCache;

import com.example.arouter_api.ARouterLoadParameter;

/**
 * 全局参数管理类：
 * 存储每个类需要的参数，提供根据Activity实例自动加载
 * 并对ParameterLoad实例进行缓存
 */
public class ParameterManager {
    public static final int CACHE_SIZE = 163;
    private static volatile ParameterManager INSTANCE;

    private LruCache<String, ARouterLoadParameter> mCache;

    // APT生成类类名的后缀
    private static final String PARAMETER_SUFFIX_PATH = "$$Parameter";

    public static ParameterManager getInstance(){
        if (INSTANCE == null){
            synchronized (ParameterManager.class){
                if (INSTANCE == null){
                    INSTANCE = new ParameterManager();
                }
            }
        }
        return INSTANCE;
    }

    private ParameterManager(){
        mCache = new LruCache<>(CACHE_SIZE);
    }

    public void loadParameter(Activity activity){
        String fileName = activity.getClass().getName() + PARAMETER_SUFFIX_PATH;
        ARouterLoadParameter parameter = null;
        if (mCache.get(fileName) == null){
            try {
                Class<?> clazz = Class.forName(fileName);
                parameter = (ARouterLoadParameter) clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCache.put(fileName, parameter);
        }else{
            parameter = mCache.get(fileName);
        }
        parameter.loadParameter(activity);
    }
}
