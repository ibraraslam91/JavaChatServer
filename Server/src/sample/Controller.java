package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public ListView usersList;

    private ObservableList<SocketClientModel> userListData ;
    public Controller(){
        userListData = FXCollections.observableArrayList();


        new Thread(){
            @Override
            public void run() {
                super.run();
                Server server = new Server();
                server.setSocketClients(userListData);
                server.getUserData();
                server.initServer();

            }
        }.start();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersList.setItems(userListData);
        usersList.setCellFactory(item -> new ClientCellView());
    }
}
