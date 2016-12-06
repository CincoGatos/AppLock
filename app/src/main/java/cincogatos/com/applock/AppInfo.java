package cincogatos.com.applock;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class AppInfo implements Comparable<AppInfo> {


    //Fields
    private String appname;
    private String packageName;
    private Drawable icon;


    private boolean systemApp;
    private Intent intent;
    private boolean blocked;

    //Comparators
    public static final Comparator<AppInfo> NAME_COMPARATOR_ASC = new Comparator<AppInfo>() {
              @Override
              public int compare(AppInfo appInfo, AppInfo t1) {
                  return appInfo.getAppname().compareToIgnoreCase(t1.getAppname());
              }
          };
    public static final Comparator<AppInfo> NAME_COMPARATOR_DES = new Comparator<AppInfo>() {
              @Override
              public int compare(AppInfo appInfo, AppInfo t1) {
                  return t1.getAppname().compareToIgnoreCase(appInfo.getAppname());
              }
          };

    //Getters and Setters
    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSystemApp() {
        return systemApp;
    }

    public void setSystemApp(boolean systemApp) {
        this.systemApp = systemApp;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    //Construct
    public AppInfo(String appname, String packageName, Drawable icon,boolean systemApp, Intent intent, boolean blocked) {
        this.appname = appname;
        this.packageName = packageName;
        this.icon = icon;
        this.systemApp = systemApp;
        this.intent = intent;
        this.blocked = blocked;
      }

    public AppInfo(){

    }
    //Methods
    public static String getForegroundApp(Context context){
        String currentApp = "NULL";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            currentApp = tasks.get(0).topActivity.getPackageName();
        }


        return currentApp;
    }

    public static AppInfo getAppInfoByPackageName(Context context, String packageName){
        AppInfo appInfo = new AppInfo();
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo app = pm.getApplicationInfo(packageName, 0);

            appInfo.setIcon(pm.getApplicationIcon(app));
            appInfo.setAppname((String) pm.getApplicationLabel(app));
            appInfo.setPackageName(packageName);
            appInfo.setIntent(pm.getLaunchIntentForPackage(packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appInfo;
    }



    @Override
    public boolean equals(Object obj) {

        boolean result = false;

        if(obj != null){

            if(obj instanceof AppInfo){

                result = ((AppInfo)obj).getPackageName().equals(this.getPackageName());
            }
        }

        return result;
    }

    @Override
    public int compareTo(AppInfo o) {
        return this.getAppname().compareToIgnoreCase(o.getAppname());
    }
}
