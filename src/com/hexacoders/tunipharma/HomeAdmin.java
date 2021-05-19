/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import Entity.Product;
import com.mycompany.myapp.gui.front.chart.ChartDemosForm;
import WebService.ServiceProduct;

/**
 *
 * @author yossr
 */
public class HomeAdmin extends Form {

    Form currentA;

    public HomeAdmin(Form previous) {
        super(new BorderLayout());
        currentA = this;
        setTitle("Home Admin");
        setLayout(BoxLayout.y());
        getToolbar().addCommandToOverflowMenu("Exit",
                null, ev -> {
                    Display.getInstance().exitApplication();
                });
        //insert code here BTN TO CRUD

        Button btnAjouterProd = new Button("Add Product");

        Button btnListProd = new Button("All Products");
        Button btnAjouterCata = new Button("Add Catalogue");

        Button btnListCata = new Button("All Catalogue");
        //show categorie
        Button btnAddCateg = new Button("Add Categorie");
        Button btnListCateg = new Button("All Categorie");
        //show coffrets
        Button btnAddCoff = new Button("Add Coffrets");
        Button btnListCoff = new Button("All Coffrets");

        //show catalogue
        btnAjouterCata.addActionListener(e -> new CatalogueAdd(currentA).show());

        btnListCata.addActionListener(e -> new CatalogueList(currentA).show());

        //show categorie
        btnAddCateg.addActionListener(e -> new CategorieAdd(currentA).show());
        btnListCateg.addActionListener(e -> new CategorieList(currentA).show());
        //show coffrets
        btnAddCoff.addActionListener(e -> new coffretsAdd(currentA).show());
        btnListCoff.addActionListener(e -> new CoffretsList(currentA).show());
        //**

        Button btnvalchart = new Button(" stat ");
        btnAjouterProd.addActionListener(e -> new AjouterProd(currentA).show());

        btnListProd.addActionListener(e -> new ListProd(currentA).show());
        //**
        this.addAll(btnAjouterProd, btnListProd, btnvalchart,btnAjouterCata,btnListCata,btnAddCateg,btnListCateg,btnAddCoff,btnListCoff);
        /**
         * ***********************************************************************************************
         */
        btnvalchart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ChartDemosForm().show();
            }
        });
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

}
