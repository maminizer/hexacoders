/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.gui;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.entities.User;
import com.codename1.services.ServiceUser;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Firas
 */
public class ListUsersForm extends Form{
Form current;
ArrayList<User> data = new ArrayList<>();

public ListUsersForm(Form previous) {
    setTitle("Listes  Des Abonnements");
    data = ServiceUser.getInstance().getAllUsers();
    Container y = new Container(new BoxLayout(BoxLayout.Y_AXIS));

    for (int i=0;i<data.size();i++){
        Container x = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container xx = new Container(new BoxLayout(BoxLayout.X_AXIS));
        User u = new User();
        u.setId(data.get(i).getId());
        u.setEmail(data.get(i).getEmail());
        u.setPassword(data.get(i).getPassword());
        u.setFirstname(data.get(i).getFirstname());
        u.setLastname(data.get(i).getLastname());
        Label separation = new Label("----------------------------");
        Label email = new Label("Email : " + data.get(i).getEmail());
        Label firstname = new Label("Firstname : "+ data.get(i).getFirstname());
        Label lastname = new Label("Lastname : "+ data.get(i).getLastname());
        Label nbrTel = new Label("NbrTel : "+ data.get(i).getNbrTel());
        
//        Button modif = new Button("Modifier");
//        Button supp = new Button("Supprimer");
        //CheckBox box = new CheckBox();
        
        
//        modif.addActionListener(e -> new ModifierAbonnementForm(current,ab).show());
//        supp.addActionListener(new ActionListener(){
//        @Override
//        public void actionPerformed(ActionEvent evt) {
//            ServiceAbonnement.getInstance().deleteAbonnement(ab);
//            Dialog.show("Success", "Memory Deleted Successfully.", "OK", "Cancel");
//        }
//        });
        
        x.addAll(email,firstname,lastname,nbrTel);
        //xx.addAll(supp,modif);
        y.addAll(x,xx,separation);
    }
    
    
    
    
    addAll(y);
    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack()); // Revenir vers l'interface précédente
}
}