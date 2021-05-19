/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.util.StringUtil;
import WebService.ServiceProduct;

/**
 *
 * @author yossr
 */
public class ModifierProd extends Form {

    Form currentC;

    public ModifierProd(Form previous) {
        currentC = this;
        setTitle("Update Product");
        setLayout(BoxLayout.y());
        getToolbar().addCommandToOverflowMenu("Exit",
                null, ev -> {
                    Display.getInstance().exitApplication();
                });
        getToolbar().addMaterialCommandToLeftBar("back",
                FontImage.MATERIAL_BACKUP, ev -> {
                    new ListProd(currentC).show();
                });

        Container c = new Container(new FlowLayout(Container.CENTER));
        //c.add(a);
        c.getAllStyles().setMargin(Component.TOP, 70);
        add(c);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
        //insert code here code de modifer
        /**
         * **************************Title******************************************************
         */

        Label title = new Label("      Produit :");
        Label title1 = new Label("               " + ListProd.b.getTitle());
        title.getAllStyles().setFgColor(0xff6600);
        title1.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        title1.getAllStyles().setFgColor(0xff6600);
        /**
         * **************************************************************************************
         */
        /**
         * ******************************En Vente**************************************************
         */

        Label envente = new Label("Availability :");
        TextArea envente1 = new TextArea("" + ListProd.b.getEnVente());
        envente.getAllStyles().setMargin(Component.BOTTOM, 120);

        envente1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle = envente1.getAllStyles();
        butStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        envente.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        envente1.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        envente1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        envente.getAllStyles().setFgColor(0xFFFFFF);
        envente1.getAllStyles().setFgColor(0x000000);
        /**
         * ********************************************************************************
         */
        /**
         * **************************Quantity******************************************************
         */

        Label stock = new Label("Stock :");
        TextArea stock1 = new TextArea("" + ListProd.b.getQuantity());
        stock.getAllStyles().setMargin(Component.BOTTOM, 120);

        stock1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle1 = stock1.getAllStyles();
        butStyle1.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        stock.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        stock.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        stock1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        stock.getAllStyles().setFgColor(0xFFFFFF);
        stock1.getAllStyles().setFgColor(0x000000);
        /**
         * ********************************************************************************
         */

        /**
         * **************************Price******************************************************
         */
        Label price = new Label("Price :");
        TextArea price1 = new TextArea("" + ListProd.b.getPrice());
        price.getAllStyles().setMargin(Component.BOTTOM, 120);

        price1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle2 = price1.getAllStyles();
        butStyle2.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        price.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        price.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        price1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        price.getAllStyles().setFgColor(0xFFFFFF);
        price1.getAllStyles().setFgColor(0x000000);
        /**
         * ********************************************************************************
         */
        /**
         * ***********************************************************************************************
         */
        Button valider = new Button("modifier");

        /**
         * ***********************************************************************************************
         */
        Container cnt3 = new Container(new FlowLayout(Container.CENTER));
        /**
         * ********************************************************************************
         */
        Container c1 = BoxLayout.encloseY(envente, stock, price);

        Container c3 = BoxLayout.encloseY(envente1, stock1, price1);

        setScrollableY(false);

        cnt3.add(valider);
        Container c4 = BoxLayout.encloseX(c1, c3);

        c.getAllStyles().setMargin(Component.BOTTOM, 150);
        c4.getAllStyles().setMargin(Component.TOP, 10);
        c4.getAllStyles().setMargin(Component.LEFT, 50);
        c4.getAllStyles().setMargin(Component.RIGHT, 50);
        c4.getStyle().setBgColor(0xff6600);
        c4.getStyle().setBgTransparency(250);

        valider.addActionListener(e -> {
            float price2 = Float.parseFloat(price1.getText());
            boolean envente2 = Boolean.parseBoolean(envente1.getText());
            int quantite2 = Integer.parseInt(stock1.getText());
            ListProd.b.setEnVente(envente2);
            ListProd.b.setQuantity(quantite2);
            ListProd.b.setPrice(price2);

            if (ServiceProduct.getInstance().UpdateProductAction(ListProd.b)) {
                Dialog.show("Success", "evenement modifié ", new Command("OK"));

            } else {
                Dialog.show("ERROR", " Server error", new Command("OK"));
            }

        });

        Container MainCont = BoxLayout.encloseY(title, title1, c4, cnt3);
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
