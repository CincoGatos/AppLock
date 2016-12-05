package cincogatos.com.applock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home_Activity extends AppCompatActivity {
    InstalledApps apps;
    static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apps = new InstalledApps(Home_Activity.this);
        Log.d(TAG, String.valueOf(apps.getInstalledApplications().size()));
        for (AppInfo app: apps.getInstalledApplications())
            Log.d(TAG, app.getAppname() + "\n" + app.getPackageName() + "\n" + app.getIntent()+ "\n" + "Blocked: "+ app.isBlocked()+ "\n.");
    }
    public void startService(View view){
        Intent intent = new Intent(this,ListeningService.class);
        startService(intent);
    }
    public void stopService(View view){
        Intent intent = new Intent(this,ListeningService.class);
        stopService(intent);
    }
}
