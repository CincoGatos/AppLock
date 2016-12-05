package cincogatos.com.applock;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.util.Comparator;


public class AppInfo {


    //Fields
    private String appName;
    private String packageName;
    private Drawable icon;
    private Intent intent;
    private boolean bloked;

    //Comparators
    public static final Comparator<AppInfo> BLOCKED_COMPARATOR_ASC = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo appInfo, AppInfo t1) {
            return 0;
        }
    };
    public static final Comparator<AppInfo> BLOCKED_COMPARATOR_DES = new Comparator<AppInfo>() {
           @Override
           public int compare(AppInfo appInfo, AppInfo t1) {
               return 0;
           }
       };
    public static final Comparator<AppInfo> NAME_COMPARATOR_ASC = new Comparator<AppInfo>() {
              @Override
              public int compare(AppInfo appInfo, AppInfo t1) {
                  return 0;
              }
          };
    public static final Comparator<AppInfo> NAME_COMPARATOR_DES = new Comparator<AppInfo>() {
              @Override
              public int compare(AppInfo appInfo, AppInfo t1) {
                  return 0;
              }
          };

    //Getters and Setters
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public boolean isBloked() {
        return bloked;
    }

    public void setBloked(boolean bloked) {
        this.bloked = bloked;
    }

    //Construct
    public AppInfo(String appName, String packageName, Drawable icon, Intent intent, boolean bloked) {
          this.appName = appName;
          this.packageName = packageName;
          this.icon = icon;
          this.intent = intent;
          this.bloked = bloked;
      }

    //Methods
    public static String getForegroundApp(Context context){

        return null;
    }

    public static AppInfo getAppInfoByPackageName(Context context, String packageName){

        return null;
    }



}
