/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entities.Product;
import IServices.IServiceProduct;
import Utils.DbConnect;
import Utils.Maconnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author khamm
 */
public class ServiceProduct implements IServiceProduct{
    Connection cnx;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
        ObservableList<Product>  ProductList = FXCollections.observableArrayList();

    
    public ServiceProduct() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void addProduct(Product p) {
        try {
        Statement stm = cnx.createStatement();
        String query = "INSERT INTO `Product`(`title`, `price`, `quantity`) VALUES ('"+p.getTitle()+"','"+p.getPrice()+"','"+p.getQuantity()+"')";
                
        stm.executeUpdate(query);
        
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Product> AfficheProduct() throws SQLException {
        connection = DbConnect.getConnect();
        query = "SELECT * FROM `Product`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                ProductList.add(new  Product(
                        resultSet.getString("title"),
                        resultSet.getFloat("price"),
                        resultSet.getInt("quantity")));
                
                
            }
            return ProductList;
    }

    
}
