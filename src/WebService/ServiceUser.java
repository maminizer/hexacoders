/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import com.codename1.io.ConnectionRequest;
import Entity.User;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Utils.Utils;
import java.util.ArrayList;
import com.codename1.io.JSONParser;
import java.util.Map;
import com.codename1.io.CharArrayReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author Firas
 */
public class ServiceUser {

    public ArrayList<User> users;
    public User UserSession = new User();
    public static ServiceUser instance = null;
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
        String url = Utils.BASE_URL + "/api/v1/newuser/" + u.getEmail() + "/" + u.getPassword() + "/" + u.getFirstname() + "/" + u.getLastname() + "/" + u.getNbrTel();
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

    public boolean updateUser(User u) {
        String url = Utils.BASE_URL + "/api/v1/updateuser/" +u.getId()+"/"+ u.getEmail() + "/" + u.getPassword() + "/" + u.getFirstname() + "/" + u.getLastname() + "/" + u.getNbrTel();        
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
    
    public boolean deleteAbonnement(int id) {
        String url = Utils.BASE_URL + "/api/v1/userdelete/"+id;
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
    
    
    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
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

    public ArrayList<User> getAllUsers() {
        String url = Utils.BASE_URL + "/api/v1/userall";
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

    public User Login(String username, String password) {
        String url = Utils.BASE_URL + "/loginMobile/" + username + "/" + password;
        System.out.println("urlll =" + Utils.BASE_URL);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                UserSession = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return UserSession;
    }

    public User parseUser(String jsonText) {

        User UserL = new User();
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> UserListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            if (UserListJson.get("type").equals("Login succeed")) {

                UserL.setId((int) Double.parseDouble(UserListJson.get("id").toString()));
                UserL.setFirstname(UserListJson.get("firstname").toString());
                UserL.setLastname(UserListJson.get("lastname").toString());
                UserL.setEmail(UserListJson.get("email").toString());
                UserL.setNbrTel((int) Double.parseDouble(UserListJson.get("nbrtel").toString()));
                UserL.setPassword(UserListJson.get("Password").toString());
                UserL.toString();

            } else {
                return null;
            }

        } catch (IOException ex) {

        }

        return UserL;
    }

    public User fetchUser(int id) {
        String idstring = String.valueOf(id);
        String url = Utils.BASE_URL + "/fetchuser/" + idstring;
        System.out.println("urlll =" + Utils.BASE_URL);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                UserSession = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return UserSession;
    }

}
