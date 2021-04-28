package Services;

import Entities.Cart;
import Entities.User;
import IServices.IServiceUser;
import Utils.DbConnect;
import Utils.Maconnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author khamm
 */
public class ServiceUser implements IServiceUser {

    Connection cnx;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    ObservableList<User> UserList = FXCollections.observableArrayList();

    @Override
    public ObservableList<User> AfficheUser(String username) throws SQLException {

        connection = DbConnect.getConnect();
        query = "SELECT * FROM `user` where firstname = '" + username + "' ";
        System.out.println(query);
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            UserList.add(new User(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("firstname")));

        }
        return UserList;
    }

    public ObservableList<User> ReturnList() {
        return UserList;
    }

    public void ServiceUser() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    public void addUser(User r) throws SQLException {

        try (Connection conn = DbConnect.getConnect()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO `user`(`email`, `roles`, `password`, `is_verified`, `firstname`, `lastname`, `nbr_tel`)"
                    + "VALUES ('"
                    + r.getEmail() + "','"
                    + "[\\\"ROLE_USER\\\"]" + "','"
                    + r.getPassword() + "','"
                    + "1" + "','"
                    + r.getUsername() + "','"
                    + r.getLastname() + "','"
                    + r.getNbr_tel() + "')");
        }

    }

    public List<User> ShowUsers() throws SQLException {
        List<User> UserList = new ArrayList<>();
        try {
            String requete = "select * from User";
            Statement ab = Maconnexion.getInstance().getConnection().createStatement();
            ResultSet rs = ab.executeQuery(requete);
            while (rs.next()) {
                User r = new User();
                r.setId(rs.getInt(1));
                r.setEmail(rs.getString(2));
                r.setPassword(rs.getString(4));
                r.setUsername(rs.getString(6));
                r.setLastname(rs.getString(7));
                r.setNbr_tel(rs.getInt(8));
                UserList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return UserList;
    }

    public void deleteUser(int id) {
        try {
            String requete = "DELETE FROM `user` WHERE  id =?";
            PreparedStatement pst = Maconnexion.getInstance().getConnection().prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Utilisateu supprimer");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateUser(User u) {
        try {
            String requete = "UPDATE `user` SET id='" + u.getId()
                    + "',email='" + u.getEmail()
                    + "',password='" + u.getPassword()
                    + "',firstname='" + u.getUsername()
                    + "',lastname='" + u.getLastname()
                    + "',nbr_tel='" + u.getNbr_tel()
                    + "' WHERE id =" + "'" + u.getId() + "'";
            System.out.println(requete);
            PreparedStatement pst = Maconnexion.getInstance().getConnection().prepareStatement(requete);
            int rowsUpdated = pst.executeUpdate(requete);
            if (rowsUpdated > 0) {
            }
            System.out.println("Utilisateur modifier");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<User> chercherUser(String type, String valeur) {
        ArrayList<User> listUser = new ArrayList<>();
        try {
            String requete = null;
            if (type.equals("firstname")) {
                requete = "SELECT * from User where firstname like '%" + valeur + "%'"; //MAJUSCULE NON OBLIGATOIRE 
            } else if (type.equals("lastname")) {
                requete = "SELECT * from User where lastname like '%" + valeur + "%'"; //MAJUSCULE NON OBLIGATOIRE 
            } else if (type.equals("Tout")) {
                requete = "SELECT * from User"; //MAJUSCULE NON OBLIGATOIRE 
            }
            PreparedStatement st = Maconnexion.getInstance().getConnection().prepareStatement(requete);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User r = new User();
                r.setId(rs.getInt(1));
                r.setEmail(rs.getString(2));
                r.setPassword(rs.getString(4));
                r.setUsername(rs.getString(6));
                r.setLastname(rs.getString(7));
                r.setNbr_tel(rs.getInt(8));
                listUser.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (listUser.isEmpty()) {
            System.out.println("Il y a aucune User dans cette date");
        }
        return listUser;
    }

    public List<User> trier(String type) throws SQLException {
        ArrayList<User> listUser = new ArrayList<>();

        String requete = null;
        if (type.equals("Trie firstname")) {
            requete = "SELECT * from User ORDER by firstname "; //MAJUSCULE NON OBLIGATOIRE 
        } else if (type.equals("Trie lastname")) {
            requete = "SELECT * from User ORDER by lastname"; //MAJUSCULE NON OBLIGATOIRE 
        } else if (type.equals("Trie Email")) {
            requete = "SELECT * from User ORDER by email";
        } else if (type.equals("Tout")) {
            requete = "SELECT * from User";
        }
        PreparedStatement st = Maconnexion.getInstance().getConnection().prepareStatement(requete);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            User r = new User();
            r.setId(rs.getInt(1));
            r.setEmail(rs.getString(2));
            r.setPassword(rs.getString(4));
            r.setUsername(rs.getString(6));
            r.setLastname(rs.getString(7));
            r.setNbr_tel(rs.getInt(8));
            listUser.add(r);
        }
        return listUser;

    }
}
