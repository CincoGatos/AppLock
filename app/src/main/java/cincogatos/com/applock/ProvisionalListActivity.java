package cincogatos.com.applock;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProvisionalListActivity extends AppCompatActivity {


    private ListView appList;
    private ListAppAdapter adapter;
    private InstalledApps installedApps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provisional_list);

        installedApps = new InstalledApps(ProvisionalListActivity.this);
        adapter = new ListAppAdapter(ProvisionalListActivity.this, installedApps.getInstalledApplications());
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
                Toast.makeText(ProvisionalListActivity.this, "Prueba", Toast.LENGTH_SHORT).show();
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
}
