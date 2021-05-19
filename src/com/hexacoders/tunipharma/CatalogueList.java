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
import Entity.Catalogue;
import WebService.catalogueServices;

/**
 *
 * @author WIKI
 */
public class CatalogueList extends Form{
  static Catalogue b=new Catalogue();
   Form currentB;

    ArrayList<Catalogue> list2=new ArrayList<>();
    public CatalogueList(Form previous) {
         currentB=this;
             setTitle("All Catalogues");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
        // getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack() );
           getToolbar().addMaterialCommandToLeftBar("back", 
                FontImage.MATERIAL_BACKUP, ev->{new HomeAdmin(previous).show();});
          ArrayList<Catalogue> liste=catalogueServices.getInstance().getCatalogueAll();
          
           Style s3=getAllStyles();
        Container listss = new Container(BoxLayout.y());
        Container lists = new Container(BoxLayout.y());
           for(Catalogue c1 : liste) {
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
        //ancien code codenameOne
       /* this.setTitle("catalogue List");
        this.setLayout(BoxLayout.y());
        
        SpanLabel tasksListSP = new SpanLabel();
        tasksListSP.setText(catalogueServices.getInstance().getCatalogueAll().toString());
        
    this.add(tasksListSP);
        
        
        //button
         Button catalogue=new Button("AddCatalogue");
        
        catalogue.addActionListener((evt) -> 
    new CatalogueAdd().show() );
        this.setTitle("Listcatalogue");
        this.add(catalogue);
         getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, ev->new Home().showBack());*/
         
    }

    private Component createCoursContainer(Catalogue c1) {
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
          
            Label nom1 = new Label("NOM:");
          
            Label description1 = new Label("DESCRIPTION:");
     
//SpanLabel sp = new SpanLabel(); pour le retour a la ligne
            SpanLabel nom =  new SpanLabel("");
            SpanLabel description =  new SpanLabel("");


           

           Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
           cnt.getStyle().setBgColor(0xFFFFFF);
        cnt.getStyle().setBgTransparency(255);
        nom.getAllStyles().setFgColor(0x000000);
        description.getAllStyles().setFgColor(0x000000);
         nom1.getAllStyles().setFgColor(0x000000);
        description1.getAllStyles().setFgColor(0x000000);
        
        
            cnt.add(nom1);
            cnt.add(nom);
        
            cnt.add(description1);
            cnt.add(description);
            

        Style st=cnt.getAllStyles();
        st.setMargin(Component.BOTTOM,2);


            nom.setText(""+c1.getNom());    
        description.setText(c1.getDescription());
           
                 
   

           Container c4=BoxLayout.encloseX(bt,bt1);
            bt1.addActionListener(e->{ 
                list2=catalogueServices.getInstance().DeleteCatalogueAction(c1.getId());
                list2=catalogueServices.getInstance().getCatalogueAll();
                Dialog.show("Success", "Voulez-vous supprimer ce catalogue?", new Command("OK"));

                cnt.remove();
                c4.remove();
    });
    //btm.addActionListener(e->{ b=products ;});
    bt.addActionListener(e->{ b=c1;new CatalogueUpdate(currentB).show();
    });
       
            /***************************/
     
          
                   return BorderLayout.center(cnt).add(BorderLayout.EAST,c4);

        
        
        
        
        
    }
    
    
}
