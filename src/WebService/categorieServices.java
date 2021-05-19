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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import Entity.Catalogue;
import Entity.Categorie;
import Utils.Utils;

/**
 *
 * @author WIKI
 */
public class categorieServices {
     public ArrayList<Categorie> categories=new ArrayList<>();
      public static categorieServices instance=null;
      private ConnectionRequest request;


    
    //var
    boolean resultOK;
        private categorieServices() {
      request = new ConnectionRequest();
    }
            public static categorieServices getInstance() {
        if (instance == null) {
            instance = new categorieServices();
        }
        return instance;
    }
    //ADD : Insert
    public boolean addCategorie(Categorie p){
         String url = Utils.BASE_URL+"/addCategorie/"+ "?catalogue="+p.getCatalogue().getId()+"&nom="+p.getNom()+"&description="+p.getDescription()+"&image="+p.getImage();
         System.out.println("***************: "+p.getCatalogue().getId());   
         ConnectionRequest req1 = new ConnectionRequest(url);
                 req1.setPost(false);
                  req1.addResponseListener((evt)->{
             
                  resultOK= req1.getResponseCode() == 200; // code http 200 OK
              
        });
        NetworkManager.getInstance().addToQueueAndWait(req1);
         return resultOK;
     }
     //Parsing     
         public ArrayList<Categorie> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> tasksList = (ArrayList<Map<String,Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : tasksList) {
                
                Categorie t = new Categorie();
                float id = Float.parseFloat(obj.get("id").toString());
               t.setId((int) id);
               //urgent this
               
              Map<String,Object> catalogue_id =(Map<String,Object>) obj.get("catalogue");
            // float catalogue_id1 = Float.parseFloat(catalogue_id.getId().);
          //  int idc=catalogue_id.getId();
             float id1 = Float.parseFloat(catalogue_id.get("id").toString());
              t.setCatalogue( new Catalogue((int) id1));
                System.out.println("id####:::"+id1);
              //  for (Object object : obj) {
               //  float catalogue_id = Float.parseFloat(object.get("catalogue").toString());
             // t.setCatalogue_id((int) catalogue_id);   
            //  }
                t.setNom(obj.get("nom").toString());
                t.setDescription(obj.get("description").toString());
                t.setImage(obj.get("image").toString());
                //if you want add categories just add t.setCategories
                categories.add(t);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return categories;  
    }
    
    
    
    public ArrayList<Categorie> getCategorieAll(){
        
         String url = Utils.BASE_URL+"/categorieAll/";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             categories = parseJSONAction(new String(request.getResponseData()));
             request.removeResponseListener(this);
             }
         });
         
        NetworkManager.getInstance().addToQueueAndWait(request);
        
       // NetworkManager.getInstance().addToQueueAndWait(request);
        return categories;
    }
    
    
     public boolean UpdateCategorieAction(Categorie p) {
     String url = Utils.BASE_URL+"/updateCategorie" + "?id=" + p.getId()+ "&catalogue="+p.getCatalogue().getId()+"&nom="+p.getNom()+"&description="+p.getDescription()+"&image="+p.getImage();
       try{
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
    
       
       }catch(Exception e){
           System.out.println("error:"+e);
        }
           return resultOK;
    }
      /************************************************************************************************/    
   public ArrayList<Categorie> DeleteCategorieAction(int id){
        String url =Utils.BASE_URL + "/deleteCategorie" + "?id=" + id;
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
        return categories;
    }  
   
}
