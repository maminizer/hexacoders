/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.InputStream;
import java.sql.Blob;
import javafx.scene.control.Button;

/**
 *
 * @author yossr
 */
public class product {
    private int id;
    private String title;
    private InputStream imageJ;
    private int price;
    private int quantity;
    private int en_vente;
    private String description;
    



    public product() {
    }

    public product(int id, String title, InputStream imageJ, int price, int quantity, int en_vente, String description) {
        this.id = id;
        this.title = title;
        this.imageJ = imageJ;
        this.price = price;
        this.quantity = quantity;
        this.en_vente = en_vente;
        this.description = description;
    }

    public product(String title, InputStream imageJ, int price, int quantity, int en_vente, String description) {
        this.title = title;
        this.imageJ = imageJ;
        this.price = price;
        this.quantity = quantity;
        this.en_vente = en_vente;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public InputStream getImageJ() {
        return imageJ;
    }

    public int getEn_vente() {
        return en_vente;
    }

    public String getDescription() {
        return description;
    }

    public product(String title, int price, int quantity, int en_vente, String description) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.en_vente = en_vente;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageJ(InputStream imageJ) {
        this.imageJ = imageJ;
    }

    public void setTitle(String title) {
        this.title = title;
    }





    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setEn_vente(int en_vente) {
        this.en_vente = en_vente;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "product{" + "id=" + id + ", title=" + title + ", image=" + imageJ + ", price=" + price + ", quantity=" + quantity + ", en_vente=" + en_vente + ", description=" + description + '}';
    }


}
