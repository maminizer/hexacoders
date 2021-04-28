package Entities;

/**
 *
 * @author khamm
 */
public class Cart {
    private String title;
    private float price;
    private int quantity;
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Cart(String title, float price, int quantity, String username) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.username = username;
    }

    public Cart() {
    }

    public Cart(String title, float price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
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
        return "Cart{" + "title=" + title + ", price=" + price + ", quantity=" + quantity + "}\n";
    }

    
}
