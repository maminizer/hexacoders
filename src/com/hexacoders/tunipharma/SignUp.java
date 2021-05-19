/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.hexacoders.tunipharma;

import com.codename1.components.FloatingHint;
import Entity.User;
import WebService.ServiceUser;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUp extends Form {

    Form current;

    public SignUp(Resources res) {
        current = this;
        //setTitle("inscription");
        setLayout(new BorderLayout());
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        center.setUIID("ContainerWithPadding");

        TextField tfemail = new TextField("", "email", 20, TextArea.EMAILADDR);
        TextField tfpassword = new TextField("", "password", 20, TextArea.PASSWORD);
        TextField tffirstname = new TextField("", "firstname", 20, TextArea.ANY);
        TextField tflastname = new TextField("", "lastname", 20, TextArea.ANY);
        TextField tfNbrTel = new TextField("", "NbrTel", 8, TextArea.PHONENUMBER);

        center.addComponent(tfemail);
        center.addComponent(tfpassword);
        center.addComponent(tffirstname);
        center.addComponent(tflastname);
        center.addComponent(tfNbrTel);

        Button signUp = new Button("Sign Up");
        signUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (tfemail.getText().length() == 0 || tfpassword.getText().length() == 0) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    return;
                } else {
                    try {
                        Toolbar.setGlobalToolbar(false);
                        User u = new User(tfemail.getText(), tfpassword.getText(), tffirstname.getText(), tflastname.getText(), Integer.parseInt(tfNbrTel.getText()));
                        Toolbar.setGlobalToolbar(true);
                        if (ServiceUser.getInstance().addUser(u)) {
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

        center.addComponent(signUp);
        Button signIn = new Button("Sign In");
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Toolbar.setGlobalToolbar(false);
                System.out.println("Button login clicked");
                new Login(res).show();
            }
        });
        center.addComponent(signIn);
        addComponent(BorderLayout.CENTER, center);

    }
}
