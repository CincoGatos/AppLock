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
    private ListBlockedAppAdapter listBlockedAppAdapter;
    private ListUnblokedAppAdapter listUnblokedAppAdapter;
    private TabLayout tabLayout;
    private Toolbar toolbar;

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

        listUnblokedAppAdapter = new ListUnblokedAppAdapter(Home_Activity.this, installedApps.getUnBlockedAppsList());
        fragmentListUnBlocked = FragmentListUnBlocked.newInstance(listUnblokedAppAdapter);

        listBlockedAppAdapter = new ListBlockedAppAdapter(Home_Activity.this, installedApps.getBlockedAppsList());
        fragmentListBlocked = FragmentListBlocked.newInstance(listBlockedAppAdapter);

        adapterFragmentPager = new AdapterFragmentPager(getSupportFragmentManager(), Home_Activity.this,
                fragmentListBlocked, fragmentListUnBlocked);

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapterFragmentPager);
        


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
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
