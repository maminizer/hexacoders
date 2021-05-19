/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
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
import Entity.Product;
import WebService.ServiceProduct;
import java.io.IOException;


/**
 *
 * @author yossr
 */
public class AjouterProd extends Form{
  String path;
    public AjouterProd(Form previous) {
                setTitle("Add Product");
        setLayout(BoxLayout.y());
         getToolbar().addCommandToOverflowMenu("Exit",
                                              null, ev-> {Display.getInstance().exitApplication();});
        //insert code here
        TextField tfTitle = new TextField("","Title");
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
         TextField tfPrice = new TextField("", "price");
        TextField tfenvente = new TextField("", "Availability");
        TextField tfquantite = new TextField("", "quantity");
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
                        String title = tfTitle.getText();
                        String image = path;
                        float price = Float.parseFloat(tfPrice.getText());
                        boolean envente = Boolean.parseBoolean(tfenvente.getText());
                        int quantite = Integer.parseInt(tfquantite.getText());
                        String descu = tfdesc.getText();
                        Product p = new Product(title,image,price,envente,quantite,descu);
                        if(ServiceProduct.getInstance().addProduct(p))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "price must be a number", new Command("OK"));
                    }
                    new ListProd(previous).show();
                }
                
                
            }
        });
        
        addAll(tfTitle,tfImage,tfPrice,tfenvente,tfquantite,tfdesc,btnValider,btCapture,labImage);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
