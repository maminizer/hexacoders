/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author yossr
 */
public class maConnexion {
     final static String URL = "jdbc:mysql://127.0.0.1:3306/tunipharma3" ;
   final static String LOGIN = "root" ;
   final static String PWD = "" ;
    static maConnexion instance = null ;
   private Connection cnx ;
   
    private maConnexion(){
       try{
           cnx= DriverManager.getConnection(URL,LOGIN,PWD);
           System.out.println("connexion établie");
           
       }catch(SQLException ex){
                      System.out.println("pas de connexion");
       }
       
   }
   public static maConnexion getInstance(){
       if(instance == null){
           instance = new maConnexion();
       }
           return instance;
   }
      public Connection getConnection(){
          return cnx; //tunnel de la connexion
      }

    
}
