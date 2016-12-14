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

public class ListBlockedAppAdapter extends ArrayAdapter<AppInfo> {

    private Context context;
    private ArrayList<AppInfo> localList;
    public AdapterCallBack callBack;
    public interface AdapterCallBack{

        void onUnBlockedApp(String packageName);
    }

    public ListBlockedAppAdapter(Context context, ArrayList<AppInfo> dataList, AdapterCallBack callBack) {
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

        if(getItem(position).isSystemApp()){

            holder.txvAppSystem.setText(R.string.system_app);

        }else{

            holder.txvAppSystem.setText(R.string.non_system_app);
        }

        holder.imvPadLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    if (AppInfo.doIHavePermission(getContext())){
                        clickEvent(position,holder);
                    } else {
                        showDialog();
                    }
                } else {
                    clickEvent(position,holder);
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

            }
        });
        dialog.show();
    }

    private void clickEvent(int position, final ListBlockedAppAdapter.AppInfoHolder holder){
        final int pos = position;

        if(callBack != null){

            callBack.onUnBlockedApp(getItem(position).getPackageName());
        }
    }

    class AppInfoHolder{

        ImageView imvAppIcon, imvPadLock;
        TextView txvAppName;
        TextView txvAppSystem;
    }
}
