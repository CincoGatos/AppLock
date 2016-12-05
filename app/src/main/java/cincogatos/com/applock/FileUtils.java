package cincogatos.com.applock;


import android.util.Base64;

public class FileUtils {

    private static final String PASSWORD_FILE = "file_data.txt";
    private static final String APP_FILE = "file_app.txt";
    public static final int PASSWORD_FILE_CODE = 0;
    public static final int PASSWORD_APP_CODE = 1;

    public static void save(String path, String msg, int action){

        String msgEncrip = encrypt(msg);
        StreamIO streamIO = new StreamIO(path);
        switch (action){

            case PASSWORD_FILE_CODE:
                streamIO.writeFile(PASSWORD_FILE, msgEncrip, false);
                break;
            case PASSWORD_APP_CODE:
                streamIO.writeFile(APP_FILE, msgEncrip, false);
                break;
        }

    }

    public static String load(String path, int action){

        StreamIO streamIO = new StreamIO(path);
        String text = null;
        switch (action){

                 case PASSWORD_FILE_CODE:
                     text = decrypt(streamIO.readInFile(PASSWORD_FILE));
                     break;
                 case PASSWORD_APP_CODE:
                     text = decrypt(streamIO.readInFile(APP_FILE));
                     break;
             }

        return text;
    }

    private static String encrypt(String message){

      return  Base64.encodeToString(message.getBytes(), Base64.DEFAULT);
    }

    private static String decrypt(String message){

        return new String(Base64.decode(message, Base64.DEFAULT));
    }


}
