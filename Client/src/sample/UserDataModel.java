package sample;

import java.io.Serializable;

/**
 * Created by ibrar on 2/22/17.
 */
public class UserDataModel implements Serializable {
    String userID,userStatus;
    public UserDataModel() {
    }
    public UserDataModel(String userID, String userStatus) {
        this.userID = userID;
        this.userStatus = userStatus;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
