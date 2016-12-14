package cincogatos.com.applock;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;

public class Home_Activity extends AppCompatActivity {
    InstalledApps apps;
    static final String TAG = "TAG";
    private ListView appList;
    private ListAppAdapter adapter;
    private InstalledApps installedApps;
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
        installedApps = new InstalledApps(Home_Activity.this);
        adapter = new ListAppAdapter(Home_Activity.this, installedApps.getInstalledApplications());
        appList = (ListView)findViewById(R.id.listApp);
        appList.setAdapter(adapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_filter_by_bloked:
                adapter.filterBy(ListAppAdapter.ONLY_BLOKED);
                break;
            case R.id.action_filter_by_unbloked:
                adapter.filterBy(ListAppAdapter.ONLY_UNBLOKED);
                break;
            case R.id.action_all_apps:
                adapter.filterBy(ListAppAdapter.ALL_APP);
                break;
            case R.id.action_menu_sort:
                adapter.orderBy(ListAppAdapter.ORDERBY_NAME_ASC);
                break;
            case R.id.action_order_by_des:
                adapter.orderBy(ListAppAdapter.ORDERBY_NAME_DES);
                break;
        }


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
                adapter.filerBy(newText);
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
