package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage pri;
    static String userID;



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"));
        pri = primaryStage;
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(new Scene(root, 338, 311));
        primaryStage.show();
    }
    /**
     Main function.
     */

    public static void main(String[] args) {
        launch(args);
    }


    public Stage getStage(){
        return pri;
    }

}
