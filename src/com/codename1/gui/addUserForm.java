/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.gui;

import com.codename1.entities.User;
import com.codename1.services.ServiceUser;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author maminizer
 */
public class addUserForm extends Form {

    public addUserForm(Form previous) {

        setTitle("Add a new User");
        setLayout(BoxLayout.y());

        TextField tfemail = new TextField("", "email");
        TextField tfpassword = new TextField("", "password");
        TextField tffirstname = new TextField("", "firstname");
        TextField tflastname = new TextField("", "lastname");
        TextField tfNbrTel = new TextField("", "NbrTel");
        Button btnValider = new Button("Add User");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfemail.getText().length()==0)||(tffirstname.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        User u = new User(tfemail.getText(), tfpassword.getText(), tffirstname.getText(), tflastname.getText(), Integer.parseInt(tfNbrTel.getText()));
                        if( ServiceUser.getInstance().addUser(u))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        addAll(tfemail,tfpassword,tffirstname,tflastname,tfNbrTel,btnValider);
        

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
                 e -> previous.showBack()); // Revenir vers l'interface précédente
    }

}
