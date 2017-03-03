package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ibrar on 2/23/17.
 */

public class dbUtility {
    Connection conn;
    public dbUtility() {
        String dbURL = "jdbc:mysql://localhost:3306/ChatApp";
        String username = "root";
        String password = "";
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<UserDataModel> getUsersData() {
        ArrayList<UserDataModel> usersData = new ArrayList<UserDataModel>();
        String query = "SELECT * FROM `user_account`";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                UserDataModel userData = new UserDataModel();
                userData.setUserStatus("Offline");
                userData.setUserID(resultSet.getString("user_ID_name"));
                usersData.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersData;
    }
    public boolean signUpUser(String id,String password){
        String query = "INSERT INTO `user_account`(`user_ID_name`, `user_password`) VALUES ('"+id+"','"+password+"')";
        int rowsInserted = 0;
        boolean result =false;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new person was inserted successfully!");
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean signInUser(String id,String password){
        String query = "SELECT * FROM `user_account` WHERE  `user_ID_name` = '"+id+"' and `user_password` = '"+password+"' ";
        boolean result=false;
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                System.out.println("Found");
                result = true;
            }else {
                System.out.println("NOT Found");
                result = false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}