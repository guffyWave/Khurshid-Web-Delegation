package lab.computing.khurshid.khurshidwebdelegation.management;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.computing.khurshid.khurshidwebdelegation.dto.RequestStatus;
import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;


public class WebDelegateManager {

    Context context;
    DatabasePersistenceManager persistenceManager;

    Gson gson;

    public WebDelegateManager(Context context) {
        super();
        this.context = context;
        persistenceManager = new DatabasePersistenceManager(context);
        gson = new Gson();
    }

    public Object delegateWebRequest(final WebRequest webRequest, final Class responseClass, final WebDelegateResponseListener webDelegateResponseListener, Response.ErrorListener responseErrorListener) {


        // if internet is available then
        // instant request with volley
        // else delegate it to web request manager

        //we need a success response handler and error response handler

        //we need to decide what to do if caching is enabled or npt

        // offline support is needed or not


        if (isNetworkAvailable() == true) {
            delegateToVolley(webRequest, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println(response);

                    //send to the response
                    if (response != null) {
                        if (!response.equals("")) {
                            webDelegateResponseListener.onResponse(gson.fromJson(response, responseClass));
                        }
                    }

                    //---->> update or save the response
                    List<WebRequest> webRequestList = getWebRequest(webRequest.getUrl());
                    if (webRequestList != null && webRequestList.size() > 0) {
                        WebRequest wr = webRequestList.get(0);
                        wr.setResponseString(response);
                        persistenceManager.update(wr);
                    } else {
                        persistenceManager.save(webRequest);
                    }
                }
            }, responseErrorListener);
        }

        //persistenceManager.update(webRequest);

        List<WebRequest> webRequestList = getWebRequest(webRequest.getUrl());
        if (webRequestList != null && webRequestList.size() > 0) {
            WebRequest wr = webRequestList.get(0);
            String responseString = wr.getResponseString();
            if (responseString != null) {
                if (!responseString.equals("")) {
                    return gson.fromJson(responseString, responseClass);
                }
            }
        }
        return null;
    }

    public boolean updateWebRequest(WebRequest webRequest) {
        return persistenceManager.update(webRequest);
    }

    public WebRequest getWebRequest(int id) {
        return persistenceManager.getWebRequest(id);
    }

    public List<WebRequest> getWebRequest(String url) {
        return persistenceManager.getWebRequest(url);
    }

    public List<WebRequest> getWebRequests() {
        return persistenceManager.getAllWebRequest();
    }

    public List<WebRequest> getPendingWebRequests() {
        return persistenceManager.getAllWebRequest(RequestStatus.PENDING);
    }

    public List<WebRequest> getCompletedWebRequests() {
        return persistenceManager.getAllWebRequest(RequestStatus.SUCCESS);
    }

    public List<WebRequest> getErrorWebRequests() {
        return persistenceManager.getAllWebRequest(RequestStatus.ERROR);
    }

    public boolean deleteAllWebRequests() {
        return persistenceManager.deleteAllWebRequest();
    }

    public boolean deleteWebRequests(int id) {
        return persistenceManager.deleteById(id);
    }


    public boolean deleteWebRequestsByURL(String url) {
        return persistenceManager.delete(persistenceManager.getWebRequest(url).get(0));
    }


    private boolean isNetworkAvailable() {
        return WebDelegationApp.isNetworkAvailable();
    }

    private void delegateToVolley(WebRequest webRequest, Response.Listener responseListener, Response.ErrorListener responseErrorListener) {
        final HashMap<String, String> params = webRequest.getWebParams();
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, webRequest.getUrl(), responseListener, responseErrorListener) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        requestQueue.add(request);
    }


    public interface WebDelegateResponseListener {
        public void onResponse(Object object);
    }
}
