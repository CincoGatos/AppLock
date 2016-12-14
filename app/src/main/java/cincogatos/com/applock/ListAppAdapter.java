package cincogatos.com.applock;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amador on 14/12/16.
 */

public class ListAppAdapter extends ArrayAdapter<AppInfo> {

    private Context context;
    private ArrayList<AppInfo> localList;
    public static final int ORDERBY_NAME_ASC = 0;
    public static final int ORDERBY_NAME_DES = 1;
    public static final int ONLY_BLOKED = 0;
    public static final int ONLY_UNBLOKED = 1;
    public static final int ALL_APP = 2;
    public AdapterCallBack callBack;
    public interface AdapterCallBack{

        void onUnBlockedApp(String packageName);

        void onBlockedApp(String packageName);
    }

    public ListAppAdapter(Context context, ArrayList<AppInfo> dataList, AdapterCallBack callBack) {
        super(context, R.layout.item_list_app, new ArrayList<AppInfo>(dataList));

        this.context = context;
        this.localList = dataList;
        this.callBack = callBack;

    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        final AppInfoHolder holder;

        if(rootView == null){

            rootView = LayoutInflater.from(this.context).inflate(R.layout.item_list_app, null);
            holder = new AppInfoHolder();

            holder.imvAppIcon = (ImageView)rootView.findViewById(R.id.imvAppIcon);
            holder.txvAppName = (TextView)rootView.findViewById(R.id.txvAppName);
            holder.imvPadLock = (ImageView)rootView.findViewById(R.id.imvPadlock);
            holder.txvAppSystem = (TextView)rootView.findViewById(R.id.txvAppSystem);

            rootView.setTag(holder);

        }else {

            holder = (AppInfoHolder)rootView.getTag();
        }

        holder.imvAppIcon.setImageDrawable(getItem(position).getIcon());
        holder.imvPadLock.setImageResource(R.drawable.padlock_close);
        holder.txvAppName.setText(getItem(position).getAppname());

        if(getItem(position).isSystemApp()){

            holder.txvAppSystem.setText(R.string.system_app);

        }else{

            holder.txvAppSystem.setText(R.string.non_system_app);
        }

        if(getItem(position).isBlocked()){

            holder.imvPadLock.setImageResource(R.drawable.padlock_close);

        }else {

            holder.imvPadLock.setImageResource(R.drawable.padlock_open);
        }

        holder.imvPadLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    if (AppInfo.doIHavePermission(getContext())){
                        clickEvent(position);
                    } else {
                        showDialog();
                    }
                } else {
                    clickEvent(position);
                }

            }
        });


        return rootView;

    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.non_permissions_dialog);
        dialog.setMessage(R.string.non_permissions_dialog_message);
        dialog.setPositiveButton(R.string.allow_permissions_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppInfo.setPermission(getContext());
            }
        });
        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void clickEvent(int position){

        AppInfo appInfo = getItem(position);
        appInfo.setBlocked(!appInfo.isBlocked());
        if(callBack != null){

           if(appInfo.isBlocked()){

               callBack.onBlockedApp(appInfo.getPackageName());

           }else {

               callBack.onUnBlockedApp(appInfo.getPackageName());
           }
        }
    }

    //Instance Methods
    public void orderBy(int typeOrder){

        switch (typeOrder){

            case ORDERBY_NAME_ASC:
                sort(AppInfo.NAME_COMPARATOR_ASC);
                break;
            case ORDERBY_NAME_DES:
                sort(AppInfo.NAME_COMPARATOR_DES);
                break;
        }
    }

    public void filterBy(int typeFilter){

        switch (typeFilter){

            case ONLY_BLOKED:
                loadBlokedApps();
                break;
            case ONLY_UNBLOKED:
                loadUnBlokedApps();
                break;
            case ALL_APP:
                loadApps();
                break;

        }

    }

    public void filerBy(String filterText){

        clear();

        for (AppInfo tmp : this.localList) {

            if (tmp.getAppname().toUpperCase().startsWith(filterText.toUpperCase())) {

                add(tmp);
            }
        }
    }

    private void loadApps() {

        clear();
        addAll(this.localList);
    }

    private void loadUnBlokedApps() {

        clear();

        for(AppInfo tmp: this.localList){

            if (!tmp.isBlocked()){

                add(tmp);
            }
        }

    }

    private void loadBlokedApps() {

        clear();

        for(AppInfo tmp: this.localList){

            if (tmp.isBlocked()){

                add(tmp);
            }
        }
    }

    class AppInfoHolder{

        ImageView imvAppIcon, imvPadLock;
        TextView txvAppName;
        TextView txvAppSystem;
    }
}
