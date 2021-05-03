/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.entities.User;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.utils.Statics;
import java.util.ArrayList;
import com.codename1.io.JSONParser;
import java.util.Map;
import com.codename1.io.CharArrayReader;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author Firas
 */
public class ServiceUser {
    
    
    public ArrayList<User> users;
    public static ServiceUser instance=null;
    private ConnectionRequest req;
    public boolean resultOK;
    private ServiceUser() {
         req = new ConnectionRequest();
    }
    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }
    
    public boolean addUser(User u) {
String url = Statics.BASE_URL + "/api/v1/newuser/" + u.getEmail() + "/" + u.getPassword() + "/" + u.getFirstname() + "/" + u.getLastname() + "/" + u.getNbrTel();        
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
//    public boolean updateAbonnement(Abonnement t) {
//        String url = Statics.BASE_URL + "/abonnement/updateAbonnements/"+t.getId_ab()+"?" +"titre="+t.getTitre_ab() + "&type=" + t.getType_ab()+ "&prix=" + t.getPrix_ab()+ "&descr_ab=" + t.getDesc_ab();
//        
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200;
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }
//    
//    public boolean deleteAbonnement(Abonnement t) {
//        String url = Statics.BASE_URL + "/abonnement/deleteAbonnements/"+t.getId_ab();
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200;
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//    }
     
     
     
        public ArrayList<User> parseUsers(String jsonText){
        try {
            users=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                User u = new User();
                u.setId((int) Double.parseDouble(obj.get("id").toString()));
                u.setEmail(obj.get("email").toString());
                u.setPassword(obj.get("password").toString());
                u.setFirstname(obj.get("firstname").toString());
                u.setLastname(obj.get("lastname").toString());
                u.setNbrTel((int) Double.parseDouble(obj.get("nbrTel").toString()));
       
                users.add(u);
            }
                     
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }
        
        
    public ArrayList<User> getAllUsers(){
        String url = Statics.BASE_URL+"/api/v1/userall";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
}
