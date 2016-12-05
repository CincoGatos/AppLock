package cincogatos.com.applock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
}
