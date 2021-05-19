/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author yossr
 */
public class Product {
    int id;
    String title;
    String image;
    float price;
    boolean enVente;
    int quantity;
    String description;

    public Product() {
    }

    public Product(String title, String image, float price, boolean enVente, int quantity, String description) {
        this.title = title;
        this.image = image;
        this.price = price;
        this.enVente = enVente;
        this.quantity = quantity;
        this.description = description;
    }

    public Product(int id, String title, String image, float price, boolean enVente, int quantity, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.enVente = enVente;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public boolean getEnVente() {
        return enVente;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setEnVente(boolean enVente) {
        this.enVente = enVente;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
