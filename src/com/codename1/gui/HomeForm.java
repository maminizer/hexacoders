/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author maminizer
 */
public class HomeForm extends Form {
   Form current;
    public HomeForm() {
        current= this;
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnUser = new Button("Add User");
        Button btnListUsers = new Button("List users");
        
        btnUser.addActionListener(e-> new addUserForm(current).show());
        btnListUsers.addActionListener(e-> new ListUsersForm(current).show());
        
        addAll(btnListUsers, btnUser);
    }
}
