/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Catalogue;
import Utils.Maconnexion;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author WIKI
 */
public class CatalogueService {

    Connection cnx;
    PreparedStatement st;

    public CatalogueService() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    public void ajouterCatalogues(String query) {
        Statement ste;

        try {
            ste = cnx.createStatement();

            ste.executeUpdate(query);
            System.out.println("catalogue ajouter");
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<Catalogue> afficherCatalogue() {
        ObservableList<Catalogue> catalogues = FXCollections.observableArrayList();
        String sql = "select * from catalogue";
        try {
            st = cnx.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Catalogue catalogue = new Catalogue();
                catalogue.setId(rs.getInt("id"));
                catalogue.setNom(rs.getString("nom"));
                catalogue.setDescription(rs.getString("description"));
                catalogues.add(catalogue);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CatalogueService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("affiche failed");
        }
        return catalogues;
    }

}
