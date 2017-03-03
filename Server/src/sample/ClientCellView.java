package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ibrar on 2/24/17.
 */
public class ClientCellView extends ListCell<SocketClientModel> {

    @FXML
    private Label userid,userip,userport;
    @FXML
    private AnchorPane aPane;

    FXMLLoader mLoader;


    @Override
    protected void updateItem(SocketClientModel item, boolean empty) {
        super.updateItem(item, empty);
        if(empty||item==null){
            setGraphic(null);
            setText(null);
        }else {
            if(mLoader==null){
                mLoader = new FXMLLoader(getClass().getResource("clientItem.fxml"));
                mLoader.setController(this);
            }
            try {
                mLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            userid.setText(item.getUserID());
            userip.setText(item.getIp());
            userport.setText(item.getPort());
            setText(null);
            setGraphic(aPane);
        }

    }
}
