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
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import Utils.Utils;
import Entity.Livreur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PCS
 */
public class LivreurService {

    public ArrayList<Livreur> livreurs;
    public static LivreurService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    private boolean responseResult;

    public LivreurService() {
        req = new ConnectionRequest();

    }

    public static LivreurService getInstance() {
        if (instance == null) {
            instance = new LivreurService();
        }
        return instance;
    }

    public ArrayList<Livreur> parseLivreur(String jsonText) {

        try {
            livreurs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> LivreurListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) LivreurListJson.get("root");
            for (Map<String, Object> obj : list) {
                Livreur l = new Livreur();
                float id = Float.parseFloat(obj.get("id").toString());
                l.setId((int) id);
                l.setNom(obj.get("nom").toString());
                l.setPrenom(obj.get("prenom").toString());
                l.setDisponibilite(Boolean.parseBoolean(obj.get("disponibilite").toString()));
                l.setCin((int) Float.parseFloat(obj.get("cin").toString()));
                l.setNumtlf((int) Float.parseFloat(obj.get("numtlf").toString()));
                l.setEmail(obj.get("email").toString());
                livreurs.add(l);
            }
        } catch (IOException ex) {

        }
        return livreurs;
    }

    public ArrayList<Livreur> getAllLivreur() {
        String url = "http://127.0.0.1:8000/livreur/Mobi";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                livreurs = parseLivreur(new String(req.getResponseData()));

                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return livreurs;
    }

    public boolean addLivreur(Livreur l) {

        String url = Utils.BASE_URL + "/newMob?nom=" + l.getNom() + "&prenom=" + l.getPrenom() + "&cin=" + l.getCin() + "&email=" + l.getEmail() + "&numtlf=" + l.getNumtlf() + "&disponibilite=" + l.getDis();
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
//     
//
//     
//      public ArrayList<Livreur> rechercheProduit(){
//         Livreur p = new Livreur();
//         String url = Statics.BASE_URL + "/FindProduitMobile?id_produit="+p.getId();
//        req.setUrl(url);
//        req.setPost(false);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                livreurs = parseProduits(new String(req.getResponseData()));
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return livreurs;
//    }

//     
    public void SupprimerLivreur(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/livreur/deletelivMob/" + id);
        con.setPost(false);
        con.addResponseListener((evt) -> {
            System.out.println(con.getResponseData());
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

    }

    public void ModifierLivreur(int id, String nom, String prenom, int numtlf, int cin) {
        ConnectionRequest con = new ConnectionRequest();
        String Url = "http://127.0.0.1:8000/livreur/modMob/" + id + "/" + nom + "/" + prenom
                + "/" + numtlf + "/" + cin;
        con.setUrl(Url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
    }

}
