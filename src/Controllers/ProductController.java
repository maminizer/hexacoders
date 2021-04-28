/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import Entities.Product;
import Services.ServiceProduct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeString.search;

/**
 * FXML Controller class
 *
 * @author yossr
 */
public class ProductController implements Initializable {

    @FXML
    private Button BtnAdd;
    @FXML
    private Button BtnUpdate;
    @FXML
    private Button BtnDell;

    @FXML
    private TableView<Product> TableProducts;
    @FXML
    private TableColumn<Product, Integer> colId;
    @FXML
    private TableColumn<Product, String> colImage;
    @FXML
    private TableColumn<Product, String> colTitle;
    @FXML
    private TableColumn<Product, Integer> colPrice;
    @FXML
    private TableColumn<Product, Integer> colEnvente;
    @FXML
    private TableColumn<Product, Integer> colQuantity;
    @FXML
    private TableColumn<Product, String> colDescription;

    @FXML
    private TextField TfTitle;
    private TextField TfImage;
    @FXML
    private TextField TfPrice;
    @FXML
    private TextField TfEnvente;
    @FXML
    private TextField TfQuantity;
    @FXML
    private TextField TfDescription;
    @FXML
    private Button BtnComments;

    Product pp = null;
    @FXML
    private AnchorPane AnchorProd;
    private TextField TfId;
    @FXML
    private TextField TfRecherche;

    public File image;

    ObservableList<Product> produits = FXCollections.observableArrayList();
    ObservableList<Product> selectedproduit;
    @FXML
    private Button Image;
    @FXML
    private TextField pathfile;
    ServiceProduct sp = new ServiceProduct();
    @FXML
    private TextField colItitle5;
    @FXML
    private TextField colItitle2;
    @FXML
    private TextField colItitle1;
    @FXML
    private TextField colItitle4;
    @FXML
    private TextField colItitle3;
    private TextField string;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO preload Data on the table
            AfficherProduit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);

        }
        TableProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pp = (Product) TableProducts.getSelectionModel().getSelectedItem();
                System.out.println(pp);
                int pid = pp.getId();
                String Spid = String.valueOf(pid);
                TfTitle.setText(pp.getTitle());
                int pprice = (int) pp.getPrice();
                String Spprice = String.valueOf(pprice);
                TfPrice.setText(Spprice);
                int pquantity = pp.getQuantity();
                String Spquantity = String.valueOf(pquantity);
                TfQuantity.setText(Spquantity);
                int penvente = pp.getEn_vente();
                String Spenvente = String.valueOf(penvente);
                TfEnvente.setText(Spenvente);
                TfDescription.setText(pp.getDescription());
            }
        });

    }

    @FXML
    private void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        image = fileChooser.showOpenDialog(null);
        if (image != null) {
            pathfile.setText(image.getAbsolutePath());
        }
    }

    private void AfficherProduit() throws SQLException {
        ServiceProduct sp = new ServiceProduct();

        List product = (List) sp.AfficherProduct();
        ObservableList et = FXCollections.observableArrayList(product);
        colId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
        colImage.setCellValueFactory(new PropertyValueFactory<Product, String>("imageJ"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
        colEnvente.setCellValueFactory(new PropertyValueFactory<Product, Integer>("en_vente"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        TableProducts.setItems(et);
    }

    @FXML
    private void AddProduct(ActionEvent event) throws FileNotFoundException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Ajout de produit");
        String titleval = TfTitle.getText().toString().toLowerCase();
        String descrival = TfDescription.getText().toString().toLowerCase();
        String prixval = TfPrice.getText().toString().toLowerCase();
        String quantiteval = TfQuantity.getText().toString().toLowerCase();
        String enventeval = TfEnvente.getText().toString().toLowerCase();
        boolean verif = true;
        System.out.println("Initialize Done !");
        
        if (!sp.ControleNom(titleval)) {
            verif = false;
            alert.setContentText("Verifiez le nom du produit !");
            alert.showAndWait();
        }

        if (!sp.ControleNom(descrival)) {
            verif = false;
            alert.setContentText("Verifiez la description du produit !");
            alert.showAndWait();
        }
        if (!sp.ControleInt(prixval)) {
            verif = false;
            alert.setContentText("Verifiez le prix du produit !");
            alert.showAndWait();
        }

        if (image == null) {
            verif = false;
            alert.setContentText("Verifiez image du produit !");
            alert.showAndWait();
        }
        if (verif) {
            FileInputStream imageinput = new FileInputStream(image);
            Product produit = new Product(titleval,Integer.parseInt(prixval) , Integer.parseInt(quantiteval), Integer.parseInt(enventeval), descrival);
            System.out.println(produit);
            System.out.println("Product Initialized !");
            sp.AddProduct(produit, 1);
            System.out.println("Product Added Done !");
            //Notifications notificationBuilder = Notifications.create().title("Produit ajouté").graphic(null)
            //		.hideAfter(javafx.util.Duration.seconds(5)).position(Pos.TOP_LEFT);
            //notificationBuilder.show();
        }

        try {
            AfficherProduit();
        } catch (SQLException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        TfTitle.clear();

        TfPrice.clear();
        TfEnvente.clear();
        TfQuantity.clear();
        TfDescription.clear();

    }

    @FXML
    private void UpdateProduct(ActionEvent event) {
        ServiceProduct sp = new ServiceProduct();
        System.out.println(pp);
        if (pp == null) {
            JOptionPane.showMessageDialog(null, "choisir produit");

        } else {
            String title = TfTitle.getText();

            int price = Integer.parseInt(TfPrice.getText());
            int envente = Integer.parseInt(TfEnvente.getText());
            int quantity = Integer.parseInt(TfQuantity.getText());
            String description = TfDescription.getText();
            sp.UpdateProduct(new Product(title, price, quantity, envente, description), pp.getId());

            JOptionPane.showMessageDialog(null, "Produit modifier");
            TfTitle.clear();
            TfPrice.clear();
            TfEnvente.clear();
            TfQuantity.clear();
            TfDescription.clear();

            pp = null;
            try {
                AfficherProduit();
              
            } catch (SQLException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void DeleteProduct(ActionEvent event) {
        ServiceProduct sp = new ServiceProduct();
        Product p = (Product) TableProducts.getSelectionModel().getSelectedItem();
        System.out.println(p);
        if (pp == null) {
            JOptionPane.showMessageDialog(null, "choisir produit");

        } else {
            sp.DeleteProduct(pp.getId());

            try { //afficher au fur et a mesure avec l'ajout
                AfficherProduit();
            } catch (SQLException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(null, "produit supprimer");
            TfTitle.clear();
            TfImage.clear();
            TfPrice.clear();
            TfEnvente.clear();
            TfQuantity.clear();
            TfDescription.clear();

            pp = null;
        }
    }

    @FXML
    private void comment(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Commentaire.fxml"));

        AnchorPane pane = loader.load();
        AnchorProd.getChildren().setAll(pane);

        CommentaireController controllerC = loader.getController();
        controllerC.initData(TableProducts.getSelectionModel().getSelectedItem());
        controllerC.initData(TableProducts.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void RechercheProduct(ActionEvent event) throws SQLException, ClassNotFoundException {
        String critere = TfRecherche.getText().toLowerCase();
        if (critere.length() > 0) {
            produits.clear();
            List<Product> produitsaux = sp.displayRecherche(critere);
            if (!produitsaux.isEmpty()) {
                for (int i = 0; i < produitsaux.size(); i++) {
                    produits.add(produitsaux.get(i));
                }
            }
            colId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
            colTitle.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));
            colImage.setCellValueFactory(new PropertyValueFactory<Product, String>("imageJ"));
            colPrice.setCellValueFactory(new PropertyValueFactory<Product, Integer>("price"));
            colEnvente.setCellValueFactory(new PropertyValueFactory<Product, Integer>("en_vente"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
            colDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
            TableProducts.setItems(produits);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Recherche du produit");
            alert.setContentText("Critere non valide !");
            alert.showAndWait();
            try { //afficher au fur et a mesure avec l'ajout
                AfficherProduit();
            } catch (SQLException ex) {
                Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void GestionProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/PieChart.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Dashboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/adminDashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
