/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Product;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author khamm
 */
public interface IServiceProduct {

    public void addProduct(Product p);

    public ObservableList<Product> AfficheProduct() throws SQLException;

    public void AddProduct(Product p, int lengh);

    public boolean DeleteProduct(int id);

    public boolean UpdateProduct(Product p, int id);

    public ObservableList<Product> AfficherProduct() throws SQLException;
}
