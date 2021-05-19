/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.ArrayList;
import Entity.Categorie;
import WebService.categorieServices;

/**
 *
 * @author WIKI
 */
public class CategorieList extends Form{

    Form currentB;
   static Categorie b=new Categorie();
   ArrayList<Categorie> list2=new ArrayList<>();
    public CategorieList(Form previous) {
             
           currentB=this;
             setTitle("All Categories");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
         getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new HomeAdmin(previous).showBack());

  /***********************************************************************************/
  
         ArrayList<Categorie> liste=categorieServices.getInstance().getCategorieAll();

  
         Style s3=getAllStyles();
   Container listss = new Container(BoxLayout.y());
        Container lists = new Container(BoxLayout.y());
           for(Categorie c1 : liste) {
            listss.add(createCoursContainer(c1));
        }
      /*************************/
            Style st=lists.getAllStyles();
        st.setMargin(Component.BOTTOM,900);
        Tabs t = new Tabs();
        Style s = UIManager.getInstance().getComponentStyle("Tab");
         t.setUIID("Tab");        
        t.addTab("ma liste", listss);
        t.setScrollableY(true);
         add(t);
    }
     private Container createCoursContainer(Categorie categories) {
           
            Button bt=new Button("Update");
            Style butStylebn=bt.getAllStyles();
        
            Button btm=new Button("affecter");
           
            
           
            //butStylebnm.setFgColor(0x000000);   
              butStylebn.setBgTransparency(255);
        butStylebn.setMarginUnit(Style.UNIT_TYPE_DIPS);
        butStylebn.setMargin(Component.BOTTOM, 50);
        butStylebn.setMargin(Component.TOP,30);
        butStylebn.setMargin(Component.LEFT,2);
        
            Button bt1=new Button("X");
          Style butStylebnm=bt1.getAllStyles();
            butStylebnm.setBgTransparency(255);
            butStylebnm.setMarginUnit(Style.UNIT_TYPE_DIPS);
            butStylebnm.setMargin(Component.BOTTOM, 50);
            butStylebnm.setMargin(Component.TOP,30);
            butStylebnm.setMargin(Component.LEFT,2);
            butStylebnm.setMargin(Component.LEFT,0);
           
            //butStylebbn.setFgColor(0x000000);
          
            Label catalogue_id1 = new Label("catalogue_id:");
            Label nom1 = new Label("nom:");
            Label description1 = new Label("DESCRIPTION:");
            Label image1 = new Label("IMAGE:");
            
     
//SpanLabel sp = new SpanLabel(); pour le retour a la ligne
            SpanLabel catalogue_id =  new SpanLabel("");
            SpanLabel nom =  new SpanLabel("");
            SpanLabel description =  new SpanLabel("");
            SpanLabel image =  new SpanLabel("");
            

           

           Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
           cnt.getStyle().setBgColor(0xFFFFFF);
        cnt.getStyle().setBgTransparency(255);
        catalogue_id.getAllStyles().setFgColor(0x000000);
       
        nom.getAllStyles().setFgColor(0x000000);
        description.getAllStyles().setFgColor(0x000000);
        image.getAllStyles().setFgColor(0x000000);
       
        catalogue_id1.getAllStyles().setFgColor(0x000000);
        nom1.getAllStyles().setFgColor(0x000000);
       description1.getAllStyles().setFgColor(0x000000);
       image1.getAllStyles().setFgColor(0x000000);
        
        
        
            cnt.add(catalogue_id1);
            cnt.add(catalogue_id);
            cnt.add(nom1);
            cnt.add(nom);
            cnt.add(description1);
            cnt.add(description);
            cnt.add(image1);
            cnt.add(image);
            

        Style st=cnt.getAllStyles();
        st.setMargin(Component.BOTTOM,2);


            catalogue_id.setText(""+categories.getCatalogue().getId());    
            nom.setText("" + categories.getNom());
            description.setText("" + categories.getDescription());
     
                   image.setText(categories.getImage());
   

           Container c4=BoxLayout.encloseX(bt,bt1);
            bt1.addActionListener(e->{ 
                list2=categorieServices.getInstance().DeleteCategorieAction(categories.getId());
                list2=categorieServices.getInstance().getCategorieAll();
                Dialog.show("Success", "Voulez-vous supprimer ce Categorie?", new Command("OK"));

                cnt.remove();
                c4.remove();
    });

    bt.addActionListener(e->{ b=categories;new CategorieUpdate(currentB).show(); });
       
            /***************************/
     
          
                   return BorderLayout.center(cnt).add(BorderLayout.EAST,c4);
                   
                   
        }


  

    
}
