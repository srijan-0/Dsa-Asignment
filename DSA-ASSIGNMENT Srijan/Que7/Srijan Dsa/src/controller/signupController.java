
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import database.MyConnection;

public class signupController {
    
    public void signUp(String username, String password){
     try (Connection conn = MyConnection.dbConnect();
             PreparedStatement pst = conn.prepareStatement("INSERT INTO user(Username, Password) VALUES (?, ?)")) {

            pst.setString(1, username);
            pst.setString(2, password);
            

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data Inserted");

            } else {
                System.out.println("Failed to insert data");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }   
    }
    
}
