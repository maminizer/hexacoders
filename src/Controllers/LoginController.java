package Controllers;

import Entities.User;
import Utils.Maconnexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

;

/**
 * FXML Controller class
 *
 * @author maminizer
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignin;
    @FXML
    private Label btnForgot;
    @FXML
    private Button btnSignup;
    @FXML
    private Label lblErrors;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private Parent root;
    private Scene scene;
    private Stage stage;
    ObservableList<User> UserList = FXCollections.observableArrayList();
    Argon2 argon;

    public String username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = Maconnexion.getInstance().getConnection();

        if (connection == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    @FXML
    private void handleButtonAction(MouseEvent event) throws InterruptedException, IOException, SQLException {
        if (event.getSource() == btnSignin) {
            //login here
            if (logInAsUser().equals("Success")) {
                try {
                    username = getUsername(txtUsername.getText());
                    System.out.println(username);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/userDashboard.fxml"));
                    root = loader.load();

                    UserDashboardController userDashboardController = loader.getController();
                    userDashboardController.sendData(username);
                    stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            } else if (logInAsAdmin().equals("Success")) {
                try {
                    //add you loading or delays - ;-)
                    root = FXMLLoader.load(getClass().getResource("/Views/adminDashboard.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } else if (event.getSource() == btnSignup) {
            //login here
            try {
                root = FXMLLoader.load(getClass().getResource("/Views/register.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        }

    }

    private String logInAsUser() throws InterruptedException {
        String status = "Success";
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM user Where email = ? and roles = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, "[\"ROLE_USER\"]");
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {

                    System.out.println("No result in query");
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    System.out.println("result in query");
                    boolean valid = verifyHash(password, email);
                    if (valid == false) {
                        setLblError(Color.TOMATO, "Enter Correct Email/Password");
                        status = "Error";
                    } else {
                        setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    }

                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private String logInAsAdmin() throws InterruptedException {
        String status = "Success";
        String email = txtUsername.getText();

        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM user Where email = ? and roles = ?";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, "[\"ROLE_ADMIN\"]");
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    System.out.println("No result in query");
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    System.out.println("result in query");
                    boolean valid = verifyHash(password, email);
                    if (valid == false) {
                        setLblError(Color.TOMATO, "Enter Correct Email/Password");
                        status = "Error";
                    } else {
                        setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    }

                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    private boolean verifyHash(String PlainPassword, String email) throws SQLException {
        boolean valid = false;
        String sql = "SELECT PASSWORD FROM user WHERE email=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            //get the hashedPassword 
            String hashedPassword = resultSet.getString("password");
            System.out.println("verify hash Query Initialised with password  : " + hashedPassword);
            System.out.println("verify hash Query Initialised with password  : " + PlainPassword);
            //verify hashedPassword with PlainPassword
            boolean success;
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            success = argon2.verify(hashedPassword, PlainPassword);
            valid = success;
            System.out.println("verification done!");
        } else {
            System.out.println("verify hash Query Not Initialised");
            valid = false;
        }
        System.out.println("status code : " + valid);
        return valid;
    }

    public String getUsername(String email) throws SQLException {
        //System.out.println(txtUsername.getText());
        String firstname = "";
        String query = "SELECT firstname FROM `user` where email = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        resultSet = preparedStatement.executeQuery();
        // System.out.println(query);
        while (resultSet.next()) {
            firstname = resultSet.getString("firstname");
            // System.out.println(firstname);
        }

        return firstname;
    }

}
