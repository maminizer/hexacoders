
package Entities;

/**
 *
 * @author khamm
 */
public class Commande {
    public int id_product,id_user,quantity;
    public String created_at;
    public int validated;

    public Commande() {
    }

    public Commande(int id_product, int id_user, int quantity, String created_at) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.created_at = created_at;
    }

    public Commande(int id_product, int id_user, int quantity, String created_at, int validated) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.created_at = created_at;
        this.validated = validated;
    }

    public int getValidated() {
        return validated;
    }

    public void setValidated(int validated) {
        this.validated = validated;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_product=" + id_product + ", id_user=" + id_user + ", quantity=" + quantity + ", created_at=" + created_at + "}\n";
    }
    
    
    
}
