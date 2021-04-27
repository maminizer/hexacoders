package Entities;

/**
 *
 * @author khamm
 */
public class Product {
    private int id;
    private String title;
    private float price;
    private int quantity;

    public Product() {
    }

    public Product(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Product{" + "title=" + title + ", price=" + price + ", quantity=" + quantity + "}\n";
    }
    
    
    
}
