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
    private ArrayList<AppInfo> blockedAppsList;
    private ArrayList<AppInfo> unBlockedAppsList;
    private static ArrayList<String> blockedApps = new ArrayList<>();
    private static ArrayList<String> unlockedApps = new ArrayList<>();
    private Context context;
    private static InstalledApps instance;


    public static InstalledApps getInstance(Context context){

        if(instance == null){

            instance = new InstalledApps(context);
        }

        return instance;
    }


    private InstalledApps(Context context){

        this.context = context;
        getBlockedApps();
        installedApps = getInstalledApps();
        separeApps();
    }


    public ArrayList<AppInfo> getBlockedAppsList(){

        return blockedAppsList;
    }

    public ArrayList<AppInfo> getUnBlockedAppsList(){

        return unBlockedAppsList;
    }

    public ArrayList<AppInfo> getInstalledApplications(){

        return installedApps;
    }

    private void separeApps(){

        blockedAppsList = new ArrayList<AppInfo>();
        unBlockedAppsList = new ArrayList<AppInfo>();

        for (AppInfo tmp: installedApps){

            if(tmp.isBlocked()){

                blockedAppsList.add(tmp);

            }else {

                unBlockedAppsList.add(tmp);
            }
        }
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
        blockedApps.add("com.android.camera");

    }

    public static boolean isBlocked(String packageName){
        boolean result = blockedApps.contains(packageName);

        if (result) {
            result = !unlockedApps.contains(packageName);
        }
        return result;
    }

    public static void unlockApp(String packageName) {
        if (!unlockedApps.contains(packageName))
            unlockedApps.add(packageName);
    }
}
