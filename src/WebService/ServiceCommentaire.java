/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entity.Commentaire;
import Entity.Product;
import Utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yossr
 */
public class ServiceCommentaire {
       //var
    public boolean resultOK;
    public ArrayList<Commentaire> commentaires ;
  
        public static ServiceCommentaire instance=null;

    public ServiceCommentaire() {
    }
        
            public static ServiceCommentaire getInstance() {
        if (instance == null) {
            instance = new ServiceCommentaire();
        }
        return instance;
    }
                //ADD : Insert
     public boolean addCommentaire(Commentaire c,int idProduit){
         String url = Utils.BASE_URL+"/addComment"+ "?id="+idProduit+"&contenu="+c.getContenu()+"&user="+ c.getUserId();
                 ConnectionRequest req1 = new ConnectionRequest(url);
                 req1.setPost(false);
                  req1.addResponseListener((evt)->{
             
                  resultOK= req1.getResponseCode() == 200; // code http 200 OK
              
        });
        NetworkManager.getInstance().addToQueueAndWait(req1);
         return resultOK;
     }
          //Parsing     
    public ArrayList<Commentaire> parseJsonAction(String jsonText){
        try {
            commentaires=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> productsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
 
            List<Map<String,Object>> list = (List<Map<String,Object>>) productsListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
          
          Commentaire c = new Commentaire();
                float id = Float.parseFloat(obj.get("id").toString());
                c.setId((int)id);
                c.setContenu(obj.get("contenu").toString());
              
                //Ajouter la tâche extraite de la réponse Json à la liste
                commentaires.add(c);
            }
            
            
        } catch (IOException ex) {
            
        }
        return commentaires;
   
        }
    
    
    
    public ArrayList<Commentaire> getAllCommentsAction(int idProduit){
   //      ArrayList<Product> result = new  ArrayList<> ;
        String url = Utils.BASE_URL+"/displayComments"+"?id="+idProduit;
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                commentaires = parseJsonAction(new String(req.getResponseData())); // Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commentaires;
    }
}
