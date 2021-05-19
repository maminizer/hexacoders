/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author khamm
 */
public class Commande {

    public int id, id_product, id_user, quantity;
    public boolean validated;
    public String created_at, nom_product;

    public Commande(int id, int id_product, int id_user, int quantity, boolean validated, String created_at) {
        this.id = id;
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.validated = validated;
        this.created_at = created_at;
    }

    public Commande(String nom_product, int id_user, int quantity) {
        this.nom_product = nom_product;
        this.id_user = id_user;
        this.quantity = quantity;
    }

    public void setNom_product(String nom_product) {
        this.nom_product = nom_product;
    }

    public String getNom_product() {
        return nom_product;
    }

    public Commande(int id, int id_product, int id_user, int quantity, boolean validated) {
        this.id = id;
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.validated = validated;
    }

    public Commande(int id_product, int id_user, int quantity, boolean validated) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.validated = validated;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", id_product=" + id_product + ", id_user=" + id_user + ", quantity=" + quantity + ", validated=" + validated + ", created_at=" + created_at + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId_product() {
        return id_product;
    }

    public int getId_user() {
        return id_user;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isValidated() {
        return validated;
    }

    public String getCreated_at() {
        return created_at;
    }

    public Commande() {
    }

}
