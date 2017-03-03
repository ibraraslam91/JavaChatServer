package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ibrar on 2/22/17.
 */
public class MessageViewCell extends ListCell<MessageModel> {
    @FXML
    private Label txtUserID,txtMsg;
    private FXMLLoader mLoader;
    @FXML
    private AnchorPane paneID;
    @Override
    protected void updateItem(MessageModel item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item==null){
            setText(null);
            setGraphic(null);
        }else {
            if(mLoader == null){
                mLoader = new FXMLLoader(getClass().getResource("../fxmls/iteml.fxml"));
                mLoader.setController(this);

                try {
                    mLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            txtUserID.setText(item.getMsgBy());
            txtMsg.setText(item.getMsgTxt());
            setText(null);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setGraphic(paneID);
                }
            });

        }
    }
}
