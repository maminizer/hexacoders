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
import Entity.Coffret;
import Utils.Utils;

/**
 *
 * @author WIKI
 */
public class coffretsServices {
     public ArrayList<Coffret> categories=new ArrayList<>();
      public static coffretsServices instance=null;
      private ConnectionRequest request;


    
    //var
    boolean resultOK;
        private coffretsServices() {
      request = new ConnectionRequest();
    }
            public static coffretsServices getInstance() {
        if (instance == null) {
            instance = new coffretsServices();
        }
        return instance;
    }
    //ADD : Insert
   public boolean addCoffrets(Coffret p){
         String url = Utils.BASE_URL+"/addCoffrets/"/*+ "?catalogue_id="+p.getCatalogue_id()+*/+"&nom="+p.getNom()+"&description="+p.getDescription()+"&image="+p.getImage()+"&prix="+p.getPrix()+"&offre="+p.getOffre();
                ConnectionRequest req1 = new ConnectionRequest(url);
                 req1.setPost(false);
                  req1.addResponseListener((evt)->{
             
                  resultOK= req1.getResponseCode() == 200; // code http 200 OK
              
        });
        NetworkManager.getInstance().addToQueueAndWait(req1);
         return resultOK;
     }

     //Parsing     
         public ArrayList<Coffret> parseJSONAction(String textJson){
        
             JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> tasksList = (ArrayList<Map<String,Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : tasksList) {
                
                Coffret t = new Coffret();
                float id = Float.parseFloat(obj.get("id").toString());
               t.setId((int) id);
             
                t.setNom(obj.get("nom").toString());
                t.setDescription(obj.get("description").toString());
                t.setImage(obj.get("image").toString());
               
                Double prix= Double.parseDouble(obj.get("prix").toString());
                t.setPrix(prix);
                Double offre= Double.parseDouble(obj.get("offre").toString());
                t.setOffre(offre);
          
                categories.add(t);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return categories;  
    }
    
    
    
    public ArrayList<Coffret> getCoffretsAll(){
        
         String url = Utils.BASE_URL+"/coffretsAll/";
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
        
    
        return categories;
    }
    
    
     public boolean UpdateCoffretsAction(Coffret p) {
     String url = Utils.BASE_URL+"/updateCoffrets" + "?id=" + p.getId()+ "&nom="+p.getNom()+"&description="+p.getDescription()+"&image="+p.getImage()+"&prix"+p.getPrix()+"&offre="+p.getOffre();
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
  public ArrayList<Coffret> DeleteCoffretsAction(int id){
        String url =Utils.BASE_URL + "/deleteCoffrets" + "?id=" + id;
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
