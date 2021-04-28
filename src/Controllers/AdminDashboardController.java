package Controllers;

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
    @FXML
    private Button catalogueBttn;

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
                    root = FXMLLoader.load(getClass().getResource("/Views/index.fxml"));
                    stage =(Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
  
        } else if(event.getSource()==catalogueBttn){
          try {
                //add you loading or delays - ;-)
                    root = FXMLLoader.load(getClass().getResource("/Views/AdminHome.fxml"));
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
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));;
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }
    
             @FXML
    private void ProdGst(ActionEvent event) throws IOException {
        		Parent root = FXMLLoader.load(getClass().getResource("/views/product.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
}
