package cincogatos.com.applock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Lock_Activity extends AppCompatActivity {

    Button btTrue, btFalse;
    TextView tvApp;
    String app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        btTrue = (Button) findViewById(R.id.btTrue);
        btFalse = (Button) findViewById(R.id.btFalse);
        tvApp = (TextView) findViewById(R.id.tvApp);
        app = getIntent().getExtras().getString("app","Error");
        tvApp.setText(app);
        btTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openApp();
            }
        });

        btFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Error con la contraseña
            }
        });
    }

    private void openApp(){
        Intent intent = new Intent(this,ListeningService.class);
        intent.putExtra("appunlocked", app);
        startService(intent);
        finish();
        //TODO se vuelve a ejecutar el bloqueo al cerrar esta
    }

    private void showHomeScreen(){
        Intent startHomescreen=new Intent(Intent.ACTION_MAIN);
        startHomescreen.addCategory(Intent.CATEGORY_HOME);
        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startHomescreen);
    }

    @Override
    public void onBackPressed() {
        showHomeScreen();
    }
}
