/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import Entity.Product;
import Entity.User;
import WebService.ServiceProduct;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author yossr
 */
public class HomeClientSearched extends SideMenuBaseForm {

    Resources res;
    Form currentC;
    static Product b = new Product();
    ArrayList<Product> list2 = new ArrayList<>();
    public static boolean boolListe = false;
    public static ArrayList<Product> liste;
    //static int priceminA ;
    //   static int  pricemaxA ;
    //   static boolean  enventeA;

    public HomeClientSearched(User u  , Resources res) {

        super(BoxLayout.y());
        currentC = this;
        Toolbar tb = getToolbar();
        setTitle("Ajouter Commande");
        tb.setTitleCentered(true);
        getToolbar().addCommandToOverflowMenu("Exit",
                null, ev -> {
                    Display.getInstance().exitApplication();
                });
        getToolbar().addMaterialCommandToLeftBar("back",
                FontImage.MATERIAL_BACKUP, ev -> {
                    new HomeClient(u, res).show();
                });
        //insert code of listing all products 
//SpanLabel sp = new SpanLabel();
// sp.setText(ServiceProduct.getInstance().getAllProductsAction().toString());
        //add(sp);

        /**
         * *********Filtre Bar*************
         */
        Label enventef = new Label("Availabiliry :");
        TextArea enventef1 = new TextArea("");
        Container F1 = BoxLayout.encloseX(enventef, enventef1);
        Label pricef = new Label("Price Range :");
        TextArea pricef1 = new TextArea("");
        TextArea pricef2 = new TextArea("");
        //  pricemin = (int) Float.parseFloat(pricef1.getText());
        //         pricemax = (int) Float.parseFloat(pricef2.getText());
        //          envente = Boolean.parseBoolean(enventef1.getText());
        Container F2 = BoxLayout.encloseX(pricef, pricef1, pricef2);
        //liste=ServiceProduct.getInstance().getAllProductsAction(); 
        Button btnFiltrer = new Button("Filtrer");
        /**
         * *******************************************************************************************************
         */
        /**
         * ***************************************Rechercher****************************************************************
         */
        TextArea recherche = new TextArea("Type your search here");
        Button btnRechercher = new Button("Rechercher");

        /**
         * *******************************************************************************************************
         */
        Container RechercheCont = BoxLayout.encloseY(recherche, btnRechercher);
        RechercheCont.getStyle().setBgColor(0xff6600);
        RechercheCont.getStyle().setBgTransparency(250);
        Container FiltreCont = BoxLayout.encloseY(F1, F2, btnFiltrer);
        FiltreCont.getStyle().setBgColor(0xff6600);
        FiltreCont.getStyle().setBgTransparency(250);
        /**
         * ********************************************************************************
         */
        liste = ServiceProduct.getInstance().getSearchedProductsAction(HomeClient.rechercher);

        Style s3 = getAllStyles();
        Container listss = new Container(BoxLayout.y());
        Container lists = new Container(BoxLayout.y());
        for (Product c1 : liste) {
            listss.add(createCoursContainer(c1,u));
        }
        /**
         * **********************
         */
        Style st = lists.getAllStyles();
        st.setMargin(Component.BOTTOM, 900);
        st.setMargin(Component.TOP, 50);
        Tabs t = new Tabs();
        Style s = UIManager.getInstance().getComponentStyle("Tab");
        t.setUIID("Tab");
        t.addTab("ma liste", listss);
        t.setScrollableY(true);
        Container mainCont = BoxLayout.encloseY(FiltreCont, RechercheCont, t);
        //add(FiltreCont,t);
        add(mainCont);
    }

    private Container createCoursContainer(Product products, User u) {

        Button bt = new Button("Show");
        Style butStylebn = bt.getAllStyles();

        //butStylebnm.setFgColor(0x000000);   
        butStylebn.setBgTransparency(255);
        butStylebn.setMarginUnit(Style.UNIT_TYPE_DIPS);
        butStylebn.setMargin(Component.BOTTOM, 50);
        butStylebn.setMargin(Component.TOP, 30);
        butStylebn.setMargin(Component.LEFT, 2);

        Label image = new Label();

        Label titre1 = new Label("TITLE:");
        Label image1 = new Label("IMAGE:");
        Label price1 = new Label("PRICE:");
        Label envente1 = new Label("AVAILABILITY:");
        Label quantity1 = new Label("QUANTITY:");
        Label description1 = new Label("DESCRIPTION:");

//SpanLabel sp = new SpanLabel(); pour le retour a la ligne
        SpanLabel titre = new SpanLabel("");
        SpanLabel price = new SpanLabel("");
        SpanLabel envente = new SpanLabel("");
        SpanLabel quantity = new SpanLabel("");
        SpanLabel description = new SpanLabel("");

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.getStyle().setBgColor(0xFFFFFF);
        cnt.getStyle().setBgTransparency(255);
        titre.getAllStyles().setFgColor(0x000000);
        image.getAllStyles().setFgColor(0x000000);
        price.getAllStyles().setFgColor(0x000000);
        envente.getAllStyles().setFgColor(0x000000);
        quantity.getAllStyles().setFgColor(0x000000);
        description.getAllStyles().setFgColor(0x000000);
        titre1.getAllStyles().setFgColor(0xff6600);
        image1.getAllStyles().setFgColor(0xff6600);
        price1.getAllStyles().setFgColor(0xff6600);
        envente1.getAllStyles().setFgColor(0xff6600);
        quantity1.getAllStyles().setFgColor(0xff6600);
        description1.getAllStyles().setFgColor(0xff6600);

        cnt.add(titre1);
        cnt.add(titre);
        cnt.add(price1);
        cnt.add(price);
        cnt.add(envente1);
        cnt.add(envente);
        cnt.add(quantity1);
        cnt.add(quantity);
        cnt.add(description1);
        cnt.add(description);
        cnt.add(bt);

        Style st = cnt.getAllStyles();
        st.setMargin(Component.BOTTOM, 2);

        titre.setText(products.getTitle());
        price.setText("" + products.getPrice());
        envente.setText("" + products.getEnVente());
        quantity.setText("" + products.getQuantity());
        description.setText(products.getDescription());
        //image.setText(products.getImage());
        try {
            Image img = Image.createImage(products.getImage());

            int displayHeight = Display.getInstance().getDisplayHeight();
            Image scaledPhotoImage = img.scaled(-1, displayHeight / 6);
            ScaleImageLabel scaleImageLabel = new ScaleImageLabel(scaledPhotoImage);

            Style ImgStyle = image.getAllStyles();

            //butStylebnm.setFgColor(0x000000);   
            ImgStyle.setBgTransparency(255);
            ImgStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            ImgStyle.setMargin(Component.BOTTOM, 50);
            ImgStyle.setMargin(Component.TOP, 10);
            ImgStyle.setMargin(Component.LEFT, 2);

            image.setIcon(scaledPhotoImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Container c4 = BoxLayout.encloseX(image);

        //btm.addActionListener(e->{ b=products ;});
        bt.addActionListener(e -> {
            b = products;
            new ShowClient(u , res).show();
        });

        /**
         * ************************
         */
        return BorderLayout.center(cnt).add(BorderLayout.EAST, c4);

    }

    @Override
    protected void showOtherForm(User u, Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
