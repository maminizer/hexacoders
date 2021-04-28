package Entities;

import java.io.InputStream;

/**
 *
 * @author khamm
 */
public class Product {
    private int id;
    private String title;
    private float price;
    private int quantity;
     private InputStream imageJ;
    private int en_vente;
    private String description;
    
     
    private int categorie_id;
  
    private String image;

    public Product(int id, String title, float price, int quantity, InputStream imageJ, int en_vente, String description, int categorie_id, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.imageJ = imageJ;
        this.en_vente = en_vente;
        this.description = description;
        this.categorie_id = categorie_id;
        this.image = image;
    }

    public Product(String title, float price, int quantity, InputStream imageJ, int en_vente, String description, int categorie_id, String image) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.imageJ = imageJ;
        this.en_vente = en_vente;
        this.description = description;
        this.categorie_id = categorie_id;
        this.image = image;
    }

    public InputStream getImageJ() {
        return imageJ;
    }

    public void setImageJ(InputStream imageJ) {
        this.imageJ = imageJ;
    }

 

    public Product() {
    }

    public Product(int id,String title) {
        this.id = id;
        this.title=title;
    }

    public Product(int id, String title, float price, int quantity, int categorie_id, String image, int en_vente, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.categorie_id = categorie_id;
        this.image = image;
        this.en_vente = en_vente;
        this.description = description;
    }
    
    

    public Product(String title, float price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int id, String title, float price, int quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getEn_vente() {
        return en_vente;
    }

    public void setEn_vente(int en_vente) {
        this.en_vente = en_vente;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" + "title=" + title + ", price=" + price + ", quantity=" + quantity + "}\n";
    }
     public String TitletoString() {
        return this.getTitle();
    }
    
    
}
