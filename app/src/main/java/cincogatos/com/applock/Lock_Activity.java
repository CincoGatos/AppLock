package cincogatos.com.applock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;

public class Lock_Activity extends AppLockActivity {
    final static int LIMIT = 3;
    private static final String TRY_COUNT = "count";
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Iniciando activity", Toast.LENGTH_SHORT).show();
        if (savedInstanceState != null)
            count = savedInstanceState.getInt(TRY_COUNT,0);
        else
            count = 0;
    }

    @Override
    public void onBackPressed() {
        showHomeScreen();
    }

    @Override
    public void showForgotDialog() {
        Toast.makeText(getApplicationContext(), "Forgot", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPinFailure(int attempts) {
        count++;
        if (count == 3) {
            limitReached();
        }
    }

    @Override
    public void onPinSuccess(int attempts) {
        openApp();
    }

    @Override
    public int getPinLength() {
        return super.getPinLength();//you can override this method to change the pin length from the default 4
    }

    private void openApp(){
        String app;
        Intent intent = new Intent(this,ListeningService.class);
        app = getIntent().getExtras().getString("app","Error");
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

    private void limitReached() {
        Toast.makeText(this, "Límite de intentos", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TRY_COUNT, count);
    }

}
