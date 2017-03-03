package sample;

import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by ibrar on 2/23/17.
 */
public interface ChatSocketI extends MainSocketI {
    public void sendMessage(String userID,MessageModel messageModel);
    public void setUserData(ObservableList<UserDataModel> userListData);
    public void onUserStatusChange(String userID,UserDataModel userData);
    public void setMessageList(ObservableList<MessageModel> messageList);
    public void getUserData();
}
