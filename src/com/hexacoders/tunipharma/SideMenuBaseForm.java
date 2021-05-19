/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

//import Entity.DailyScrum;
//import com.esprit.formulaire.Meeting.Meetings;
import Entity.User;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
//import com.esprit.formulaire.Claims.Claims;
//import com.esprit.formulaire.DailyScrum.Dailys;
//import com.esprit.formulaire.Issue.Issues;
//import com.esprit.formulaire.Project.Projects;
//import com.esprit.formulaire.Release.Releases;

/**
 *
 * @author Lenovo
 */
public abstract class SideMenuBaseForm extends Form {

    Form current;

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public void setupSideMenu(User u, Resources res) {
//        Image profilePic = res.getImage("" + u.getUserPhoto());
//        Image mask = res.getImage("round-mask.png");
//        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
//        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(u.getEmail(), "SideMenuTitle");
//        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Shop", FontImage.MATERIAL_SHOP, e -> new HomeClient(u, res).show());
        getToolbar().addMaterialCommandToSideMenu("  Commandes", FontImage.MATERIAL_REPORT, e -> new CommandForm(u, res).show());
        getToolbar().addMaterialCommandToSideMenu("  Livreurs", FontImage.MATERIAL_PEOPLE, e -> new ListeLivreur(u, res).show());
        getToolbar().addMaterialCommandToSideMenu("  Profil", FontImage.MATERIAL_ACCESS_TIME, e -> new UserInfo(u, res).show());
        getToolbar().addMaterialCommandToSideMenu("  Admin", FontImage.MATERIAL_ACCESS_TIME, e -> new HomeAdmin(current).show());
        getToolbar().addMaterialCommandToSideMenu("  Déconnexion", FontImage.MATERIAL_EXIT_TO_APP, e -> new Login(res).show());

    }

    protected abstract void showOtherForm(User u, Resources res);

}
