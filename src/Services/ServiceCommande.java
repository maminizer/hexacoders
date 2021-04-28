package Services;

import Entities.Cart;
import Entities.Commande;
import Entities.Product;
import Entities.User;
import IServices.IServiceCommande;
import Utils.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Services.ServiceCart;
import Services.ServiceUser;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author khamm
 */
public class ServiceCommande implements IServiceCommande {

    Connection cnx;
    String queryC = null;
    String queryP = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSetC = null;
    ResultSet resultSetP = null;

    ObservableList<Product> ProductList = FXCollections.observableArrayList();
    ObservableList<Cart> CartList = FXCollections.observableArrayList();
    ObservableList<Commande> CommandeList = FXCollections.observableArrayList();
    ObservableList<User> UserList = FXCollections.observableArrayList();

    @Override
    public void addCommande(String username) {
        try {
            //get the current date 
            Date currentDate = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a" + "     " + "MM/dd/yyyy");

            connection = DbConnect.getConnect();
//            queryC = "SELECT * FROM `cart` where username = '"+username+"' ";
//            preparedStatement = connection.prepareStatement(queryC);
//            resultSetC = preparedStatement.executeQuery();
//            System.out.println(queryC);
            ServiceCart sc = new ServiceCart();
            CartList = sc.AfficheCart(username);
            //System.out.println(CartList.get(0).getTitle());

            System.out.println("---------------------------------------------------------------");
            System.out.println("---------------------------------------------------------------");

            for (int i = 0; i < CartList.size(); i++) {
                queryP = "SELECT * FROM `Product` where title = '" + CartList.get(i).getTitle() + "'";
                preparedStatement = connection.prepareStatement(queryP);
                resultSetP = preparedStatement.executeQuery();
                System.out.println(queryP);
                while (resultSetP.next()) {
                    ProductList.add(new Product(
                            resultSetP.getInt("id"),
                            resultSetP.getString("title"),
                            resultSetP.getFloat("price"),
                            resultSetP.getInt("quantity")));
                }

                System.out.println("---------------------------------------------------------------");

            }
            System.out.println("---------------------------------------------------------------");
            //System.out.println(ProductList.get(0).getId());

            System.out.println("---------------------------------------------------------------");
            ServiceUser su = new ServiceUser();
            UserList = su.AfficheUser(username);
            System.out.println("id de " + username);
            System.out.println(UserList.get(0).id);
            System.out.println("---------------------------------------------------------------");

            for (int i = 0; i < ProductList.size(); i++) {
                System.out.println("product id de " + username + ":");
                System.out.println(ProductList.get(i).getId());
                System.out.println("quantity :" + CartList.get(i).getQuantity());
                String addToCommande = "INSERT INTO `commande`(`id_product`, `id_user`, `quantity`,`validated`, `created_at`) values ('" + ProductList.get(i).getId() + "','" + UserList.get(0).id + "','" + CartList.get(i).getQuantity() + "','" + 1 + "','" + timeFormat.format(currentDate) + "')";
                System.out.println(addToCommande);
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);

                System.out.println(addToCommande);
                System.err.println(addToCommande);
                statement.executeUpdate(addToCommande);
                System.out.println("---------------------------------------------------------------");
            }

            System.out.println(timeFormat.format(currentDate));

            //delete data from the cart 
            ServiceCart scc = new ServiceCart();
            scc.DeleteFromCart_ALL(username);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommande.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
