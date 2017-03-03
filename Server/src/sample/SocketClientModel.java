package sample;

/**
 * Created by ibrar on 2/23/17.
 */
public class SocketClientModel {

    String userID,ip,port;

    public SocketClientModel() {


    }

    public SocketClientModel(String userID, String ip, String port) {
        this.userID = userID;
        this.ip = ip;
        this.port = port;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
