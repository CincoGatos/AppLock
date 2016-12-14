package cincogatos.com.applock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;


public class ListeningService extends Service {

    private final static int INTERVAL_TIME = 100;
    private boolean active;
    private final BroadcastReceiver myReceiver = new MyReceiver();

    private void checkApplication(){
        String app = "";
        while (active) {
            try {
                app = AppInfo.getForegroundApp(ListeningService.this);
                if (InstalledApps.getInstance().isBlocked(app)) {
                    openLocker(app);
                }
                Thread.sleep(INTERVAL_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void openLocker(String packageName){
        Intent intent = new Intent (ListeningService.this, AppLock_Activity.class);
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
        unregisterReceiver(myReceiver);
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle args = intent.getExtras();
        if (args != null) {
            String app = args.getString("appunlocked", "");
            if (app != "") {
                InstalledApps.getInstance().unlockApp(app);
            }
        }
        active = true;
        if (!thread.isAlive())
            thread.start();
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(myReceiver, filter);
    }
}
