package example;

import android.app.Application;
import android.content.res.Configuration;

public interface BaseAppInit {
    /**
     * 高优先级
     * @param application
     * @return
     */
    boolean onInitHighPriority(Application application);

    /**
     * 低优先级
     * @param application
     * @return
     */
    boolean onInitLowPriority(Application application);
}
