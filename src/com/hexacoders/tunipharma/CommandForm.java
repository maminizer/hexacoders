/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import Entity.User;
import Entity.Commande;
import WebService.ServiceUser;
import WebService.ServiceCommande;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.hexacoders.tunipharma.SideMenuBaseForm;
import java.util.HashMap;
import java.util.Map;
//import com.esprit.formulaire.StatsForm;

/**
 *
 * @author Lenovo
 */
class CommandForm extends SideMenuBaseForm {

    Form current;
    String rowindex;

    public CommandForm(User u, Resources res) {

        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        setTitle("Ajouter Commande");
        tb.setTitleCentered(true);
        Label left = new Label("Procéder à séléctionner le produit et la quantité désirée");
        left.setTextPosition(Component.LEFT);
        add(left);
        Label left2 = new Label("pour passer la commande");
        left2.setTextPosition(Component.LEFT);
        add(left2);

        // code goes here 
        ComboBox<Map<String, Object>> combo = new ComboBox<>(
                createListEntry("Gel", "69.2"),
                createListEntry("Creme", "22.5")
        );

        combo.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rowindex = combo.getSelectedItem().get("Line1").toString();
                System.out.println(rowindex);
            }
        });
        add(combo);

        TextField quantity = new TextField("", "Quantité", 20, TextArea.NUMERIC);
        quantity.setUIID("TextFieldBlack");
        addStringValue("Quantité", quantity);

        Button addcommand = new Button("Commander");
        addcommand.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (quantity.getText().length() == 0 || rowindex == "") {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    return;
                } else {
                    try {
                        Toolbar.setGlobalToolbar(false);
                        Commande c = new Commande(rowindex, u.getId(), Integer.parseInt(quantity.getText()));
                        Toolbar.setGlobalToolbar(true);
                        if (ServiceCommande.getInstance().addCommande(c)) {
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
        addComponent(addcommand);
        setupSideMenu(u, res);
    }

    protected void showOtherForm(User user, Resources res) {
        // new StatsForm(user,res).show();
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private Map<String, Object> createListEntry(String name, String date) {
        Map<String, Object> entry = new HashMap<>();
        entry.put("Line1", name);
        entry.put("Line2", date);
        return entry;
    }

}
