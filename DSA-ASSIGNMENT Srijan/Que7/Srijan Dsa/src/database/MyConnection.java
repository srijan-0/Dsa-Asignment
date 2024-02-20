package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    public static Connection dbConnect() {
//        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/connectopia", "root", "khadgi986");
        System.out.println("connected");
            return conn;
        } 
        catch (ClassNotFoundException e) {
        } 
        catch (SQLException e) {
        }

        return null;
    }
}
