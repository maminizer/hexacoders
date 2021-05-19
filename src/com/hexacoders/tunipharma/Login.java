/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import Entity.Session;
import Entity.User;
import WebService.ServiceUser;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import javafx.scene.image.ImageView;

/**
 *
 * @author Lenovo
 */
public class Login extends Form {

    Form current;
    private static User User;

    public Login(Resources res) {
        current = this;
        //setTitle("Login");
        setLayout(new BorderLayout());
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("ContainerWithPadding");

        final TextField username = new TextField();
        username.setHint("Username");
        final TextField pass = new TextField();
        pass.setHint("Password");
        pass.setConstraint(TextField.PASSWORD);

        center.addComponent(username);
        center.addComponent(pass);

        Button signIn = new Button("Sign In");
        signIn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (username.getText().length() == 0 || pass.getText().length() == 0) {
                    return;
                }
                User = ServiceUser.getInstance().Login(username.getText(), pass.getText());
                System.out.println(User);
                if (User == null) {
                    Dialog.show("Erreur", "Email et/ou mdp est invalide", new Command("OK"));
                } else {
                    Toolbar.setGlobalToolbar(false);
                    User = ServiceUser.getInstance().Login(username.getText(), pass.getText());
                    System.out.println(User);
                    new Welcome(User, res).show();

                    Toolbar.setGlobalToolbar(true);
                }
            }
        });

        center.addComponent(signIn);
        Button signUp = new Button("Sign Up");
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Toolbar.setGlobalToolbar(false);
                new SignUp(res).show();
            }
        });
        center.addComponent(signUp);
        addComponent(BorderLayout.CENTER, center);
    }
}
