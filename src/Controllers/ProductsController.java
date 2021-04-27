package Controllers;

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

/**
 *
 * @author khamm
 */
public class ProductsController implements Initializable {
    
    private Label label;
    @FXML
    private TableColumn<Product, String> colTitle;
    @FXML
    private TableColumn<Product, String> colPrice;
    @FXML
    private TableColumn<Product, String> colQuantity;
    @FXML
    private TableView<Product> table;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Product student = null ;
    
    ObservableList<Product>  ProductList = FXCollections.observableArrayList();
    @FXML
    private TextField QuantityField;
    
    private String username;


    public ProductsController() {
    }


    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loadData();
//           if(username != null){
//        loadData();
//    }
        
        table.setOnMousePressed(new EventHandler<MouseEvent>() {
                    
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Product selectedProduct = table.getSelectionModel().getSelectedItem();
			}
		});
        
    }    

    @FXML
    private void addToCart(ActionEvent event) {
        
        if (table.getSelectionModel().getSelectedItem() != null) {
            
            ServiceCart sc = new ServiceCart();
            
            int qte = Integer.parseInt(QuantityField.getText());
			Product selectedProduct = table.getSelectionModel().getSelectedItem();
			if (sc.saveToCart(selectedProduct, username,qte ) && qte !=0 ) {
				Alert alert = new Alert(Alert.AlertType.NONE, selectedProduct.getTitle() +" Added Successfully!", ButtonType.OK);
				alert.showAndWait();
                                System.out.println(qte);
				return;
			} else {
				Alert alert = new Alert(Alert.AlertType.NONE, "Product Adding Failed!", ButtonType.OK);
				alert.showAndWait();
				return;
			}
		}
		else {
			Alert alert = new Alert(Alert.AlertType.NONE, "Please Select Product To Add!", ButtonType.OK);
			alert.showAndWait();
			return;
		}
    }
    
//    private boolean saveToCart(Product p) {
//		Connection connection = null;
//		try {
//                    
//                    //set quantity
//                    int qte = Integer.parseInt(QuantityField.getText());
//                    System.out.println(qte);
//                    
//			// create a database connection
//                        connection = DbConnect.getConnect();
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 second
//			// check on commit
//			String addToCart = "insert into `cart`(`title`, `price`, `quantity`, `username`) values ('"+p.getTitle()+"','"+p.getPrice()+"','"+qte+"','"+username+"')";
//                        System.out.println(addToCart);
//                        System.err.println(addToCart);
//			statement.executeUpdate(addToCart);
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
//	}
    

    @FXML
    private void viewCart(ActionEvent event) throws IOException{
        
        FXMLLoader shoppingCartPage = new FXMLLoader(getClass().getResource("/Views/Cart.fxml"));
	Parent shoppingCartParent = (Parent) shoppingCartPage.load();
	Scene shoppingCartScene = new Scene(shoppingCartParent);
	Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        
        CartController newUserName = shoppingCartPage.getController();
	newUserName.sendData(username);
        
        window.setScene(shoppingCartScene);
        window.show();
    }
    
    
    private void loadData(){
        try {
            connection = DbConnect.getConnect();
            
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            
            ServiceProduct sp = new ServiceProduct();
            table.setItems(sp.AfficheProduct());
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    
//    public void loadDatabase() {
//
//		Connection connection = null;
//		try {
//			// create a database connection
//			connection = DbConnect.getConnect();
//			Statement statement = connection.createStatement();
//			statement.setQueryTimeout(30); // set timeout to 30 sec.
//			String query = "select * from Product";
//			ResultSet rs = statement.executeQuery(query);
//			while (rs.next()) {
//				Product existedProduct = new Product(rs.getString("title"), rs.getFloat("price"), rs.getInt("quantity"));
//
//				ProductList.add(existedProduct);
//				table.setItems(ProductList);
//			}
//			statement.close();
//			rs.close();
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
    
    
    	public void sendData(String text) {
		this.username = text;
		System.out.println("home maminizer  "+username); // verify username passed
		//loadDatabase();
	}
    
}
