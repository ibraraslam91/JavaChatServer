package sample;

import java.io.Serializable;

/**
 * Created by ibrar on 2/22/17.
 */
public class MessageModel implements Serializable {

    String msgBy="",msgTxt="";


    public MessageModel() {
    }

    public MessageModel(String msgBy, String msgTxt) {
        this.msgBy = msgBy;
        this.msgTxt = msgTxt;
    }

    public String getMsgBy() {
        return msgBy;
    }

    public void setMsgBy(String msgBy) {
        this.msgBy = msgBy;
    }

    public String getMsgTxt() {
        return msgTxt;
    }

    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt;
    }
}
