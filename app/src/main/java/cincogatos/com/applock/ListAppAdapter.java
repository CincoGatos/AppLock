package cincogatos.com.applock;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAppAdapter extends ArrayAdapter<AppInfo> {

    //Camps
    private Context context;
    private ArrayList<AppInfo> localList;
    public static final int ORDERBY_NAME_ASC = 0;
    public static final int ORDERBY_NAME_DES = 1;
    public static final int ORDERBY_BLOKED_ASC = 2;
    public static final int ORDERBY_BLOKED_DES = 3;


    //Construct
    public ListAppAdapter(Context context, ArrayList<AppInfo> appInfoList) {
        super(context, R.layout.item_list_app);
        this.context = context;
        this.localList = new ArrayList<AppInfo>(appInfoList);
        addAll(appInfoList);
    }

    //Overray Methods
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = convertView;
        AppInfoHolder holder;

        if(convertView == null){

            holder = new AppInfoHolder();
            rootView = LayoutInflater.from(this.context).inflate(R.layout.item_list_app, null);
            holder.imvAppIcon = (ImageView)rootView.findViewById(R.id.imvAppIcon);
            holder.imvPadLock = (ImageView)rootView.findViewById(R.id.imvPadlock);
            holder.txvNameIcon = (TextView)rootView.findViewById(R.id.txvAppName);
            rootView.setTag(holder);

        }else{

            holder = (AppInfoHolder)rootView.getTag();
        }

        holder.imvAppIcon.setImageDrawable(this.localList.get(position).getIcon());
        holder.txvNameIcon.setText(this.localList.get(position).getAppname());

        if(this.localList.get(position).isBlocked()){

            holder.imvPadLock.setImageResource(R.drawable.padlock_close);

        }else{

            holder.imvPadLock.setImageResource(R.drawable.padlock_open);
        }

        return rootView;
    }

    //Instance Methods
    public int orderBy(int typeOrder){

        return 0;
    }

    class AppInfoHolder{

        ImageView imvAppIcon, imvPadLock;
        TextView txvNameIcon;
    }
}
