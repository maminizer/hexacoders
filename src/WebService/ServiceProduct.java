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
import com.codename1.l10n.ParseException;
import com.codename1.ui.events.ActionListener;
import Entity.Product;
import Utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author yossr
 */
public class ServiceProduct {
    //var
    public boolean resultOK;
    public ArrayList<Product> products ;
  
        public static ServiceProduct instance=null;

    public ServiceProduct() {
    }
        
            public static ServiceProduct getInstance() {
        if (instance == null) {
            instance = new ServiceProduct();
        }
        return instance;
    }
    //ADD : Insert
     public boolean addProduct(Product p){
         String url = Utils.BASE_URL+"/addProduct/"+ "?title="+p.getTitle()+"&image="+p.getImage()+"&price="+p.getPrice()+"&enVente="+p.getEnVente()+"&quantity="+p.getQuantity()+"&description="+p.getDescription();
                 ConnectionRequest req1 = new ConnectionRequest(url);
                 req1.setPost(false);
                  req1.addResponseListener((evt)->{
             
                  resultOK= req1.getResponseCode() == 200; // code http 200 OK
              
        });
        NetworkManager.getInstance().addToQueueAndWait(req1);
         return resultOK;
     }
     //Parsing     
    public ArrayList<Product> parseJsonAction(String jsonText){
        try {
            products=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> productsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
 
            List<Map<String,Object>> list = (List<Map<String,Object>>) productsListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                //Création des tâches et récupération de leurs données
                Product p = new Product();
          
                float id = Float.parseFloat(obj.get("id").toString());
                p.setId((int)id);
                p.setTitle(obj.get("title").toString());
                p.setImage(obj.get("image").toString());
                float price = Float.parseFloat(obj.get("price").toString());
                p.setPrice(price);
               
                boolean envente = Boolean.parseBoolean(obj.get("enVente").toString());
                p.setEnVente(envente);
                float quantity = Float.parseFloat(obj.get("quantity").toString());
                p.setQuantity((int) quantity);
                p.setDescription(obj.get("description").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                products.add(p);
            }
            
            
        } catch (IOException ex) {
            
        }
        return products;
   
        }
    
    
    public ArrayList<Product> getAllProductsAction(){
   //      ArrayList<Product> result = new  ArrayList<> ;
        String url = Utils.BASE_URL+"/displayProducts/";
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                products = parseJsonAction(new String(req.getResponseData())); // Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }

 public Product DetailProductAction(int id, Product p){
        String url = Utils.BASE_URL+"/detailProduct"+"?id="+id;
        ConnectionRequest req = new ConnectionRequest(url);
        String str = new String(req.getResponseData());
        req.addResponseListener(((evt)->{
            JSONParser j = new JSONParser();
            try {
                Map<String,Object> obj = j.parseJSON(new CharArrayReader(str.toCharArray()));
            p.setTitle(obj.get("title").toString());
                p.setImage(obj.get("image").toString());
                float price = Float.parseFloat(obj.get("price").toString());
                p.setPrice(price);
               
                boolean envente = Boolean.parseBoolean(obj.get("enVente").toString());
                p.setEnVente(envente);
                float quantity = Float.parseFloat(obj.get("quantity").toString());
                p.setQuantity((int) quantity);
                p.setDescription(obj.get("description").toString());
            } catch (IOException ex) {
                System.out.println("error related to mysql"+ex.getMessage());
            }
            System.out.println("data -----"+str);
        }));
         NetworkManager.getInstance().addToQueueAndWait(req);
        return p;
     
 }
          public boolean UpdateProductAction(Product p) {
     String url = Utils.BASE_URL+"/updateProduct" + "?id=" + p.getId()+ "&title="+p.getTitle()+"&image="+p.getImage()+"&price="+p.getPrice()+"&enVente="+p.getEnVente()+"&quantity="+p.getQuantity()+"&description="+p.getDescription();
        ConnectionRequest req = new ConnectionRequest(url);
     req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
      /************************************************************************************************/    
   public ArrayList<Product> DeleteProductAction(int id){
        String url =Utils.BASE_URL + "/deleteProduct" + "?id=" + id;
         ConnectionRequest req = new ConnectionRequest(url);
         System.out.println(url);
         req.setUrl(url);
         req.setPost(false);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
            
                    req.removeResponseListener(this);
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }   
   
       public ArrayList<Product> getFiltredProductsAction(boolean envente,int min,int max){
   //      ArrayList<Product> result = new  ArrayList<> ;
        String url = Utils.BASE_URL+"/displayProductsFiltred"+"?enVente="+envente+"&min="+min+"&max="+max;
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                products = parseJsonAction(new String(req.getResponseData())); // Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
       
           public ArrayList<Product> getSearchedProductsAction(String search){
   //      ArrayList<Product> result = new  ArrayList<> ;
        String url = Utils.BASE_URL+"/searchA?searchValue="+search;
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                products = parseJsonAction(new String(req.getResponseData())); // Logger.getLogger(ServiceProduct.class.getName()).log(Level.SEVERE, null, ex);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return products;
    }
}
               
              