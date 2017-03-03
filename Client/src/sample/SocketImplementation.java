package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ibrar on 2/23/17.
 */
public class SocketImplementation implements LoginSocketI, ChatSocketI {


    Socket clientSocket ;
    ObjectInputStream in ;
    ObjectOutputStream out;
    final String serverName = "localhost";
    final int portNumber = 6066;

    static SocketImplementation singleton;

    ObservableList<MessageModel> msgList;
    ObservableList<UserDataModel> userListData = FXCollections.observableArrayList();
    HashMap<String,Integer> userIndex = new HashMap<String,Integer>();

    LoginCallback callback;


    public static SocketImplementation getInstance(){
        if(singleton==null){
            singleton = new SocketImplementation();
        }
        return  singleton;
    }

    @Override
    public void initSocket() {

        if(clientSocket==null){
            try {
                clientSocket = new Socket("localhost",6066);
                OutputStream outStream = clientSocket.getOutputStream();
                out = new ObjectOutputStream(outStream);
                InputStream inputStream = clientSocket.getInputStream();
                in = new ObjectInputStream(inputStream);
                while (clientSocket.isConnected()){
                    ServerMessage message = (ServerMessage) in.readObject();
                    switch (message.getMessageType()){
                        case "UserMessage":
                            MessageModel messageModel = message.getMessageModel();
                            msgList.add(messageModel);
                            break;
                        case "AuthSuccess":
                            if(message.getMessageModel().getMsgTxt().equals("Found")){
                                Main.userID = message.getMessageModel().getMsgBy();
                            }
                            callback.onLogSuccess(message.getMessageModel().getMsgTxt());
                            break;
                        case "UserList":
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    userListData.clear();
                                    for(UserDataModel user : message.getUserList()){
                                        if(!user.getUserID().equals(Main.userID)){
                                            userListData.add(user);
                                        }
                                    }
                                }
                            });

                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void RegisterUSer(String userID, String password) {

        ServerMessage message = new ServerMessage();
        message.setMessageType("RegisterR");
        message.setUserID(userID);
        message.setUserPassword(password);
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void LoginUser(String userID, String password) {
        ServerMessage message = new ServerMessage();
        message.setMessageType("LoginR");
        message.setUserID(userID);
        message.setUserPassword(password);
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setLoginCallback(LoginCallback callback) {
        this.callback = callback;
    }

    @Override
    public void sendMessage(String userID, MessageModel messageModel) {

        if(out!=null){
            ServerMessage outMessage = new ServerMessage();
            outMessage.setMessageType("UserMessage");
            outMessage.setUserID(userID);
            outMessage.setMessageModel(messageModel);
            try {
                out.writeObject(outMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void setUserData(ObservableList<UserDataModel> userListData) {
        this.userListData = userListData;
        userIndex.clear();
        for(UserDataModel user : userListData){
            userIndex.put(user.getUserID(),userListData.indexOf(user));
        }
    }
    @Override
    public void onUserStatusChange(String userID,UserDataModel userDataModel) {
        userListData.get(userIndex.get(userID)).setUserStatus(userDataModel.getUserStatus());
    }
    @Override
    public void setMessageList(ObservableList<MessageModel> messageList) {
        this.msgList = messageList;
    }
    @Override
    public void getUserData() {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setMessageType("GetUserList");
        try {
            out.writeObject(serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
