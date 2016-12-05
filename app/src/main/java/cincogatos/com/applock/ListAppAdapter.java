package cincogatos.com.applock;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        super(context, 0, appInfoList);
        this.context = context;
        this.localList = new ArrayList<AppInfo>(appInfoList);
    }

    //Overray Methods
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    //Instance Methods
    public int orderBy(int typeOrder){

        return 0;
    }
}
