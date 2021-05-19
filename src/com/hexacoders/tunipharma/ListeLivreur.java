/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import Entity.Livreur;
import Entity.User;
import WebService.LivreurService;
import WebService.ServiceUser;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.hexacoders.tunipharma.SideMenuBaseForm;
import java.util.ArrayList;
import static java.util.Collections.list;
//import com.esprit.formulaire.StatsForm;

/**
 *
 * @author Lenovo
 */
class ListeLivreur extends SideMenuBaseForm {

    Form current;
    ArrayList<Livreur> list;

    public ListeLivreur(User u, Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        setTitle("Liste des Livreurs");
        tb.setTitleCentered(true);
        // code goes here 

        getToolbar().addCommandToOverflowMenu("Back",
                null, ev -> {
                    new HomeClient(u, res).show();
                });
        list = new ArrayList<>();
        list = LivreurService.getInstance().getAllLivreur();
        Container c0 = new Container(BoxLayout.y());

        for (Livreur liv : list) {
            Label l = new Label(liv.getNom());
            c0.add(l);
            l.addPointerPressedListener(e -> {
                Form f2 = new Form("Détails Livreur", BoxLayout.y());
                SpanLabel sp = new SpanLabel("Détails Livreur");
                sp.setWidth(20);
                SpanLabel spl = new SpanLabel("Nom de Livreur : " + "  " + liv.getNom());
                SpanLabel spl2 = new SpanLabel("Prenom : " + "  " + liv.getPrenom());
                SpanLabel spl3 = new SpanLabel("Email : " + "  " + liv.getEmail());
                SpanLabel spl4 = new SpanLabel("CIN : " + "  " + liv.getCin());
                SpanLabel spl5 = new SpanLabel("Num Tel : " + "  " + liv.getNumtlf());
                SpanLabel spl6 = new SpanLabel("Dispo : " + "  " + liv.getDisponibilite());

                Button modif = new Button("Modifier");
                Button sup = new Button("Supprimer");
                sup.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
                sup.getAllStyles().setBgColor(BOTTOM);

                sup.addActionListener((evt) -> {

                    LivreurService serL = new LivreurService();
                    serL.SupprimerLivreur(liv.getId());

                    Dialog.show("Alert", "supprimer ce Livreur?", new Command("OK"));
                    Dialog.show("Success", "Livreur supprimé avec succès", new Command("OK"));
                    new ListeLivreur(u, res).show();
                });

                modif.addActionListener((evt) -> {
                    Form f3 = new Form();
                    Container Cmodif = new Container(BoxLayout.y());
                    Container Cmodif1 = new Container(BoxLayout.x());
                    Container Cmodif2 = new Container(BoxLayout.x());
                    Container Cmodif3 = new Container(BoxLayout.x());
                    Container Cmodif4 = new Container(BoxLayout.x());

                    TextField nomm = new TextField("", "nom");
                    Label ln = new Label("Nom:");

                    TextField prenom = new TextField("", "Prenom");
                    Label ln1 = new Label("Prenom:");

                    TextField Cin = new TextField("", "Cin");
                    Label ln2 = new Label("Cin:");

                    TextField numtlf = new TextField("", "Num Tel");
                    Label ln3 = new Label("Num Tel:");

                    Button valid = new Button("Enregistrer");
                    nomm.setText(liv.getNom());
                    prenom.setText(liv.getPrenom());
                    Cin.setText(Integer.toString(liv.getCin()));
                    numtlf.setText(Integer.toString(liv.getNumtlf()));
                    Cmodif1.addAll(ln, nomm);
                    Cmodif2.addAll(ln1, prenom);
                    Cmodif3.addAll(ln2, numtlf);
                    Cmodif4.addAll(ln3, Cin);
                    Cmodif.addAll(Cmodif1, Cmodif2, Cmodif3, Cmodif4, valid);

                    f3.add(Cmodif);
                    f3.show();
                    valid.addActionListener((ev) -> {
                        LivreurService serL = new LivreurService();
                        serL.ModifierLivreur(liv.getId(), nomm.getText(), prenom.getText(), Integer.parseInt(numtlf.getText()), Integer.parseInt(Cin.getText()));
                        System.out.println("Livreur modifié");
                        Dialog.show("Success", "Livreur modifié avec succès", new Command("OK"));
                        new ListeLivreur(u, res).show();
                    });
                });

                Container c3 = new Container(BoxLayout.y());
                setScrollableY(true);
                c3.addAll(sp, spl, spl2, spl3, spl4, spl5, spl6, sup, modif);
                addAll(c3);

            });
            setLeadComponent(c0);

        };
        add(c0);
        setupSideMenu(u, res);
    }

    protected void showOtherForm(User user, Resources res) {
        // new StatsForm(user,res).show();
    }
}
