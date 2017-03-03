package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by ibrar on 2/22/17.
 */

/**
        Chat form Controller
 */


public class ChatController implements Initializable {
    @FXML
    public ListView chatList;
    @FXML
    public TextArea txtMsgArea;
    @FXML
    public Button btnSend;
    @FXML
    public ListView userList;
    @FXML
    public Label currentSelected;
    private ObservableList<MessageModel> msgList;
    private ObservableList<UserDataModel> userListData;
    private String selectedUser;

    ChatSocketI clientSocket ;

    public ChatController(){
        clientSocket = SocketImplementation.getInstance();
        msgList = FXCollections.observableArrayList();
        userListData = FXCollections.observableArrayList();

        if(userListData.size()>0){
            selectedUser = userListData.get(0).getUserID();
            currentSelected.setText(selectedUser);
        }
        clientSocket.setMessageList(msgList);
        clientSocket.setUserData(userListData);
        clientSocket.getUserData();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatList.setItems(msgList);
        chatList.setCellFactory(itemView -> new MessageViewCell());
        userList.setItems(userListData);
        userList.setCellFactory(itemview->new UserViewCell());
        userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserDataModel>() {
            @Override
            public void changed(ObservableValue<? extends UserDataModel> observable, UserDataModel oldValue, UserDataModel newValue) {
                System.out.println("Selectes User : "+ newValue.getUserID());
                selectedUser = newValue.getUserID();
                currentSelected.setText(selectedUser);
            }
        });
    }
    @FXML
    public void onSendClick(ActionEvent actionEvent) {
        if(selectedUser != null){
            MessageModel message = new MessageModel("me",txtMsgArea.getText());
            msgList.add(message);
            clientSocket.sendMessage(selectedUser,new MessageModel(Main.userID,txtMsgArea.getText()));
            txtMsgArea.setText("");
        }else {
            currentSelected.setText("Please Selecte Recipient");
        }
    }
}
