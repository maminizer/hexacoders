package Controllers;

import Entities.Cart;
import Entities.Product;
import Utils.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Services.ServiceCart;
import Services.ServiceProduct;
import Services.ServiceCommande;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * FXML Controller class
 *
 * @author khamm
 */
public class CartController implements Initializable {

    @FXML
    private TableView<Cart> table;
    @FXML
    private TableColumn<Cart, String> colTitle;
    @FXML
    private TableColumn<Cart, String> colPrice;
    @FXML
    private TableColumn<Cart, String> colQuantity;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ObservableList<Cart> CartList = FXCollections.observableArrayList();
    @FXML
    private TextField QuantityUpdateField;

    ObservableList<Product> data = FXCollections.observableArrayList();

    private String username;

    private float totalPrice;
    @FXML
    private Label afficheTotal;
    @FXML
    private TextField tfSearch;

    public CartController() {
    }

    public void sendData(String text) {
        this.username = text;
        System.out.println("cart   " + username); // verify username passed
        CartData();
    }

//    public void loadDatabase() {
//
//		Connection connection = null;
//		try {
//			// create a database connection
//                        connection = DbConnect.getConnect();
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 second
//			String query = "select * from cart where username = '" + username + "'";
//                        System.out.println("loadDatabase "+query);
//			ResultSet resultSet = statement.executeQuery(query);  
//			while (resultSet.next()) {
//                            CartList.add(new  Cart(
//                            resultSet.getString("title"),
//                            resultSet.getFloat("price"),
//                            resultSet.getInt("quantity")
//                            ));
//                           // System.out.println(resultSet.getString("title"));
//                            table.setItems(CartList);
//			}
//			statement.close();
//			resultSet.close(); 
//		} catch (SQLException e) {
//			// if the error message is "out of memory",
//			// it probably means no database file is found
//			System.err.println(e.getMessage());
//		} finally {
//			try {
//				if (connection != null)
//					connection.close();
//			} catch (SQLException e) {
//				// connection close failed.
//				System.err.println(e);
//			}
//		}
//	}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        table.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                // TODO Auto-generated method stub
                Cart selectedCart = table.getSelectionModel().getSelectedItem();
            }
        });
    }

    @FXML
    private void BackToHome(ActionEvent event) throws IOException {
        FXMLLoader Home = new FXMLLoader(getClass().getResource("/Views/products.fxml"));
        Parent HomeParent = (Parent) Home.load();
        Scene HomeScene = new Scene(HomeParent);
        Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

        ProductsController newUserName = Home.getController();
        newUserName.sendData(username);

        window.setScene(HomeScene);
        window.show();

    }

    private void CartData() {
        try {

            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            ServiceCart sc = new ServiceCart();
            table.setItems(sc.AfficheCart(username));

            //CartList1 = sc.AfficheCart(username);
            //table.getColumns().get(0).getCellData(0)
            //System.out.println("quantity  "+);
            CartList = sc.ReturnList();
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Cart> filteredData = new FilteredList<>(CartList, b -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(Cart -> {
                    // If filter text is empty, display all persons.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Cart.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches first name.
                    } else if (String.valueOf(Cart.getPrice()).indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(Cart.getQuantity()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false; // Does not match.
                    }
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Cart> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            table.setItems(sortedData);

            totalPrice = sc.totalPrice;
            System.out.println(totalPrice);
            afficheTotal.setText("$" + totalPrice);

        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void RemoveProduct(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Cart selectedCart = table.getSelectionModel().getSelectedItem();
            ServiceCart sc = new ServiceCart();

            if (sc.DeleteFromCart(selectedCart, username)) {
                Alert alert = new Alert(Alert.AlertType.NONE, selectedCart.getTitle() + " Removed Successfully!", ButtonType.OK);
                alert.showAndWait();
                FXMLLoader shoppingCartPage = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
                Parent shoppingCartParent = (Parent) shoppingCartPage.load();
                Scene shoppingCartScene = new Scene(shoppingCartParent);
                Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
                CartController newUserName = shoppingCartPage.getController();
                newUserName.sendData(username);
                window.setScene(shoppingCartScene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Product removing Failed!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please Select Product To Remove!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void UpdateQte(ActionEvent event) throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Cart selectedCart = table.getSelectionModel().getSelectedItem();
            int New_Qte = Integer.parseInt(QuantityUpdateField.getText());
            ServiceCart sc = new ServiceCart();

            if (sc.Update_Quantity(selectedCart, username, New_Qte)) {
                Alert alert = new Alert(Alert.AlertType.NONE, selectedCart.getTitle() + " Updated Successfully!", ButtonType.OK);
                alert.showAndWait();

                FXMLLoader shoppingCartPage = new FXMLLoader(getClass().getResource("ShoppingCart.fxml"));
                Parent shoppingCartParent = (Parent) shoppingCartPage.load();
                Scene shoppingCartScene = new Scene(shoppingCartParent);
                Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

                CartController newUserName = shoppingCartPage.getController();
                newUserName.sendData(username);

                window.setScene(shoppingCartScene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.NONE, "Product Updating Failed!", ButtonType.OK);
                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Please Select Product To Update!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

//    private boolean Update_Quantity(Cart c){
//		Connection connection = null;
//                try {
//                    int New_Qte = Integer.parseInt(QuantityUpdateField.getText());
//
//			// create a database connection
//                        connection = DbConnect.getConnect();
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 second
//			// check on commit
//
//			String UpdateCart = "UPDATE `cart` set `quantity` = '"+New_Qte+"' WHERE `title` = '"+c.getTitle()+"' AND `username` = '"+username+"' ";
//                        System.err.println(UpdateCart);
//			statement.executeUpdate(UpdateCart);
//                        
//			return true;
//		} catch (SQLException e) {
//			System.err.println(e.getMessage());
//		} finally {
//			try {
//				if (connection != null)
//					connection.close();
//			} catch (SQLException e) {
//				// connection close failed.
//				System.err.println(e);
//			}
//		}
//		return true;
//                
//    }
    @FXML
    private void Checkout(ActionEvent event) throws IOException {

        if (totalPrice != 0) {
            ServiceCommande sc = new ServiceCommande();
            sc.addCommande(username);
            System.out.println("add commande executed");
            Alert alert = new Alert(Alert.AlertType.NONE, "Proceed to Payment", ButtonType.OK);
            alert.showAndWait();
            FXMLLoader Paiement = new FXMLLoader(getClass().getResource("/Views/Paiement.fxml"));
            Parent PaiementParent = (Parent) Paiement.load();
            Scene PaiementScene = new Scene(PaiementParent);
            Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();

            PaiementController paiement = Paiement.getController();
            paiement.sendTotal(totalPrice, username);

            window.setScene(PaiementScene);
            window.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Your Cart is Empty !!", ButtonType.OK);
            alert.showAndWait();
        }

    }

}
