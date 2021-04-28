/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Catalogue;
import Entities.Categorie;
import Services.CatalogueService;
import Utils.Maconnexion;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author WIKI
 */
public class CategorieService {
      private List<Catalogue> catalogues;
    Connection cnx;
    PreparedStatement st;
    public CategorieService(){
       cnx = Maconnexion.getInstance().getConnection();
    }
     public void ajouterCategories(String query){
    Statement ste;
  
        try {
            ste=cnx.createStatement();
           
            ste.executeUpdate(query);
            System.out.println("categorie ajouter");
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    } 
     
     
     
     
    
     
     
     
     
     
     
     
     public ObservableList<Categorie> afficherCategorie(){
    ObservableList<Categorie> categories=FXCollections.observableArrayList();
    String sql="SELECT * FROM categorie c";

        try {
            st=cnx.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
while(rs.next()){
    Categorie categorie=new Categorie();
    categorie.setId(rs.getInt("id"));
  categorie.setImage(rs.getString("image"));
    categorie.setNom(rs.getString("nom"));
    categorie.setDescription(rs.getString("description"));
    categorie.setCatalogue_id(rs.getInt("catalogue_id"));
    categories.add(categorie);
}            
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("affiche failed");
        }
        return categories;
    }
     
    
     
    
    
}
