package cincogatos.com.applock;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;


public class ListeningService extends Service {

    private final static int INTERVAL_TIME = 100;
    private boolean active;

    private void checkApplication(){
        String app = "";
        while (active) {
            try {
                app = AppInfo.getForegroundApp(ListeningService.this);
                if (InstalledApps.isBlocked(app)) {
                    openLocker(app);
                }
                Thread.sleep(INTERVAL_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void openLocker(String packageName){
        Intent intent = new Intent (ListeningService.this, Lock_Activity.class);
        intent.putExtra("app",packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN);
        startActivity(intent);
    }


    Thread thread = new Thread() {
        @Override
        public void run() {
            checkApplication();
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        active = false;
        thread.interrupt();
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle args = intent.getExtras();
        if (args != null) {
            String app = args.getString("appunlocked", "");
            if (app != "") {
                InstalledApps.unlockApp(app);
            }
        }
        active = true;
        if (!thread.isAlive())
            thread.start();
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();

        return START_REDELIVER_INTENT;
    }
}
