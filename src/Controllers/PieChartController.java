/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.product;
import Service.ServiceProduct;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yossr
 */
public class PieChartController implements Initializable {

    @FXML
    private PieChart PieChart;
    
      ObservableList<PieChart.Data> list=FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ServiceProduct pdao;
         
		try {
			pdao = ServiceProduct.getInstance();

        List<product> pers=pdao.AfficherProduct();
        for(product p:pers) {
            list.addAll(
           
                new PieChart.Data(String.valueOf(p.getQuantity()), 12.0)             
        );
        }
        PieChart.setAnimated(true);
        PieChart.setData(list);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException ex) {
            Logger.getLogger(PieChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Backproduits(ActionEvent event) throws IOException {
        	Parent root = FXMLLoader.load(getClass().getResource("/views/Product.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
    
}
