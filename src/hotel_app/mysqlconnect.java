/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_app;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Paweł
 */
public class mysqlconnect {
    Connection conn = null;

    public static Connection ConnecrDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");       
      String url = "jdbc:mysql://192.168.1.15:3306/Hotel_database_final";
      Connection conn = DriverManager.getConnection(url,"root","sw592");
      return conn;
        } catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
            return null;
        }
    }
}
