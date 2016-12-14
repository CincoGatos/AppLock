package cincogatos.com.applock;

import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;

/**
 * Created by yeray697 on 14/12/16.
 */

public class SettingsLock_Activity extends AppLockActivity {
    @Override
    public void showForgotDialog() {

    }

    @Override
    public void onPinFailure(int attempts) {

    }

    @Override
    public void onPinSuccess(int attempts) {
        finish();
    }
}
