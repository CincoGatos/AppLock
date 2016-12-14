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

public class FragmentListUnBlocked extends Fragment {


    private ListUnblokedAppAdapter adapter;
    private ListView listApp;

    public static FragmentListUnBlocked newInstance(ListUnblokedAppAdapter adapter) {

        FragmentListUnBlocked fragment = new FragmentListUnBlocked();
        fragment.setAdapter(adapter);
        return fragment;
    }

    public void setAdapter(ListUnblokedAppAdapter adapter){

        this.adapter = adapter;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_unbloked_list, null);

        listApp = (ListView)rootView.findViewById(R.id.listUnBloked);
        listApp.setAdapter(adapter);

        return rootView;
    }
}
