/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Cart;
import Entities.Product;
import IServices.IServicesCart;
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
public class ServiceCart implements IServicesCart{
    
    Connection cnx;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    ObservableList<Cart>  CartList = FXCollections.observableArrayList();
    public float totalPrice;

    
    public ServiceCart() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    
//    private boolean saveToCart(Product p, String username) {
//		Connection connection = null;
//		try {
//                    
//                    //set quantity
//                    //int qte = Integer.parseInt(QuantityField.getText());
//                    //System.out.println(qte);
//                    
//			// create a database connection
//                        connection = DbConnect.getConnect();
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 second
//			// check on commit
//			String addToCart = "insert into `cart`(`title`, `price`, `quantity`, `username`) values ('"+p.getTitle()+"','"+p.getPrice()+"','"+username+"')";
//                        System.out.println(addToCart);
//                        System.err.println(addToCart);
//			statement.executeUpdate(addToCart);
//			return true;
//		} catch (SQLException e) {
//			System.err.println(e.getMessage());
//		} finally {
//			try {
//				if (connection != null)
//					connection.close();
//			} catch (SQLException e) {
//				// connection close failed.
//				System.err.println(e);
//			}
//		}
//		return true;
//	}
    
    
    

    @Override
    public ObservableList<Cart> AfficheCart(String username) throws SQLException {
        connection = DbConnect.getConnect();
            
            query = "SELECT * FROM `cart` where username = '"+username+"' ";
            System.out.println(query);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                CartList.add(new  Cart(
                        resultSet.getString("title"),
                        resultSet.getFloat("price"),
                        resultSet.getInt("quantity")));
                
                
            }
        
        for (int i = 0; i < CartList.size(); i++) {
            totalPrice += CartList.get(i).getPrice() * CartList.get(i).getQuantity();
            }
        System.out.println(totalPrice);
        return CartList ;
    }
    
    public float prixTotal(){
//            for (int i = 0; i < CartList.size(); i++) {
//            totalPrice += CartList.get(i).getPrice() * CartList.get(i).getQuantity();
//            }
            return totalPrice;
    }
     @Override
    public ObservableList<Cart> ReturnList() {
            return CartList;
        }

    @Override
    public boolean saveToCart(Product p, String username, int qte) {
                Connection connection = null;
		try {
                    
                    //set quantity
                    //int qte = Integer.parseInt(QuantityField.getText());
                    //System.out.println(qte);
                    
			// create a database connection
                        connection = DbConnect.getConnect();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 second'"+p.getTitle()+"','"+p.getPrice()+"','"+qte+"','"+username+"'
			// check on commit
			String addToCart = "insert into `cart`(`title`, `price`, `quantity`, `username`) values ('"+p.getTitle()+"','"+p.getPrice()+"','"+qte+"','"+username+"')";
                        System.out.println(addToCart);
                        System.err.println(addToCart);
			statement.executeUpdate(addToCart);
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return true;
    }

    @Override
    public boolean DeleteFromCart(Cart c, String username) {
		Connection connection = null;
		try {
			// create a database connection
                        connection = DbConnect.getConnect();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 second
			// check on commit
			String DeleteCart = "DELETE FROM `cart` WHERE `title` = '"+c.getTitle()+"' AND `username` = '"+username+"' ";
                        System.err.println(DeleteCart);
			statement.executeUpdate(DeleteCart);
                        
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return true;
    }

    @Override
    public boolean Update_Quantity(Cart c, String username , int New_Qte) {

        
        Connection connection = null;
                try {

			// create a database connection
                        connection = DbConnect.getConnect();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 second
			// check on commit

			String UpdateCart = "UPDATE `cart` set `quantity` = '"+New_Qte+"' WHERE `title` = '"+c.getTitle()+"' AND `username` = '"+username+"' ";
                        System.err.println(UpdateCart);
			statement.executeUpdate(UpdateCart);
                        
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		return true;
    }

    @Override
    public void DeleteFromCart_ALL(String username) {
		Connection connection = null;
		try {
			// create a database connection
                        connection = DbConnect.getConnect();
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30); // set timeout to 30 second
			// check on commit
			String DeleteCart = "DELETE FROM `cart` WHERE `username` = '"+username+"' ";
                        System.err.println(DeleteCart);
			statement.executeUpdate(DeleteCart);
                        
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		
    }

   


    
    
}
