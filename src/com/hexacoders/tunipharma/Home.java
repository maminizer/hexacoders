/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.hexacoders.tunipharma.MyApplication;

/**
 *
 * @author yossr
 */
public class Home extends Form {

    Form current;

    public Home(Resources res) {
        super(new BorderLayout());
        current = this;
        setTitle("Home");
        setLayout(BoxLayout.y());
        getToolbar().addCommandToOverflowMenu("Exit",
                null, ev -> {
                    Display.getInstance().exitApplication();
                });

        //btnadmin
        Button btnAdmin = new Button("Admin Dashboard");

        //btnclient
        Button btnClient = new Button("Shop");

        btnAdmin.addActionListener(e -> new HomeAdmin(current).show());
        //btnClient.addActionListener(e -> new HomeClient(current).show());
        //**
        this.addAll(btnAdmin, btnClient);
    }

}
