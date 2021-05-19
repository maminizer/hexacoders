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
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import Entity.Product;
import WebService.ServiceProduct;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author yossr
 */
public class ListProd extends Form{
    Form currentB;
   static Product b=new Product();
   ArrayList<Product> list2=new ArrayList<>();
    public ListProd(Form previous) {
             
           currentB=this;
             setTitle("All Products");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
         getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
  //insert code of listing all products 
//SpanLabel sp = new SpanLabel();
// sp.setText(ServiceProduct.getInstance().getAllProductsAction().toString());
  //add(sp);
  /***********************************************************************************/
  
         ArrayList<Product> liste=ServiceProduct.getInstance().getAllProductsAction();

  
         Style s3=getAllStyles();
   Container listss = new Container(BoxLayout.y());
        Container lists = new Container(BoxLayout.y());
           for(Product c1 : liste) {
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
     private Container createCoursContainer(Product products) {
           
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
          
            Label titre1 = new Label("TITLE:");
            Label image1 = new Label("IMAGE:");
            Label price1 = new Label("PRICE:");
            Label envente1 = new Label("AVAILABILITY:");
            Label quantity1 = new Label("QUANTITY:");
            Label description1 = new Label("DESCRIPTION:");
     
//SpanLabel sp = new SpanLabel(); pour le retour a la ligne
            SpanLabel titre =  new SpanLabel("");
            SpanLabel price =  new SpanLabel("");
            SpanLabel image =  new SpanLabel("");
            SpanLabel envente =  new SpanLabel("");
            SpanLabel quantity =  new SpanLabel("");
            SpanLabel description =  new SpanLabel("");

           

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
            cnt.add(image1);
            cnt.add(image);
            cnt.add(price1);
            cnt.add(price);
            cnt.add(envente1);
            cnt.add(envente);
            cnt.add(quantity1);
            cnt.add(quantity);
            cnt.add(description1);
            cnt.add(description);
            

        Style st=cnt.getAllStyles();
        st.setMargin(Component.BOTTOM,2);


            titre.setText(products.getTitle());    
            price.setText("" + products.getPrice());
            envente.setText("" + products.getEnVente());
            quantity.setText("" + products.getQuantity());
            description.setText(products.getDescription());
                   image.setText(products.getImage());
   

           Container c4=BoxLayout.encloseX(bt,bt1);
            bt1.addActionListener(e->{ 
                list2=ServiceProduct.getInstance().DeleteProductAction(products.getId());
                list2=ServiceProduct.getInstance().getAllProductsAction();
                Dialog.show("Success", "Voulez-vous supprimer ce Produit?", new Command("OK"));

                cnt.remove();
                c4.remove();
    });
    //btm.addActionListener(e->{ b=products ;});
    bt.addActionListener(e->{ b=products;new ModifierProd(currentB).show(); });
       
            /***************************/
     
          
                   return BorderLayout.center(cnt).add(BorderLayout.EAST,c4);
                   
                   
        }


  

  
    }
       
   
        

    
    

