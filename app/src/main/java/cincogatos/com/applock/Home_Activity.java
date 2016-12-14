package cincogatos.com.applock;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;

public class Home_Activity extends AppCompatActivity {

    InstalledApps apps;
    static final String TAG = "TAG";
    private ListView appList;
    private InstalledApps installedApps;
    private FragmentListBlocked fragmentListBlocked;
    private FragmentListUnBlocked fragmentListUnBlocked;
    private ViewPager viewPager;
    private AdapterFragmentPager adapterFragmentPager;
    private ListAppAdapter listBlockedAppAdapter;
    private ListAppAdapter listUnBlockedAdapter;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ListAppAdapter.AdapterCallBack callBack = new ListAppAdapter.AdapterCallBack() {
        @Override
        public void onUnBlockedApp(String packageName) {

            AppInfo appInfo = AppInfo.getAppInfoByPackageName(Home_Activity.this, packageName);
            listBlockedAppAdapter.remove(appInfo);
            listUnBlockedAdapter.add(appInfo);
        }

        @Override
        public void onBlockedApp(String packageName) {

            AppInfo appInfo = AppInfo.getAppInfoByPackageName(Home_Activity.this, packageName);
            listBlockedAppAdapter.add(appInfo);
            listUnBlockedAdapter.remove(appInfo);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!((AppLock_Application)getApplicationContext()).getLockManager().getAppLock().isPasscodeSet())
        {
            savePin();
        } else {
            login();
        }
        startService();
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager)findViewById(R.id.pager);
        installedApps = InstalledApps.getInstance(Home_Activity.this);

        listUnBlockedAdapter = new ListAppAdapter(Home_Activity.this, installedApps.getUnBlockedAppsList(), callBack);
        fragmentListUnBlocked = FragmentListUnBlocked.newInstance(listUnBlockedAdapter);

        listBlockedAppAdapter = new ListAppAdapter(Home_Activity.this, installedApps.getBlockedAppsList(), callBack);
        fragmentListBlocked = FragmentListBlocked.newInstance(listBlockedAppAdapter);

        adapterFragmentPager = new AdapterFragmentPager(getSupportFragmentManager(), Home_Activity.this,
                fragmentListBlocked, fragmentListUnBlocked);

        tabLayout = (TabLayout)findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getStringArray(R.array.tab_titles)[0]));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getStringArray(R.array.tab_titles)[1]));
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapterFragmentPager);
        tabLayout.setupWithViewPager(viewPager, true);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int typeOrder = ListAppAdapter.ORDERBY_NAME_ASC;

        if(item.getItemId() == R.id.action_order_by_des){

            typeOrder = ListAppAdapter.ORDERBY_NAME_DES;
        }

        sort(typeOrder);

        return super.onOptionsItemSelected(item);
    }

    private void sort(int typeOrder) {

        if(fragmentListUnBlocked.isVisible()){

            listUnBlockedAdapter.orderBy(typeOrder);

        }else {

            listBlockedAppAdapter.orderBy(typeOrder);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_list, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_menu_find);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //permite modificar el hint que el EditText muestra por defecto
        searchView.setQueryHint(getText(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Home_Activity.this, "Prueba", Toast.LENGTH_SHORT).show();
                //se oculta el EditText
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(fragmentListBlocked.isVisible()){

                    listBlockedAppAdapter.filerBy(newText);

                }else {

                    listUnBlockedAdapter.filerBy(newText);
                }

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }





    public void startService(){
        Intent intent = new Intent(this,ListeningService.class);
        startService(intent);
    }
    public void stopService(){
        Intent intent = new Intent(this,ListeningService.class);
        stopService(intent);
    }

    public void savePin() {
        Intent intent = new Intent(Home_Activity.this, SettingsLock_Activity.class);
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
        startActivity(intent);
    }
    public void changePin() {
        Intent intent = new Intent(Home_Activity.this, SettingsLock_Activity.class);
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
        startActivity(intent);
    }
    public void login(){
        Intent intent = new Intent (Home_Activity.this, SettingsLock_Activity.class);
        intent.putExtra(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN);
        startActivity(intent);
    }
}
