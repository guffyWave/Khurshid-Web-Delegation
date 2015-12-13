
package lab.computing.khurshid.khurshidwebdelegation.requestresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Friend {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("gcmID")
    @Expose
    private String gcmID;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("displayPicFileURI")
    @Expose
    private String displayPicFileURI;
    @SerializedName("userActivationStatus")
    @Expose
    private Boolean userActivationStatus;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * 
     * @param emailID
     *     The emailID
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    /**
     * 
     * @return
     *     The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 
     * @param phoneNumber
     *     The phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 
     * @return
     *     The gcmID
     */
    public String getGcmID() {
        return gcmID;
    }

    /**
     * 
     * @param gcmID
     *     The gcmID
     */
    public void setGcmID(String gcmID) {
        this.gcmID = gcmID;
    }

    /**
     * 
     * @return
     *     The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *     The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     * @return
     *     The displayPicFileURI
     */
    public String getDisplayPicFileURI() {
        return displayPicFileURI;
    }

    /**
     * 
     * @param displayPicFileURI
     *     The displayPicFileURI
     */
    public void setDisplayPicFileURI(String displayPicFileURI) {
        this.displayPicFileURI = displayPicFileURI;
    }

    /**
     * 
     * @return
     *     The userActivationStatus
     */
    public Boolean getUserActivationStatus() {
        return userActivationStatus;
    }

    /**
     * 
     * @param userActivationStatus
     *     The userActivationStatus
     */
    public void setUserActivationStatus(Boolean userActivationStatus) {
        this.userActivationStatus = userActivationStatus;
    }

}
