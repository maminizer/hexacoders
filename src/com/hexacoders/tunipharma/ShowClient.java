/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import Entity.User;
import Entity.Commande;
import Entity.Commentaire;
import WebService.ServiceUser;
import WebService.ServiceCommande;
import WebService.ServiceCommentaire;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.hexacoders.tunipharma.SideMenuBaseForm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import com.esprit.formulaire.StatsForm;

/**
 *
 * @author Lenovo
 */
class ShowClient extends SideMenuBaseForm {

    Form currentC;
    public static ArrayList<Commentaire> listeC;

    public ShowClient(User u, Resources res) {

        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        setTitle("Ajouter Commande");
        tb.setTitleCentered(true);
        //code goes here 
        //user also fetched succesfully in here
        Container c = new Container(new FlowLayout(Container.CENTER));
        c.getAllStyles().setMargin(Component.TOP, 70);
        add(c);
        Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);

        Label image = new Label();
        try {
            Image img = Image.createImage(HomeClient.b.getImage());
            image.setIcon(img);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Label price = new Label("");
        price.setText("" + HomeClient.b.getPrice() + " TND");
        price.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        price.getAllStyles().setFgColor(0x000000);

        Label title = new Label("");
        title.setText(HomeClient.b.getTitle());
        title.getAllStyles().setFgColor(0x000000);

        Label envente = new Label("");
        if (HomeClient.b.getEnVente()) {
            envente.setText("In Stock");
        } else {
            envente.setText("Out of Stock");
        }

        Label titre = new Label("Detail :");
        // TextArea titre1  =new TextArea(HomeClient.b.getDescription());
        SpanLabel titre1 = new SpanLabel();
        titre1.setText(HomeClient.b.getDescription());
        titre.getAllStyles().setMargin(Component.BOTTOM, 120);

        titre1.getAllStyles().setMargin(Component.BOTTOM, 7);

        Style butStyle = titre1.getAllStyles();
        butStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        titre.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        titre1.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        titre1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
        titre.getAllStyles().setFgColor(0xFFFFFF);
        titre1.getAllStyles().setFgColor(0xFFFFFF);

        Container cnt3 = new Container(new FlowLayout(Container.CENTER));

        Container c1 = BoxLayout.encloseY(titre);

        Container c3 = BoxLayout.encloseY(titre1);

        Container c4 = BoxLayout.encloseX(c1, c3);

        c.getAllStyles().setMargin(Component.BOTTOM, 150);
        c4.getAllStyles().setMargin(Component.TOP, 10);
        c4.getAllStyles().setMargin(Component.LEFT, 50);
        c4.getAllStyles().setMargin(Component.RIGHT, 50);
        c4.getStyle().setBgColor(0xff6600);
        c4.getStyle().setBgTransparency(250);

        Container MainCont = BoxLayout.encloseY(image, price, title, envente, c4);
        MainCont.getAllStyles().setMargin(Component.TOP, 1);
        MainCont.getAllStyles().setMargin(Component.LEFT, 1);
        MainCont.getAllStyles().setMargin(Component.RIGHT, 1);
        MainCont.getAllStyles().setMargin(Component.BOTTOM, 1);
        MainCont.getStyle().setBgColor(0xFFFFFF);
        MainCont.getStyle().setBgTransparency(255);

        listeC = ServiceCommentaire.getInstance().getAllCommentsAction(HomeClient.b.getId());
        Style s3 = getAllStyles();
        Container listss = new Container(BoxLayout.y());
        Container lists = new Container(BoxLayout.y());
        for (Commentaire comm : listeC) {
            listss.add(createCoursContainer(comm));
        }

        Style st = lists.getAllStyles();
        st.setMargin(Component.BOTTOM, 900);
        st.setMargin(Component.TOP, 50);
        Tabs t = new Tabs();
        Style s = UIManager.getInstance().getComponentStyle("Tab");
        t.setUIID("Tab");
        t.addTab("Comments", listss);
        t.setScrollableY(true);

        Label comment = new Label("Leave a comment:");
        TextField comment1 = new TextField("", "Type here...");
        Button btnComment = new Button("New Comment");
        btnComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((comment1.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        String comment12 = comment1.getText();
                         System.out.println("user id before submitting comment " + u.getId());
                        Commentaire c = new Commentaire(0,HomeClient.b.getId(), u.getId(), comment12);
                            
                        if (ServiceCommentaire.getInstance().addCommentaire(c, HomeClient.b.getId())) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "id product must be a number", new Command("OK"));
                    }
                    new ShowClient(u, res).show();
                }

            }
        });
        Container F1 = BoxLayout.encloseX(comment, comment1);
        Container FC = BoxLayout.encloseY(F1, btnComment);

        addAll(MainCont, t, FC);

        setupSideMenu(u, res);
    }

    protected void showOtherForm(User user, Resources res) {
        // new StatsForm(user,res).show();
    }

    private Container createCoursContainer(Commentaire commentaires) {

        Button bt = new Button("Like");
        Style butStylebn = bt.getAllStyles();

        //butStylebnm.setFgColor(0x000000);   
        butStylebn.setBgTransparency(255);
        butStylebn.setMarginUnit(Style.UNIT_TYPE_DIPS);
        butStylebn.setMargin(Component.BOTTOM, 50);
        butStylebn.setMargin(Component.TOP, 30);
        butStylebn.setMargin(Component.LEFT, 2);

        Label titre1 = new Label("Contenu:");

//SpanLabel sp = new SpanLabel(); pour le retour a la ligne
        SpanLabel titre = new SpanLabel("");

        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.getStyle().setBgColor(0xFFFFFF);
        cnt.getStyle().setBgTransparency(255);
        titre.getAllStyles().setFgColor(0x000000);

        titre1.getAllStyles().setFgColor(0xff6600);

        cnt.add(titre1);
        cnt.add(titre);

        cnt.add(bt);

        Style st = cnt.getAllStyles();
        st.setMargin(Component.BOTTOM, 2);

        titre.setText(commentaires.getContenu());

        Container c4 = BoxLayout.encloseX();
        //btm.addActionListener(e->{ b=products ;});
        //pur le bouton like:
//  bt.addActionListener(e->{ b=commentaire;new ShowClient(currentB).show(); });

        /**
         * ************************
         */
        return BorderLayout.center(cnt).add(BorderLayout.EAST, c4);

    }

}
