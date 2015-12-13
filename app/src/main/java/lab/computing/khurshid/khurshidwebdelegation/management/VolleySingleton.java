package lab.computing.khurshid.khurshidwebdelegation.management;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by gufran on 13/12/15.
 */
public class VolleySingleton {
    private static VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    private VolleySingleton() {
    }

    public static VolleySingleton getInstance() {
        if (volleySingleton == null) {
            volleySingleton = new VolleySingleton();
            volleySingleton.requestQueue = Volley.newRequestQueue(WebDelegationApp.getAppContext());
        }

        return volleySingleton;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
