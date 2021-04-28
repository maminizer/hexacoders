/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.File;
import javafx.stage.FileChooser;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import Entities.Catalogue;
import Entities.Categorie;
import Entities.Coffrets;
import Entities.Product;
import Entities.coffrets_product;
import Services.CatalogueService;

import Services.CategorieService;
import Services.CoffretsService;
import Utils.Maconnexion;

/**
 * FXML Controller class
 *
 * @author WIKI
 */
public class AdminHomeController implements Initializable {

    @FXML
    private Tab catalog;
    @FXML
    private TableView<Catalogue> catalogue;
    @FXML
    private TableColumn<Catalogue, Integer> aid;
    @FXML
    private TableColumn<Catalogue, String> anom;
    @FXML
    private TableColumn<Catalogue, String> adescription;
    @FXML
    private Button aadd;
    @FXML
    private Button aupdate;
    @FXML
    private TextField tid;
    @FXML
    private TextField tnom;
    @FXML
    private TextField tdescription;
    @FXML
    private Button adelete;
    private TextField tCid;
    private TextField tCnom;
    private TextField tCdescription;
    private ChoiceBox<Categorie> tCcatalogue;
    @FXML
    private Button badd;
    @FXML
    private TableView<Categorie> categorie;
    @FXML
    private TableColumn<Categorie, Integer> bid;
    @FXML
    private TableColumn<Categorie, Integer> bcatalogue;
    @FXML
    private TableColumn<Categorie, String> bnom;
    @FXML
    private TableColumn<Categorie, String> bdescription;
    @FXML
    private TableColumn<Categorie, String> bimage;
    @FXML
    private Button bdelete;
    @FXML
    private Button bupdate;
    @FXML
    private Tab categorie1;
    @FXML
    private TextField txtpath;
    @FXML
    private Button btnpath;
    @FXML
    private TextField ttid;
    @FXML
    private TextField ttnom;
    @FXML
    private TextField ttdescription;
    @FXML
    private ComboBox<Catalogue> ttcatalogue;

    @FXML
    private Button ev;
    @FXML
    private Button badd1;
    @FXML
    private TableColumn<Coffrets, Integer> cid1;
    @FXML
    private TableColumn<Coffrets, String> cnom1;
    @FXML
    private TableColumn<Coffrets, String> cdescription1;
    @FXML
    private TableColumn<Coffrets, String> cimage1;
    @FXML
    private TableColumn<Coffrets, Double> cprix;
    @FXML
    private TableColumn<Coffrets, Double> coffre;
    @FXML
    private Button bdelete1;
    @FXML
    private Button bupdate1;
    @FXML
    private TextField ttid1;
    @FXML
    private TextField ttnom1;
    @FXML
    private TextField ttdescription1;
    @FXML
    private ListView<Product> listview1;
    @FXML
    private Button ev1;
    @FXML
    private TextField txtpath1;
    @FXML
    private Button btnpath1;
    @FXML
    private Tab coffrets;
    @FXML
    private TableColumn<Product, String> cproduit1;
    @FXML
    private TableView<Coffrets> coffret1;
    Connection cnx = Maconnexion.getInstance().getConnection();
    @FXML
    private TextField ttoffre;
    @FXML
    private TextField ttprix;
    public File image;
    @FXML
    private Button listProduit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showCatalogue();
        showCategorie();
        showCoffret();

        ttcatalogue.setPromptText("select Catalogue");
        ObservableList<Catalogue> selections = FXCollections.observableArrayList();
        CatalogueService cs = new CatalogueService();
        selections.addAll(cs.afficherCatalogue());
        ttcatalogue.setItems(selections);
        setEventOncatalogue();

        ObservableList<Product> selectionss = FXCollections.observableArrayList();
        CoffretsService co = new CoffretsService();
        selectionss.addAll(co.afficherProduct());
        listview1.setItems(selectionss);
        listview1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

//start function for Catalogue
    @FXML
    //afficher les donnes d'un catalogue
    private void showCatalogue() {
        CatalogueService cs = new CatalogueService();
        ObservableList<Catalogue> list = cs.afficherCatalogue();
        aid.setCellValueFactory(new PropertyValueFactory<Catalogue, Integer>("id"));
        anom.setCellValueFactory(new PropertyValueFactory<Catalogue, String>("nom"));
        adescription.setCellValueFactory(new PropertyValueFactory<Catalogue, String>("description"));
        catalogue.setItems(list);
    }

    private void ajouterCatalogue() {
        //      String query="INSERT INTO catalogue VALUES ('" +tnom.getText()+"','"+tdescription.getText()+"')";
        String query = "INSERT INTO catalogue(nom,description) VALUES('" + tnom.getText() + "','" + tdescription.getText() + "')";
        CatalogueService cs = new CatalogueService();
        cs.ajouterCatalogues(query);
        showCatalogue();
    }

    private void supprimerCatalogue() {
        String query = "DELETE FROM catalogue WHERE id=" + tid.getText() + "";
        CatalogueService cs = new CatalogueService();
        cs.ajouterCatalogues(query);
        showCatalogue();

    }

    private void modifierCatalogue() {
        String query = "UPDATE catalogue SET nom= '" + tnom.getText() + "',description ='" + tdescription.getText() + "'" + "WHERE id=" + tid.getText() + "";
        CatalogueService cs = new CatalogueService();
        cs.ajouterCatalogues(query);
        showCatalogue();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == aadd) {
            ajouterCatalogue();
        } else if (event.getSource() == aupdate) {
            modifierCatalogue();
        } else if (event.getSource() == adelete) {
            supprimerCatalogue();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Catalogue cataloguee = catalogue.getSelectionModel().getSelectedItem();
        tid.setText("" + cataloguee.getId());
        tnom.setText(cataloguee.getNom());
        tdescription.setText(cataloguee.getDescription());

    }
    //end function catalogue

    //start functions categorie
    private void ajouterCategorie() {

        //   String query="INSERT INTO categorie VALUES (" +ttid.getText()+","+ttcatalogue.getSelectionModel().getSelectedItem().getId()+",'"+ttnom.getText()+"','"+ttdescription.getText()+"','"+txtpath.getText()+"')";
        String query = "INSERT INTO categorie(catalogue_id,nom,description,image) VALUES (" + ttcatalogue.getSelectionModel().getSelectedItem().getId() + ",'" + ttnom.getText() + "','" + ttdescription.getText() + "','" + txtpath.getText() + "')";

        CategorieService cs = new CategorieService();
        cs.ajouterCategories(query);
        showCategorie();
    }

    private void showCategorie() {
        CategorieService cs = new CategorieService();
        ObservableList<Categorie> list = cs.afficherCategorie();
        bid.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("id"));
        bcatalogue.setCellValueFactory(new PropertyValueFactory<Categorie, Integer>("catalogue_id"));

        bimage.setCellValueFactory(new PropertyValueFactory<Categorie, String>("image"));

        bnom.setCellValueFactory(new PropertyValueFactory<Categorie, String>("nom"));
        bdescription.setCellValueFactory(new PropertyValueFactory<Categorie, String>("description"));
        categorie.setItems(list);
    }

    @FXML
    private void handleButtonActionCategorie(ActionEvent event) {
        if (event.getSource() == badd) {
            ajouterCategorie();
        } else if (event.getSource() == bupdate) {
            modifierCategorie();
        } else if (event.getSource() == bdelete) {
            supprimerCategorie();
        }

    }

    private void modifierCategorie() {
        String query = "UPDATE categorie SET catalogue_id=" + ttcatalogue.getSelectionModel().getSelectedItem().getId() + ",nom= '" + ttnom.getText() + "',description ='" + ttdescription.getText() + "',image ='" + txtpath.getText() + "'" + "WHERE id=" + ttid.getText() + "";
        CategorieService cs = new CategorieService();
        cs.ajouterCategories(query);
        showCategorie();
    }

    private void supprimerCategorie() {
        String query = "DELETE FROM categorie WHERE id=" + ttid.getText() + "";
        CategorieService cs = new CategorieService();
        cs.ajouterCategories(query);
        showCategorie();

    }

    @FXML
    private void MouseActionCategorie(MouseEvent event) {
        Categorie categoriee = categorie.getSelectionModel().getSelectedItem();
        ttid.setText("" + categoriee.getId());

        ttnom.setText(categoriee.getNom());
        ttdescription.setText(categoriee.getDescription());
        txtpath.setText(categoriee.getImage());
    }

    //end code categorie
    @FXML
    private void btnPath(ActionEvent event) {
        //  Connexion v=new Connexion();
        // v.filen();
        // String vpath=v.getp();
        // if(vpath==null){
        // }else{
        //    txtpath.setText(vpath);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        image = fileChooser.showOpenDialog(null);
        if (image != null) {
            txtpath.setText(image.getAbsolutePath());
        }
        //   }

    }

    @FXML
    private void setEventOncatalogue() {
        ttcatalogue.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Catalogue airport = ttcatalogue.getSelectionModel().getSelectedItem();

                System.out.println(airport.getId());
            }
        });
    }

    //start function coffrets
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
        cid1.setCellValueFactory(new PropertyValueFactory<Coffrets, Integer>("id"));
        //   cproduit1.setCellValueFactory(x -> new SimpleObjectProperty<>(k));
        cproduit1.setCellValueFactory(new PropertyValueFactory<Product, String>("title"));

        cnom1.setCellValueFactory(new PropertyValueFactory<Coffrets, String>("nom"));
        cdescription1.setCellValueFactory(new PropertyValueFactory<Coffrets, String>("description"));
        cimage1.setCellValueFactory(new PropertyValueFactory<Coffrets, String>("image"));
        cprix.setCellValueFactory(new PropertyValueFactory<Coffrets, Double>("prix"));
        coffre.setCellValueFactory(new PropertyValueFactory<Coffrets, Double>("offre"));
        coffret1.setItems(ob);
    }
    //  } catch (SQLException ex) {
    //     Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);

    //  }
    private void ajouterCoffre( ) {

       String query = "INSERT INTO coffrets(nom,image,description,prix,offre) VALUES ('" + ttnom1.getText() + "','" + txtpath1.getText() + "','" + ttdescription1.getText() + "'," + ttprix.getText() + "," + ttoffre.getText() + ")";

        CoffretsService cs = new CoffretsService();
       cs.ajouterCoffrets(query);
       // Set ser=new HashSet<Product> (listview1.getSelectionModel().getSelectionMode().MULTIPLE);
   //   Coffrets l;
     //   l = new Coffrets(Integer.parseInt(ttid.getText()),ttnom1.getText(),txtpath1.getText(),ttdescription1.getText(),Double.parseDouble(ttprix.getText()),Double.parseDouble(ttoffre.getText()),ser);
        //  try {  
        //    PreparedStatement ste1;
          //  DateFormat format3 = new SimpleDateFormat("h:mm", Locale.ENGLISH);
          //  String heureDebut = format3.format(l.getHoraireDebut());
          //  String heureFin = format3.format(l.getHoraireFin());
          //  int i = su.ajouterUser(l.getUser());
           // int i=
           // String sql = "INSERT INTO laboratoire (id, num_service, nom, horaire_debut, horaire_fin, note) VALUES (?, ?, ?, '" + heureDebut + "', '" + heureFin + "', 0)";
           // if (i != -1) {
             //      String sql = "INSERT INTO coffrets(nom,image,description,prix,offre) VALUES ('" + ttnom1.getText() + "','" + txtpath1.getText() + "','" + ttdescription1.getText() + "'," + ttprix.getText() + "," + ttoffre.getText() + ")";
            //  Statement ste=cnx.createStatement();
             //  CoffretsService css = new CoffretsService();
              //  ste1 = cnx.prepareStatement();
               // ste1.setInt(1, i);
              //  ste1.setInt(2, l.getNumService());
              //  ste1.setString(3, l.getNom());
          //      ste.executeUpdate(sql);
           //     for (Object s : l.getServices()) {
            //       css.ajouterCoffrets(new Coffrets(), (Product) s);
            //    }
        //    }
      //  } catch (SQLException ex) {
      //      System.out.println(ex.getMessage());
      //  }
        
        
        showCoffret();
    }

    @FXML
    private void btnPathCoffrets(ActionEvent event) {
        //   Connexion v=new Connexion();
        //  v.filen();
        // String vpath=v.getp();
        // if(vpath==null){
        // }else{
        //  txtpath1.setText(vpath);
        //  }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        image = fileChooser.showOpenDialog(null);
        if (image != null) {
            txtpath1.setText(image.getAbsolutePath());
        }
    }

    @FXML
    private void MouseActionCoffrets(MouseEvent event) {
        Coffrets categoriee = coffret1.getSelectionModel().getSelectedItem();
        ttid1.setText("" + categoriee.getId());

        ttnom1.setText(categoriee.getNom());
        ttdescription1.setText(categoriee.getDescription());
        txtpath1.setText(categoriee.getImage());
        ttoffre.setText("" + categoriee.getOffre());
        //  ttprix.setText(""+categoriee.getPrix());

    }

    @FXML
    private void handleButtonActionCoffrets(ActionEvent event) {
        if (event.getSource() == badd1) {
            ajouterCoffre();
        } else if (event.getSource() == bupdate1) {
          //  modifierCoffrets();
        } else if (event.getSource() == bdelete1) {
          //  supprimerCoffrets();
        }

    }

    @FXML
    private void setEventOncoffrets(ActionEvent event) {
        double somme = 0;
        double promo = 0;
        ObservableList<Product> lists;
        lists = listview1.getSelectionModel().getSelectedItems();

        for (Product m : lists) {
            somme += m.getPrice();
        }
        double offre = Double.parseDouble(ttoffre.getText());
        promo = (100 - offre) / 100;
        System.out.println("somme est " + somme + "DT");
        System.out.println("prix aprés offre est=" + promo * somme + "DT");
        ttprix.setText("" + promo * somme);
    }

   /* private void modifierCoffrets() {
        String query = "UPDATE coffrets SET nom= '" + ttnom1.getText() + "',description ='" + ttdescription1.getText() + "',image ='" + txtpath1.getText() + "',prix=" + ttprix.getText() + ",offre=" + ttoffre.getText() + "WHERE id=" + ttid1.getText() + "";
        CoffretsService css = new CoffretsService();
        css.ajouterCoffrets(query);
        showCoffret();
    }

    private void supprimerCoffrets() {
        String query = "DELETE FROM coffrets WHERE id=" + ttid1.getText() + "";
        CoffretsService css = new CoffretsService();
        css.ajouterCoffrets(query);
        showCoffret();

    }
*/
    @FXML
    private void listProduit(ActionEvent event) throws SQLException{
             CoffretsService cs = new CoffretsService();
        List<Coffrets> list  ;
       list = cs.afficherCoffret();
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Details Labo");
        alert.setHeaderText("Produits : ");
        String ch = "";
        System.out.println(ttid1.getText());
        list.get(Integer.parseInt(ttid1.getText())).getServices();
        Set ss;
        ss = list.get(Integer.parseInt(ttid1.getText())).getServices();
        for (Object service : ss) {
         //   Product s =(Product) service;
          System.out.println(service);
           ch+= service +"\n";
       }
        alert.setContentText(ch);
        alert.show();
        
    }

    }
