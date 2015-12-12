package lab.computing.khurshid.khurshidwebdelegation.management;

import android.content.Context;

import java.util.List;

import lab.computing.khurshid.khurshidwebdelegation.dto.RequestStatus;
import lab.computing.khurshid.khurshidwebdelegation.dto.WebRequest;


public class WebDelegateManager {

	Context context;
	DatabasePersistenceManager persistenceManager;

	public WebDelegateManager(Context context) {
		super();
		this.context = context;
		persistenceManager = new DatabasePersistenceManager(context);
	}

	public boolean delegateWebRequest(WebRequest webRequest) {
		return persistenceManager.save(webRequest);
	}

	public boolean updateWebRequest(WebRequest webRequest) {
		return persistenceManager.update(webRequest);
	}

	public WebRequest getWebRequest(int id) {
		return persistenceManager.getWebRequest(id);
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
}
