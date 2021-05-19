/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entity.Commande;
import com.codename1.io.ConnectionRequest;

import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Utils.Utils;
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
public class ServiceCommande {

    public ArrayList<Commande> commandes;
    public static ServiceCommande instance = null;
    private ConnectionRequest req;
    public boolean resultOK;

    private ServiceCommande() {
        req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }

    public boolean addCommande(Commande c) {
        String url = Utils.BASE_URL + "/api/v1/commandeAdd/" + c.getNom_product() + "/" + c.getId_user() + "/" + c.getQuantity();
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
    public ArrayList<Commande> parseCommandes(String jsonText) {
        try {
            commandes = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Commande c = new Commande();
                c.setId_product((int) Double.parseDouble(obj.get("idProduct").toString()));
                c.setId_user((int) Double.parseDouble(obj.get("idUser").toString()));
                c.setQuantity((int) Double.parseDouble(obj.get("quantity").toString()));
                c.setValidated((boolean) Boolean.parseBoolean(obj.get("validated").toString()));

                commandes.add(c);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return commandes;
    }

    public ArrayList<Commande> getAllCommandes() {
        String url = Utils.BASE_URL + "/api/v1/CommandeAll";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commandes = parseCommandes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commandes;
    }
}
