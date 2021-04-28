/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Coffrets;
import Entities.Product;
import Services.CatalogueService;
import Utils.Maconnexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author WIKI
 */
public class CoffretsService {

    private List<Coffrets> coffrets;
    Connection cnx;
    PreparedStatement st;

    public CoffretsService() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    public List<Coffrets> afficherCoffret() throws SQLException {
        List<Coffrets> ls = new ArrayList<Coffrets>();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM coffrets";
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            ServiceProduct ss = new ServiceProduct();

            List<Product> s = ss.getServicesByIdLab(rs.getInt(1));
            ls.add(new Coffrets(rs.getInt("id"), rs.getString("nom"), rs.getString("description"), rs.getString("image"), rs.getDouble("prix"), rs.getDouble("offre"), new HashSet<Product>(s)));
        }
        return ls;

    }

    //afficher coffrets
    /*  public ObservableList<Coffrets> afficherCoffret(){
    ObservableList<Coffrets> coffrets=FXCollections.observableArrayList();
    String sql="SELECT coffrets.id,coffrets.nom,coffrets.description,coffrets.image,product.id AS product_id,product.title AS product_title FROM coffrets JOIN coffrets_product ON (coffrets.id=coffrets_product.coffrets_id) JOIN product ON (coffrets_product.product_id=product.id))";

        try {
            st=cnx.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
while(rs.next()){
    Coffrets coffret=new Coffrets();
   
    coffret.setId(rs.getInt("id"));
  coffret.setImage(rs.getString("image"));
    coffret.setNom(rs.getString("nom"));
    coffret.setDescription(rs.getString("description"));
     coffret.setPrix(rs.getDouble("prix"));
  coffret.setOffre(rs.getDouble("offre")); 

    coffrets.add(coffret);
   
    
}            
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("affiche failed");
        }
        return coffrets;
    }
     */
    ///end affiche coffrets
    public ObservableList<Product> afficherProduct() {
        ObservableList<Product> catalogues = FXCollections.observableArrayList();
        String sql = "select * from product";
        try {
            st = cnx.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setImage(rs.getString("image"));
                product.setCategorie_id(rs.getInt("categorie_id"));
                product.setQuantity(rs.getInt("quantity"));
                product.setEn_vente(rs.getInt("en_vente"));
                product.setPrice(rs.getFloat("price"));
                catalogues.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("affiche failed");
        }
        return catalogues;
    }

    public void ajouterCoffrets(String query) {
        Statement ste;

        try {
            //        PreparedStatement ste2;
            //String sql = "INSERT INTO laboratoire_service (coffrets_id, product_id) VALUES ('"+c.getId()+"', '"+p.getId()+"') ";
            //   ste2 = cnx.prepareStatement(sql);
            //   System.out.println(c.getId());
            //  ste2.executeUpdate();
            ste = cnx.createStatement();

            ste.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

}
