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
import com.codename1.ui.Component;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Entity.Catalogue;
import WebService.catalogueServices;
import WebService.categorieServices;

/**
 *
 * @author WIKI
 */
public class CategorieUpdate extends Form{
String path;
  Form currentC;
      ArrayList<Object> list2=new ArrayList<>();
  
   // ArrayList<Catalogue> liste=;
  //
  ComboBox<String> cata1;
    public CategorieUpdate(Form previous) {
         currentC=this;
                        setTitle("Update Categorie");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
            getToolbar().addMaterialCommandToLeftBar("back", 
                FontImage.MATERIAL_BACKUP, ev->{new CategorieList(currentC).show();});

 
          Container c=new Container(new FlowLayout(Container.CENTER));
        //c.add(a);
        c.getAllStyles().setMargin(Component.TOP,70);
        add(c);
          Stroke borderStroke = new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1);
         //insert code here code de modifer
         /****************************Title*******************************************************/
               Label envente0  =new Label("catalogue_id :");
         TextArea envente10  =new TextArea(""+CategorieList.b.getCatalogue().getId());
        envente0.getAllStyles().setMargin(Component.BOTTOM,40);

        envente10.getAllStyles().setMargin(Component.BOTTOM,7);

        Style butStyle0=envente10.getAllStyles();
        butStyle0.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        envente0.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   envente10.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   envente10.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
   envente0.getAllStyles().setFgColor(0x000000);
envente10.getAllStyles().setFgColor(0x000000);
        /////update chackbo
        Label listCatalo  =new Label("lite Catalogue exist:");
           this.cata1 = new ComboBox<String>();
        list2=new ArrayList<>();
     
      for(Catalogue listes:catalogueServices.getInstance().getCatalogueAll()){
   //   c+=listes.getNom()+"\n";
   int v=(int)listes.getId();
 
     // list2.add(listes.getNom());
        this.cata1.addItem(listes.getNom());
        String n=String.valueOf(listes.getId());
      this.cata1.addItem(n);
      }
       
     //   this.cata1.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
        
        
         /*****************************************************************************************/
         /********************************En Vente***************************************************/

         Label envente  =new Label("Nom :");
         TextArea envente1  =new TextArea(""+CategorieList.b.getNom());
        envente.getAllStyles().setMargin(Component.BOTTOM,40);

        envente1.getAllStyles().setMargin(Component.BOTTOM,7);

        Style butStyle=envente1.getAllStyles();
        butStyle.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        envente.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   envente1.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   envente1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
   envente.getAllStyles().setFgColor(0x000000);
envente1.getAllStyles().setFgColor(0x000000);
/***********************************************************************************/
         /****************************Quantity*******************************************************/

       Label stock=new Label("Description :");
         TextArea stock1  =new TextArea(""+CategorieList.b.getDescription());
        stock.getAllStyles().setMargin(Component.BOTTOM,40);

        stock1.getAllStyles().setMargin(Component.BOTTOM,7);

        Style butStyle1=stock1.getAllStyles();
        butStyle1.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(130).
                stroke(borderStroke));
        stock.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   stock.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   stock1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
   stock.getAllStyles().setFgColor(0x000000);
stock1.getAllStyles().setFgColor(0x000000);
/***********************************************************************************/

         /****************************Price*******************************************************/

        Label image  =new Label("Image :");
         TextArea image1  =new TextArea(""+CategorieList.b.getImage());
        image.getAllStyles().setMargin(Component.BOTTOM,120);

        image1.getAllStyles().setMargin(Component.BOTTOM,7);

        Style butStyle2=image1.getAllStyles();
        butStyle2.setBorder(RoundRectBorder.create().
                strokeColor(0).
                strokeOpacity(120).
                stroke(borderStroke));
        image.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   image.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
   image1.getAllStyles().setFont(Font.createSystemFont(TOP, TOP, 500));
   image.getAllStyles().setFgColor(0x000000);
image1.getAllStyles().setFgColor(0x000000);
 Label labImage = new Label();
/***********************************************************************************/
    /**************************************************************************************************/             
                         Button valider = new Button("modifier");

          Button btCapture = new Button("upload");

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
  /**************************************************************************************************/   
                     Container cnt3=new Container(new FlowLayout(Container.CENTER));
/***********************************************************************************/
Container c1=BoxLayout.encloseY(listCatalo,envente0,envente,stock,image);

Container c3=BoxLayout.encloseY(cata1,envente10,envente1,stock1,image1);


setScrollableY(false);

cnt3.addAll(valider,btCapture,labImage);
Container c4=BoxLayout.encloseX(c1,c3);

c.getAllStyles().setMargin(Component.BOTTOM,150);
c4.getAllStyles().setMargin(Component.TOP,10);
c4.getAllStyles().setMargin(Component.LEFT,50);
c4.getAllStyles().setMargin(Component.RIGHT,50);
//c4.getStyle().setBgColor(0x000000);
c4.getStyle().setBgTransparency(250);
String cata=cata1.getSelectedItem().toString();
            //   envente10.setText(cata.toString());
      valider.addActionListener(e->{
               String envente3 = envente10.getText();
          System.out.println("catalogue++"+envente3);
          String envente2 = envente1.getText();
          System.out.println("nom+++"+envente2);
          String stock2 = stock1.getText();
          System.out.println("description++"+stock2);
           String image2 = path;
          System.out.println("image++"+image2);
                     







CategorieList.b.setCatalogue(new Catalogue(Integer.parseInt(envente3)));
 CategorieList.b.setNom(envente2);
  CategorieList.b.setDescription(stock2);
CategorieList.b.setImage(image2);

  
if(categorieServices.getInstance().UpdateCategorieAction(CategorieList.b))
{                            Dialog.show("Success","evenement modifié ",new Command("OK"));


} else                             Dialog.show("ERROR", " Server error", new Command("OK"));

});             
      
      
      Container MainCont=BoxLayout.encloseY( c4, cnt3);
MainCont.getAllStyles().setMargin(Component.TOP,1);
MainCont.getAllStyles().setMargin(Component.LEFT,1);
MainCont.getAllStyles().setMargin(Component.RIGHT,1);
MainCont.getAllStyles().setMargin(Component.BOTTOM,1);
MainCont.getStyle().setBgColor(0xFFFFFF);
MainCont.getStyle().setBgTransparency(255);

addAll(MainCont);
/***********************************************************************************/
  /*******************************************************************************************/       
         /*********************************/
          


    }

 
    
    
}
