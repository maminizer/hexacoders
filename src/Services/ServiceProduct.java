/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;


import Entities.Product;
import IServices.IServiceProduct;
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
public class ServiceProduct implements IServiceProduct{
    Connection cnx;
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    private static ServiceProduct instance;
        ObservableList<Product>  ProductList = FXCollections.observableArrayList();

    
    public ServiceProduct() {
        cnx = Maconnexion.getInstance().getConnection();
    }

    @Override
    public void addProduct(Product p) {
        try {
        Statement stm = cnx.createStatement();
        String query = "INSERT INTO `Product`(`title`, `price`, `quantity`) VALUES ('"+p.getTitle()+"','"+p.getPrice()+"','"+p.getQuantity()+"')";
                
        stm.executeUpdate(query);
        
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ObservableList<Product> AfficheProduct() throws SQLException {
        connection = DbConnect.getConnect();
        query = "SELECT * FROM `Product`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                ProductList.add(new  Product(
                        resultSet.getString("title"),
                        resultSet.getFloat("price"),
                        resultSet.getInt("quantity")));
                
                
            }
            return ProductList;
    }

    //hosni 
        
    public Product getServiceById(int id) throws SQLException {
        Product ls = new Product();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM product where id="+id;
        ResultSet rs = st.executeQuery(req);
          if(rs.next()){
              ls = new Product(rs.getInt(1),rs.getString("title"));
          }
        return ls;
   }
    public List<Product> getServicesByIdLab(int id) throws SQLException {
        List<Product> ls = new ArrayList<Product>();
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM coffrets_product where coffrets_id="+id;
        ResultSet rs = st.executeQuery(req);
          while(rs.next()){
              ls.add(this.getServiceById(rs.getInt("product_id")));
          }
        return ls;
   }
    //end function hosni

  @Override
    public void AddProduct(Product p, int lengh) {
       try {
     PreparedStatement prep;
			String query = "insert into product (title,imageJ,price,quantity,en_vente,description) values (? , ? , ? , ?, ?, ?)";// varchar 'var'
			prep = cnx.prepareStatement(query);

			prep.setString(1, p.getTitle());
                        prep.setBinaryStream(2,p.getImageJ(), lengh);
			prep.setInt(3, (int) p.getPrice());
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
             Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean UpdateProduct(Product p, int id) {
        if(chercher(id)){
 
     
        
      try {
                    String query = "UPDATE product SET title =? ,price =?,en_vente =?,description =?,quantity =? WHERE id=? ";
                    PreparedStatement pstmt = cnx.prepareStatement(query);
                    pstmt.setString(1,p.getTitle());
                    pstmt.setInt(2, (int) p.getPrice());
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
    public ObservableList<Product> AfficherProduct() throws SQLException {
            
            Statement stm = cnx.createStatement(); // transporteur des requêtes
            String query="SELECT * FROM `product`";
            ResultSet rst = stm.executeQuery(query); // résultat de la requete
            ObservableList<Product> products = FXCollections.observableArrayList() ;
            while(rst.next()){
                Product P = new Product();
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
    	
	public List<Product> displayRecherche(String critere) {
		String req = "select * from product where LOWER(title) like '%"+critere+"%' or LOWER(description) like '%"+critere+"%'";
		List<Product> list = new ArrayList<>();

		try {
                     
			resultSet = cnx.createStatement().executeQuery(req);
			while (resultSet.next()) {
				Product P = new Product();
			       P.setId(resultSet.getInt(1));
                P.setTitle(resultSet.getString("title"));
                P.setImageJ(resultSet.getBinaryStream("imageJ"));
                P.setPrice(resultSet.getInt("price"));
                P.setEn_vente(resultSet.getInt("en_vente"));
                P.setQuantity(resultSet.getInt("quantity"));
                P.setDescription(resultSet.getString("description"));
                list.add(P);
			}

		} catch (SQLException ex) {
			Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
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
