package cincogatos.com.applock;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Amador on 05/12/2016.
 */
public class ListeningService extends Service {

    private boolean active;

    private void checkApplication(){
        String app = "";
        while (active) {
            try {
                app = AppInfo.getForegroundApp(ListeningService.this);
                Log.d("TAG",app);
                if (InstalledApps.isBlocked(app)) {
                    Log.d("TAG","App bloqueada");
                    openLocker(app);
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showHomeScreen(){
        Log.d("TAG","No se ha desbloqueado");

    }

    public static void openApp(){
        Log.d("TAG","Desbloqueado correctamente");

    }

    private void openLocker(String packageName){
        Intent intent = new Intent (ListeningService.this, Lock_Activity.class);
        intent.putExtra("app",packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        active = true;
        if (!thread.isAlive())
            thread.start();
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();

        return START_REDELIVER_INTENT;
    }
}
