package cincogatos.com.applock;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class InstalledApps {


    private ArrayList<AppInfo> installedApps;
    private static ArrayList<String> blockedApps = new ArrayList<>();
    private Context context;


    public InstalledApps(Context context){

        this.context = context;
        getBlockedApps();
        installedApps = getInstalledApps();
    }

    public ArrayList<AppInfo> getInstalledApplications(){

        return installedApps;
    }

    private ArrayList<AppInfo> getInstalledApps(){
        final PackageManager pm = context.getPackageManager();
        ArrayList<AppInfo> res = new ArrayList<AppInfo>();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        boolean blocked;
        for(int i=0;i<packs.size();i++) {
            blocked = false;
            PackageInfo p = packs.get(i);
            AppInfo newAppInfo = new AppInfo();
            if((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                newAppInfo.setSystemApp(true);
            }
            newAppInfo.setAppname(p.applicationInfo.loadLabel(pm).toString());
            newAppInfo.setPackageName(p.packageName);
            newAppInfo.setIcon(p.applicationInfo.loadIcon(context.getPackageManager()));
            newAppInfo.setIntent(pm.getLaunchIntentForPackage(p.packageName));
            if (blockedApps.contains(newAppInfo.getPackageName()))
                blocked = true;
            newAppInfo.setBlocked(blocked);
            res.add(newAppInfo);
        }
        Log.d("TAG", String.valueOf(res.size()));
        return res;
    }

    private void getBlockedApps(){

        //TODO Desencriptar fichero, recorrer y devolver
        blockedApps.add("yrj.ayudasordomudo");

    }

    public static boolean isBlocked(String packageName){

        return blockedApps.contains(packageName);
    }
}
