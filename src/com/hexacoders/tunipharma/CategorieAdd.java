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
import java.util.Map;
import Entity.Catalogue;
import Entity.Categorie;
import WebService.catalogueServices;
import WebService.categorieServices;

/**
 *
 * @author WIKI
 */
public class CategorieAdd extends Form{
   // public ComboBox<String>  catal;
    ArrayList<Object> list2=new ArrayList<>();
  String path;
   // ArrayList<Catalogue> liste=;
  //
  ComboBox<Integer> cata1;
    public CategorieAdd(Form previous) {
                setTitle("Add Categorie");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
        //insert code here
        this.cata1 = new ComboBox<Integer>();
        list2=new ArrayList<>();
      for(Catalogue listes:catalogueServices.getInstance().getCatalogueAll()){
   //   c+=listes.getNom()+"\n";
   
     // list2.add(listes.getNom());
         this.cata1.addItem(listes.getId());
      }
     
      
     this.cata1.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
        TextField tfTitle = new TextField("","nom");
        TextField tfImage = new TextField("", "image");
        Button btCapture = new Button("upload");
        Label labImage = new Label();
      
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
                if ((tfTitle.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        
                        
                        Catalogue catalog=new Catalogue(Integer.parseInt(cata1.getSelectedItem().toString()));
                        System.out.println("dddddddddddd :"+catalog);
                        String nom = tfTitle.getText();
                        String image = path;
                      //  float price = Float.parseFloat(tfPrice.getText());
                     //   boolean envente = Boolean.parseBoolean(tfenvente.getText());
                      //  int quantite = Integer.parseInt(tfquantite.getText());
                        String description = tfdesc.getText();
                        Categorie p = new Categorie(catalog,nom,description,image);
                        if(categorieServices.getInstance().addCategorie(p))
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
        
        addAll(cata1,tfTitle,tfImage,tfdesc,btnValider,btCapture,labImage);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
  //    public String getshow(){
        
    //    }
   //   liste.forEach(c->c.getNom())
}
