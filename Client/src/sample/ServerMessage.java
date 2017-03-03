package sample;

import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ibrar on 2/22/17.
 */
public class ServerMessage implements Serializable {
    String messageType="";
    @Nullable
    MessageModel messageModel = new MessageModel();
    @Nullable
    ArrayList<UserDataModel> userList=new ArrayList<UserDataModel>();
    @Nullable
    String userID="",userPassword="";

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }

    public ArrayList<UserDataModel> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserDataModel> userList) {
        this.userList = userList;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
