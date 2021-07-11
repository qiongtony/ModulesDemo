package example.library.router;

import android.content.Intent;
import android.util.Log;

import com.example.annotation.model.RouterBean;
import com.example.arouter_api.ARouterLoadGroup;
import com.example.arouter_api.ARouterLoadPath;

import java.util.Map;

public class RouterManager {
    private static volatile RouterManager INSTANCE;

    private static final String GROUP_PACKAGE_PREFIX = "com.example.apt.ARouter$$Group$$";
    private static final String PATH_PACKAGE_PREFIX = "com.example.apt.ARouter$$Path$$";

    public static RouterManager getInstance() {
        if (INSTANCE == null) {
            synchronized (RouterManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RouterManager();
                }
            }
        }
        return INSTANCE;
    }

    private RouterManager() {

    }

    public BundleManager build(String path){
        return new BundleManager(path);
    }

    public Object navigation(BundleManager bundleManager) {
        Object result = new Object();
        try {
            Class<? extends ARouterLoadGroup> loadGroupClazz = (Class<? extends ARouterLoadGroup>) Class.forName(GROUP_PACKAGE_PREFIX + bundleManager.getGroup());
            ARouterLoadGroup loadGroup = loadGroupClazz.newInstance();
            Map<String, Class<? extends ARouterLoadPath>> loadGroupMap = loadGroup.loadGroup();
            Class<? extends ARouterLoadPath> loadPathClazz = loadGroupMap.get(bundleManager.getGroup());
            if (loadPathClazz == null) {
                Log.e(getClass().getSimpleName(), "WWS navigation return as loadPathClazz is null group = " + bundleManager.getGroup());
                return new Object();
            }
            ARouterLoadPath loadPath = loadPathClazz.newInstance();
            RouterBean routerBean = loadPath.loadPath().get(bundleManager.getPath());
            if (routerBean == null) {
                Log.e(getClass().getSimpleName(), "WWS navigation return as routerBean is null");
                return new Object();
            }
            switch (routerBean.mType) {
                case ACTIVITY:
                    if (bundleManager.isResult()) {
                        Intent intent = new Intent();
                        intent.putExtras(bundleManager.getBundle());
                        bundleManager.getActivity().setResult(bundleManager.getCode(), intent);
                        bundleManager.getActivity().finish();
                    } else {
                        Intent intent = new Intent(bundleManager.getActivity(), routerBean.getClazz());
                        intent.putExtras(bundleManager.getBundle());
                        bundleManager.getActivity().startActivityForResult(intent, bundleManager.getCode());
                    }
                    break;
                case CALL:
                     result = routerBean.getClazz().newInstance();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
