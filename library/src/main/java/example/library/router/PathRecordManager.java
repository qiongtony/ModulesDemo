package example.library.router;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathRecordManager {
    private static Map<String, List<PathBean>> groupMap = new HashMap<>();

    public static void joinGroup(String groupName, String pathName, Class<?> clazz){
        List<PathBean> list = groupMap.get(groupName);
        if (list == null){
            list = new ArrayList<>();
            list.add(new PathBean(pathName, clazz));
            groupMap.put(groupName, list);
            return;
        }
        for (PathBean pathBean : list){
            if (pathBean.getPathName().equals(pathName)){
                pathBean.setClazz(clazz);
                return;
            }
        }
        list.add(new PathBean(pathName, clazz));
    }

    public static Class<?> getClass(String groupName, String pathName){
        List<PathBean> pathBeans = groupMap.get(groupName);
        Log.e("WWS", "getClass groupName = " + groupName + " pathName = " + pathName);
        if (pathBeans == null){
            return null;
        }
        for (PathBean pathBean : pathBeans){
            if (pathBean.getPathName().equalsIgnoreCase(pathName)){
                return pathBean.getClazz();
            }
        }
        return null;
    }
}
