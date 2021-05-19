/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hexacoders.tunipharma;


import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import Entity.Livreur;
import WebService.LivreurService;




/**
 *
 * @author PCS
 */
public class addLivreur extends Form{
    public addLivreur(Form previous){
        setTitle("Add a new Livreur");
        
        setLayout(BoxLayout.y());
        
        TextField tfName = new TextField("","LivreurName");
        TextField tfPrenom = new TextField("","Prenom");
        TextField tfcin = new TextField("","Cin");
        TextField tfemail = new TextField("","Email");
        TextField tfNumtel = new TextField("","Num Tel");
        ComboBox cb = new ComboBox("Disponibilité",0,1);
        
      
        Button btnValider = new Button("Add Livreur");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length()==0)||(tfPrenom.getText().length()==0) ||(tfemail.getText().length()==0) ||(tfNumtel.getText().length()==0) ||(tfcin.getText().length()==0) )
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Livreur l = new Livreur(tfName.getText(),tfPrenom.getText(),Integer.parseInt(cb.getSelectedItem().toString()),Integer.parseInt(tfcin.getText()),Integer.parseInt(tfNumtel.getText()),tfemail.getText());
                        System.out.println("okk");
                        System.out.println(l);
                        if( LivreurService.getInstance().addLivreur(l))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                       
                    }                   
                }               
            }
        });
       
        
        
    
        
        addAll(tfName,tfPrenom,tfcin,tfemail,tfNumtel,cb,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
}
