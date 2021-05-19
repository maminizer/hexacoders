/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.capture.Capture;
import com.codename1.components.CheckBoxList;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import Entity.Catalogue;
import Entity.Coffret;
import Entity.Product;

import WebService.categorieServices;
import WebService.coffretsServices;

/**
 *
 * @author WIKI
 */
public class CoffretsUpdate extends Form {

    String path;
    Form currentC;

    public CoffretsUpdate(Form previous) {
        currentC = this;
        setTitle("Update Coffrets");
        setLayout(BoxLayout.y());
        getToolbar().addCommandToOverflowMenu("Exit",
                null, ev -> {
                    Display.getInstance().exitApplication();
                });
        getToolbar().addMaterialCommandToLeftBar("back",
                FontImage.MATERIAL_BACKUP, ev -> {
                    new CoffretsList(currentC).show();
                });

        Container c = new Container(new FlowLayout(Container.CENTER));
        //c.add(a);
        c.getAllStyles().setMargin(Component.TOP, 70);
        add(c);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        //insert code here code de modifer

        /**
         * **************************************************************************************
         */
        /**
         * ******************************Nom**************************************************
         */
        Label nom = new Label("Nom :");
        TextArea nom1 = new TextArea("" + CoffretsList.b.getNom());
        nom.getAllStyles().setMargin(Component.BOTTOM, 40);

        nom1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle = nom1.getAllStyles();
        butStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        nom.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        nom1.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        nom1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        nom.getAllStyles().setFgColor(0x000000);
        nom1.getAllStyles().setFgColor(0x000000);
        /**
         * ********************************************************************************
         */
        /**
         * **************************Description******************************************************
         */

        Label description = new Label("Description :");
        TextArea description1 = new TextArea("" + CoffretsList.b.getDescription());
        description.getAllStyles().setMargin(Component.BOTTOM, 40);

        description1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle1 = description1.getAllStyles();
        butStyle1.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(130).
                stroke(borderStroke));
        description.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        description.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        description1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        description.getAllStyles().setFgColor(0x000000);
        description1.getAllStyles().setFgColor(0x000000);
        /**
         * ********************************************************************************
         */

        /**
         * **************************Image******************************************************
         */
        Label image = new Label("Image :");
        TextArea image1 = new TextArea("" + CoffretsList.b.getImage());
        image.getAllStyles().setMargin(Component.BOTTOM, 40);

        image1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle2 = image1.getAllStyles();
        butStyle2.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        image.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        image.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        image1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        image.getAllStyles().setFgColor(0x000000);
        image1.getAllStyles().setFgColor(0x000000);
        Label labImage = new Label();
        /**
         * ********************************************************************************
         */
        /**
         * ***********************************************************************************************
         */
        /**
         * **************************Prix******************************************************
         */

        Label prix = new Label("Prix :");
        TextArea prix1 = new TextArea("" + CoffretsList.b.getPrix());
        prix.getAllStyles().setMargin(Component.BOTTOM, 40);

        prix1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle3 = prix1.getAllStyles();
        butStyle3.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        prix.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        prix.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        prix1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        prix.getAllStyles().setFgColor(0x000000);
        prix1.getAllStyles().setFgColor(0x000000);

        /**
         * ********************************************************************************
         */
        /**
         * **************************offre******************************************************
         */
        Label offre = new Label("Offre :");
        TextArea offre1 = new TextArea("" + CoffretsList.b.getPrix());
        offre.getAllStyles().setMargin(Component.BOTTOM, 40);

        offre1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle4 = offre1.getAllStyles();
        butStyle4.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        offre.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        offre.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        offre1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        offre.getAllStyles().setFgColor(0x000000);
        offre1.getAllStyles().setFgColor(0x000000);

        /**
         * ********************************************************************************
         */
        /*     
CheckBox produit;
        for(Product s:ServiceProduct.getInstance().getAllProductsAction()){
        produit=new CheckBox(s.getTitle());
            
    
    }*/
        /**
         * ********************************************************************************
         */
        Button valider = new Button("modifier");

        Button btCapture = new Button("upload");

        btCapture.addActionListener((evt) -> {
            path = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (path != null) {
                try {
                    Image img = Image.createImage(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        /**
         * ***********************************************************************************************
         */
        Container cnt3 = new Container(new FlowLayout(Container.CENTER));
        /**
         * ********************************************************************************
         */
        Container c1 = BoxLayout.encloseY(nom, description, image, prix, offre);

        Container c3 = BoxLayout.encloseY(nom1, description1, image1, prix1, offre1);

        setScrollableY(false);

        cnt3.addAll(valider, btCapture, labImage);
        Container c4 = BoxLayout.encloseX(c1, c3);

        c.getAllStyles().setMargin(Component.BOTTOM, 150);
        c4.getAllStyles().setMargin(Component.TOP, 10);
        c4.getAllStyles().setMargin(Component.LEFT, 50);
        c4.getAllStyles().setMargin(Component.RIGHT, 50);
//c4.getStyle().setBgColor(0x000000);
        c4.getStyle().setBgTransparency(250);

        valider.addActionListener(e -> {

            String nom2 = nom1.getText();
            System.out.println("nom" + nom2);
            String description2 = description1.getText();
            System.out.println("description" + description2);
            String image2 = path;
            System.out.println("image" + image2);
            Double prix2 = Double.parseDouble(prix1.getText());
            Double offre2 = Double.parseDouble(offre1.getText());
//String produit2=produit.getText();

//CategorieList.b.setCatalogue(new Catalogue(Integer.parseInt(envente3)));
            CoffretsList.b.setNom(nom2);
            CoffretsList.b.setDescription(description2);
            CoffretsList.b.setImage(image2);
            CoffretsList.b.setPrix(prix2);
            CoffretsList.b.setOffre(offre2);

            if (coffretsServices.getInstance().UpdateCoffretsAction(CoffretsList.b)) {
                Dialog.show("Success", "evenement modifié ", new Command("OK"));

            } else {
                Dialog.show("ERROR", " Server error", new Command("OK"));
            }

        });

        Container MainCont = BoxLayout.encloseY(c4, cnt3);
        MainCont.getAllStyles().setMargin(Component.TOP, 1);
        MainCont.getAllStyles().setMargin(Component.LEFT, 1);
        MainCont.getAllStyles().setMargin(Component.RIGHT, 1);
        MainCont.getAllStyles().setMargin(Component.BOTTOM, 1);
        MainCont.getStyle().setBgColor(0xFFFFFF);
        MainCont.getStyle().setBgTransparency(255);

        addAll(MainCont);
        /**
         * ********************************************************************************
         */
        /**
         * ****************************************************************************************
         */
        /**
         * ******************************
         */

    }

}
