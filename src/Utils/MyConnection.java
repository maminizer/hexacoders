/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;



public class MyConnection {
    public String url="jdbc:mysql://localhost:3306/tunipharma3";
    public String login="root";
    public String pwd="";
    public Connection cn;
    public static MyConnection instance;

    public MyConnection() {
        try {
            cn = DriverManager.getConnection(url,login,pwd);
            System.out.println("Connexion etablie!");
        } catch (SQLException ex) {
            System.out.println("Erreur de connexion");
            System.out.println(ex.getMessage());        }
    }
    public Connection getCnx() {
        return cn;
    }


    public static MyConnection getInstance(){
        if(instance == null){
            instance = new MyConnection();
        }
        return instance;
    }
    Connection conn = null;
    public static Connection ConnectDb(){
        try {

            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/tunipharma3","root","");
            // JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

}

