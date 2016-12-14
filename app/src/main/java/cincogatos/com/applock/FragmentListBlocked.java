package cincogatos.com.applock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by amador on 14/12/16.
 */

public class FragmentListBlocked extends Fragment {

    private ListBlockedAppAdapter adapter;
    private ListView listApps;



    public static FragmentListBlocked newInstance(ListBlockedAppAdapter adapter) {

        FragmentListBlocked fragment = new FragmentListBlocked();
        fragment.setAdapter(adapter);
        return fragment;
    }

    private void setAdapter(ListBlockedAppAdapter adapter){

        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blocked_list, null);

        listApps = (ListView)rootView.findViewById(R.id.listBlocked);
        listApps.setAdapter(adapter);

        return rootView;
    }
}
