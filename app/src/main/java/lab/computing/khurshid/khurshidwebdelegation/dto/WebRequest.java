package lab.computing.khurshid.khurshidwebdelegation.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class WebRequest {
	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String url;
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	WebParam<String, String> webParams = new WebParam<String, String>();
	@DatabaseField
	String responseString;
	@DatabaseField(dataType = DataType.ENUM_STRING)
	RequestStatus requestStatus;
	@DatabaseField(dataType = DataType.DATE_STRING)
	Date requestTimeStamp;
	@DatabaseField(dataType = DataType.DATE_STRING)
	Date responseTimeStamp;

	public WebRequest() {
	}

	public WebRequest(String url) {
		super();
		this.url = url;
		this.requestStatus = RequestStatus.PENDING;
		this.requestTimeStamp = new Date();
	}

	public WebParam<String, String> getWebParams() {
		return webParams;
	}

	public void setWebParams(WebParam<String, String> webParams) {
		this.webParams = webParams;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public RequestStatus getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Date getRequestTimeStamp() {
		return requestTimeStamp;
	}

	public void setRequestTimeStamp(Date requestTimeStamp) {
		this.requestTimeStamp = requestTimeStamp;
	}

	public Date getResponseTimeStamp() {
		return responseTimeStamp;
	}

	public void setResponseTimeStamp(Date responseTimeStamp) {
		this.responseTimeStamp = responseTimeStamp;
	}

	@Override
	public String toString() {
		return "WebRequest [id=" + id + ", url=" + url + ", webParams="
				+ webParams + ", responseString=" + responseString
				+ ", requestStatus=" + requestStatus + ", requestTimeStamp="
				+ requestTimeStamp + ", responseTimeStamp=" + responseTimeStamp
				+ "]";
	}

}
