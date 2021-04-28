package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author maminizer
 */
public class UserDashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Stage primarystage;
    @FXML
    private Button logout;
    public String username;
    private Parent root;
    private Scene scene;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("login.fxml"));;
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    @FXML
    private void checkProduct(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/products.fxml"));
        root = loader.load();

        ProductsController fxmldocumentController = loader.getController();
        fxmldocumentController.sendData(username);

        stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void sendData(String text) {
        this.username = text;
        System.out.println("userdashboard   " + username); // verify username passed

    }
}
