package lab.computing.khurshid.khurshidwebdelegation;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import lab.computing.khurshid.khurshidwebdelegation.dto.WebParam;
import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;
import lab.computing.khurshid.khurshidwebdelegation.management.WebDelegateManager;

public class MainActivity extends AppCompatActivity {

    WebDelegateManager webDelegateManager;

    // ------View Refrences-------->>
    EditText urlEditText, employeeNameEditText, employeeEmailEditText,
            employeePhoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webDelegateManager = new WebDelegateManager(getApplicationContext());

        // -------referingview--->>
        urlEditText = (EditText) findViewById(R.id.urlEditText);
        employeeNameEditText = (EditText) findViewById(R.id.employeeNameEditText);
        employeeEmailEditText = (EditText) findViewById(R.id.employeeEmailEditText);
        employeePhoneEditText = (EditText) findViewById(R.id.employeePhoneEditText);

    }

    public void saveData(View v) {

        WebRequest webRequest = new WebRequest(urlEditText.getText().toString());
        // webRequest.getWebParams().put("company_id", "5");
        // webRequest.getWebParams().put("name",
        // employeeNameEditText.getText().toString());
        // webRequest.getWebParams().put("email",
        // employeeEmailEditText.getText().toString());
        // webRequest.getWebParams().put("phone",
        // employeePhoneEditText.getText().toString());

        webRequest.getWebParams().put("latitude", "23.565");
        webRequest.getWebParams().put("longitude", "34.342");
        webRequest.getWebParams().put("ACCIDENT_APP_USER_ID", "1");
        webRequest.getWebParams().put("placeName", "Ambala");

        webDelegateManager.delegateWebRequest(webRequest);

        new ExportDatabaseFileTask().execute("");

    }

    public void getData(View v) {

        List<WebRequest> webRequests = webDelegateManager.getWebRequests();

        for (WebRequest webRequest : webRequests) {
            WebParam<String, String> param = webRequest.getWebParams();

            Toast.makeText(
                    getApplicationContext(),
                    webRequest.getUrl() + " " + webRequest.getRequestStatus()
                            + " " + webRequest.getRequestTimeStamp(),
                    Toast.LENGTH_LONG).show();

        }

    }

    public void deleteAll(View v) {
        webDelegateManager.deleteAllWebRequests();

    }

    private void copyDataBaseFromSdCard() {
        try {
            String DB_PATH = "data/data/" + getPackageName() + "/databases/";
            String DB_NAME = "WebDB.db";

            String yourDbFileNamePresentInSDCard = "/mnt/sdcard/AppXpertsWebDataBase.db";
            File file = new File(yourDbFileNamePresentInSDCard);
            // Open your local db as the input stream
            InputStream myInput = new FileInputStream(file);

            // Path to created empty db
            String outFileName = DB_PATH + DB_NAME;

            // Opened assets database structure
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {

        }
    }

    // private SQLiteDatabase getDB() {
    // String DB_NAME = "WebDB.db";
    // return openOrCreateDatabase(DB_NAME, SQLiteDatabase.OPEN_READWRITE,
    // null);
    // }

    private class ExportDatabaseFileTask extends
            AsyncTask<String, Void, Boolean> {
        // private final ProgressDialog dialog = new ProgressDialog(g);

        // can use UI thread here
        protected void onPreExecute() {
            // this.dialog.setMessage("Exporting database...");
            // this.dialog.show();
        }

        // automatically done on worker thread (separate from UI thread)
        protected Boolean doInBackground(final String... args) {

            File dbFile = new File(
                    Environment.getDataDirectory()
                            + "/data/com.appxperts.appxpertswebdelegate/databases/AppXpertsWebDataBase.db");

            File exportDir = new File(
                    Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            File file = new File(exportDir, dbFile.getName());

            try {
                file.createNewFile();
                this.copyFile(dbFile, file);
                return true;
            } catch (IOException e) {
                Log.e("mypck", e.getMessage(), e);
                return false;
            }
        }

        // can use UI thread here
        protected void onPostExecute(final Boolean success) {
            // if (this.dialog.isShowing()) {
            // this.dialog.dismiss();
            // }
            if (success) {
                Toast.makeText(getApplicationContext(), "Export successful!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Export failed",
                        Toast.LENGTH_SHORT).show();
            }
        }

        void copyFile(File src, File dst) throws IOException {
            FileChannel inChannel = new FileInputStream(src).getChannel();
            FileChannel outChannel = new FileOutputStream(dst).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } finally {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            }
        }

    }
}
