/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.User;
import Service.serviceUser;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HAMZA
 */
public class IndexController implements Initializable {

    @FXML
    private TableView<User> tableUser;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, String> firstnameCol;
    @FXML
    private TableColumn<User, String> lastnameCol;
    @FXML
    private TableColumn<User, String> nbrtelCol;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;
    
    private Stage primarystage; 
    
   
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    User u = null ;
    ObservableList<User> CartList = FXCollections.observableArrayList();
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfpassword;
    @FXML
    private TextField tffirstname;
    @FXML
    private TextField tflastname;
    @FXML
    private TextField tfNbrTel;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField tfrecherche;
    @FXML
    private ComboBox<String> typeRecherche;
     ObservableList<String> listeTypeRecherche = FXCollections.observableArrayList("Tout", "firstname", "lastname");
    @FXML
    private Button btnimpression;
    @FXML
    private ComboBox<String> typetri;
    ObservableList<String> listeTypetri = FXCollections.observableArrayList("Tout", "Trie firstname", "Trie lastname","Trie Email");
    ObservableList<User> reslist = FXCollections.observableArrayList();
    @FXML
    private Button btnactualiser;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            UserData();
        } catch (SQLException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        typeRecherche.setItems(listeTypeRecherche);
                        typeRecherche.setValue("Tout");
                        typetri.setItems(listeTypetri);
                        typetri.setValue("Tout");
        try {
            listtri();
//        try {
//            list();
//        } catch (SQLException ex) {
//            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (SQLException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    private void  UserData () throws SQLException  {
        ObservableList<User> listUser = FXCollections.observableArrayList();
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        nbrtelCol.setCellValueFactory(new PropertyValueFactory<>("nbr_tel"));
        serviceUser r = new serviceUser();
        List<User> old = r.ShowUsers();
        listUser.addAll(old);
        tableUser.setItems(listUser);
    }

    @FXML
     private void ButtonAction(javafx.event.ActionEvent event) throws SQLException, IOException {
        if(event.getSource() == addBtn){
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("addUser.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
        
        } else if (event.getSource() == deleteBtn) {
            deleteUser();
        }
         else if (event.getSource() == editBtn) {
            User selected = (User) tableUser.getSelectionModel().getSelectedItem();
            
            tfemail.setVisible(true);
            tfpassword.setVisible(true);
            tffirstname.setVisible(true);
            tflastname.setVisible(true);
            tfNbrTel.setVisible(true);
            updateBtn.setVisible(true);
            
            
            System.out.println(selected.NbrParse());
            
            tfemail.setText(selected.getEmail());
            tfpassword.setText(selected.getPassword());
            tffirstname.setText(selected.getFirstname());
            tflastname.setText(selected.getLastname());
            tfNbrTel.setText(selected.NbrParse());
            }
         else if (event.getSource() == updateBtn) {
            User selected = (User) tableUser.getSelectionModel().getSelectedItem();
            serviceUser u = new serviceUser();
            
            User user;
            user = new User(selected.getId(),tfemail.getText(), tfpassword.getText(), tffirstname.getText(), tflastname.getText(),Integer.parseInt(tfNbrTel.getText()));
            
            u.updateUser(user);
            UserData ();
            tfemail.setVisible(false);
            tfpassword.setVisible(false);
            tffirstname.setVisible(false);
            tflastname.setVisible(false);
            tfNbrTel.setVisible(false);
            updateBtn.setVisible(false);
            tableUser.refresh();
            
        }
        else if(event.getSource() == btnimpression){
            BPDF();
        }
        else if(event.getSource() == btnactualiser){
            actualiser();
        }
        
    }
     
   private void deleteUser() throws SQLException{
        User selected = (User) tableUser.getSelectionModel().getSelectedItem();
        serviceUser user = new serviceUser();
        user.deleteUser(selected.getId());
        JOptionPane.showMessageDialog(null,"utilisateur supprimeé");
        UserData();
        tableUser.refresh();
        
        
    }
   
   public String toString(int k){
        return "" ;
    }
   @FXML
   private void list() throws SQLException {
        
            serviceUser rt = new serviceUser();
            //UserData();
            List arr = rt.chercherUser(typeRecherche.getValue(),tfrecherche.getText());
            ObservableList obb = FXCollections.observableArrayList(arr);
            tableUser.setItems(obb);
        
    }
       private void BPDF() {
    System.out.println("To Printer!");
         PrinterJob job = PrinterJob.createPrinterJob();
           if(job != null){
    Window primaryStage = null;
           job.showPrintDialog(primaryStage); 
    Node root = this.tableUser;
           job.printPage(root);
           job.endJob();
    }
    }
          @FXML
     private void listtri() throws SQLException {
        
        serviceUser rt = new serviceUser();
            //loadDate();
            List arr = rt.trier(typetri.getValue());
            ObservableList obb = FXCollections.observableArrayList(arr);
            tableUser.setItems(obb);
       
    }
       public void actualiser() throws SQLException{
         reslist.clear();
       UserData();
       tableUser.refresh(); 
        
    }
          @FXML
    private void back(javafx.event.ActionEvent event) throws IOException {
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));;
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }
 
}
