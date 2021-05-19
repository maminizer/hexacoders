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
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import Entity.Catalogue;
import Utils.Utils;


/**
 *
 * @author WIKI
 */

public class catalogueServices {
     public ArrayList<Catalogue> catalogues=new ArrayList<>();
      public static catalogueServices instance=null;
      private ConnectionRequest request;


    
    //var
    boolean resultOK;
        private catalogueServices() {
      request = new ConnectionRequest();
    }
            public static catalogueServices getInstance() {
        if (instance == null) {
            instance = new catalogueServices();
        }
        return instance;
    }
//insert
    public boolean addCatalogue(Catalogue c){
        String url=Utils.BASE_URL+"/newCatalogue/"+c.getNom()+"/"+c.getDescription();
         request=new ConnectionRequest(url);
        request.setPost(false);
        request.addResponseListener((evt) -> {
            resultOK=request.getResponseCode()==200;
            
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
    return resultOK;
    
    }
   //parse catalogues
    
    
           public ArrayList<Catalogue> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> tasksList = (ArrayList<Map<String,Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : tasksList) {
                
                Catalogue t = new Catalogue();
                float id = Float.parseFloat(obj.get("id").toString());
               t.setId((int) id);
              
                t.setNom(obj.get("nom").toString());
                t.setDescription(obj.get("description").toString());
                //if you want add categories just add t.setCategories
                catalogues.add(t);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return catalogues;  
    }

//show all
    
    public ArrayList<Catalogue> getCatalogueAll(){
        
         String url = Utils.BASE_URL+"/catalogueAll/";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             catalogues = parseJSONAction(new String(request.getResponseData()));
             request.removeResponseListener(this);
             }
         });
         
        NetworkManager.getInstance().addToQueueAndWait(request);
        
       // NetworkManager.getInstance().addToQueueAndWait(request);
        return catalogues;
    }
    
    //update
     public boolean UpdateCatalogueAction(Catalogue p) {
     String url = Utils.BASE_URL+"/updateCatalogue" + "?id=" + p.getId()+ "&nom="+p.getNom()+"&description="+p.getDescription();
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
   public ArrayList<Catalogue> DeleteCatalogueAction(int id){
        String url =Utils.BASE_URL + "/deleteCatalogue" + "?id=" + id;
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
        return catalogues;
    }   
}
