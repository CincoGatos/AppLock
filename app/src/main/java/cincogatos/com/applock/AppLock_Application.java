package cincogatos.com.applock;

import android.app.Application;

import com.github.orangegangsters.lollipin.lib.managers.LockManager;

/**
 * Created by yeray697 on 14/12/16.
 */

public class AppLock_Application extends Application {
    LockManager<AppLock_Activity> lockManager;
    @Override
    public void onCreate() {
        super.onCreate();
        lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, AppLock_Activity.class);
    }
    public LockManager<AppLock_Activity> getLockManager() {
        return lockManager;
    }
}
