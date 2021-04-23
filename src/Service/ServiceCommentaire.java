/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.commentaire;
import Services.IServiceCommentaire;
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
import utils.maConnexion;

/**
 *
 * @author yossr
 */
public class ServiceCommentaire implements IServiceCommentaire {
    Connection cnx;
    
    public ServiceCommentaire() {
        cnx = maConnexion.getInstance().getConnection();
    }

    @Override
    public void AddCommentaire(commentaire c) {
        try {
            Statement stm = cnx.createStatement();
                    String query = "INSERT INTO `commentaire`(`produit_id`, `contenu`) VALUES ('"+c.getProduit_id()+"','"+c.getContenu()+"')";
                    stm.executeUpdate(query);

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
    public Boolean chercher(int id) {
        String req ="select * from commentaire";
        List<Integer> list =new ArrayList<>();
         try {
             Statement st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       
 
        list.add(rs.getInt(1));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(commentaire.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list.contains(id);
    } 

    @Override
    public boolean DeleteCommentaire(int id) {
        if(chercher(id)){
        try {
               PreparedStatement pstmt = cnx.prepareStatement("delete from commentaire where id= ?;");
            
             pstmt.setInt(1, id);
             pstmt.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
         }
             System.out.println("delete valide");
         return true;
         }
         System.out.println("commentaire deleted n existe pas");
         return false;
    }

    @Override
    public boolean UpdateCommentaire(commentaire c, int id) {
        if(chercher(id)){
 
     
        
      try {
                    String query = "UPDATE product SET product_id =? ,contenu =? WHERE id=? ";
                    PreparedStatement pstmt = cnx.prepareStatement(query);
                    pstmt.setInt(1, c.getProduit_id());
                    pstmt.setString(2, c.getContenu());
                    pstmt.setInt(3, id);

                    pstmt.executeUpdate();
                   

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCommentaire.class.getName()).log(Level.SEVERE, null, ex);
        }  
      System.out.println("update valide");
         return true;
         }System.out.println("update invalid: commentaire nexiste pas");
        return false;
    }

    @Override
    public ObservableList<commentaire> AfficherCommentaire() throws SQLException {
        
            Statement stm = cnx.createStatement(); // transporteur des requêtes
            String query="SELECT * FROM `commentaire`";
            ResultSet rst = stm.executeQuery(query); // résultat de la requete
            ObservableList<commentaire> commentaires = FXCollections.observableArrayList() ;
            while(rst.next()){
                commentaire C = new commentaire();
                C.setId(rst.getInt("id"));
                C.setProduit_id(rst.getInt("produit_id"));
                C.setContenu(rst.getString("contenu"));

                commentaires.add(C);
                
            }
     
        
        return commentaires;
    }
    
}
