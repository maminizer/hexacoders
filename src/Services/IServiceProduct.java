/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.product;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author yossr
 */
public interface IServiceProduct {
     public void AddProduct(product p, int lengh);
    public boolean DeleteProduct(int id);
     public boolean UpdateProduct(product p,int id);
    public ObservableList<product>  AfficherProduct() throws SQLException;
}
