/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author maminizer
 */
public class AdminDashboardController implements Initializable {

    @FXML
    private Button userBtn;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    private Stage primarystage; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void handleButtonAction(ActionEvent event) throws InterruptedException, IOException, SQLException {
        if (event.getSource() == userBtn) {
            //login here
            try {
                //add you loading or delays - ;-)
                    root = FXMLLoader.load(getClass().getResource("index.fxml"));
                    stage =(Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
  
        } 
    }
          @FXML
    private void back(javafx.event.ActionEvent event) throws IOException {
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("login.fxml"));;
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }
    
}
