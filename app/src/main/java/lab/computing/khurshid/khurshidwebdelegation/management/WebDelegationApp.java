package lab.computing.khurshid.khurshidwebdelegation.management;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.facebook.stetho.Stetho;

/**
 * Created by gufran on 13/12/15.
 */
public class WebDelegationApp extends Application {
    private static WebDelegationApp webDelegationApp;
    public static String TAG = "KHURSHID_WEB_DELEGATION";


    ///--------Volley Request Queue----->>
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        webDelegationApp = WebDelegationApp.this;
        Stetho.initializeWithDefaults(this);
    }

    public WebDelegationApp getInstance() {
        return webDelegationApp;
    }

    public static Context getAppContext() {
        return webDelegationApp.getApplicationContext();
    }


    static public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) webDelegationApp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] activeNetworkInfo = connectivityManager
                .getAllNetworkInfo();

        for (NetworkInfo networkInfo : activeNetworkInfo) {
            if (networkInfo.isConnectedOrConnecting()) {
                return networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }
}
