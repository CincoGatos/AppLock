package cincogatos.com.applock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class EncriptActivity extends AppCompatActivity {

    private Button btn;
    private EditText editText;
    private TextView app, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encript);

        btn = (Button)findViewById(R.id.btn);
        editText = (EditText)findViewById(R.id.edt);
        app = (TextView)findViewById(R.id.app);
        pass = (TextView)findViewById(R.id.pass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FileUtils.save(getApplicationContext().getFilesDir().getAbsolutePath(), app.getText().toString(), FileUtils.PASSWORD_APP_CODE);
                FileUtils.save(getApplicationContext().getFilesDir().getAbsolutePath(), pass.getText().toString(), FileUtils.PASSWORD_FILE_CODE);
                app.setText(FileUtils.load(getApplicationContext().getFilesDir().getAbsolutePath(),FileUtils.PASSWORD_APP_CODE)+" desde el fichero");
                pass.setText(FileUtils.load(getApplicationContext().getFilesDir().getAbsolutePath(),FileUtils.PASSWORD_FILE_CODE)+" desde el fichero");
            }
        });
    }
}
