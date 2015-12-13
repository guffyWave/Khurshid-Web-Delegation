package lab.computing.khurshid.khurshidwebdelegation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;
import lab.computing.khurshid.khurshidwebdelegation.management.WebDelegateManager;
import lab.computing.khurshid.khurshidwebdelegation.requestresponse.Friend;
import lab.computing.khurshid.khurshidwebdelegation.requestresponse.GetFriendsRequestResponse;

public class MainActivity extends AppCompatActivity {

    WebDelegateManager webDelegateManager;

    // ------View Refrences-------->>
    ListView friendsListView;


    ArrayAdapter<String> adapter;

    ArrayList<String> friendsNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webDelegateManager = new WebDelegateManager(getApplicationContext());

        // -------referingview--->>
        friendsListView = (ListView) findViewById(R.id.friendsListView);
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_text, friendsNameList);
        friendsListView.setAdapter(adapter);


        getData();
    }

    public void getData() {

        WebRequest webRequest = new WebRequest("http://crossappsender-1155.appspot.com/getFriends");
        webRequest.getWebParams().put("userID", "1");

        GetFriendsRequestResponse getFriendsRequestResponse = (GetFriendsRequestResponse) webDelegateManager.delegateWebRequest(webRequest, GetFriendsRequestResponse.class, new WebDelegateManager.WebDelegateResponseListener() {
            @Override
            public void onResponse(Object object) {
                GetFriendsRequestResponse getFriendsRequestResponse = (GetFriendsRequestResponse) object;
                friendsNameList.clear();
                if (getFriendsRequestResponse != null) {
                    for (Friend f :
                            getFriendsRequestResponse.getFriends()) {
                        friendsNameList.add(f.getDisplayName());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("VOLLEY ERROR " + error.toString());
            }
        });


        friendsNameList.clear();
        if (getFriendsRequestResponse != null) {
            for (Friend f :
                    getFriendsRequestResponse.getFriends()) {
                friendsNameList.add(f.getDisplayName());
            }
        }
        adapter.notifyDataSetChanged();


        //   new ExportDatabaseFileTask().execute("");

    }


//    public void getData(View v) {
//
//        List<WebRequest> webRequests = webDelegateManager.getWebRequests();
//
//        for (WebRequest webRequest : webRequests) {
//            WebParam<String, String> param = webRequest.getWebParams();
//
//            Toast.makeText(
//                    getApplicationContext(),
//                    webRequest.getUrl() + " " + webRequest.getRequestStatus()
//                            + " " + webRequest.getRequestTimeStamp(),
//                    Toast.LENGTH_LONG).show();
//
//        }
//
//    }

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
