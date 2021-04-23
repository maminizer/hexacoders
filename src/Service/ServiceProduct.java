/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.product;
import Services.IServiceProduct;
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
public class ServiceProduct implements IServiceProduct {
Connection cnx;
private static ServiceProduct instance;
	private Statement st;
	private ResultSet rs;
public ServiceProduct() {
        cnx = maConnexion.getInstance().getConnection();
    }
    @Override
    public void AddProduct(product p, int lengh) {
       try {
     PreparedStatement prep;
			String query = "insert into product (title,imageJ,price,quantity,en_vente,description) values (? , ? , ? , ?, ?, ?)";// varchar 'var'
			prep = cnx.prepareStatement(query);

			prep.setString(1, p.getTitle());
                        prep.setBinaryStream(2,p.getImageJ(), lengh);
			prep.setInt(3, p.getPrice());
                        prep.setInt(4, p.getQuantity());
                      	prep.setInt(5, p.getEn_vente());
                        prep.setString(6, p.getDescription());
			prep.executeUpdate();
			System.out.println("Ajout OK!");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    	public static ServiceProduct getInstance() throws ClassNotFoundException {
		if (instance == null)
			instance = new ServiceProduct();
		return instance;
	}
public Boolean chercher(int id) {
        String req ="select * from product";
        List<Integer> list =new ArrayList<>();
         try {
             Statement st=cnx.createStatement();
             ResultSet rs =st.executeQuery(req);
               while(rs.next())
               {       
 
        list.add(rs.getInt(1));
               
               }
         } catch (SQLException ex) {
             Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
         }
        return list.contains(id);
    } 
    @Override
    public boolean DeleteProduct(int id) {
       if(chercher(id)){
        try {
               PreparedStatement pstmt = cnx.prepareStatement("delete from product where id= ?;");
            
             pstmt.setInt(1, id);
             pstmt.execute();
         } catch (SQLException ex) {
             Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
         }
             System.out.println("delete valide");
         return true;
         }
         System.out.println("Producy deleted n existe pas");
         return false;
    }

    @Override
    public boolean UpdateProduct(product p, int id) {
        if(chercher(id)){
 
     
        
      try {
                    String query = "UPDATE product SET title =? ,price =?,en_vente =?,description =?,quantity =? WHERE id=? ";
                    PreparedStatement pstmt = cnx.prepareStatement(query);
                    pstmt.setString(1,p.getTitle());
                    pstmt.setInt(2, p.getPrice());
                    pstmt.setInt(3, p.getEn_vente());
                    pstmt.setString(4, p.getDescription());
                    pstmt.setInt(5, p.getQuantity());
                    pstmt.setInt(6, id);
                    pstmt.executeUpdate();
                   

        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
        }  
      System.out.println("update valide");
         return true;
         }System.out.println("update invalid: produit nexiste pas");
        return false;
    }
    
    


               



    @Override
    public ObservableList<product> AfficherProduct() throws SQLException {
            
            Statement stm = cnx.createStatement(); // transporteur des requêtes
            String query="SELECT * FROM `product`";
            ResultSet rst = stm.executeQuery(query); // résultat de la requete
            ObservableList<product> products = FXCollections.observableArrayList() ;
            while(rst.next()){
                product P = new product();
                P.setId(rst.getInt("id"));
                P.setTitle(rst.getString("title"));
                P.setImageJ(rst.getBinaryStream("imageJ"));
                P.setPrice(rst.getInt("price"));
                P.setEn_vente(rst.getInt("en_vente"));
                P.setQuantity(rst.getInt("quantity"));
                P.setDescription(rst.getString("description"));
                
                
                products.add(P);
                
            }
     
        
        return products;
    }
    	
	public List<product> displayRecherche(String critere) {
		String req = "select * from product where LOWER(title) like '%"+critere+"%' or LOWER(description) like '%"+critere+"%'";
		List<product> list = new ArrayList<>();

		try {
                     
			rs = cnx.createStatement().executeQuery(req);
			while (rs.next()) {
				product P = new product();
			       P.setId(rs.getInt(1));
                P.setTitle(rs.getString("title"));
                P.setImageJ(rs.getBinaryStream("imageJ"));
                P.setPrice(rs.getInt("price"));
                P.setEn_vente(rs.getInt("en_vente"));
                P.setQuantity(rs.getInt("quantity"));
                P.setDescription(rs.getString("description"));
                list.add(P);
			}

		} catch (SQLException ex) {
			Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
		}
		return list;
	}
    	public static boolean ControleFloat(String prix) {
		if ((prix).matches("([0-9]*[.])?[0-9]+")){
			return true;
		}
		return false;
	}
	
	public static boolean ControleInt(String prix) {
		if ((prix).matches("[0-9]+")){
			return true;
		}
		return false;
	}
	
	public static boolean ControleNom(String str) {
		str = str.toLowerCase();
                if (str.length() == 0)
                    return false;
		char[] charArray = str.toCharArray();
                
		for (int i = 0; i < charArray.length; i++) {
			char ch = charArray[i];
			if (!((ch >= 'a' && ch <= 'z') || (String.valueOf(ch)).equals(" "))) {
				return false;
			}
		}
		return true;
	}
    
}
