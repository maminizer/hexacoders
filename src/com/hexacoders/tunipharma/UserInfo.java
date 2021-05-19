/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import Entity.User;
import WebService.ServiceUser;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.hexacoders.tunipharma.SideMenuBaseForm;
//import com.esprit.formulaire.StatsForm;

/**
 *
 * @author Lenovo
 */
class UserInfo extends SideMenuBaseForm {

    Form current;

    public UserInfo(User u, Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        setTitle("mon Compte");
        tb.setTitleCentered(false);
        // code goes here 
        TextField email = new TextField(u.getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);

        TextField password = new TextField(u.getPassword(), "Password", 20, TextField.PASSWORD);
        password.setUIID("TextFieldBlack");
        addStringValue("Password", password);

        TextField firstname = new TextField(u.getFirstname(), "Prénom", 20, TextField.EMAILADDR);
        firstname.setUIID("TextFieldBlack");
        addStringValue("Prénom", firstname);

        TextField lastname = new TextField(u.getLastname(), "Nom", 20, TextField.EMAILADDR);
        lastname.setUIID("TextFieldBlack");
        addStringValue("Nom", lastname);
        
        TextField nbrtel = new TextField(""+u.getNbrTel(), "Numéro du téléphone", 20,TextArea.PHONENUMBER);
        nbrtel.setUIID("TextFieldBlack");
        addStringValue("Numéro du téléphone", nbrtel);
        
        Button signUp = new Button("Mettre à jour");
        signUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (email.getText().length() == 0 || password.getText().length() == 0) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    return;
                } else {
                    try {
                        Toolbar.setGlobalToolbar(false);
                        User user = new User(u.getId(),email.getText(), password.getText(), firstname.getText(), lastname.getText(), Integer.parseInt(nbrtel.getText()));
                        System.out.println(user);
                        Toolbar.setGlobalToolbar(true);
                        if (ServiceUser.getInstance().updateUser(user)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Numéro de téléphone Invalide", new Command("OK"));
                    }
                }
            }
        });
        
        Button delete = new Button("supprimer mon compte");
        delete.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (email.getText().length() == 0 || password.getText().length() == 0) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    return;
                } else {
                    try {                        
                        Toolbar.setGlobalToolbar(true);
                        if (ServiceUser.getInstance().deleteAbonnement(u.getId())) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                            new Login(res).show();
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Numéro de téléphone Invalide", new Command("OK"));
                    }
                }
            }
        });
        
        

        addComponent(signUp);
        addComponent(delete);
        setupSideMenu(u, res);
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    protected void showOtherForm(User user, Resources res) {
        // new StatsForm(user,res).show();
    }
}
