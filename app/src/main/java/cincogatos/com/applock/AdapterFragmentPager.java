package cincogatos.com.applock;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by amador on 14/12/16.
 */

public class AdapterFragmentPager extends FragmentStatePagerAdapter {

    private Context context;
    private FragmentListUnBlocked fragmentListUnBlocked;
    private FragmentListBlocked fragmentListBlocked;

    public AdapterFragmentPager(FragmentManager fm, Context context, FragmentListBlocked fb, FragmentListUnBlocked fub) {
        super(fm);
        this.context = context;
        this.fragmentListBlocked = fb;
        this.fragmentListUnBlocked = fub;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return fragmentListUnBlocked;
            case 1:
                return fragmentListBlocked;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getStringArray(R.array.tab_titles)[position];
    }
}
