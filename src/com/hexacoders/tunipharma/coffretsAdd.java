/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.capture.Capture;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import java.io.IOException;
import java.util.ArrayList;
import Entity.Catalogue;
import Entity.Categorie;
import Entity.Coffret;
import WebService.catalogueServices;
import WebService.categorieServices;
import WebService.coffretsServices;
import com.hexacoders.tunipharma.CategorieList;

/**
 *
 * @author WIKI
 */
public class coffretsAdd extends Form{

   ArrayList<Object> list2=new ArrayList<>();
  String path;
   // ArrayList<Catalogue> liste=;
  //
  //ComboBox<Integer> cata1;
    public coffretsAdd(Form previous) {
                setTitle("Add Categorie");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
        //insert code here
    //    this.cata1 = new ComboBox<Integer>();
     //   list2=new ArrayList<>();
      //for(Catalogue listes:catalogueServices.getInstance().getCatalogueAll()){
   //   c+=listes.getNom()+"\n";
   
     // list2.add(listes.getNom());
    //     this.cata1.addItem(listes.getId());
    //  }
     
      
  //   this.cata1.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
        TextField tfNom = new TextField("","nom");
        TextField tfImage = new TextField("", "image");
        Button btCapture = new Button("upload");
        Label labImage = new Label();
         TextField tfPrix = new TextField("", "prix");
            TextField tfOffre = new TextField("", "offre");
        btCapture.addActionListener((evt)->{
          path =  Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
          if(path != null){
              try {
                  Image img =  Image.createImage(path);
              } catch (IOException ex) {
                 ex.printStackTrace();
              }
          }
        });
     
      
        TextField tfdesc = new TextField("", "description");
         Button btnValider = new Button("Add");
                btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfNom.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        
                        
                      //  Catalogue catalog=new Catalogue(cata1.getSelectedItem());
                        String nom = tfNom.getText();
                        String image = path;
                      //  float price = Float.parseFloat(tfPrice.getText());
                     //   boolean envente = Boolean.parseBoolean(tfenvente.getText());
                      //  int quantite = Integer.parseInt(tfquantite.getText());
                        String description = tfdesc.getText();
                        Double prix=Double.parseDouble(tfPrix.getText());
                        Double offre=Double.parseDouble(tfOffre.getText());
                        Coffret p = new Coffret(nom,description,image,prix,offre);
                        if(coffretsServices.getInstance().addCoffrets(p))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "price must be a number", new Command("OK"));
                    }
                    new CategorieList(previous).show();
                }
                
                
            }
        });
        
        addAll(tfNom,tfImage,tfdesc,tfPrix,tfOffre,btnValider,btCapture,labImage);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
