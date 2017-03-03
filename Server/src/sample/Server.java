package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ibrar on 2/23/17.
 */
public class Server {
    static ArrayList<UserDataModel> userListData = new ArrayList<UserDataModel>() ;
    ObservableList<SocketClientModel> socketClients ;
    static HashMap<String,ObjectOutputStream> writer = new HashMap<String, ObjectOutputStream>();
    static HashMap<String,Integer> userIndex = new HashMap<String,Integer>();

    final int port = 6066;
    public Server(){

    }

    public synchronized void getUserData(){
        for(UserDataModel userData: new dbUtility().getUsersData()){
            userListData.add(userData);
            userIndex.put(userData.getUserID(),userListData.indexOf(userData));
        }
    }

    public synchronized void setSocketClients(ObservableList<SocketClientModel> socketClients){
        this.socketClients = socketClients;
    }

    public void initServer(){
        try {
            ServerSocket socketServer;
            socketServer = new ServerSocket(port);
            System.out.print(socketServer.getInetAddress());
            while (true){
                Socket con = socketServer.accept();
                ClientSocket clientSocket = new ClientSocket(con);
                clientSocket.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class ClientSocket extends Thread {

        Socket client;
        ObjectOutputStream out;
        ObjectInputStream in;
        String userID;

        public ClientSocket(Socket conn){
            client = conn;
        }

        public synchronized void registerUser(String userID,String userPassword){
            boolean result = new dbUtility().signUpUser(userID,userPassword);
            try {
                ServerMessage message = new ServerMessage();
                message.setMessageType("AuthSuccess");
                if(result){
                    UserDataModel userData = new UserDataModel(userID,"Available");
                    userListData.add(userData);
                    userIndex.put(userData.getUserID(),userListData.indexOf(userData));
                    SocketClientModel socketClient = new SocketClientModel();
                    socketClient.setUserID(userID);
                    socketClient.setIp(client.getInetAddress().toString());
                    socketClient.setPort(""+client.getPort());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            socketClients.add(socketClient);
                        }
                    });

                    this.userID = userID;
                    writer.put(userID,out);

                    message.setMessageModel(new MessageModel(userID,"Found"));
                    out.writeObject(message);
                    sendUserStatusUpdate(userID);
                }else {
                    message.setMessageModel(new MessageModel("","NotFound"));
                    out.writeObject(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public synchronized void  sendUserStatusUpdate(String userID){

            if(!userID.equals("")){
                userListData.get(userIndex.get(userID)).setUserStatus("Available");

            }

            for(ObjectOutputStream write : writer.values()){
                ServerMessage message = new ServerMessage();
                message.setMessageType("UserList");
                message.setUserList(userListData);
                try {
                    write.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }

        public synchronized void loginUser(String userID,String userPassword){

            boolean result = new dbUtility().signInUser(userID,userPassword);
            try {
                ServerMessage message = new ServerMessage();
                message.setMessageType("AuthSuccess");
                if(result){
                    SocketClientModel socketClient = new SocketClientModel();
                    socketClient.setUserID(userID);
                    socketClient.setIp(client.getInetAddress().toString());
                    socketClient.setPort(""+client.getPort());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            socketClients.add(socketClient);
                        }
                    });

                    this.userID = userID;
                    writer.put(userID,out);

                    message.setMessageModel(new MessageModel(userID,"Found"));
                    out.writeObject(message);
                    sendUserStatusUpdate(userID);
                }else {
                    message.setMessageModel(new MessageModel("","NotFound"));
                    out.writeObject(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public synchronized void SendMessage(String userID,MessageModel messageModel){
            try {
                ServerMessage serverMessage = new ServerMessage();
                serverMessage.setMessageType("UserMessage");
                serverMessage.setMessageModel(messageModel);
                writer.get(userID).writeObject(serverMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            super.run();
            try {
                out = new ObjectOutputStream(client.getOutputStream());

                in = new ObjectInputStream(client.getInputStream());

                while (client.isConnected()){
                    ServerMessage serverMessage = (ServerMessage) in.readObject();
                    switch (serverMessage.getMessageType()){
                        case"RegisterR":
                            registerUser(serverMessage.getUserID(),serverMessage.getUserPassword());
                            break;
                        case "LoginR":
                            loginUser(serverMessage.getUserID(),serverMessage.getUserPassword());
                            break;
                        case "UserMessage":
                            SendMessage(serverMessage.getUserID(),serverMessage.getMessageModel());
                            break;
                        case  "GetUserList":
                            sendUserStatusUpdate("");
                            break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}



