package cincogatos.com.applock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class HomeActivity extends AppCompatActivity {

    private ListView listApp;
    private ListAppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //Methods
    private void blockApp(String packageName){


    }
}
