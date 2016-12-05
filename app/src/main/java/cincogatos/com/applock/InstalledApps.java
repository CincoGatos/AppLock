package cincogatos.com.applock;


import android.content.Context;

import java.io.StringReader;
import java.util.ArrayList;

public class InstalledApps {


    private ArrayList<AppInfo> installedApps;
    private ArrayList<String> blockedApps;
    private Context context;



    public InstalledApps(Context context){

        this.context = context;
    }

    private ArrayList<AppInfo> getInstalledApps(boolean blocked){

        return null;
    }

    public ArrayList<AppInfo> getInstalledApps(){

          return null;
      }

    private ArrayList<String> getBlockedApps(){

        return null;
    }

    public static boolean isBlocked(String packageName){

        return false;
    }





}
