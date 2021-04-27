/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Cart;
import Entities.Product;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author khamm
 */
public interface IServicesCart {
    public boolean saveToCart(Product p, String username, int qte);
    public ObservableList<Cart> AfficheCart(String username) throws SQLException;
    public boolean DeleteFromCart(Cart c, String username);
    public boolean Update_Quantity(Cart c, String username, int New_Qte);
    public ObservableList<Cart> ReturnList();
    public void DeleteFromCart_ALL(String username);


}
