/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.AdminHomeController;
import Controllers.AdminHomeController;
import Entities.Catalogue;
import Entities.Coffrets;
import Entities.Product;
import Entities.User;
import Services.CatalogueService;
import Services.CoffretsService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author WIKI
 */
public class CoffretsController implements Initializable {

    @FXML
    private TableColumn<Coffrets, String> imageCoffret;
    @FXML
    private TableColumn<Product, String> nomProduit;
    @FXML
    private TableColumn<Coffrets, Float> prixCoffret;
    @FXML
    private TableColumn<Coffrets, Double> offrteCoffret;
    @FXML
    private TableColumn<Coffrets, String> nomCoffret;
   // private TableView<Coffrets> coffrets;
    @FXML
    private AnchorPane coffretss;
    @FXML
    private TableView<Coffrets> coffretssss;
    @FXML
    private Button listproduits;
    @FXML
    private TextField tttid;
    @FXML
    private Button share;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showCoffret();
    }    
 
     private void showCoffret() {
        //   String sql="SELECT product.title FROM product";

        //   PreparedStatement st;
        //   try {
        //   st = cnx.prepareStatement(sql);
        //    ResultSet rs=st.executeQuery();
        //   while(rs.next()){
        //      String k=rs.getString("title");
        CoffretsService cs = new CoffretsService();
        List<Coffrets> list ;
         ObservableList<Coffrets> ob = null;
     
        try {
            list = cs.afficherCoffret();
              ob = FXCollections.observableArrayList(list);
              list.get(0).getServices().forEach(c->System.out.println(c));
              System.out.println(list.get(0).getImage());
        } catch (SQLException ex) {
            Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        imageCoffret.setCellValueFactory(new PropertyValueFactory<Coffrets, String>("image"));
        //   cproduit1.setCellValueFactory(x -> new SimpleObjectProperty<>(k));
        nomProduit.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));

        nomCoffret.setCellValueFactory(new PropertyValueFactory<Coffrets, String>("nom"));
           prixCoffret.setCellValueFactory(new PropertyValueFactory<Coffrets, Float>("prix"));
        offrteCoffret.setCellValueFactory(new PropertyValueFactory<Coffrets, Double>("offre"));
        coffretssss.setItems(ob);
    }

 

 

    @FXML
    private void OnRecIdCoffret(MouseEvent event) {
        Coffrets categoriee = coffretssss.getSelectionModel().getSelectedItem();
        tttid.setText("" + categoriee.getId());

      
    }

    @FXML
    private void listproduit1(ActionEvent event) throws SQLException {
         CoffretsService cs = new CoffretsService();
        List<Coffrets> list  ;
       list = cs.afficherCoffret();
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Details Labo");
        alert.setHeaderText("Produits : ");
        String ch = "";
      //  System.out.println(ttid1.getText());
       // list.get(Integer.parseInt(tttid.getText())).getServices();
        Set ss;
        ss = list.get(Integer.parseInt(tttid.getText())).getServices();
        for (Object service : ss) {
         //   Product s =(Product) service;
          System.out.println(service);
           ch+= service +"\n";
       }
        alert.setContentText(ch);
        alert.show();
    }

    @FXML
    private void share(ActionEvent event) throws SQLException {
          CoffretsService cs = new CoffretsService();
        List<Coffrets> list  ;
         String ch = "";
         
       list = cs.afficherCoffret();
      ch="nomCoffrets:"+list.get(Integer.parseInt(tttid.getText())).getNom()+"\n";
        Set ss;
        ss = list.get(Integer.parseInt(tttid.getText())).getServices();
        for (Object service : ss) {
           Product s =(Product) service;
          System.out.println(service);
           ch+= service +"\n";
       }
          String accessToken="EAAM38XnZB3UMBAPNL7EDbZBZBmAmXtWBOliRHOGrYW1Eb0qz6JTd0hUJUp8hp1zcEf2T6UFZAYhKxDrPzxEabsLm5QJ6ZCsinIZAQtzuEFDiAX4keRXfSWyWVZBn3bZAetEgHZAfo35GleI7wu44iqLphH4TepAhohBTHQLSAuzDSnRXtsGmCUioIEm2HpmzbtCh85TXtjbLICgZDZD";
        FacebookClient fbclient=new DefaultFacebookClient(accessToken);
        //User me=fbclient.fetchObject("me", User.class);
     FacebookType response=  fbclient.publish("me/feed", FacebookType.class, Parameter.with("message", "test"));
        System.out.println("fb.com/"+response.getId());
    }

 

}
