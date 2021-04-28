package Controllers;

import static Controllers.AddUserController.showAlert;
import Entities.User;
import Services.ServiceUser;
import Utils.Maconnexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.time.Duration;
import java.time.Instant;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
//import de.mkammerer.argon2.Argon2Types;

/**
 * FXML Controller class
 *
 * @author maminizer
 */
public class RegisterController implements Initializable {

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button signUpBtn;
    @FXML
    private Button signinBtn;
    @FXML
    private TextField tflastname;
    @FXML
    private PasswordField tfpassword;
    @FXML
    private TextField tffirstname;
    @FXML
    private TextField tfnbrtel;
    @FXML
    private TextField tfemail;

    private Parent root;
    private Scene scene;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = Maconnexion.getInstance().getConnection();
    }

    @FXML
    private void handleButtonAction(MouseEvent event) throws InterruptedException, IOException, SQLException {
        if (event.getSource() == signinBtn) {
            //login here
            try {
                //add you loading or delays - ;-)
                root = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }
    }

    @FXML
    private void add(ActionEvent event) throws SQLException, IOException {
        if (tfemail.getText().isEmpty() || tffirstname.getText().isEmpty()) {
            //showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Remplir les champs vides! ");
            notiferror("Remplir les champs vides!");

        } else if (!Pattern.matches("[a-zA-Z0-9@.]*", tfemail.getText())) {
            //showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez l'email! ");
            notiferror("Vérifiez l'email!");
            tfemail.requestFocus();
            tfemail.selectEnd();

        } else if (!Pattern.matches("[a-zA-Z]*", tffirstname.getText())) {
            //showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le nom! ");
            notiferror("Vérifiez le nom!");
            tffirstname.requestFocus();
            tffirstname.selectEnd();

        } else if (!Pattern.matches("[a-zA-Z]*", tflastname.getText())) {
            //showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le prenom! ");
            notiferror("Vérifiez le prenom!");
            tflastname.requestFocus();
            tflastname.selectEnd();

        } else if (!Pattern.matches("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]", tfnbrtel.getText())) {
            // showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le numero de telephone ! ");
            notiferror("Vérifiez le numero de telephone !");
            tfnbrtel.requestFocus();
            tfnbrtel.selectEnd();
        } else {

            ServiceUser u = new ServiceUser();
            User user;
            String password = tfpassword.getText();
            int k = Integer.parseInt(tfnbrtel.getText());

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hashed = argon2.hash(4, 64 * 1024, 1, password);
            System.out.println(hashed);
            boolean worked = argon2.verify(hashed, password);
            System.out.println("the hashing reverse is  " + worked);
            user = new User(tfemail.getText(), hashed, tffirstname.getText(), tflastname.getText(), k);
            System.out.println(user);
            u.addUser(user);
            notifsuccess("utilisateur ajoute");
//            FXMLLoader Login = new FXMLLoader(getClass().getResource("login.fxml"));
//            LoginController login = Login.getController();
//            login.sendArgon(argon2);
            //notifsuccess("Veuillez se connecter!!");
            //JOptionPane.showMessageDialog(null,"utilisateur ajoute");

        }
    }

    private void notifsuccess(String message) {
        String title = "Congratulations";
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(javafx.util.Duration.seconds(5));
    }

    private void notiferror(String message) {
        String title = "Failed";
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.ERROR);
        tray.showAndDismiss(javafx.util.Duration.seconds(5));
    }

}
