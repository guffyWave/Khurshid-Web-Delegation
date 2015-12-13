
package lab.computing.khurshid.khurshidwebdelegation.requestresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class GetFriendsRequestResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("messsage")
    @Expose
    private String messsage;
    @SerializedName("friends")
    @Expose
    private List<Friend> friends = new ArrayList<Friend>();

    /**
     * @return The result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return The messsage
     */
    public String getMesssage() {
        return messsage;
    }

    /**
     * @param messsage The messsage
     */
    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    /**
     * @return The friends
     */
    public List<Friend> getFriends() {
        return friends;
    }

    /**
     * @param friends The friends
     */
    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

}
