package lab.computing.khurshid.khurshidwebdelegation.management;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

import lab.computing.khurshid.khurshidwebdelegation.dto.RequestStatus;
import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;

public class DatabasePersistenceManager {
    Context context;
    DatabaseHelper dbHelper;
    RuntimeExceptionDao<WebRequest, Integer> webRequestRuntimeExceptionDao;

    public DatabasePersistenceManager(Context context) {
        super();
        this.context = context;
        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        webRequestRuntimeExceptionDao = dbHelper
                .getWebRequestRuntimeExceptionDao();
    }

    public boolean save(WebRequest webRequest) {
        int r = webRequestRuntimeExceptionDao.create(webRequest);
        return ((r > 0) ? true : false);
    }

    public boolean update(WebRequest webRequest) {
        int r = webRequestRuntimeExceptionDao.update(webRequest);
        return ((r > 0) ? true : false);
    }

    public WebRequest getWebRequest(int id) {
        return webRequestRuntimeExceptionDao.queryForId(id);
    }

    public List<WebRequest> getAllWebRequest() {
        return webRequestRuntimeExceptionDao.queryForAll();
    }

    public List<WebRequest> getAllWebRequest(RequestStatus requestStatus) {
        return webRequestRuntimeExceptionDao.queryForEq("requestStatus",
                requestStatus);
    }

    public boolean deleteAllWebRequest() {
        List<WebRequest> webRequests = getAllWebRequest();
        for (WebRequest webRequest : webRequests) {
            deleteById(webRequest.getId());
        }
        return true;
    }

    public boolean deleteById(int id) {
        int r = webRequestRuntimeExceptionDao.deleteById(id);
        return ((r > 0) ? true : false);
    }
}
