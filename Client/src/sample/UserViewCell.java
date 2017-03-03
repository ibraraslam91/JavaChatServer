package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by ibrar on 2/22/17.
 */
public class UserViewCell extends ListCell<UserDataModel> {


    @FXML
    private Label txtUserID,txtUserStatus;
    @FXML
    private AnchorPane userItem;

    private FXMLLoader mLoader;


    @Override
    protected void updateItem(UserDataModel item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item==null){
            setText(null);
            setGraphic(null);
        }else {
            if(mLoader==null){
                mLoader = new FXMLLoader(getClass().getResource("../fxmls/useritem.fxml"));
                mLoader.setController(this);

                try {
                    mLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            txtUserID.setText(item.getUserID());
            txtUserStatus.setText(item.getUserStatus());

            setText(null);
            setGraphic(userItem);

        }

    }
}
