/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import Entity.Catalogue;
import WebService.catalogueServices;

/**
 *
 * @author WIKI
 */
public class CatalogueAdd extends Form {

    public CatalogueAdd(Form previous) {
        this.setTitle("Add catalogue");
        this.setLayout(BoxLayout.y());
        TextField tfnom = new TextField("", "insert nom");
        TextField tfdescription = new TextField("", "insert description");
        Button submitBtn = new Button("submit");
        submitBtn.addActionListener((evt) -> {

            if (tfnom.getText().length() == 0 || tfdescription.getText().length() == 0) {
                Dialog.show("show", "textfields cannot be empty", null, "ok");
            } else {
                try {
                    Catalogue catalogue = new Catalogue();
                    catalogue.setNom(tfnom.getText());
                    catalogue.setDescription(tfdescription.getText());

                    if (catalogueServices.getInstance().addCatalogue(catalogue)) {
                        Dialog.show("Succes", "added success", null, "ok");
                    }
                } catch (NumberFormatException e) {

                    Dialog.show("Succes", "added success", null, "ok");

                }
            }

        });

        this.addAll(tfnom, tfdescription, submitBtn);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev -> new HomeAdmin(previous).showBack());
    }

}
