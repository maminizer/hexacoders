/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import javafx.scene.control.Alert;

import Entity.User;
import Service.serviceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HAMZA
 */
public class AddUserController implements Initializable {

    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfnbrtel;
    @FXML
    private TextField tffirstname;
    @FXML
    private TextField tflastname;
    @FXML
    private TextField tfpassword;
    @FXML
    private Button addBtn;
    private Stage primarystage; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void add(ActionEvent event) throws SQLException, IOException {
       if (tfemail.getText().isEmpty() || tffirstname.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Remplir les champs vides! ");

       }else 
       if (!Pattern.matches("[a-zA-Z]*", tfemail.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez l'email! ");
                tfemail.requestFocus();
                tfemail.selectEnd();
                
       }else 
       if (!Pattern.matches("[a-zA-Z]*", tffirstname.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le nom! ");
                tffirstname.requestFocus();
                tffirstname.selectEnd();
                
       }else 
       if (!Pattern.matches("[a-zA-Z]*", tflastname.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le prenom! ");
                tflastname.requestFocus();
                tflastname.selectEnd();
                
       }else 
       if (!Pattern.matches("[0-9]*", tfnbrtel.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le numero de telephone ! ");
                tfnbrtel.requestFocus();
                tfnbrtel.selectEnd();
       }
                    else{
                
            serviceUser u = new serviceUser();
            User user ;
            int k = Integer.parseInt(tfnbrtel.getText());

            user = new User(tfemail.getText(),tfpassword.getText(), tffirstname.getText(), tflastname.getText(), k);
            System.out.println(user);
            u.addUser(user);

            JOptionPane.showMessageDialog(null,"utilisateur ajoute");

            Parent rootRec2 = FXMLLoader.load(getClass().getResource("index.fxml"));
            Scene rec2 = new Scene(rootRec2);
            Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app.setScene(rec2);
            app.show();
           }
    }
       @FXML
    private void back(ActionEvent event) throws IOException {
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("index.fxml"));;
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

   
     public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }
}
