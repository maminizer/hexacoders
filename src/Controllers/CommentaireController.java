/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.commentaire;
import Entities.product;
import Service.ServiceCommentaire;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author yossr
 */
public class CommentaireController implements Initializable {

    @FXML
    private TableView<commentaire> TableCommentaire;
    @FXML
    private TableColumn<commentaire, Integer> colId;
    @FXML
    private TableColumn<commentaire, Integer> colIdProduit;
    @FXML
    private TableColumn<commentaire, String> colContenu;
    @FXML
    private Button BtnAdd;
    @FXML
    private Button BtnUpdate;
    @FXML
    private Button BtnDell;

    
    commentaire com = null;
    private product selectedProduct;

    @FXML
    private TextField TfContenu;
    @FXML
    private AnchorPane AnchorComm;
    @FXML
    private Button BtnBack;
    @FXML
    private Label labTitle;
    @FXML
    private Label labPrice;
    @FXML
    private Label labEnvente;
    @FXML
    private Label labQuantity;
    @FXML
    private Label labDescription;
    private Label labId;
    @FXML
    private ImageView imageview;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                  try {
            
            AfficherCommentaire();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
      
    }
            TableCommentaire.setOnMouseClicked(new EventHandler<MouseEvent>() 
          {
            @Override
            public void handle(MouseEvent event) {
                com = (commentaire)TableCommentaire.getSelectionModel().getSelectedItem();
                System.out.println(com);

               
      
                TfContenu.setText(com.getContenu());  
            } } );

    }

   private void AfficherCommentaire() throws SQLException {
        ServiceCommentaire sc = new ServiceCommentaire();
        ObservableList<commentaire> list = sc.AfficherCommentaire();
        
        colIdProduit.setCellValueFactory(new PropertyValueFactory<commentaire, Integer>("produit_id"));
        colContenu.setCellValueFactory(new PropertyValueFactory<commentaire, String>("contenu"));
        TableCommentaire.setItems(list);
   
   
   }    

    @FXML
    private void AjouterCommentaire(ActionEvent event) {
          ServiceCommentaire sc = new ServiceCommentaire();
          commentaire c = new commentaire();
        int pid = Integer.parseInt(labId.getText());
         c.setProduit_id(pid);
        c.setContenu(TfContenu.getText());
        sc. AddCommentaire(c);
        try { //afficher au fur et a mesure avec l'ajout
            AfficherCommentaire();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ModifierCommentaire(ActionEvent event) {
              ServiceCommentaire sc = new ServiceCommentaire();
                        System.out.println(com);
         if(com== null){
            JOptionPane.showMessageDialog(null, "choisir commentaire");
                   
        }else{
             
             String contenu = TfContenu.getText();
             sc.UpdateCommentaire(new commentaire(contenu),com.getId());
           
             JOptionPane.showMessageDialog(null, "commentaire modifier");
       
            TfContenu.clear();
            
             com=null;
               try {
                 AfficherCommentaire() ;
             } catch (SQLException ex) {
                 Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }

    @FXML
    private void SupprimerCommentaire(ActionEvent event) {
        ServiceCommentaire sc = new ServiceCommentaire();
        commentaire p = (commentaire)TableCommentaire.getSelectionModel().getSelectedItem();
        System.out.println(p);
        if(com== null){
            JOptionPane.showMessageDialog(null, "choisir Commentaire");
                   
        }else{
            sc.DeleteCommentaire(com.getId());
    
           try { //afficher au fur et a mesure avec l'ajout
            AfficherCommentaire();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           JOptionPane.showMessageDialog(null, "Commentaire supprimer");
          
            TfContenu.clear();
            
            com=null;
    }
    }
    public void initData(product product) throws FileNotFoundException, IOException{
         selectedProduct = product;
         
        int pid=selectedProduct.getId();
        String Spid=String.valueOf(pid);
        
                
        labTitle.setText(selectedProduct.getTitle());
       // labImage.setText(selectedProduct.getImage());
        
         int pprice=selectedProduct.getPrice();
         String Spprice=String.valueOf(pprice);
         labPrice.setText(Spprice);
            
         int penvente=selectedProduct.getEn_vente();
         String Spenvente=String.valueOf(penvente);
         labEnvente.setText(Spenvente);
         
         int pquantity=selectedProduct.getQuantity();
         String Spquantity=String.valueOf(pquantity);
         labQuantity.setText(Spquantity);
         
        labDescription.setText(selectedProduct.getDescription());
        
        InputStream is = selectedProduct.getImageJ();
        OutputStream os = new FileOutputStream(new File("photo.jpg"));
        	byte[] content = new byte[1024];
		int size = 0;
		while ((size = is.read(content)) != -1) {
			os.write(content, 0, size);
		}
		os.close();
		is.close();
		Image image = new Image("file:photo.jpg", imageview.getFitWidth(), imageview.getFitHeight(), true, true);
		imageview.setImage(image);
        
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        	Parent root = FXMLLoader.load(getClass().getResource("/views/Product.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }

}
